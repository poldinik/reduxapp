package studio.volare.reduxapp.redux;

import com.google.gwt.core.client.GWT;
import elemental2.promise.Promise;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.subjects.PublishSubject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Store<S> {

    S state;
    Reducer<S> reducer;
    List<NextDispatcher> dispatchers;
    Observable<S> stream;
    ObservableEmitter<S> emitter;
    Promise<S> promise;
    List<Middleware> middleware;
    Publisher publisher;
    PublishSubject<S> subject;


    public Store(Reducer reducer, S initialState, List<Middleware> middleware, boolean syncStream, boolean distinct){
        this.reducer = reducer;
        this.state = initialState;
        this.middleware = middleware;
        syncStream = false;
        distinct = false;
        dispatchers = createDispatchers(middleware, createReduceAndNotify(distinct));
        stream = Observable.empty();
        subject = PublishSubject.create();
    }

    public void setState(S state) {
        GWT.log("State changed!");
        GWT.log("curent state: " + state.toString());
        this.state = state;
        subject.onNext(state);
    }


    public S getState() {
        return state;
    }

    public void init(S state){
        this.state = state;
    }

    NextDispatcher createReduceAndNotify(boolean distinct){
        return action -> {
            GWT.log("S state = reducer.call(this.state, action);");
            S state = reducer.call(this.state, action);
            if (distinct && state.equals(this.state)) return this; //??
            setState(state);
            GWT.log("Notify");
            stream = Observable.just(state);
            return this;
        };
    }

    List<NextDispatcher> createDispatchers(List<Middleware> middleware, NextDispatcher reduceAndNotify){
        dispatchers = new ArrayList<>();
        dispatchers.add(reduceAndNotify);

        Collections.reverse(middleware);
        //convert each Middleware into a NextDispatcher
        //TODO: verificare correttezza, semplicemente una funziona chiama la prossima, dove l'ultima è un Next Dispatcher
        //TODO: implementato chiamando il root reducer dello stato
        for(Middleware nextMiddleware: middleware) {
            NextDispatcher next = dispatchers.get(dispatchers.size() - 1);
            dispatchers.add(action -> nextMiddleware.call(this, action, next));
        }

        Collections.reverse(dispatchers);

        return dispatchers;
    }

    public Observable<S> onChange(){
        return subject;
    };


    public Object dispatch(Object action){
        GWT.log("dispatchers.get(0).run(action);");
        return dispatchers.get(0).run(action);
    }


}

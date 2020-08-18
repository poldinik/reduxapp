package studio.volare.reduxapp.redux;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Store<S> {

    S state;
    Reducer<S> reducer;
    List<NextDispatcher> dispatchers;
    Observable<S> stream;

    public Store(Reducer reducer, S initialState, List<Middleware<S>> middleware, boolean syncStream, boolean distinct){
        this.reducer = reducer;
        this.state = initialState;
        syncStream = false;
        distinct = false;
        dispatchers = createDispatchers(middleware, createReduceAndNotify(distinct));

//        ObservableEmitter<S> e = new ObservableEmitter<S>() {
//            @Override
//            public void setDisposable(Disposable d) {
//
//            }
//
//            @Override
//            public void setCancellable(Cancellable c) {
//
//            }
//
//            @Override
//            public boolean isDisposed() {
//                return false;
//            }
//
//            @Override
//            public ObservableEmitter<S> serialize() {
//                return null;
//            }
//
//            @Override
//            public boolean tryOnError(Throwable t) {
//                return false;
//            }
//
//            @Override
//            public void onNext(S value) {
//
//            }
//
//            @Override
//            public void onError(Throwable error) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        ObservableOnSubscribe<S> observableOnSubscribe = new ObservableOnSubscribe<S>() {
//
//
//            @Override
//            public void subscribe(ObservableEmitter<S> e) throws Exception {
//
//            }
//        };
//
//
//        stream = Observable.create(observableOnSubscribe);


        //TODO: cambiare perchè emetta lo stato continuamente, cioè ogni volta che c'è un reducer sostanzialmente da chiamare
        stream = Observable.just(state);

    }

    public S getState() {
        return state;
    }

    public void init(S state){
        this.state = state;
    }

    NextDispatcher createReduceAndNotify(boolean distinct){
        return action -> {
            S state = reducer.call(this.state, action);
            if (distinct && state.equals(this.state)) return this; //??
            this.state = state;
            stream = Observable.just(state);
            return this;
        };
    }

    List<NextDispatcher> createDispatchers(List<Middleware<S>> middleware, NextDispatcher reduceAndNotify){
        dispatchers = new ArrayList<>();
        dispatchers.add(reduceAndNotify);

        Collections.reverse(middleware);
        //convert each Middleware into a NextDispatcher
        for(Middleware nextMiddleware: middleware) {
            NextDispatcher next = dispatchers.get(dispatchers.size() - 1);
            dispatchers.add(action -> nextMiddleware.call(this, action, next));
        }

        Collections.reverse(dispatchers);

        return dispatchers;
    }

    Observable<S> onChange(){
        return stream;
    };


    public Object dispatch(Object action){
        return dispatchers.get(0);
    }


}

package studio.volare.reduxapp.gwtredux;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StreamBuilder<T> extends Composite {

    interface StreamBuilderUiBinder extends UiBinder<Widget, StreamBuilder> {
    }

    private static StreamBuilderUiBinder ourUiBinder = GWT.create(StreamBuilderUiBinder.class);

    T initialData;
    Observable<T> stream;
    AsyncSnapshotFactory.AsyncSnapshot<T> summary;
    AsyncWidgetBuilder<T> builder;
    AsyncSnapshotFactory<T> asyncSnapshotFactory;
    @UiField HTMLPanel root;

    public StreamBuilder(T initialData, Observable<T> stream, AsyncWidgetBuilder<T> builder){
        initWidget(ourUiBinder.createAndBindUi(this));
        this.initialData = initialData;
        this.stream = stream;
        this.builder = builder;
        asyncSnapshotFactory = new AsyncSnapshotFactory<>();
        summary = initial();
        subscribe();
    }

    public StreamBuilder(Observable<T> stream, AsyncWidgetBuilder<T> builder){
        initWidget(ourUiBinder.createAndBindUi(this));
        this.stream = stream;
        this.builder = builder;
        asyncSnapshotFactory = new AsyncSnapshotFactory<>();
        summary = initial();
        subscribe();
    }

    void setSummary(AsyncSnapshotFactory.AsyncSnapshot<T> current){
        summary = current;
        GWT.log(current.connectionState.toString());
        //Ogni volta che c'è un setSummary riappende l'oggetto ricostruito
        build(current);
    }

    void build(AsyncSnapshotFactory.AsyncSnapshot<T> current){
        GWT.log("StreamBuilder build");
        root.clear();
        root.add(builder.build(current));
        if(current.data != null){
            GWT.log("data recived for stream builder");
        }
    }

    // if (widget.stream != null) {
    //      _subscription = widget.stream.listen((T data) {
    //        setState(() {
    //          _summary = widget.afterData(_summary, data);
    //        });
    //      }, onError: (Object error) {
    //        setState(() {
    //          _summary = widget.afterError(_summary, error);
    //        });
    //      }, onDone: () {
    //        setState(() {
    //          _summary = widget.afterDone(_summary);
    //        });
    //      });
    //      _summary = widget.afterConnected(_summary);
    //    }

    void subscribe(){

        //TODO: approfondire per fare subscribe tramite observable on Complete on error ecc..
        if(stream != null){
            stream.subscribe(new Observer<T>() {
                @Override
                public void onSubscribe(Disposable d) {
                    GWT.log("onSubscribe Stream Builder");
                }

                @Override
                public void onNext(T t) {
                    GWT.log("setSummary(afterData(summary, t));");
                    setSummary(afterData(summary, t));
                }

                @Override
                public void onError(Throwable e) {
                    setSummary(afterError(summary, e));
                }

                @Override
                public void onComplete() {
                    GWT.log("setSummary(afterDone(summary));");
                    setSummary(afterDone(summary));
                }
            });

            //setSummary(afterData(summary, data));
            GWT.log("afterConnected(summary)");
            setSummary(afterConnected(summary));
        }
    }

    AsyncSnapshotFactory.AsyncSnapshot<T> initial(){

        return asyncSnapshotFactory.withData(ConnectionState.NONE, initialData);
    }

    AsyncSnapshotFactory.AsyncSnapshot<T> afterConnected(AsyncSnapshotFactory.AsyncSnapshot<T> current){
        return current.inState(ConnectionState.WAITING);
    }

    AsyncSnapshotFactory.AsyncSnapshot<T> afterData(AsyncSnapshotFactory.AsyncSnapshot<T> current, T data){
        return asyncSnapshotFactory.withData(ConnectionState.ACTIVE, data);
    }

    AsyncSnapshotFactory.AsyncSnapshot<T> afterError(AsyncSnapshotFactory.AsyncSnapshot<T> current, Object error){
        return asyncSnapshotFactory.withError(ConnectionState.ACTIVE, error);
    }

    AsyncSnapshotFactory.AsyncSnapshot<T> afterDone(AsyncSnapshotFactory.AsyncSnapshot<T> current){
        return current.inState(ConnectionState.DONE);
    }


}

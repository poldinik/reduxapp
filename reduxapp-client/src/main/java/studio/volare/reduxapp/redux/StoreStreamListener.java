package studio.volare.reduxapp.redux;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import studio.volare.reduxapp.MainView;

public class StoreStreamListener<S, ViewModel> extends Composite {
    final ViewModelBuilder<ViewModel> builder;
    final StoreConverter<S, ViewModel> converter;
    final Store<S> store;
    boolean distinct;
    OnInitCallback<S> onInit;
    OnDisposeCallback<S> onDispose;
    boolean rebuildOnChange;
    IgnoreChangeTest<S> ignoreChange;
    OnWillChangeCallback<ViewModel> onWillChange;
    OnDidChangeCallback<ViewModel> onDidChange;
    OnInitBuildCallback<ViewModel> onInitialBuild;
    Observable<ViewModel> stream;
    ViewModel latestValue;

    @UiField
    HTMLPanel panel;

    interface StoreStreamListenerUiBinder extends UiBinder<Widget, StoreStreamListener> {
    }

    private static StoreStreamListenerUiBinder ourUiBinder = GWT.create(StoreStreamListenerUiBinder.class);

    public StoreStreamListener(ViewModelBuilder<ViewModel> builder, StoreConverter<S, ViewModel> converter, Store<S> store) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.builder = builder;
        this.converter = converter;
        this.store = store;
        this.rebuildOnChange = true;

        latestValue = converter.convert(store);
        createStream();

        panel.add(builder.build(latestValue));

    }

    ViewModel mapConverter(S state){
        //return widget.converter(widget.store);
        return converter.convert(store);
    }

    boolean whereDistinct(ViewModel vm){
        if(distinct){
            return !vm.equals(latestValue);
        }
        return true;
    }

    boolean ignoreChange(S state){
        if(ignoreChange != null){
            return !ignoreChange(state);
        }

        return true;
    }

    void createStream(){

        //stream = (Observable<S>) store.onChange().map(this::mapConverter).subscribe(this::handleChange);

        stream = store.onChange().map(this::mapConverter);
        stream.subscribe(this::handleChange);

        //stream = widget.store.onChange
        //        .where(_ignoreChange)
        //        .map(_mapConverter)
        //        // Don't use `Stream.distinct` because it cannot capture the initial
        //        // ViewModel produced by the `converter`.
        //        .where(_whereDistinct)
        //        // After each ViewModel is emitted from the Stream, we update the
        //        // latestValue. Important: This must be done after all other optional
        //        // transformations, such as ignoreChange.
        //        .transform(StreamTransformer.fromHandlers(handleData: _handleChange));



    }

    void handleChange(ViewModel vm){
        GWT.log("handleChange!");
        latestValue = vm;
    }

    Widget build(){
        return null;
    }
}

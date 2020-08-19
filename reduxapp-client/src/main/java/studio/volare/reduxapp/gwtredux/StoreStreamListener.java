package studio.volare.reduxapp.gwtredux;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import io.reactivex.Observable;
import studio.volare.reduxapp.redux.Store;

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
    @UiField HTMLPanel root;

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
        build();
    }

    void build(){
        root.clear();
        root.add(new StreamBuilder<>(stream, snapshot -> builder.build(latestValue)));
    }

    ViewModel mapConverter(S state){
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
        stream = store.onChange().map(this::mapConverter);
        stream.subscribe(this::handleChange);
    }

    void handleChange(ViewModel vm){
        GWT.log("handleChange!");
        latestValue = vm;
    }

}

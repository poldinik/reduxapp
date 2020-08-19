package studio.volare.reduxapp.gwtredux;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import studio.volare.reduxapp.redux.Store;
import studio.volare.reduxapp.redux.StoreRepository;

public class StoreConnector<S, ViewModel> extends Composite {

    final ViewModelBuilder<ViewModel> builder;
    final StoreConverter<S, ViewModel> converter;
    Store<S> store;
    final StoreStreamListener<S, ViewModel> storeStreamListener;

    @UiField
    HTMLPanel panel;

    interface StoreConnectorUiBinder extends UiBinder<Widget, StoreConnector> {
    }

    private static StoreConnectorUiBinder ourUiBinder = GWT.create(StoreConnectorUiBinder.class);


    public StoreConnector(StoreConverter<S, ViewModel> converter, ViewModelBuilder<ViewModel> builder) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.builder = builder;
        this.converter = converter;
        //TODO: store deve arrivare da qualche parte, sarebbe meglio tramide CDI tipo
        this.store = StoreRepository.getInstance().getStore();
        this.storeStreamListener = new StoreStreamListener<>(builder, converter, store);
        panel.add(storeStreamListener);
    }



}

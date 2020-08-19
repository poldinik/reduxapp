package studio.volare.reduxapp.gwtredux;

import com.google.gwt.user.client.ui.Widget;
import studio.volare.reduxapp.redux.Store;

public class StoreProvider<S> {
    final Store<S> store;
    final Widget widget;

    public StoreProvider(Store<S> store, Widget widget) {
        this.store = store;
        this.widget = widget;
    }
}

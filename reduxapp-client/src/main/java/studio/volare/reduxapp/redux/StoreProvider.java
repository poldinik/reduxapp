package studio.volare.reduxapp.redux;

import com.google.gwt.user.client.ui.Widget;

public class StoreProvider<S> {
    final Store<S> store;
    final Widget widget;

    public StoreProvider(Store<S> store, Widget widget) {
        this.store = store;
        this.widget = widget;
    }
}

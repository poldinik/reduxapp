package studio.volare.reduxapp.gwtredux;

import com.google.gwt.user.client.ui.Widget;

public interface AsyncWidgetBuilder<T> {
    Widget build(AsyncSnapshotFactory.AsyncSnapshot<T> snapshot);
}

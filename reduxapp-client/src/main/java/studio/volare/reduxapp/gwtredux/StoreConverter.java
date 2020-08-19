package studio.volare.reduxapp.gwtredux;

import studio.volare.reduxapp.redux.Store;

public interface StoreConverter<S, ViewModel> {
    ViewModel convert(Store<S> store);
}

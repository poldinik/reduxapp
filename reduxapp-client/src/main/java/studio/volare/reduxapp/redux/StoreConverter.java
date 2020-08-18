package studio.volare.reduxapp.redux;

public interface StoreConverter<S, ViewModel> {
    ViewModel convert(Store<S> store);
}

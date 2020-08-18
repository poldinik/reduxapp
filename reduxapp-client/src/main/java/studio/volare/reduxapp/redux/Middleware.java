package studio.volare.reduxapp.redux;

public interface Middleware<S> {
    Object call(Store<S> store, Object action, NextDispatcher next);
}

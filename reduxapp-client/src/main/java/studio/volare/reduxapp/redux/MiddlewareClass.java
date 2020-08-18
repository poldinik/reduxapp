package studio.volare.reduxapp.redux;

public abstract class MiddlewareClass<S> {

    //Mettere le Promise come future
    abstract void call(Store<S> store, Object action, NextDispatcher next);
}

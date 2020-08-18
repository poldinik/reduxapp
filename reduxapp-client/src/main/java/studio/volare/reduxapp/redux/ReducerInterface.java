package studio.volare.reduxapp.redux;

public interface ReducerInterface<S> {

    S call(S state, Action action);
}

package studio.volare.reduxapp.redux;

public interface Reducer<S> {
    S call(S state, Object action);
}

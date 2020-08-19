package studio.volare.reduxapp.redux;

import java.util.Collection;

public class ReducersCombiner<S> {
    public Reducer<S> combineReducers(Collection<Reducer<S>> reducers){
        return (state, action) -> {
            for(Reducer<S> r: reducers){
                state = r.call(state, action);
            }
            return state;
        };
    }
}

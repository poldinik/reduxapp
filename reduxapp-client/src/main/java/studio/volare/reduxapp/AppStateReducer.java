package studio.volare.reduxapp;

import com.google.gwt.core.client.GWT;
import studio.volare.reduxapp.redux.Reducer;

public class AppStateReducer implements Reducer<AppState> {

    @Override
    public AppState call(AppState state, Object action) {

        if(action instanceof LoadedRandomText){
            GWT.log("instanceof LoadedRandomText");
            return new AppState(state.isLoading(), ((LoadedRandomText) action).getText());
        }else if(action instanceof LoadingAction){
            GWT.log("instanceof LoadingAction");
            return new AppState(((LoadingAction) action).loading, state.text);
        } else{
            return AppState.copyWith(state.isLoading(), state.text);
        }
    }
}

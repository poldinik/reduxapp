package studio.volare.reduxapp;


import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import elemental2.dom.DomGlobal;
import studio.volare.reduxapp.redux.Middleware;
import studio.volare.reduxapp.redux.NextDispatcher;
import studio.volare.reduxapp.redux.Store;

import java.util.Random;

public class AppStateMiddleware implements Middleware<AppState> {

    @Override
    public Object call(Store<AppState> store, Object action, NextDispatcher next) {

        if(action instanceof GetRandomText){
            Random rn = new Random();
            int i = 1 + rn.nextInt(10);
            String API = "https://jsonplaceholder.typicode.com/todos/" + i;
            DomGlobal.fetch(API)
                    .then( r -> r.text() )
                    .then(
                            data -> {
                                Console.log(data);
                                JSONValue jsonValue = JSONParser.parseStrict(data);
                                JSONValue jsonText = jsonValue.isObject().get("title");
                                JSONString text = jsonText.isString();
                                store.dispatch(new LoadedRandomText(text.toString()));
                                store.dispatch(new LoadingAction(false));
                                return null;
                            }
                    );
        }

        return next.run(action);
    }
}

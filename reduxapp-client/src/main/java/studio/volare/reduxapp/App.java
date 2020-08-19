package studio.volare.reduxapp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import studio.volare.reduxapp.gwtredux.StoreConnector;
import studio.volare.reduxapp.gwtredux.StoreConverter;
import studio.volare.reduxapp.redux.*;

import java.util.*;

public class App implements EntryPoint {

	public void onModuleLoad() {

		Middleware<AppState> appStateMiddleware = new AppStateMiddleware();
		List<Middleware> middleware = Collections.singletonList(appStateMiddleware);

		//simula una sorta di injection application scoped....
		StoreRepository.getInstance().setStore(
				new Store<>(new AppStateReducer(), AppState.init(), middleware, true, true)
		);

		RootPanel
				.get()
				.add(
						new StoreConnector<>(
								(StoreConverter<AppState, MainView.ViewModel>) store -> new MainView.ViewModel() {
									@Override
									public boolean isLoading() {
										return store.getState().isLoading();
									}

									@Override
									public String getText() {
										return store.getState().getText();
									}

									@Override
									public void changeTitle(String title) {
										GWT.log("store.dispatch(new GetRandomString());");
										store.dispatch(new LoadingAction(true));
										store.dispatch(new GetRandomText());
									}
								},
								MainView::new
						)
		);


	}

}

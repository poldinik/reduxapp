package studio.volare.reduxapp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import studio.volare.reduxapp.redux.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class App implements EntryPoint {

	public void onModuleLoad() {
		//MainView mainView = new MainView();


		//simula una sorta di application scoped....
		StoreRepository.getInstance().setStore(
				new Store<>(
						null,
						AppState.init(),
						new ArrayList<>(),
						true,
						true
				)
		);

		RootPanel
				.get()
				.add(
						new StoreConnector<>(
								(StoreConverter<AppState, MainView.ViewModel>) store -> new MainView.ViewModel() {
									@Override
									public String getTitle() {
										return store.getState().getTitle();
									}

									@Override
									public void setLoading(boolean loading) {
										store.dispatch(new SetLoadingAction(loading));
									}
								},
						MainView::new
				)
		);


		MyState state = new MyState("ddd");


//		Store<MyState> store = Store.getInstance();
//		store.init(state);
//
//		GWT.log(store.getState().getState());
//
//
//

		//http://reactivex.io/documentation/operators.html
		Observable.just("Hello world").subscribe(GWT::log);
		Observable.range(0,10).map(this::square).filter(n -> n > 10).subscribe(n -> GWT.log(n.toString()));
		Flowable.range(0,10).map(this::square).subscribe(n -> GWT.log(n.toString()));

		//Va all'infinito, occhio
		//RxFibonacci.fibs().subscribe(n -> GWT.log(n.toString()));
	}

	private int square(Integer n) {
		return n*n;
	}

}

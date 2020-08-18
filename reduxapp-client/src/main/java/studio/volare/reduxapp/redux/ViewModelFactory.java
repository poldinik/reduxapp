package studio.volare.reduxapp.redux;

public interface ViewModelFactory<V extends ViewModel> {

   V fromStore(Store<AppState> store);
}

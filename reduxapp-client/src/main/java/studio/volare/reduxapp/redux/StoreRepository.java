package studio.volare.reduxapp.redux;

public class StoreRepository<S> {

    static StoreRepository instance = new StoreRepository();
    Store<S> store;

    public static StoreRepository getInstance(){
        return instance;
    }

    private StoreRepository(){

    }

    public Store<S> getStore() {
        return store;
    }

    public void setStore(Store<S> store) {
        this.store = store;
    }
}

package studio.volare.reduxapp.gwtredux;

import com.google.gwt.core.client.GWT;

public class AsyncSnapshotFactory<T> {

    public static class AsyncSnapshot<T> {
        final ConnectionState connectionState;
        final T data;
        final Object error;

        private AsyncSnapshot(ConnectionState connectionState, T data, Object error) {
            this.connectionState = connectionState;
            this.data = data;
            this.error = error;
        }

        private boolean hasData(){
            return data != null;
        }

        private boolean hasError(){
            return error != null;
        }

        T getRequireData(){
            //TODO: gestire meglio questo algoritmo, soprattutto per lancio eccezione
            if(hasData()){
                return data;
            }
            if (hasError()){
                GWT.log("Errore");
            }
            return data;
        }

        AsyncSnapshot<T> inState(ConnectionState state){
            return new AsyncSnapshot<>(state, data, error);
        }
    }

    public AsyncSnapshot<T> nothing(){
        return new AsyncSnapshot(ConnectionState.NONE, null, null);
    }

    public AsyncSnapshot<T> withData(ConnectionState state, T data){
        return new AsyncSnapshot(state, data, null);
    }

    public AsyncSnapshot<T> withError(ConnectionState state, Object error){
        return new AsyncSnapshot(state, null, error);
    }

}

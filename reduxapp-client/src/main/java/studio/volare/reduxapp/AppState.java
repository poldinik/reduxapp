package studio.volare.reduxapp;


//è immutabile
public final class AppState {
    final boolean loading;
    final String text;

    public AppState(boolean loading, String text) {
        this.loading = loading;
        this.text = text;
    }

    public boolean isLoading() {
        return loading;
    }

    public String getText() {
        return text;
    }

    public static AppState init(){
        return new AppState(false, "Testo iniziale");
    }

    @Override
    public String toString() {
        return "AppState{" + "loading=" + loading + ", text='" + text + '\'' + '}';
    }

    public static AppState copyWith(boolean loading, String text){
        return new AppState(loading, text);
    }
}

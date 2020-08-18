package studio.volare.reduxapp.redux;

public final class AppState {
    final boolean loading;
    final String title;

    public AppState(boolean loading, String title) {
        this.loading = loading;
        this.title = title;
    }

    public boolean isLoading() {
        return loading;
    }

    public String getTitle() {
        return title;
    }

    public static AppState init(){
        return new AppState(false, "Titolo 1");
    }
}

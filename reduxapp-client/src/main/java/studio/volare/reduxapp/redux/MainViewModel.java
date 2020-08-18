package studio.volare.reduxapp.redux;

public abstract class MainViewModel{
    boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public abstract void setLoading(boolean loading);

}

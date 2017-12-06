package ua.sng.kiwitest.presenters;

public abstract class BasePresenter<T> {

    private T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public abstract void cancel();

    public abstract void destroy();

}

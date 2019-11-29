package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.reactive;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {

    private CompositeDisposable compositeDisposable;

    public void add(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    public void dispose() {
        getCompositeDisposable().dispose();
    }

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    public DisposableManager() {
    }
}
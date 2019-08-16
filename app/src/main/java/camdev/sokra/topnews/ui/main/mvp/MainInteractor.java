package camdev.sokra.topnews.ui.main.mvp;

import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.service.ArticlesService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainInteractor implements MainMVP.Interactor{
    ArticlesService articlesService;
    private CompositeDisposable disposable = new CompositeDisposable();
    public MainInteractor() {
        this.articlesService = ServiceGenerator.createService(ArticlesService.class);
    }

    @Override
    public void onLoadingData(String title, int page, int limit, final InteractorResponse<ArticilesRespone> response) {
        disposable.add(
                articlesService.getArticles(title,page,limit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<ArticilesRespone>() {
                            @Override
                            public void onNext(ArticilesRespone articilesRespone) {
                                response.onSuccess(articilesRespone);
                            }

                            @Override
                            public void onError(Throwable t) {
                                response.onError("Error: "+t.toString());
                            }

                            @Override
                            public void onComplete() {
                                response.onComplete("Complete");
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        disposable.clear();
    }
}

package camdev.sokra.topnews.ui.scrap.kohsantepheap.mvp;

import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.scrap.authservice.BlogServiceGenerator;
import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;
import camdev.sokra.topnews.scrap.service.BlogService;
import camdev.sokra.topnews.service.ArticlesService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainInteractor implements MainMVP.Interactor{
    BlogService articlesService;
    private CompositeDisposable disposable = new CompositeDisposable();
    public MainInteractor() {
        this.articlesService = BlogServiceGenerator.createBlogService();
    }

    @Override
    public void onLoadingData(int page, final InteractorResponse<ScrapArticlseRespone> response) {
        disposable.add(
                articlesService.getArticles(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<ScrapArticlseRespone>() {
                            @Override
                            public void onNext(ScrapArticlseRespone articilesRespone) {
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

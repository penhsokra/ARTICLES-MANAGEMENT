package camdev.sokra.topnews.ui.scrap.kohsantepheap.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;

public class MainPresenter implements MainMVP.Presenter{
    private MainMVP.Interactor interactor;
    private MainMVP.View view;

    public MainPresenter(MainMVP.View view) {
        this.interactor = new MainInteractor();
        this.view = view;
    }

    @Override
    public void onLoadingData(int page) {
        interactor.onLoadingData(page, new InteractorResponse<ScrapArticlseRespone>() {
            @Override
            public void onSuccess(ScrapArticlseRespone dataResponse) {
                view.onRequestSuccess(dataResponse);
            }

            @Override
            public void onComplete(String message) {
                view.onRequestComplete(message);
            }

            @Override
            public void onError(String message) {
                view.onRequestError(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        interactor.onDestroy();
    }
}

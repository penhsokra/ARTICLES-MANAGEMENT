package camdev.sokra.topnews.ui.main.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;

public class MainPresenter implements MainMVP.Presenter{
    private MainMVP.Interactor interactor;
    private MainMVP.View view;

    public MainPresenter(MainMVP.View view) {
        this.interactor = new MainInteractor();
        this.view = view;
    }

    @Override
    public void onLoadingData(String title,int page, int limit) {
        interactor.onLoadingData(title,page, limit, new InteractorResponse<ArticilesRespone>() {
            @Override
            public void onSuccess(ArticilesRespone dataResponse) {
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

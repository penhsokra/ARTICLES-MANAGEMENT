package camdev.sokra.topnews.ui.scrap.kohsantepheap.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;

public interface MainMVP {
    interface View{
        void onRequestSuccess(ScrapArticlseRespone articles);
        void onRequestComplete(String message);
        void onRequestError(String message);
    }
    interface Presenter{
        void onLoadingData(int page);
        void onDestroy();
    }
    interface Interactor{
        void onLoadingData(int page, InteractorResponse<ScrapArticlseRespone> response);
        void onDestroy();
    }
}

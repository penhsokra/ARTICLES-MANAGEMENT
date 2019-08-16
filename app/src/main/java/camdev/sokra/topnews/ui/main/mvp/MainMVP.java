package camdev.sokra.topnews.ui.main.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;

public interface MainMVP {
    interface View{
        void onRequestSuccess(ArticilesRespone articles);
        void onRequestComplete(String message);
        void onRequestError(String message);
    }
    interface Presenter{
        void onLoadingData(String title,int page,int limit);
        void onDestroy();
    }
    interface Interactor{
        void onLoadingData(String title,int page, int limit, InteractorResponse<ArticilesRespone> response);
        void onDestroy();
    }
}

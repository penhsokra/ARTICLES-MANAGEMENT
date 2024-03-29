package camdev.sokra.topnews.ui.insert.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.crud.ArticlesCRUDRespone;

public interface AddMPV {
    interface VIEW{
        void onSuccess(String message);
        void onError(String message);
    }
    interface Presentor{
        void onInsert(Articles articles);
    }
    interface Interactor{
        void onIsert(Articles articles,InteractorResponse<ArticlesCRUDRespone> responses);
    }
}

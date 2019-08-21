package camdev.sokra.topnews.ui.insert.mvp;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.crud.ArticlesCRUDRespone;

public class AddPresenter implements AddMPV.Presentor{
    private AddMPV.Interactor interactor;
    private AddMPV.VIEW view;

    public AddPresenter(AddMPV.VIEW view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onInsert(Articles articles) {
        interactor.onIsert(articles, new InteractorResponse<ArticlesCRUDRespone>() {
            @Override
            public void onSuccess(ArticlesCRUDRespone dataResponse) {

            }

            @Override
            public void onComplete(String message) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}

package camdev.sokra.topnews.ui.insert.mvp;

import android.widget.Toast;

import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;

public class AddPresenter implements AddMPV.Presentor{
    private AddMPV.Interactor interactor;
    private AddMPV.VIEW view;

    public AddPresenter(AddMPV.VIEW view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onInsert(Articles articles) {
        interactor.onIsert(articles, new InteractorResponse<ArticlesCRUD>() {
            @Override
            public void onSuccess(ArticlesCRUD dataResponse) {

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

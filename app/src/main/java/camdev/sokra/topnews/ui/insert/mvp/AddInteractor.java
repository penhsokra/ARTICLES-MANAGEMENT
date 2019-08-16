package camdev.sokra.topnews.ui.insert.mvp;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.callback.InteractorResponse;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;
import camdev.sokra.topnews.service.ArticlesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInteractor{
    ArticlesService articlesService;
    AppCompatActivity context;
    public AddInteractor() {
        this.articlesService = ServiceGenerator.createService(ArticlesService.class);
    }


}

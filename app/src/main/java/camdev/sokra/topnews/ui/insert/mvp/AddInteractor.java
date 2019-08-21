package camdev.sokra.topnews.ui.insert.mvp;

import android.support.v7.app.AppCompatActivity;

import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.service.ArticlesService;

public class AddInteractor{
    ArticlesService articlesService;
    AppCompatActivity context;
    public AddInteractor() {
        this.articlesService = ServiceGenerator.createService(ArticlesService.class);
    }


}

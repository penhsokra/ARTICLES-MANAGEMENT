package camdev.sokra.topnews.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.adapter.ArticlesAdapter;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;
import camdev.sokra.topnews.service.ArticlesService;
import camdev.sokra.topnews.ui.main.mvp.MainMVP;
import camdev.sokra.topnews.ui.main.mvp.MainPresenter;

public class DetailActivity extends AppCompatActivity implements MainMVP.View,ArticlesAdapter.OnArticleCRUD{
    private TextView dtitle,ddesc;
    private ImageView dImage;
    private ImageView btnBack;
    private RecyclerView rvRelate;
    private List<Articles> articlesList = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;
    private MainPresenter presenter;
    private TextView rlTitle;
    ArticlesService articlesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //View decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
       // decorView.setSystemUiVisibility(uiOptions);

        rlTitle = findViewById(R.id.title);
        btnBack = findViewById(R.id.btnBack);
        dtitle = findViewById(R.id.title);
        ddesc = findViewById(R.id.dartDetail);
        dImage = findViewById(R.id.imageURL);
        rvRelate = findViewById(R.id.rvRelate);

        if (getIntent() !=null){
                Articles getArticles = getIntent().getParcelableExtra("DetailArticles");

            try {
                Glide.with(this).load(getArticles.getImage()).thumbnail(Glide.with(this).load(R.drawable.spinner_200px)).into(dImage);
                dtitle.setText(getArticles.getTitle());
                ddesc.setText(getArticles.getDescription());
            }catch (Exception e){
                Log.e("0000",""+e.toString());
            }

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter = new MainPresenter(this);
        presenter.onLoadingData("",1,10);

        rvRelate.setLayoutManager(new LinearLayoutManager(this));
        rvRelate.setNestedScrollingEnabled(false);
        articlesAdapter = new ArticlesAdapter(articlesList,this);
        rvRelate.setAdapter(articlesAdapter);
    }

    @Override
    public void onRequestSuccess(ArticilesRespone articles) {
        articlesList.addAll(articles.getData());
        articlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestComplete(String message) {

    }

    @Override
    public void onRequestError(String message) {

    }

    @Override
    public void onArticlesDetail(Articles getDetailArticles) {
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DetailArticles",getDetailArticles);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onArticlesDelete(int position, Articles articles) {

    }

    @Override
    public void onArticlesUpdate(Articles getUpdateArticles) {

    }
}

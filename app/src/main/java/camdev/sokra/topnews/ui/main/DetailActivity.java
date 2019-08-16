package camdev.sokra.topnews.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import camdev.sokra.topnews.ui.main.mvp.MainMVP;
import camdev.sokra.topnews.ui.main.mvp.MainPresenter;

public class DetailActivity extends AppCompatActivity implements MainMVP.View,ArticlesAdapter.OnArticleCRUD{
    private TextView dtitle,ddesc;
    private ImageView dImage;
    private ImageView btnBack;
    private RecyclerView rvRelate;
    private List<Articles> articles = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnBack = findViewById(R.id.btnBack);
        dtitle = findViewById(R.id.dartTitle);
        ddesc = findViewById(R.id.dartDetail);
        dImage = findViewById(R.id.dartImage);

        if (getIntent() !=null){
                Articles getArticles = getIntent().getParcelableExtra("DetailArticles");
                Glide.with(this).load(getArticles.getImage()).thumbnail(Glide.with(this).load(R.drawable.spinner_200px)).into(dImage);
                dtitle.setText(getArticles.getTitle());
                ddesc.setText(getArticles.getDescription());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reLate();
        presenter = new MainPresenter(this);
        presenter.onLoadingData("",1,6);
    }

    private void reLate(){
        rvRelate = findViewById(R.id.rvRelate);
        rvRelate.setLayoutManager(new GridLayoutManager(this,2));
        articlesAdapter = new ArticlesAdapter(articles,this);
        rvRelate.setAdapter(articlesAdapter);
    }

    @Override
    public void onRequestSuccess(ArticilesRespone articles) {
        articlesAdapter.addMoreItem(articles.getData());
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

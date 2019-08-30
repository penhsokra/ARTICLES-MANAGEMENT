package camdev.sokra.topnews.ui.scrap.kohsantepheap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.paginate.PaginationScrollListener;
import camdev.sokra.topnews.scrap.adapter.ArticlesAdapter;
import camdev.sokra.topnews.scrap.authservice.BlogServiceGenerator;
import camdev.sokra.topnews.scrap.model.ScrapArticles;
import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;
import camdev.sokra.topnews.scrap.service.BlogService;
import camdev.sokra.topnews.ui.scrap.kohsantepheap.mvp.MainMVP;
import camdev.sokra.topnews.ui.scrap.kohsantepheap.mvp.MainPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainScrapActivity extends AppCompatActivity implements ArticlesAdapter.OnCallback, MainMVP.View {

    private List<ScrapArticles> scrapArticlesList = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;
    private RecyclerView rvSabay;
    BlogService blogService;
    private ProgressBar progressBarScrap;
    MainMVP.Presenter presenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scrap);
        rvSabay = findViewById(R.id.rvSabay);
        progressBarScrap = findViewById(R.id.progressBarScrap);
        progressBarScrap.setVisibility(View.VISIBLE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSabay.setLayoutManager(linearLayoutManager);
        articlesAdapter = new ArticlesAdapter(scrapArticlesList,this);
        //rvSabay.setLayoutManager(new LinearLayoutManager(this));
        //articlesAdapter = new ArticlesAdapter(scrapArticlesList,this);
        rvSabay.setAdapter(articlesAdapter);

        presenter = new MainPresenter(this);
        blogService = BlogServiceGenerator.createBlogService();

        rvSabay.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                progressBarScrap.setVisibility(View.VISIBLE);
                //imgLoadingPage.setVisibility(View.VISIBLE);
               // get_Page.setVisibility(View.GONE);

                if (!isLastPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onLoadingData(page);
                        }
                    }, 100);
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        presenter.onLoadingData(10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void OnArticleClick(int position, ScrapArticles scrapArticles) {
        Intent intent = new Intent(this, ScrapDetailActivity.class);
        String url = scrapArticles.getLinkURL();
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public void onRequestSuccess(ScrapArticlseRespone articles) {
        progressBarScrap.setVisibility(View.VISIBLE);
        articlesAdapter.addMoreItem(articles.getGetArticles());
        progressBarScrap.setVisibility(View.GONE);

        isLoading = false;
        if (articles != null) {
            if (page == 200) {
                isLastPage = true;
                Toast.makeText(this, "All of ScrapArticles", Toast.LENGTH_SHORT).show();
            } else {
                page++;
            }
        }
    }

    @Override
    public void onRequestComplete(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        progressBarScrap.setVisibility(View.GONE);
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        progressBarScrap.setVisibility(View.GONE);
    }
}
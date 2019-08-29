package camdev.sokra.topnews.ui.scrap.sabay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.scrap.adapter.SabayAdapter;
import camdev.sokra.topnews.scrap.authservice.BlogServiceGenerator;
import camdev.sokra.topnews.scrap.model.Articles;
import camdev.sokra.topnews.scrap.model.ArticlseRespone;
import camdev.sokra.topnews.scrap.service.BlogService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainMenuActivity extends AppCompatActivity implements SabayAdapter.OnCallback{

    private List<Articles> sabayArticlesList = new ArrayList<>();
    private SabayAdapter sabayAdapter;
    private RecyclerView rvSabay;
    BlogService blogService;

    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        rvSabay = findViewById(R.id.rvSabay);

        rvSabay.setLayoutManager(new LinearLayoutManager(this));
        sabayAdapter = new SabayAdapter(sabayArticlesList,this);
        rvSabay.setAdapter(sabayAdapter);

        blogService = BlogServiceGenerator.createBlogService();
        disposable.add(
                 blogService.getSabayArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ArticlseRespone>() {
                    @Override
                    public void onNext(ArticlseRespone articlseRespone) {
                        sabayAdapter.addMoreItem(articlseRespone.getGetSabayArticles());
                       // Log.e("00000",""+ articlseRespone.getGetSabayArticles());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("00000",""+t.toString());
                    }
                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void OnArticleClick(int position, Articles sabayArticles) {
        Intent intent = new Intent(this,SabayDetailActivity.class);
        String url = sabayArticles.getLinkURL();
        http://news.sabay.com.kh/article/1160842#utm_campaign=onpage
        intent.putExtra("url",url);
        startActivity(intent);
        Log.e("SABAY_URL",""+sabayArticles.getLinkURL().toString());
        Log.e("00000000",""+sabayArticles.getDetail());
    }
}
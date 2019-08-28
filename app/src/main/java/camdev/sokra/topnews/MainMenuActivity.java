package camdev.sokra.topnews;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import camdev.sokra.topnews.auth.BlogServiceGenerator;
import camdev.sokra.topnews.scrap.BlogAdapter;
import camdev.sokra.topnews.scrap.BlogPage;
import camdev.sokra.topnews.scrap.Post;
import camdev.sokra.topnews.scrap.SabayArticlseRespone;
import camdev.sokra.topnews.service.BlogService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainMenuActivity extends AppCompatActivity {
    RecyclerView wordList;
    FloatingActionButton fab;
    HashMap<String, Integer> occurrences = new HashMap<>();
    BlogAdapter blogAdapter;
    TextView getTextss;
    BlogService blogService;
    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        blogService = BlogServiceGenerator.createBlogService();
        /*disposable.add(blogService.getBlogPage(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSubscriber<BlogPage>() {
            @Override
            public void onNext(BlogPage blogPage) {
                Log.e("00000",""+blogPage.posts);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("00000",""+t.toString());
            }

            @Override
            public void onComplete() {

            }
        }));*/

        disposable.add(blogService.getSabayArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSubscriber<SabayArticlseRespone>() {
                @Override
                public void onNext(SabayArticlseRespone sabayArticlseRespone) {
                    String html = sabayArticlseRespone.getSabayArticles.toString();
                    Document document = Jsoup.parse(html);
                    Log.e("00000",""+document.text());
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("00000",""+t.toString());
                }

                @Override
                public void onComplete() {

                }
            }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
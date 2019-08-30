package camdev.sokra.topnews.ui.scrap.kohsantepheap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.scrap.authservice.BlogServiceGenerator;
import camdev.sokra.topnews.scrap.model.detail.Detail;
import camdev.sokra.topnews.scrap.model.detail.DetailRespone;
import camdev.sokra.topnews.scrap.service.BlogService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class ScrapDetailActivity extends AppCompatActivity {
    private BlogService blogService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private TextView contentText,dTitle;
    private String url;
    private ProgressBar progressBarSdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_detail);
        contentText = findViewById(R.id.detailText);
        dTitle = findViewById(R.id.sdTitle);
        progressBarSdetail = findViewById(R.id.progressBarSdetail);

        if (getIntent() !=null){
            url=""+getIntent().getExtras().getString("url");
            Log.e("xxxxxxxxxxx",""+url.toString());
        }

        blogService = BlogServiceGenerator.createBlogService();
        disposable.add(
                blogService.getDetailArticles("915129.html")
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<DetailRespone>() {
                    @Override
                    public void onNext(DetailRespone detailRespone) {
                        Detail detail = detailRespone.getDetail();
                        Log.e("d0000","" + detail.getContentDetail());
                        dTitle.setText(detail.getDetailTitle());
                        contentText.setText(detail.getContentDetail());
                    }

                    @Override
                    public void onError(Throwable t) {
                        progressBarSdetail.setVisibility(View.GONE);
                        Log.e("d0000",""+t.toString());

                    }

                    @Override
                    public void onComplete() {
                        progressBarSdetail.setVisibility(View.GONE);
                    }
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

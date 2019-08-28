package camdev.sokra.topnews.ui.main;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.adapter.ArticlesAdapter;
import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.login.LoginActivity;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;
import camdev.sokra.topnews.model.crud.ArticlesCRUDRespone;
import camdev.sokra.topnews.service.ArticlesService;
import camdev.sokra.topnews.ui.insert.AddActivity;
import camdev.sokra.topnews.ui.main.mvp.MainMVP;
import camdev.sokra.topnews.ui.main.mvp.MainPresenter;
import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.paginate.PaginationScrollListener;
import camdev.sokra.topnews.ui.update.UpdateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArticlesAdapter.OnArticleCRUD, MainMVP.View{
    private RecyclerView rvArticlse;
    private List<Articles> articlesList = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;
    private ProgressBar lgProgressBar;
    private MainMVP.Presenter presenter;
    private Handler handler;
    private TextView total_Page,toTal_Count,get_Page;
    private ImageView imgLoadingPage;
    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private ArticlesService articlesService;
    public static  final int ADD_REQUEST_CODE=111;
    public static final int EDIT_REQUEST_CODE=222;
    public static final int DETAIL_REQUEST_CODE=0000;
    CarouselView carouselView;
    private List<String> mImages = new ArrayList<>();
    TextView customCarouselLabel;
    LinearLayoutManager layoutManager;
    private ImageView loadingImg;
    private ConstraintLayout lyLoading;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.

        Toolbar collapsingToolbar = findViewById(R.id.toolbar);
        collapsingToolbar.setTitle("TOP NEWS");

        floatingActionButton = findViewById(R.id.floatingActionButton);
        lyLoading = findViewById(R.id.lyLoading);
        loadingImg = findViewById(R.id.loadingImg);
        Glide.with(this).load(R.drawable.loading).thumbnail(Glide.with(this).load(R.drawable.loading)).into(loadingImg);
        final Handler myH = new Handler();
        myH.postDelayed(new Runnable() {
            @Override
            public void run() {
                lyLoading.setVisibility(View.GONE);
                setTheme(R.style.AppTheme);
            }
        }, 5000);

        //customCarouselLabel =findViewById(R.id.customCarouselLabel);

        presenter = new MainPresenter(this);
        articlesService = ServiceGenerator.createService(ArticlesService.class);
        initUI();

        /* slide */
        mImages = Arrays.asList(new String[]{"" +
                "https://i2.wp.com/www.feedough.com/wp-content/uploads/2018/04/ADVERTISING-07.png",
                "https://nullsgpl.b-cdn.net/wp-content/uploads/2018/01/Unlimited-Addons-for-WPBakery-Page-Builder-Free.png",
                "http://vamarf.net.br/wp-content/uploads/2018/07/CYBER02.png",
                "http://cash4ads.com/wp-content/uploads/2016/09/advertising.jpg",

        });
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(mImages.size());
        carouselView.setImageListener(imageListener);
        /* //slide */

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i,ADD_REQUEST_CODE);
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            //imageView.set(sampleImages[position]);
            Glide.with(getApplicationContext())
                    .load(mImages.get(position))
                    .thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_002))
                    .into(imageView);
        }
    };

    private void initUI(){
        total_Page = findViewById(R.id.getTotalPage);
        toTal_Count = findViewById(R.id.getTotalCount);
        imgLoadingPage = findViewById(R.id.imgLoadingPage);
        get_Page = findViewById(R.id.getPage);
        Glide.with(this).load(R.drawable.loading_elip).thumbnail(Glide.with(this).load(R.drawable.loading_elip)).into(imgLoadingPage);
        rvArticlse = findViewById(R.id.rvArticles);
        //rvArticlse.setNestedScrollingEnabled(false);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvArticlse.setLayoutManager(linearLayoutManager);
        articlesAdapter = new ArticlesAdapter(articlesList,this);

        rvArticlse.setAdapter(articlesAdapter);

        rvArticlse.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                imgLoadingPage.setVisibility(View.VISIBLE);
                get_Page.setVisibility(View.GONE);

                if (!isLastPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onLoadingData("",page,10);
                            get_Page.setText(""+page);
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
        presenter.onLoadingData("",page,10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onRequestSuccess(ArticilesRespone articles) {
        articlesAdapter.addMoreItem(articles.getData());
        toTal_Count.setText(""+articles.getPagination().getTotalCount());
        total_Page.setText(""+articles.getPagination().getTotalPages());
        get_Page.setVisibility(View.VISIBLE);
        imgLoadingPage.setVisibility(View.GONE);

        isLoading = false;
        if (articles != null) {
            if (articles.getPagination().getPage() == articles.getPagination().getTotalPages()) {
                isLastPage = true;
                Toast.makeText(this, "All of Articles", Toast.LENGTH_SHORT).show();
            } else {
                page = articles.getPagination().getPage()+1;
                Log.e("0000",""+page);
            }
        }

    }

    @Override
    public void onRequestComplete(String message) {

    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticlesDetail(Articles getDetailArticles) {
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DetailArticles",getDetailArticles);
        intent.putExtras(bundle);
        startActivityForResult(intent,DETAIL_REQUEST_CODE);
    }

    @Override
    public void onArticlesDelete(final int position, final Articles articles) {
        SharedPreferences shf = getSharedPreferences("Login_SharedPref", MODE_PRIVATE);
        String strPref = shf.getString("LOGIN", null);
        if(strPref == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else {
            articlesService.delete(articles.getId()).enqueue(new Callback<ArticlesCRUDRespone>() {
                @Override
                public void onResponse(Call<ArticlesCRUDRespone> call, Response<ArticlesCRUDRespone> response) {
                    Toast.makeText(getApplicationContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                    Log.e("0000", "Deleted");
                    articlesList.remove(position);
                    articlesAdapter.notifyItemRemoved(position);
                    //articlesAdapter.notifyItemRangeChanged(position, articlesList.size());
                }
                @Override
                public void onFailure(Call<ArticlesCRUDRespone> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Delete Fail" + t.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("0000", "" + t.toString());
                }
            });
        }
    }

    @Override
    public void onArticlesUpdate(Articles getUpdateArticles) {
        SharedPreferences shf = getSharedPreferences("Login_SharedPref", MODE_PRIVATE);
        String strPref = shf.getString("LOGIN", null);
        if(strPref == null) {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else {
            ArticlesCRUD articlesCRUD = new ArticlesCRUD(getUpdateArticles.getId(),
                    getUpdateArticles.getAuthor().getId(),
                    getUpdateArticles.getTitle(),
                    getUpdateArticles.getDescription(),
                    getUpdateArticles.getImage());
            //Log.e("1111",""+getUpdateArticles.getAuthor().getId());
            Intent u = new Intent(this, UpdateActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("articlesCRUD", articlesCRUD);
            u.putExtras(bundle);
            startActivityForResult(u, EDIT_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //get new Inbox from add and edit activity
        if (EDIT_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            final ArticlesCRUD articlesCRUD = data.getParcelableExtra("data");
            articlesService.update(articlesCRUD.getId(),articlesCRUD).enqueue(new Callback<ArticlesCRUDRespone>() {
                @Override
                public void onResponse(Call<ArticlesCRUDRespone> call, Response<ArticlesCRUDRespone> response) {
                    articlesAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArticlesCRUDRespone> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Fail Update"+t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }else if(ADD_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            final ArticlesCRUD articlesData = data.getParcelableExtra("data");
            final Articles newArticles = new Articles();
            newArticles.setImage(articlesData.getImage());
            newArticles.setTitle(articlesData.getTitle());
            articlesService.create(articlesData).enqueue(new Callback<ArticlesCRUDRespone>() {
                @Override
                public void onResponse(Call<ArticlesCRUDRespone> call, Response<ArticlesCRUDRespone> response) {
                    //Log.e("0000",""+articlesData.getAuthor().getId());
                    articlesList.add(newArticles);
                    //articlesAdapter.notifyItemInserted(0);
                    articlesAdapter.notifyDataSetChanged();
                    rvArticlse.smoothScrollToPosition(0);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArticlesCRUDRespone> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    Log.e("0000",""+t.toString());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        /*SharedPreferences shf = getSharedPreferences("Login_SharedPref", MODE_PRIVATE);
        String strPref = shf.getString("LOGIN", null);
        if(strPref == null) {
            menuInflater.inflate(R.menu.main_option_menu, menu);
            return true;
            }else {
                menuInflater.inflate(R.menu.main_option_menu_login, menu);
            return true;
        }*/
        menuInflater.inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences shf = getSharedPreferences("Login_SharedPref", MODE_PRIVATE);
        String strPref = shf.getString("LOGIN", null);
        if(strPref == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else{
            switch (item.getItemId()){
                case R.id.goToAdd:
                    Intent i = new Intent(this, AddActivity.class);
                    startActivityForResult(i,ADD_REQUEST_CODE);
                    return true;
                case R.id.logOut:
                    String filePath = getApplicationContext().getFilesDir().getParent()+"/shared_prefs/Login_SharedPref.xml";
                    File deletePrefFile = new File(filePath);
                    deletePrefFile.delete();
                    return true;
                default:return false;
            }
        }
        return false;
    }
}

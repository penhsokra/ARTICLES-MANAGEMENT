package camdev.sokra.topnews.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.adapter.ArticlesAdapter;
import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        articlesService = ServiceGenerator.createService(ArticlesService.class);
        initUI();

    }

    private void initUI(){
        total_Page = findViewById(R.id.getTotalPage);
        toTal_Count = findViewById(R.id.getTotalCount);
        imgLoadingPage = findViewById(R.id.imgLoadingPage);
        get_Page = findViewById(R.id.getPage);
        Glide.with(this).load(R.drawable.loading_elip).thumbnail(Glide.with(this).load(R.drawable.loading_elip)).into(imgLoadingPage);
        rvArticlse = findViewById(R.id.rvArticles);

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
                            presenter.onLoadingData("",page,5);
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
        presenter.onLoadingData("",page,5);
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
        startActivity(intent);
    }

    @Override
    public void onArticlesDelete(final int position, final Articles articles) {
            articlesService.delete(articles.getId()).enqueue(new Callback<ArticlesCRUD>() {
            @Override
            public void onResponse(Call<ArticlesCRUD> call, Response<ArticlesCRUD> response) {
                Toast.makeText(getApplicationContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                Log.e("0000","Deleted");
                articlesList.remove(position);
                articlesAdapter.notifyItemRemoved(position);
                articlesAdapter.notifyItemRangeChanged(position, articlesList.size());
            }

            @Override
            public void onFailure(Call<ArticlesCRUD> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Delete Fail"+t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("0000",""+t.toString());
            }
        });

    }

    @Override
    public void onArticlesUpdate(Articles getUpdateArticles) {
        Intent u = new Intent(this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("getUpdateArticles",getUpdateArticles);
        u.putExtras(bundle);
        startActivity(u);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //get new Inbox from add and edit activity
        if (EDIT_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            /*Film film = data.getParcelableExtra("data");
            this.filmList.set(this.itemPosition, film);
            filmAdapter.notifyItemChanged(itemPosition);*/

        }else if(ADD_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            final Articles articlesData = data.getParcelableExtra("data");
            articlesService.create(articlesData).enqueue(new Callback<ArticlesCRUD>() {
                @Override
                public void onResponse(Call<ArticlesCRUD> call, Response<ArticlesCRUD> response) {
                    Log.e("0000",""+articlesData.getAuthor().getId());
                    articlesList.add(articlesData);
                    articlesAdapter.notifyDataSetChanged();
                    rvArticlse.smoothScrollToPosition(0);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArticlesCRUD> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    Log.e("0000",""+t.toString());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.goToAdd:
                Intent i = new Intent(this, AddActivity.class);
                startActivityForResult(i,ADD_REQUEST_CODE);
                return true;
             default:return false;
        }

    }
}

package camdev.sokra.topnews.service;

import camdev.sokra.topnews.scrap.BlogPage;
import camdev.sokra.topnews.scrap.SabayArticlseRespone;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BlogService {
    @GET("blog")
    Flowable<BlogPage> getBlogPage(@Query("page") int page);

    @GET("topics/technology")
    Flowable<SabayArticlseRespone> getSabayArticles();

    @GET(".")
    Call<String> getStringResponse();
}

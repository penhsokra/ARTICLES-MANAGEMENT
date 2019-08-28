package camdev.sokra.topnews.service;

import camdev.sokra.topnews.scrap.BlogPage;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BlogService {
    @GET("blog")
    Single<BlogPage> getBlogPage(@Query("page") int page);

    @GET(".")
    Call<String> getStringResponse();
}

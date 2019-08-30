package camdev.sokra.topnews.scrap.service;

import camdev.sokra.topnews.scrap.model.ScrapArticlseRespone;
import camdev.sokra.topnews.scrap.model.detail.DetailRespone;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlogService {
    //@GET("blog")
   // Flowable<BlogPage> getBlogPage(@Query("page") int page);

    @GET("category/security/page/{pageID}")
    Flowable<ScrapArticlseRespone> getArticles(@Path("pageID") int pageID);

    @GET("article/918188.html")
    Flowable<DetailRespone> getDetailArticles(@Query("link") String pageURl);

}

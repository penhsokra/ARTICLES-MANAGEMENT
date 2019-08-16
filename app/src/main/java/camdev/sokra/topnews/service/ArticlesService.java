package camdev.sokra.topnews.service;

import camdev.sokra.topnews.model.ArticilesRespone;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.UploadImageRespone;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;
import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticlesService {

   /* @GET("v2/leagues/")
    Call<DataRespone> getLeague();
    */

    @GET("/v1/api/articles")
    Flowable<ArticilesRespone> getArticles(@Query("title") String title,@Query("page") int page,@Query("limit") int limit);

    @POST("/v1/api/articles")
    Call<ArticlesCRUD> create(@Body Articles articles);

    @DELETE("/v1/api/articles/{id}")
    Call<ArticlesCRUD> delete(@Path("id") int id);

    @Multipart
    @POST("/v1/api/uploadfile/single")
    Call<UploadImageRespone> uploadImage(@Part MultipartBody.Part file);

}

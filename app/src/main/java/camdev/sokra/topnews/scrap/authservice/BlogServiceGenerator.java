package camdev.sokra.topnews.scrap.authservice;

import camdev.sokra.topnews.scrap.service.BlogService;
import pl.droidsonroids.retrofit2.JspoonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class BlogServiceGenerator {
    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://news.sabay.com.kh/")
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static BlogService createBlogService() {
        return createRetrofit()
                .create(BlogService.class);
    }
}

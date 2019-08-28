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
import camdev.sokra.topnews.service.BlogService;
import io.reactivex.Single;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


      /*  BlogService blogService = BlogServiceGenerator.createBlogService();
        blogService.error.getBlogPage(1).subscribe(MainMenuActivity::printBlogPage);

        Single.error(new IOException("Something went wrong"))
                .doOnError(error -> System.err.println("The error message is: " + error.getMessage()))
                .subscribe(MainMenuActivity::printBlogPage);ribe(
                        x -> MainMenuActivity::printBlogPage,
                        Throwable::printStackTrace,
                        () -> System.out.println("onComplete should never be printed!"));

        /*wordList = findViewById(R.id.wordList);
        fab = findViewById(R.id.fab);
        getTextss = findViewById(R.id.getTextss);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://news.sabay.com.kh/topics/technology/")
                .client(okHttpClient).build();
        final BlogService apiService = retrofit.create(BlogService.class);

        Call<String> stringCall = apiService.getStringResponse();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseString = response.body();
                    Document doc = Jsoup.parse(responseString);
                    responseString = doc.text();
                    createHashMap(responseString);
                    Log.e("0000",""+responseString);

                    getTextss.setText(responseString.toString());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("0000",""+t.toString());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occurrences = sortByValueDesc(occurrences);
                blogAdapter = new BlogAdapter(MainMenuActivity.this, occurrences);
                wordList.setAdapter(blogAdapter);
            }
        });*/
    }

    /*private void createHashMap(String responseString) {


        responseString = responseString.replaceAll("[^a-zA-Z0-9]", " ");

        String[] splitWords = responseString.split(" +");

        for (String word : splitWords) {

            if (StringUtil.isNumeric(word)) {
                continue;
            }

            Integer oldCount = occurrences.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put(word, oldCount + 1);
        }

        blogAdapter = new BlogAdapter(this, occurrences);
        wordList.setAdapter(blogAdapter);
    }

    public static HashMap<String, Integer> sortByValueDesc(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }*/


    private static void printBlogPage(BlogPage blogPage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            blogPage.posts.forEach(MainMenuActivity::printPost);
        }
    }

    private static void printPost(Post post) {
        System.out.println(post.title);
        System.out.println(post.excerpt);
        System.out.println(post.imageUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println(String.join(", ", post.tags));
        }
        System.out.println();
    }


}
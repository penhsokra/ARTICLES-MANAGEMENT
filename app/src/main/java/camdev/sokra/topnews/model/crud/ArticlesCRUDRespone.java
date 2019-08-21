package camdev.sokra.topnews.model.crud;

import com.google.gson.annotations.SerializedName;

import camdev.sokra.topnews.model.Articles;

public class ArticlesCRUDRespone {
    @SerializedName("DATA")
    private Articles articles;

    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }
}

package camdev.sokra.topnews.scrap.model;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ArticlseRespone {
    @Selector(".row.list-item.item") private List<Articles> getSabayArticles;

    public List<Articles> getGetSabayArticles() {
        return getSabayArticles;
    }

    public void setGetSabayArticles(List<Articles> getSabayArticles) {
        this.getSabayArticles = getSabayArticles;
    }

    @Override
    public String toString() {
        return "ArticlseRespone{" +
                "getSabayArticles=" + getSabayArticles +
                '}';
    }
}

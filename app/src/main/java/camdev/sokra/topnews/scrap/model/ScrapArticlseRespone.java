package camdev.sokra.topnews.scrap.model;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ScrapArticlseRespone {
    @Selector(".featrued-artical.container .col-md-3.col-sm-6") private List<ScrapArticles> getArticles;

    public List<ScrapArticles> getGetArticles() {
        return getArticles;
    }

    public void setGetArticles(List<ScrapArticles> getArticles) {
        this.getArticles = getArticles;
    }

    @Override
    public String toString() {
        return "ScrapArticlseRespone{" +
                "getArticles=" + getArticles +
                '}';
    }
}

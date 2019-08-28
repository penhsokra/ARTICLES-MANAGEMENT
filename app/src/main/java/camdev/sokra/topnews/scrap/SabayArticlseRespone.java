package camdev.sokra.topnews.scrap;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class SabayArticlseRespone {
    @Selector(".row.list-item.item") public List<SabayArticles> getSabayArticles;

    @Override
    public String toString() {
        return "SabayArticlseRespone{" +
                "getSabayArticles=" + getSabayArticles +
                '}';
    }
}

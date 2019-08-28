package camdev.sokra.topnews.scrap;

import pl.droidsonroids.jspoon.annotation.Selector;

public class SabayArticles {

    @Selector(".content > .title > .web") public String title;
    @Selector(value = ".small .pub-date") public String public_date;

    @Override
    public String toString() {
        return "SabayArticles{" +
                "title='" + title + '\'' +
                ", public_date='" + public_date + '\'' +
                '}';
    }
}

package camdev.sokra.topnews.scrap;

import java.util.List;
import pl.droidsonroids.jspoon.annotation.Selector;

public class Post {
    @Selector(".post-content > h2 > a") public String title;
    @Selector(".excerpt") public String excerpt;
    @Selector(value = ".post-featured-image > a > img", attr = "data-lazy-src") public String imageUrl;
    @Selector(".post-category > a") public List<String> tags;

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + tags +
                '}';
    }
}

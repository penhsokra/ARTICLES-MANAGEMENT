package camdev.sokra.topnews.scrap;

import java.util.List;
import pl.droidsonroids.jspoon.annotation.Selector;

public class BlogPage {
    @Selector(".post") public List<Post> posts;

    @Override
    public String toString() {
        return "BlogPage{" +
                "posts=" + posts +
                '}';
    }
}

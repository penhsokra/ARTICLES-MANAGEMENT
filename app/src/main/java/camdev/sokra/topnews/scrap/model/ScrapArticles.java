package camdev.sokra.topnews.scrap.model;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ScrapArticles {

    @Selector(".col-md-3.col-sm-6 > article > p > a") private String title;
    @Selector(".col-md-3.col-sm-6 .time") private String public_date;
    @Selector(value = ".col-md-3.col-sm-6 > article > .image-box > a > img", attr="data-original") private String imageURL;
    @Selector(value = ".col-md-3.col-sm-6 > article > .image-box > a", attr="href") private String linkURL;

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublic_date() {
        return public_date;
    }

    public void setPublic_date(String public_date) {
        this.public_date = public_date;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "ScrapArticles{" +
                "title='" + title + '\'' +
                ", public_date='" + public_date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", linkURL='" + linkURL + '\'' +
                '}';
    }
}

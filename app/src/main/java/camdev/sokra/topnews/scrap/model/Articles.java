package camdev.sokra.topnews.scrap.model;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Articles {

    @Selector(".row.list-item.item .content > .title > .web") private String title;
    @Selector(".title .small .pub-date") private String public_date;
    @Selector(".row.list-item.item .detail") private String detail;
    @Selector(value = ".row.list-item.item .ele.lozad", attr = "data-background-image") private String imageURL;
    @Selector(value = ".row.list-item.item > a", attr = "href") private String linkURL;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "title='" + title + '\'' +
                ", public_date='" + public_date + '\'' +
                ", detail='" + detail + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", linkURL='" + linkURL + '\'' +
                '}';
    }
}

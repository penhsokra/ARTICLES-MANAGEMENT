package camdev.sokra.topnews.scrap.model.detail;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Detail {

    @Selector(".col-lg-8.col-md-8 > h1") private String detailTitle;
    @Selector(".col-lg-8.col-md-8 .content-text p") private String detailDate;
    @Selector(".col-lg-8.col-md-8 .content-text") private String contentDetail;

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(String detailDate) {
        this.detailDate = detailDate;
    }

    public String getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }
}

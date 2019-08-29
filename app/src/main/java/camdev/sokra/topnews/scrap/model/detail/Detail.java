package camdev.sokra.topnews.scrap.model.detail;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Detail {

    @Selector(".header.post_content .title.detail> p") private String detailTitle;
    @Selector(".header.post_content .small #sdate") private String detailDate;
    @Selector(".detail.content-detail") private String contentDetail;

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

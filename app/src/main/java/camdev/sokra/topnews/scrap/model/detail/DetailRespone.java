package camdev.sokra.topnews.scrap.model.detail;

import pl.droidsonroids.jspoon.annotation.Selector;

public class DetailRespone {

    @Selector(".content-outline") private Detail detail;
    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "DetailRespone{" +
                "detail=" + detail +
                '}';
    }
}

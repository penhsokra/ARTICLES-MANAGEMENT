package camdev.sokra.topnews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticilesRespone {
    @SerializedName("PAGINATION")
    private Pagination pagination;
    @SerializedName("DATA")
    private List<Articles> data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private String code;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Articles> getData() {
        return data;
    }

    public void setData(List<Articles> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

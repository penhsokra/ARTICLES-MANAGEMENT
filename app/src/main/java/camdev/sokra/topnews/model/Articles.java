package camdev.sokra.topnews.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Articles implements Parcelable {
    @SerializedName("IMAGE")
    private String image;
    @SerializedName("CATEGORY")
    private Category category;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("AUTHOR")
    private Author author;
    @SerializedName("CREATED_DATE")
    private String createdDate;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("ID")
    private int id;
    public Articles(){}
    public Articles(String image, Category category, String status, Author author, String createdDate, String description, String title, int id) {
        this.image = image;
        this.category = category;
        this.status = status;
        this.author = author;
        this.createdDate = createdDate;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    protected Articles(Parcel in) {
        image = in.readString();
        status = in.readString();
        createdDate = in.readString();
        description = in.readString();
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel in) {
            return new Articles(in);
        }

        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScrapArticles{" +
                "image='" + image + '\'' +
                ", category=" + category +
                ", status='" + status + '\'' +
                ", author=" + author +
                ", createdDate='" + createdDate + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(status);
        dest.writeString(createdDate);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeInt(id);
    }
}

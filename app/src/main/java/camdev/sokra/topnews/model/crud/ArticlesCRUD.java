package camdev.sokra.topnews.model.crud;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ArticlesCRUD implements Parcelable {

    @SerializedName("IMAGE")
    private String image;
    @SerializedName("AUTHOR")
    private int author;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("ID")
    private int id;

    public ArticlesCRUD(int id, int author,String title,String description,String image) {
        this.image = image;
        this.author = author;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    protected ArticlesCRUD(Parcel in) {
        image = in.readString();
        author = in.readInt();
        description = in.readString();
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<ArticlesCRUD> CREATOR = new Creator<ArticlesCRUD>() {
        @Override
        public ArticlesCRUD createFromParcel(Parcel in) {
            return new ArticlesCRUD(in);
        }

        @Override
        public ArticlesCRUD[] newArray(int size) {
            return new ArticlesCRUD[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeInt(author);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeInt(id);
    }
}

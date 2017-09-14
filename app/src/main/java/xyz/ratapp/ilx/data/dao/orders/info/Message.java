package xyz.ratapp.ilx.data.dao.orders.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 06/09/2017.
 */

public class Message implements Serializable {

    @SerializedName("author")
    private String author;
    @SerializedName("author_id")
    private String authorId;
    @SerializedName("message")
    private String message;
    @SerializedName("date_add")
    private String addDate;

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getMessage() {
        return message;
    }

    public String getAddDate() {
        return addDate;
    }
}

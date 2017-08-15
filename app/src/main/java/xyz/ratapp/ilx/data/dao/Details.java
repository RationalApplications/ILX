package xyz.ratapp.ilx.data.dao;

/**
 * Created by timtim on 15/08/2017.
 *
 * temp dao for rv in DetailsActivity
 */

public class Details {

    public enum Type {
        ADDRESS,
        TIME,
        PHONE,
        TEXT
    }

    private Type type;
    private String text;

    public Details(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}

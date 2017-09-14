package xyz.ratapp.ilx.data.dao.app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by timtim on 06/09/2017.
 */

public class NegativeButton implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("options")
    private List<Button> options;

    public String getName() {
        return name;
    }

    public List<Button> getOptions() {
        return options;
    }
}

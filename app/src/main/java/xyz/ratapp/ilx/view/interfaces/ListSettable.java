package xyz.ratapp.ilx.view.interfaces;

import java.util.List;

import xyz.ratapp.ilx.data.dao.Request;

/**
 * Created by timtim on 08/08/2017.
 */

public interface ListSettable<T> {

    void setData(List<T> requests);
}
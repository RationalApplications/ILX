package xyz.ratapp.ilx.ui.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by timtim on 06/09/2017.
 *
 */

public interface ErrorDisplayable {

    void showError(@StringRes int throwable);

    void showError(String throwable);

}

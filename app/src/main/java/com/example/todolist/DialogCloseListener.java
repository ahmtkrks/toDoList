package com.example.todolist;

import android.content.DialogInterface;

public interface DialogCloseListener {
    public default void handleDialogClose(DialogInterface dialog) {

    }
}

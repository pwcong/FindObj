package me.pwcong.findobj.ui.dialog;

import android.content.Context;
import android.widget.EditText;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by pwcong on 2016/7/20.
 */
public class SimpleMessageSendDialog extends MaterialDialog {

    EditText editText;

    public SimpleMessageSendDialog(Context context,int lines,int maxLines) {
        super(context);

        maxLines=maxLines>lines?maxLines:lines;


        editText=new EditText(context);
        editText.setLines(lines);
        editText.setMaxLines(maxLines);

        setContentView(editText);

    }

    public EditText getEditText() {
        return editText;
    }

}

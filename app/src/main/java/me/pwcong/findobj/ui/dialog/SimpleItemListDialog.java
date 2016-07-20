package me.pwcong.findobj.ui.dialog;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import me.pwcong.findobj.base.BaseObject;

/**
 * Created by pwcong on 2016/7/19.
 */
public class SimpleItemListDialog extends MaterialDialog{

    ListView listView;


    public SimpleItemListDialog(Context context, List<String> itemList) {
        super(context);

        ListAdapter adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,itemList);

        listView=new ListView(context);
        listView.setAdapter(adapter);

        setContentView(listView);

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        listView.setOnItemClickListener(listener);

    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}

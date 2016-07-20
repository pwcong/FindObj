package me.pwcong.findobj.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.pwcong.findobj.R;
import me.pwcong.findobj.bean.FindMessage;
import me.pwcong.findobj.listener.FindMessageItemListener;

/**
 * Created by pwcong on 2016/7/20.
 */
public class FindMessageItemAdapter extends RecyclerView.Adapter<FindMessageItemAdapter.ViewHolder>{

    List<FindMessage> findMessageList;
    FindMessageItemListener listener;

    public FindMessageItemAdapter(List<FindMessage> findMessageList, FindMessageItemListener listener) {
        this.findMessageList = findMessageList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_findmessage,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.findMessage=findMessageList.get(position);
        holder.tv_time.setText(findMessageList.get(position).getCreatedAt());
        holder.tv_title.setText(findMessageList.get(position).getTitle());
        holder.tv_reply.setText(findMessageList.get(position).getReply());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFindMessageItemInteraction(holder.findMessage);
            }
        });

    }

    @Override
    public int getItemCount() {
        return findMessageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View view;
        public final TextView tv_reply;
        public final TextView tv_title;
        public final TextView tv_time;
        public FindMessage findMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            this.tv_reply= (TextView) itemView.findViewById(R.id.tv_reply);
            this.tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_time= (TextView) itemView.findViewById(R.id.tv_time);



        }
    }


}

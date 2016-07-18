package me.pwcong.findobj.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.pwcong.findobj.R;
import me.pwcong.findobj.base.BaseObject;
import me.pwcong.findobj.bean.Lost;
import me.pwcong.findobj.ui.fragment.FindSquareFragment;

/**
 * Created by pwcong on 2016/7/17.
 */
public class FindSquareFragmentAdapter extends RecyclerView.Adapter<FindSquareFragmentAdapter.ViewHolder>{

    private final List<Lost> lostList;
    private final FindSquareFragment.FindSquareFragmentListener mListener;

    public FindSquareFragmentAdapter(List<Lost> lostList, FindSquareFragment.FindSquareFragmentListener mListener) {
        this.lostList = lostList;
        this.mListener = mListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_object,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.baseObject=lostList.get(position);
        holder.tv_title.setText(lostList.get(position).getTitle());
        holder.tv_describe.setText(lostList.get(position).getDescribe());
        holder.tv_phone.setText(lostList.get(position).getPhone());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFindSquareFragmentInteraction(holder.baseObject);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View view;
        public final TextView tv_title;
        public final TextView tv_describe;
        public final TextView tv_phone;
        public BaseObject baseObject;

        public ViewHolder(View view) {
            super(view);
            this.view=view;
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_describe = (TextView) view.findViewById(R.id.tv_describe);
            this.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        }
    }

}

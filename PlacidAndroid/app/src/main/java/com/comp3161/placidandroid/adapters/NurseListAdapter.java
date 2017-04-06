package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.models.Nurse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/5/17.
 */

public class NurseListAdapter extends RecyclerView.Adapter<NurseListAdapter.NurseListViewHolder> {


    public static class NurseListViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public NurseListViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.nurse_name);
        }
    }

    Context context;
    List<Nurse> nurses;

    public NurseListAdapter(Context context, List<Nurse> nurses)
    {
        this.context = context;
        this.nurses = new ArrayList<>(nurses);
    }

    @Override
    public NurseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nurse_item,
                parent,false);
        NurseListViewHolder nurseListViewHolder = new NurseListViewHolder(view);
        return nurseListViewHolder;
    }

    @Override
    public void onBindViewHolder(NurseListViewHolder holder, int position) {
        String name = nurses.get(position).getName();
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return nurses.size();
    }

    public void updateNurseList(List<Nurse> nurses)
    {
        this.nurses = new ArrayList<>(nurses);
        notifyDataSetChanged();
    }
}

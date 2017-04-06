package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.models.Intern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/5/17.
 */

public class InternListAdapter extends RecyclerView.Adapter<InternListAdapter.InternListViewHolder> {


    public static class InternListViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public InternListViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.intern_name);
        }
    }

    Context context;
    List<Intern> interns;

    public InternListAdapter(Context context, List<Intern> interns)
    {
        this.context = context;
        this.interns = new ArrayList<>(interns);
    }

    @Override
    public InternListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_intern_item,
                parent,false);
        InternListViewHolder internListViewHolder = new InternListViewHolder(view);
        return internListViewHolder;
    }

    @Override
    public void onBindViewHolder(InternListViewHolder holder, int position) {
        String name = interns.get(position).getName();
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return interns.size();
    }

    public void updateInternList(List<Intern> interns)
    {
        this.interns = new ArrayList<>(interns);
        notifyDataSetChanged();
    }
}

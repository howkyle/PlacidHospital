package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3161.placidandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/5/17.
 */

public class VitalSignListAdapter extends RecyclerView.Adapter<VitalSignListAdapter.VitalViewHolder>{

    public static class VitalViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        public VitalViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.vital_name);
        }
    }

    Context context;
    List<String> vitals;

    public VitalSignListAdapter(Context context, List<String> vitals)
    {
        this.context = context;
        this.vitals = new ArrayList<>(vitals);
    }

    @Override
    public VitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vital_item,
                parent,false);
        VitalViewHolder vitalViewHolder = new VitalViewHolder(view);
        return vitalViewHolder;
    }

    @Override
    public void onBindViewHolder(VitalViewHolder holder, int position) {
        String name = vitals.get(position);
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return vitals.size();
    }

    public void updateVitalList(List<String> vitals)
    {
        this.vitals = new ArrayList<>(vitals);
        notifyDataSetChanged();
    }
}

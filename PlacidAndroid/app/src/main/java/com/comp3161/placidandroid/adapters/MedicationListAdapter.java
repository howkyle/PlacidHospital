package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.models.Medication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/4/17.
 */

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationListViewHolder>{

    public static class MedicationListViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public MedicationListViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.med_name);
        }
    }

    Context context;
    List<Medication> medications;

    public MedicationListAdapter(Context context, List<Medication> medications)
    {
        this.context = context;
        this.medications = new ArrayList<>(medications);
    }

    @Override
    public MedicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medication_item,
                parent,false);
        MedicationListViewHolder medicationListViewHolder = new MedicationListViewHolder(view);
        return medicationListViewHolder;
    }

    @Override
    public void onBindViewHolder(MedicationListViewHolder holder, int position) {
        String name = medications.get(position).getName();
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public void updateMedicationList(List<Medication> medications)
    {
        this.medications = new ArrayList<>(medications);
        notifyDataSetChanged();
    }
}

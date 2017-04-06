package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.activities.ViewPatientActivity;
import com.comp3161.placidandroid.models.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/3/17.
 */

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {


    public static class PatientViewHolder extends RecyclerView.ViewHolder{

        ImageView patientImg;
        TextView patientName;
        Button loadMoreButton;

        public PatientViewHolder(View view) {
            super(view);
            patientImg = (ImageView) view.findViewById(R.id.patient_img);
            patientName = (TextView) view.findViewById(R.id.patient_name);
            loadMoreButton = (Button) view.findViewById(R.id.load_more);
        }
    }

    Context mContext;
    List<Patient> mPatients;
    boolean loadMore;
    private final int TITLE = 0;
    private final int LOAD_MORE = 1;
    private String mFilter = "";
    public PatientListAdapter(Context context, List<Patient> patients)
    {
        this.mContext = context;
        this.mPatients = new ArrayList<>(patients);
    }


    @Override
    public PatientViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view;

        if(viewType==TITLE) {
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_patient_item, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_row,parent,false);
        }

        final PatientViewHolder patientViewHolder = new PatientViewHolder(view);
        if(viewType==TITLE) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = patientViewHolder.getAdapterPosition();
                    Patient patient = mPatients.get(pos);
                    Intent intent = new Intent(view.getContext(), ViewPatientActivity.class);
                    intent.putExtra("id", patient.getId());
                    intent.putExtra("first_name", patient.getFirst_name());
                    intent.putExtra("last_name", patient.getLast_name());
                    intent.putExtra("dob", patient.getDob());
                    intent.putExtra("address", patient.getAddress());
                    intent.putExtra("tel_number", patient.getPhone_number());
                    view.getContext().startActivity(intent);
                }
            });
        }

        return patientViewHolder;
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        String name = mPatients.get(position).getFirst_name()+ " " + mPatients.get(position).getLast_name();

        holder.patientName.setText(name);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(name);

        char firstLetter = name.toUpperCase().charAt(0);

        TextDrawable drawable2 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(firstLetter + "", color2);

        holder.patientImg.setImageDrawable(drawable2);
    }

    @Override
    public int getItemCount() {
        if (loadMore) {
            return mPatients.size() + 1;
        } else {
            return mPatients.size();
        }
    }

    public void updatePatientList(List<Patient> patients)
    {
        this.mPatients = new ArrayList<>(patients);
        notifyDataSetChanged();
    }

    public void setHasLoadButton(boolean hasLoadButton) {
        this.loadMore = hasLoadButton;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount()) {
            return TITLE;
        } else {
            return LOAD_MORE;
        }
    }

    public void animateTo(List<Patient> models, String filter) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
        this.mFilter = filter;
    }

    private void applyAndAnimateRemovals(List<Patient> newModels) {
        for (int i = mPatients.size() - 1; i >= 0; i--) {
            final Patient model = mPatients.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Patient> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Patient model = newModels.get(i);
            if (!mPatients.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Patient> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Patient model = newModels.get(toPosition);
            final int fromPosition = mPatients.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Patient removeItem(int position) {
        final Patient model = mPatients.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int pos, Patient model) {
        mPatients.add(pos, model);
        notifyItemInserted(pos);
    }

    public void moveItem(int fromPos, int toPos) {
        final Patient model = mPatients.remove(fromPos);
        mPatients.add(toPos, model);
        notifyItemMoved(fromPos, toPos);
    }
}

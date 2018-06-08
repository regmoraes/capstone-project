package com.regmoraes.closer.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.closer.data.database.Reminder;
import com.regmoraes.closer.databinding.AdapterReminderItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RemindersItemAdapter extends RecyclerView.Adapter<RemindersItemAdapter.ViewHolder> {

    private List<Reminder> reminders = new ArrayList<>();
    private AdapterClickListener listener;

    public RemindersItemAdapter(AdapterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return reminders.get(position).getUid();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        AdapterReminderItemBinding binding =
                AdapterReminderItemBinding.inflate(inflater, parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reminders.get(position));
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    interface AdapterClickListener {
        void onReminderClicked(long reminderId);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AdapterReminderItemBinding binding;

        ViewHolder(AdapterReminderItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Reminder reminder){

            binding.setReminder(reminder);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onReminderClicked(getItemId());
        }
    }

    public void setData(List<Reminder> reminders){
        this.reminders = reminders;
        notifyDataSetChanged();
    }
}

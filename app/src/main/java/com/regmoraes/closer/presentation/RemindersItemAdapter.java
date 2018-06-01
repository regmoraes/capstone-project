package com.regmoraes.closer.presentation;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.closer.data.database.ReminderContract;
import com.regmoraes.closer.data.entity.Reminder;
import com.regmoraes.closer.data.entity.ReminderMapper;
import com.regmoraes.closer.databinding.AdapterReminderItemBinding;

public class RemindersItemAdapter extends RecyclerView.Adapter<RemindersItemAdapter.ViewHolder> {

    private Cursor mCursor = null;
    private AdapterClickListener listener;

    public RemindersItemAdapter(AdapterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ReminderContract.Query._ID);
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

        mCursor.moveToPosition(position);

        holder.bind(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
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

        public void bind(Cursor cursor){

            Reminder reminder = ReminderMapper.fromCursor(cursor);

            binding.setReminder(reminder);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onReminderClicked(getItemId());
        }
    }

    public void setData(Cursor cursor){
        this.mCursor = cursor;
        notifyDataSetChanged();
    }
}

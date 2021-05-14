package com.example.notesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mData;
    private LayoutInflater mInflater;
    private NoteClickListener mClickListener;

    NotesAdapter(Context context, List<Note> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Here I switch between the layout it should use depending on if mIsVertical is true or not. (I couldn't figure out how to get access to LayoutManager.orientation.)
        View view = mInflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {

        if(mData.get(position).getText().length() > 16){
            holder.noteDesc.setText(String.format("%s...", mData.get(position).getText().substring(0, 15)));
        }else{
            holder.noteDesc.setText(mData.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteDesc;

        ViewHolder(View itemView) {
            super(itemView);
            noteDesc = itemView.findViewById(R.id.note_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Note getItem(int id) {
        return mData.get(id);
    }


    // allows clicks events to be caught
    void setClickListener(NoteClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface NoteClickListener {
        void onItemClick(View view, int position);
    }
}

package com.example.notes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notes;
    private OnNoteClickListener onClickListener;

    public NotesAdapter() {
        notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnNoteClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        Note note = notes.get(i);
        //Title
        notesViewHolder.textViewTitle.setText(note.getTitle());
        //Description
        if (note.getDescription().isEmpty()) {
            notesViewHolder.textViewDesc.setVisibility(View.GONE);
        } else {
            notesViewHolder.textViewDesc.setText(note.getDescription());
        }
        //Date
        notesViewHolder.textViewDate.setText(Note.getDayAsString(note.getDate()));
        //Image
        int color;
        switch (note.getPriority()) {
            case 0:
                color = notesViewHolder.itemView.getResources().getColor(R.color.priorityLow);
                break;
            case 1:
                color = notesViewHolder.itemView.getResources().getColor(R.color.priorityMedium);
                break;
            case 2:
                color = notesViewHolder.itemView.getResources().getColor(R.color.priorityHigh);
                break;
            default:
                color = notesViewHolder.itemView.getResources().getColor(R.color.colorPrimary);
                break;
        }
        notesViewHolder.textViewTitle.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public interface OnNoteClickListener {
        void onNoteClick(int position);

        void onLongClick(int position);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView textViewTitle;
        @BindView(R.id.description)
        TextView textViewDesc;
        @BindView(R.id.date)
        TextView textViewDate;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (onClickListener != null) onClickListener.onNoteClick(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                if (onClickListener != null) onClickListener.onLongClick(getAdapterPosition());
                return true; //if return false - it calls onClick method too!
            });
        }
    }
}

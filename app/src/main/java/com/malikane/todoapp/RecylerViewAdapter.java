package com.malikane.todoapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malikane.todoapp.DataBase.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.NoteHolder> {
    private List<NoteEntity> notes=new ArrayList<>();
    OnItemClickListener listener;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        NoteEntity note=notes.get(position);
        holder.Name.setText(note.getName());
        holder.Category.setText(note.getCategory());
        holder.Description.setText(note.getDescription());
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
    void setNote(List<NoteEntity> note){
        this.notes=note;
        notifyDataSetChanged();
    }
    NoteEntity getNoteAt(int position){
        return notes.get(position);
    }
    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView Name;
        private TextView Description;
        private TextView Category;
        public NoteHolder(View itemView){
            super(itemView);
            Name=itemView.findViewById(R.id.note_name);
            Description=itemView.findViewById(R.id.Description);
            Category=itemView.findViewById(R.id.note_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                       listener.OnItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(NoteEntity note);
    }
    void SetOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}

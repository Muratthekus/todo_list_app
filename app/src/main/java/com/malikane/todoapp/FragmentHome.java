package com.malikane.todoapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.malikane.todoapp.DataBase.NoteEntity;
import com.malikane.todoapp.ViewModel.ViewModel;

import java.util.List;
import java.util.Objects;

public class FragmentHome extends Fragment {
    private ViewModel viewModel;
    RecyclerView recyclerView;
    RecylerViewAdapter adapter;
    static SharedPreferences data;
    static SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home,container,false);

        //For override onCreateOptionsMenu method inside of this fragment
        setHasOptionsMenu(true);
        recyclerView=v.findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //For can delete any notes from recylerview with a single swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
        }
        }).attachToRecyclerView(recyclerView);

        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        adapter=new RecylerViewAdapter();
        recyclerView.setAdapter(adapter);
        //Share infromation between fragments
        data= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication().getApplicationContext());
        editor = data.edit();
        adapter.SetOnItemClickListener(new RecylerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(NoteEntity note) {
                editor.putInt("ID",note.getId());
                editor.putString("NAME",note.getName());
                editor.putString("CATEGORY",note.getCategory());
                editor.putString("DESCRIPTION",note.getDescription());
                editor.commit();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentEdit()).commit();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                adapter.setNote(noteEntities);
            }
        });
    }
    //Delete All Note
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //For add icon to actionbar
        inflater.inflate(R.menu.delete_all_notes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                viewModel.deleteAllNotes();
                Toast.makeText(getActivity(),"All notes deleted",Toast.LENGTH_SHORT).show();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
    //**************

}

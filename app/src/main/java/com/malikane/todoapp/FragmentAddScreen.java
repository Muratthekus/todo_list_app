package com.malikane.todoapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.malikane.todoapp.DataBase.NoteEntity;
import com.malikane.todoapp.ViewModel.ViewModel;

public class FragmentAddScreen extends Fragment {
    private EditText name;
    private ViewModel viewModel;
    private EditText category;
    private EditText description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addscreen, container, false);
        //For override onCreateOptionsMenu method inside of this fragment
        setHasOptionsMenu(true);
        name = v.findViewById(R.id.edit_text_name);
        category = v.findViewById(R.id.edit_text_category);
        description = v.findViewById(R.id.edit_text_description);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        //Set indicator
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_save_black_24dp);
    }
    private void saveNote() {
        if (name.getText().toString().trim().isEmpty() || description.getText().toString().trim().isEmpty() ||
                description.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
        } else {
            NoteEntity note = new NoteEntity(name.getText().toString(), category.getText().toString(), description.getText().toString());
            viewModel.insert(note);
            Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //For add icon to actionbar
        inflater.inflate(R.menu.addnote_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
                default:return super.onOptionsItemSelected(item);
        }
    }
}

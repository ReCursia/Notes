package com.example.notes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.notes.adapter.NotesAdapter;
import com.example.notes.model.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleViewNotes)
    RecyclerView recyclerViewNotes;
    private MainViewModel notes;
    private NotesAdapter adapter;

    @OnClick(R.id.floatingActionButton)
    public void onFabClicked() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        notes = ViewModelProviders.of(this).get(MainViewModel.class);
        setOnLiveDataChangedListener();
        initRecyclerView();
    }

    private void setOnLiveDataChangedListener() {
        notes.getNotes().observe(this, notes -> adapter.setNotes(notes));
    }

    private void initRecyclerView() {
        adapter = new NotesAdapter();
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                //do nothing
            }

            @Override
            public void onLongClick(int position) {
                //do nothing
            }
        });
        attachItemTouchHelper();
        recyclerViewNotes.setAdapter(adapter);
    }

    private void attachItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeElem(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void removeElem(int position) {
        notes.deleteNote(adapter.getNotes().get(position));
    }
}

package com.example.notes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.notes.model.MainViewModel;
import com.example.notes.model.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNoteActivity extends AppCompatActivity {

    @BindView(R.id.editTextTitle)
    EditText editTextTitle;
    @BindView(R.id.editTextDesc)
    EditText editTextDesc;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.spinner)
    Spinner spinner;

    @OnClick(R.id.addButton)
    public void onAddButtonClicked() {
        if (isFilled()) {
            addNewNote();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void addNewNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        int date = spinner.getSelectedItemPosition();
        int priority = getPriority();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.insertNote(new Note(title, description, date, priority));
    }

    private boolean isFilled() {
        boolean isCorrect = true;
        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        if (title.isEmpty()) {
            editTextTitle.setError(getString(R.string.invalid_title));
            isCorrect = false;
        }
        if (description.isEmpty()) {
            editTextDesc.setError(getString(R.string.invalid_description));
            isCorrect = false;
        }
        return isCorrect;
    }

    private int getPriority() {
        return radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
    }
}

package com.plaps.androidcleancode.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.plaps.androidcleancode.BaseApp;
import com.plaps.androidcleancode.R;
import com.plaps.androidcleancode.database.NotesRepository;
import com.plaps.androidcleancode.database.data.Notes;
import com.plaps.androidcleancode.models.CityListData;
import com.plaps.androidcleancode.models.CityListResponse;
import com.plaps.androidcleancode.networking.Service;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {

    private RecyclerView list;
    @Inject
    public  Service service;

    @Inject
    public NotesRepository notesRepository;

    ProgressBar progressBar;
    AlertDialog alertDialog;
    FloatingActionButton addNewNoteActionButton;
    TextView emptyListMessageTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        init();

        HomePresenter presenter = new HomePresenter(service, this);
        presenter.getCityList();
    }

    public  void renderView(){
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        addNewNoteActionButton = findViewById(R.id.addNoteActionButton);
        emptyListMessageTv = (TextView) findViewById(R.id.empty_list_tv);

    }

    public void init(){
        list.setLayoutManager(new LinearLayoutManager(this));


        notesRepository.findAll().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notesList) {
                if(notesList!= null && notesList.size() > 0) {
                    emptyListMessageTv.setVisibility(View.GONE);
                } else {
                    emptyListMessageTv.setVisibility(View.VISIBLE);
                }
                populateYourListWithNotesData(notesList);

            }
        });

        addNewNoteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrEditNote(null);
            }
        });

    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getCityListSuccess(CityListResponse cityListResponse) {




       populateYourListWithNotesData(notesRepository.getAllNotes());

//        list.setAdapter(adapter);

    }


    private void populateYourListWithNotesData(List<Notes> notesData) {

        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), notesData,
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Notes yourNote) {
//                       addOrEditNote(yourNote);
                    }
                }, new HomeAdapter.onDeleteNoteListener() {
            @Override
            public void onDeleteClick(Notes yourNote) {
                deleteNote(yourNote);
            }
        });

        list.setAdapter(adapter);
    }

    private void deleteNote(final Notes noteToDelete){
        new AlertDialog.Builder(this)
                .setTitle("Dump in trash!!!")
                .setMessage("Are you sure you wish to Nuke this entry?")
                .setPositiveButton("Nuke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("HomeActivity", "Sending atomic bombs to Jupiter");
                        notesRepository.delete(noteToDelete);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("HomeActivity", "Aborting mission...");
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void addOrEditNote(Notes notes) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_notes, null);
        dialogBuilder.setView(dialogView);

        final EditText notesHeading = (EditText) dialogView.findViewById(R.id.etv_add_note_heading);
        final EditText notesContent = (EditText) dialogView.findViewById(R.id.etv_add_note_content);
        Button closeBtn = (Button) dialogView.findViewById(R.id.btn_close);
        Button saveBtn = (Button) dialogView.findViewById(R.id.btn_save);


        if(notes != null) {
            notesHeading.setText(notes.getTitle());
            notesContent.setText(notes.getDescription());
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAddNotesDialog();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((TextUtils.isEmpty(notesHeading.getText().toString().trim())) ||
                        (TextUtils.isEmpty(notesContent.getText().toString().trim()))) {
                    Toast.makeText(HomeActivity.this, "Add a note or else simply click Skip button, Fellas!", Toast.LENGTH_SHORT).show();


                } else {
                    Notes notesObj = new Notes();
                    notesObj.setTitle(notesHeading.getText().toString().trim());
                    notesObj.setDescription(notesContent.getText().toString().trim());

                    notesRepository.insert(notesObj);
                    dismissAddNotesDialog();
                }


            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    private void dismissAddNotesDialog() {
        if(alertDialog != null || alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

}

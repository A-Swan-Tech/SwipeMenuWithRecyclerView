package com.ahtesham.swipemenuwithrecyclerview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerviewAdapter recyclerviewAdapter;
    private RecyclerTouchListener touchListener;
    public List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerviewAdapter = new RecyclerviewAdapter(this);

         taskList = new ArrayList<>();
        Task task = new Task("Java","Object oriented programing language");
        taskList.add(task);
        task = new Task("SQL","Programing language to manage database");
        taskList.add(task);
        task = new Task("C#","General Purpose programing language");
        taskList.add(task);
        task = new Task("Kotlin","Moderen programing language");
        taskList.add(task);
        task = new Task("Assembly","LowLevel programing language");
        taskList.add(task);
        task = new Task("Read book","Read android book completely");
        taskList.add(task);
        recyclerviewAdapter.setTaskList(taskList);
        recyclerView.setAdapter(recyclerviewAdapter);

        touchListener = new RecyclerTouchListener(this,recyclerView);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Toast.makeText(getApplicationContext(),taskList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID){
                            case R.id.delete_task:
                                taskList.remove(position);
                                recyclerviewAdapter.setTaskList(taskList);
                                break;
                            case R.id.edit_task:
                                Update(position);
                               // Toast.makeText(getApplicationContext(),"Edit Not Available",Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });
        recyclerView.addOnItemTouchListener(touchListener);
    }

    public void Update(int position){
        // get prompts.xml view

        // Create an alert builder
        AlertDialog dialog = null;
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this,R.style.AlertDialog);
        builder.setTitle(Html.fromHtml("<font color='#fff'>Choose One</font>"));

        // set the custom layout
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.prompts,
                        null);
        builder.setView(customLayout);
     //   LayoutInflater li = LayoutInflater.from(getApplicationContext());
      //  View promptsView = li.inflate(R.layout.prompts, null);

       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

        // set prompts.xml to alertdialog builder
        builder.setView(customLayout);
     //   alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) customLayout
                .findViewById(R.id.editTextDialogUserInput);

        final EditText userInput2 = (EditText) customLayout
                .findViewById(R.id.editTextDialogUserInput2);

        // set dialog message
        builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String user_input = userInput.getText().toString();
                                String user_input2 = userInput2.getText().toString();
                                if (TextUtils.isEmpty(user_input))
                                {
                                    userInput.setError("Enter Name!");
                                }
                                else
                                {
                                    Task task = new Task(user_input,user_input2);
                                   // taskList.add(task);
                                   // taskList.add(position,task);
                                    taskList.set(position,task);
                                    recyclerviewAdapter.setTaskList(taskList);
                                    dialog.cancel();
                                }

                                // get user input and set it to result
                                // edit text
                                //  result.setText(userInput.getText());
                                //     File from = new File(Environment.getExternalStorage().getAbsolutePath()+"/kaic1/imagem.jpg");
                                //   File to = new File(Environment.getExternalStorage().getAbsolutePath()+"/kaic2/imagem.jpg");
                                // from.renameTo(to);
                           /*     File file = new File(path+fName);
                                if (file.exists()){

                                    File fil = new File(path+userInput.getText()+".pdf");
                                    file.renameTo(fil);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "File rename Successfully!", Toast.LENGTH_SHORT).show();

                                }
                                */
                                // Toast.makeText(getContext(), "File name!"+userInput.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                              //  Refresh();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog =  builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        // show it
        alertDialog.show();

    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(touchListener);
    }
}
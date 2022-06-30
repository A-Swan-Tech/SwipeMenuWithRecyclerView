package com.ahtesham.swipemenuwithrecyclerview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    public List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        customAdapter = new CustomAdapter(this);

         taskList = new ArrayList<>();
        Task task = new Task("Java","Object oriented programing language");
        taskList.add(task);
        task = new Task("SQL","Programing language to manage database");
        taskList.add(task);
        task = new Task("C#","General Purpose programing language");
        taskList.add(task);
        task = new Task("Kotlin","Modern programing language");
        taskList.add(task);
        task = new Task("Assembly","LowLevel programing language");
        taskList.add(task);
        task = new Task("Read book","Read android book completely");
        taskList.add(task);
        customAdapter.setTaskList(taskList);

        /*// below line is to set layout manager for our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // setting layout manager for our recycler view.
        recyclerView.setLayoutManager(manager);*/

        recyclerView.setAdapter(customAdapter);


        /************************************/

/*
        // on below line we are creating a method to create item touch helper
        // method for adding swipe to delete functionality.
        // in this we are specifying drag direction and position to right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.



                RecyclerData deletedCourse = recyclerDataArrayList.get(viewHolder.getAdapterPosition());

                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                recyclerDataArrayList.remove(viewHolder.getAdapterPosition());

                // below line is to notify our item is removed from adapter.
                recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // below line is to display our snackbar with action.
                Snackbar.make(courseRV, deletedCourse.getTitle(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // adding on click listener to our action of snack bar.
                        // below line is to add our item to array list with a position.
                        recyclerDataArrayList.add(position, deletedCourse);

                        // below line is to notify item is
                        // added to our adapter class.
                        recyclerViewAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
            // at last we are adding this
            // to our recycler view.
        }
        ).attachToRecyclerView(courseRV);*/
    }
        /************************************/


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
                                    customAdapter.setTaskList(taskList);
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


}
package com.ahtesham.swipemenuwithrecyclerview.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ahtesham.swipemenuwithrecyclerview.R;
import com.ahtesham.swipemenuwithrecyclerview.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements Animation.AnimationListener {

    //Initialize Variables.....
    EditText editText;
    Button btn_add;
    RecyclerView recycler_view;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter mainAdapter;
    Animation animSlideup;
    LinearLayout layout;
    private MainTouchListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Assign Variables.....
        editText = findViewById(R.id.editText);
        btn_add = findViewById(R.id.btn_add);
        recycler_view = findViewById(R.id.recycler_view);

        //Initialize Database.....
        database = RoomDB.getInstance(this);

        //Store Database value in Data list.....
        dataList = database.mainDao().getAll();

        //Initialize linearLayoutManager.....
        linearLayoutManager = new LinearLayoutManager(this);

        //Set Layout Manager.....
        recycler_view.setLayoutManager(linearLayoutManager);

        //Initialize Adapter.....
        mainAdapter = new MainAdapter(Main2Activity.this,dataList);

        //Set Adapter.....
        recycler_view.setAdapter(mainAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from Edit text.....
                String mtext = editText.getText().toString().trim();

                //check condition.....
                if (!mtext.equals("")){
                    //When text is not empty.....
                    //Initialize MainData.....
                    MainData data = new MainData();

                    //Set text on Main Data.....
                    data.setText(mtext);

                    //Insert text in database.....
                    database.mainDao().insert(data);

                    //Clear edit text.....
                    editText.setText("");

                    //Notify when data is inserted.....
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });


     /*   touchListener = new MainTouchListener(this,recycler_view);
        touchListener
                .setSwipeable(R.id.rowFG, R.id.rowBG);
        recycler_view.addOnItemTouchListener(touchListener);*/

    }

    public void left(){
        layout = (LinearLayout) findViewById(R.id.rowFG);
        animSlideup = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.right_to_left);
        animSlideup.setAnimationListener(Main2Activity.this);

        View view = layout;
        view.postDelayed(new Runnable() {
            public void run() {
                view.setVisibility(View.VISIBLE);
                layout.startAnimation(animSlideup);
            }
        }, 500);
    }

   /* @Override
    public void onResume() {
        super.onResume();
        recycler_view.addOnItemTouchListener(touchListener);
    }*/

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
package com.ahtesham.swipemenuwithrecyclerview.roomdatabase;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahtesham.swipemenuwithrecyclerview.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    //Initialize variables.....
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
    Animation animSlideup;

    //Create Constructor.....
    public MainAdapter(Activity context, List<MainData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize View.....
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize Main Data.....
        MainData data = dataList.get(position);

        //Initialize Database.....
        database = RoomDB.getInstance(context);

        //setText on TextView.....
        holder.textView.setText(data.getText());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize MainData.....
                MainData d = dataList.get(holder.getAdapterPosition());

                //Get ID.....
                int sID = d.getID();

                //Get Text.....
                String sText = d.getText();

                //Create Dialog.....
                Dialog dialog = new Dialog(context);

                //Set Content View.....
                dialog.setContentView(R.layout.dialog_update);

                //Initialize Width.....
                int width = WindowManager.LayoutParams.MATCH_PARENT;

                //Initialize Height.....
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                //Set Layout.....
                dialog.getWindow().setLayout(width,height);

                //Show dialog.....
                dialog.show();

                //Initialize and Assign Variables.....
                EditText edit_text = dialog.findViewById(R.id.edit_text);
                Button btn_update = dialog.findViewById(R.id.btn_update);

                //Set Text on EditText.....
                edit_text.setText(sText);

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Dialog dismiss.....
                        dialog.dismiss();

                        //Get updated Text from EditText.....
                        String utext = edit_text.getText().toString().trim();

                        //Update Text in database.....
                        database.mainDao().update(sID,utext);

                        //Notify when data is updated.....
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize MainData.....
                MainData d = dataList.get(holder.getAdapterPosition());

                //Delete Text from database.....
                database.mainDao().delete(d);

                //Notify when data is deleted.....
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
        holder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animSlideup = AnimationUtils.loadAnimation(context.getApplicationContext(),
                        R.anim.left_to_right);
                /*animSlideup.setAnimationListener(R);*/
                animSlideup.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        holder.layout2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      //  holder.left.setVisibility(View.INVISIBLE);
                        holder.layout2.setVisibility(View.VISIBLE);
                        holder.layout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                View views = holder.layout2;
                views.postDelayed(new Runnable() {
                    public void run() {
                       // views.setVisibility(View.VISIBLE);
                        holder.layout.startAnimation(animSlideup);
                        holder.layout.setVisibility(View.INVISIBLE);
                    }
                }, 100);
            }
        });
        holder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animSlideup = AnimationUtils.loadAnimation(context.getApplicationContext(),
                        R.anim.right_to_left);
                /*animSlideup.setAnimationListener(R);*/
                animSlideup.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        holder.layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //  holder.left.setVisibility(View.INVISIBLE);
                        holder.layout.setVisibility(View.VISIBLE);
                        holder.layout2.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                View views = holder.layout2;
                views.postDelayed(new Runnable() {
                    public void run() {
                        // views.setVisibility(View.VISIBLE);
                        holder.layout2.startAnimation(animSlideup);
                        holder.layout.setVisibility(View.VISIBLE);
                       // holder.layout2.setVisibility(View.INVISIBLE);
                    }
                }, 100);

         /*       holder.layout.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.INVISIBLE);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize Variables.....
        TextView textView;
        ImageView btn_edit;
        ImageView btn_delete;
        ImageView left,right;

        LinearLayout layout,layout2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            left=itemView.findViewById(R.id.left);
            right=itemView.findViewById(R.id.right);
            layout = (LinearLayout) itemView.findViewById(R.id.rowFG);
            layout2 = (LinearLayout) itemView.findViewById(R.id.rowBG);
        }
    }
}

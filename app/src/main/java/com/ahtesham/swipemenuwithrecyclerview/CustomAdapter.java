package com.ahtesham.swipemenuwithrecyclerview;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahtesham.swipemenuwithrecyclerview.roomdatabase.MainData;
import com.ahtesham.swipemenuwithrecyclerview.roomdatabase.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    /**************-Initialize variables-********************/
    private List<Task> taskList;
    Animation animSlideup;
    private Context mContext;

   /**********-Create Constructor-***********/
    CustomAdapter(Context context){
        mContext = context;
        taskList = new ArrayList<>();
    }

    /*********-setData-****************/
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /**************************-Initialize View-*******************************/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /**************************-Initialize  Data-*******************************/
        Task data = taskList.get(position);


        /**************************-set Data on TextView-*******************************/
        holder.nametxtView.setText(data.getName());
        holder.desctxtView.setText(data.getDesc());
        /****************-edit button clicl-*******************/
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*********************- Initialize Data-***************************/
                Task d = taskList.get(holder.getAdapterPosition());


                /*********************- Get Data-***************************/
                String tName = d.getName();
                String tDesc = d.getDesc();

                /*********************- Create Dialog to update Data-***************************/
                Dialog dialog = new Dialog(mContext);


                dialog.setContentView(R.layout.prompts);

                /*********************- set height and width of dialog-***************************/
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);

              /************************- Initialize Views of dialog and get id-*************************/
                EditText name_text = dialog.findViewById(R.id.editTextDialogUserInput);
                EditText desc_text = dialog.findViewById(R.id.editTextDialogUserInput2);
                Button update = dialog.findViewById(R.id.update);
                Button cancel = dialog.findViewById(R.id.cancel);

              /************************- when dialog show show data of current row-*****************************/
                name_text.setText(tName);
                desc_text.setText(tDesc);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        String sName = name_text.getText().toString().trim();
                        String sDesc = desc_text.getText().toString().trim();

                        /***********-update data and refresh recycler view-************/
                        Task task = new Task(sName,sDesc);
                        taskList.set(holder.getAdapterPosition(),task);

                        notifyDataSetChanged();
                        holder.frontlayout.setVisibility(View.VISIBLE);
                        holder.backlayout.setVisibility(View.INVISIBLE);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        holder.frontlayout.setVisibility(View.VISIBLE);
                        holder.backlayout.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        /****************-delete button click-************************/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*data is deleted*/
                int position = holder.getAdapterPosition();
                taskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,taskList.size());
            }
        });
        /*****************-click < to swipe view left-************************/
        holder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animSlideup = AnimationUtils.loadAnimation(mContext,
                        R.anim.left_to_right);
                /*animSlideup.setAnimationListener(R);*/
                animSlideup.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        holder.backlayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      //  holder.left.setVisibility(View.INVISIBLE);
                        holder.backlayout.setVisibility(View.VISIBLE);
                        holder.frontlayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                View views = holder.backlayout;
                views.postDelayed(new Runnable() {
                    public void run() {
                       // views.setVisibility(View.VISIBLE);
                        holder.frontlayout.startAnimation(animSlideup);
                        holder.frontlayout.setVisibility(View.INVISIBLE);
                    }
                }, 100);
            }
        });
        /*****************-click > to swipe view right-******************/
        holder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animSlideup = AnimationUtils.loadAnimation(mContext,
                        R.anim.right_to_left);
                /*animSlideup.setAnimationListener(R);*/
                animSlideup.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        holder.frontlayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //  holder.left.setVisibility(View.INVISIBLE);
                        holder.frontlayout.setVisibility(View.VISIBLE);
                        holder.backlayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                View views = holder.backlayout;
                views.postDelayed(new Runnable() {
                    public void run() {
                        // views.setVisibility(View.VISIBLE);
                        holder.backlayout.startAnimation(animSlideup);
                        holder.frontlayout.setVisibility(View.VISIBLE);
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
        return taskList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**********************Initialize Variables of custom layout******************/
        TextView nametxtView,desctxtView;
        RelativeLayout edit, delete;
        ImageView left,right;
        LinearLayout frontlayout,backlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nametxtView = itemView.findViewById(R.id.task_name);
            desctxtView = itemView.findViewById(R.id.task_desc);
            edit = itemView.findViewById(R.id.edit_task);
            delete = itemView.findViewById(R.id.delete_task);
            left=itemView.findViewById(R.id.left);
            right=itemView.findViewById(R.id.right);
            frontlayout = (LinearLayout) itemView.findViewById(R.id.rowFG);
            backlayout = (LinearLayout) itemView.findViewById(R.id.rowBG);
        }
    }
}

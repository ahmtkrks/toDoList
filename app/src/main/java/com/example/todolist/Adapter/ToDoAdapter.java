package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.CardViewObjectHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity activity){
        this.db=db;
        this.activity=activity;
    }

    @NonNull
    @Override
    public CardViewObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.task_layout,parent,false);
        return new CardViewObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewObjectHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.toDoCheckBox.setText(item.getTask());
        holder.toDoCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.toDoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });

    }

    public void addItem(ToDoModel item) {
        toDoList.add(item);
        notifyDataSetChanged();
    }

    private Boolean toBoolean(int n) {
        return n!=0;
    }

    public void setTasks(List<ToDoModel> toDoList){
        this.toDoList=toDoList;
        notifyDataSetChanged();
        //SADAMS
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item =toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
        notifyDataSetChanged();
        

    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class CardViewObjectHolder extends RecyclerView.ViewHolder{


        CheckBox toDoCheckBox;

        public CardViewObjectHolder( View itemView) {
            super(itemView);
            toDoCheckBox=itemView.findViewById(R.id.toDoCheckBox);
        }
    }

}

package com.tecxpert.myeduapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.model.Subject;

import java.util.List;
import java.util.Random;

public class SubjectListAdepter extends RecyclerView.Adapter<SubjectListAdepter.MyViewholder> {

    Context context;
    List<Subject> mpDataList;
    public interface OnitemClick{
        void OnitemClick(int position, Subject mpData, View view);
    }
    OnitemClick onitemClick;
   public SubjectListAdepter(Context context, List<Subject> mpDataList, OnitemClick onitemClick){
       this.context=context;
       this.mpDataList=mpDataList;
       this.onitemClick=onitemClick;
   }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);



        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Subject mpData=mpDataList.get(position);
        holder.text.setText(mpData.getName());
        holder.tvIcon.setText(mpData.getName().substring(0,1));
        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.tvIcon.getBackground()).setColor(color);
        holder.onBind(position,mpData);

    }

    @Override
    public int getItemCount() {
        return mpDataList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView text,tvIcon;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            text=(TextView) itemView.findViewById(R.id.text);
            tvIcon=(TextView) itemView.findViewById(R.id.tvIcon);

        }
    public void onBind(int position,Subject mpData){
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view ) {
            onitemClick.OnitemClick(position,mpData,view);
        }
    });

}
    }
}

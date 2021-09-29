package com.tecxpert.myeduapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.model.Pdf;
import com.tecxpert.myeduapplication.model.Vedio;

import java.util.List;
import java.util.Random;

public class BookListAdepter extends RecyclerView.Adapter<BookListAdepter.MyViewholder> {

    Context context;
    List<Pdf> mpDataList;
    public interface OnitemClick{
        void OnitemClick(int position, Pdf mpData, View view);
    }
    OnitemClick onitemClick;
   public BookListAdepter(Context context, List<Pdf> mpDataList, OnitemClick onitemClick){
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
        Pdf mpData=mpDataList.get(position);
        holder.text.setText(mpData.getTopic_name());
        holder.tvIcon.setText(mpData.getTopic_name().substring(0,1));
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
    public void onBind(int position,Pdf mpData){
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view ) {
            onitemClick.OnitemClick(position,mpData,view);
        }
    });

}
    }
}

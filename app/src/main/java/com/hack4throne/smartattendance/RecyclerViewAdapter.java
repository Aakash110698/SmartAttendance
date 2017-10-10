package com.hack4throne.smartattendance;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by malavan on 27/08/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{


    Context context;
    int Count;
    String csa;
    HashMap<String,String> values = new HashMap<>();


    RecyclerViewAdapter(Context context,int Count,String csa)
    {
        this.context  =  context;
        this.Count =     Count;
        this.csa = csa;
    }


    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        for(int i=1;i<=Count;i++)
        {
            if(i<10)
            values.put(csa+"0"+i,"P");
            else
            {
                values.put(csa+i,"P");
            }
        }


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_grid_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;






    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder,  int position) {
        holder.att.setImageBitmap(textAsBitmap(holder.getAdapterPosition()+1+"",40, Color.WHITE));
        holder.att.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        //  holder.att.setImageResource(R.drawable.absent);


        // values.put(position+"","A");
        holder.att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Tag for BackgroudTint",holder.att.getBackgroundTintList().toString());

                if(holder.att.getBackgroundTintList() == ColorStateList.valueOf(Color.BLACK))
                {
                    holder.att.setImageBitmap(textAsBitmap("" + (holder.getAdapterPosition()+1),40,Color.WHITE));
                    holder.att.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    if(holder.getAdapterPosition()<10)
                    values.put(csa+"0"+(holder.getAdapterPosition()+1),"P");
                    else
                    {
                        values.put(csa+(holder.getAdapterPosition()+1),"P");
                    }
                    //  holder.att.setImageBitmap(textAsBitmap("P",40,Color.WHITE));
                }
                else if(holder.att.getBackgroundTintList() == ColorStateList.valueOf(Color.GREEN) ) {


                    //  holder.att.setImageResource(R.drawable.absent);
                    holder.att.setImageBitmap(textAsBitmap((holder.getAdapterPosition()+1)+"",40,Color.WHITE));
                    holder.att.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    if(holder.getAdapterPosition()<10)
                    {
                        values.put(csa+"0"+(holder.getAdapterPosition()+1),"A");
                    }
                    else
                    values.put(csa + (holder.getAdapterPosition()+1), "A");
                }

                // Toast.makeText(context, "Position " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    @Override
    public int getItemCount() {

        return Count;
    }
}

package com.hack4throne.smartattendance;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by malavan on 27/08/17.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {




    FloatingActionButton att;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        att = (FloatingActionButton)itemView.findViewById(R.id.att);







    }



    @Override
    public void onClick(View v) {

    }
}

package com.demo.android.smsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.activities.SendSMSActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DU on 12/17/2017.
 */

public class ListContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<String> numbers;
        ArrayList<String> names;
        Context context;

        public ListContactsAdapter(Context mContext, ArrayList<String> names,ArrayList<String> numbers){
            this.names =names;
            this.numbers=numbers;
            context=mContext;
        }

    @Override
    public ItemContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemContactViewHolder holder;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rc_item_contacts, parent, false);
        holder = new ItemContactViewHolder(context,view);


        return holder;
    }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final String name = names.get(position);
            final String phone = numbers.get(position);

            ((ItemContactViewHolder) holder).txtName.setText(name);
            ((ItemContactViewHolder) holder).txtphone.setText(phone);

           ((View) ((ItemContactViewHolder) holder).txtName.getParent().getParent())
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ItemContactViewHolder) holder).txtphone.setTextColor(Color.BLUE);
                            ((ItemContactViewHolder) holder).txtName.setTextColor(Color.BLUE);


                        }
                    });
        }

        @Override
        public int getItemCount() {

          //  Log.e("size",names.size()+"");
            return  names.size();
        }

}
class ItemContactViewHolder extends RecyclerView.ViewHolder {

    public TextView txtName, txtphone, txtMessage;
    private Context context;

    ItemContactViewHolder(Context context, View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtphone = (TextView) itemView.findViewById(R.id.txtphone);
        this.context = context;
    }
}

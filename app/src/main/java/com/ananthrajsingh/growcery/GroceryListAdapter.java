package com.ananthrajsingh.growcery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

/**
 * Created by ananthrajsingh on 06/02/19
 * This is the adapter for our recyclerview. Nothing special here. Not-so-usual things will
 * be addressed below using comments below.
 * TODO We are not implementing OnClickListener as of now.
 */
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder> {



    class GroceryViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView quantity;
        TextView unit;

        GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTv);
            quantity = itemView.findViewById(R.id.quantityTv);
            unit = itemView.findViewById(R.id.unitTv);
        }
    }

    private final LayoutInflater mLayoutInflater;
    /* This is the cached copy of grocery items, which needs to be updated on change */
    private List<Item> mList;
    private Context mContext;

    public GroceryListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder viewHolder, int i) {
        if (mList != null){
            Item current = mList.get(i);
            viewHolder.quantity.setText(Integer.toString(current.getQuantity()));
            viewHolder.name.setText(current.getName());
            /*
             * If we have unit as Unit, we will take care of pluralising.
             * Else we will not care.
             */
            if (current.getUnitType() == 0){
                viewHolder.unit.setText(mContext.getResources().getQuantityText(R.plurals.unit_plural, current.getQuantity()));
            }
            else {
                viewHolder.unit.setText(mContext.getResources().getTextArray(R.array.units)[current.getUnitType()]);
            } }
        else{
            /* In case the list is not yet ready */
            viewHolder.name.setText(R.string.no_item);
        }
    }

    public void setList(List<Item> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /* Take care of case where list is not yet initialised */
        if (mList != null) return mList.size();
        else return 0;
    }


}

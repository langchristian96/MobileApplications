package com.example.langchristian96.androidshopping.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.langchristian96.androidshopping.EditShoppingListActivity;
import com.example.langchristian96.androidshopping.R;
import com.example.langchristian96.androidshopping.ShoppingListsActivity;
import com.example.langchristian96.androidshopping.model.ShoppingList;

import java.util.List;

/**
 * Created by langchristian96 on 11/9/2017.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;
    private Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShoppingListAdapter(List<ShoppingList> myDataset, Context context) {
        shoppingLists = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(((TextView)v).getText().toString().split("-")[0]);
                Intent intent = new Intent(context, EditShoppingListActivity.class);
                Bundle b = new Bundle();
                b.putInt("position", position);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(""+(position+1)+"-"+ shoppingLists.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }
}

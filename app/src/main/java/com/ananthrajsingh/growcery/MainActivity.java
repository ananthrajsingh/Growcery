package com.ananthrajsingh.growcery;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Will help creating link to ViewModel */
    private GroceryViewModel mViewModel;
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        /*
         * We will be deleting the item on a swipe. Therefore we need this implementation.
         */
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                /* Get which data item to delete */
                int position = viewHolder.getAdapterPosition();
               /*
                * So delete takes Item object. What we are doing here is getting the current
                * copy of list from database. We know the position. We eill fetch the item at
                * that position from newly fetched list, and pass to delete function.
                */
                LiveData<List<Item>> list = mViewModel.getAllItems();
                mViewModel.delete(list.getValue().get(position));
            }
        };


        /*
         * ItemTouchHelper must know which recycler view it should be concerned with.
         * It should also know what code to call in case of events.
         */
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        final GroceryListAdapter adapter = new GroceryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        /*
         * When the activity is destroyed, for example through a configuration change,
         * the ViewModel persists. When the activity is re-created, the ViewModelProviders
         * return the existing ViewModel
         */
        mViewModel = ViewModelProviders.of(this).get(GroceryViewModel.class);

        /*
         * GroceryViewModel.getAllItems() returns a LiveData<> therefore here we can observe to it
         * for any changes.
         * This will be fired when the list changes AND the activity is in foreground.
         */
        mViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                /* The data has been updated, let's update cached copy of adapter */
                adapter.setList(items);
            }
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Let's go to AddItemActivity */
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This is where we will receive the Item from AddItemActivity.
     * On receiving we will be storing it to database
     * @param requestCode The code sent when requesting data
     * @param resultCode The code sent by the concerned activity
     * @param data this is the data we got from the concerned activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            /* We successfully got an Item, let's get is and save to database */
            Item item = new Item();
            try {
                item.setUnitType(data.getIntExtra(AddItemActivity.EXTRA_UNIT, 0));
                item.setQuantity(data.getIntExtra(AddItemActivity.EXTRA_AMOUNT, 0));
                item.setName(data.getStringExtra(AddItemActivity.EXTRA_NAME));
                mViewModel.insert(item);
            }catch (NullPointerException e){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}

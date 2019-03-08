package com.ananthrajsingh.growcery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Will help creating link to ViewModel */
    private GroceryViewModel mViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

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
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);            }
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
}

package com.ananthrajsingh.growcery;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

/**
 * Created by ananthrajsingh on 31/01/19
 * This is the Database Access Object class. As the name suggest, here we define the queries for
 * Read, Update and Delete operations. These queries are associated with method calls here.
 * By default, it is mandatory to execute queries on separate thread. This can be altered though.
 * @Dao class must be abstract or interface. An implementation of this class is automatically made
 * by Room. Currently we are not giving the option to update grocery list items, so we will have insert,
 * query and delete.
 */
@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM item_table WHERE uid = :del_uid")
    void deleteItem(int del_uid);

    /*
     * Using LiveData, we don't have to observe database for constant updates.
     * Just by changing the return type to LiveData, Room automatically generates all necessary
     * code to update the LiveData when the database is updated. How great is that!
     */
    @Query("SELECT * FROM item_table ORDER BY uid DESC")
    LiveData<List<Item>> getAllItems();

}

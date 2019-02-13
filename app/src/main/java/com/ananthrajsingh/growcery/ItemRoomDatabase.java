package com.ananthrajsingh.growcery;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ananthrajsingh.growcery.Model.Item;

/**
 * Created by ananthrajsingh on 01/02/19
 * This serves as a layer over our SQLite database. This issues query to the database using Dao class
 * Usually we only need one instance of this class, therefore this should be singleton class.
 * My dear people, please note that if you change schema and don't update version, app will crash.
 * My dear people, again, if you update the version and do not define migration strategy, app will
 * crash.
 */
@Database(entities = {Item.class}, version = 1)
public abstract class ItemRoomDatabase extends RoomDatabase {


    // Abstract getter of Dao. This is how db knows from which class to make Database access objects
    public abstract ItemDao itemDao();
//    private static RoomDatabase.Callback mRoomDatabaseCallback =
//            new RoomDatabase.Callback(){
//                @Override
//                public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                    super.onOpen(db);
//                    new PopulateDbAsync(INSTANCE).execute();
//                }
//            }

    /*
     * volatile fields share a common value. Different threads might be running with their own
     * instance of a variable. To make all threads share a common value, we need 'volatile'. Any
     * changes in one thread is instantaneously reflected in other thread(s). Variables cannot
     * be declared synchronized, therefore we are given volatile. Potentially, this removes any
     * inconsistency that can occur when working on same database through different threads.
     * Basically, read and write happens on main memory and not in thread cache for volatile
     * fields.
     */
    private static volatile ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context){
        // if there is no instance of database in this thread
        if (INSTANCE == null){
            /*
             * When multiple thread exist, they may try to access same resource at same time,
             * resulting in concurrency issue.
             * This block signifies that the code inside this block should only be executed by
             * having a lock to this resource. ItemRoomDatabase.class signifies whose lock needs
             * to held to execute code in block.
             */
            synchronized (ItemRoomDatabase.class){
                // if there is no instance on any thread
                if (INSTANCE == null){
                    // Phew! finally here we can create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "grocery_database")
                            .build();

                }
            }
        }

        return INSTANCE;
    }
}

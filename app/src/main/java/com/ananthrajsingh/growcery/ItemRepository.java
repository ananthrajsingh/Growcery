package com.ananthrajsingh.growcery;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

/**
 * Created by ananthrajsingh on 01/02/19
 * A Repository is a class that abstracts access to multiple data sources. Though it is not a part
 * of Architecture Components, yet it is a good practice. In this application, we only have one
 * one (offline) database, we can easily get away without using this class. Since we pledged to use
 * best practices, here we go.
 *
 * Repository also helps in testing by mocking repository.
 *
 * Repository allows multiple backends. It is repository where we build the logic for whether
 * we need new data from network or use cached up data. The above layers don't bother whre the data
 * is coming from, they are just delivered the data they need.
 *
 *                 ---------REPOSITORY--------
 *                 |                         |
 *                DAO                     NETWORK
 */
public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    public static final int INSERT_QUERY = 1;
    public static final int DELETE_QUERY = 2;

    /**
     * The constructor to give us the hold of database
     * @param application
     */
    public ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    /*
     * We need a wrapper for all database operations here.
     * Since we will only be dealing with this class. For us,
     * this class will deal with the database.
     */

    LiveData<List<Item>> getAllItems(){
        return mAllItems;
    }

    /*
     * Insert and delete queries need to happen on different threads.
     * We are going to use AsyncTask.
     * But should we use different AsyncTask for each type of query?
     * This is debatable, but if I have many different types of queries,
     * code could get long real quick. Therefore, I am going to implement
     * all queries in a single AsyncTask.
     */

    public void insert (Item item){
        new queryAsyncTask(mItemDao, INSERT_QUERY).execute(item);
    }

    public void delete (Item item){
        new queryAsyncTask(mItemDao, DELETE_QUERY).execute(item);
    }

    private static class queryAsyncTask extends AsyncTask<Item, Void, Void>{

        private ItemDao mAsyncDao;
        private int mQueryType;

        queryAsyncTask(ItemDao dao, int queryType){
            mAsyncDao = dao;
            mQueryType = queryType;
        }

        @Override
        protected Void doInBackground(Item... items) {
            switch (mQueryType){
                case DELETE_QUERY:
                    mAsyncDao.deleteItem(items[0].getUid());
                    break;
                case INSERT_QUERY:
                    mAsyncDao.insert((items[0]));
                    break;
            }
            return null;
        }
    }

}

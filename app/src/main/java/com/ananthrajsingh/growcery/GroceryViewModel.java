package com.ananthrajsingh.growcery;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ananthrajsingh.growcery.Model.Item;

import java.util.List;

/**
 * Created by ananthrajsingh on 01/02/19
 * Most basic function of ViewModel is to provide Activity with data in a lifecycle-conscious manner.
 * That is, this data survives configuration changes for eg Screen Rotation. Finally!
 * This serves as the intermediate class between UI and Repository. Increasing modularity,
 * Activity/Fragment is now only responsible for drawing data onto screen. ViewModel takes care of
 * holding and processing data.
 */
public class GroceryViewModel extends AndroidViewModel {

    // Since, well, ViewModel talks to repository not Activity any more
    private ItemRepository mItemRepository;
    // LiveData can do wonders
    private LiveData<List<Item>> mAllItems;

    /*
     * We need another wrapper for getter and setters here.
     * here they go
     */
    public LiveData<List<Item>> getAllItems (){
        return mAllItems;
    }
    public void insert (Item item){
        mItemRepository.insert(item);
    }
    public void delete (Item item){
        mItemRepository.delete(item);
    }
}

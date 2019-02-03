package com.ananthrajsingh.growcery;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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

    // Since, well, repository talks to ViewModel not Activity any more
    private ItemRepository mItemRepository;
    // LiveData can do wonders
    private LiveData<List<Item>> mAllItems;

    public GroceryViewModel(@NonNull Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
        mAllItems = mItemRepository.getAllItems();
    }

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

    /*
     ************************************************************************************
     * Just before you go, please note that that never pass Activity, Fragments or View
     * instances to ViewModel. Regard it as a -
     * WARNING: If we pass activity/fragment/view objects or activity/fragment context,
     * this ViewModel will hold reference to them. Now, in a ViewModel lifecycle,
     * activities can be destroyed and made. ViewModel will hold reference to all those
     * destroyed activities. Now when Garbage Collector comes along to take destroyed
     * activity away it sees "Oh! ViewModel has it's reference, lets not collect it, activity
     * might be in use" and moves ahead. THIS IS MEMORY LEAK!
     */
}

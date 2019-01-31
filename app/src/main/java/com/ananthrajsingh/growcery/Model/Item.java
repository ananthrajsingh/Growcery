package com.ananthrajsingh.growcery.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ananthrajsingh on 31/01/19
 * This is the Entity class. Data for this app is grocery items, and each item is an Entity.
 * An entity must have at least  one field annotated with PrimaryKey.
 */
@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true) // This automatically creates and increments
    private int uid;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "unit_type")
    private int unitType;

    /*
     * Since our variable are private, we need getter and setters.
     * That is the primary rule of encapsulation.
     */

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}

package com.vintile.bingo.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vintile.bingo.model.Feed;

import java.util.List;

/**
 * Created by Sharath on 2020-03-13.
 */

@Dao
public interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Feed> restuarantMenuList);

    @Query("SELECT * FROM table_box")
    LiveData<List<Feed>> getAll();

    @Query("DELETE FROM table_box")
    void deleteAll();

  /*  @Query("SELECT * FROM menuitems WHERE selected = '1'")
    LiveData<List<RestuarantMenu>> getCartMenu();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCart(RestuarantMenu restuarantMenu);*/
}

package com.vintile.bingo;

import androidx.lifecycle.ViewModel;

/**
 * Created by Sharath on 2020/04/05
 **/
public class BingoBoxViewModel extends ViewModel {

    private int count = 0;

    public BingoBoxViewModel() {
    }


    private void updateCount(){
        count++;
    }

    public int getCount(){
        return count;
    }

}

package com.vintile.bingo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BingoBox extends Fragment implements View.OnClickListener{


    private TextView box1;
    private TextView box2;
    private TextView box3;
    private TextView box4;
    private TextView box5;
    private TextView box6;
    private TextView box7;
    private TextView box8;
    private TextView box9;
    private TextView box10;
    private TextView box11;
    private TextView box12;
    private TextView box13;
    private TextView box14;
    private TextView box15;
    private TextView box16;
    private TextView box17;
    private TextView box18;
    private TextView box19;
    private TextView box20;
    private TextView box21;
    private TextView box22;
    private TextView box23;
    private TextView box24;
    private TextView box25;

    private BingoBoxViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_bingo_box, container, false);

        box1 = root.findViewById(R.id.textView1);
        box2 = root.findViewById(R.id.textView2);
        box3 = root.findViewById(R.id.textView3);
        box4 = root.findViewById(R.id.textView4);
        box5 = root.findViewById(R.id.textView5);
        box6 = root.findViewById(R.id.textView6);
        box7 = root.findViewById(R.id.textView7);
        box8 = root.findViewById(R.id.textView8);
        box9 = root.findViewById(R.id.textView9);
        box10 = root.findViewById(R.id.textView10);
        box11 = root.findViewById(R.id.textView11);
        box12 = root.findViewById(R.id.textView12);
        box13 = root.findViewById(R.id.textView13);
        box14 = root.findViewById(R.id.textView14);
        box15 = root.findViewById(R.id.textView15);
        box16 = root.findViewById(R.id.textView16);
        box17 = root.findViewById(R.id.textView17);
        box18 = root.findViewById(R.id.textView18);
        box19 = root.findViewById(R.id.textView19);
        box20 = root.findViewById(R.id.textView20);
        box21 = root.findViewById(R.id.textView21);
        box22 = root.findViewById(R.id.textView22);
        box23 = root.findViewById(R.id.textView23);
        box24 = root.findViewById(R.id.textView24);
        box25 = root.findViewById(R.id.textView25);

        viewModel = new ViewModelProvider(this).get(BingoBoxViewModel.class);
        return root;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.textView1 :

                break;
        }
    }
}

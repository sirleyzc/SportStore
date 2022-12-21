package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.BranchCatalog;
import com.example.myapplication.BranchForm;
import com.example.myapplication.BranchInfo;
import com.example.myapplication.DB.DBFirebaseBranch;
import com.example.myapplication.Entities.Branch;
import com.example.myapplication.R;

import java.util.ArrayList;

public class BranchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Branch> arrayBranch;

    public BranchAdapter(Context context, ArrayList<Branch> arrayBranch) {
        this.context = context;
        this.arrayBranch = arrayBranch;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Branch> getArrayBranch() {
        return arrayBranch;
    }

    public void setArrayBranch(ArrayList<Branch> arrayBranch) {
        this.arrayBranch = arrayBranch;
    }

    @Override
    public int getCount() {
        return arrayBranch.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayBranch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.branch_template, null);

        Branch branch = arrayBranch.get(i);

        ImageView imgBranch = (ImageView) view.findViewById(R.id.imgBranch);
        TextView textNameBranch = (TextView) view.findViewById(R.id.textNameBranch);
        TextView textPhoneBranch = (TextView) view.findViewById(R.id.textPhoneBranch);
        Button btnDelBranch = (Button) view.findViewById(R.id.btnDelBranch);
        Button btnEditBranch = (Button) view.findViewById(R.id.btnEditBranch);

        imgBranch.setImageResource(R.drawable.balon_futbol);
        textNameBranch.setText(branch.getName());
        textPhoneBranch.setText(branch.getPhone());

        Glide.with(context)
                .load(branch.getImage())
                .override(500, 500)
                .into(imgBranch);

        imgBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), BranchInfo.class);
                intent.putExtra("name", branch.getName());
                intent.putExtra("phone", branch.getPhone());
                intent.putExtra("image",branch.getImage());
                context.startActivity(intent);
            }
        });

        btnDelBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebaseBranch dbFirebaseBranch = new DBFirebaseBranch();
                dbFirebaseBranch.deleteData(branch.getId());
                Intent intent = new Intent(context, BranchCatalog.class);
                context.startActivity(intent);
            }
        });

        btnEditBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BranchForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", branch.getId());
                intent.putExtra("name", branch.getName());
                intent.putExtra("phone", branch.getPhone());
                intent.putExtra("image", branch.getImage());
                intent.putExtra("latitude", branch.getLatitude());
                intent.putExtra("longitude", branch.getLongitude());
                context.startActivity(intent);
            }
        });

        return view;
    }

}

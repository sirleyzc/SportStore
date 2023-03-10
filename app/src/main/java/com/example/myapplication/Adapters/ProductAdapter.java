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
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.ProductForm;
import com.example.myapplication.ProductInfo;
import com.example.myapplication.ProductCatalog;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> arrayProducts;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }

    @Override
    public int getCount() {
        return arrayProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template,null);

        Product product = arrayProducts.get(i);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        TextView textNameProd = (TextView) view.findViewById(R.id.textNameProd);
        TextView textDescriptionProd = (TextView) view.findViewById(R.id.textDescriptionProd);
        TextView textPriceProd = (TextView) view.findViewById(R.id.textPriceProd);
        Button btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteProd);
        Button btnEditTemplate = (Button) view.findViewById(R.id.btnEditProd);

        textNameProd.setText(product.getName());
        textDescriptionProd.setText(product.getDescription());
        textPriceProd.setText(String.valueOf(product.getPrice()));

        Glide.with(context)
                .load(product.getImage())
                .override(500, 500)
                .into(imgProduct);

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductInfo.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());

                context.startActivity(intent);
            }
        });

        btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase = new DBFirebase();
                dbFirebase.deleteProduct(product.getId());
                Intent intent = new Intent(context.getApplicationContext(), ProductCatalog.class);
                context.startActivity(intent);
            }
        });

        btnEditTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", product.getId());
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }
        });
        return view;
    }
}

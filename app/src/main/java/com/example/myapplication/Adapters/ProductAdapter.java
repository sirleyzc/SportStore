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

import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.ProductForm;
import com.example.myapplication.ProductInfo;
import com.example.myapplication.ProductCatalog;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Product> arrayProducts;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Product> getArrayProducts() {
        return arrayProducts;
    }

    public void setArrayProducts(ArrayList<Product> arrayProducts) {
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
        view = layoutInflater.inflate(R.layout.product_template, null);

        Product product = arrayProducts.get(i);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        TextView textNameProd = (TextView) view.findViewById(R.id.textNameProd);
        TextView textDescriptionProd = (TextView) view.findViewById(R.id.textDescriptionProd);
        TextView textPriceProd = (TextView) view.findViewById(R.id.textPriceProd);
        Button btnDeleteProd = (Button) view.findViewById(R.id.btnDeleteProd);
        Button btnEditProd = (Button) view.findViewById(R.id.btnEditProd);

        imgProduct.setImageResource(R.drawable.balon_futbol);
        textNameProd.setText(product.getName());
        textDescriptionProd.setText(product.getDescription());
        textPriceProd.setText(product.getPrice());

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductInfo.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                context.startActivity(intent);
            }
        });

        btnDeleteProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase = new DBFirebase();
                dbFirebase.deleteData(product.getId());
                Intent intent = new Intent(context, ProductCatalog.class);
                context.startActivity(intent);
            }
        });

        btnEditProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductForm.class);
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

package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.Entities.Service;
import com.example.myapplication.R;
import com.example.myapplication.ServiceCatalog;
import com.example.myapplication.ServiceForm;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Service> arrayServices;

    public ServiceAdapter(Context context, ArrayList<Service> arrayServices) {
        this.context = context;
        this.arrayServices = arrayServices;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setArrayServices(ArrayList<Service> arrayServices) {
        this.arrayServices = arrayServices;
    }

    public ArrayList<Service> getArrayServices() {
        return arrayServices;
    }
    @Override
    public int getCount() {
        return arrayServices.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayServices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.service_template, null);

        Service service = arrayServices.get(i);

        ImageView imgService = (ImageView) view.findViewById(R.id.imgService);
        TextView textNameService = (TextView) view.findViewById(R.id.textNameService);
        TextView textDescriptionService = (TextView) view.findViewById(R.id.textDescriptionService);
        TextView textPriceService = (TextView) view.findViewById(R.id.textPriceService);
        Button btnDelService = (Button) view.findViewById(R.id.btnDelService);
        Button btnEditService = (Button) view.findViewById(R.id.btnEditService);

        imgService.setImageResource(R.drawable.balon_futbol);
        textNameService.setText(service.getName());
        textDescriptionService.setText(service.getDescription());
        textPriceService.setText(service.getPrice());

        imgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ServiceInfo.class);
                intent.putExtra("name", service.getName());
                intent.putExtra("description", service.getDescription());
                intent.putExtra("price", service.getPrice());
                context.startActivity(intent);
            }
        });

        btnDelService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase = new DBFirebase();
                dbFirebase.deleteData(service.getId());
                Intent intent = new Intent(context, ServiceCatalog.class);
                context.startActivity(intent);
            }
        });

        btnEditService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ServiceForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", service.getId());
                intent.putExtra("name", service.getName());
                intent.putExtra("description", service.getDescription());
                intent.putExtra("price", service.getPrice());
                intent.putExtra("image", service.getImage());
                context.startActivity(intent);
            }
        });

        return view;
    }

}


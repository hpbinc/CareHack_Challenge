package com.example.hance.carehack_challenge;

/**
 * Created by hance on 15/11/17.
 */


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static java.lang.System.exit;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private String[] titles = {"OPHTHALMOLOGY",
            "ONCOLOGY",
            "GYNECOLOGY",
            "PSYCHOLOGY",
            "PSYCHIARTY",
            "UROLOGY",
            "RADIOLOGY",
            "PATHOLOGY"};

    private String[] details = {"Jiri Pasta, M.D., Ph.D., FEBO",
            "Lubcos Petruzelka, M.D., Ph.D.", "Dr. Bohuslav Svobada Csc.",
            "COL. Dr. Jiri Klose, Ph.D.", "COL. M.D. Vlastinil Tichy",
            "Jiri Kocarek, M.D., Ph.D.", "COL. Tomas Belsan, M.D., Ph.D.",
            "COL. M.D. Petr Hrabal"};

    private int[] images = { R.drawable.jiri,
            R.drawable.lubos,
            R.drawable.bohuslav,
            R.drawable.klose,
            R.drawable.vlastimil,
            R.drawable.kocarek,
            R.drawable.tomas,
            R.drawable.petr };


    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.e("hance",":::" + titles[position] + ":::" + details[position]);
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent intent = new Intent (v.getContext(), insert.class);
                    intent.putExtra("titles", titles[position]);
                    intent.putExtra("details", details[position]);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
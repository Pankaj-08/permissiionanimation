package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import cdflynn.android.library.checkview.CheckView;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.RecViewHolder> {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1 ;
    AppBarLayout appBarLayout;
    private AnimatedVectorDrawable tickToCross;
    private AnimatedVectorDrawable crossToTick;
    private boolean tick = true, checkpermission = false;
    private List<Pair<Boolean,Movie>> list;
    private Context context;
    RecViewHolder hol;
    int pos;
    Pair<Boolean, Movie> mo;
    public ReAdapter(Context context, List<Movie> list, AppBarLayout appBarLayout) {
        this.list=new ArrayList<>();
        for (Movie m:list){
            this.list.add(new Pair<>(false,m));
        }
        this.context=context;
        this.appBarLayout = appBarLayout;
        tickToCross= (AnimatedVectorDrawable) ContextCompat.getDrawable(context,R.drawable.avd_tick_to_cross);
        crossToTick= (AnimatedVectorDrawable) ContextCompat.getDrawable(context,R.drawable.avd_cross_to_tick);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        Pair<Boolean, Movie> movie = list.get(position);
        holder.bind(movie.second);
        if(position==list.size()-1)
        {
            holder.button.setText("ALL DONE");
        }

            holder.itemView.setOnClickListener(v -> {
                boolean expanded = movie.second.isExpanded();
            movie.second.setExpanded(!expanded);
            notifyItemChanged(position);
            if(position>0)
            {
                appBarLayout.setExpanded(false);
            }
            else if(appBarLayout.getTop() < 0 && position ==0)
            {
                    appBarLayout.setExpanded(true);
            }
            int i = 0;
            Movie m = null;
            while (i<list.size())
            {
                m = list.get(i).second;

                if(i==position)
                {

                }
                else if(m.isExpanded())
                {
                    m.setExpanded(false);
                    notifyItemChanged(i);
                }
                i++;


            }
        });
        holder.button.setOnClickListener(v ->{
            this.hol = holder;
            this.mo = movie;
            this.pos = position;
            if(holder.title.getText().equals("Storage Access"))
            {
                checkpermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,1);
            }
            else if(holder.title.getText().equals("Location Access"))
            {
                checkpermission(Manifest.permission.ACCESS_FINE_LOCATION,2);
            }
            else if(holder.title.getText().equals("Battery Optimization"))
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)!= PackageManager.PERMISSION_GRANTED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS},4);
                    }
                }else{
                    PowerManager powerManager= (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !powerManager.isIgnoringBatteryOptimizations(context.getPackageName())){
                        Intent intent=new Intent();
                        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:"+context.getPackageName()));
                        context.startActivity(intent);
                    }else{
                        buttonclick();
                    }
                }

            }
            else if(holder.title.getText().equals("Camera Access"))
            {
                checkpermission(Manifest.permission.CAMERA,4);
            }


        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView genre;
        private View subItem, viewfirst;
        private Button button;
        private ImageView uncheck, tickCross;
        private CheckView checkView;

        public RecViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            genre = itemView.findViewById(R.id.sub_item_genre);
            subItem = itemView.findViewById(R.id.sub_item);
            button = itemView.findViewById(R.id.conti);
            checkView = itemView.findViewById(R.id.check);
            uncheck = itemView.findViewById(R.id.notcheck);
            viewfirst = itemView.findViewById(R.id.viewfirst);
            tickCross = itemView.findViewById(R.id.tick);
        }

        private void bind(Movie movie) {
            boolean expanded = movie.isExpanded();
            Animation slide_down = AnimationUtils.loadAnimation(context,
                    R.anim.slide_down);

            Animation slide_up = AnimationUtils.loadAnimation(context,
                    R.anim.slide_up);

            subItem.startAnimation(expanded ? slide_down:slide_up);
            subItem.setVisibility(expanded ?View.VISIBLE : View.GONE);

            title.setText(movie.getTitle());
            genre.setText("Info: " + movie.getGenre());
        }
    }

public void checkpermission(String perm, int requestcode) {

        if (ContextCompat.checkSelfPermission(context,
            perm)
            != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
               perm)) {
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{perm},
                    requestcode);
        }
    } else {
        // Permission has already been granted
    }
}

public void buttonclick()
{
     mo.second.setExpanded(false);
    notifyItemChanged(pos);
    AnimatedVectorDrawable drawable;



    drawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(context, R.drawable.animated_check);
    hol.tickCross.setImageDrawable(drawable);
    if(pos==list.size()-1)
    {
        drawable.start();
        list.get(pos).second.setExpanded(false);
        hol.viewfirst.setVisibility(View.GONE);
        Intent i = new Intent(context,MainActivity.class);
        context.startActivity(i);
    }
    else {
        Pair<Boolean, Movie> movie1 = list.get(pos + 1);
        movie1.second.setExpanded(true);
        hol.uncheck.setVisibility(View.GONE);
        if (!movie1.first) {
            drawable.start();
//                if(tick)
//                    drawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(context,R.drawable.avd_tick_to_cross);
//                else
            list.set(pos + 1, new Pair<>(true, movie1.second));
//                tick = !tick;
//                holder.checkView.setVisibility(View.VISIBLE);
            hol.checkView.check();
            if (pos > 0) {
                appBarLayout.setExpanded(false);
            }
            notifyItemChanged(pos + 1);
        }
    }
}

}
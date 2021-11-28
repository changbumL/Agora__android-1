package com.cos.Agora.location.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.R;
import com.cos.Agora.location.LocationActivity;
import com.cos.Agora.location.model.LocationReqDto;
import com.cos.Agora.location.model.LocationRespDto;
import com.cos.Agora.login.AuthActivity;
import com.cos.Agora.main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private List<LocationRespDto> locations;
    private LocationActivity laContext;
    private double longitude;
    private double latitude;
    private LocationReqDto locationReqDto;
    private static final String TAG = "LocationAdapter";

    public LocationAdapter(LocationActivity laContext) {
        this.laContext= laContext;
        this.locations=new ArrayList<>();
    }

    public void setLocations(List<LocationRespDto> locations){
        this.locations =locations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.location_item,parent,false);
        TextView guname = view.findViewById(R.id.tv_guname);
        TextView dongname =view.findViewById(R.id.tv_dongname);
        LinearLayout locationItem = view.findViewById(R.id.location_item);

        locationItem.setOnClickListener(v -> {
            SharedPreferences pref = v.getContext().getSharedPreferences("pref", LocationActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("dong", dongname.getText().toString());
            editor.putString("gu",guname.getText().toString());
            editor.commit();

            //LocationActivity에서 위치 받아오기
            longitude = laContext.getLongitude();
            latitude = laContext.getLatitude();

            locationReqDto = new LocationReqDto(longitude,latitude);

            int userId = pref.getInt("userId",0);
            if(userId == 0) {
                Intent intent = new Intent(laContext, AuthActivity.class);
//                intent.putExtra("location", (Parcelable) locationReqDto);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                laContext.startActivity(intent);
                laContext.finish();
            }else{
                Intent intent = new Intent(laContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                laContext.startActivity(intent);
                laContext.finish();
            }
        });
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView siname;
        private TextView guname;
        private TextView dongname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            siname = itemView.findViewById(R.id.tv_siname);
            guname = itemView.findViewById(R.id.tv_guname);
            dongname= itemView.findViewById(R.id.tv_dongname);

        }
        public void setItem(LocationRespDto locationRespDto){
            siname.setText(locationRespDto.getSdm());
            guname.setText(locationRespDto.getSggm());
            dongname.setText(locationRespDto.getGhhjd());
        }
    }
}
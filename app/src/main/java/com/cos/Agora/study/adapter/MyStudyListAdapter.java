package com.cos.Agora.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.R;
import com.cos.Agora.study.model.MyStudyListRespDto;
import com.cos.Agora.study.mystudy.MyDetailActivity;
import com.cos.Agora.study.studydetail.DetailActivity;
import com.cos.Agora.study.model.StudyListRespDto;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyStudyListAdapter extends RecyclerView.Adapter<MyStudyListAdapter.MyViewHolder>{

    private static final String TAG = "MyStudyListAdapter";
    private List<MyStudyListRespDto> mItemsList;
    private Context mContext;
    private static double latitude; // 사용자 위도
    private static double longitude;

    public MyStudyListAdapter(List<MyStudyListRespDto> mItemsList, Context mContext, double latitude, double longitude) {
        this.mItemsList = mItemsList;
        this.mContext = mContext;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.study_item,parent,false);
        // 스터디 리스트의 리사이클러뷰를 반복 사용해도 무관하다.
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(mItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView photo;
        private TextView interest,title,distance,createDate,limit,current;
        private LinearLayout studyItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            interest =itemView.findViewById(R.id.study_interest);
            title =itemView.findViewById(R.id.study_title);
            distance =itemView.findViewById(R.id.study_distance);
            createDate = itemView.findViewById(R.id.study_create_date);
            limit = itemView.findViewById(R.id.study_limit);
            current = itemView.findViewById(R.id.study_current);
            studyItem= itemView.findViewById(R.id.study_item);

        }

        public void setItem(MyStudyListRespDto mystudyListRespDto){
            try {
                interest.setText(mystudyListRespDto.getInterest());
                title.setText(mystudyListRespDto.getTitle());

                distance.setText(new Double((mystudyListRespDto.getDistance()*100/100.0)/1000).toString());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                createDate.setText(format.format(mystudyListRespDto.getCreateDate())+"");
                limit.setText(mystudyListRespDto.getLimit()+"");
                current.setText(mystudyListRespDto.getCurrent()+"");
                studyItem.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), MyDetailActivity.class); // detail
                    intent.putExtra("studyId", mystudyListRespDto.getId());
                    // 이 밑의 코드부터는 추가된 코드로써 클릭했을 때의 스터디의 데이터를 전달하는 역할을 한다.
                    intent.putExtra("studyTitle", mystudyListRespDto.getTitle());
                    intent.putExtra("studyInterest", mystudyListRespDto.getInterest());
                    intent.putExtra("studyCreateDate", mystudyListRespDto.getCreateDate()); // Date로 보냈음
                    intent.putExtra("studyLimit", mystudyListRespDto.getLimit());
                    intent.putExtra("studyCurrent", mystudyListRespDto.getCurrent());
                    intent.putExtra("studyDistance", mystudyListRespDto.getDistance());

                    v.getContext().startActivity(intent);
                });
            } catch (Exception e) {
                Log.d(TAG, "null");
            }
        }
    }
}

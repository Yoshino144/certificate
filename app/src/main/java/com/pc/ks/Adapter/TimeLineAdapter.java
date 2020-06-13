package com.pc.ks.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.github.vipulasri.timelineview.TimelineView;
import com.pc.ks.List.OrderStatus;
import com.pc.ks.List.TimeLineModel;
import com.pc.ks.MainActivity;
import com.pc.ks.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder>{

    private LayoutInflater mLayoutInflater;
    private List<TimeLineModel> mFeedList;
    private MainActivity mainActivity;

    public TimeLineAdapter(List<TimeLineModel> mDataList, MainActivity mainActivity) {
        mFeedList = mDataList;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        TimeLineModel timeLineModel = mFeedList.get(position);

        if(timeLineModel.getStatus() == OrderStatus.INACTIVE){
            holder.mTimelineView.setMarker(VectorDrawableCompat.create(mainActivity.getResources(),R.drawable.ic_marker_inactive, mainActivity.getTheme()),R.color.colorGrey400);
        }else if(timeLineModel.getStatus() == OrderStatus.ACTIVE){
            holder.mTimelineView.setMarker(VectorDrawableCompat.create(mainActivity.getResources(),R.drawable.ic_marker_active, mainActivity.getTheme()),R.color.colorGrey400);
        }else{
            holder.mTimelineView.setMarker(VectorDrawableCompat.create(mainActivity.getResources(),R.drawable.ic_marker, mainActivity.getTheme()),R.color.colorGrey400);
        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(FormatDateTime(timeLineModel.getDate(),"yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        }
        else
            holder.date.setVisibility(View.GONE);

        holder.message.setText(timeLineModel.getMessage());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private  String FormatDateTime(String time,String originalFormat, String ouputFormat){
        LocalDateTime date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(originalFormat, Locale.ENGLISH));
        return date.format(DateTimeFormatter.ofPattern(ouputFormat, Locale.ENGLISH));
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public static class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;
        public AppCompatTextView date;
        public AppCompatTextView message;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            date = itemView.findViewById(R.id.text_timeline_date);
            message = itemView.findViewById(R.id.text_timeline_title);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
        }
    }

}

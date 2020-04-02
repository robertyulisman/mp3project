package com.baru.shawnmendes.lirikmp3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baru.shawnmendes.R;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private List<com.baru.shawnmendes.lirikmp3.Audio> AudioList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, speaker, length;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            speaker = (TextView) view.findViewById(R.id.speaker);
            length = (TextView) view.findViewById(R.id.length);
        }
    }

    public AudioAdapter(List<com.baru.shawnmendes.lirikmp3.Audio> AudioList) {
        this.AudioList = AudioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        com.baru.shawnmendes.lirikmp3.Audio audio = AudioList.get(position);
        holder.name.setText(audio.getName());
        holder.speaker.setText(audio.getSpeaker());
        holder.length.setText(audio.getLength());
    }

    @Override
    public int getItemCount() {
        return AudioList.size();
    }

    public void filterList(List<com.baru.shawnmendes.lirikmp3.Audio> AudioList)
    {
        this.AudioList = AudioList;
        notifyDataSetChanged();
    }
}

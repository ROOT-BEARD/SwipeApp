package com.example.swipeapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{


    private List<VideoItem> videoItems;

    public VideoAdapter(List<VideoItem> videoItems) {
        this.videoItems =  videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.video_viewer,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }


    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView textVideoTitle, textVideoDescription, idText;
        VideoView videoView;
        ProgressBar progressBar;

        ImageView pauseButton;

        CardView playButton;

        private boolean descriptionExpanded = false;



        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle = itemView.findViewById(R.id.textTitle);
            textVideoDescription = itemView.findViewById(R.id.textDescription);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            pauseButton = itemView.findViewById(R.id.pauseButtonIcon);
            playButton = itemView.findViewById(R.id.playButton);
            idText = itemView.findViewById(R.id.textId);
        }

        void setVideoData(VideoItem videoItem){
            descriptionExpanded = false;

            textVideoTitle.setText(videoItem.title);
            idText.setText(videoItem.id);
            if(videoItem.description.length() > 50){
                textVideoDescription.setText(videoItem.description.substring(0, 50) + "...");
                textVideoDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        descriptionExpanded = !descriptionExpanded;
                        if(descriptionExpanded){
                            textVideoDescription.setText(videoItem.description);
                        } else {
                            textVideoDescription.setText(videoItem.description.substring(0, 50) + "...");
                        }
                    }
                });
            } else{
                textVideoDescription.setText(videoItem.description);
                textVideoDescription.setOnClickListener(null);
            }

            videoView.setVideoURI(Uri.parse(videoItem.videoUrl));

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pauseButton.setImageResource(R.drawable.ic_play);
                    videoView.setAlpha(1.0f);
                    progressBar.setVisibility(View.GONE);

                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    videoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            playButton.animate()
                                    .scaleX(1.1f)
                                    .scaleY(1.1f)
                                    .setDuration(100)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This runs automatically after the 100ms expand animation finishes
                                            playButton.animate()
                                                    .scaleX(1.0f)
                                                    .scaleY(1.0f)
                                                    .setDuration(100)
                                                    .start();
                                        }
                                    })
                                    .start();

                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.pause();
                                pauseButton.setImageResource(R.drawable.ic_pause);
                                videoView.setAlpha(0.5f);
                            }else{
                                mediaPlayer.start();
                                pauseButton.setImageResource(R.drawable.ic_play);
                                videoView.setAlpha(1.0f);
                            }
                        }
                    });
                }
            });



            //used to loop the video by starting again on completion
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }
}

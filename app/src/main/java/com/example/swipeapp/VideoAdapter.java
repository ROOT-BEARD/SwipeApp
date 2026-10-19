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

    /**the list of videoItems*/
    private List<VideoItem> videoItems;

    /**
     * 
     * constructor
     * @param videoItems the videoItems that will in the videoViewer
     */
    public VideoAdapter(List<VideoItem> videoItems) {
        this.videoItems =  videoItems;
    }

    /**
     * creates a inflated xml layout with the new screen
     * 
     * @param parent view container
     * @param viewType this is unused, but would be used to show the type of video being played
     * @return a VideoViewHolder with the inflated video_viewer
     */
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.video_viewer,parent,false)
        );
    }

    /**
     * binds the videoItem and the videoViewHolder
     * 
     * @param holder 
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItems.get(position));
    }

    /**
     * gets the count of videos
     * 
     * @return returns an integer count of the amount of videoItems
     */
    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    /**
     * 
     * VideoViewHolder, this class holds the the video and ui
     * containing a videos metadata
     */
    static class VideoViewHolder extends RecyclerView.ViewHolder{
        
        TextView textVideoTitle, textVideoDescription, idText;
        
        VideoView videoView;
        
        ProgressBar progressBar;

        ImageView pauseButton;

        CardView playButton;

        private boolean descriptionExpanded = false;


        /**
         * asigns all of the ui elements within the xml
         * 
         * @param itemView the items within view
         */
        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            /**assigns all of the ui elements */
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle = itemView.findViewById(R.id.textTitle);
            textVideoDescription = itemView.findViewById(R.id.textDescription);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            pauseButton = itemView.findViewById(R.id.pauseButtonIcon);
            playButton = itemView.findViewById(R.id.playButton);
            idText = itemView.findViewById(R.id.textId);
        }

        /**
         * sets all the ui elements to use the information from the passed
         * in VideoItem
         * 
         * @param videoItem the video item for this videoViewHolder
         */
        void setVideoData(VideoItem videoItem){
            descriptionExpanded = false;
            // sets the title and id
            textVideoTitle.setText(videoItem.title);
            idText.setText(videoItem.id);
            // if the character count of the description is over 50, make it expandable
            if(videoItem.description.length() > 50){
                //set the text to the first 50 characters, and "..." to show there is more
                textVideoDescription.setText(videoItem.description.substring(0, 50) + "...");
                //set the oncall listener for the description text
                textVideoDescription.setOnClickListener(new View.OnClickListener() {
                    /**
                     * 
                     * toggles expanding the description when clicked
                     * 
                     * @param v the view that was clicked
                     */
                    @Override
                    public void onClick(View v){
                        //toggle if expanded
                        descriptionExpanded = !descriptionExpanded;
                        // if it is expanded show the whole description, otherwise just the short version
                        if(descriptionExpanded){
                            textVideoDescription.setText(videoItem.description);
                        } else {
                            textVideoDescription.setText(videoItem.description.substring(0, 50) + "...");
                        }
                    }
                });
            } else{
                /*if the length if not over the limit, then just set
                the desctiption in full, and set not onclick listener*/
                textVideoDescription.setText(videoItem.description);
                textVideoDescription.setOnClickListener(null);
            }
            //gets the video url from the videoItem
            videoView.setVideoURI(Uri.parse(videoItem.videoUrl));
            //listens for when the mediaPlayer is ready
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                /**
                 * 
                 * runs when the media player is ready, seting onclick logic,
                 * plays the video, and sets the progress bar to be invisable
                 * 
                 * @param mediaPlayer the video player
                 */
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
                            //plays animation
                            playButton.animate()
                                    .scaleX(1.1f)
                                    .scaleY(1.1f)
                                    .setDuration(100)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This runs automatically after animation finsihes
                                            playButton.animate()
                                                    .scaleX(1.0f)
                                                    .scaleY(1.0f)
                                                    .setDuration(100)
                                                    .start();
                                        }
                                    })
                                    .start();
                            // if the mediaplay is playing when clicked, pause
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.pause();
                                pauseButton.setImageResource(R.drawable.ic_pause);
                                videoView.setAlpha(0.5f);
                            }else{
                                //otherwise play
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

package com.example.swipeapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;


/**
 * Honor Code: I pledge that this submission is solely my work, and that I have neither given to nor
 * received help from anyone other than the instructor or TA’s.
 * 
 * @author Alden Wyatt
 * date: 10/18/2026
 * 
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    /**used to hold the videoItems*/
    List<VideoItem> videoItems = new ArrayList<>();

    /**
     * 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        
        final ViewPager2 videoViewPager = findViewById(R.id.videoViewPage);

        /** creating all of the video items with their metadata */
        VideoItem catVid = new VideoItem("https://github.com/ROOT-BEARD/SwipeAppVideos/blob/624e17b762e3898b048b3fe4037015e8db6c86d0/CatInGym.mp4?raw=true",
                "Cat in the gym",
                "There was a tiny kitten at the gym, the rest of this text is just to show how you can expand the description" +
                        " as to have longer descriptions without them always being in the way of the video",
                "101010101");

        VideoItem dogOnBed = new VideoItem("https://github.com/ROOT-BEARD/SwipeAppVideos/blob/main/DogOnBedOP.mp4?raw=true","Rotisserie Dog", "Why is she laying like that tho", "10000101010");
        VideoItem hotDog = new VideoItem("https://github.com/ROOT-BEARD/SwipeAppVideos/blob/main/hotdogOP.mp4?raw=true", "Hot Dog", "It was really hot that day", "10101010111");
        VideoItem dogRolling = new VideoItem("https://github.com/ROOT-BEARD/SwipeAppVideos/blob/main/rollingOP.mp4?raw=true", "Rolling", "Dog rolls around like crazy for no reason and then is awkward after", "1000010010");
        VideoItem tired = new VideoItem("https://github.com/ROOT-BEARD/SwipeAppVideos/blob/main/tiredOP.mp4?raw=true", "Tired", "She acts like she can't just take a nap", "001010101");

        /** add the videos to the videoItemlist */
        videoItems.add(dogRolling);
        videoItems.add(catVid);
        videoItems.add(dogOnBed);
        videoItems.add(hotDog);
        videoItems.add(tired);

        /** create a new video adapter for the videoViewPage with the videos in the list */
        videoViewPager.setAdapter(new VideoAdapter(videoItems));
    }

}
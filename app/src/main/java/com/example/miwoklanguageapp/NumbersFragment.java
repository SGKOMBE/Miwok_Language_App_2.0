package com.example.miwoklanguageapp;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    //Audio Manager declaration for managing audio of our app.
    private AudioManager mAudioManager;
    //This will hold the media player object for global use.
    private MediaPlayer mMediaPlayer;
    //Global Focus Change Listener that will manage various focus change scenarios.
    AudioManager.OnAudioFocusChangeListener mChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }

        }
    };
    //It will release the mediaplayer instance on completion of the playback of the song.
    private MediaPlayer.OnCompletionListener onComplete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //ArrayList of objects of class Word.
        final ArrayList<Word> number = new ArrayList<Word>();

        //Data addition to Array List.
        number.add(new Word("lutti", "one", R.drawable.number_one, R.raw.number_one));
        number.add(new Word("otiiko", "two", R.drawable.number_two, R.raw.number_two));
        number.add(new Word("tolookosu", "three", R.drawable.number_three, R.raw.number_three));
        number.add(new Word("oyyisa", "four", R.drawable.number_four, R.raw.number_four));
        number.add(new Word("massokka", "five", R.drawable.number_five, R.raw.number_five));
        number.add(new Word("temmokka", "six", R.drawable.number_six, R.raw.number_six));
        number.add(new Word("kenekaku", "seven", R.drawable.number_seven, R.raw.number_seven));
        number.add(new Word("kawinta", "eight", R.drawable.number_eight, R.raw.number_eight));
        number.add(new Word("wo’e", "nine", R.drawable.number_nine, R.raw.number_nine));
        number.add(new Word("na’aacha", "ten", R.drawable.number_ten, R.raw.number_ten));

        //Setting the array adapter to display word in Numbers Activity.
        WordAdapter adapter = new WordAdapter(getActivity(), number, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = number.get(position);

                releaseMediaPlayer();

                //This will request for audio focus for our media player instance
                //and if present the audio focus will be allocated to our media player instance
                //and then only the media player will be started to play our audio.
                int focus = mAudioManager.requestAudioFocus(mChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmMusic());
                    mMediaPlayer.start();
                }

                //This will set listener for the Completion of the audio playback by passing the global onComplete variable.
                mMediaPlayer.setOnCompletionListener(onComplete);
            }
        });
        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //This will release the audio focus from our media player instance.
            mAudioManager.abandonAudioFocus(mChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}

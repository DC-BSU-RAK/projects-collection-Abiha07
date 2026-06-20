package com.example.dailyvibes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class HomeFragment extends Fragment {

    private final String[][] vibeData = {
        {"🌞", "Sunny Vibes", "Today is full of golden possibilities!", "#FFD93D"},
        {"🌊", "Ocean Calm", "Flow with ease through your day.", "#4ECDC4"},
        {"🔥", "Fire Energy", "You are unstoppable today!", "#FF6B6B"},
        {"🌙", "Moon Dreamer", "Let your imagination soar tonight.", "#C7CEEA"},
        {"🌿", "Earth Grounded", "Stay rooted, grow naturally.", "#96CEB4"},
        {"⚡", "Lightning Focus", "Sharp mind, clear goals ahead!", "#FFE66D"},
        {"🎵", "Melody Flow", "Move to the rhythm of your heart.", "#DDA0DD"},
        {"🦋", "Transformation", "Change is beautiful — embrace it.", "#A8DADC"},
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvVibeEmoji = view.findViewById(R.id.tvVibeEmoji);
        TextView tvVibeTitle = view.findViewById(R.id.tvVibeTitle);
        TextView tvVibeMsg   = view.findViewById(R.id.tvVibeMsg);
        TextView tvUserName  = view.findViewById(R.id.tvUserName);
        Button   btnNewVibe  = view.findViewById(R.id.btnNewVibe);
        Button   btnInfo     = view.findViewById(R.id.btnHomeInfo);
        View     vibeCard    = view.findViewById(R.id.vibeCard);

        // Load saved name from preferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String name = prefs.getString("username", "Friend");
        tvUserName.setText("Hello, " + name + "! ✨");

        // Set a random vibe on load
        showRandomVibe(tvVibeEmoji, tvVibeTitle, tvVibeMsg, vibeCard);

        btnNewVibe.setOnClickListener(v -> showRandomVibe(tvVibeEmoji, tvVibeTitle, tvVibeMsg, vibeCard));

        btnInfo.setOnClickListener(v ->
            startActivity(new Intent(requireContext(), InfoActivity.class)));

        return view;
    }

    private void showRandomVibe(TextView emoji, TextView title, TextView msg, View card) {
        int idx = new Random().nextInt(vibeData.length);
        emoji.setText(vibeData[idx][0]);
        title.setText(vibeData[idx][1]);
        msg.setText(vibeData[idx][2]);
        card.setBackgroundColor(Color.parseColor(vibeData[idx][3]));
    }
}

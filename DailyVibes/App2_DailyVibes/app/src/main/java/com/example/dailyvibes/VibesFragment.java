package com.example.dailyvibes;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VibesFragment extends Fragment {

    private final String[][] allVibes = {
        {"🌞", "Sunny Vibes",       "Golden possibilities ahead.",    "#FFD93D"},
        {"🌊", "Ocean Calm",        "Flow with ease today.",          "#4ECDC4"},
        {"🔥", "Fire Energy",       "You are unstoppable!",           "#FF6B6B"},
        {"🌙", "Moon Dreamer",      "Let imagination soar.",          "#C7CEEA"},
        {"🌿", "Earth Grounded",    "Stay rooted, grow naturally.",   "#96CEB4"},
        {"⚡", "Lightning Focus",   "Sharp mind, clear goals!",       "#FFE66D"},
        {"🎵", "Melody Flow",       "Move to your heart's rhythm.",   "#DDA0DD"},
        {"🦋", "Transformation",    "Change is beautiful.",           "#A8DADC"},
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vibes, container, false);
        LinearLayout container2 = view.findViewById(R.id.vibesContainer);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String favoriteVibe = prefs.getString("favorite_vibe", "");

        for (String[] vibe : allVibes) {
            View card = inflater.inflate(R.layout.item_vibe_card, container2, false);
            ((TextView) card.findViewById(R.id.cardEmoji)).setText(vibe[0]);
            ((TextView) card.findViewById(R.id.cardTitle)).setText(vibe[1]);
            ((TextView) card.findViewById(R.id.cardDesc)).setText(vibe[2]);
            card.setBackgroundColor(Color.parseColor(vibe[3]));

            if (vibe[1].equals(favoriteVibe)) {
                card.findViewById(R.id.cardFavStar).setVisibility(View.VISIBLE);
            }

            // Tap to set as favourite
            card.setOnClickListener(v -> {
                prefs.edit().putString("favorite_vibe", vibe[1]).apply();
                // refresh
                getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new VibesFragment())
                    .commit();
            });

            container2.addView(card);
        }

        return view;
    }
}

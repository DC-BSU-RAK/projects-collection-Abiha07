package com.example.dailyvibes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);

        EditText etName       = view.findViewById(R.id.etName);
        Switch   switchNotify = view.findViewById(R.id.switchNotify);
        Switch   switchDark   = view.findViewById(R.id.switchDarkMode);
        TextView tvFav        = view.findViewById(R.id.tvFavoriteVibe);
        Button   btnSave      = view.findViewById(R.id.btnSaveSettings);

        // Load saved prefs
        etName.setText(prefs.getString("username", ""));
        switchNotify.setChecked(prefs.getBoolean("notifications", true));
        switchDark.setChecked(prefs.getBoolean("dark_mode", false));
        String fav = prefs.getString("favorite_vibe", "None set");
        tvFav.setText("Favourite Vibe: " + fav);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) name = "Friend";

            prefs.edit()
                .putString("username", name)
                .putBoolean("notifications", switchNotify.isChecked())
                .putBoolean("dark_mode", switchDark.isChecked())
                .apply();

            Toast.makeText(requireContext(), "Settings saved! ✅", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}

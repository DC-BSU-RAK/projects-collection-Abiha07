package com.example.moodcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Mood Calculator — a NaN calculator that processes emotions, not numbers.
 *
 * The user selects two moods and an "operation" to discover
 * what emotional blend results. No numbers are ever shown or computed.
 */
public class MainActivity extends AppCompatActivity {

    // Mood words used as "operands"
    private static final String[] MOODS = {
        "😊 Happy",  "😢 Sad",    "😠 Angry",
        "😨 Anxious","😴 Tired",  "🤩 Excited",
        "😌 Calm",   "😤 Frustrated", "❤️ Loved",
        "😐 Meh",   "🌟 Inspired","🌀 Confused"
    };

    // Operations (replaces +, -, ×, ÷)
    private static final String[] OPS = { "➕ Plus", "➖ Minus", "✖️ Times", "➗ Divided by" };

    // Result combinations (mood1_index, op_index → result string)
    // We'll compute a simple deterministic blend
    private static final String[] BLEND_WORDS = {
        "Bittersweet","Turbulent","Radiant","Melancholic","Restless","Peaceful",
        "Electric","Hollow","Tender","Fuzzy","Unstoppable","Distant",
        "Whimsical","Fierce","Soft","Chaotic","Grounded","Nostalgic",
        "Fierce","Luminous","Heavy","Featherlight","Scattered","Whole"
    };

    // UI state
    private int firstMoodIndex = -1;
    private int opIndex = -1;
    private String currentInput = "";
    private boolean moodOneSet = false;
    private boolean opSet = false;

    private TextView tvDisplay;
    private TextView tvExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvExpression = findViewById(R.id.tvExpression);

        // Info button → modal
        findViewById(R.id.btnInfo).setOnClickListener(v ->
            startActivity(new Intent(this, InfoModalActivity.class))
        );

        // Mood buttons (the "number pad")
        int[] moodBtnIds = {
            R.id.btn0, R.id.btn1, R.id.btn2,
            R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9, R.id.btn10, R.id.btn11
        };

        for (int i = 0; i < moodBtnIds.length; i++) {
            final int moodIdx = i;
            Button btn = findViewById(moodBtnIds[i]);
            btn.setText(MOODS[i]);
            btn.setOnClickListener(v -> onMoodPressed(moodIdx));
        }

        // Operator buttons
        int[] opBtnIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnTimes, R.id.btnDiv};
        for (int i = 0; i < opBtnIds.length; i++) {
            final int opIdx = i;
            Button btn = findViewById(opBtnIds[i]);
            btn.setText(OPS[i]);
            btn.setOnClickListener(v -> onOpPressed(opIdx));
        }

        // Equals
        findViewById(R.id.btnEquals).setOnClickListener(v -> onEquals());

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> onClear());

        tvDisplay.setText("Pick a mood…");
        tvExpression.setText("");
    }

    private void onMoodPressed(int idx) {
        if (!moodOneSet) {
            firstMoodIndex = idx;
            moodOneSet = true;
            tvDisplay.setText(MOODS[idx]);
            tvExpression.setText(MOODS[idx]);
        } else if (opSet) {
            // Second mood
            String expr = tvExpression.getText() + " " + MOODS[idx];
            tvExpression.setText(expr);
            tvDisplay.setText(MOODS[idx]);
            // Auto-calculate
            computeResult(firstMoodIndex, opIndex, idx);
        }
    }

    private void onOpPressed(int idx) {
        if (moodOneSet && !opSet) {
            opIndex = idx;
            opSet = true;
            String expr = tvExpression.getText() + "\n" + OPS[idx];
            tvExpression.setText(expr);
            tvDisplay.setText(OPS[idx]);
        }
    }

    private void onEquals() {
        // Nothing extra; result shown after second mood picked
        tvDisplay.setText("Pick a second mood first!");
    }

    private void onClear() {
        firstMoodIndex = -1;
        opIndex = -1;
        moodOneSet = false;
        opSet = false;
        tvDisplay.setText("Pick a mood…");
        tvExpression.setText("");
    }

    private void computeResult(int m1, int op, int m2) {
        // Deterministic blend: use indices to pick a blend word
        int blendIdx = ((m1 * 3) + (op * 7) + m2) % BLEND_WORDS.length;
        String result = BLEND_WORDS[blendIdx];

        // Craft a sentence
        String sentence = "You feel: " + result + " ✨";
        tvDisplay.setText(sentence);
        String expr = tvExpression.getText() + "\n= " + result;
        tvExpression.setText(expr);

        // Reset for next calculation
        moodOneSet = false;
        opSet = false;
    }
}

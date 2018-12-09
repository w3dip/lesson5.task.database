package ru.sberbank.lesson5.task.database;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import me.priyesh.chroma.ChromaDialog;

import static me.priyesh.chroma.ColorMode.RGB;

public class SettingsActivity extends FragmentActivity {
    private static final int step = 1;
    private static final int max = 50;
    private static final int min = 10;

    private TextView noteTextSize;
    private View noteColorView;
    private View textColorView;
    private SeekBar noteTextSizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        noteTextSizeSeekBar = findViewById(R.id.noteTextSizeSeekBar);
        noteTextSizeSeekBar.setMax( (max - min) / step );
        noteTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                noteTextSize.setText(getRealProgress(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        noteTextSize = findViewById(R.id.noteTextSize);
        noteTextSize.setText(getRealProgress(noteTextSizeSeekBar.getProgress()));

        noteColorView = findViewById(R.id.noteColorPicker);
        noteColorView.setOnClickListener(this::changeColor);

        textColorView = findViewById(R.id.textColorPicker);
        textColorView.setOnClickListener(this::changeColor);

        findViewById(R.id.saveSettingsBtn).setOnClickListener((v) -> {
            finish();
        });
        findViewById(R.id.cancelEditSettingsBtn).setOnClickListener((v) -> {
            finish();
        });
    }

    private void changeColor(View v) {
        new ChromaDialog.Builder()
            .initialColor(((ColorDrawable)v.getBackground()).getColor())
            .colorMode(RGB)
            .onColorSelected(v::setBackgroundColor)
            .create()
            .show(getSupportFragmentManager(), null);
    }

    private String getRealProgress(int value) {
        return String.valueOf(min + (value * step));
    }
}

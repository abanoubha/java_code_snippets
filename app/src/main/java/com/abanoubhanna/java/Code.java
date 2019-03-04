package com.abanoubhanna.java;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

public class Code extends AppCompatActivity {

    static String codeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        final CodeView codeView = (CodeView) findViewById(R.id.code_view);

        codeNo = Objects.requireNonNull(getIntent().getExtras()).getString("snippet");

        String script = getCode("code" + codeNo);

        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(script)
                .withTheme(ColorTheme.MONOKAI)
        );
    }

    public String getCode(String inFile) {
        String tContents = "";
        try {
            InputStream stream = getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }
        return tContents;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(Code.this,MainActivity.class);
        startActivity(back);
        finish();
    }
}

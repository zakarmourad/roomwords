package com.example.android.roomwordssample;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";
    public static boolean update = false;
    public static String name="";

    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setText(name);
        WordViewModel mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Word word = new Word(mEditWordView.getText().toString());

                if (update) {

                    mWordViewModel.deleteByName(new Word(name));
                    mWordViewModel.insert(word);

                }
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word1 = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word1);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}


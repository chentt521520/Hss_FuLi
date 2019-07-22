package com.example.haoss.conversation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.haoss.R;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (!fragment.onBackPressed()) {
            finish();
        }
    }
}

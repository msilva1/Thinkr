package com.android.thinkr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.thinkr.admin.AdminActivity;
import com.android.thinkr.parent.ParentActivity;
import com.android.thinkr.student.StudentActivity;
import com.android.thinkr.teacher.TeacherActivity;
import com.bytes.hack.model.account.User;

public class ThinkrMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thinkr_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (!Preferences.isSignedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        switch (Preferences.getUser().getUserType()) {

            case Admin:
                startActivity(new Intent(this, AdminActivity.class));
                break;

            case Student:
                startActivity(new Intent(this, StudentActivity.class));
                break;

            case Parent:
                startActivity(new Intent(this, ParentActivity.class));
                break;

            case Teacher:
                startActivity(new Intent(this, TeacherActivity.class));
                break;

            default:
                Toast.makeText(
                    ThinkrMain.this, getString(R.string.InvalidUserType), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thinkr_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

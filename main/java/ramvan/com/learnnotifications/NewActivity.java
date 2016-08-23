package ramvan.com.learnnotifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import ramvan.com.learnnotifications.fragments.MyFragment;

/**
 * Created by Mahajan on 21-Aug-16.
 */
public class NewActivity extends AppCompatActivity{

    private FrameLayout mContainer;
    private String showFrag;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        sharedpreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        showFrag = sharedpreferences.getString("Source", null);
        mContainer =  (FrameLayout) findViewById(R.id.fragment_container);
        mContainer.setVisibility(View.GONE);
       if(showFrag != null){
            mContainer.setVisibility(View.VISIBLE);
            Fragment mFrag = new MyFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , mFrag).commit();
        }
    }

    @Override
    public void onBackPressed() {
        editor.putString("Source", null).commit();
        super.onBackPressed();
        finish();
    }
}

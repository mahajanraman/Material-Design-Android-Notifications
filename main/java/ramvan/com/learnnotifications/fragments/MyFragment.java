package ramvan.com.learnnotifications.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ramvan.com.learnnotifications.R;

/**
 * Created by Mahajan on 23-Aug-16.
 */
public class MyFragment extends Fragment{

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment , container , false);
        return mView;
    }
}

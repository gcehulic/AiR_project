package hr.foi.air602.watchme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by markopc on 11/2/2016.
 */
public class SimpleFragment extends Fragment {

    private final String mText;
    private TextView mTextView;



    public SimpleFragment(String text) {
        mText = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.simple_fragment_layout, container, false);

        mTextView = (TextView)rootView.findViewById(R.id.simple_fragment_text);
        mTextView.setText(mText);

        return rootView;
    }
}

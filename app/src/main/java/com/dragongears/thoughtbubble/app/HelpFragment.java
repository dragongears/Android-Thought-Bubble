package com.dragongears.thoughtbubble.app;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    public static final HelpFragment newInstance(String message, String image)
    {
        HelpFragment f = new HelpFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        bdl.putString(EXTRA_IMAGE, image);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Resources resources = getResources();
        String message = getArguments().getString(EXTRA_MESSAGE);
        String image = getArguments().getString(EXTRA_IMAGE);
        View v = inflater.inflate(R.layout.help_fragment_layout, container, false);
        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
        messageTextView.setText(message);
        ImageView messageImageView = (ImageView)v.findViewById(R.id.imageView);
        Context context = container.getContext();
        int resId = resources.getIdentifier(image, "drawable", context.getPackageName());
        messageImageView.setImageResource(resId);
        return v;
    }

}
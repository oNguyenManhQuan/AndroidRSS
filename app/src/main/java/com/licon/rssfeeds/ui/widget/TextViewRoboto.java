package com.licon.rssfeeds.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.util.FontCollectionUtil;

public class TextViewRoboto extends TextView {

    public TextViewRoboto(Context context) {
        super(context);
        init(null);
    }

    public TextViewRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public TextViewRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontView);
            final String fontName = a.getString(R.styleable.FontView_font);
            if (TextUtils.isEmpty(fontName))
                setTypeface(FontCollectionUtil.get(FontCollectionUtil.FONT_ROBOTO_REGULAR, getContext()));
            else
                setTypeface(FontCollectionUtil.get(fontName, getContext()));
        } else {
            setTypeface(FontCollectionUtil.get(FontCollectionUtil.FONT_ROBOTO_REGULAR, getContext()));
        }
    }
}
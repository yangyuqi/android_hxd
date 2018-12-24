package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zhejiang.haoxiadan.ui.EmojiInputFilter;
import com.zhejiang.haoxiadan.util.ArrayUtil;


/**
 * Created by KK on 2016/10/25.
 */
public class NoEmojiEditText extends EditText {
    public NoEmojiEditText(Context context) {
        this(context, null);
    }

    public NoEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setFilters(ArrayUtil.appendFilter(this.getFilters(), new EmojiInputFilter()));
    }

//    public NoEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
//        this(context, attrs, defStyleAttr, 0);
//    }
//
//    public NoEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.setFilters(ArrayUtil.appendFilter(this.getFilters(), new EmojiInputFilter()));
//    }


}

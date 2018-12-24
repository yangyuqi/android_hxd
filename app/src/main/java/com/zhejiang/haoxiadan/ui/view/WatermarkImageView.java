package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by KK on 2017/12/14.
 */
public class WatermarkImageView extends ImageView {

    private String contentStr = "haoxiadan";

    public WatermarkImageView(Context context) {
        this(context, null, 0);
    }

    public WatermarkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatermarkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

//        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int imgWidth = canvas.getWidth();
        int imgHeight = canvas.getHeight();
        int textSize = imgHeight/10;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAlpha(100);
        paint.setTextSize(textSize);

        for(int i=0;i<5;i++){
            //TODO 调整位置
            Path path = new Path();
            path.moveTo(i*100,50);
            path.lineTo(i*100+100,100);

            canvas.drawTextOnPath(contentStr,path,0,0,paint);
        }

    }

}

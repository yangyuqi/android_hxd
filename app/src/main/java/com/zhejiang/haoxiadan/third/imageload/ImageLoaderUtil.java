package com.zhejiang.haoxiadan.third.imageload;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zhejiang.haoxiadan.MyApplication;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.third.network.Server;

/**
 * Created by TaoRan on 2016/4/22 0022.
 */
public class ImageLoaderUtil {

    /**
     * 加载自定义配置的图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImage(String url, ImageView imageView) {
        displayImageAndDefaultImg(url,imageView,R.mipmap.ic_launcher);
    }

    /**
     * 加载自定义配置的图片,指定不成功的显示的默认图片
     * @param url
     * @param imageView
     * @param def
     */
    public static void displayImageAndDefaultImg(String url, ImageView imageView,int def) {
        if(needAddFront(url)){
            url = Server.IMG_HOST + url;
        }else if(url !=null && url.startsWith("/storage")){
            url = "file://" + url;
        }
        Picasso.with(MyApplication.getContext())
                .load(url)
                .resize(400,400)
                .centerInside()
                .onlyScaleDown()
                .placeholder(def)
                .error(def)
                .into(imageView);
    }

    public static void displayFromSDCard(String path, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        if(!path.startsWith("file:")){
            path = "file:"+path;
        }
        Picasso.with(MyApplication.getContext())
                .load(path)
                .resize(400,400)
                .centerInside()
                .onlyScaleDown()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void displayFromUri(String uri, ImageView imageView) {
        Picasso.with(MyApplication.getContext())
                .load(uri)
                .resize(400,400)
                .centerInside()
                .onlyScaleDown()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    /**
     * 加载gif动图
     * @param url
     * @param imageView
     */
    public static void displayGifImage(String url, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /***
     * 清除图片缓存
     */

    public static void clearImageCache(){
    }

    public static boolean needAddFront(String url){
        if(null != url && !url.startsWith("http") && !url.startsWith("content:/") && !url.startsWith("file:")){
            return true;
        }
        return false;
    }

    private static Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {

            int targetWidth = 800;
            Log.i("K_K","source.getHeight()="+source.getHeight()+",source.getWidth()="+source.getWidth()+",targetWidth="+targetWidth);

            if(source.getWidth()==0){
                return source;
            }

            //如果图片小于设置的宽度，则返回原图
            if(source.getWidth()<targetWidth){
                return source;
            }else{
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }

        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };
}

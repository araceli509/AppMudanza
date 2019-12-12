package com.example.appmudanzas.mCloud;

import android.content.Context;
import android.widget.ImageView;

import com.example.appmudanzas.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).placeholder(R.drawable.img_base).into(img);

        }else {
            Picasso.with(c).load(R.drawable.img_base).into(img);
        }
    }
}

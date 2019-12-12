package com.example.appmudanzas.mCloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

public class CloudinaryClient {

    public static String getRoundCornerImage(String imageName){
        Cloudinary cloud=new Cloudinary(MyConfiguration.getMyConfigs());
        Transformation t= new Transformation();
        t.radius(60);
        t.height(320);
        t.width(320);
        String url= cloud.url().transformation(t).generate(imageName);
        return url;
    }
}

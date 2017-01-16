package com.example.vuduc.utils;

import android.app.Activity;
import android.content.Context;

import com.example.vuduc.apptuthien.R;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;

/**
 * Created by Brucelee Thanh on 16/01/2017.
 */

public class ImagePickerUtils {
    public static void showFileChooser(Activity activity, ArrayList<Image> lstImage, int PICK_IMAGE_REQUEST) {
        ImagePicker.create(activity)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Thư mục") // folder selection title
                .imageTitle("Chọn ảnh") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(99) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Chọn ảnh") // directory name for captured image  ("Camera" folder by default)
                .origin(lstImage) // original selected images, used in multi mode
                .start(PICK_IMAGE_REQUEST); // start image picker activity with request code
    }
}

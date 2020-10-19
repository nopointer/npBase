package npBase.BaseCommon.base.activity;

import android.content.Context;

import npBase.BaseCommon.extend.lzy.imagepicker.ImagePicker;
import npBase.BaseCommon.extend.lzy.imagepicker.view.CropImageView;
import npBase.BaseCommon.util.imageloader.GlideImageLoader;

/**
 * 裁剪参数工具
 */
public class CropParameterUtils {

    /**
     * 裁剪240的方屏
     */
    public static void useDial240(Context context) {
        ImagePicker imagePicker = init();
        useDial240Circle(context);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
    }

    /**
     * 裁剪240的圆屏
     */
    public static void useDial240Circle(Context context) {
        ImagePicker imagePicker = init();
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(dip2px(context,240));                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(dip2px(context,240));                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);

    }


    /**
     * 裁剪80*160的长屏
     */
    public static void useDial80_160(Context context) {
        ImagePicker imagePicker = init();
//        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(dip2px(context,80));                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(dip2px(context,160));                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }

    private static ImagePicker init() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader()); //设置图片加载器
        imagePicker.setShowCamera(true);                    //显示拍照按钮
        imagePicker.setCrop(true);                          //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                 //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);
        return imagePicker;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

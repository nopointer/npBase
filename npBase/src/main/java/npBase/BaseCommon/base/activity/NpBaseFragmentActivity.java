package npBase.BaseCommon.base.activity;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import npBase.BaseCommon.extend.lzy.imagepicker.ImagePicker;
import npBase.BaseCommon.extend.lzy.imagepicker.bean.ImageItem;
import npBase.BaseCommon.extend.lzy.imagepicker.ui.ImageGridActivity;
import npBase.BaseCommon.util.log.LogUtil;
import npPermission.nopointer.core.NpPermissionRequester;
import npPermission.nopointer.core.RequestPermissionInfo;
import npPermission.nopointer.core.callback.PermissionCallback;


/**
 * 基本共用的activity,集成权限申请
 */

public class NpBaseFragmentActivity extends FragmentActivity implements PermissionCallback {


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    /**
     * 是否是dark模式
     *
     * @return
     */
    protected boolean isDarkMode() {
        return false;
    }


    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().close(this);
        super.onDestroy();
    }


    /**
     * 跳转界面
     *
     * @param argClass
     */
    public void startActivity(Class<?> argClass) {
        startActivity(new Intent(this, argClass));
    }

    /**
     * 跳转后关闭界面
     *
     * @param argClass
     */
    public void startActivityAndFinish(Class<?> argClass) {
        startActivity(new Intent(this, argClass));
        this.finish();
    }

    /**
     * 跳转界面，加回调
     *
     * @param argClass
     * @param argRequestCode
     */
    public void startActivityForResult(Class<?> argClass, int argRequestCode) {
        startActivityForResult(new Intent(this, argClass), argRequestCode);
    }


    @Override
    public void finish() {
        releaseResource();
        super.finish();
    }

    /**
     * 在调用finish后会释放资源
     */
    public void releaseResource() {
    }


    private NpPermissionRequester npPermissionRequester = null;

    public void requestPermission(RequestPermissionInfo requestPermissionInfo) {
        if (requestPermissionInfo == null) {
            LogUtil.e("debug==没有需要请求的权限");
            return;
        }
        if (npPermissionRequester == null) {
            npPermissionRequester = new NpPermissionRequester(requestPermissionInfo);
        } else {
            npPermissionRequester.setPermissionInfo(requestPermissionInfo);
        }
        npPermissionRequester.requestPermission(this, this);
    }


    protected RequestPermissionInfo loadPermissionsConfig() {
        return null;
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (npPermissionRequester == null) return;
        npPermissionRequester.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }


    @Override
    public void onGetAllPermission() {
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (npPermissionRequester == null) return;
        RequestPermissionInfo requestPermissionInfo = npPermissionRequester.getPermissionInfo();
        if (requestPermissionInfo != null && !TextUtils.isEmpty(requestPermissionInfo.getAgainPermissionMessage())) {
            npPermissionRequester.checkDeniedPermissionsNeverAskAgain(this, perms);
        }
    }


    /**
     * 打开相机
     */
    public void camera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .enableCrop(true)
                .cropWH(300, 300)
                .scaleEnabled(true)
                .freeStyleCropEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 打开相机
     */
    public void cameraCircle() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .enableCrop(true)
                .cropWH(300, 300)
                .scaleEnabled(true)
                .freeStyleCropEnabled(true)
                .circleDimmedLayer(true)//圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isDragFrame(false)//是否可拖动裁剪框(固定)
                .cropWH(300, 300)
                .scaleEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 打开相册
     */
    public void galleryMultiple() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(false)
                .maxSelectNum(9)
                .enableCrop(false)
                .compress(true)
                .rotateEnabled(true)
                .scaleEnabled(true)
                .selectionMode(PictureConfig.MULTIPLE)
                .freeStyleCropEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 打开相册
     */
    public void gallery() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(false)
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(true)
                .rotateEnabled(true)
                .isSingleDirectReturn(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .setCircleDimmedColor(0xFF000000)
                .setCircleDimmedBorderColor(0xFFFFFFFF)
//                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .isDragFrame(false)//是否可拖动裁剪框(固定)
                .cropWH(300, 300)
                .scaleEnabled(true)

                .freeStyleCropEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    /**
     * 打开相册
     */
    public void galleryCircle() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(false)
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(true)
                .rotateEnabled(true)
                .isSingleDirectReturn(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .setCircleDimmedColor(0xFF000000)
                .setCircleDimmedBorderColor(0xFFFFFFFF)
                .circleDimmedLayer(true)//圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isDragFrame(false)//是否可拖动裁剪框(固定)
                .cropWH(300, 300)
                .scaleEnabled(false)
                .freeStyleCropEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 打开相册
     */
    public void gallery(int width, int height) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(false)
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(true)
                .rotateEnabled(true)
                .isSingleDirectReturn(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .setCircleDimmedColor(0xFF000000)
                .setCircleDimmedBorderColor(0xFFFFFFFF)
//                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .isDragFrame(false)//是否可拖动裁剪框(固定)
                .cropWH(width, height)
                .scaleEnabled(true)

                .freeStyleCropEnabled(true)
                .withAspectRatio(1, 1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                onImageSelectWithPicker(images);
//                if (images != null) {
//                    selImageList.addAll(images);
//                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                onImageSelectWithPicker(images);
//                if (images != null) {
//                    selImageList.clear();
//                    selImageList.addAll(images);
//                    adapter.setImages(selImageList);
//                }
            }
        } else if (resultCode == RESULT_OK) {
            onImageSelect(PictureSelector.obtainMultipleResult(data));
        }
    }


    /**
     * 预览
     *
     * @param position
     * @param argLocalMedia
     */
    public void externalPicturePreview(int position, List<LocalMedia> argLocalMedia) {
        PictureSelector.create(this).externalPicturePreview(position, argLocalMedia, 1);
    }


    protected void onImageSelect(List<LocalMedia> selectList) {

    }

    /**
     * 后面集成的图片选择器
     *
     * @param imageItemList
     */
    protected void onImageSelectWithPicker(List<ImageItem> imageItemList) {

    }


    /**
     * 打开相册并裁剪
     */
    public void cropWithGallery() {
        //打开选择,本次允许选择的数量
        Intent intent1 = new Intent(this, ImageGridActivity.class);
        /* 如果需要进入选择的时候显示已经选中的图片，
         * 详情请查看ImagePickerActivity
         * */
//        intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    /**
     * 打开相机并裁剪
     */
    public void cropCamera() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }


}

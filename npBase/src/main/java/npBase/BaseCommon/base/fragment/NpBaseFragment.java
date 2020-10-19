package npBase.BaseCommon.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import npBase.BaseCommon.util.log.LogUtil;
import npPermission.nopointer.core.NpPermissionRequester;
import npPermission.nopointer.core.RequestPermissionInfo;
import npPermission.nopointer.core.callback.PermissionCallback;

/**
 * 基本共用的afragment,集成权限申请
 */

public abstract class NpBaseFragment extends Fragment implements PermissionCallback {
    //上下文
    public Context context;

    //根布局
    protected View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(loadLayout(), container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity();
        initView();
        return rootView;
    }


    protected abstract int loadLayout();


    protected abstract void initView();


    /**
     * 跳转界面
     *
     * @param argClass
     */
    public void startActivity(Class<?> argClass) {
        startActivity(new Intent(getActivity(), argClass));
    }

    /**
     * 跳转后关闭界面
     *
     * @param argClass
     */
    public void startActivityAndFinish(Class<?> argClass) {
        startActivity(new Intent(getActivity(), argClass));
        getActivity().finish();
    }

    /**
     * 跳转界面，加回调
     *
     * @param argClass
     * @param argRequestCode
     */
    public void startActivityForResult(Class<?> argClass, int argRequestCode) {
        startActivityForResult(new Intent(getActivity(), argClass), argRequestCode);
    }


    private NpPermissionRequester npPermissionRequester = null;

    public void requestPermission(RequestPermissionInfo requestPermissionInfo) {

        if (requestPermissionInfo == null) {
            LogUtil.e("权限列表为空，不请求");
            return;
        }
        if (npPermissionRequester == null) {
            npPermissionRequester = new NpPermissionRequester(requestPermissionInfo);
        }else {
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
        RequestPermissionInfo requestPermissionInfo = npPermissionRequester.getPermissionInfo();
        if (requestPermissionInfo != null && !TextUtils.isEmpty(requestPermissionInfo.getAgainPermissionMessage())) {
            npPermissionRequester.checkDeniedPermissionsNeverAskAgain(this, perms);
        }
    }

}

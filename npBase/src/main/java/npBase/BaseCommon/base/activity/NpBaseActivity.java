package npBase.BaseCommon.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.ButterKnife;
import npPermission.nopointer.core.callback.PermissionCallback;


/**
 * 基本共用的activity,集成权限申请
 */

public abstract class NpBaseActivity extends NpBaseFragmentActivity implements PermissionCallback {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加到页面栈
        ActivityManager.getInstance().putActivity(this);
        //沉浸式
        QMUIStatusBarHelper.translucent(this);
        if (isDarkMode()) {
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        } else {
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }
        afterCreateBeforeInitView();
        //加载布局
        setContentView(loadLayout());
        ButterKnife.bind(this);
        //初始化组件
        initView();

        afterInitView();
    }

    /**
     * 创建之后，初始化view之前
     */
    protected void afterCreateBeforeInitView() {

    }

    protected void afterInitView(){

    }

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int loadLayout();

    /**
     * 初始化组件
     */
    protected abstract void initView();



}

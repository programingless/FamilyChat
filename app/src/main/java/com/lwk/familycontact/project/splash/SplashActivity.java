package com.lwk.familycontact.project.splash;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.lib.base.utils.AppUtil;
import com.lwk.familycontact.R;
import com.lwk.familycontact.base.FCBaseActivity;
import com.lwk.familycontact.im.HxSdkHelper;
import com.lwk.familycontact.project.MainActivity;
import com.lwk.familycontact.project.login.view.LoginActivity;

/**
 * 欢迎界面
 */
public class SplashActivity extends FCBaseActivity
{
    private static final int ANIM_DURATION = 2500;
    private AlphaAnimation mAnimation;
    private View mLlContent;
    private TextView mTvAuthorDesc;
    private long mLoginStartTime, mLoginEndTime, mLoginTime;

    @Override
    protected int setContentViewId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI()
    {
        mLlContent = findView(R.id.ll_splash_content);
        TextView tvVersionName = findView(R.id.tv_splash_version_name);
        tvVersionName.setText(AppUtil.getAppVersionName(this));
        mTvAuthorDesc = findView(R.id.tv_splash_author_desc);
    }

    @Override
    protected void initData()
    {
        super.initData();
        mAnimation = new AlphaAnimation(0f, 1f);
        mAnimation.setDuration(ANIM_DURATION);
        mAnimation.setFillAfter(true);
        mLlContent.startAnimation(mAnimation);
        mTvAuthorDesc.startAnimation(mAnimation);

        mLoginStartTime = System.currentTimeMillis();
        //自动登录
        if (HxSdkHelper.getInstance().canAutoLogin())
        {
            HxSdkHelper.getInstance().loadHxLocalData();
            mLoginEndTime = System.currentTimeMillis();
            mLoginTime = mLoginEndTime - mLoginStartTime;
            if (mLoginTime > ANIM_DURATION)
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else
            {
                mMainHanlder.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }, ANIM_DURATION - mLoginTime);
            }
        }
        //手动登录
        else
        {
            mLoginEndTime = System.currentTimeMillis();
            mLoginTime = mLoginEndTime - mLoginStartTime;
            mMainHanlder.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, ANIM_DURATION - mLoginTime);
        }
    }


    @Override
    public void onBackPressed()
    {
        //不准后退
    }

    @Override
    protected void onClick(int id, View v)
    {

    }
}

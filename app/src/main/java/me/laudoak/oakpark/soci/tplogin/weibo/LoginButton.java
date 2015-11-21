/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.laudoak.oakpark.soci.tplogin.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.utils.LogUtil;

public class LoginButton extends ImageView implements OnClickListener {
    private static final String TAG = "LoginButton";
    
	private Context mContext;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private WeiboAuthListener mAuthListener;

	public LoginButton(Context context) {
		this(context, null);
	}

	public LoginButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public LoginButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

    public void setWeiboAuthInfo(AuthInfo authInfo, WeiboAuthListener authListener) {
		mAuthInfo = authInfo;
		mAuthListener = authListener;
	}

    public void setWeiboAuthInfo(String appKey, String redirectUrl, String scope, WeiboAuthListener authListener) {
		mAuthInfo = new AuthInfo(mContext, appKey, redirectUrl, scope);
		mAuthListener = authListener;
	}


	@Override
	public void onClick(View v) {

		if (null == mSsoHandler && mAuthInfo != null) {
			mSsoHandler = new SsoHandler((Activity)mContext, mAuthInfo);
		}
		
        if (mSsoHandler != null) {
            mSsoHandler.authorize(mAuthListener);
        } else {
            LogUtil.e(TAG, "Please setWeiboAuthInfo(...) for first");
        }
	}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    private void initialize(Context context) {
    	mContext = context;
    	setOnClickListener(this);
    }
}

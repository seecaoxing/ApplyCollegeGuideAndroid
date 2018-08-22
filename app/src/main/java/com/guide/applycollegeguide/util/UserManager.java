package com.guide.applycollegeguide.util;

import android.content.Context;
import android.text.TextUtils;

import com.guide.applycollegeguide.bean.User;
import com.xyz.core.util.SharedPreferencesUtils;

import static com.xyz.core.util.SharedPreferencesUtils.USER_AVATAR;
import static com.xyz.core.util.SharedPreferencesUtils.USER_EMAIL;
import static com.xyz.core.util.SharedPreferencesUtils.USER_PHONE;
import static com.xyz.core.util.SharedPreferencesUtils.USER_USERID;
import static com.xyz.core.util.SharedPreferencesUtils.USER_USERNAME;


/**
 * Created by bjcaoxing on 2018/2/6.
 */

public class UserManager {


    public static void setUserInfo(Context context, User user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.getUserId())) {
                SharedPreferencesUtils.setParam(context, USER_USERID, user.getUserId());
                SharedPreferencesUtils.setParam(context, USER_USERNAME, user.getUsername());
                SharedPreferencesUtils.setParam(context, USER_EMAIL, user.getEmail());
                SharedPreferencesUtils.setParam(context, USER_PHONE, user.getPhone());
                SharedPreferencesUtils.setParam(context, USER_AVATAR, user.getAvatar());
            }
        } else {
            SharedPreferencesUtils.setParam(context, USER_USERID, "");
            SharedPreferencesUtils.setParam(context, USER_USERNAME, "");
            SharedPreferencesUtils.setParam(context, USER_EMAIL, "");
            SharedPreferencesUtils.setParam(context, USER_PHONE, "");
            SharedPreferencesUtils.setParam(context, USER_AVATAR, "");
        }
    }

    public static User getUserInfo(Context context) {

        String userid = (String) SharedPreferencesUtils.getParam(context, USER_USERID, "");
        String username = (String) SharedPreferencesUtils.getParam(context, USER_USERNAME, "");
        String email = (String) SharedPreferencesUtils.getParam(context, USER_EMAIL, "");
        String phone = (String) SharedPreferencesUtils.getParam(context, USER_PHONE, "");
        String avatar = (String) SharedPreferencesUtils.getParam(context, USER_AVATAR, "");
        if (!TextUtils.isEmpty(userid)) {
            User user = new User();
            user.setUserId(userid);
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAvatar(avatar);
            return user;
        }
        return null;
    }

    public static String getUserId(Context context){
       return (String) SharedPreferencesUtils.getParam(context, USER_USERID, "");
    }
}

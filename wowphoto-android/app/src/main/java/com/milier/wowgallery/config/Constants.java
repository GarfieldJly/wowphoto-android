package com.milier.wowgallery.config;

/**
 * Created by konie on 16-8-22.
 */
public class Constants {

    /**
     * 传输TOKEN的header
     */
    public final static String HEADER_TOKEN_NAME = "X-Auth-Token";

    /**
     * 设备信息的header
     */
    public final static String HEADER_DEVIECE_INFO = "X-DEVICE_INFO";


    /**
     * 存储TOKEN的SP名称
     */
    public final static String SP_NAME_STORE_TOKEN = "SP_URIEL_TOKEN";

    /**
     * 存储TOKEN的SP key
     */
    public final static String SP_KEY_STORE_TOKEN = "URIEL_TOKEN";

    /**
     * 网络连接
     */
    public static final String NET_UNCONNECTED = "NET_UNCONNECTED";

    public static final String STOP_REFRESH = "STOP_REFRESH";

    public static final String LOAD_MORE = "LOAD_MOER";

    public static final String LOAD_REFRESH = "LOAD_REFRESH";

    public static final int PAGE_SIZE = 10;

//    public static final String OSS_BUCKET = "milier-raphae"; //测试bucket
    public static final String OSS_BUCKET = "wowphoto"; // 正式bucket
//    public static final String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com"; //测试
    public static final String OSS_ENDPOINT = "http://oss-ap-southeast-1.aliyuncs.com"; //正式

    /**
     * 用户登录标识
     */
    public static final String SIGN_IN = "SIGN_IN";

    /**
     * 用户登出标识
     */
    public static final String SIGN_OUT = "SIGN_OUT";

    /**
     * 用户id
     */
    public static final String USER_ID = "USER_ID";

    /**
     * 头像地址
     */
    public static final String PORTRAIT_URL = "PORTRAIT_URL";

    /**
     * 名称
     */
    public static final String NAME = "NAME";
    /**
     * vip 剩余天数
     */
    public static final String  VIP_EXPIRE_DAY = "VIP_EXPIRE_DAY";

    /**
     * 图片模糊
     */
    public static final String BLUR = "BLUR";

    public static final String IS_ACTIVATE = "IS_ACTIVATE";

    public static final String UMENG_KEY = "58844ba95312dd4ce600144e";

    public static final String slider_menu_click = "slider_menu_click";  //侧滑栏4个按钮点击数

    public static final String account_vip_click = "account_vip_click"; //4档会员每档的点击数

    public static final String image_download_click = "image_download_click"; //VIP下载按钮点击数总和

    public static final String like_btn_click = "like_btn_click"; //所有相册喜欢的点击数总和

    public static final String IS_SHOW_BENEFIT = "is_show_benefit"; //新手福利

    public static final String IS_SHOW_BENEFIT_FLAG = "is_show_benefit_flag"; //新手福利标识

    public static final String Vungle_App_ID = "591aae4e729f4c037300136b";

    public static final String UNITY_AD_ID = "1419007";

    public static final String APPSFLYER_ID = "f6e5wS4FCsqnFCzjQbmGtn";

    //AppsFlyer的事件
    public static final String ON_LINE_TIME = "on_line_time"; //用户在线时长

    public static final String LOOK_PHOTO_ALBUM_COUNT = "look_photo_album_count"; //用户浏览的相册数

    public static final String LOOK_PHOTO_ALBUM_TIME = "look_photo_album_time"; //用户浏览相册的时间

    public static final String PAY_SUCCESS = "pay_success";//用户从哪个相册付款

    //记录进入应用的时间
    public static long EnterTime = -1;
    //浏览相册的数量
    public static long LOOK__ALBUM_COUNT = 0;

    public static long LOOK_ALBUM_TIME = -1; //每个相册浏览时间




}

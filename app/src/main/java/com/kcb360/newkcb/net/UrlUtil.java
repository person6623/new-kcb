package com.kcb360.newkcb.net;

/**
 * Created by xinshichao on 2017/5/15.
 * <p>
 * 接口
 */

public class UrlUtil {

//    // 邱富成本地服务器
//    public static final String URL = "http://192.168.0.114:8080/rentalcar";

//    // 肖响本地服务器
//    public static final String URL = "http://192.168.0.164:8080/rentalcar";

    // 测试服
    public static final String URL = "http://cba360.cn/rentalcar";

//    // 正式服
//    public static final String URL = "http://cba360.com/rentalcar";

    /****************************upDate***********************
     * 版本更新相关
     */
    // 更新文档获取
    public static final String VERSION_UPDATE = "http://cba360.com/newapp/app/";

    /****************************image url********************
     * 图片url
     */
    // 首页轮播
    public static final String HOME_PAGE_IMAGE_ONE = "http://cba360.com/clientApp/transfer/assets/img/banner-1.png";
    public static final String HOME_PAGE_IMAGE_TWO = "http://cba360.com/clientApp/transfer/assets/img/banner-2.png";
    public static final String HOME_PAGE_IMAGE_THREE = "http://cba360.com/clientApp/transfer/assets/img/banner-3.png";


    /****************************通用***************
     *
     */
    // 登录
    public static final String APP_LOGIN = URL + "/index/appLogin";

    // 退出登录
    public static final String APP_QUIT = URL + "/index/quitPhone";

    // 忘记密码
    public static final String GET_PHONE_CODE = URL + "";

    // 忘记密码修改密码
    public static final String BACK_PWD = URL + "/index/backPwd";

    // 注册
    public static final String REG_CUSTOMER = URL + "/index/regCustomer";

    // 注册时获取验证码
    public static final String GETVAL_CODE = URL + "/commons/getvalCode";

    // 发布订单(询价)
    public static final String PU_ASK_ORDER = URL + "/lessee/puAskOrder";

    // 发布空车
    public static final String ADD_EMPTY_CAR = URL + "/lessee/addEmptyCar";

    // 查找空车
    public static final String FIND_EMPTY_CARS = URL + "/lessee/findEmptyCars";

    // 用户订单
    public static final String GET_ORDER_LIST_NEW = URL + "/lessee/getOrderListNew";

    // (询价)计算所需数据(询价第一请求接口)
    public static final String FIND_TOUR = URL + "/backstage/datamanage/findTour";

    // 是否需要发票(询价最后请求接口)
    public static final String VI_PRICE = URL + "/commons/VIPrice";

    // 询价/成团/包车设置 座位数列表
    public static final String SEATS_LIST = URL + "/backstage/datamanage/seatsList";

    // 获取城市/特殊景点
    public static final String GET_ALL_CITY = URL + "/commons/getAllCity";

    // 不跑区域
    public static final String NGA_LIST = URL + "/backstage/datamanage/ngaList";

    // 获取省份
    public static final String GET_PROVINCE = URL + "/carTeam/getProvince";

    // 包车价格设置
    public static final String AD_UPDE_TOUC = URL + "/backstage/datamanage/adupdeTouc";

    // 顺风车订单
    public static final String FREE_ORDER_LIST = URL + "/motorFree/freeOrderList";

    // 接送机订单
    public static final String GET_PICKUP_ORDER_LIST = URL + "/motorTransfer/getPickupOrderList";

    // 包车价格设置列表
    public static final String TOUC_LIST = URL + "/backstage/datamanage/toucList";

    // 车辆信息获取
    public static final String FIND_MY_CAR_LIST = URL + "/lessor/findMyCarList";

    // 钱包
    public static final String MEMBER_WALLET = URL + "/wallet/memberWallet";

    // 提取提成/押金/定金到余额
    public static final String TAKE_ROYALTY = URL + "/wallet/takeRoyalty";

    // 领取红包(优惠券)
    public static final String DISCOUNT_LIST = URL + "/motorFinance/discountList";

    // 提现记录
    public static final String DRAW_CASH_LIST = URL + "/wallet/drawCashList";

    // 交易记录
    public static final String TRADE_RECORD_LIST = URL + "/wallet/tradeRecordList";

    // 添加银行卡
    public static final String ADD_BANK_CARD = URL + "/wallet/addBankCard";

    // 银行卡列表
    public static final String GET_BANK_LIST = URL + "/wallet/getBankList";

    // 删除银行卡
    public static final String DEL_BANK_CARD = URL + "/wallet/delBankCard";

    // 提现
    public static final String SUB_DRAW_CASH = URL + "/wallet/subDrawCash";
}

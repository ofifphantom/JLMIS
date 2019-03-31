package com.jl.mis.utils;

public class Constants {

	public static final boolean SUCCESS = true;

	public static final boolean FAILURE = false;

	public static final String OPERATION_SUCCESS_MSG = "操作成功!";
	
	public static final String OPERATION_FAILURE_MSG = "操作失败!";

	public static final String SAVE_FAILURE_MSG = "信息保存失败!";
	
	public static final String SAVE_SUCCESS_MSG = "信息保存成功!";

	public static final String UPDATE_SUCCESS_MSG = "信息修改成功!";

	public static final String UPDATE_FAILURE_MSG = "信息修改失败!";

	public static final String DELE_SUCCESS_MSG = "信息删除成功!";

	public static final String DELE_FAILURE_MSG = "信息删除失败!";
	
	public static final String EFFECT_SUCCESS_MSG = "操作成功，已生效!";

	public static final String EFFECT_FAILURE_MSG = "操作失败，请确认后重新操作!";
	
	public static final String SESSION_TIMEOUT_MSG = "登录已失效,请重新登录!";
	
	public static final String OPERATE_INSERT = "添加";
	
	public static final String OPERATE_UPDATE = "修改";
	
	public static final String OPERATE_DELETE = "删除";
	
	public static final String NO_DATA_EXPORT = "无数据";
	
	public static final String AD_SALE = "火热促销";
	
	public static final String AD_NEW = "新品上架";
	
	public static final String AD_HOT = "测试用热卖";
	
	public static final String AD_PRESALE = "爆品预售";
	
	
	
	//管理员标志
	public static final int ADMIN_FLAG = 0;
	//用户标志
	public static final int USER_FLAG = 1;
	
	
	//用户组_超级管理员
	public static final int USER_GROUP_SUPER_ADMINISTRATOR = 0;
	//用户组_管理员
	public static final int USER_GROUP_ADMINISTRATOR = 1;
	//用户组_操作员
	public static final int USER_GROUP_OPERATOR = 2;
	
	
	
	
//流水号前缀
	
	//优惠券
	public static final String NO_YHQ = "COU";
	//一级分类
	public static final String NO_ONECLASSIFICATION = "ONECLA";
	//二级分类
	public static final String NO_TWOCLASSIFICATION = "TWOCLA";
	//活动
	public static final String NO_HD = "ACT";
	//管理员
	public static final String NO_ADMIN = "ADMIN";
	//用户
	public static final String NO_USER = "USER";
	//商品
	public static final String NO_GOODS = "GOODS";
	//广告
	public static final String NO_ADVERTISEMENT = "AD";
	//订单
	public static final String NO_ORDER = "ORDER";
	//发票
	public static final String NO_INVOICE = "INV";
	
	
//Code码
	
	//优惠券
	 public static final int CODE_YHQ = 1;
	//一级分类
	 public static final int CODE_ONECLASSIFICATION = 2;
	//二级分类
	 public static final int CODE_TWOCLASSIFICATION = 3;
	//活动
	public static final int CODE_HD = 4;
	//管理员
	public static final int CODE_ADMIN = 5;
	//商品
	public static final int CODE_GOODS = 6;
	//广告
	public static final int CODE_ADVERTISEMENT = 7;
	//用户
	public static final int CODE_USER = 8;
	//订单
	public static final int CODE_ORDER = 9;
	//发票
	public static final int CODE_INVOICE = 10;
	
	
	
    //优惠券状态
    	//正常
    public static final int STATE_YHQ_NORMAL = 0;
    
    	//失效
    public static final int STATE_YHQ_INVALID = 1;
    
    	//撤回
    public static final int STATE_YHQ_BACKOUT = 2;
    
 		//未送审
    public static final int STATE_YHQ_NOSEND = 3;
    
    	//待审核
    public static final int STATE_YHQ_CHECKING = 4;
    
    	//已驳回
    public static final int STATE_YHQ_REFUSE = 5;
    
    
    
    
    //活动状态
		//未送审
	public static final int STATE_HD_NOTSUBMIT = 0;
	
		//审核中
	public static final int STATE_HD_CHECKING = 1;
	
		//审核通过
	public static final int STATE_HD_PASS = 2;
	
		//审核未通过
	public static final int STATE_HD_REFUSED = 3;
	
		//上线中
	public static final int STATE_HD_ONLINE = 4;
		
		//已下线
	public static final int STATE_HD_OFFLINE = 5;
	
		//已失效
	public static final int STATE_HD_INVALID = 6;
	
		//低价审核中
	public static final int STATE_HD_LOWPRICE_CHECKING = 7;
	
	
	
	 //活动类型
		//折扣
	public static final int TYPE_HD_ZK = 0;
	
		//团购
	public static final int TYPE_HD_TG = 1;
	
		//秒杀
	public static final int TYPE_HD_MS = 2;
	
		//立减
	public static final int TYPE_HD_LJ = 3;
	
		//满减
	public static final int TYPE_HD_MJ = 4;
	
		//预售
	public static final int TYPE_HD_YS = 5;
	
	
	
	
	

	//广告类型  0：开屏，1：首页广告，2：分类广告，3：限时抢购，4：热门分类
		//开屏
	public static final int TYPE_AD_KP = 0;
	
		//首页广告
	public static final int TYPE_AD_SY = 1;
	
		//分类广告
	public static final int TYPE_AD_FL = 2;
	
		//限时抢购
	public static final int TYPE_AD_QG = 3;
	
		//热门分类
	public static final int TYPE_AD_RM = 4;
	
	
	

    
    
  //日志操作类型   0：商品配置  1：活动配置  2：广告配置  3：VIP管理  4：订单管理  5：审核  6：库存  7：用户管理
    
    public static final int TYPE_LOG_GOODS = 0;
    
    public static final int TYPE_LOG_ACTIVITY = 1;
    
    public static final int TYPE_LOG_ADVERTISE = 2;
    
    public static final int TYPE_LOG_VIP = 3;
    
    public static final int TYPE_LOG_ORDER = 4;
    
    public static final int TYPE_LOG_CHECK = 5;
    
    public static final int TYPE_LOG_STOCK = 6;
    
    public static final int TYPE_LOG_USERMANAGER = 7;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

package com.linrun.ssm.constant;

/**
 * 全局常量类
 * @author 骆焕
 * @createDate 2019-11-14
 */
public class GlobalConst {

    // 项目名: 在登录完成后给其赋值
    public static String PROJECT_NAME = "";

    // 未注册
    public final static String UN_REGISTER = "unRegister";

    // 默认菜单的链接地址
    public final static String DEFAULT_MENU_URL = "/menu/toInConstruction";

    // 会话名称
    public final static String SESSION_USER = "userSession";
    // 临时会话名称
    public final static String SESSION_USER_TEMP = "userSessionTemp";
    // 输入信息
    public final static String INPUT_INFO = "inputInfo";
    // 错误消息
    public final static String ERROR_MSG = "errorMsg";
    // 操作消息
    public final static String OPERATE_MSG = "operateMsg";

    // 操作类型
    public final static String OPTION_TYPE = "optionType";
    // 操作名称
    public final static String OPTION_NAME = "optionName";

    // 换行符
    public final static String NEW_LINE_SEPARATOR = "\r\n";

    // 电脑注册码
    public final static String PC_REGISTER_CODE = "pcRegisterCode";

    // 报障附件(Web端)存储路径
    public final static String FAULTREPORT_FILE_WEB_PATH = "D:\\fileServer\\faultReportWeb";
    // APP版本管理存储路径
    public final static String APP_VERSION_PATH = "D:\\appVersion";

    // 附件文件连接符号
    public final static String ATTACH_FILE_CONNECTOR = ",";

    // 数据源定义
    public final static String DATASOURCE_BUSINESS = "businessDataSource"; // 业务数据库
    public final static String DATASOURCE_MAP = "mapDataSource"; // 地图数据库
    public final static String DATASOURCE_STATE = "stateDataSource"; // 地图状态数据库

    // 按钮权限定义
    public final static char RIGHT_BTN_ENABLED = '0'; // 不可用状态
    public final static char RIGHT_BTN_UNCHECKED = '1'; // 未选中状态
    public final static char RIGHT_BTN_CHECKED = '2'; // 选中状态

    // 是否(int型)
    public final static int INT_YES = 1;
    public final static int INT_NO = 0;

    // 顶级使用单位编码
    public final static String ROOT_EMPLOY_CODE="99";

    // 初始化用户信息
    public final static String INIT_USER_ACCOUNT = "TBW3000";
    public final static String INIT_USER_PASSWORD = "Init123";
    public final static String INIT_USER_ID = "-1";
    public final static int INIT_ROLE_ID = -1;
    public final static int INIT_USER_LEVEL = -1;
    public final static String INIT_USER_NAME = "初始化用户";

    // 格式化信息
    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 日期时间格式
    public final static String FORMAT_DATE = "yyyy-MM-dd"; // 日期格式
    public final static String FORMAT_TIME = "HH:mm:ss"; // 时间格式 (hh表示12小时制, HH表示24小时制)

    public final static Integer PAGEHELPER_PAGESIZE = 20;   // 分页每页显示记录条数
    public final static String MODULE_ID = "moduleId";      // 打开模块主界面请求参数中获取模块ID的参数名
    public final static String MODULE_NAME = "moduleName";  // 打开模块主界面请求参数中获取模块名称的参数名

    // 记录状态
    public final static String STS_COPYTO = "COPYTO";
    public final static String STS_READY ="READY";
    public final static String STS_RREADY = "RREADY";
    public final static String STS_COMMITED = "COMMITED";
    public final static String STS_RCOMMITED = "RCOMMITED";
    public final static String STS_POSTED = "POSTED";

    // 操作类型
    public final static String OPERATION_TYPE_ADD = "1"; // 增加操作
    public final static String OPERATION_TYPE_EDIT = "2"; // 编辑操作
    public final static String OPERATION_TYPE_DELETE = "3"; // 删除操作
    public final static String OPERATION_TYPE_COPYTO = "4"; // 复制操作
    public final static String OPERATION_TYPE_SAVEAS = "5"; // 另存为操作
    public final static String OPERATION_TYPE_COMMIT = "6"; // 提交操作
    public final static String OPERATION_TYPE_REVIEW = "7"; // 审核操作
    public final static String OPERATION_TYPE_REWORK = "8"; // 返工操作
    public final static String OPERATION_TYPE_MAINTAIN = "9"; // 维护操作
    public final static String OPERATION_TYPE_RECOVER = "10"; // 恢复操作
    public final static String OPERATION_TYPE_UPDATE_PWD = "42"; // 修改密码操作
    public final static String OPERATION_TYPE_AUTOASSIGN = "43"; // 自动派工操作
    public final static String OPERATION_TYPE_SORT = "99"; // 恢复操作

    // 设备模型代码
    public final static int MODEL_PARAM_FATHERID = 128;
    // 读取129参数表
    public final static int MODEL_PARAM_DATATYPE = 129;
    //  设备模型数据状态  新增为 0  审核为1
    public final static int MODEL_PARAM_STS = 0;

    //Excel导出时临时文件保存路径
    public final static String EXPORT_EXCEL_ROOT_PATH = "/export/excel/";

    //csaasdkfjakdsfj kl

    //帧头标识
    public final static int FRAME_HEADER = 0x2C2C2C2C;
    //信号控制机
    public final static byte DEVICE_SIGNAL = 0x10;
    //智能机柜
    public final static byte DEVICE_CABINET = 0x11;
    //上位机（平台管理系统）
    public final static byte DEVICE_UPMMS = 0x20;
    //UI
    public final static byte DEVICE_UI = 0x30;


    public static final String ffmpegpath = "G:/ffmpeg/bin/ffmpeg.exe";		// ffmpeg工具安装位置

    public static final String videofolder = "G:/websiteimages/temp/"; 	// 需要被转换格式的视频目录
    public static final String videoRealPath = "G:/websiteimages/temp/"; 	// 需要被截图的视频目录

    public static final String targetfolder = "G:/websiteimages/finshvideo/"; // 转码后视频保存的目录
    public static final String imageRealPath = "G:/websiteimages/finshimg/"; // 截图的存放目录
}
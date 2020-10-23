package com.linrun.ssm.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具包
 * @author 骆焕
 * @createDate 2019-11-14
 */
public class RegUtils {

    public final static String NUMBER_ONLY = "^[0-9]*$"; // 只允许数字
    public final static String NUMBER_ON_FIXED = "^\\d{<0>}$"; // 固定n位数字
    public final static String NUMBER_AT_LEAST = "^\\d{<0>,}$"; // 至少n位数字
    public final static String NUMBER_IN_RANGE = "^\\d{<0>,<1>}$"; // 至少n位,至多m位数字
    public final static String NUMBER_IS_POS_REAL = "^\\d+(.\\d)?$"; // 正实数
    public final static String NUMBER_IS_REAL = "^(-?\\d+)(.\\d)?$"; // 实数

    public final static String NUMBER_IS_BOOLEAN = "^[0-1]$"; // 只允许为布尔值

    public final static String NUMBER_LETTER_ONLY = "^[A-Za-z0-9]+$"; // 只允许数字和英文字符

    // 账号是否合法: 字母开头,允许3-12位字节,可以包含下划线
    public final static String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{2,11}$";
    // 密码是否合法: 4-15位数字、字母、下划线组成
    public final static String PASSWORD = "^\\w{4,15}$";
    // Email地址
    public final static String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    // 身份证号: 15位数字或18位(前17位为数字后面1位为数字或罗马字符X)
    public final static String ID_CARD = "^\\d{15}|(\\d{17}([0-9]|X))$";
    // 手机号码: 13开头、145、147、15或18开头(第三位不为4)的11位数字
    // public final static String MOBILE_PHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[6|7]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    public final static String MOBILE_PHONE = "^\\d{11}$";
    // 电话号码: 前头3位后面8位或者开头4位后面7位
    public final static String TELEPHONE = "^\\d{3}-\\d{8}|\\d{4}-\\d{7}$";
    // Mac地址: 6组十六进制字符加连接符组成
    public final static String MAC_ADDRESS = "^([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}$";
    // 邮政编码: 6位数字
    public final static String ZIPCODE = "^[1-9]\\d{5}$";
    // IPV4
    public final static String IPV4 =
            "^((\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.){3}(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))$";
    // IPV6
    public final static String IPV6 = "^(([\\da-fA-F]{1,4}):){7}([\\da-fA-F]{1,4})$";

    // 日期格式: yyyy-MM-dd
    public final static String DATE_FORMAT = "^\\d{4}-(0?[1-9]|1[0-2])-((0?[1-9])|((1|2)[0-9])|30|31)$";
    // 时间格式(24小时制): HH:mm:ss
    public final static String TIME_FORMAT = "^((0|1)?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$";
    // 时间日期格式: yyyy-MM-dd HH:mm:ss
    public final static String DATE_TIME_FORMAT =
            "^\\d{4}-(0?[1-9]|1[0-2])-((0?[1-9])|((1|2)[0-9])|30|31) ((0|1)?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$";

    /**
     * 验证规则
     * @param content 内容
     * @param regStr 正则表达式
     * @param nullFlag 非空验证
     * @param args 参数
     * @return 错误字符串
     */
    public static String validateRule(String content, String regStr, boolean nullFlag, int ...args) {
        String errorStr = null;
        String regStr4Validation = regStr;

        // 非空验证
        if (nullFlag) {
            if (StringUtils.isEmpty(content)) {
                return "为空";
            }
        }
        // 可为空(即可选项),内容为空时,验证通过!
        else
        {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
        }

        // 如果只做非空验证,正则表达式为空,则直接返回
        if (StringUtils.isEmpty(regStr))
            return null;

        // 替换参数
        if (args != null && args.length >0) {
            for (int i = 0, j = args.length; i <j; i++) {
                String item = "<" + i + ">";
                regStr4Validation = regStr4Validation.replace(item, args[i]+"");
            }
        }
        // 验证
        Pattern pattern = Pattern.compile(regStr4Validation);
        Matcher matcher = pattern.matcher(content);
        boolean result = matcher.matches();

        if (!result) {
            // 验证失败
            errorStr = getErrorStrByRegStr(regStr, args);
        }

        return errorStr;
    }

    /**
     * 根据表达式获取错误信息
     * @param regStr 表达式
     * @param args 参数
     * @return
     */
     private static String getErrorStrByRegStr(String regStr, int ...args) {
        String errorStr = "";
        switch (regStr) {
            case NUMBER_ONLY:
                errorStr = "只允许为数字";
                break;
            case NUMBER_ON_FIXED:
                errorStr = "请输入固定的" + args[0] + "位数字";
                break;
            case NUMBER_AT_LEAST:
                errorStr = "请输入至少" + args[0] + "位数字";
                break;
            case NUMBER_IN_RANGE:
                errorStr = "请输入至少" + args[0] + "位,至多" + args[1] + "位数字";
                break;
            case NUMBER_IS_REAL:
                errorStr = "请输入一个实数";
                break;
            case NUMBER_IS_POS_REAL:
                errorStr = "请输入一个正实数";
                break;
            case NUMBER_LETTER_ONLY:
                errorStr = "只允许为数字和字母";
                break;
            case ACCOUNT:
                errorStr = "字母开头,3到12位字母、数字、下划线";
                break;
            case PASSWORD:
                errorStr = "由4-15位数字、字母、下划线组成";
                break;
            case EMAIL:
                errorStr = "邮箱格式错误";
                break;
            case ID_CARD:
                errorStr = "身份证号格式错误";
                break;
            case MOBILE_PHONE:
                errorStr = "手机号码格式错误";
                break;
            case TELEPHONE:
                errorStr = "电话号码格式错误";
                break;
            case MAC_ADDRESS:
                errorStr = "Mac地址格式错误";
                break;
            case ZIPCODE:
                errorStr = "邮政编码格式错误";
                break;
            case IPV4:
                errorStr = "ipv4格式错误";
                break;
            case IPV6:
                errorStr = "ipv6格式错误";
                break;
            case DATE_FORMAT:
                errorStr = "日期格式(yyyy-MM-dd)错误";
                break;
            case TIME_FORMAT:
                errorStr = "时间格式(hh:mm:ss)错误";
                break;
            case DATE_TIME_FORMAT:
                errorStr = "日期时间格式(yyyy-MM-dd hh:mm:ss)错误";
                break;
            case NUMBER_IS_BOOLEAN:
                errorStr = "只允许为0或1";
                break;
            default:
                break;
        }

        return errorStr;
    }
}

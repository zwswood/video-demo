package com.linrun.ssm.entity.vo;

import com.linrun.ssm.entity.bo.AbstractBO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象VO类
 *
 * @author 骆焕
 * @createDate 2019-11-14
 */
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 保存转换信息的Map
    protected Map<String, String> convertMap = new HashMap<>();

    // 转义后的换行符(用于提示)
    final static String NEW_LINE_SEPARATOR_4_ALERT = "<br/>";

    /**
     * 获取转换信息
     *
     * @remark 不能定义方法为getConvertInfo, 内置机制会将此方法看作是Getter或Setter访问器而Json序列化该方法
     */
    public String obtainConvertInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        if (convertMap.size() > 0) {
            for (Map.Entry<String, String> entry : convertMap.entrySet()) {
                stringBuilder.append(entry.getKey() + ": " + entry.getValue());
                stringBuilder.append(NEW_LINE_SEPARATOR_4_ALERT);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * VO转BO抽象方法
     *
     * @return
     */
    public abstract AbstractBO convert2BO();

    /**
     * (用于查询,无需验证的)VO转BO抽象方法
     *
     * @return
     */
    public abstract AbstractBO convert2BO4Query();
}

package com.linrun.ssm.entity.bo;

import com.linrun.ssm.entity.vo.AbstractVO;

/**
 * 抽象BO类
 * @author 骆焕
 * @createDate 2019-11-14
 */
public abstract class AbstractBO {

    /**
     * BO转VO抽象方法
     * @return
     */
    public abstract AbstractVO convert2VO();


}

package com.linrun.ssm.controller;

import com.linrun.ssm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * [基础]业务控制类
 *
 * @author 骆焕
 * @createDate 2019-11-18
 */
@Controller
public abstract class BaseController {

    /**
     * 注入Service
     *
     * added by luohuan 2020-03-16
     *
     * 继承层次从extends BaseDao升华到extends BaseController即可
     * 这样的话,写日志与其他逻辑分离,servericeImpl中无需try...catch操作
     * 从而可以让事务正常自动回滚
     */
    @Autowired
    private BaseService baseService;
    /**
     * 根据HeadCode获取快码列表
     * @param headCode
     * @return
     */
    /*public List<ParaBodyBO> getParamBodyListByHeadCode(int headCode) {
        return baseService.getParamBodyListByHeadCode(headCode);
    }*/
}

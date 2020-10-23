package com.linrun.ssm.service.impl;

import com.linrun.ssm.aop.DataSource;
import com.linrun.ssm.dao.BaseDAO;
import com.linrun.ssm.service.BaseService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述    :
 * 作者    : lyp
 * 创建时间: 2020-03-11
 */
@Log4j
@Service("BaseService")
@DataSource
public class BaseServiceImpl implements BaseService {
    // 注入Dao
    @Autowired
    private BaseDAO baseDAO;

    /*// 根据头编码获取参数体列表
    @Override
    public List<ParaBodyBO> getParamBodyListByHeadCode(int headCode) {
        return baseDAO.getParamBodyListByHeadCode(headCode);
    }*/
}

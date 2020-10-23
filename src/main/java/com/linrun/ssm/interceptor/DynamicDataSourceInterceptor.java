package com.linrun.ssm.interceptor;

import com.linrun.ssm.aop.DataSource;
import com.linrun.ssm.constant.GlobalConst;
import com.linrun.ssm.datasource.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 动态数据源拦截器
 * @author 骆焕
 * @createDate 2019-11-19
 */
public class DynamicDataSourceInterceptor {

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DataSourceHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行完成后将数据源置为默认
     */
    public void after() {
        DataSourceHolder.setDataSource(GlobalConst.DATASOURCE_BUSINESS);
    }
}

package com.linrun.ssm.datasource;

/**
 * 用于持有当前线程中使用的数据源标识
 */
public class DataSourceHolder {

    // Synchronized用于线程间的数据共享,而ThreadLocal则用于线程间的数据隔离.
    // 所以,ThreadLocal的应用场合,最适合的是按线程多实例的对象的访问,并且这个对象很多地方都要用到
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    @SuppressWarnings("unused")
    public static void setDataSource(String dataSource) {
        dataSources.set(dataSource);
    }

    public static String getDataSource() {
        return dataSources.get();
    }
}

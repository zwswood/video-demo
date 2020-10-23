package com.linrun.ssm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/1/8
 * @Version V1.0
 **/
public class PropertiesUtils {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * 根据文件名获取Properties对象
     * @param fileName
     * @return
     */
    public static Properties read(String fileName){
        InputStream in = null;
        try{
            Properties prop = new Properties();
            //InputStream in = Object.class.getResourceAsStream("/"+fileName);
            in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
            if(in == null){
                return null;
            }
            prop.load(in);
            return prop;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据文件名和键名获取值
     * @param fileName
     * @param key
     * @return
     */
    public static String readKeyValue(String fileName, String key){
        Properties prop = read(fileName);
        if(prop != null){
            return prop.getProperty(key);
        }
        return null;
    }

    /**
     * 根据键名获取值
     * @param prop
     * @param key
     * @return
     */
    public static String readKeyValue(Properties prop, String key){
        if(prop != null){
            return prop.getProperty(key);
        }
        return null;
    }
}

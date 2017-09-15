package com.yjcloud.transfer.util.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties 配置
 * Created by hhc on 17/4/28.
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, String> propertyMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertyMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    public static void check(String key) throws IllegalArgumentException {
        if (!containsKey(key)) {
            throw new IllegalArgumentException("not configured: " + key);
        }
    }

    public static boolean containsKey(String key) {
        return propertyMap.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String name) {
        check(name);
        return (T) propertyMap.get(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String name, T defaultValue) {
        if (propertyMap.containsKey(name)) {
            return (T) propertyMap.get(name);
        } else {
            return defaultValue;
        }
    }

}

package com.szhis.frsoft.common.factory.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.util.Assert;

/**
 * 
 * @author xiaozheng
 * 参考：
 * http://www.open-open.com/lib/view/open1425954600717.html
 * http://www.cnblogs.com/dream-to-pku/p/6367396.html
 */
public class PropertyPlaceholderConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {
	private static Properties properties;
	/*private static Map<String,String> ctxPropMap;*/
	
	@Override
	protected Properties mergeProperties() throws IOException {
		properties = super.mergeProperties();
		return properties;
	}

	public static String getProperty(String key) {
		Assert.notNull(properties, "bean not inject");
		return properties.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		Assert.notNull(properties, "bean not inject");
		return properties.getProperty(key, defaultValue);
	}
	
	public static Properties getProperties() {
		return properties;
	}

   /* @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropMap = new HashMap<String, String>();
        for (Object key : props.keySet()){
            String keyStr = key.toString();
            String value = String.valueOf(props.get(keyStr));
            ctxPropMap.put(keyStr,value);
        }
    }

    public static String getCtxProp(String name) {
        return ctxPropMap.get(name);
    }

    public static Map<String, String> getCtxPropMap() {
        return ctxPropMap;
    }*/	
}

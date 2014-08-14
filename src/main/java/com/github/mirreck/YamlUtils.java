package com.github.mirreck;

import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by thomas.decoster on 11/08/2014.
 */
public class YamlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlUtils.class);
    private static final String SEPARATOR = "/";

    private YamlUtils(){

    }

    public static Map<String, Object> loadYamlResource(Class targetClass, String resourceName, Map<String, Object> defaultResult){
        final InputStream inputStream = targetClass.getResourceAsStream(resourceName);
        if(inputStream != null){
            final Object yaml = Yaml.load(inputStream);
            if(!(yaml instanceof Map)){
                throw new FakeFactoryException("Error while loading yaml resource !");
            }
            return (Map<String, Object>) yaml;
        } else {
            LOGGER.warn("no yaml resource found for class {}", targetClass.getName());
            return defaultResult;
        }
    }

    public static Object fetchObject(Map<String, Object> yaml, String path){
        return fetchObject(yaml,path,SEPARATOR);
    }

    public static Object fetchObject(Map<String, Object> yaml, String path, String separator){
        if(yaml == null){
            return null;
        }
        final int index = path.indexOf(separator);
        if( index >=0){

            Map<String, Object> subYaml = fetchMap(yaml, path.substring(0,index),separator);
            return fetchObject(subYaml, path.substring(index+1),separator);
        } else {
            return yaml.get(path);
        }
    }

    public static Map<String, Object> fetchMap(Map<String, Object> yaml, String path){
        return fetchMap(yaml,path,SEPARATOR);
    }

    private static Map<String, Object> fetchMap(Map<String, Object> yaml, String path, String separator) {
        Object obj = fetchObject(yaml, path);
        if(obj == null){
            return null;
        }
        if(!(obj instanceof Map)){
            throw new FakeFactoryException("Error while searching yaml tree !");
        }
        return (Map<String, Object>) obj;
    }
}

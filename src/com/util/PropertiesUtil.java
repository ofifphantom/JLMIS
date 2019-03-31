package com.util;

 import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class PropertiesUtil {
	
    //根据Key读取Value
     public static String GetValueByKey(String key) {
         Properties pps = new Properties();
         try {
        	

           //  InputStream in = new BufferedInputStream (new FileInputStream("resources/resources.properties"));  
         pps.load( PropertiesUtil.class.getClassLoader().getResourceAsStream("resources.properties"));
            String value = pps.getProperty(key);
            System.out.println(key + " = " + value);
             return value;
            
         }catch (IOException e) {
             e.printStackTrace();
             return null;
         }
     }
}

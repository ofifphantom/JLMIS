package com.jl.mis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jl.mis.service.CouponInformationService;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.SundryCodeUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring-config.xml") 
public class BaseTest {
	@Resource
	CouponInformationService couponInformationService;
	
	
	
	
	
	@Test
	public void test1(){
		 System.out.println("encryptedData加密方法后的数据为：" + MD5("123456"));
		
	}
	  private static String algorithm = "AES";
	  private static String encroptMode = "ECB";
	  private static String paddingMode = "PKCS5Padding";
	  private static String keyStr = "assistant7654321";
	public static final String MD5(String s)
	  {
	    try
	    {
	      BASE64Decoder base64decoder = new BASE64Decoder();
	      BASE64Encoder base64encoder = new BASE64Encoder();
	      byte[] keyBytes = base64decoder.decodeBuffer(keyStr);
	      KeyGenerator generator = KeyGenerator.getInstance(algorithm);
	      generator.init(new SecureRandom(keyBytes));
	      Key key = generator.generateKey();
	      Cipher cipher = Cipher.getInstance(algorithm + "/" + encroptMode + "/" + paddingMode);
	      cipher.init(1, key);
	      
	      byte[] encryptBytes = cipher.doFinal(s.getBytes("UTF-8"));
	      return base64encoder.encode(encryptBytes);
	    }
	    catch (InvalidKeyException e)
	    {
	      e.printStackTrace();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	      e.printStackTrace();
	    }
	    catch (NoSuchPaddingException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IllegalBlockSizeException e)
	    {
	      e.printStackTrace();
	    }
	    catch (BadPaddingException e)
	    {
	      e.printStackTrace();
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return "";
	  }
	  
	/*  public static void main(String[] args)
	    throws Exception
	  {
	    System.out.println("encryptedData加密方法后的数据为：" + MD5("123456"));
	  }*/
	
	
	@Test
	public void testQuestResfult(){
	}

}

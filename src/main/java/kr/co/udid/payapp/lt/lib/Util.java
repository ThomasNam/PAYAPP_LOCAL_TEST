package kr.co.udid.payapp.lt.lib;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class Util
{
	/**
	 * 스트링을 int 형으로 파싱해준다.
	 * 파싱이 제대로 되지 않으면 0 을 리턴한다.
	 * @param srcStr
	 * @return
	 */
	public static int parseInt(String srcStr)
	{
		int retVal = 0;
		
		try
		{
			retVal = Integer.parseInt (srcStr);
		} 
		catch (Exception e) { }

		return(retVal);
	}
	
	/**
	 * 스트링을 short 형태로 파싱해준다.
	 * 파싱이 제대로 되지 않으면 0 을 리턴한다.
	 * @param srcStr
	 * @return
	 */
	public static short parseShort (String srcStr)
	{
		short retVal = 0;
		try
		{
			retVal = Short.parseShort (srcStr);
		} 
		catch (Exception e)
		{
		}

		return (retVal);
	}

	/**
	 * Object 형 스트링을 int 형으로 파싱해준다.
	 * 파싱이 제대로 되지 않으면 0을 리턴한다.
	 * @param srcData
	 * @return
	 */
	public static int parseInt (Object srcData)
	{
		int retVal = 0;

		try
		{
			retVal = Integer.parseInt ((String) srcData);
		}
		catch (Exception e)
		{
		}
		return (retVal);
	}
	
	/**
	 * 무조건 올림
	 * @param v
	 * @param k
	 * @return
	 */
	public static int forceUp (int v, int k)
	{
		int mod = v % k;
		
		if (mod > 0)
		{
			v = v - mod + k;
		}
		
		return v;
	}

	/**
	 * 토큰을 생성한다.
	 * @return 토큰값
	 */
	public static String generateToken ()
	{
		String token = null;

		try
		{
			SecureRandom secureRandom = SecureRandom.getInstance ("SHA1PRNG");
			MessageDigest digest = MessageDigest.getInstance ("SHA-256");
			secureRandom.setSeed (secureRandom.generateSeed (128));
			token = new String (digest.digest ((secureRandom.nextLong () + "").getBytes ()));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace ();
		}
		return token;
	}


	public static String[] getNullPropertyNames (Object source)
	{
		final BeanWrapper src = new BeanWrapperImpl (source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors ();

		Set<String> emptyNames = new HashSet<String> ();
		for (java.beans.PropertyDescriptor pd : pds)
		{
			Object srcValue = src.getPropertyValue (pd.getName ());
			if (srcValue == null) emptyNames.add (pd.getName ());
		}
		String[] result = new String[emptyNames.size ()];
		return emptyNames.toArray (result);
	}

	// then use Spring BeanUtils to copy and ignore null
	public static void myCopyProperties (Object src, Object target)
	{
		BeanUtils.copyProperties (src, target, getNullPropertyNames (src));
	}
}

package kr.co.udid.payapp.lt.lib;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class StrLib
{
	/**
	 * 문자열 null 체크  null 이거나 빈공백이면 true 리턴 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr (String str)
	{
		if (str == null)
			return true;
		
		if (str.equals (""))
			return true;

		if (str.equals ("null"))
			return true;
		
		return false;
	}
	
	/**
	 * 숫자 null 체크  0이면 true 리턴 
	 * @return
	 */
	public static boolean isEmptyNum(int no) 
	{
		if(no == 0)
			return true;
		
		return false;
	}
	
	/**
	 * 스트링 null 체크 null 이면 "" 을 리턴.
	 * @param str
	 * @return
	 */
	public static String notNull (String str)
	{
		if (str == null)
			return "";
		
		return str;
	}
	
	/**
	 *  String 바인딩 1개 바인딩
	 *  StrLib.getStrFormatOne (R.L("RESOURCE_9000"), String.valueOf (mailCnt)));
	 * @param templete
	 * @param data
	 * @return
	 */
	static public String getStrFormatOne (String templete, String data)
	{
		MessageFormat messageFormat = new MessageFormat (templete);

		String [] datas = new String [1];
		
		datas[0] = data;
		
		return messageFormat.format (datas);
	}
	
	/**
	 *   String 바인딩 2개 바인딩
	 *	 StrLib.getStrFormatOne (R.L("RESOURCE_9000"), String.valueOf (mailCnt)));
	 * @param templete
	 * @param data
	 * @param date2
	 * @return
	 */
	static public String getStrFormatTwo (String templete, String data, String date2)
	{
		MessageFormat messageFormat = new MessageFormat (templete);
	
		  String [] datas = new String [2];
		  datas[0] = String.valueOf (data);
		  datas[1] = String.valueOf (date2);
		  
		  return messageFormat.format (datas);
	}
	
	/**
	 * 전부 str :aacccc delim: a =>[ , , cccc]
	 * @param str
	 * @param delim
	 * @return
	 */
	public static Vector<String> getTokens (String str, String delim)
	{
		StringTokenizer st 		= new StringTokenizer (str, delim, true);
		Vector<String> 	vTokens = new Vector<String> ();

		String prevToken = null;
		String curToken = null;
		
		while (st.hasMoreTokens ())
		{
			curToken = st.nextToken ();
			
			if (curToken.equals (delim))
			{
				if (prevToken == null || prevToken.equals (delim))
				{
					vTokens.addElement ("");
				}
			}
			else
			{
				vTokens.addElement (curToken);
			}
			
			prevToken = curToken;
		}
		
		if (prevToken.equals (delim))
		{
			vTokens.addElement ("");
		}

		return vTokens;
	}
	
	public static String subStr (String str, int len, String tail)
	{
		try
		{
			if (str == null)
				return "";

			byte[] arrStr = str.getBytes ("UTF8");
			byte[] retStr;

			if (arrStr.length <= len)
				return (str);

			int i = 0;
			double more = 0;

			for (i = 0; i < len; i++)
			{
				if ((int) arrStr[i] > 127 || (int) arrStr[i] < 0)
				{
					if (i != len - 2)
					{
						i += 2;
						more += 0.9;
					}
					else
						break;
					if (more >= 4)
					{
						len += Math.round (more);
						more = 0;
					}
				}
				if (len >= arrStr.length)
					return (str);
			}

			retStr = new byte[i];
			for (int j = 0; j < i; j++)
				retStr[j] = arrStr[j];

			return (new String (retStr, "UTF8") + tail);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			System.out.println (e.getMessage ());
		}

		return ("");
	}
	
	/**
	 * 엔터,<,>," " = 변환 
	 * @param str
	 * @return
	 */
	public static String printComment (String str)
	{
		str = str.replaceAll ("<","&lt;");
		str = str.replaceAll (">", "&gt;");
		str = str.replaceAll ("\n", "<br>");
		str = str.replaceAll (" ", "&nbsp;");
		
		return str;
	}
	
	/**
	 *  [ " ] => [ 특수문자 " 로 변환 ] 
	 * @param str
	 * @return
	 */
	public static String encodeInputText (String str)
	{
		str = str.replace ("\"", "&quot;");
		
		return str;
	}

	/**
	 *   [ ' ] => [ 특수문자 ' 로 변환 ] 
	 * @param str
	 * @return
	 */
	public static String encodeInputText2 (String str)
	{
		str = str.replace ("\'", "&#39");
		
		return str;
	}
	
	/**
	 * Url 인코드
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode (String url) throws UnsupportedEncodingException
	{
		return URLEncoder.encode (url, "UTF-8");
	}
	
	/**
	 * 동 주소를 출력한다.
	 * @param dong
	 * @return
	 */
	public static String printDongAddr (String dong)
	{
		String [] d = dong.split (" ");
		
		if (d.length <= 2)
			return dong;
		
		StringBuffer sb = new StringBuffer ();
		
		for (int i = 2; i < d.length; i++)
		{
			if (i > 2)
				sb.append (" ");
				
			sb.append (d[i]);
		}
		
		return sb.toString ();
	}
	
	public static boolean isNumeric (String str)
	{
		try
		{
			Double.parseDouble (str);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 전화번호를 나눈다.
	 * @param phone
	 * @return
	 */
	static public String [] getPhoneSplit (String phone)
	{
		if (phone == null)
			return null;
		
		String [] phones = null;
		
		if (phone.length () == 11)
		{
			phones = new String [3];
			
			phones[0] = phone.substring (0, 3);
			phones[1] = phone.substring (3, 7);
			phones[2] = phone.substring (7, 11);
		}
		else if (phone.length () == 10)
		{
			phones = new String [3];
			
			if (phone.startsWith ("02"))
			{
				phones[0] = phone.substring (0, 2);
				phones[1] = phone.substring (2, 6);
				phones[2] = phone.substring (6, 10);
			}
			else
			{
				phones[0] = phone.substring (0, 3);
				phones[1] = phone.substring (3, 6);
				phones[2] = phone.substring (6, 10);
			}
		}
		
		return phones;
	}
	
	static public boolean isFirstChar (char c)
	{
		if (c == 'ㄱ')
			return true;
		else if (c == 'ㄴ')
			return true;
		else if (c == 'ㄷ')
			return true;
		else if (c == 'ㄹ')
			return true;
		else if (c == 'ㅁ')
			return true;
		else if (c == 'ㅂ')
			return true;
		else if (c == 'ㅅ')
			return true;
		else if (c == 'ㅇ')
			return true;
		else if (c == 'ㅈ')
			return true;
		else if (c == 'ㅊ')
			return true;
		else if (c == 'ㅋ')
			return true;
		else if (c == 'ㅌ')
			return true;
		else if (c == 'ㅍ')
			return true;
		else if (c == 'ㅎ')
			return true;

		return false;
	}

	/**
	 * \n 만 <br> 로 바꾼다.
	 * @param str
	 * @return
	 */
	public static String nl2br (String str)
	{
		str = str.replaceAll ("\n", "<br>");

		return str;
	}

	public static String generateToken ()
	{
		SecureRandom random = new SecureRandom();

		return new BigInteger (130, random).toString(32);
	}

	public static String getRndNum (int loopCount)
	{
		String str = "";
		int d;

		for (int i = 1; i <= loopCount; i++)
		{
			Random r = new Random();

			d = r.nextInt (9);

			str = str + Integer.toString(d);
		}

		return str;
	}

	public static String getRandomCode(int len)
	{
		Random random =new Random();
		char alphaNum[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
		String str = "";
		for(int j = 0; j < len ; j++)
			str = str + alphaNum[ Math.abs(random.nextInt()) % 36 ];

		return str;
	}

	/**
	 * 모바일에 있는 특수 문자를 삭제한다.
	 * @param mobile 핸드폰 번호
	 * @return 특수 문자 삭제된 핸드폰 번호
	 */
	public static String getRemoveMobileChar (String mobile)
	{
		mobile = mobile.replaceAll ("-", "");
		mobile = mobile.replaceAll (" ", "");

		return mobile;
	}
}

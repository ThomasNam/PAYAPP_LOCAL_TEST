package kr.co.udid.payapp.lt.model.common.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Created by RED on 2015-05-07.
 */
@Embeddable
public class CommonSeqPk implements Serializable
{
	/**
	 * 체번 구분값 1
	 */
	private String	type1;

	/**
	 * 체번 구분값 2
	 */
	private String	type2;

	public CommonSeqPk ()
	{}

	public CommonSeqPk (String type1, String type2)
	{
		this.type1 = type1;
		this.type2 = type2;
	}

	public String getType1 ()
	{
		return type1;
	}

	public void setType1 (String type1)
	{
		this.type1 = type1;
	}

	public String getType2 ()
	{
		return type2;
	}

	public void setType2 (String type2)
	{
		this.type2 = type2;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;
		if (o == null || getClass () != o.getClass ()) return false;

		CommonSeqPk that = (CommonSeqPk) o;

		if (type1 != null ? !type1.equals (that.type1) : that.type1 != null) return false;
		return !(type2 != null ? !type2.equals (that.type2) : that.type2 != null);

	}

	@Override
	public int hashCode ()
	{
		int result = type1 != null ? type1.hashCode () : 0;
		result = 31 * result + (type2 != null ? type2.hashCode () : 0);
		return result;
	}
}

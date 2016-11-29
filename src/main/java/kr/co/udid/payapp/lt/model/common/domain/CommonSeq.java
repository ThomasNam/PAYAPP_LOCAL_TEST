package kr.co.udid.payapp.lt.model.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class CommonSeq implements Serializable
{
	@EmbeddedId
	CommonSeqPk commonSeqPk;

	@Column (name = "curVal")
	private	Integer curVal;	//체번	[현재값]
	
	public CommonSeq ()
	{
		curVal = 0;
	}
	
	public CommonSeq (CommonSeqPk commonSeqPk)
	{
		this.commonSeqPk = commonSeqPk;

		curVal = 0;
	}

	public int getCurVal ()
	{
		return curVal;
	}

	public void setCurVal (int curVal)
	{
		this.curVal = curVal;
	}

	public CommonSeqPk getCommonSeqPk ()
	{
		return commonSeqPk;
	}
}

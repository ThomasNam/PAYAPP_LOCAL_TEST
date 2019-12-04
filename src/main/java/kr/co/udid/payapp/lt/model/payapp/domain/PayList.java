package kr.co.udid.payapp.lt.model.payapp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Accessors(chain = true)
public class PayList extends PayListBase
{
	@Id
	@GeneratedValue
	private Long no;

	private String url;

	private short payType;

	private String payInfo1;

	private String payInfo2;

	/**
	 * MUL No
	 */
	private String mulNo;

	/**
	 * 결제 상태
	 */
	private int ostate;

	private Date regDate;

	private Date payDate;

	private Date cancelDate;

	public String getStateStr ()
	{
		if (getOstate () == 0)
			return "결제요청";
		else if (getOstate () == 4)
			return "결제완료";
		else if (getOstate () == 8)
			return "요청취소";
		else if (getOstate () == 9)
			return "승인취소";
		else
			return "알수 없음";
	}

	public String getPayTypeStr ()
	{
		return "결제수단";
	}
}

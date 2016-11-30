package kr.co.udid.payapp.lt.model.payapp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Thomas on 2016. 11. 29..
 */
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

	public String getStateStr ()
	{
		return "뭐하는 상태임";
	}
}

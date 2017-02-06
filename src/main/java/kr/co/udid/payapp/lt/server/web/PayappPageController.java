package kr.co.udid.payapp.lt.server.web;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.udid.payapp.lt.model.payapp.PayappSv;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by RED on 2016-11-30.
 */
@Controller
@RequiredArgsConstructor
public class PayappPageController
{
	private final PayappSv payappSv;

	@GetMapping ("/p/{url}")
	public String pay (Model mo, @PathVariable ("url") String url)
	{
		PayList pay = payappSv.findUrl (url);

		mo.addAttribute ("pay", pay);
		mo.addAttribute ("url", url);

		return "pay";
	}

	/**
	 * 카드 결제 완료
	 * @param no
	 * @return
	 */
	@PostMapping("/oapi/cardAccountComplete.html")
	@ResponseBody
	public String cardAccountComplete(
			@RequestParam(value = "no", required = false) Long no,
			@RequestParam(value = "url", required = false) String url
	)
	{
		boolean bool = payappSv.cardAccountComplete(no);

		return getObject(bool, url);
	}


	/**
	 * 핸드폰 결제 완료
	 * @param no
	 * @return
	 */
	@PostMapping("/oapi/mobileAccountComplete.html")
	@ResponseBody
	public String mobileAccountComplete (
			@RequestParam(value = "no", required = false) Long no,
			@RequestParam(value = "url", required = false) String url
	)
	{

		boolean bool = payappSv.mobileAccountComplete(no);

		return getObject(bool, url);

	}

	/**
	 * 결제 취소
	 * @param no
	 * @return
	 */
	@PostMapping("/oapi/cancelAccount.html")
	@ResponseBody
	public String cancelAccount (
			@RequestParam(value = "no", required = false) Long no,
			@RequestParam(value = "url", required = false) String url
	)
	{
		boolean bool = payappSv.cancelAccount(no);

		return getObject(bool, url);
	}

	public String getObject(boolean isRefresh, String url){

		JSONObject object = new JSONObject();

		if (isRefresh){
			PayList pay = payappSv.findUrl (url);

			object.put("refresh", isRefresh);
			object.put("ostate", pay.getOstate());

		}
		object.put("refresh", isRefresh);
		return object.toString();

	}


}

package kr.co.udid.payapp.lt.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.udid.payapp.lt.model.payapp.PayappSv;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
import lombok.RequiredArgsConstructor;

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

		return "pay";
	}
}

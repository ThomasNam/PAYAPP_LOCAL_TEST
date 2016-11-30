package kr.co.udid.payapp.lt.server.vaadin;

import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.List;

import kr.co.udid.payapp.lt.model.payapp.PayappSv;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
import lombok.RequiredArgsConstructor;

/**
 * Created by Thomas on 2016. 11. 30..
 */
@Title ("Payapp Local Test")
@Theme ("valo")
@SpringUI
@RequiredArgsConstructor
public class MainUI extends UI
{
	final private PayappSv payappSv;

	final int page = 1;

	final int limit = 20;

	MTable<PayList> table;

	Pagination pagination;

	private Button refreshBtn = new MButton (FontAwesome.REFRESH, "새로고침", this::onRefresh);

	private Button cardCompleteBtn = new MButton (FontAwesome.CREDIT_CARD, "카드결제완료", this::onCardPayComplete);

	private Button mobileCompleteBtn = new MButton (FontAwesome.MOBILE, "휴대폰결제완료", this::onMobilePayComplete);

	private Button cancelBtn = new MButton (FontAwesome.REMOVE, "결제취소", this::onCancelPay);

	@Override
	protected void init(VaadinRequest request)
	{
		Page<PayList> payLists = payappSv.findAll (new PageRequest (0, 10));

		long total = payLists.getTotalElements ();

		table = createTable (payLists.getContent ());

		pagination = createPagination (total, page, limit);

		pagination.addPageChangeListener((PaginationChangeListener) event -> listRefresh (event.pageIndex(), event.limit ()));

		table.addValueChangeListener ((event -> {
			refreshButtonState ();
		}));

		setContent(
			new MVerticalLayout (
				// aboutBox,
				new MHorizontalLayout (refreshBtn, cardCompleteBtn, mobileCompleteBtn, cancelBtn),
				table,
				pagination
			).expand(table)
		);
		// listEntities();
	}

	private void refreshButtonState ()
	{
		cardCompleteBtn.setEnabled (false);
		cancelBtn.setEnabled (false);

		if (table.getValue () != null)
		{
			if (table.getValue ().getOstate () == 0)
			{
				cardCompleteBtn.setEnabled (true);
				cancelBtn.setEnabled (false);
			}
			else if (table.getValue ().getOstate () == 4)
			{
				cardCompleteBtn.setEnabled (true);
				cancelBtn.setEnabled (true);
			}
		}
	}

	private MTable<PayList> createTable(List<PayList> users)
	{
		final MTable<PayList> table = new MTable<> ();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer<> (PayList.class, users));
		table.setVisibleColumns("no", "goodName", "goodPrice", "sellerUserID", "memPhone", "regDate", "payTypeStr", "stateStr", "var1", "var2", "feedbackUrl");
		table.setColumnHeaders("no", "상품명", "상품가격", "판매자아이디", "구매자 핸드폰", "등록일", "결제수단", "상태스트링", "추가데이터1", "추가데이터2", "피드백URL");
		return table;
	}

	private Pagination createPagination(long total, int page, int limit)
	{
		final PaginationResource paginationResource = PaginationResource.newBuilder().setTotal(total).setPage(page).setLimit(limit).build();
		final Pagination pagination = new Pagination(paginationResource);
		pagination.setItemsPerPage(10, 20, 50, 100);
		return pagination;
	}

	private void onRefresh (Button.ClickEvent clickEvent)
	{
		listRefresh ();
	}

	private void listRefresh ()
	{
		listRefresh (0, limit);
	}

	private void onCardPayComplete (Button.ClickEvent clickEvent)
	{
		if (payappSv.cardAccountComplete (table.getValue ().getNo ()))
		{
			Notification.show ("결제 완료", "결제 완료 처리를 하였습니다.", Notification.Type.TRAY_NOTIFICATION);

			listRefresh ();
		}
		else
			Notification.show ("실패", "결제 완료 처리를 실패하였습니다.", Notification.Type.ERROR_MESSAGE);
	}

	private void onMobilePayComplete (Button.ClickEvent clickEvent)
	{
		if (payappSv.mobileAccountComplete (table.getValue ().getNo ()))
		{
			Notification.show ("결제 완료", "결제 완료 처리를 하였습니다.", Notification.Type.TRAY_NOTIFICATION);

			listRefresh ();
		}
		else
			Notification.show ("실패", "결제 완료 처리를 실패하였습니다.", Notification.Type.ERROR_MESSAGE);
	}

	private void onCancelPay (Button.ClickEvent clickEvent)
	{
		if (payappSv.cancelAccount (table.getValue ().getNo ()))
		{
			Notification.show ("결제 취소", "결제 취소 처리를 하였습니다.", Notification.Type.TRAY_NOTIFICATION);

			listRefresh ();
		}
		else
			Notification.show ("실패", "결제 완료 처리를 실패하였습니다.", Notification.Type.ERROR_MESSAGE);
	}

	private void listRefresh (int page, int limit)
	{
		Page<PayList> tempPayList = payappSv.findAll (new PageRequest (page, limit));
		pagination.setTotalCount(tempPayList.getTotalElements());
		table.removeAllItems();

		tempPayList.getContent ().forEach ((p) -> table.addItem (p));
	}
}

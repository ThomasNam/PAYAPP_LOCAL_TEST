package kr.co.udid.payapp.lt.server.vaadin;

import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	Table table;

	Pagination pagination;

	@Override
	protected void init(VaadinRequest request)
	{
		Page<PayList> payLists = payappSv.findAll (new PageRequest (0, 10));

		long total = payLists.getTotalElements ();

		table = createTable (payLists.getContent ());

		pagination = createPagination (total, page, limit);

		pagination.addPageChangeListener((PaginationChangeListener) event -> {

			Page<PayList> tempPayList = payappSv.findAll (new PageRequest (event.pageIndex(), event.limit ()));
			pagination.setTotalCount(tempPayList.getTotalElements());
			table.removeAllItems();

			tempPayList.getContent ().forEach ((p) -> table.addItem (p));
		});


		setContent(
			new MVerticalLayout (
				// aboutBox,
				// new MHorizontalLayout (filterByName, addNew, edit, delete),
				table,
				pagination
			).expand(table)
		);
		// listEntities();


	}

	private Table createTable(List<PayList> users) {
		final Table table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer<> (PayList.class, users));
		table.setVisibleColumns("no", "goodName", "goodPrice", "sellerUserID", "memPhone", "regDate", "stateStr");
		table.setColumnHeaders("no", "상품명", "상품가격", "sellerUserID", "memPhone", "등록일", "상태스트링");
		return table;
	}

	private Pagination createPagination(long total, int page, int limit)
	{
		final PaginationResource paginationResource = PaginationResource.newBuilder().setTotal(total).setPage(page).setLimit(limit).build();
		final Pagination pagination = new Pagination(paginationResource);
		pagination.setItemsPerPage(10, 20, 50, 100);
		return pagination;
	}
}

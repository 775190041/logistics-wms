package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Invoice;


import java.util.List;

/**
 *  直接发货
 */
public interface InvoiceService {

    List<Invoice> queryAll();

    void selectDataGrid(PageInfo pageInfo);

    Invoice selectById(Integer id);

    int addInvoice(Invoice invoice);

    int insert(Invoice invoice);

    int deleteById(Integer id);

    int updateInvoice(Invoice invoice);

    int importInvoice(Invoice invoice);
}

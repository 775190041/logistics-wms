package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.OrderNumberUtil;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.InvoiceMapper;
import com.nf.entity.Invoice;
import com.nf.service.GodownService;
import com.nf.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private GodownService godownService;

    @Override
    public List<Invoice> queryAll() {
        return invoiceMapper.queryAll();
    }

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<Invoice> page = new Page<Invoice>(pageInfo.getNowPage(), pageInfo.getSize());
        List<Invoice> list = invoiceMapper.selectInvoicePage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public Invoice selectById(Integer id) {
        return invoiceMapper.selectByPrimaryKey(id);
    }

    @SuppressWarnings("deprecation")
	@Override
    public int addInvoice(Invoice invoice) {
        invoice.setInOddnumber(OrderNumberUtil.generateOrderNo());
        godownService.reduction(invoice.getInWhid(), invoice.getInVolume());
        return invoiceMapper.insert(invoice);
    }

    @Override
    public int insert(Invoice invoice) {
        return invoiceMapper.insert(invoice);
    }

    @Override
    public int deleteById(Integer id) {
        Invoice invoice = selectById(id);
        if (invoice != null) {
            return invoiceMapper.deleteByPrimaryKey(id);
        } else {
            //没有此ID
            return 0;
        }
    }

    /**
     * Selective 为动态SQL
     *
     * @param invoice
     * @return
     */
    @Override
    public int updateInvoice(Invoice invoice) {
        return invoiceMapper.updateByPrimaryKeySelective(invoice);
    }

    @SuppressWarnings("deprecation")
	@Transactional
    @Override
    public int importInvoice(Invoice invoice) {
        godownService.reduction(invoice.getInWhid(), invoice.getInVolume());
        return invoiceMapper.insert(invoice);
    }

}

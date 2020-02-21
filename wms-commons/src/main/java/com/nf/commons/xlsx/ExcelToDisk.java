package com.nf.commons.xlsx;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 写入
 * @author 骚哥神机
 */
public class ExcelToDisk<T> {
    public void Excel(Object data[], String fileName, String [] title, HttpServletResponse resp, String sheet ){
        try {
            // 取得输出流
            OutputStream os = resp.getOutputStream();
            // 清空输出流
            resp.reset();
            //设置请求头
            resp.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));
            // 定义输出类型(设定输出文件头)
            resp.setContentType("application/msexcel");
            /** 创建工作簿 */
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            WritableSheet sheets = workbook.createSheet(sheet,0);
            //设置纵横打印（默认为纵打）、打印纸
            jxl.SheetSettings sheetset = sheets.getSettings();
            sheetset.setProtected(false);
            //设置单元格字体
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 12);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD);
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行
            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行
            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            // sheet.mergeCells(0, 0, colWidth, 0);
            // sheet.addCell(new Label(0, 0, "XX报表", wcf_center));

            /** ***************以下是EXCEL第一行列标题********************* */
            for(int i = 0 ; i < title.length ; i++){
                sheets.addCell(new Label(i,0,title[i],wcf_center));
            }

            int i = 1;
            int j = 0;
            for(int k=0;k<title.length;k++){
                sheets.addCell(new Label(j,i,data[k].toString(),wcf_left));
                if(k == (title.length-1)){
                    i=2;
                    j=0;
                    for(int o=title.length;o<data.length;o++){
                        sheets.addCell(new Label(j,i,data[o].toString(),wcf_left));
                        j++;
                    }
                }
                j++;
            }
            i++;
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

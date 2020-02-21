<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" src="${staticPath }/static/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/js/warehouse.js" charset="utf-8"></script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="crossDatabaseAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>货物名称</td>
                    <td><input name="cdName" type="text" placeholder="请输入货物名称" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]',novalidate:true"/></td>
                    <td>货物型号</td>
                    <td><input name="cdSkumodel" type="text" placeholder="请输入货物型号" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]',novalidate:true"/></td>
                </tr>
                <tr>
                    <td>发货数量</td>
                    <td><input name="cdNum" type="text" placeholder="请输入发货数量" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]',novalidate:true"></td>
                    <td>仓库</td>
                    <!--
                    <td><input name="cdWhid" type="text" placeholder="请输入仓库编号" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]',novalidate:true"/></td>
                    -->
                    <td><input name="cdWhid" id="selectCombobox" class="easyui-combobox"  data-options="required:true,novalidate:true" /></td>
                </tr>
                <tr>
                    <td>发货时间</td>
                    <td><input name="byTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
                    <td>货物体积</td>
                    <td><input name="cdVolume" type="text" placeholder="请输入货物体积" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]',novalidate:true"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
$(function () {
    $("#selectCombobox").combobox({
        url:'${path}/godown/godownComboBox',
        method:'GET',
        valueField:'id',
        textField:'text',
        editable:false,
        panelHeight:'auto',
        onLoadSuccess:function () {
            //得到下拉框所有数据
            var  data = $(this).combobox('getData');
            if(data > 0){
                $(this).combobox('select','请选择仓库');
            }
        }
    })

    $("#crossDatabaseAddForm").from({
        url:"${path}/crossDatabase/add",
        onSubmit:function () {
            progressLoad();
            var isValid = $(this).from('enableValidation').from('validate');
            if (!isValid) {
                progressClose();
            }
            return isValid;
        },
        success:function (result) {
            result = $.parseJSON(result);
            if (result.success){
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }else{
                parent.$.messager.alert("错误",result.msg,'error');
            }
        }
    })
})
</script>

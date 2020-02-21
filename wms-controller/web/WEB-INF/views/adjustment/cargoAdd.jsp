<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#selectCombobox').combobox({
            url:'${path}/godown/godownComboBox',
            method: 'get',
            valueField: 'id',
            textField: 'text',
            editable:false,
            panelHeight: 'auto',
            onLoadSuccess:function () {
                var data = $('#selectCombobox').combobox('getData');
                if(data.length > 0){
                    $('#selectCombobox').combobox('select','请选择仓库...')
                }
            }
        })
        $('#carogAddForm').form({
            url : '${path }/cargo/insert',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('enableValidation').form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });

    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="carogAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>货物名称</td>
                    <td><input name="cName" type="text" placeholder="请输入货物名称" class="easyui-validatebox"  data-options="required:true,novalidate:true" value=""></td>
                    <td>货主</td>
                    <td><input name="cStorerid" type="text" placeholder="请输入货主姓名" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                </tr>
                <tr>
                    <td>供应商</td>
                    <td><input name="cSupplierid" type="text" placeholder="请输入供应商" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                    <td>仓库编码</td>
                    <td><input name="cWhid" id="selectCombobox" class="easyui-combobox"  data-options="required:true,novalidate:true" /></td>
                </tr>
                <tr>
                    <td>数量</td>
                    <td><input name="cNum" type="text" class="easyui-numberbox" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                    <td>总货毛重</td>
                    <td><input name="cTotalweight" type="text" class="easyui-numberbox" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                </tr>
                <tr>
                    <td>总货体积</td>
                    <td><input name="cTotalvolume" type="text" class="easyui-numberbox" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                    <td>入库时间</td>
                    <td><input name="cReceivedate" placeholder="请选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>货物型号</td>
                    <td><input name="cSkumodel" type="text" placeholder="请输入货物型号" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                    <td>货主号码</td>
                    <td><input name="cPhone" type="text" placeholder="请输入货主号码" class="easyui-validatebox" data-options="required:true,novalidate:true" value=""></td>
                </tr>
            </table>
        </form>
    </div>
</div>

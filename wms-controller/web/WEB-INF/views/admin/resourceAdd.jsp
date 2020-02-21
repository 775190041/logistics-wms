<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function () {
        /** 组合树（combotree）把选择控件和下拉树结合起来 */
        $('#pid').combotree({
            url:"${path}/resource/allTree",
            parentField : 'pid',
            lines:true,
            panelHeight: 'auto'
        });
        $("#resourceAddForm").form({
            url:'${path}/resource/add',
            //表单提交时触发
            onSubmit:function () {
                progressLoad();
                //进行表单字段验证，当全部字段都有效时返回 true,否则返回false，可用于阻止提交动作
                var isValid = $(this).form("validate")
                if(!isValid){
                    progressClose();
                }
                return isValid;
            },success:function (result) {
                progressClose();
                //将符合标准格式的的JSON字符串转为与之对应的JavaScript对象。
                result= $.parseJSON(result);
                if (result.success){
                    //之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
                        parent.$.modalDialog.openner_treeGrid.treegrid("reload");
                    parent.layout_west_tree.tree("reload");
                    parent.$.modalDialog.handler.dialog("close");
                }
            }
        })
    })
</script>


<div style="padding: 3px;">
    <form id="resourceAddForm" method="post">
        <table class="grid">
            <tr>
                <td>资源名称</td>
                <td><input name="name" type="text" placeholder="请输入资源名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                <td>资源类型</td>
                <td>
                    <select name="resourceType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源路径</td>
                <td><input name="url" type="text" placeholder="请输入资源路径" class="easyui-validatebox span2" data-options="width:140,height:29" ></td>
                <td>排序</td>
                <td><input name="seq" value="0"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
            </tr>
            <tr>
                <td>菜单图标</td>
                <td ><input  name="icon" /></td>
                <td>状态</td>
                <td>
                    <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">正常</option>
                        <option value="1">停用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>上级资源</td>
                <td colspan="3">
                    <select id="pid" name="pid" style="width: 200px; height: 29px;"></select>
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>

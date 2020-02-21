<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>仓库管理</title>
    <script type="text/javascript">
        var dataGrid;
        var organizationTree;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url : '${path }/godown/dataGrid',
                fit : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                singleSelect : true,
                idField : 'goid',
                sortName : 'goid',
                sortOrder : 'asc',
                pageSize : 20,
                pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
                columns : [ [ {
                    width : '80',
                    title : '仓库名称',
                    field : 'goWhid',
                    sortable : true
                }, {
                    width : '80',
                    title : '管理员',
                    field : 'userList',
                    sortable : true,
                    formatter : function(value, row, index) {
                        var roles = [];
                        for(var i = 0; i< value.length; i++) {
                            roles.push(value[i].name);
                        }
                        return(roles.join('\n'));
                    }
                },{
                    width : '130',
                    title : '仓库容积(m³)',
                    field : 'goVolume',
                    sortable : true
                }, {
                    width : '130',
                    title : '已用容积(m³)',
                    field : 'goUsevolume',
                    sortable : true,
                }, {
                    width : '130',
                    title : '可用容积(m³)',
                    field : 'goRdvolume',
                    sortable : true
                },{
                    width : '80',
                    title : '状态',
                    field : 'goStatus',
                    sortable : true,
                    formatter : function(value) {
                        switch (value) {
                            case 0:
                                return '正常';
                            case 1:
                                return '停用';
                            case 2:
                                return '已满';
                        }
                    }
                },{
                    field : 'action',
                    title : '操作',
                    width : 200,
                    formatter : function (value,row) {
                        var str = '';
                        if (row.goUsevolume == 0){
                            <shiro:hasPermission name="/godown/edit">
                            str += $.formatString('<a style="height: 24px;" href="javascript:void(0)" style="height:25px;" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >扩建</a>', row.goId);
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/godown/delete">
                            if(row.goStatus == 0){
                                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                str += $.formatString('<a style="height: 24px;" href="javascript:void(0)" style="height:25px;" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >停用</a>', row.goId);
                            }else if(row.goStatus == 1) {
                                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                str += $.formatString('<a style="height: 24px;" href="javascript:void(0)" style="height:25px;" class="user-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'icon-ok\'" onclick="deleteFun(\'{0}\');" >启用</a>', row.goId);
                            }
                            </shiro:hasPermission>
                        }
                        return str;
                    }
                }]],
                onLoadSuccess:function () {
                    $('.user-easyui-linkbutton-edit').linkbutton({text:'扩建',plain:true,iconCls:'icon-edit'});
                    $('.user-easyui-linkbutton-del').linkbutton({text:'停用',plain:true,iconCls:'icon-del'});
                    $('.user-easyui-linkbutton-ok').linkbutton({text:'启用',plain:true,iconCls:'icon-ok'});
                },
                toolbar : '#toolbar'
            });
        })

        function searchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }

        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
        
        function addFun() {
            parent.$.modalDialog({
                title : '添加仓库',
                width : 400,
                height : 200,
                href : '${path }/godown/addPage',
                buttons : [ {
                    text : '添加',
                    handler : function() {
                        //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        parent.$.modalDialog.openner_dataGrid = dataGrid;
                        var f = parent.$.modalDialog.handler.find('#godownAddForm');
                        f.submit();
                    }
                } ]
            })
        }

        function deleteFun(id) {
            if (id == undefined) {//点击右键菜单才会触发这个
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            } else {//点击操作里面的删除图标会触发这个
                dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
            }
            parent.$.messager.confirm('询问', '您是否要改变仓库当前状态？', function(estimate) {
                if(estimate){
                    progressLoad();
                    var rows = dataGrid.datagrid('getSelections');
                    $.post('${path}/godown/delete',{id:id,'status':rows[0].goStatus},function (result) {
                        if(result.success){
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid("reload");
                        }
                        progressClose();
                    },"JSON");
                }
            });
        }

        function editFun(id) {
            if (id == undefined){
                var rows =dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }else{
                //取消选择所有当前页中所有的行。
                dataGrid.datagrid('unselectAll').datagrid('unselectAll');
            }
            parent.$.modalDialog({
                title : '仓库扩建',
                width : 280,
                height : 150,
                href : '${path }/godown/editPage?id=' + id,
                buttons:[{
                    text: '确定',
                    handler : function () {
                        //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        parent.$.modalDialog.openner_dataGrid = dataGrid;
                        //变量f 值设定为模板值                   对话模型处理器 查找
                        var f = parent.$.modalDialog.handler.find('#godownEditForm');
                        //已提交
                        f.submit();
                    }
                }]
            })
        }

    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
    <form id="searchForm">
        <table>
            <tr>
                <th>管理员查询：</th>
                <td><input name="name" placeholder="请输入管理员姓名"/></td>
                <th>仓库查询:</th>
                <td>
                    <input name="goWhid" placeholder="请输入仓库名称"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div data-options="region:'center',border:true,title:'用户列表'" >
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/godown/add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </shiro:hasPermission>
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <title>资源管理</title>
    <script type="text/javascript">
        var treeGrid;
        $(function() {
            /* 树形网格（treegrid）允许您创建可定制的、可异步展开的行，并以多列形式显示分层数据。*/
            treeGrid = $('#treeGrid').treegrid({
                url : '${path }/resource/treeGrid',   //初始化时数据展示要请求路径(资源管理列表)
                idField : 'id',   //定义标识树节点的键名字段。必需 且默认值为空。
                treeField : 'name',//定义树节点的字段 必需
                parentField : 'pid',
                fit : true,   // 自动补全
                fitColumns : false,
                border : false,
                frozenColumns : [ [ {
                    title : '编号',
                    field : 'id',
                    width : 40
                } ] ],
                columns : [ [ {
                    field : 'name',
                    title : '资源名称',
                    width : 210
                }, {
                    field : 'resourceType',
                    title : '资源类型',
                    width : 80,
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 0:
                                return '菜单';
                            case 1:
                                return '按钮';
                        }
                    }
                }, {
                    field : 'pid',
                    title : '上级资源ID',
                    width : 150,
                    hidden : true
                }, {
                    field : 'status',
                    title : '状态',
                    width : 40,
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 0:
                                return '正常';
                            case 1:
                                return '停用';
                        }
                    }
                }, {
                    field : 'action',
                    title : '操作',
                    width : 200,
                    formatter : function(value, row, index) {
                        var str = '';
                        <shiro:hasPermission name="/resource/edit">
                        str += $.formatString('<a style="height: 24px;" href="javascript:void(0)" class="resource-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/resource/delete">
                        str += '&nbsp;&nbsp;&nbsp;&nbsp;| &nbsp;&nbsp;&nbsp;&nbsp;';
                        str += $.formatString('<a style="height: 24px;" href="javascript:void(0)" class="resource-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                        return str;
                    }
                } ] ],
                onLoadSuccess:function(data){
                    $('.resource-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                    $('.resource-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
                },
                toolbar : '#toolbar'
            });
        });

        function editFun(id) {
            if (id != undefined) {
                treeGrid.treegrid('select', id);
            }
            var node = treeGrid.treegrid('getSelected'); //获取选中列
            if (node) {
                parent.$.modalDialog({
                    title : '编辑',
                    width : 600,
                    height : 550,
                    href : '${path }/resource/editPage?id=' + node.id,
                    buttons : [ {
                        text : '确定',
                        handler : function() {
                            parent.$.modalDialog.openner_treeGrid = treeGrid;
                            //因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                            //获取编辑框
                            var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                            //关闭编辑框
                            f.submit();
                        }
                    } ]
                });
            }
        }

        function deleteFun(id) {
            if (id != undefined) {
                treeGrid.treegrid('select', id);
            }
            var node = treeGrid.treegrid('getSelected');
            if (node) {
                parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
                    if (b) {
                        progressLoad();
                        $.post('${pageContext.request.contextPath}/resource/delete', {
                            id : node.id
                        }, function(result) {
                            if (result.success) {
                                parent.$.messager.alert('提示', result.msg, 'info');
                                treeGrid.treegrid('reload');
                                parent.layout_west_tree.tree('reload');
                            }
                            progressClose();
                        }, 'JSON');
                    }
                });
            }
        }

        function addFun() {
            parent.$.modalDialog({
                title : '添加',
                width : 500,
                height : 350,
                href : '${path }/resource/addPage',
                buttons : [ {
                    text : '添加',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#resourceAddForm');
                        f.submit();
                    }
                } ]
            });
        }
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="treeGrid"></table>
    </div>
</div>

<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/resource/add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </shiro:hasPermission>
</div>
</body>
</html>
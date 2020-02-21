<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ page isELIgnored="false" %>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
    <link rel="stylesheet" href="/static/3d/css/index.css" />
<%--    <!--/*外层最大容器*/-->
    <div class="wrap">
        <!--	/*包裹所有元素的容器*/-->
        <div class="cube">
            <!--前面图片 -->
            <div class="out_front">
                <img src="/static/3d/img/1.jpg"  class="pic"/>
            </div>
            <!--后面图片 -->
            <div class="out_back">
                <img src="/static/3d/img/2.jpg"  class="pic"/>
            </div>
            <!--左图片 -->
            <div class="out_left">
                <img src="/static/3d/img/3.jpg"  class="pic"/>
            </div>
            <div class="out_right">
                <img src="/static/3d/img/4.jpg"  class="pic"/>
            </div>
            <div class="out_top">
                <img src="/static/3d/img/5.jpg"  class="pic"/>
            </div>
            <div class="out_bottom">
                <img src="/static/3d/img/6.jpg"  class="pic"/>
            </div>
            <!--小正方体 -->
            <span class="in_front">
				                            <img src="/static/3d/img/7.jpg" class="in_pic" />
			                            </span>
            <span class="in_back">
                                            <img src="/static/3d/img/8.jpg" class="in_pic" />
                                        </span>
            <span class="in_left">
                                        <img src="/static/3d/img/9.jpg" class="in_pic" />
                                        </span>
            <span class="in_right">
                                            <img src="/static/3d/img/10.jpg" class="in_pic" />
                                        </span>
            <span class="in_top">
                                            <img src="/static/3d/img/11.jpg" class="in_pic" />
                                        </span>
            <span class="in_bottom">
                                            <img src="/static/3d/img/12.jpg" class="in_pic" />
                                        </span>
        </div>
    </div>--%>
<script type="text/javascript">
    var index_layout;
    var index_tabs;
    var layout_west_tree;

    $(function() {
        var mesint = '${mesint}';
        if(mesint != 1){
       		window.location.href = '${path }/notice/message';
        }
        var s = 0;
        index_layout = $('#index_layout').layout({
            fit : true
        });
        index_tabs = $('#index_tabs').tabs({
            fit : true,
            border : false,
            tools : [{
                iconCls : 'icon-home',
                handler : function() {
                    index_tabs.tabs('select', 0);
                }
            }, {
                iconCls : 'icon-refresh',
                handler : function() {
                    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                    var tab = index_tabs.tabs('getTab', index);
                    var options = tab.panel('options');
                    if (options.content) {
                        index_tabs.tabs('update', {
                            tab: tab,
                            options: {
                                content: options.content
                            }
                        });
                    } else {
                        tab.panel('refresh', options.href);
                    }
                }
            }, {
                iconCls : 'icon-del',
                handler : function() {
                    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                    var tab = index_tabs.tabs('getTab', index);
                    if (tab.panel('options').closable) {
                        index_tabs.tabs('close', index);
                    }
                }
            } ]
        });
        
        layout_west_tree = $('#layout_west_tree').tree({
            url : '${path }/resource/tree',
            parentField : 'pid',
            lines : true,
            expanded:false,
            onClick : function(node) {
                if (node.attributes.indexOf("http") >= 0) {
                    var url = node.attributes;
                    addTab({
                        title : node.text,
                        url : url,
                        iconCls : node.iconCls
                    });
                } else if (node.attributes) {
                    var url = '${path }' + node.attributes;
                    addTab({
                        title : node.text,
                        url : url,
                        iconCls : node.iconCls
                    });
                }
            }
        });
    });

    function addTab(params) {
        var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
        var t = $('#index_tabs');
        var opts = {
            title : params.title,
            closable : true,
            iconCls : params.iconCls,
            content : iframe,
            border : false,
            fit : true
        };
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
        }
    }

    function logout(){
        $.messager.confirm('提示','确定要退出?',function(r){
            if (r){
                progressLoad();
                $.post('${path }/logout', function(result) {
                    if(result.success){
                        progressClose();
                        window.location.href='${path }';
                    }
                }, 'json');
            }
        });
    }

    function editUserPwd() {
        parent.$.modalDialog({
            title : '修改密码',
            width : 300,
            height : 250,
            href : '${path }/user/editPwdPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
                    f.submit();
                }
            } ]
        });
    }

</script>
</head>
<body>
    <div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
        <img src="${staticPath }/static/style/images/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
    </div>
    <div id="index_layout" style="background:red;">
        <div data-options="region:'north',border:false" style=" overflow: hidden; ">
            <div>
                <span style="float: right; padding-right: 20px; color: #333">
                      欢迎<span style="color: red; "><b><shiro:principal></shiro:principal></b></span>&nbsp;&nbsp;
                    <shiro:hasPermission name="/user/editPwdPage">
                        <a href="javascript:void(0)" onclick="editUserPwd()" class="easyui-linkbutton" plain="true" icon="icon-edit" >修改密码</a>
                    </shiro:hasPermission>&nbsp;&nbsp;
                    <a href="javascript:void(0)" onclick="logout()" class="easyui-linkbutton" plain="true" icon="icon-clear">安全退出</a>
                    <br />
                    上次登录时间:${time}
                </span>
                <div>
                	<span class="header" style="float: left"></span>
                </div>
                <div style="width:700px;float:left;padding-top:5px;">
			    	<marquee vspace="5px" direction="left" width="100%" onmouseout="this.start()" onmouseover="this.stop()" scrollamount="4" scrolldelay="1" style="font:bold 20px '黑体';color:red;">
			    		<img src="${staticPath }/static/style/images/b143f09fd2aa8bbc5462805be70cf44e.gif" style="height:35px;"/>&nbsp;
			    		 最新公告  内容:${notice.text}          发布时间:${notice.stringtime};
			    	</marquee>
			    </div>
            </div>
        </div>
        <div data-options="region:'west',split:true" title="菜单" style="width:200px; overflow: hidden;overflow-y:auto; padding:0px">
            <div class="well well-small" style="padding: 5px 5px 5px 5px;">
                <ul id="layout_west_tree"></ul>
            </div>
        </div>
        <div data-options="region:'center'" style="overflow: hidden;">
            <div id="index_tabs" style="overflow: hidden;">
                <div title="首页" data-options="iconCls:'l-btn-icon icon-home',border:false" style="overflow: hidden;">
                    <div>
<%--
                        <div style="float: right;">
                            <iframe allowtransparency="true" frameborder="0" width="185" height="74" scrolling="no" src="//tianqi.2345.com/plugin/widget/index.htm?s=1&z=1&t=0&v=0&d=1&bd=0&k=000000&f=&q=1&e=1&a=1&c=54511&w=185&h=74&align=center"></iframe>
                        </div>
--%>

                        <!--/*外层最大容器*/-->
                        <div class="wrap">
                            <!--	/*包裹所有元素的容器*/-->
                            <div class="cube">
                                <!--前面图片 -->
                                <div class="out_front">
                                    <img src="/static/3d/img/1.jpg"  class="pic"/>
                                </div>
                                <!--后面图片 -->
                                <div class="out_back">
                                    <img src="/static/3d/img/2.jpg"  class="pic"/>
                                </div>
                                <!--左图片 -->
                                <div class="out_left">
                                    <img src="/static/3d/img/3.jpg"  class="pic"/>
                                </div>
                                <div class="out_right">
                                    <img src="/static/3d/img/4.jpg"  class="pic"/>
                                </div>
                                <div class="out_top">
                                    <img src="/static/3d/img/5.jpg"  class="pic"/>
                                </div>
                                <div class="out_bottom">
                                    <img src="/static/3d/img/6.jpg"  class="pic"/>
                                </div>
                                <!--小正方体 -->
                                <span class="in_front">
				                            <img src="/static/3d/img/7.jpg" class="in_pic" />
			                            </span>
                                <span class="in_back">
                                            <img src="/static/3d/img/8.jpg" class="in_pic" />
                                        </span>
                                <span class="in_left">
                                        <img src="/static/3d/img/9.jpg" class="in_pic" />
                                        </span>
                                <span class="in_right">
                                            <img src="/static/3d/img/10.jpg" class="in_pic" />
                                        </span>
                                <span class="in_top">
                                            <img src="/static/3d/img/11.jpg" class="in_pic" />
                                        </span>
                                <span class="in_bottom">
                                            <img src="/static/3d/img/12.jpg" class="in_pic" />
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >Spring MVC Shiro mybatis Mysql <a href="http://localhost:8080/notice/message" target="_blank">风行物流仓库管理系统</a></div>
    </div>
</body>
</html>
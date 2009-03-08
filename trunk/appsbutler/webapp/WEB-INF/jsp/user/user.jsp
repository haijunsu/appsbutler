<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Mini-Web 帐号管理</title>
<%@ include file="/common/meta.jsp"%>
</head>

<body>
<div id="menu">
<h3>帐号列表 <a href="${ctx}/user/role.action">角色列表</a> 
    <a href="${ctx}/j_spring_security_logout">退出登录</a></h3>
</div>

<div id="message"><s:actionmessage theme="mytheme"/></div>

<div id="content">
<table class="listView">
	<tr>
		<th><a href="${ctx}/user/user.action?page.orderBy=loginName&page.order=
		<s:if test="page.orderBy=='loginName'">${page.inverseOrder}</s:if><s:else>desc</s:else>
		"><b>登录名</b></a></th>
		<th><a href="${ctx}/user/user.action?page.orderBy=name&page.order=
		<s:if test="page.orderBy=='name'">${page.inverseOrder}</s:if><s:else>desc</s:else>
		"><b>姓名</b></a></th>
		<th><a href="${ctx}/user/user.action?page.orderBy=email&page.order=
		<s:if test="page.orderBy=='email'">${page.inverseOrder}</s:if><s:else>desc</s:else>
		"><b>电邮</b></a></th>
		<th><b>角色</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="page.result">
		<tr>
			<td>${loginName}&nbsp;</td>
			<td>${name}&nbsp;</td>
			<td>${email}&nbsp;</td>
			<td>${roleNames}&nbsp;</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="ROLE_MODIFY_USER">
					<a href="user!input.action?id=${id}">修改</a>、
					<a href="user!delete.action?id=${id}">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer" style="margin-top:10px">
	第${page.pageNo}页, 共${page.totalPages}页 
	<s:if test="page.hasPre">
		<a href="user.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
	</s:if>
	<s:if test="page.hasNext">
		<a href="user.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
	</s:if>
	<security:authorize ifAnyGranted="ROLE_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
	</security:authorize>
</div>

<div id="comment" style="margin-top:20px">本页面为单纯的白板，各式Table Grid组件的应用见Showcase项目(开发中).</div>
</body>
</html>

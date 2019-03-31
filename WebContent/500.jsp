<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对不起，您访问的页面不存在 请转到首页进入</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<STYLE type=text/css>BODY {
	FONT-SIZE: 14px; FONT-FAMILY: Tahoma
}
TD {
	FONT-SIZE: 14px; FONT-FAMILY: Tahoma
}
A:link {
	COLOR: #636363; TEXT-DECORATION: none
}
A:visited {
	COLOR: #838383; TEXT-DECORATION: none
}
A:hover {
	COLOR: #a3a3a3; TEXT-DECORATION: underline
}
BODY {
	BACKGROUND-COLOR: #cccccc
}
</STYLE>
<META content="MSHTML 6.00.2900.2523" name=GENERATOR></HEAD>
<BODY style="TABLE-LAYOUT: fixed; WORD-BREAK: break-all" topMargin=10 
marginwidth="10" marginheight="10">
<TABLE height="95%" cellSpacing=0 cellPadding=0 width="100%" align=center 
border=0>
  <TBODY>
  <TR vAlign=center align=middle>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 width=468 bgColor=#ffffff border=0>
        <TBODY>
        <TR>
          <TD width=20 background="<%=basePath%>images/rbox_1.gif"
height=20></TD>
          <TD width=108 background="<%=basePath%>images/rbox_2.gif"
          height=20></TD>
          <TD width=56><IMG height=20 src="<%=basePath%>images/rbox_ring.gif"
            width=56></TD>
          <TD width=100 background="<%=basePath%>images/rbox_2.gif"></TD>
          <TD width=56><IMG height=20 src="<%=basePath%>images/rbox_ring.gif"
            width=56></TD>
          <TD width=108 background="<%=basePath%>images/rbox_2.gif"></TD>
          <TD width=20 background="<%=basePath%>images/rbox_3.gif"
        height=20></TD></TR>
        <TR>
          <TD align=left background="<%=basePath%>images/rbox_4.gif"
          rowSpan=2></TD>
          <TD align=middle bgColor=#eeeeee colSpan=5 height=50>
            <P><strong><font color=#ba1c1c>HTTP500错误</font></strong>:</P>
            <p>没有找到您要访问的页面，请检查您是否输入正确URL。</p>
            <P><strong><br>
                <br>
            </strong></P></TD>
          <TD align=left background="<%=basePath%>images/rbox_6.gif"
          rowSpan=2></TD></TR>
        <TR>
          <TD align=left colSpan=5 height=80>请尝试以下操作：
            <P>・如果您已经在地址栏中输入该网页的地址，请确认其拼写正确。<BR>
              ・打开该站点主页，然后查找指向您感兴趣信息的链接。<BR>
              ・单击<A href="javascript:history.back(1)"><font color="#BA1C1C">后退</font></A>链接，尝试其他链接。<BR>
              <!-- ・单击单击<A href="javascript:doSearch()"><font color="#BA1C1C">搜索</font></A>链接，寻找Internet上的信息。</P> -->
            <P align="right">如果您在浏览本站时，多次出现此错误，请与管理员联系</P></TD></TR>
        <TR>
          <TD align=left background="<%=basePath%>images/rbox_7.gif"
          height=20></TD>
          <TD align=left background="<%=basePath%>images/rbox_8.gif" colSpan=5
          height=20></TD>
          <TD align=left background="<%=basePath%>images/rbox_9.gif" 
          height=20></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></BODY></HTML>

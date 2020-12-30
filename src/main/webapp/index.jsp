<%@ page import="chapter3.hello.HelloSpring" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<html>
<body>
<h2>Hello World!</h2>

<%
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    HelloSpring helloSpring = context.getBean(HelloSpring.class);
    out.println(helloSpring.sayHello("Root Context"));
%>

</body>
</html>

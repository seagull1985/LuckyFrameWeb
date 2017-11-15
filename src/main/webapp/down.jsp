<%	response.setContentType("application/unknown;charset=gbk");
	response.setHeader("Content-disposition","filename=" + (String) request.getAttribute("filename")); 
	out.println((String) request.getAttribute("data"));
%>
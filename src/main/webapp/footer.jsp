<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function gouserInfo(){
	var status = document.getElementById("loginstatus").value;
	if(status=="false"){
		if(window.confirm("你未登录哦，要先去首页登录吗？")){
			var url = '/progressus/signin.html';
			window.location.href=url;
		}else{
			return false; 
		} 	
	}else{
		var url = '/userInfo/list.do';
		window.location.href=url;
	}
}

</script>

		<div class="footer2" style="background-color:rgba(204, 204, 204,0.5);">
			<div class="container">
				<div class="row">
					
					<div class="col-md-6 widget">
						<div class="widget-body">
							<p class="simplenav">
								<a href="#">主页</a> | 
								<a href="/progressus/about.jsp">关于</a> |
								<a href="/progressus/help.jsp">帮助</a> |
								<a href="/progressus/contact.jsp">联系我们</a>|
								<a href="#" onclick="gouserInfo()">系统管理</a>
							</p>
						</div>
					</div>

					<div class="col-md-6 widget">
						<div class="widget-body">
							<p class="text-right">
								Copyright &copy; 2017, LuckyFrame. Designed by <a href="javascript:window.open('http://www.luckyframe.cn')">LuckyFrame</a>
							</p>
						</div>
					</div>

			</div>
		</div>
		</div>

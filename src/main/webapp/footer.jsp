<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
function gouserInfo(){
	var status = document.getElementById("loginstatus").value;
	if(status=="false"){
		if(window.confirm("你未登录哦，要先去首页登录吗？")){
			var url = '/progressus/signin.jsp';
			window.location.href=url;
		}else{
			return false; 
		} 	
	}else{
		var url = "/userlogin/permissionboolean.do?permissioncode=ui_4";
		jQuery.getJSON(url, null, function call(result) {
			if (result.data[0] == null) {
				toastr.warning('你好，当前用户无权限进入系统管理，请联系管理员！'); 
				return false;
			} else if (result.data[0] == "true") {
				var url = '/userInfo/load.do';
				window.location.href=url;
			}else{
				toastr.warning('你好，当前用户无权限进入系统管理，请联系管理员！'); 
				return false;
			}
		});
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
								<a href="javascript:window.open('http://www.luckyframe.cn')">官网</a>|
								<a href="javascript:window.open('http://www.luckyframe.cn/page/aboutme.html')">关于</a> |
								<a href="javascript:window.open('http://www.luckyframe.cn/book/yhsc/syschyy-24.html')">用户手册</a> |
								<a href="javascript:window.open('http://www.luckyframe.cn/allwz.html')">技巧分享</a>|
								<a href="#" onclick="gouserInfo()">系统管理</a>
							</p>
						</div>
					</div>

					<div class="col-md-6 widget">
						<div class="widget-body">
							<p class="text-right">
							    <!--此处版权信息不属于开源修改范围，严禁篡改，一旦发现保留诉讼权利 -->
								Copyright &copy; 2017, LuckyFrame.Designed by <a href="javascript:window.open('http://www.luckyframe.cn')">LuckyFrame V2.6</a>
							</p>
						</div>
					</div>

			</div>
		</div>
		</div>

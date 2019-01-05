/**
 * 说明：JavaScript 函数库
 * 日期：2004-06-02
 * 修改：2004-12-11 文本框验证同时支持多行文本框
 */

var JSLib_RegExp_Mobile=/^(13|15|18|14)[0-9]{9}$/;							//手机号码
var JSLib_RegExp_Trim = /(^\s+)|(\s+$)/g;								// 实现Trim的正则表达式
var JSLib_RegExp_Int = /^(\+|-)?\d+$/;									// 整数
var JSLib_RegExp_Float = /^(\+|-)?\d+\.\d+$/;							// 浮点数（必须有小数点和小数）
var JSLib_RegExp_Date = /^([12]\d{3})([-\/\.])([01]?\d)\2([0-3]?\d)$/;	// 日期

var JSLib_RegExp_Num = /^\d+$/;											// 无符号整数
var JSLib_RegExp_StrNum = /^[A-Za-z0-9]+$/;								// 字符或数字
var JSLib_RegExp_Email = /^[\w-]+@[\w-]+\.[\w-]+\.?[\w-]*$/;			// email



JSLib_RegExp_Trim.compile(JSLib_RegExp_Trim);
JSLib_RegExp_Int.compile(JSLib_RegExp_Int);
JSLib_RegExp_Float.compile(JSLib_RegExp_Float);
JSLib_RegExp_Date.compile(JSLib_RegExp_Date);
JSLib_RegExp_Num.compile(JSLib_RegExp_Num);
JSLib_RegExp_StrNum.compile(JSLib_RegExp_StrNum);
JSLib_RegExp_Email.compile(JSLib_RegExp_Email);


//是否是合法的手机号码
String.prototype.isMobile = function() {
	return JSLib_RegExp_Mobile.test(this);
}

// 字符串全有数字组成
String.prototype.isNumStr = function() {
	return JSLib_RegExp_Num.test(this);
}

// 字符串全有数字、字母组成
String.prototype.isNumCharStr = function() {
	return JSLib_RegExp_StrNum.test(this);
}

// 是否email地址
String.prototype.isEmail = function() {
	return JSLib_RegExp_Email.test(this);
}

// 删除字符串前后空格
String.prototype.trim = function() {
    return this.replace(JSLib_RegExp_Trim, "");
}

// 计算字符串的长度(一个中文算2个字符)
String.prototype.size = function() {
	var size = 0;
	var length = this.length;
	var c;
	for (var i = 0; i < length; i ++) {
		c = this.charCodeAt(i);
		if (c < 127) {
			size ++;
		} else {
			size += 2;
		}
	}
	return size;
}

// 字符串是否整数(可前导正负号)
String.prototype.isInt = function() {
	return JSLib_RegExp_Int.test(this);
}

// 字符串是否浮点数(可前导正负号,必须有小数点)
String.prototype.isFloat = function() {
	return JSLib_RegExp_Float.test(this);
}

// 字符串是否数字(可前导正负号,可有小数点，如果有小数点，则小数点后应该有数字)
String.prototype.isNum = function() {
	return (this.isInt() || this.isFloat());
}

// 字符串是否合法日期(合法日期：yyyy-mm-dd,分隔符可以为-/.)
String.prototype.isDate = function() {
	var year,month,date,day;
	
	if (!JSLib_RegExp_Date.test(this)) return false;
	
	year  = parseInt(RegExp.$1,10);
	month = parseInt(RegExp.$3,10) - 1;
	date  = parseInt(RegExp.$4,10);
	day = new Date(year,month,date);
	
	return ((date == day.getDate()) && (month == day.getMonth()));
}

// 返回两个日期之间的时间间隔,以天为单位(date1-date2=?)
function dateDiff(date1,date2) {
	var year,month,date,day1,day2;

	JSLib_RegExp_Date.test(date1);
	year  = parseInt(RegExp.$1,10);
	month = parseInt(RegExp.$3,10) - 1;
	date  = parseInt(RegExp.$4,10);
	day1 = new Date(year,month,date);

	JSLib_RegExp_Date.test(date2);
	year  = parseInt(RegExp.$1,10);
	month = parseInt(RegExp.$3,10) - 1;
	date  = parseInt(RegExp.$4,10);
	day2 = new Date(year,month,date);

	return (day1.getTime() - day2.getTime()) / 86400000;
}

// 对一组复选框作反向选择操作(特别说明：如果checkbox只有一个，那么length为null)
function reverseSelect(checkboxs) {
	if (checkboxs != null) {
		var length = checkboxs.length;
		if (length == null) {
			if (!checkboxs.disabled) checkboxs.checked = !checkboxs.checked;
		} else {
			var checkbox;
			for (var i = 0; i < length; i ++) {
				checkbox = checkboxs[i];
				if (!checkbox.disabled) checkbox.checked = !checkbox.checked;
			}
		}
	}
}

// 一组单选钮或复选钮是否被选中至少一个
function isChecked(os) {
	if (os == null) return false;
	var length = os.length;
	if (length == null) return os.checked;
	for (var i = 0; i < length; i ++) {
		if (os[i].checked) return true;
	}
	return false;
}

// 返回被选中的单/复选钮(复选钮只返回第一个被选中的)
function getChecked(os) {
	var o = null;
	if (os != null) {
		var length = os.length;
		if (length == null) {
			if (os.checked) o = os;
		} else {
			for (var i = 0; i < length; i ++) {
				if (os[i].checked) {
					o = os[i];
					break;
				}
			}
		}
	}
	return o;
}

// 返回列表被选择的选项(option)
function getSelectedOption(select) {
	var index = select.selectedIndex;
	if (index == -1) {
		return null;
	} else {
		return select.options[index];
	}
}

// 检查检查框(单/复)是否被选中
function validateCheck(os,label) {
	if (os == null) {
		alert(label + "没有可供选择的选项。");
		return false;
	}
	if (!isChecked(os)) {
		alert("请选择" + label + "。");
		if (os.length == null) {
			if (!os.disabled) os.focus();
		} else {
			if (!os[0].disabled) os[0].focus();
		}
		return false;
	}
	return true;
}

// 检查列表是否被选择
function validateSelect(select,label,begin) {
	if (select.selectedIndex == -1) {
		alert(label + "没有可供选择的选项。");
		return false;
	} else {
		if (select.selectedIndex < begin) {
			alert("请选择" + label);
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 检查用户输入文本是否合法,返回值为true/fasle
 * arg[0]	要检查的文本框对象			Object.type = text
 * arg[1]	文本框标题					String
 * arg[2]	是否必须输入				boolean
 * arg[3]	最大长度（为零表示不限)		int
 * arg[4]	指定长度（为零表示不限)		int
 */
function validateStr() {
	
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type.indexOf("text") == -1) {
		alert("错误：参数错误，第一个参数必须是文本框对象。" + arguments[1]);
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	var mandatory = arguments[2];
	if (mandatory == null) mandatory = true;
	
	var maxLength = arguments[3];
	if (maxLength == null) maxLength = 0;
	
	var speLength = arguments[4];
	if (speLength == null) speLength = 0;
	
	textbox.value = textbox.value.trim();
	var value = textbox.value;
	if (mandatory && (value == "")) {
		alert(label + "不能为空，请重新输入。");
		textbox.focus();
		return false;
	}
	
	if ((maxLength > 0) && (value.size() > maxLength)) {
		alert(label + "长度过大，最多为" + maxLength + "个字符。");
		textbox.focus();
		return false;
	}
	
	if ((speLength > 0) && (value.size() != speLength)) {
		alert(label + "长度错误，应该为" + speLength + "个字符。");
		textbox.focus();
		return false;
	}

	return true;
}
/**
 * 检查用户输入文本是否合法(two),返回值为true/fasle
 * arg[0]	要检查的文本框对象			Object.type = text
 * arg[1]	文本框标题					String
 * arg[2]	是否必须输入				boolean
 * arg[3]	最大长度（为零表示不限)		int
 * arg[4]	最小长度					int
 */
function validateStrTwo() {
	
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type.indexOf("text") == -1) {
		alert("错误：参数错误，第一个参数必须是文本框对象。" + arguments[1]);
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	var mandatory = arguments[2];
	if (mandatory == null) mandatory = true;
	
	var maxLength = arguments[3];
	if (maxLength == null) maxLength = 0;
	
	var speLength = arguments[4];
	if (speLength == null) speLength = 0;
	
	textbox.value = textbox.value.trim();
	var value = textbox.value;
	if (mandatory && (value == "")) {
		alert(label + "不能为空，请重新输入。");
		textbox.focus();
		return false;
	}
	
	if ((maxLength > 0) && (value.size() > maxLength)) {
		alert(label + "长度过大，最多为" + maxLength + "个字符。");
		textbox.focus();
		return false;
	}
	
	if ((speLength > 0) && (value.size() < speLength)) {
		alert(label + "长度错误，应该为" + speLength + "（包含）个字符以上。");
		textbox.focus();
		return false;
	}

	return true;
}
/**
 * 检查用户输入是否整数
 * arg[0]	要检查的文本框对象				Object.type = text
 * arg[1]	文本框标题						String
 * arg[2]	是否必须输入					boolean
 * arg[3]	输入的最小值（null表示不限）	int
 * arg[4]	输入的最大值（null表示不限）	int
 */
function validateInt() {
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type != "text") {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	var mandatory = arguments[2];
	if (mandatory == null) mandatory = true;
	
	var min = arguments[3];
	var max = arguments[4];

	textbox.value = textbox.value.trim();
	var value = textbox.value;
	if (mandatory || (value != "")) {
		if (!value.isInt()) {
			alert(label + "应该是一个整数，请重新输入。");
			textbox.select();
			textbox.focus();
			return false;
		}
	}
	
	if (min != null && (min > parseInt(value,10))) {
		alert(label + "不能小于" + min + "，请重新输入。");
		textbox.select();
		textbox.focus();
		return false;
	}

	if (max != null && (max < parseInt(value,10))) {
		alert(label + "不能大于" + max + "，请重新输入。");
		textbox.select();
		textbox.focus();
		return false;
	}

	return true;
}

/**
 * 检查用户输入是否数字
 * arg[0]	要检查的文本框对象				Object.type = text
 * arg[1]	文本框标题						String
 * arg[2]	是否必须输入					boolean
 * arg[3]	输入的最小值（null表示不限）	int
 * arg[4]	输入的最大值（null表示不限）	int
 */
function validateNum() {
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type != "text") {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	var mandatory = arguments[2];
	if (mandatory == null) mandatory = true;
	
	var min = arguments[3];
	var max = arguments[4];

	textbox.value = textbox.value.trim();
	var value = textbox.value;
	if (mandatory || (value != "")) {
		if (!value.isNum()) {
			alert(label + "应该是一个数字，请重新输入。");
			textbox.select();
			textbox.focus();
			return false;
		}
	}
	
	if (min != null && (min > parseFloat(value))) {
		alert(label + "不能小于" + min + "，请重新输入。");
		textbox.select();
		textbox.focus();
		return false;
	}

	if (max != null && (max < parseFloat(value))) {
		alert(label + "不能大于" + max + "，请重新输入。");
		textbox.select();
		textbox.focus();
		return false;
	}

	return true;
}

/**
 * 检查用户输入是否合法日期
 * arg[0]	要检查的文本框对象				Object.type = text
 * arg[1]	文本框标题						String
 * arg[2]	是否必须输入					boolean
 */
function validateDate() {
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type != "text") {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	var mandatory = arguments[2];
	if (mandatory == null) mandatory = true;
	
	textbox.value = textbox.value.trim();
	var value = textbox.value;
	if (mandatory || (value != "")) {
		if (!value.isDate()) {
			alert(label + "不是一个合法的日期，正确的日期格式是YYYY-MM-DD，请重新输入。");
			textbox.select();
			textbox.focus();
			return false;
		}
	}
	return true;
}

/**
 * 检查用户输入的2个日期大小关系(第一个日期小于第二个日期，返回真，否则返回假并弹出警告框)
 * arg[0]	第一个文本框对象				Object.type = text
 * arg[1]	第二个文本框对象				Object.type = text
 * arg[2]	第一个日期提示					String
 * arg[2\3]	第二个日期提示					String
 */
function validateDateDiff() {
	if (arguments.length < 2) {
		alert("错误：参数不足，至少需要二个参数。");
		return false;
	}
	
	var textbox1 = arguments[0],textbox2 = arguments[1];
	
	if (textbox1 == null || textbox1.type != "text") {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	if (textbox2 == null || textbox2.type != "text") {
		alert("错误：参数错误，第二个参数必须是文本框对象。");
		return false;
	}
	
	var label1 = arguments[2],label2 = arguments[3];
	
	if (dateDiff(textbox1.value,textbox2.value) > 0) {
		if (label1 == null || label2 == null) {
			alert("错误：日期大小不符。");
		} else {
			alert(label1 + "应该小于" + label2 + "。");
		}
		textbox1.select();
		textbox1.focus();
		return false;
	} else {
		return true;
	}
}

/**
 * 写cookie
 * name			cookie名称
 * value		cookie值
 * expireDays	过期天数(和当天比较)，若设为负数，则可以删除cookie
 */
function setCookie(name,value,expireDays) {
	var date = new Date();
	date.setTime(date.getTime() + 86400000 * expireDays);	
	document.cookie = name + "=" + escape(value) + ";expires=" + date.toGMTString();
}

// 取得cookie值
function getCookie(name) {
	var search = name + "=";
	var cookies = document.cookie;
	if (cookies.length > 0) {
		var offset = cookies.indexOf(search);
		if (offset != -1) {
			offset += search.length;
			end = cookies.indexOf(";", offset);
			if (end == -1) end = cookies.length;
			return unescape(cookies.substring(offset, end));
		}
	}
	return "";
}


// 检查文本框输入是否只由字母数字组成
function validateNumCharStr() {
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type.indexOf("text") == -1) {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	
	if (!textbox.value.isNumCharStr()) {
		alert(label + "只能由字母和数字组成。");
		textbox.focus();
		return false;
	}

	return true;
}



// 检查文本框输入是否只由数字组成
function validateNumStr() {
	if (arguments.length == 0) {
		alert("错误：参数不足，至少需要一个参数。");
		return false;
	}
	
	var textbox = arguments[0];
	if (textbox == null || textbox.type.indexOf("text") == -1) {
		alert("错误：参数错误，第一个参数必须是文本框对象。");
		return false;
	}
	
	var label = arguments[1];
	if (label == null) label = "输入";
	if (!textbox.value.isNumStr()) {
		alert(label + "只能由数字组成。");
		textbox.focus();
		return false;
	}

	return true;
}

// 检查文本框输入是否email
function validateEmailStr(textbox) {
	if (textbox.value != "") {
		if (!textbox.value.isEmail()) {
			alert("请输入正确有效的EMAIL地址。");
			textbox.focus();
			return false;
		}
	}

	return true;
}



/**
 * 给pub.js的方法加壳
 */
var pub = {};


/**
 * 判断是否为空
 */
pub.isEmpty = function(obj){
	if(typeof obj != undefined && obj != '' && obj != null){
		return false;
	}
	return true;
}

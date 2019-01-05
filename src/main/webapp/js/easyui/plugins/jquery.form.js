/**
 * jQuery EasyUI 1.4.2
 * 
 * Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at info@jeasyui.com
 *
 */
(function($){
function _1(_2,_3){
var _4=$.data(_2,"form").options;
$.extend(_4,_3||{});
var _5=$.extend({},_4.queryParams);
if(_4.onSubmit.call(_2,_5)==false){
return;
}
$(_2).find(".textbox-text:focus").blur();
var _6="easyui_frame_"+(new Date().getTime());
var _7=$("<iframe id="+_6+" name="+_6+"></iframe>").appendTo("body");
_7.attr("src",window.ActiveXObject?"javascript:false":"about:blank");
_7.css({position:"absolute",top:-1000,left:-1000});
_7.bind("load",cb);
_8(_5);
function _8(_9){
var _a=$(_2);
if(_4.url){
_a.attr("action",_4.url);
}
var t=_a.attr("target"),a=_a.attr("action");
_a.attr("target",_6);
var _b=$();
try{
for(var n in _9){
var _c=$("<input type=\"hidden\" name=\""+n+"\">").val(_9[n]).appendTo(_a);
_b=_b.add(_c);
}
_d();
_a[0].submit();
}
finally{
_a.attr("action",a);
t?_a.attr("target",t):_a.removeAttr("target");
_b.remove();
}
};
function _d(){
var f=$("#"+_6);
if(!f.length){
return;
}
try{
var s=f.contents()[0].readyState;
if(s&&s.toLowerCase()=="uninitialized"){
setTimeout(_d,100);
}
}
catch(e){
cb();
}
};
var _e=10;
function cb(){
var f=$("#"+_6);
if(!f.length){
return;
}
f.unbind();
var _f="";
try{
var _10=f.contents().find("body");
_f=_10.html();
if(_f==""){
if(--_e){
setTimeout(cb,100);
return;
}
}
var ta=_10.find(">textarea");
if(ta.length){
_f=ta.val();
}else{
var pre=_10.find(">pre");
if(pre.length){
_f=pre.html();
}
}
}
catch(e){
}
_4.success(_f);
setTimeout(function(){
f.unbind();
f.remove();
},100);
};
};
function _11(_12,_13){
var _14=$.data(_12,"form").options;
if(typeof _13=="string"){
var _15={};
if(_14.onBeforeLoad.call(_12,_15)==false){
return;
}
$.ajax({url:_13,data:_15,dataType:"json",success:function(_16){
_17(_16);
},error:function(){
_14.onLoadError.apply(_12,arguments);
}});
}else{
_17(_13);
}
function _17(_18){
var _19=$(_12);
for(var _1a in _18){
var val=_18[_1a];
if(!_1b(_1a,val)){
if(!_1c(_1a,val)){
_19.find("input[name=\""+_1a+"\"]").val(val);
_19.find("textarea[name=\""+_1a+"\"]").val(val);
_19.find("select[name=\""+_1a+"\"]").val(val);
}
}
}
_14.onLoadSuccess.call(_12,_18);
_19.form("validate");
};
function _1b(_1d,val){
var cc=$(_12).find("input[name=\""+_1d+"\"][type=radio], input[name=\""+_1d+"\"][type=checkbox]");
if(cc.length){
cc._propAttr("checked",false);
cc.each(function(){
var f=$(this);
if(f.val()==String(val)||$.inArray(f.val(),$.isArray(val)?val:[val])>=0){
f._propAttr("checked",true);
}
});
return true;
}
return false;
};
function _1c(_1e,val){
var _1f=$(_12).find("[textboxName=\""+_1e+"\"],[sliderName=\""+_1e+"\"]");
if(_1f.length){
for(var i=0;i<_14.fieldTypes.length;i++){
var _20=_14.fieldTypes[i];
var _21=_1f.data(_20);
if(_21){
if(_21.options.multiple||_21.options.range){
_1f[_20]("setValues",val);
}else{
_1f[_20]("setValue",val);
}
return true;
}
}
}
return false;
};
};
function _22(_23){
$("input,select,textarea",_23).each(function(){
var t=this.type,tag=this.tagName.toLowerCase();
if(t=="text"||t=="hidden"||t=="password"||tag=="textarea"){
this.value="";
}else{
if(t=="file"){
var _24=$(this);
if(!_24.hasClass("textbox-value")){
var _25=_24.clone().val("");
_25.insertAfter(_24);
if(_24.data("validatebox")){
_24.validatebox("destroy");
_25.validatebox();
}else{
_24.remove();
}
}
}else{
if(t=="checkbox"||t=="radio"){
this.checked=false;
}else{
if(tag=="select"){
this.selectedIndex=-1;
}
}
}
}
});
var _26=$(_23);
var _27=$.data(_23,"form").options;
for(var i=_27.fieldTypes.length-1;i>=0;i--){
var _28=_27.fieldTypes[i];
var _29=_26.find("."+_28+"-f");
if(_29.length&&_29[_28]){
_29[_28]("clear");
}
}
_26.form("validate");
};
function _2a(_2b){
_2b.reset();
var _2c=$(_2b);
var _2d=$.data(_2b,"form").options;
for(var i=_2d.fieldTypes.length-1;i>=0;i--){
var _2e=_2d.fieldTypes[i];
var _2f=_2c.find("."+_2e+"-f");
if(_2f.length&&_2f[_2e]){
_2f[_2e]("reset");
}
}
_2c.form("validate");
};
function _30(_31){
var _32=$.data(_31,"form").options;
$(_31).unbind(".form");
if(_32.ajax){
$(_31).bind("submit.form",function(){
setTimeout(function(){
_1(_31,_32);
},0);
return false;
});
}
$(_31).bind("_change.form",function(e,t){
_32.onChange.call(this,t);
}).bind("change.form",function(e){
var t=e.target;
if(!$(t).hasClass("textbox-text")){
_32.onChange.call(this,t);
}
});
_33(_31,_32.novalidate);
};
function _34(_35,_36){
_36=_36||{};
var _37=$.data(_35,"form");
if(_37){
$.extend(_37.options,_36);
}else{
$.data(_35,"form",{options:$.extend({},$.fn.form.defaults,$.fn.form.parseOptions(_35),_36)});
}
};
function _38(_39){
if($.fn.validatebox){
var t=$(_39);
t.find(".validatebox-text:not(:disabled)").validatebox("validate");
var _3a=t.find(".validatebox-invalid");
_3a.filter(":not(:disabled):first").focus();
return _3a.length==0;
}
return true;
};
function _33(_3b,_3c){
var _3d=$.data(_3b,"form").options;
_3d.novalidate=_3c;
$(_3b).find(".validatebox-text:not(:disabled)").validatebox(_3c?"disableValidation":"enableValidation");
};
$.fn.form=function(_3e,_3f){
if(typeof _3e=="string"){
this.each(function(){
_34(this);
});
return $.fn.form.methods[_3e](this,_3f);
}
return this.each(function(){
_34(this,_3e);
_30(this);
});
};
$.fn.form.methods={options:function(jq){
return $.data(jq[0],"form").options;
},submit:function(jq,_40){
return jq.each(function(){
_1(this,_40);
});
},load:function(jq,_41){
return jq.each(function(){
_11(this,_41);
});
},clear:function(jq){
return jq.each(function(){
_22(this);
});
},reset:function(jq){
return jq.each(function(){
_2a(this);
});
},validate:function(jq){
return _38(jq[0]);
},disableValidation:function(jq){
return jq.each(function(){
_33(this,true);
});
},enableValidation:function(jq){
return jq.each(function(){
_33(this,false);
});
}};
$.fn.form.parseOptions=function(_42){
var t=$(_42);
return $.extend({},$.parser.parseOptions(_42,[{ajax:"boolean"}]),{url:(t.attr("action")?t.attr("action"):undefined)});
};
$.fn.form.defaults={fieldTypes:["combobox","combotree","combogrid","datetimebox","datebox","combo","datetimespinner","timespinner","numberspinner","spinner","slider","searchbox","numberbox","textbox"],novalidate:false,ajax:true,url:null,queryParams:{},onSubmit:function(_43){
return $(this).form("validate");
},success:function(_44){
},onBeforeLoad:function(_45){
},onLoadSuccess:function(_46){
},onLoadError:function(){
},onChange:function(_47){
}};
})(jQuery);


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
function _1(_2){
var _3=$("<div class=\"slider\">"+"<div class=\"slider-inner\">"+"<a href=\"javascript:void(0)\" class=\"slider-handle\"></a>"+"<span class=\"slider-tip\"></span>"+"</div>"+"<div class=\"slider-rule\"></div>"+"<div class=\"slider-rulelabel\"></div>"+"<div style=\"clear:both\"></div>"+"<input type=\"hidden\" class=\"slider-value\">"+"</div>").insertAfter(_2);
var t=$(_2);
t.addClass("slider-f").hide();
var _4=t.attr("name");
if(_4){
_3.find("input.slider-value").attr("name",_4);
t.removeAttr("name").attr("sliderName",_4);
}
_3.bind("_resize",function(e,_5){
if($(this).hasClass("easyui-fluid")||_5){
_6(_2);
}
return false;
});
return _3;
};
function _6(_7,_8){
var _9=$.data(_7,"slider");
var _a=_9.options;
var _b=_9.slider;
if(_8){
if(_8.width){
_a.width=_8.width;
}
if(_8.height){
_a.height=_8.height;
}
}
_b._size(_a);
if(_a.mode=="h"){
_b.css("height","");
_b.children("div").css("height","");
}else{
_b.css("width","");
_b.children("div").css("width","");
_b.children("div.slider-rule,div.slider-rulelabel,div.slider-inner")._outerHeight(_b._outerHeight());
}
_c(_7);
};
function _d(_e){
var _f=$.data(_e,"slider");
var _10=_f.options;
var _11=_f.slider;
var aa=_10.mode=="h"?_10.rule:_10.rule.slice(0).reverse();
if(_10.reversed){
aa=aa.slice(0).reverse();
}
_12(aa);
function _12(aa){
var _13=_11.find("div.slider-rule");
var _14=_11.find("div.slider-rulelabel");
_13.empty();
_14.empty();
for(var i=0;i<aa.length;i++){
var _15=i*100/(aa.length-1)+"%";
var _16=$("<span></span>").appendTo(_13);
_16.css((_10.mode=="h"?"left":"top"),_15);
if(aa[i]!="|"){
_16=$("<span></span>").appendTo(_14);
_16.html(aa[i]);
if(_10.mode=="h"){
_16.css({left:_15,marginLeft:-Math.round(_16.outerWidth()/2)});
}else{
_16.css({top:_15,marginTop:-Math.round(_16.outerHeight()/2)});
}
}
}
};
};
function _17(_18){
var _19=$.data(_18,"slider");
var _1a=_19.options;
var _1b=_19.slider;
_1b.removeClass("slider-h slider-v slider-disabled");
_1b.addClass(_1a.mode=="h"?"slider-h":"slider-v");
_1b.addClass(_1a.disabled?"slider-disabled":"");
var _1c=_1b.find(".slider-inner");
_1c.html("<a href=\"javascript:void(0)\" class=\"slider-handle\"></a>"+"<span class=\"slider-tip\"></span>");
if(_1a.range){
_1c.append("<a href=\"javascript:void(0)\" class=\"slider-handle\"></a>"+"<span class=\"slider-tip\"></span>");
}
_1b.find("a.slider-handle").draggable({axis:_1a.mode,cursor:"pointer",disabled:_1a.disabled,onDrag:function(e){
var _1d=e.data.left;
var _1e=_1b.width();
if(_1a.mode!="h"){
_1d=e.data.top;
_1e=_1b.height();
}
if(_1d<0||_1d>_1e){
return false;
}else{
_1f(_1d);
return false;
}
},onBeforeDrag:function(){
_19.isDragging=true;
},onStartDrag:function(){
_1a.onSlideStart.call(_18,_1a.value);
},onStopDrag:function(e){
_1f(_1a.mode=="h"?e.data.left:e.data.top);
_1a.onSlideEnd.call(_18,_1a.value);
_1a.onComplete.call(_18,_1a.value);
_19.isDragging=false;
}});
_1b.find("div.slider-inner").unbind(".slider").bind("mousedown.slider",function(e){
if(_19.isDragging||_1a.disabled){
return;
}
var pos=$(this).offset();
_1f(_1a.mode=="h"?(e.pageX-pos.left):(e.pageY-pos.top));
_1a.onComplete.call(_18,_1a.value);
});
function _1f(pos){
var _20=_21(_18,pos);
var s=Math.abs(_20%_1a.step);
if(s<_1a.step/2){
_20-=s;
}else{
_20=_20-s+_1a.step;
}
if(_1a.range){
var v1=_1a.value[0];
var v2=_1a.value[1];
var m=parseFloat((v1+v2)/2);
if(_20<v1){
v1=_20;
}else{
if(_20>v2){
v2=_20;
}else{
_20<m?v1=_20:v2=_20;
}
}
$(_18).slider("setValues",[v1,v2]);
}else{
$(_18).slider("setValue",_20);
}
};
};
function _22(_23,_24){
var _25=$.data(_23,"slider");
var _26=_25.options;
var _27=_25.slider;
var _28=$.isArray(_26.value)?_26.value:[_26.value];
var _29=[];
if(!$.isArray(_24)){
_24=$.map(String(_24).split(_26.separator),function(v){
return parseFloat(v);
});
}
_27.find(".slider-value").remove();
var _2a=$(_23).attr("sliderName")||"";
for(var i=0;i<_24.length;i++){
var _2b=_24[i];
if(_2b<_26.min){
_2b=_26.min;
}
if(_2b>_26.max){
_2b=_26.max;
}
var _2c=$("<input type=\"hidden\" class=\"slider-value\">").appendTo(_27);
_2c.attr("name",_2a);
_2c.val(_2b);
_29.push(_2b);
var _2d=_27.find(".slider-handle:eq("+i+")");
var tip=_2d.next();
var pos=_2e(_23,_2b);
if(_26.showTip){
tip.show();
tip.html(_26.tipFormatter.call(_23,_2b));
}else{
tip.hide();
}
if(_26.mode=="h"){
var _2f="left:"+pos+"px;";
_2d.attr("style",_2f);
tip.attr("style",_2f+"margin-left:"+(-Math.round(tip.outerWidth()/2))+"px");
}else{
var _2f="top:"+pos+"px;";
_2d.attr("style",_2f);
tip.attr("style",_2f+"margin-left:"+(-Math.round(tip.outerWidth()))+"px");
}
}
_26.value=_26.range?_29:_29[0];
$(_23).val(_26.range?_29.join(_26.separator):_29[0]);
if(_28.join(",")!=_29.join(",")){
_26.onChange.call(_23,_26.value,(_26.range?_28:_28[0]));
}
};
function _c(_30){
var _31=$.data(_30,"slider").options;
var fn=_31.onChange;
_31.onChange=function(){
};
_22(_30,_31.value);
_31.onChange=fn;
};
function _2e(_32,_33){
var _34=$.data(_32,"slider");
var _35=_34.options;
var _36=_34.slider;
var _37=_35.mode=="h"?_36.width():_36.height();
var pos=_35.converter.toPosition.call(_32,_33,_37);
if(_35.mode=="v"){
pos=_36.height()-pos;
}
if(_35.reversed){
pos=_37-pos;
}
return pos.toFixed(0);
};
function _21(_38,pos){
var _39=$.data(_38,"slider");
var _3a=_39.options;
var _3b=_39.slider;
var _3c=_3a.mode=="h"?_3b.width():_3b.height();
var _3d=_3a.converter.toValue.call(_38,_3a.mode=="h"?(_3a.reversed?(_3c-pos):pos):(_3c-pos),_3c);
return _3d.toFixed(0);
};
$.fn.slider=function(_3e,_3f){
if(typeof _3e=="string"){
return $.fn.slider.methods[_3e](this,_3f);
}
_3e=_3e||{};
return this.each(function(){
var _40=$.data(this,"slider");
if(_40){
$.extend(_40.options,_3e);
}else{
_40=$.data(this,"slider",{options:$.extend({},$.fn.slider.defaults,$.fn.slider.parseOptions(this),_3e),slider:_1(this)});
$(this).removeAttr("disabled");
}
var _41=_40.options;
_41.min=parseFloat(_41.min);
_41.max=parseFloat(_41.max);
if(_41.range){
if(!$.isArray(_41.value)){
_41.value=$.map(String(_41.value).split(_41.separator),function(v){
return parseFloat(v);
});
}
if(_41.value.length<2){
_41.value.push(_41.max);
}
}else{
_41.value=parseFloat(_41.value);
}
_41.step=parseFloat(_41.step);
_41.originalValue=_41.value;
_17(this);
_d(this);
_6(this);
});
};
$.fn.slider.methods={options:function(jq){
return $.data(jq[0],"slider").options;
},destroy:function(jq){
return jq.each(function(){
$.data(this,"slider").slider.remove();
$(this).remove();
});
},resize:function(jq,_42){
return jq.each(function(){
_6(this,_42);
});
},getValue:function(jq){
return jq.slider("options").value;
},getValues:function(jq){
return jq.slider("options").value;
},setValue:function(jq,_43){
return jq.each(function(){
_22(this,[_43]);
});
},setValues:function(jq,_44){
return jq.each(function(){
_22(this,_44);
});
},clear:function(jq){
return jq.each(function(){
var _45=$(this).slider("options");
_22(this,_45.range?[_45.min,_45.max]:[_45.min]);
});
},reset:function(jq){
return jq.each(function(){
var _46=$(this).slider("options");
$(this).slider(_46.range?"setValues":"setValue",_46.originalValue);
});
},enable:function(jq){
return jq.each(function(){
$.data(this,"slider").options.disabled=false;
_17(this);
});
},disable:function(jq){
return jq.each(function(){
$.data(this,"slider").options.disabled=true;
_17(this);
});
}};
$.fn.slider.parseOptions=function(_47){
var t=$(_47);
return $.extend({},$.parser.parseOptions(_47,["width","height","mode",{reversed:"boolean",showTip:"boolean",range:"boolean",min:"number",max:"number",step:"number"}]),{value:(t.val()||undefined),disabled:(t.attr("disabled")?true:undefined),rule:(t.attr("rule")?eval(t.attr("rule")):undefined)});
};
$.fn.slider.defaults={width:"auto",height:"auto",mode:"h",reversed:false,showTip:false,disabled:false,range:false,value:0,separator:",",min:0,max:100,step:1,rule:[],tipFormatter:function(_48){
return _48;
},converter:{toPosition:function(_49,_4a){
var _4b=$(this).slider("options");
return (_49-_4b.min)/(_4b.max-_4b.min)*_4a;
},toValue:function(pos,_4c){
var _4d=$(this).slider("options");
return _4d.min+(_4d.max-_4d.min)*(pos/_4c);
}},onChange:function(_4e,_4f){
},onSlideStart:function(_50){
},onSlideEnd:function(_51){
},onComplete:function(_52){
}};
})(jQuery);


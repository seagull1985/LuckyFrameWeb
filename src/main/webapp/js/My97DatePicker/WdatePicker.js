/*
 * My97 DatePicker 4.8 Beta1
 * License: http://www.my97.net/dp/license.asp
 */
var $dp, WdatePicker;
(function() {
	var $ = {
		$langList : [ {
			name : "en",
			charset : "UTF-8"
		}, {
			name : "zh-cn",
			charset : "gb2312"
		}, {
			name : "zh-tw",
			charset : "GBK"
		} ],
		$skinList : [ {
			name : "default",
			charset : "gb2312"
		}, {
			name : "whyGreen",
			charset : "gb2312"
		} ],
		$wdate : true,
		$crossFrame : true,
		$preLoad : false,
		doubleCalendar : false,
		enableKeyboard : true,
		enableInputMask : true,
		autoUpdateOnChanged : null,
		whichDayIsfirstWeek : 4,
		position : {},
		lang : "auto",
		skin : "default",
		dateFmt : "yyyy-MM-dd",
		realDateFmt : "yyyy-MM-dd",
		realTimeFmt : "HH:mm:ss",
		realFullFmt : "%Date %Time",
		minDate : "1900-01-01 00:00:00",
		maxDate : "2099-12-31 23:59:59",
		startDate : "",
		alwaysUseStartDate : false,
		yearOffset : 1911,
		firstDayOfWeek : 0,
		isShowWeek : false,
		highLineWeekDay : true,
		isShowClear : true,
		isShowToday : true,
		isShowOK : true,
		isShowOthers : true,
		readOnly : false,
		errDealMode : 0,
		autoPickDate : null,
		qsEnabled : true,
		autoShowQS : false,

		specialDates : null,
		specialDays : null,
		disabledDates : null,
		disabledDays : null,
		opposite : false,
		errMsg : "",
		quickSel : [],
		has : {},
		getRealLang : function() {
			var _ = $.$langList;
			for (var A = 0; A < _.length; A++)
				if (_[A].name == this.lang)
					return _[A];
			return _[0]
		}
	};
	WdatePicker = T;
	var X = window, S = {
		innerHTML : ""
	}, M = "document", H = "documentElement", C = "getElementsByTagName", U, A, R, G, a, W = navigator.appName;
	if (W == "Microsoft Internet Explorer")
		R = true;
	else if (W == "Opera")
		a = true;
	else
		G = true;
	A = J();
	if ($.$wdate)
		K(A + "skin/WdatePicker.css");
	U = X;
	if ($.$crossFrame) {
		try {
			while (U.parent && U.parent[M] != U[M]
					&& U.parent[M][C]("frameset").length == 0)
				U = U.parent
		} catch (N) {
		}
	}
	if (!U.$dp)
		U.$dp = {
			ff : G,
			ie : R,
			opera : a,
			status : 0,
			defMinDate : $.minDate,
			defMaxDate : $.maxDate
		};
	B();
	if ($.$preLoad && $dp.status == 0)
		E(X, "onload", function() {
			T(null, true)
		});
	if (!X[M].docMD) {
		E(X[M], "onmousedown", D);
		X[M].docMD = true
	}
	if (!U[M].docMD) {
		E(U[M], "onmousedown", D);
		U[M].docMD = true
	}
	E(X, "onunload", function() {
		if ($dp.dd)
			O($dp.dd, "none")
	});
	function B() {
		U.$dp = U.$dp || {};
		obj = {
			$ : function($) {
				return (typeof $ == "string") ? X[M].getElementById($) : $
			},
			$D : function($, _) {
				return this.$DV(this.$($).value, _)
			},
			$DV : function(_, $) {
				if (_ != "") {
					this.dt = $dp.cal.splitDate(_, $dp.cal.dateFmt);
					if ($)
						for ( var B in $)
							if (this.dt[B] === undefined)
								this.errMsg = "invalid property:" + B;
							else {
								this.dt[B] += $[B];
								if (B == "M") {
									var C = $["M"] > 0 ? 1 : 0, A = new Date(
											this.dt["y"], this.dt["M"], 0)
											.getDate();
									this.dt["d"] = Math
											.min(A + C, this.dt["d"])
								}
							}
					if (this.dt.refresh())
						return this.dt
				}
				return ""
			},
			show : function() {
				var A = U[M].getElementsByTagName("div"), $ = 100000;
				for (var B = 0; B < A.length; B++) {
					var _ = parseInt(A[B].style.zIndex);
					if (_ > $)
						$ = _
				}
				this.dd.style.zIndex = $ + 2;
				O(this.dd, "block")
			},
			hide : function() {
				O(this.dd, "none")
			},
			attachEvent : E
		};
		for ( var $ in obj)
			U.$dp[$] = obj[$];
		$dp = U.$dp
	}
	function E(A, $, _) {
		if (R)
			A.attachEvent($, _);
		else if (_) {
			var B = $.replace(/on/, "");
			_._ieEmuEventHandler = function($) {
				return _($)
			};
			A.addEventListener(B, _._ieEmuEventHandler, false)
		}
	}
	function J() {
		var _, A, $ = X[M][C]("script");
		for (var B = 0; B < $.length; B++) {
			_ = $[B].getAttribute("src");
			_ = _.substr(0, _.toLowerCase().indexOf("wdatepicker.js"));
			A = _.lastIndexOf("/");
			if (A > 0)
				_ = _.substring(0, A + 1);
			if (_)
				break
		}
		return _
	}
	function K(A, $, B) {
		var D = X[M][C]("HEAD").item(0), _ = X[M].createElement("link");
		if (D) {
			_.href = A;
			_.rel = "stylesheet";
			_.type = "text/css";
			if ($)
				_.title = $;
			if (B)
				_.charset = B;
			D.appendChild(_)
		}
	}
	function F($) {
		$ = $ || U;
		var A = 0, _ = 0;
		while ($ != U) {
			var D = $.parent[M][C]("iframe");
			for (var F = 0; F < D.length; F++) {
				try {
					if (D[F].contentWindow == $) {
						var E = V(D[F]);
						A += E.left;
						_ += E.top;
						break
					}
				} catch (B) {
				}
			}
			$ = $.parent
		}
		return {
			"leftM" : A,
			"topM" : _
		}
	}
	function V(F) {
		if (F.getBoundingClientRect)
			return F.getBoundingClientRect();
		else {
			var A = {
				ROOT_TAG : /^body|html$/i,
				OP_SCROLL : /^(?:inline|table-row)$/i
			}, E = false, H = null, _ = F.offsetTop, G = F.offsetLeft, D = F.offsetWidth, B = F.offsetHeight, C = F.offsetParent;
			if (C != F)
				while (C) {
					G += C.offsetLeft;
					_ += C.offsetTop;
					if (Q(C, "position").toLowerCase() == "fixed")
						E = true;
					else if (C.tagName.toLowerCase() == "body")
						H = C.ownerDocument.defaultView;
					C = C.offsetParent
				}
			C = F.parentNode;
			while (C.tagName && !A.ROOT_TAG.test(C.tagName)) {
				if (C.scrollTop || C.scrollLeft)
					if (!A.OP_SCROLL.test(O(C)))
						if (!a || C.style.overflow !== "visible") {
							G -= C.scrollLeft;
							_ -= C.scrollTop
						}
				C = C.parentNode
			}
			if (!E) {
				var $ = Z(H);
				G -= $.left;
				_ -= $.top
			}
			D += G;
			B += _;
			return {
				"left" : G,
				"top" : _,
				"right" : D,
				"bottom" : B
			}
		}
	}
	function L($) {
		$ = $ || U;
		var B = $[M], A = ($.innerWidth) ? $.innerWidth
				: (B[H] && B[H].clientWidth) ? B[H].clientWidth
						: B.body.offsetWidth, _ = ($.innerHeight) ? $.innerHeight
				: (B[H] && B[H].clientHeight) ? B[H].clientHeight
						: B.body.offsetHeight;
		return {
			"width" : A,
			"height" : _
		}
	}
	function Z($) {
		$ = $ || U;
		var B = $[M], A = B[H], _ = B.body;
		B = (A && A.scrollTop != null && (A.scrollTop > _.scrollTop || A.scrollLeft > _.scrollLeft)) ? A
				: _;
		return {
			"top" : B.scrollTop,
			"left" : B.scrollLeft
		}
	}
	function D($) {
		var _ = $ ? ($.srcElement || $.target) : null;
		try {
			if ($dp.cal && !$dp.eCont && $dp.dd && _ != $dp.el
					&& $dp.dd.style.display == "block")
				$dp.cal.close()
		} catch ($) {
		}
	}
	function Y() {
		$dp.status = 2
	}
	var P, _;
	function T(L, D) {
		$dp.win = X;
		B();
		L = L || {};
		for ( var J in $)
			if (J.substring(0, 1) != "$" && L[J] === undefined)
				L[J] = $[J];
		if (D) {
			if (!K()) {
				_ = _ || setInterval(function() {
					if (U[M].readyState == "complete")
						clearInterval(_);
					T(null, true)
				}, 50);
				return
			}
			if ($dp.status == 0) {
				$dp.status = 1;
				L.el = S;
				I(L, true)
			} else
				return
		} else if (L.eCont) {
			L.eCont = $dp.$(L.eCont);
			L.el = S;
			L.autoPickDate = true;
			L.qsEnabled = false;
			I(L)
		} else {
			if ($.$preLoad && $dp.status != 2)
				return;
			var H = F();
			if (H) {
				L.srcEl = H.srcElement || H.target;
				H.cancelBubble = true
			}
			L.el = L.el = $dp.$(L.el || L.srcEl);
			if (!L.el
					|| L.el["My97Mark"] === true
					|| L.el.disabled
					|| ($dp.dd && O($dp.dd) != "none" && $dp.dd.style.left != "-970px")) {
				L.el["My97Mark"] = false;
				return
			}
			I(L);
			if (H && L.el.nodeType == 1 && L.el["My97Mark"] === undefined) {
				L.el["My97Mark"] = false;
				var A, C;
				if (H.type == "focus") {
					A = "onclick";
					C = "onfocus"
				} else {
					A = "onfocus";
					C = "onclick"
				}
				E(L.el, A, L.el[C])
			}
		}
		function K() {
			if (R && U != X && U[M].readyState != "complete")
				return false;
			return true
		}
		function F() {
			if (G) {
				func = F.caller;
				while (func != null) {
					var $ = func.arguments[0];
					if ($ && ($ + "").indexOf("Event") >= 0)
						return $;
					func = func.caller
				}
				return null
			}
			return event
		}
	}
	function Q(_, $) {
		return _.currentStyle ? _.currentStyle[$] : document.defaultView
				.getComputedStyle(_, false)[$]
	}
	function O(_, $) {
		if (_)
			if ($ != null)
				_.style.display = $;
			else
				return Q(_, "display")
	}
	function I(G, _) {
		var D = G.el ? G.el.nodeName : "INPUT";
		if (_ || G.eCont || new RegExp(/input|textarea|div|span|p|a/ig).test(D))
			G.elProp = D == "INPUT" ? "value" : "innerHTML";
		else
			return;
		if (G.lang == "auto")
			G.lang = R ? navigator.browserLanguage.toLowerCase()
					: navigator.language.toLowerCase();
		if (!G.eCont)
			for ( var C in G)
				$dp[C] = G[C];
		if (!$dp.dd
				|| G.eCont
				|| ($dp.dd && (G.getRealLang().name != $dp.dd.lang || G.skin != $dp.dd.skin))) {
			if (G.eCont)
				E(G.eCont, G);
			else {
				$dp.dd = U[M].createElement("DIV");
				$dp.dd.style.cssText = "position:absolute";
				U[M].body.appendChild($dp.dd);
				E($dp.dd, G);
				if (_)
					$dp.dd.style.left = $dp.dd.style.top = "-970px";
				else {
					$dp.show();
					B($dp)
				}
			}
		} else if ($dp.cal) {
			$dp.show();
			$dp.cal.init();
			if (!$dp.eCont)
				B($dp)
		}
		function E(F, E) {
			F.innerHTML = "<iframe hideFocus=true width=97 height=9 frameborder=0 border=0 scrolling=no></iframe>";
			var D = F.lastChild.contentWindow[M], _ = $.$langList, C = $.$skinList, H = E
					.getRealLang();
			F.lang = H.name;
			F.skin = E.skin;
			var G = [
					"<head><script>document.oncontextmenu=function(){return false;};",
					"var $d, $dp, $cfg=document.cfg, $pdp = parent.$dp, $dt, $tdt, $sdt, $lastInput, $IE=$pdp.ie, $FF = $pdp.ff,$OPERA=$pdp.opera, $ny, $cMark = false;",
					"if($cfg.eCont) {$dp = {};for(var p in $pdp) {$dp[p] = $pdp[p];}}else{$dp = $pdp;};for (var p in $cfg) {$dp[p] = $cfg[p];};",
					"</script><script src=", A, "lang/", H.name,
					".js charset=", H.charset, "></script>" ];
			for (var I = 0; I < C.length; I++)
				if (C[I].name == E.skin)
					G.push("<link rel=\"stylesheet\" type=\"text/css\" href=\""
							+ A + "skin/" + C[I].name
							+ "/datepicker.css\" charset=\"" + C[I].charset
							+ "\"/>");
			G
					.push("<script type=\"text/javascript\" src=\""
							+ A
							+ "calendar.js?\"+Math.random()+\" charset=\"gb2312\"></script>");
			G
					.push("</head><body leftmargin=\"0\" topmargin=\"0\" tabindex=0></body></html>");
			G
					.push("<script>var t;t=t||setInterval(function(){if(document.ready){new My97DP();$cfg.onload();$c.autoSize();$cfg.setPos($dp);clearInterval(t);}},20);if($FF||$OPERA)document.close();</script>");
			E.setPos = B;
			E.onload = Y;
			D.write("<html>");
			D.cfg = E;
			D.write(G.join(""))
		}
		function B(I) {
			var G = I.position.left, B = I.position.top, C = I.el;
			if (C == S)
				return;
			if (C != I.srcEl && (O(C) == "none" || C.type == "hidden"))
				C = I.srcEl;
			var H = V(C), $ = F(X), D = L(U), A = Z(U), E = $dp.dd.offsetHeight, _ = $dp.dd.offsetWidth;
			if (isNaN(B))
				B = 0;
			if (B != "under"
					&& (($.topM + H.bottom + E > D.height) && ($.topM + H.top
							- E > 0)))
				B += A.top + $.topM + H.top - E - 2;
			else
				B += A.top + $.topM + Math.min(H.bottom, D.height - E) + 2;
			if (isNaN(G))
				G = 0;
			G += A.left + Math.min($.leftM + H.left, D.width - _ - 5)
					- (R ? 2 : 0);
			I.dd.style.top = B + "px";
			I.dd.style.left = G + "px"
		}
	}
})()
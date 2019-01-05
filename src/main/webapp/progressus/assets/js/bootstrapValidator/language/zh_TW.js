(function($) {
    /**
     * Traditional Chinese language package
     * Translated by @tureki
     */
    $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
        base64: {
            'default': '請輸入有效的Base64編碼'
        },
        between: {
            'default': '請輸入不小於 %s 且不大於 %s 的值',
            notInclusive: '請輸入不小於等於 %s 且不大於等於 %s 的值'
        },
        callback: {
            'default': '請輸入有效的值'
        },
        choice: {
            'default': '請輸入有效的值',
            less: '最少選擇 %s 個選項',
            more: '最多選擇 %s 個選項',
            between: '請選擇 %s 至 %s 個選項'
        },
        color: {
            'default': '請輸入有效的元色碼'
        },
        creditCard: {
            'default': '請輸入有效的信用卡號碼'
        },
        cusip: {
            'default': '請輸入有效的CUSIP(美國證券庫斯普)號碼'
        },
        cvv: {
            'default': '請輸入有效的CVV(信用卡檢查碼)代碼'
        },
        date: {
            'default': '請輸入有效的日期',
            min  : '請輸入 %s 或之後的日期',
            max  : '請輸入 %s 或以前的日期',
            range : '請輸入 %s 至 %s 之間的日期'
        },
        different: {
            'default': '請輸入不同的值'
        },
        digits: {
            'default': '只能輸入數字'
        },
        ean: {
            'default': '請輸入有效的EAN商品編碼'
        },
        emailAddress: {
            'default': '請輸入有效的EMAIL'
        },
        file: {
            'default': '請選擇有效的檔案'
        },
        greaterThan: {
            'default'    : '請輸入大於等於 %s 的值',
            notInclusive : '請輸入大於 %s 的值'
        },
        grid: {
            'default': '請輸入有效的GRId編碼'
        },
        hex: {
            'default': '請輸入有效的16位元碼'
        },
        hexColor: {
            'default': '請輸入有效的16位元色碼'
        },
        iban: {
            'default': '請輸入有效的IBAN(國際銀行賬戶)號碼',
            countryNotSupported: '不支援 %s 國家',
            country: '請輸入有效的 %s 國家的IBAN(國際銀行賬戶)號碼',
            countries: {
                AD: '安道​​爾',
                AE: '阿聯酋',
                AL: '阿爾巴尼亞',
                AO: '安哥拉',
                AT: '奧地利',
                AZ: '阿塞拜疆',
                BA: '波斯尼亞和黑塞哥維那',
                BE: '比利時',
                BF: '布基納法索',
                BG: '保加利亞',
                BH: '巴林',
                BI: '布隆迪',
                BJ: '貝寧',
                BR: '巴西',
                CH: '瑞士',
                CI: '象牙海岸',
                CM: '喀麥隆',
                CR: '哥斯達黎加',
                CV: '佛得角',
                CY: '塞浦路斯',
                CZ: '捷克共和國',
                DE: '德國',
                DK: '丹麥',
                DO: '多明尼加共和國',
                DZ: '阿爾及利亞',
                EE: '愛沙尼亞',
                ES: '西班牙',
                FI: '芬蘭',
                FO: '法羅群島',
                FR: '法國',
                GB: '英國',
                GE: '格魯吉亞',
                GI: '直布羅陀',
                GL: '格陵蘭島',
                GR: '希臘',
                GT: '危地馬拉',
                HR: '克羅地亞',
                HU: '匈牙利',
                IE: '愛爾蘭',
                IL: '以色列',
                IR: '伊朗',
                IS: '冰島',
                IT: '意大利',
                JO: '約旦',
                KW: '科威特',
                KZ: '哈薩克斯坦',
                LB: '黎巴嫩',
                LI: '列支敦士登',
                LT: '立陶宛',
                LU: '盧森堡',
                LV: '拉脫維亞',
                MC: '摩納哥',
                MD: '摩爾多瓦',
                ME: '蒙特內哥羅',
                MG: '馬達加斯加',
                MK: '馬其頓',
                ML: '馬里',
                MR: '毛里塔尼亞',
                MT: '馬耳他',
                MU: '毛里求斯',
                MZ: '莫桑比克',
                NL: '荷蘭',
                NO: '挪威',
                PK: '巴基斯坦',
                PL: '波蘭',
                PS: '巴勒斯坦',
                PT: '葡萄牙',
                QA: '卡塔爾',
                RO: '羅馬尼亞',
                RS: '塞爾維亞',
                SA: '沙特阿拉伯',
                SE: '瑞典',
                SI: '斯洛文尼亞',
                SK: '斯洛伐克',
                SM: '聖馬力諾',
                SN: '塞內加爾',
                TN: '突尼斯',
                TR: '土耳其',
                VG: '英屬維爾京群島'
            }
        },
        id: {
            'default': '請輸入有效的身份證字號',
            countryNotSupported: '不支援 %s 國家',
            country: '請輸入有效的 %s 身份證字號',
            countries: {
                BA: '波赫',
                BG: '保加利亞',
                BR: '巴西',
                CH: '瑞士',
                CL: '智利',
                CN: '中國',
                CZ: '捷克共和國',
                DK: '丹麥',
                EE: '愛沙尼亞',
                ES: '西班牙',
                FI: '芬蘭',
                HR: '克羅地亞',
                IE: '愛爾蘭',
                IS: '冰島',
                LT: '立陶宛',
                LV: '拉脫維亞',
                ME: '蒙特內哥羅',
                MK: '馬其頓',
                NL: '荷蘭',
                RO: '羅馬尼亞',
                RS: '塞爾維亞',
                SE: '瑞典',
                SI: '斯洛文尼亞',
                SK: '斯洛伐克',
                SM: '聖馬力諾',
                TH: '泰國',
                ZA: '南非'
            }
        },
        identical: {
            'default': '請輸入相同的值'
        },
        imei: {
            'default': '請輸入有效的IMEI(手機序列號)'
        },
        imo: {
            'default': '請輸入有效的國際海事組織(IMO)號碼'
        },
        integer: {
            'default': '請輸入有效的整數'
        },
        ip: {
            'default': '請輸入有效的IP位址',
            ipv4: '請輸入有效的IPv4位址',
            ipv6: '請輸入有效的IPv6位址'
        },
        isbn: {
            'default': '請輸入有效的ISBN(國際標準書號)'
        },
        isin: {
            'default': '請輸入有效的ISIN(國際證券號碼)'
        },
        ismn: {
            'default': '請輸入有效的ISMN(國際標準音樂編號)'
        },
        issn: {
            'default': '請輸入有效的ISSN(國際標準期刊號)'
        },
        lessThan: {
            'default': '請輸入小於等於 %s 的值',
            notInclusive: '請輸入小於 %s 的值'
        },
        mac: {
            'default': '請輸入有效的MAC位址'
        },
        meid: {
            'default': '請輸入有效的MEID(行動設備識別碼)'
        },
        notEmpty: {
            'default': '請填寫必填欄位'
        },
        numeric: {
            'default': '請輸入有效的數字(含浮點數)'
        },
        phone: {
            'default': '請輸入有效的電話號碼',
            countryNotSupported: '不支援 %s 國家',
            country: '請輸入有效的 %s 國家的電話號碼',
            countries: {
                BR: '巴西',
                CN: '中国',
                CZ: '捷克共和國',
                DE: '德國',
                DK: '丹麥',
                ES: '西班牙',
                FR: '法國',
                GB: '英國',
                MA: '摩洛哥',
                PK: '巴基斯坦',
                RO: '罗马尼亚',
                RU: '俄羅斯',
                SK: '斯洛伐克',
                TH: '泰國',
                US: '美國',
                VE: '委内瑞拉'
            }
        },
        regexp: {
            'default': '請輸入符合正規表示式所限制的值'
        },
        remote: {
            'default': '請輸入有效的值'
        },
        rtn: {
            'default': '請輸入有效的RTN號碼'
        },
        sedol: {
            'default': '請輸入有效的SEDOL代碼'
        },
        siren: {
            'default': '請輸入有效的SIREN號碼'
        },
        siret: {
            'default': '請輸入有效的SIRET號碼'
        },
        step: {
            'default': '請輸入 %s 的倍數'
        },
        stringCase: {
            'default': '只能輸入小寫字母',
            upper: '只能輸入大寫字母'
        },
        stringLength: {
            'default': '請輸入符合長度限制的值',
            less: '請輸入小於 %s 個字',
            more: '請輸入大於 %s 個字',
            between: '請輸入 %s 至 %s 個字'
        },
        uri: {
            'default': '請輸入一個有效的鏈接'
        },
        uuid: {
            'default': '請輸入有效的UUID',
            version: '請輸入版本 %s 的UUID'
        },
        vat: {
            'default': '請輸入有效的VAT(增值税)',
            countryNotSupported: '不支援 %s 國家',
            country: '請輸入有效的 %s 國家的VAT(增值税)',
            countries: {
                AT: '奧地利',
                BE: '比利時',
                BG: '保加利亞',
                BR: '巴西',
                CH: '瑞士',
                CY: '塞浦路斯',
                CZ: '捷克共和國',
                DE: '德國',
                DK: '丹麥',
                EE: '愛沙尼亞',
                ES: '西班牙',
                FI: '芬蘭',
                FR: '法語',
                GB: '英國',
                GR: '希臘',
                EL: '希臘',
                HU: '匈牙利',
                HR: '克羅地亞',
                IE: '愛爾蘭',
                IS: '冰島',
                IT: '意大利',
                LT: '立陶宛',
                LU: '盧森堡',
                LV: '拉脫維亞',
                MT: '馬耳他',
                NL: '荷蘭',
                NO: '挪威',
                PL: '波蘭',
                PT: '葡萄牙',
                RO: '羅馬尼亞',
                RU: '俄羅斯',
                RS: '塞爾維亞',
                SE: '瑞典',
                SI: '斯洛文尼亞',
                SK: '斯洛伐克',
                VE: '委内瑞拉',
                ZA: '南非'
            }
        },
        vin: {
            'default': '請輸入有效的VIN(車輛識別號碼)'
        },
        zipCode: {
            'default': '請輸入有效的郵政編碼',
            countryNotSupported: '不支援 %s 國家',
            country: '請輸入有效的 %s 國家的郵政編碼',
            countries: {
                AT: '奧地利',
                BR: '巴西',
                CA: '加拿大',
                CH: '瑞士',
                CZ: '捷克共和國',
                DE: '德國',
                DK: '丹麥',
                FR: '法國',
                GB: '英國',
                IE: '愛爾蘭',
                IT: '意大利',
                MA: '摩洛哥',
                NL: '荷蘭',
                PT: '葡萄牙',
                RO: '羅馬尼亞',
                RU: '俄羅斯',
                SE: '瑞典',
                SG: '新加坡',
                SK: '斯洛伐克',
                US: '美國'
            }
        }
    });
}(window.jQuery));

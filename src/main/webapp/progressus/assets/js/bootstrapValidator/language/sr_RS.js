(function($) {
    /**
     * Serbian Latin language package
     * Translated by @markocrni
     */
    $.fn.bootstrapValidator.i18n = $.extend(true, $.fn.bootstrapValidator.i18n, {
        base64: {
            'default': 'Molimo da unesete važeći base 64 enkodovan'
        },
        between: {
            'default': 'Molimo da unesete vrednost između %s i %s',
            notInclusive: 'Molimo da unesete vrednost strogo između %s i %s'
        },
        callback: {
            'default': 'Molimo da unesete važeću vrednost'
        },
        choice: {
            'default': 'Molimo da unesete važeću vrednost',
            less: 'Molimo da odaberete minimalno %s opciju(a)',
            more: 'Molimo da odaberete maksimalno %s opciju(a)',
            between: 'Molimo odaberite %s - %s opcije(a)'
        },
        color: {
            'default': 'Molimo da unesete ispravnu boju'
        },
        creditCard: {
            'default': 'Molimo da unesete ispravan broj kreditne kartice'
        },
        cusip: {
            'default': 'Molimo da unesete ispravan CUSIP broj'
        },
        cvv: {
            'default': 'Molimo da unesete ispravan CVV broj'
        },
        date: {
            'default': 'Molimo da unesete ispravan datum',
            min: 'Molimo da unesete datum posle %s',
            max: 'Molimo da unesete datum pre %s',
            range: 'Molimo da unesete datum od %s do %s'
        },
        different: {
            'default': 'Molimo da unesete drugu vrednost'
        },
        digits: {
             'default': 'Molimo da unesete samo cifre'
        },
        ean: {
            'default': 'Molimo da unesete ispravan EAN broj'
        },
        emailAddress: {
            'default': 'Molimo da unesete važeću e-mail adresu'
        },
        file: {
            'default': 'Molimo da unesete ispravan fajl'
        },
        greaterThan: {
            'default': 'Molimo da unesete vrednost veću ili jednaku od %s',
            notInclusive: 'Molimo da unesete vrednost veću od %s'
        },
        grid: {
            'default': 'Molimo da unesete ispravan GRId broj'
        },
        hex: {
            'default': 'Molimo da unesete ispravan heksadecimalan broj'
        },
        hexColor: {
            'default': 'Molimo da unesete ispravnu heksa boju'
        },
        iban: {
            'default': 'Molimo da unesete ispravan IBAN broj',
            countryNotSupported: 'Kod %s nije podržan',
            country: 'Molimo da unesete ispravan IBAN broj %s',
            countries: {
                AD: 'Andore',
                AE: 'Ujedinjenih Arapskih Emirata',
                AL: 'Albanije',
                AO: 'Angole',
                AT: 'Austrije',
                AZ: 'Azerbejdžana',
                BA: 'Bosne i Hercegovine',
                BE: 'Belgije',
                BF: 'Burkina Fasa',
                BG: 'Bugarske',
                BH: 'Bahraina',
                BI: 'Burundija',
                BJ: 'Benina',
                BR: 'Brazila',
                CH: 'Švajcarske',
                CI: 'Obale slonovače',
                CM: 'Kameruna',
                CR: 'Kostarike',
                CV: 'Zelenorotskih Ostrva',
                CY: 'Kipra',
                CZ: 'Češke',
                DE: 'Nemačke',
                DK: 'Danske',
                DO: 'Dominike',
                DZ: 'Alžira',
                EE: 'Estonije',
                ES: 'Španije',
                FI: 'Finske',
                FO: 'Farskih Ostrva',
                FR: 'Francuske',
                GB: 'Engleske',
                GE: 'Džordžije',
                GI: 'Giblartara',
                GL: 'Grenlanda',
                GR: 'Grčke',
                GT: 'Gvatemale',
                HR: 'Hrvatske',
                HU: 'Mađarske',
                IE: 'Irske',
                IL: 'Izraela',
                IR: 'Irana',
                IS: 'Islanda',
                IT: 'Italije',
                JO: 'Jordana',
                KW: 'Kuvajta',
                KZ: 'Kazahstana',
                LB: 'Libana',
                LI: 'Lihtenštajna',
                LT: 'Litvanije',
                LU: 'Luksemburga',
                LV: 'Latvije',
                MC: 'Monaka',
                MD: 'Moldove',
                ME: 'Crne Gore',
                MG: 'Madagaskara',
                MK: 'Makedonije',
                ML: 'Malija',
                MR: 'Mauritanije',
                MT: 'Malte',
                MU: 'Mauricijusa',
                MZ: 'Mozambika',
                NL: 'Holandije',
                NO: 'Norveške',
                PK: 'Pakistana',
                PL: 'Poljske',
                PS: 'Palestine',
                PT: 'Portugala',
                QA: 'Katara',
                RO: 'Rumunije',
                RS: 'Srbije',
                SA: 'Saudijske Arabije',
                SE: 'Švedske',
                SI: 'Slovenije',
                SK: 'Slovačke',
                SM: 'San Marina',
                SN: 'Senegala',
                TN: 'Tunisa',
                TR: 'Turske',
                VG: 'Britanskih Devičanskih Ostrva'
            }
        },
        id: {
            'default': 'Molimo da unesete ispravan identifikacioni broj',
            countryNotSupported: 'Kod %s nije podržan',
            country: 'Molimo da unesete ispravan identifikacioni broj %s',
            countries: {
                BA: 'Bosne i Herzegovine',
                BG: 'Bugarske',
                BR: 'Brazila',
                CH: 'Švajcarske',
                CL: 'Čilea',
                CN: 'Kine',
                CZ: 'Češke',
                DK: 'Danske',
                EE: 'Estonije',
                ES: 'Španije',
                FI: 'Finske',
                HR: 'Hrvatske',
                IE: 'Irske',
                IS: 'Islanda',
                LT: 'Litvanije',
                LV: 'Letonije',
                ME: 'Crne Gore',
                MK: 'Makedonije',
                NL: 'Holandije',
                RO: 'Rumunije',
                RS: 'Srbije',
                SE: 'Švedske',
                SI: 'Slovenije',
                SK: 'Slovačke',
                SM: 'San Marina',
                TH: 'Tajlanda',
                ZA: 'Južne Afrike'
            }
        },
        identical: {
            'default': 'Molimo da unesete istu vrednost'
        },
        imei: {
            'default': 'Molimo da unesete ispravan IMEI broj'
        },
        imo: {
            'default': 'Molimo da unesete ispravan IMO broj'
        },
        integer: {
            'default': 'Molimo da unesete ispravan broj'
        },
        ip: {
            'default': 'Molimo da unesete ispravnu IP adresu',
            ipv4: 'Molimo da unesete ispravnu IPv4 adresu',
            ipv6: 'Molimo da unesete ispravnu IPv6 adresu'
        },
        isbn: {
            'default': 'Molimo da unesete ispravan ISBN broj'
        },
        isin: {
            'default': 'Molimo da unesete ispravan ISIN broj'
        },
        ismn: {
            'default': 'Molimo da unesete ispravan ISMN broj'
        },
        issn: {
            'default': 'Molimo da unesete ispravan ISSN broj'
        },
        lessThan: {
            'default': 'Molimo da unesete vrednost manju ili jednaku od %s',
            notInclusive: 'Molimo da unesete vrednost manju od %s'
        },
        mac: {
            'default': 'Molimo da unesete ispravnu MAC adresu'
        },
        meid: {
            'default': 'Molimo da unesete ispravan MEID broj'
        },
        notEmpty: {
            'default': 'Molimo da unesete vrednost'
        },
        numeric: {
            'default': 'Molimo da unesete ispravan decimalni broj'
        },
        phone: {
            'default': 'Molimo da unesete ispravan broj telefona',
            countryNotSupported: 'Broj %s nije podržan',
            country: 'Molimo da unesete ispravan broj telefona %s',
            countries: {
                BR: 'Brazila',
                CN: 'Kine',
                CZ: 'Češke',
                DE: 'Nemačke',
                DK: 'Danske',
                ES: 'Španije',
                FR: 'Francuske',
                GB: 'Engleske',
                MA: 'Maroka',
                PK: 'Pakistana',
                RO: 'Rumunije',
                RU: 'Rusije',
                SK: 'Slovačke',
                TH: 'Tajlanda',
                US: 'Amerike',
                VE: 'Venecuele'
            }
        },
        regexp: {
            'default': 'Molimo da unesete vrednost koja se poklapa sa paternom'
        },
        remote: {
            'default': 'Molimo da unesete ispravnu vrednost'
        },
        rtn: {
            'default': 'Molimo da unesete ispravan RTN broj'
        },
        sedol: {
            'default': 'Molimo da unesete ispravan SEDOL broj'
        },
        siren: {
            'default': 'Molimo da unesete ispravan SIREN broj'
        },
        siret: {
            'default': 'Molimo da unesete ispravan SIRET broj'
        },
        step: {
            'default': 'Molimo da unesete ispravan korak od %s'
        },
        stringCase: {
            'default': 'Molimo da unesete samo mala slova',
            upper: 'Molimo da unesete samo velika slova'
        },
        stringLength: {
            'default': 'Molimo da unesete vrednost sa ispravnom dužinom',
            less: 'Molimo da unesete manje od %s karaktera',
            more: 'Molimo da unesete više od %s karaktera',
            between: 'Molimo da unesete vrednost dužine između %s i %s karaktera'
        },
        uri: {
            'default': 'Molimo da unesete ispravan URI'
        },
        uuid: {
            'default': 'Molimo da unesete ispravan UUID broj',
            version: 'Molimo da unesete ispravnu verziju UUID %s broja'
        },
        vat: {
            'default': 'Molimo da unesete ispravan VAT broj',
            countryNotSupported: 'Kod %s nije podržan',
            country: 'Molimo da unesete ispravan VAT broj %s',
            countries: {
                AT: 'Austrije',
                BE: 'Belgije',
                BG: 'Bugarske',
                BR: 'Brazila',
                CH: 'Švajcarske',
                CY: 'Kipra',
                CZ: 'Češke',
                DE: 'Nemačke',
                DK: 'Danske',
                EE: 'Estonije',
                ES: 'Španije',
                FI: 'Finske',
                FR: 'Francuske',
                GB: 'Engleske',
                GR: 'Grčke',
                EL: 'Grčke',
                HU: 'Mađarske',
                HR: 'Hrvatske',
                IE: 'Irske',
                IS: 'Islanda',
                IT: 'Italije',
                LT: 'Litvanije',
                LU: 'Luksemburga',
                LV: 'Letonije',
                MT: 'Malte',
                NL: 'Holandije',
                NO: 'Norveške',
                PL: 'Poljske',
                PT: 'Portugala',
                RO: 'Romunje',
                RU: 'Rusije',
                RS: 'Srbije',
                SE: 'Švedske',
                SI: 'Slovenije',
                SK: 'Slovačke',
                VE: 'Venecuele',
                ZA: 'Južne Afrike'
            }
        },
        vin: {
            'default': 'Molimo da unesete ispravan VIN broj'
        },
        zipCode: {
            'default': 'Molimo da unesete ispravan poštanski broj',
            countryNotSupported: 'Kod %s nije podržan',
            country: 'Molimo da unesete ispravan poštanski broj %s',
            countries: {
                AT: 'Austrije',
                BR: 'Brazila',
                CA: 'Kanade',
                CH: 'Švajcarske',
                CZ: 'Češke',
                DE: 'Nemačke',
                DK: 'Danske',
                FR: 'Francuske',
                GB: 'Engleske',
                IE: 'Irske',
                IT: 'Italije',
                MA: 'Maroka',
                NL: 'Holandije',
                PT: 'Portugala',
                RO: 'Rumunije',
                RU: 'Rusije',
                SE: 'Švedske',
                SG: 'Singapura',
                SK: 'Slovačke',
                US: 'Amerike'
            }
        }
    });
}(window.jQuery));

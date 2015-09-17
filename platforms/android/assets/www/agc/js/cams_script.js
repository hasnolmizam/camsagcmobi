
var GLOBAL_IP = "http://10.17.14.210/cams";

	function getSessionStorage (key)
	{
		var info = window.sessionStorage.getItem("info");
        var json = $.parseJSON(info);
	    var jsondata = $.parseJSON(json.data);
		var jsonrolesdata = $.parseJSON(json.roles);
	    //alert(jsondata);
		var myvalue = "";
				
			if (jsondata.length > 0)
				{					

					var STF_ID = jsondata[0].STF_ID;
					var STF_USERNAME = jsondata[0].STF_USERNAME;
					var STF_NAMA=jsondata[0].STF_NAMA;
					var BAHAGIAN=jsondata[0].BAHAGIAN;
					var PEN_BHGN_ID=jsondata[0].PEN_BHGN_ID;
					var FLOW_ID=jsondata[0].FLOW_ID;
					var STF_GAMBAR_FILE = "defaultFEMALE.jpg";
					if (jsondata[0].STF_GAMBAR_FILE != "")
					{
						STF_GAMBAR_FILE = encodeURIComponent(jsondata[0].STF_GAMBAR_FILE);
					}
					

					
					
					var TOTAL_OWN_INPROCESS=jsondata[0].TOTAL_OWN_INPROCESS;
					var TOTAL_OWN_APPROVED=jsondata[0].TOTAL_OWN_APPROVED;
					var TOTAL_OWN_REJECTED=jsondata[0].TOTAL_OWN_REJECTED;

					var TOTAL_APPROVER_INPROCESS=jsondata[0].TOTAL_APPROVER_INPROCESS;
					var TOTAL_APPROVER_APPROVED=jsondata[0].TOTAL_APPROVER_APPROVED;
					var TOTAL_APPROVER_REJECTED=jsondata[0].TOTAL_APPROVER_REJECTED;
					
					
					
					var TOTAL_SUPPORTER_INPROCESS=jsondata[0].TOTAL_SUPPORTER_INPROCESS;
					var TOTAL_SUPPORTER_SUPPORTTED=jsondata[0].TOTAL_SUPPORTER_SUPPORTTED;
					var TOTAL_SUPPORTER_NOTSUPPORTED=jsondata[0].TOTAL_SUPPORTER_NOTSUPPORTED;
					
					
					var myroles = "";
					if (jsonrolesdata.length > 0)
					{
						myroles = ",";		
						for (var a = 0; a < jsonrolesdata.length; a++)
						{
							myroles = myroles + jsonrolesdata[a].GROUP_ID + ",";
						}		
												
					}
					//,2,3,
					
					
					
					if (key == "STF_USERNAME") { myvalue = STF_USERNAME; }
					else if (key == "STF_USERNAME") { myvalue = STF_USERNAME; }
					else if (key == "STF_NAMA") { myvalue = STF_NAMA; }
					else if (key == "PEN_BHGN_ID") { myvalue = PEN_BHGN_ID; }
					else if (key == "BAHAGIAN") { myvalue = BAHAGIAN; }
					else if (key == "FLOW_ID") { myvalue = FLOW_ID; }

					else if (key == "TOTAL_OWN_INPROCESS") { myvalue = TOTAL_OWN_INPROCESS; }
					else if (key == "TOTAL_OWN_APPROVED") { myvalue = TOTAL_OWN_APPROVED; }
					else if (key == "TOTAL_OWN_REJECTED") { myvalue = TOTAL_OWN_REJECTED; }

					else if (key == "TOTAL_APPROVER_INPROCESS") { myvalue = TOTAL_APPROVER_INPROCESS; }
					else if (key == "TOTAL_APPROVER_APPROVED") { myvalue = TOTAL_APPROVER_APPROVED; }
					else if (key == "TOTAL_APPROVER_REJECTED") { myvalue = TOTAL_APPROVER_REJECTED; }
					
					else if (key == "TOTAL_SUPPORTER_INPROCESS") { myvalue = TOTAL_SUPPORTER_INPROCESS; }
					else if (key == "TOTAL_SUPPORTER_SUPPORTTED") { myvalue = TOTAL_SUPPORTER_SUPPORTTED; }
					else if (key == "TOTAL_SUPPORTER_NOTSUPPORTED") { myvalue = TOTAL_SUPPORTER_NOTSUPPORTED; }

					
					else if (key == "STF_GAMBAR_FILE") { myvalue = STF_GAMBAR_FILE; }
					
					else if (key == "ROLE_PENGGUNA") 
					{ 
						if (myroles.indexOf (",2,") >= 0) { myvalue = "YES";} else {myvalue = "NO";}
					}
					else if (key == "ROLE_PELULUS") 
					{ 
						if (myroles.indexOf (",3,") >= 0) { myvalue = "YES";} else {myvalue = "NO";}
					}
					else if (key == "ROLE_PENYOKONG") 
					{ 
						if (myroles.indexOf (",4,") >= 0) { myvalue = "YES";} else {myvalue = "NO";}
					}
					else if (key == "ROLE_PENTADBIRICT") 
					{ 
						if (myroles.indexOf (",6,") >= 0) { myvalue = "YES";} else {myvalue = "NO";}
					}
					else if (key == "ROLE_PENTADBIRBAHAGIAN") 
					{ 
						if (myroles.indexOf (",5,") >= 0) { myvalue = "YES";} else {myvalue = "NO";}
					}
					
												
					//myvalue = eval(jsondata[0].key);
					 //alert(myvalue);
				}	
					
		return myvalue;	
	}
	
	
	function gup( name )
                {
                        name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");  
                        var regexS = "[\\?&]"+name+"=([^&#]*)";  
                        var regex = new RegExp( regexS );  
                        var results = regex.exec( window.location.href );
                        if( results == null )    return "";  
                        else    return results[1];
                        
                }    
    
    
    
	function loading(msg) 
	{
		//alert('masuk loading function');
    	cordova.plugins.pDialog.init({
    	    theme : 'HOLO_LIGHT',
    	    progressStyle : 'SPINNER',
    	    cancelable : true,
    	    title : 'CAMS-AGC',
    	    message : msg
    	});
		
	}

	function unloading() 
	{
		cordova.plugins.pDialog.dismiss();	
	}


	function utf8_encode(argString) {
		  //  discuss at: http://phpjs.org/functions/utf8_encode/
		  // original by: Webtoolkit.info (http://www.webtoolkit.info/)
		  // improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
		  // improved by: sowberry
		  // improved by: Jack
		  // improved by: Yves Sucaet
		  // improved by: kirilloid
		  // bugfixed by: Onno Marsman
		  // bugfixed by: Onno Marsman
		  // bugfixed by: Ulrich
		  // bugfixed by: Rafal Kukawski
		  // bugfixed by: kirilloid
		  //   example 1: utf8_encode('Kevin van Zonneveld');
		  //   returns 1: 'Kevin van Zonneveld'

		  if (argString === null || typeof argString === 'undefined') {
		    return '';
		  }

		  var string = (argString + ''); // .replace(/\r\n/g, "\n").replace(/\r/g, "\n");
		  var utftext = '',
		    start, end, stringl = 0;

		  start = end = 0;
		  stringl = string.length;
		  for (var n = 0; n < stringl; n++) {
		    var c1 = string.charCodeAt(n);
		    var enc = null;

		    if (c1 < 128) {
		      end++;
		    } else if (c1 > 127 && c1 < 2048) {
		      enc = String.fromCharCode(
		        (c1 >> 6) | 192, (c1 & 63) | 128
		      );
		    } else if ((c1 & 0xF800) != 0xD800) {
		      enc = String.fromCharCode(
		        (c1 >> 12) | 224, ((c1 >> 6) & 63) | 128, (c1 & 63) | 128
		      );
		    } else { // surrogate pairs
		      if ((c1 & 0xFC00) != 0xD800) {
		        throw new RangeError('Unmatched trail surrogate at ' + n);
		      }
		      var c2 = string.charCodeAt(++n);
		      if ((c2 & 0xFC00) != 0xDC00) {
		        throw new RangeError('Unmatched lead surrogate at ' + (n - 1));
		      }
		      c1 = ((c1 & 0x3FF) << 10) + (c2 & 0x3FF) + 0x10000;
		      enc = String.fromCharCode(
		        (c1 >> 18) | 240, ((c1 >> 12) & 63) | 128, ((c1 >> 6) & 63) | 128, (c1 & 63) | 128
		      );
		    }
		    if (enc !== null) {
		      if (end > start) {
		        utftext += string.slice(start, end);
		      }
		      utftext += enc;
		      start = end = n + 1;
		    }
		  }

		  if (end > start) {
		    utftext += string.slice(start, stringl);
		  }

		  return utftext;
		}



			


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





			
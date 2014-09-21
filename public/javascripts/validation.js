function validation(){
	var r=true;
	
	
	$("input[validation]").each(function(){

	$(this).removeAttr("style");
    var type = $(this).attr("validation");
    if(type=="name"){
    	var a=$(this).val();
    	if(!a.match(/^[a-zA-Z]+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
    	}
    }
     if(type=="number"){
     	var a=$(this).val();
    	if(!a.match(/^\d+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
    	}
    }
    
    if(type=="not_null"){
     	var a=$(this).val();
    	if(a==null || a==""){
    		r=false;
    		$(this).attr("style","border-color:red");
    	}
    }
    
    if(type=="mail"){
    	var a=$(this).val();
    	 var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    	if(!a.match(re)){
    		r=false;
    		$(this).attr("style","border-color:red");

    	}
    }
});

    $("select[validation]").each(function(){

        $(this).removeAttr("style");
        var type = $(this).attr("validation");
        if(type=="name"){
            var a=$(this).val();
            if(!a.match(/^[a-zA-Z]+$/)){
                r=false;
                $(this).attr("style","border-color:red");
            }
        }
        if(type=="number"){
            var a=$(this).val();
            if(!a.match(/^\d+$/)){
                r=false;
                $(this).attr("style","border-color:red");
            }
        }

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");

            }
        }
    });


	return r;
}

function register_validation(){
    var r=true;


    $("input[Regvalidation]").each(function(){


        $(this).removeAttr("style");
        var type = $(this).attr("Regvalidation");

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");

            }
        }
    });

    if(!r){
        return r;
    }

    if($('[name="password"]').val() != $('[name="confirmPassword"]').val()){

        alert("Paswords not same!!!");
        return false;
    }

    if (!$('[name="agreement"]').is(':checked')){
        alert("please check agreement to register");
        r=false;
    }

    return r;
}

function login_validation(){
    var r=true;


    $("input[Logvalidation]").each(function(){


        $(this).removeAttr("style");
        var type = $(this).attr("Logvalidation");

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");

            }
        }
    });

    return r;
}
function validation(){
	var r=true;
	
	
	$("input[validation]").each(function(){

	$(this).removeAttr("style");
    $(this).removeClass("validation_error_fields");
    var type = $(this).attr("validation");
    if(type=="name"){
    	var a=$(this).val();
    	if(!a.match(/^[a-zA-Z]+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
     if(type=="number"){
     	var a=$(this).val();
    	if(!a.match(/^\d+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
        if(type=="double"){
            var a=$(this).val();
            if(!a.match(/^[0-9]+(\.[0-9]+)?$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

    if(type=="not_null"){
     	var a=$(this).val();
    	if(a==null || a==""){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
    
    if(type=="mail"){
    	var a=$(this).val();
    	 var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    	if(!a.match(re)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");

    	}
    }
});

    $("select[validation]").each(function(){

        $(this).removeAttr("style");
        $(this).removeClass("validation_error_fields");
        var type = $(this).attr("validation");
        if(type=="name"){
            var a=$(this).val();
            if(!a.match(/^[a-zA-Z]+$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }
        if(type=="number"){
            var a=$(this).val();
            if(!a.match(/^\d+$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }
    });


	return r;
}


function validation(inputclass){
	var r=true;
	
		var r=true;
	
	$("textarea[validation]."+inputclass).each(function(){
		$(this).removeAttr("style");
    $(this).removeClass("validation_error_fields");
    var type = $(this).attr("validation");
		if(type=="not_null"){
	     	var a=$(this).val();
	    	if(a==null || a==""){
	    		r=false;
	    		$(this).attr("style","border-color:red");
	            $(this).addClass("validation_error_fields");
	    	}
	    }
	});
	
	$("input[validation]."+inputclass).each(function(){

	$(this).removeAttr("style");
    $(this).removeClass("validation_error_fields");
    var type = $(this).attr("validation");
    if(type=="name"){
    	var a=$(this).val();
    	if(!a.match(/^[a-zA-Z]+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
     if(type=="number"){
     	var a=$(this).val();
    	if(!a.match(/^\d+$/)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
        if(type=="double"){
            var a=$(this).val();
            if(!a.match(/^[0-9]+(\.[0-9]+)?$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

    if(type=="not_null"){
     	var a=$(this).val();
    	if(a==null || a==""){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");
    	}
    }
    
    if(type=="mail"){
    	var a=$(this).val();
    	 var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    	if(!a.match(re)){
    		r=false;
    		$(this).attr("style","border-color:red");
            $(this).addClass("validation_error_fields");

    	}
    }
});

    $("select[validation]."+inputclass).each(function(){

        $(this).removeAttr("style");
        $(this).removeClass("validation_error_fields");
        var type = $(this).attr("validation");
        if(type=="name"){
            var a=$(this).val();
            if(!a.match(/^[a-zA-Z]+$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }
        if(type=="number"){
            var a=$(this).val();
            if(!a.match(/^\d+$/)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");
                $(this).addClass("validation_error_fields");
            }
        }
    });


	return r;
}

function register_validation(){
    var r=true;


    $("input[Regvalidation]").each(function(){


        $(this).removeAttr("style");
        $(this).removeClass("validation_error_fields");
        var type = $(this).attr("Regvalidation");

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
                $("#registerErrorAlert" ).html("Некоторые поля заполнены неправильно");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");
                $("#registerErrorAlert" ).html("Некоторые поля заполнены неправильно");

            }
        }
    });

    if(!r){
        return r;
    }

    if($('[name="password"]').val() != $('[name="confirmPassword"]').val()){

        $("#registerErrorAlert" ).html("Пароли не совпадают");
        $(this).addClass("validation_error_fields");
        return false;
    }

    if (!$('[name="agreement"]').is(':checked')){
        $("#registerErrorAlert" ).html("Чтобы зарегистрироваться, вам нужно согласиться с условиями использования.");
        $(this).addClass("validation_error_fields");
        r=false;
    }

    return r;
}

function login_validation(){
    var r=true;


    $("input[Logvalidation]").each(function(){


        $(this).removeAttr("style");
        $(this).removeClass("validation_error_fields");
        var type = $(this).attr("Logvalidation");

        if(type=="not_null"){
            var a=$(this).val();
            if(a==null || a==""){
                r=false;
                $(this).attr("style","border-color:red");
                $("#loginErrorAlert" ).html("Некоторые поля заполнены неправильно");
            }
        }

        if(type=="mail"){
            var a=$(this).val();
            var re = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if(!a.match(re)){
                r=false;
                $(this).attr("style","border-color:red");
                $("#loginErrorAlert" ).html("Некоторые поля заполнены неправильно");
            }
        }
    });

    return r;
}
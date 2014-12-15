/*
 *	watermark.js jQuery plugin
 *	Watermarked images with javascript and htmlcanvas	
 *
 *	author: Patrick Wied ( http://www.patrick-wied.at )
 *	version: 1.0
 *	license: MIT - feel free to use, modify, redistribute
 *	http://letmein.at/software/how-to-correctly-use-code-you-didnt-write/
 */
(function($){
	$.fn.brightness = function(){
		var doc = this,
		className = "watermark",
		getBrightness = function(imageSrc,callback) {
		    var img = document.createElement("img");
		    img.src = imageSrc;
		    img.style.display = "none";
		    document.body.appendChild(img);
		    var colorSum = 0;
		    img.onload = function() {
		        // create canvas
		        var canvas = document.createElement("canvas");
		        canvas.width = this.width;
		        canvas.height = this.height;
		        var ctx = canvas.getContext("2d");
		        ctx.drawImage(this,0,0);
		        var imageData = ctx.getImageData(0,0,canvas.width,canvas.height);
		        var data = imageData.data;
		        var r,g,b,avg;
		          for(var x = 0, len = data.length; x < len; x+=4) {
		            r = data[x];
		            g = data[x+1];
		            b = data[x+2];
		            avg = Math.floor((r+g+b)/3);
		            colorSum += avg;
		        }
		        var brightness = Math.floor(colorSum / (this.width*this.height));		        
		        callback(brightness);
		    }
		},
		changeClass= function(){
			
			setTimeout(function(){
				var els = $('.'+className);

				els.each(function(){
					//opacity = 10;
					
					var img = $(this);
					getBrightness(img.context.currentSrc,function(brightness){
						if(brightness<100){
							//dark
							img.addClass('dark');
							console.log("dark")
						}
						else{
							img.addClass('bright');
							console.log("bright")
							//bright
						}
					});
				});
			},10);
		};
		changeClass();
	};
	
})(jQuery);	

(function($){
	$.fn.watermark = function(cfg){
		var doc = this,
		gcanvas = {},
		gctx = {},
		imgQueue = [],
		className = "watermark",
		watermark = false,
		watermarkPosition = "bottom-right",
		watermarkPath = "watermark.png?"+(+(new Date())),
		opacity = (255/(100/50)), // 50%
		initCanvas = function(){
			gcanvas = $('<canvas style="display:none"></canvas>');
			gctx = gcanvas[0].getContext("2d");
			$('body').append(gcanvas);
		},
		initWatermark = function(){
			watermark = $('<img src="'+watermarkPath+'" />');

			if(opacity != 255){
				if(!watermark[0].complete)
					watermark[0].onload = function(){	
						applyTransparency();
					};
				else
					applyTransparency();
				

			}else{
				applyWatermarks();
			}
			
		},
		// function for applying transparency to the watermark
		applyTransparency = function(){
			var w = watermark[0].width || watermark[0].offsetWidth,
			h = watermark[0].height || watermark[0].offsetHeight;
			
			setCanvasSize(w, h);
			gctx.drawImage(watermark[0], 0, 0);
					
			var image = gctx.getImageData(0, 0, w, h);
			var imageData = image.data,
			length = imageData.length;
			for(var i=3; i < length; i+=4){  
				imageData[i] = (imageData[i]<opacity)?imageData[i]:opacity;
			}
			image.data = imageData;
			gctx.putImageData(image, 0, 0);
			watermark[0].onload = null;
			watermark.attr("src", "");
			watermark.attr("src", gcanvas[0].toDataURL());
			// assign img attributes to the transparent watermark
			// because browsers recalculation doesn't work as fast as needed
			watermark.width(w);
			watermark.height(h);

			applyWatermarks();
		},
		configure = function(config){
			if(config){
				
				if(config["watermark"])
					watermark = config["watermark"];
				if(config["path"])
					watermarkPath = config["path"];
				if(config["position"])
					watermarkPosition = config["position"];
				if(config["opacity"])
					opacity = (255/(100/config["opacity"]));
				if(config["className"])
					className = config["className"];
			
			}

			initCanvas();
			initWatermark();
		}
		setCanvasSize = function(w, h){
			gcanvas[0].width = w;
			gcanvas[0].height = h;
		},
		applyWatermark = function(img){

			//setCanvasSize(img[0].width || img[0].offsetWidth, img[0].height || img[0].offsetHeight);
			setCanvasSize(img[0].naturalWidth, img[0].naturalHeight);
			gctx.drawImage(img[0], 0, 0);

			var position = watermarkPosition,
			x = 0,
			y = 0;
			if(position.indexOf("top")!=-1)
				y = 10;
			else
				y = gcanvas.height()-watermark.height()-10;
			
			if(position.indexOf("left")!=-1)
				x = 10;
			else
				x = gcanvas.width()-watermark.width()-10;


			gctx.drawImage(watermark[0], x, y);
			img[0].onload = null;
	
			img.attr("src", gcanvas[0].toDataURL());
		},
		applyWatermarks = function(){
			setTimeout(function(){
				var els = $('.'+className);

				els.each(function(){

					var img = $(this);
					if(img[0].tagName.toUpperCase() != "IMG")
						return;
	
					
					if(!img[0].complete){

						img[0].onload = function(){
							applyWatermark(img);
						};
					}else{
						applyWatermark(img);
					}
				});
			},10);
		};
		configure(cfg);
	};
})(jQuery);
// 87888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
(function($){
	$.fn.watermark1 = function(cfg){
		var doc = this,
		gcanvas = {},
		gctx = {},
		imgQueue = [],
		className = "watermark1",
		watermark = false,
		watermarkPosition = "bottom-right",
		watermarkPath = "watermark.png?"+(+(new Date())),
		opacity = (255/(100/50)), // 50%
		initCanvas = function(){
			gcanvas = $('<canvas style="display:none"></canvas>');
			gctx = gcanvas[0].getContext("2d");
			$('body').append(gcanvas);
		},
		initWatermark = function(){
			watermark = $('<img src="'+watermarkPath+'" />');

			if(opacity != 255){
				if(!watermark[0].complete)
					watermark[0].onload = function(){	
						applyTransparency();
					};
				else
					applyTransparency();
				

			}else{
				applyWatermarks();
			}
			
		},
		// function for applying transparency to the watermark
		applyTransparency = function(){
			var w = watermark[0].width || watermark[0].offsetWidth,
			h = watermark[0].height || watermark[0].offsetHeight;
			
			setCanvasSize(w, h);
			gctx.drawImage(watermark[0], 0, 0);
					
			var image = gctx.getImageData(0, 0, w, h);
			var imageData = image.data,
			length = imageData.length;
			for(var i=3; i < length; i+=4){  
				imageData[i] = (imageData[i]<opacity)?imageData[i]:opacity;
			}
			image.data = imageData;
			gctx.putImageData(image, 0, 0);
			watermark[0].onload = null;
			watermark.attr("src", "");
			watermark.attr("src", gcanvas[0].toDataURL());
			// assign img attributes to the transparent watermark
			// because browsers recalculation doesn't work as fast as needed
			watermark.width(w);
			watermark.height(h);

			applyWatermarks();
		},
		configure = function(config){
			if(config){
				
				if(config["watermark"])
					watermark = config["watermark"];
				if(config["path"])
					watermarkPath = config["path"];
				if(config["position"])
					watermarkPosition = config["position"];
				if(config["opacity"])
					opacity = (255/(100/config["opacity"]));
				if(config["className"])
					className = config["className"];
			
			}

			initCanvas();
			initWatermark();
		}
		setCanvasSize = function(w, h){
			gcanvas[0].width = w;
			gcanvas[0].height = h;
		},
		applyWatermark = function(img){

			//setCanvasSize(img[0].width || img[0].offsetWidth, img[0].height || img[0].offsetHeight);
			setCanvasSize(img[0].naturalWidth, img[0].naturalHeight);
			gctx.drawImage(img[0], 0, 0);

			var position = watermarkPosition,
			x = 0,
			y = 0;
			if(position.indexOf("top")!=-1)
				y = 10;
			else
				y = gcanvas.height()-watermark.height()-10;
			
			if(position.indexOf("left")!=-1)
				x = 10;
			else
				x = gcanvas.width()-watermark.width()-10;


			gctx.drawImage(watermark[0], x, y);
			img[0].onload = null;
	
			img.attr("src", gcanvas[0].toDataURL());
		},
		applyWatermarks = function(){
			setTimeout(function(){
				var els = $('.'+className);

				els.each(function(){

					var img = $(this);
					if(img[0].tagName.toUpperCase() != "IMG")
						return;
	
					
					if(!img[0].complete){

						img[0].onload = function(){
							applyWatermark(img);
						};
					}else{
						applyWatermark(img);
					}
				});
			},10);
		};
		configure(cfg);
	};
})(jQuery);
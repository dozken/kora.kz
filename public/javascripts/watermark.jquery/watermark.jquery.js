(function($){
	$.fn.watermark = function(cfg){
		var doc = this,
		gcanvas = {},
		gctx = {},
		imgQueue = [],
		className = "watermark",
		brightWatermark = "brightWatermark",
		darkWatermark = "darkWatermark",
		watermark = false,
		watermarkPosition = "bottom-right",
		watermarkPath = "watermark.png?"+(+(new Date())),
		opacity = (255/(100/50)), // 50%
		initCanvas = function(){
			gcanvas = $('<canvas style="display:none"></canvas>');
			gctx = gcanvas[0].getContext("2d");
			$('body').append(gcanvas);
		},
		// function for applying transparency to the watermark
		applyTransparency = function(){
			var w = watermark[0].naturalWidth,
			h = watermark[0].naturalHeight;
			
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

			//applyWatermarks();
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
				if(config["brightWatermark"])
					brightWatermark = config["brightWatermark"];
				if(config["darkWatermark"])
					darkWatermark = config["darkWatermark"];
			
			}

			initCanvas();
			applyWatermarks();
		}
		setCanvasSize = function(w, h){
			gcanvas[0].width = w;
			gcanvas[0].height = h;
		},
		applyWatermark = function(img){

			setCanvasSize(img[0].naturalWidth, img[0].naturalHeight);
			gctx.drawImage(img[0], 0, 0,img[0].naturalWidth,img[0].naturalHeight);

			var position = watermarkPosition,
			x = 0,
			y = 0;

			if(position.indexOf("top")!=-1) y = 10;
			else
				y = gcanvas.height()/1.2;
			
			if(position.indexOf("left")!=-1)
				x = 10;
			else
				x = gcanvas.width()/1.60;

			//Here drawing watermark			
			gctx.drawImage(watermark[0], x, y,gcanvas.width()/3,gcanvas.height()/10);
			img[0].onload = null;
			img.attr("src", gcanvas[0].toDataURL());
		},
		getBrightness = function(image) {
		    var img = new Image();
			img.src = image.context.currentSrc;
		    var colorSum = 0;

			var canvas = document.createElement("canvas");
			canvas.width = img.width;
			canvas.height = img.height;
			var ctx = canvas.getContext("2d");
			ctx.drawImage(img,0,0);
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
			var brightness = Math.floor(colorSum / (img.width*img.height));
			return brightness;
		},
		applyWatermarks = function(){
			setTimeout(function(){
				var els = $('.'+className);
				var i = 0;
				els.each(function(){
					var img = $(this);

					console.log(getBrightness(img))
					if(getBrightness(img)<200)
						watermark = $("#"+brightWatermark);
					else
						watermark = $("#"+darkWatermark);
					
					applyTransparency();

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

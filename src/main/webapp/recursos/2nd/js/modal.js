define(function () {
	
	var Modal = function (href) {
		this.elemento = $("#modal");
		this.exibido = false;
		this.elemento.on("click", "[data-esconder='modal']", $.proxy(this.esconder, this));
	};

	Modal.prototype = {
		constructor: Modal, 
		
		exibir: function(href, carregado) {
			var $this = this;

	        if ($this.exibido) {
	        	return;
	        }

	        $this.elemento.removeAttr("style");
	        $this.elemento.addClass("pre");
	        $this.exibido = true;
	        $this.teclado();
	        
	        $this.fundo(function() {
	        	$this.elemento.html('<div class="carregando"></div>');
	        	$this.elemento.show();
	        	$this.elemento.focus();
	        	
	        	$.get(href, function(conteudo) {
	        		$this.elemento.find("div").fadeOut(400, function() {
	        			$this.elemento.html(conteudo).fadeIn(400);
	        			$this.elemento.removeClass("pre");
	        			$this.elemento.css("margin-left", "-" + $this.elemento.width() / 2 + "px");
	        			
	        			if (carregado) {
	        				carregado();
	        			}
	        		});
	        	});
	        });
		},
		
		isExibido: function() {
			return this.exibido;
		},
		
		esconder: function (e) {
			var $this = this;
			
			if (!$this.exibido) {
				return;
			}

			$this.exibido = false;
			$this.teclado();
			
			$this.elemento.fadeOut(200, function() {
				$this.fundo();
			});
		},
		
		fundo: function(retorno) {
			var $this = this;
			
			if ($this.exibido) {
				$("body").append('<div class="fundo-modal"></div>'); 
			} else {
				$(".fundo-modal").remove();
			}
			
			if (retorno) {
				retorno();
			}
		},
		
		teclado: function () {
	        var $this = this;
	        
	        if ($this.exibido) {
	        	$this.elemento.on("keyup", function (e) {
	        		if (e.keyCode === 27) {
	        			$this.esconder();
	        		}
	        	});
	        } else if (!$this.exibido) {
	        	$this.elemento.off("keyup");
	        }
		}
	},
	$modal = new Modal();
	
	return {
		exibir: function(href, carregado) {
			$modal.exibir(href, carregado);
		},
		
		exibido: function() {
			return $modal.isExibido();
		},
	
		esconder: function() {
			$modal.esconder();
		}
	};
});
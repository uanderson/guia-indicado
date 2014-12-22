define(function() {

	var Destaque = function() {
		this.area = $(".area-destaque"), 
		this.destaque = $(".destaque"),
		
		this.area.on("mouseenter", $.proxy(this.pausar, this));
	    this.area.on("mouseleave", $.proxy(this.ciclo, this));
	    this.area.on("click", "a.nave", $.proxy(this.anterior, this));
		this.area.on("click", "a.navd", $.proxy(this.proximo, this));
	};
		
	Destaque.prototype = {
		constructor: Destaque,
		
		ciclo: function(evento) {
			var $this = this;
			
			if ($this.destaque.children().length > 1) {
				$this.intervalo = setInterval(function() {
					$this.proximo();
				}, 10000);
			}
		},
		
		pausar: function(evento) {
			clearInterval(this.intervalo);
			this.intervalo = null;
		},
		
		anterior: function(evento) {
			var $ativo = this.destaque.find('.grupo.ativo'), 
				$filhos = $ativo.parent().children(), 
				$posicaoAtivo = $filhos.index($ativo);
	
			if ($posicaoAtivo === ($filhos.length - 1)) {
				$posicaoAtivo = 0;
			} else {
				$posicaoAtivo++;
			}

			$ativo.removeClass("ativo").stop(true, true).fadeOut(1000);
			$($filhos.get($posicaoAtivo)).addClass("ativo").fadeIn(1000);

			evento.preventDefault();
		},
		
		proximo: function(evento) {
			var $ativo = this.destaque.find('.grupo.ativo'), 
				$filhos = $ativo.parent().children(), 
				$posicaoAtivo = $filhos.index($ativo);
		
			if ($posicaoAtivo === 0) {
				$posicaoAtivo = $filhos.length - 1;
			} else {
				$posicaoAtivo--;
			}

			$ativo.removeClass("ativo").stop(true, true).fadeOut(1000);
			$($filhos.get($posicaoAtivo)).addClass("ativo").fadeIn(1000);

			if (evento) {
				evento.preventDefault();
			}
		}
	};

	return {
		iniciar : function() {
			new Destaque().ciclo();
		}
	};
});
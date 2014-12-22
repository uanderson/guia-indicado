define(["acao"], function(acao) {

	function configurarImagem() {
		var $miniaturas = $(".miniaturas"), 
			$indice = 0;

		$miniaturas.on("click", "img", function(evento) {
			var $url = $(this).attr("src");

			$indice = $url.lastIndexOf(".");
			$url = $url.substr(0, $indice - 1) + "m.jpg";

			$("#imagemPrincipal").attr("src", $url);

			evento.preventDefault();
		});
	}
	
	function configurarAvaliacao() {
		var $avaliacao = $("#avaliacao"),
			$estrelas = $(".estrelas"),
			$corrente = $(".avaliada").last();		

		if ($(".estrelas.off").length === 0) {
			$(".estrela").on("mouseenter", function() {
				$(this).prevAll().andSelf().addClass("sobre");
				$(this).nextAll().removeClass("avaliada");
			});

			$(".estrela").on("mouseleave", function() {
				$(this).prevAll().andSelf().removeClass("sobre");
				$corrente.prevAll().andSelf().addClass("avaliada");
			});
			
			$(".estrela").on("click", function(evento) {
				$(this).prevAll().andSelf().addClass("avaliada");
				$corrente = $(this);
				
				acao.executar({acao: $avaliacao.data("url"),
					dados : {"nota" : ($estrelas.children().index($corrente) + 1)}});
				
				evento.preventDefault();
			});
		}
	}
	
	return {
		iniciar: function() {
			configurarImagem();
			configurarAvaliacao();
		}
	};
});
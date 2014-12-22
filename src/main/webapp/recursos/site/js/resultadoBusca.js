define(function() {
	
	function manejarOrdem() {
		var $ordem = $("#ordem"), 
			$opcoesOrdem = $("#opcoesOrdem");

		$ordem.on("click", ativar);
		$opcoesOrdem.on("mouseleave", inativar);
		$opcoesOrdem.on("click", "a", inativar);

		function ativar(evento) {
			$opcoesOrdem.addClass("ativo");
			evento.preventDefault();
		}

		function inativar(evento) {
			$opcoesOrdem.removeClass("ativo");
		}
	}

	return {
		iniciar : function() {
			manejarOrdem();
		}
	};
});
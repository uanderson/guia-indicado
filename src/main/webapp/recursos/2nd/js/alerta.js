define(function() {
	var $alerta = $("#alerta");
	
	$alerta.on("click", ".dispensar", function(evento) {
		$alerta.fadeOut(200);
		evento.preventDefault();
	});
	
	return {
		exibir: function(mensagem) {
			if (mensagem === "") {
				return;
			}
		
			$alerta.find(".texto-mensagem").html(mensagem);
			$alerta.fadeIn(200);
		}
	};
});

define(["alerta"], function(alerta) {
	
	function iniciarEventos() {
		var $adicionar = $("#adicionar"),
			$imagem = $("#imagem");
	
		$adicionar.on("click", function(evento) {
			$imagem.click();
		});
		
		$imagem.on("change", function(evento) {
			$("#form").submit();
		});
	}
	
	return {
		iniciar: function() {
			iniciarEventos();
		}
	};
});
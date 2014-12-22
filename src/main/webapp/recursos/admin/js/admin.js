require.config({
	urlArgs : "burst=" + new Date().getTime()
});

require(["acao"], function(acao) {
	
	if ($ite.MOJ !== "") {
		require([$ite.MOJ], function(modulo) {
			modulo.iniciar();
		});
	}

	$("[data-foco='iniciado']").focus();
	
	$.mask.masks["latitude"] = {mask: "999999,9999999999", type: "reverse", defaultValue: "+00,0"};
	$.mask.masks["longitude"] = {mask: "999999,9999999999", type: "reverse", defaultValue: "+00,0"};
	
	$("input[data-mask]").each(function() {
		var $input = $(this);
		$input.setMask($input.data("mask"));
	});
	
	/**
	 * Data API
	 */
	(function() {
		$("body").on("click.button", "[data-form]", function(e) {
			var $botao = $(e.target);
			
			$botao.button("loading");
			
			acao.executar({
				acao: $botao.data("form"),
				termino: function() {
					$botao.button("reset");
				}
			});
			
			e.preventDefault();
		});
		
		$("body").on("click.a", "[data-acao='direta']", function(e) {
			acao.executar({acao: $(this).attr("href")});
			e.preventDefault();
		});
	})();
});

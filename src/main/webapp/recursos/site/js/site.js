require(["acao", "modal"], function(acao, modal) {
	
	if ($ite.MOJ !== "") {
		require([$ite.MOJ], function(modulo) {
			modulo.iniciar();
		});
	}
	
	$("[data-foco='iniciado']").focus();
	$("input[data-mask]").each(function() {
		var $input = $(this);
		$input.setMask($input.data("mask"));
	});
	
	setInterval(function() {
		$.get($ite.URL + "/status");
	}, (60 * 1000 * 8));
	
	/**
	 * Data API
	 */
	(function() {
		$("body").on("click.button", "[data-form]", function(e) {
			var $botao = $(e.target),
				$texto = $botao.text();
			
			$botao.attr("disabled", "disabled");
			
			if ($botao.data("processando")) {
				$botao.text($botao.data("processando"));
			}

			acao.executar({
				acao: $botao.data("form"),
				termino: function() {
					$botao.removeAttr("disabled");
					$botao.text($texto);
				}
			});
			
			e.preventDefault();
		});
		
		$("body").on("click", "[data-alvo='modal']", function(e) {
			modal.exibir($(this).attr("href").replace("#", ""), function() {
				$("#modal").find("input[data-mask]").each(function() {
					var $input = $(this);
					$input.setMask($input.data("mask"));
				});
				
				$("#modal").find("[data-foco='iniciado']").focus();
			});
			
			e.preventDefault();
		});
	})();
	
	/**
	 * Menu entrar
	 */
	(function() {
		var $entrar = $("#entrar"),
			$navMenu = $("#navMenu");

		if (!$entrar.hasClass("logado")) {
			return;
		}
		
		$entrar.on("click", ativar);
		$navMenu.on("mouseleave", inativarPrevenindo);
		$navMenu.on("click", "a", inativar);
		$navMenu.find(".nome").on("click", inativarPrevenindo);
		
		function ativar(e) {
			$navMenu.addClass("ativo");
			e.preventDefault();
		}
		
		function inativarPrevenindo(e) {
			inativar(e);
			e.preventDefault();
		}
		
		function inativar(e) {
			$navMenu.removeClass("ativo");
		}
	})();
	
	/**
	 * Combo
	 */
	(function($) {
		var $cidade = $(".busca .cidade"),
			$lista = $cidade.find(".lista"),
			$selecionada = $cidade.find(".selecionada"),
			$local = $cidade.find("#local");
		
		if ($lista.find("li").length === 0) {
			return;
		}
		
		$cidade.on("click", function(e) {
			if ($lista.hasClass("ativa")) {
				inativar(e);
			} else {
				ativar(e);
			}
		});
		
		$lista.on("click", "li", function(e) {
			$selecionada.text($(this).text());
			$local.val($(this).data("valor"));
			inativar(e);
			return false;
		});
		
		function ativar(e) {
			$lista.addClass("ativa");
			e.preventDefault();
		}
		
		function inativar(e) {
			$lista.removeClass("ativa");
			e.preventDefault();
		}
	})(jQuery);
});

define(["alerta"], function(alerta) {
	
	function configurarImagem() {
		var $selecao = $("#selecao"),
			$bannerTemp = $("#bannerTemp"),
			$banner = $("#banner"),
			$progresso = $("#progresso"),
			$percentualEnvio = 0;
	
		$selecao.on("click", function() {
			$bannerTemp.click();
			$bannerTemp.fileupload({
			    sequentialUploads: true,
			    progressall: function (e, dados) {
			    	$percentualEnvio = parseInt(dados.loaded / dados.total * 100, 10);
			        $progresso.find(".bar").css("width", $percentualEnvio + "%");
			    },
			    start: function (e, dados) {
			    	$progresso.show();
		        },
			    done: function (e, dados) {
			    	var $resultado = dados.result;
			    	
			    	if ("status" in $resultado && $resultado.status === "SUCESSO") {
			    		$banner.val($resultado.retorno.banner);
			    	}
			    	
			    	$("#progresso").hide();
			    	alerta.exibir($resultado.mensagem);
			    },
			    fail: function(e, dados) {
			    	$progresso.hide();
			    	alerta.exibir("Não foi possível enviar o banner.");
		        }
		    });
		});
	}
	
	function configurarAnunciante() {
		$("#anunciante").autocomplete({
			source: function(req, resposta) {
				$.getJSON($ite.URL + "/anunciantes/pesquisa.json", {termo : req.term}, function(resultado) {
					resposta($.map(resultado, function(item) {
						return {label: item.nome, value: item};
					}));
				});
			},
			minLength: 3,
			select: function(event, ui) {
				$("#codigoAnunciante").val(ui.item.value.id);
				$(this).val(ui.item.label);
				
				return false;
			},
			focus: function() {
				return false;
			}
		});
	}
	
	return {
		iniciar: function() {
			configurarImagem();
			configurarAnunciante();
		}
	};
});
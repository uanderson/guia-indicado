define(["alerta"], function(alerta) {
	
	function configurarCategoria() {
		$("#categoria").autocomplete({
			source: function(req, resposta) {
				$.getJSON($ite.SURL + "/categorias/pesquisa.json", {termo : req.term}, function(resultado) {
					resposta($.map(resultado, function(item) {
						return {label: item.nome, value: item};
					}));
				});
			},
			minLength: 3,
			select: function(event, ui) {
				$("#codigoCategoria").val(ui.item.value.id);
				$(this).val(ui.item.label);
				return false;
			},
			focus: function() {
				return false;
			}
		});
	}
	
	function configurarCep() {
		var $endereco = $("#endereco"),
			$bairro = $("#bairro"),
			$cidade = $("#codigoCidade"),
			$nomeCidade = $("#cidade");
		
		$("#cep").on("change", function() {
			$endereco.val("");
			$bairro.val("");
			$cidade.val("");
			$nomeCidade.val("");
			
			if ($(this).val() !== "") {
				$.getJSON($ite.SURL + "/ceps/pesquisa.json", {cep : $(this).val()}, function(resultado) {
					if (resultado) {
						$endereco.val(resultado.endereco);
						$bairro.val(resultado.bairro);
						$cidade.val(resultado.cidade);
						$nomeCidade.val(resultado.nomeCidade + " (" + resultado.siglaEstado + ")");
					}
				});
			}
		});
	}
	
	function configurarCidade() {
		$("#cidade").autocomplete({
			source: function(req, resposta) {
				$.getJSON($ite.SURL + "/cidades/pesquisa.json", {termo : req.term}, function(resultado) {
					resposta($.map(resultado, function(item) {
						return {label : item.nome + " (" + item.siglaEstado + ")", value : item};
					}));
				});
			},
            minLength: 3,
			select: function(event, ui) {
				$("#codigoCidade").val(ui.item.value.id);
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
			configurarCategoria();
			configurarCep();
			configurarCidade();
		}
	};
});
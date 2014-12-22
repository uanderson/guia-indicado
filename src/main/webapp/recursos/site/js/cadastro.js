define(function() {
	
	function configurarCidade() {
		$("#cidade").autocomplete({
			source: function(req, resposta) {
				$.getJSON($ite.URL + "/cidades/pesquisa.json", {termo : req.term}, function(resultado) {
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
			configurarCidade();
		}
	};
});
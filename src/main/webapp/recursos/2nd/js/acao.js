define(["alerta", "modal"], function(alerta, modal) {
	
	var padroes = {
		acao: "",
		dados: {},
		termino: function() {return true;}
	},
	Acao = function(opcoes) {
		this.opcoes = $.extend({}, padroes, opcoes);
		this.acaoTipoForm = (this.opcoes.acao.substring(0, 1) === "#");
		this.form = null;
		this.idForm = ""; 
	
		if (this.acaoTipoForm) {
			this.idForm = this.opcoes.acao;
			this.form = $(this.idForm);
			this.opcoes.acao = this.form.attr("action");
		}
	};
	
	Acao.prototype = {
		constructor: Acao,
		
		enviar: function() {
			var $this = this;
			
			if ($this.acaoTipoForm) {
				$this.opcoes.dados = $this.form.serialize();
			}
			
			$.ajax({
				type: "post",
				url: $this.opcoes.acao,
				data: $this.opcoes.dados
			})
			.done(function(resposta) {
				if (resposta.status === "SUCESSO") {
					$this.processarSucesso(resposta);
				} else if (resposta.status === "ERRO") {
					$this.processarErro(resposta);
					$this.processarFormErro(resposta);
				}
			})
			.fail(function(xhr) {
				alerta.exibir("Não foi possível atender a sua solicitação.");
			})
			.always(function() {
				$this.opcoes.termino();
			});
		},
		
		processarSucesso: function(resposta) {
			var $this = this,
				$redirecionar = false,
				$recarregar = false;
			
			if ("redirecionar" in resposta && resposta.redirecionar !== "") {
				window.location.replace(resposta.redirecionar);
				$redirecionar = true;
			}
			
			if ("recarregar" in resposta) {
				window.location.reload(true);
				$recarregar = true;
			}

			if (!$redirecionar && !$recarregar) {
				if ($this.acaoTipoForm) {
					if (modal.exibido()) {
						modal.esconder();
					}
					
					$this.form.find(".msg-erro").addClass("esconder");
					$this.form.find(".erro").removeClass("erro");
					$this.form.get(0).reset();
				}
				
				alerta.exibir(resposta.mensagem);
			}
		},
		
		processarErro: function(resposta) {
			alerta.exibir(resposta.mensagem);
		},
		
		processarFormErro: function(resposta) {
			var $this = this;
			
			if ($this.acaoTipoForm) {
				$this.form.find(".msg-erro").addClass("esconder");
				$this.form.find(".erro").removeClass("erro");
				
				if ("notificacoes" in resposta) {
					$.each(resposta.notificacoes, function(indice, notificacao) {
						$this.form.find("[name='" + notificacao.origem + "']").addClass("erro");
						$this.form.find("#" + notificacao.origem + "Erro")
							.html(notificacao.mensagem).removeClass("esconder");
					});
				}
			}
		}
	};
	
	return {
		definir: function(opcoes) {
			padroes = $.extend({}, padroes, opcoes);
		},
		
		executar: function(opcoes) {
			new Acao(opcoes).enviar();
		}
	};
});
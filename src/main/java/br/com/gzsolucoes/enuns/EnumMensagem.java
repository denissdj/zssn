package br.com.gzsolucoes.enuns;

public enum EnumMensagem {

    MSG_001 ("Campo Obrigatório não informado!"),
    MSG_002 ("Usuário não encontrado!"),
    MSG_003 ("Operação realizada com sucesso."),
    MSG_004 (" Usuário possui vinculos. Não é possível remover."),
    
    MSG_999 ("Mensagem Defalt");

	private final String descricao;
	
	private EnumMensagem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}

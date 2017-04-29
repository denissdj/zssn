package br.com.gzsolucoes.enuns;


public enum EnumStatusUsuario {

	INFECTADO("Infectado", true),

	NAO_INFECTADO("Não Infectado", false);

	private String value;

	private boolean status;

	private EnumStatusUsuario( final String value, final boolean status ) {
		this.value = value;
		this.status = status;
	}

	public String getValue() {
		return this.value;
	}

	public boolean isStatus() {
		return this.status;
	}
}
package br.com.gzsolucoes.enuns;


public enum EnumSexo {

	MASCULINO("Masculino", "M"),

	FEMININO("Feminino", "F");

	private String descricao;

	private String sigla;

	private EnumSexo( final String descricao, final String sigla ) {
		this.descricao = descricao;
		this.sigla = sigla;
	}
	
	public String getDescricao() {
		return descricao;
	}

	
	public String getSigla() {
		return sigla;
	}

}
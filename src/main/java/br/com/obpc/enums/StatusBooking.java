package br.com.obpc.enums;

public enum StatusBooking {
	
	PENDENTE("PENDENTE"),
    ATIVO("ATIVO"),
    ATRASADO("DEVOLUCAO PENDENTE"),
    FINALIZADO("FINALIZADO"),
    CANCELADO("CANCELADO");

    private String descricao;

    StatusBooking(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
	

}

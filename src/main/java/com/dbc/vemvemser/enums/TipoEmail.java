package com.dbc.vemvemser.enums;

import lombok.Getter;

@Getter
public enum TipoEmail {
    INSCRICAO(" Tua candidatura para o processo seletivo foi recebida com sucesso. " +
            "  Avaliaremos o teu currículo e as respostas do questionário e," +
            " caso aprovado para a próxima etapa, entraremos em contato para dar seguimento no processo seletivo. <br>" +
            "As próximas etapas consistem na realização do teste de lógica e entrevista individual.", "DBC Company - Inscrição"),

    APROVADO("Olá, tudo bem? <br>" +
            "Gostaríamos de informar que foste aprovado(a) para a primeira <br>" +
            "etapa de seleção do Vem Ser DBC. <br>", "DBC Company - Primeira Etapa"),

    REPROVADO("Olá, tudo bem? <br>" +
            "Obrigado por ter participado do processo de seleção do Vem Ser DBC!" +
            "Neste momento, selecionamos apenas algumas pessoas dos milhares inscritos." +
            "Queremos que você continue tentando, pois este é apenas o começo," +
            "então separamos algumas dicas e links para que você siga evoluindo na área e venha trabalhar conosco numa próxima oportunidade.",
            "DBC Company - Primeira etapa."),

    RECOVER_PASSWORD("Trouble signing in?<br>" +
            "Resetting your password is easy.<br>" +
            "Just press the button below and follow the instructions. We’ll have you up and running in no time.<br>" +
            "/url/" +
            " If you did not make this request then please ignore this email.<br>", "DBC Company - recuperação de senha");

    private String descricao;
    private String subject;

    TipoEmail(String descricao, String subject) {
        this.subject = subject;
        this.descricao = descricao;
    }


}

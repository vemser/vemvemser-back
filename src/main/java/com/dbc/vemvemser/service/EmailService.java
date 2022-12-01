package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;


    public void sendEmail(CandidatoDto candidatoDto, TipoEmail tipoEmail) throws RegraDeNegocioException {
        final String SUBJECT=" Processo Seletivo Vem Ser DBC - Primeira Etapa";

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(candidatoDto.getEmail());
            mimeMessageHelper.setSubject(SUBJECT);
            mimeMessageHelper.setText(geContentFromTemplate(candidatoDto,tipoEmail), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Erro ao enviar email");
        }
    }

    public String geContentFromTemplate(CandidatoDto candidatoDto, TipoEmail tipoEmail) throws IOException, TemplateException {


        final String MESSAGE_INSCRICAO = " Tua candidatura para o processo seletivo foi recebida com sucesso. " +
                "  Avaliaremos o teu currículo e as respostas do questionário e," +
                " caso aprovado para a próxima etapa, entraremos em contato para dar seguimento no processo seletivo. <br>"+
                "As próximas etapas consistem na realização do teste de lógica e entrevista individual.";

        final String MESSAGE_APROVADO = "Olá, tudo bem? <br>" +
                "Gostaríamos de informar que foste aprovado(a) para a primeira <br>" +
                "etapa de seleção do Vem Ser DBC. <br>";

        final String MESSAGE_REPROVADO = "Olá, tudo bem? <br>" +
                "Obrigado por ter participado do processo de seleção do Vem Ser DBC!" +
                "Neste momento, selecionamos apenas algumas pessoas dos milhares inscritos." +
                "Queremos que você continue tentando, pois este é apenas o começo, " +
                "então separamos algumas dicas e links para que você siga evoluindo na área e venha trabalhar conosco numa próxima oportunidade.";

        final String MESSAGE_DUVIDA= "Em caso de dúvidas, nos contate através do e-mail <br> VemSer@dbccompany.com.br";

        final String MESSAGE_SPAM_EMAIL = "Fique atento a sua Caixa de Entrada e Spam, receberá um email referente ao agendamento de sua entrevista!";

        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", candidatoDto.getNome());
        dados.put("email", from);

        if (tipoEmail==TipoEmail.INSCRICAO){
            dados.put("msg1", "Agradecemos o teu interesse em fazer parte do time DBC Company e participar desta edição do Vem Ser DBC. :)");
            dados.put("msg2", MESSAGE_INSCRICAO);
            dados.put("msg3",MESSAGE_DUVIDA);
        }else if (tipoEmail ==TipoEmail.APROVADO){
            dados.put("nome", candidatoDto.getNome());
            dados.put("msg1", MESSAGE_APROVADO);
            dados.put("msg2",MESSAGE_SPAM_EMAIL);
            dados.put("msg3",MESSAGE_DUVIDA);
        } else {
            dados.put("nome", candidatoDto.getNome());
            dados.put("msg1", MESSAGE_REPROVADO);
            dados.put("msg2", " ");
            dados.put("msg3",MESSAGE_DUVIDA);
        }
        Template template = fmConfiguration.getTemplate("email-template-universal.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

}
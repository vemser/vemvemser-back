package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.SendEmailDto;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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


    public void sendEmail(SendEmailDto sendEmailDto, TipoEmail tipoEmail) throws RegraDeNegocioException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(sendEmailDto.getEmail());
            mimeMessageHelper.setSubject(tipoEmail.getSubject());
            mimeMessageHelper.setText(geContentFromTemplate(sendEmailDto, tipoEmail), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Erro ao enviar email");
        }
    }

    public String geContentFromTemplate(SendEmailDto sendEmailDto, TipoEmail tipoEmail) throws IOException, TemplateException {

        final String MESSAGE_DUVIDA = "Em caso de dúvidas, nos contate através do e-mail <br> VemSer@dbccompany.com.br";

        String base = tipoEmail.getDescricao();
        if (tipoEmail.equals(TipoEmail.RECOVER_PASSWORD)) {
            String urlToken = sendEmailDto.getUrlToken();
            base = base.replace("/url/", urlToken);
        }

        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", sendEmailDto.getNome());
        dados.put("email", from);
        dados.put("msg1", "Agradecemos o teu interesse em fazer parte do time DBC Company e participar desta edição do Vem Ser DBC. :)");
        dados.put("msg2", base);
        dados.put("msg3", MESSAGE_DUVIDA);

        Template template = fmConfiguration.getTemplate("email-template-universal.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

}
package com.dbc.vemvemser.service;

import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.SendEmailDto;
import com.dbc.vemvemser.enums.TipoEmail;
import com.dbc.vemvemser.exception.RegraDeNegocioException;
import com.dbc.vemvemser.service.EmailService;
import factory.CandidatoFactory;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private freemarker.template.Configuration fmConfiguration;

    @Mock
    private JavaMailSender emailSender;
    @Mock
    private MimeMessage mimeMessage;
    private String from = "teste_mockito@gmail.com";

    @Before
    public void init() {
        ReflectionTestUtils.setField(emailService, "from", from);
    }

//    @Test
//    public void deveTestarSendEmailComSucesso() throws MessagingException, IOException, RegraDeNegocioException {
//
//        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();
//
//        TipoEmail tipoEmail = TipoEmail.INSCRICAO;
//
//        Template template = new Template("t ", Reader.nullReader());
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        //emailService.sendEmail(candidatoDto, tipoEmail,null);
//
//        verify(emailSender).send(mimeMessage);
//    }


//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarSendEmailComIOException() throws MessagingException, IOException, RegraDeNegocioException {
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
//
//        SendEmailDto sendEmailDto = new SendEmailDto();
//        sendEmailDto.setEmail("");
//        sendEmailDto.setNome("");
//        emailService.sendEmail(sendEmailDto, TipoEmail.INSCRICAO);
//    }


    @Test
    public void deveTestarGeContentFromTemplateComSucesso() throws IOException, TemplateException {

        Template template = new Template("", Reader.nullReader());
        CandidatoDto candidatoDto = CandidatoFactory.getCandidatoDto();

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setNome(candidatoDto.getNome());
        sendEmailDto.setEmail(candidatoDto.getEmail());
        sendEmailDto.setUrlToken("www.ols.com.br");

        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", candidatoDto.getNome());
        dados.put("email", from);
        dados.put("msg1", "Mensagem1");
        dados.put("msg2", "Mensagem2");
        dados.put("msg3", "Mensagem3");
        TipoEmail tipoEmail = TipoEmail.REPROVADO;

        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
        String geContenteFromTemplateRetorno = emailService.geContentFromTemplate(sendEmailDto, tipoEmail);

        assertNotNull(geContenteFromTemplateRetorno);
    }


}

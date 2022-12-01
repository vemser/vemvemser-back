package factory;

import com.dbc.vemvemser.dto.FormularioCreateDto;
import com.dbc.vemvemser.dto.FormularioDto;
import com.dbc.vemvemser.dto.TrilhaDto;
import com.dbc.vemvemser.entity.FormularioEntity;
import com.dbc.vemvemser.entity.TrilhaEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.enums.TipoTurno;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormularioFactory {

    public static FormularioEntity getFormularioEntity() {
        FormularioEntity formularioEntity = new FormularioEntity();

        Set<TrilhaEntity> trilhas = new HashSet<>();
        trilhas.add(TrilhaFactory.getTrilhaEntity());

        formularioEntity.setIdFormulario(1);
        formularioEntity.setMatriculado(TipoMarcacao.T);
        formularioEntity.setCurso("TECNICO T.I");
        formularioEntity.setTurno(TipoTurno.NOITE);
        formularioEntity.setInstituicao("PUC");
        formularioEntity.setGithub("github.com");
        formularioEntity.setLinkedin("linkedin.com");
        formularioEntity.setDesafios(TipoMarcacao.T);
        formularioEntity.setProblema(TipoMarcacao.T);
        formularioEntity.setReconhecimento(TipoMarcacao.T);
        formularioEntity.setAltruismo(TipoMarcacao.T);
        formularioEntity.setResposta("outro");
        formularioEntity.setDisponibilidade(TipoMarcacao.T);
        formularioEntity.setEfetivacao(TipoMarcacao.T);
        formularioEntity.setProva(TipoMarcacao.T);
        formularioEntity.setIngles("Basico");
        formularioEntity.setEspanhol("Não possuo");
        formularioEntity.setNeurodiversidade("Não possuo");
        formularioEntity.setConfiguracoes("16gb RAM");
        formularioEntity.setEfetivacao(TipoMarcacao.T);
        formularioEntity.setDisponibilidade(TipoMarcacao.T);
        formularioEntity.setGenero("MASCULINO");
        formularioEntity.setTrilhaEntitySet(trilhas);
        formularioEntity.setOrientacao("Heterossexual");
        formularioEntity.setLgpd(TipoMarcacao.T);
        formularioEntity.setTrilhaEntitySet(trilhas);

        return formularioEntity;
    }

    public static FormularioCreateDto getFormularioCreateDto() {
        FormularioCreateDto formularioCreateDto = new FormularioCreateDto();

        List<Integer> trilhas = new ArrayList<>();
        trilhas.add(TrilhaFactory.getTrilhaEntity().getIdTrilha());

        formularioCreateDto.setMatriculadoBoolean(true);
        formularioCreateDto.setCurso("TECNICO T.I");
        formularioCreateDto.setTurno(TipoTurno.NOITE);
        formularioCreateDto.setInstituicao("PUC");
        formularioCreateDto.setGithub("github.com");
        formularioCreateDto.setLinkedin("linkedin.com");
        formularioCreateDto.setDesafiosBoolean(true);
        formularioCreateDto.setProblemaBoolean(true);
        formularioCreateDto.setReconhecimentoBoolean(true);
        formularioCreateDto.setAltruismoBoolean(true);
        formularioCreateDto.setResposta("outro");
        formularioCreateDto.setDisponibilidadeBoolean(true);
        formularioCreateDto.setEfetivacaoBoolean(true);
        formularioCreateDto.setProvaBoolean(true);
        formularioCreateDto.setIngles("Basico");
        formularioCreateDto.setEspanhol("Não possuo");
        formularioCreateDto.setNeurodiversidade("Não possuo");
        formularioCreateDto.setConfiguracoes("16gb RAM");
        formularioCreateDto.setEfetivacaoBoolean(true);
        formularioCreateDto.setDisponibilidadeBoolean(true);
        formularioCreateDto.setGenero("MASCULINO");
        formularioCreateDto.setTrilhas(trilhas);
        formularioCreateDto.setOrientacao("Heterossexual");
        formularioCreateDto.setLgpdBoolean(true);

        return formularioCreateDto;
    }

    public static FormularioDto getFormularioDto() {
        FormularioDto formulario = new FormularioDto();

        Set<TrilhaDto> trilhas = new HashSet<>();
        trilhas.add(TrilhaFactory.getTrilhaDto());

        formulario.setIdFormulario(1);
        formulario.setMatriculado(TipoMarcacao.T);
        formulario.setCurso("TECNICO T.I");
        formulario.setTurno(TipoTurno.NOITE);
        formulario.setInstituicao("PUC");
        formulario.setGithub("github.com");
        formulario.setLinkedin("linkedin.com");
        formulario.setDesafios(TipoMarcacao.T);
        formulario.setProblema(TipoMarcacao.T);
        formulario.setReconhecimento(TipoMarcacao.T);
        formulario.setAltruismo(TipoMarcacao.T);
        formulario.setResposta("outro");
        formulario.setDisponibilidade(TipoMarcacao.T);
        formulario.setEfetivacao(TipoMarcacao.T);
        formulario.setProva(TipoMarcacao.T);
        formulario.setIngles("Basico");
        formulario.setEspanhol("Não possuo");
        formulario.setNeurodiversidade("Não possuo");
        formulario.setConfiguracoes("16gb RAM");
        formulario.setEfetivacao(TipoMarcacao.T);
        formulario.setDisponibilidade(TipoMarcacao.T);
        formulario.setGenero("MASCULINO");
        formulario.setOrientacao("Heterossexual");
        formulario.setLgpd(TipoMarcacao.T);
        formulario.setTrilhas(trilhas);

        return formulario;
    }
}

package factory;

import com.dbc.vemvemser.dto.CandidatoDto;
import com.dbc.vemvemser.dto.InscricaoCreateDto;
import com.dbc.vemvemser.dto.InscricaoDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.entity.InscricaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;

import java.time.LocalDate;

public class InscricaoFactory {

    public static InscricaoDto getInscricaoDto() {

        InscricaoDto inscricaoDto = new InscricaoDto();
        inscricaoDto.setIdInscricao(1);
        inscricaoDto.setDataInscricao(LocalDate.now());
        inscricaoDto.setAvaliado(TipoMarcacao.T);
        inscricaoDto.setCandidato(CandidatoFactory.getCandidatoDto());

        return inscricaoDto;
    }

    public static InscricaoEntity getInscricaoEntity() {

        InscricaoEntity inscricaoEntity = new InscricaoEntity();
        inscricaoEntity.setIdInscricao(1);
        inscricaoEntity.setDataInscricao(LocalDate.now());
        inscricaoEntity.setAvaliado(TipoMarcacao.T);
        inscricaoEntity.setCandidato(CandidatoFactory.getCandidatoEntity());
        return inscricaoEntity;
    }

    public static InscricaoCreateDto getInscricaoCreateDto() {
        InscricaoCreateDto inscricao = new InscricaoCreateDto();
        inscricao.setIdCandidato(CandidatoFactory.getCandidatoEntity().getIdCandidato());
        return inscricao;
    }
}

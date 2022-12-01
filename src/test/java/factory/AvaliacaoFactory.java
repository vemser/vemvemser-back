package factory;

import com.dbc.vemvemser.dto.AvaliacaoCreateDto;
import com.dbc.vemvemser.dto.AvaliacaoDto;
import com.dbc.vemvemser.entity.AvaliacaoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;

public class AvaliacaoFactory {


    public static AvaliacaoDto getAvaliacaoDto() {

        AvaliacaoDto avaliacaoDto = new AvaliacaoDto();
        avaliacaoDto.setAprovado(TipoMarcacao.T);
        avaliacaoDto.setIdAvaliacao(1);
        avaliacaoDto.setInscricao(InscricaoFactory.getInscricaoDto());
        
        return avaliacaoDto;
    }

    public static AvaliacaoEntity getAvaliacaoEntity() {

        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        avaliacaoEntity.setInscricao(InscricaoFactory.getInscricaoEntity());
        avaliacaoEntity.setAprovado(TipoMarcacao.T);
        avaliacaoEntity.setIdAvaliacao(1);
        avaliacaoEntity.setAvaliador(GestorFactory.getGestorEntity());

        return avaliacaoEntity;
    }

    public static AvaliacaoCreateDto getAvaliacaoCreateDto() {
        AvaliacaoCreateDto avaliacaoCreateDto = new AvaliacaoCreateDto();
        avaliacaoCreateDto.setAprovadoBoolean(true);
        avaliacaoCreateDto.setIdInscricao(InscricaoFactory.getInscricaoDto().getIdInscricao());

        return avaliacaoCreateDto;
    }


}

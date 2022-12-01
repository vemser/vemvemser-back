package factory;

import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.dto.GestorCreateDto;
import com.dbc.vemvemser.dto.GestorDto;
import com.dbc.vemvemser.entity.CargoEntity;
import com.dbc.vemvemser.entity.GestorEntity;
import com.dbc.vemvemser.enums.TipoCargo;
import com.dbc.vemvemser.enums.TipoMarcacao;

public class GestorFactory {

    public static GestorEntity getGestorEntity() {

        GestorEntity gestorEntity = new GestorEntity();
        gestorEntity.setCargoEntity(CargoFactory.getCargoEntity());
        gestorEntity.setNome("Gestor");
        gestorEntity.setEmail("gestor@dbccompany.com.br");
        gestorEntity.setIdGestor(1);

        return gestorEntity;
    }

    public static GestorDto getGestorDto() {
        GestorDto gestorDto = new GestorDto();
        gestorDto.setIdGestor(1);
        gestorDto.setAtivo(TipoMarcacao.T);
        gestorDto.setNome("Gestor");
        gestorDto.setEmail("gestor@dbccompany.com.br");
        gestorDto.setIdGestor(1);
        gestorDto.setCargoDto(CargoFactory.getCargoDto());

        return gestorDto;
    }

    public static GestorCreateDto getGestorCreateDto(){
        GestorCreateDto gestorDto = new GestorCreateDto();
        gestorDto.setNome("Gestor");
        gestorDto.setEmail("gestor@dbccompany.com.br");
        gestorDto.setSenha("senha");
        gestorDto.setTipoCargo(TipoCargo.ADMINISTRADOR.getCargo());

        return gestorDto;
    }
}

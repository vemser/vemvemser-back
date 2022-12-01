package factory;

import com.dbc.vemvemser.dto.CargoDto;
import com.dbc.vemvemser.entity.CargoEntity;

public class CargoFactory {

    public static CargoDto getCargoDto(){
        CargoDto cargoDto = new CargoDto();
        cargoDto.setIdCargo(1);
        cargoDto.setNome("nome");
        return cargoDto;

    }

    public static CargoEntity getCargoEntity(){
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("nome");
        return cargoEntity;
    }
}

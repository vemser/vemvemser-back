package factory;

import com.dbc.vemvemser.dto.TrilhaDto;
import com.dbc.vemvemser.entity.TrilhaEntity;

import java.util.Collections;

public class TrilhaFactory {

    public static TrilhaDto getTrilhaDto() {
        TrilhaDto trilha = new TrilhaDto();
        trilha.setIdTrilha(1);
        trilha.setNome("Back-end");
        return trilha;
    }

    public static TrilhaEntity getTrilhaEntity() {
        TrilhaEntity trilha = new TrilhaEntity();
        trilha.setIdTrilha(1);
        trilha.setNome("Back-end");
        trilha.setFormularios(Collections.emptySet());
        return trilha;
    }
}

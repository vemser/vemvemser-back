package factory;

import com.dbc.vemvemser.dto.CandidatoCreateDto;
import com.dbc.vemvemser.entity.CandidatoEntity;
import com.dbc.vemvemser.enums.TipoMarcacao;
import com.dbc.vemvemser.dto.CandidatoDto;

import java.time.LocalDate;

public class CandidatoFactory {

    public static CandidatoEntity getCandidatoEntity() {
        CandidatoEntity candidatoEntity = new CandidatoEntity();
        candidatoEntity.setIdCandidato(1);
        candidatoEntity.setNome("Eduardo Sedrez Rodrigues");
        candidatoEntity.setDataNascimento(LocalDate.of(2013, 10, 13));
        candidatoEntity.setEmail("eduardosedrez@gmail.com");
        candidatoEntity.setTelefone("53981258964");
        candidatoEntity.setRg("77.777.777-7");
        candidatoEntity.setCpf("049.239.620-54");
        candidatoEntity.setEstado("Rio Grande do Sul");
        candidatoEntity.setCidade("Pelotas");
        candidatoEntity.setPcd(TipoMarcacao.F);
        candidatoEntity.setFormulario(FormularioFactory.getFormularioEntity());
        return candidatoEntity;
    }

    public static CandidatoCreateDto getCandidatoCreateDto() {
        CandidatoCreateDto candidatoCreateDto = new CandidatoCreateDto();
        candidatoCreateDto.setNome("Eduardo Sedrez Rodrigues");
        candidatoCreateDto.setDataNascimento(LocalDate.of(2013, 10, 13));
        candidatoCreateDto.setEmail("eduardosedrez@gmail.com");
        candidatoCreateDto.setTelefone("53981258964");
        candidatoCreateDto.setRg("77.777.777-7");
        candidatoCreateDto.setCpf("049.239.620-54");
        candidatoCreateDto.setEstado("Rio Grande do Sul");
        candidatoCreateDto.setCidade("Pelotas");
        candidatoCreateDto.setPcdBoolean(false);
        candidatoCreateDto.setIdFormulario(FormularioFactory.getFormularioEntity().getIdFormulario());
        return candidatoCreateDto;
    }

    public static CandidatoDto getCandidatoDto() {
        CandidatoDto candidatoDto = new CandidatoDto();
        candidatoDto.setIdCandidato(1);
        candidatoDto.setNome("Eduardo Sedrez Rodrigues");
        candidatoDto.setDataNascimento(LocalDate.of(2013, 10, 13));
        candidatoDto.setEmail("eduardosedrez@gmail.com");
        candidatoDto.setTelefone("53981258964");
        candidatoDto.setRg("77.777.777-7");
        candidatoDto.setCpf("049.239.620-54");
        candidatoDto.setEstado("Rio Grande do Sul");
        candidatoDto.setCidade("Pelotas");
        candidatoDto.setPcd(TipoMarcacao.F);
        candidatoDto.setFormulario(FormularioFactory.getFormularioDto());
        return candidatoDto;
    }
}

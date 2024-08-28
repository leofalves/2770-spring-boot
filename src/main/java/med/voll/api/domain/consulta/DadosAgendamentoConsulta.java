package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @JsonAlias({"medico_id", "id_medico"}) Long idMedico,

        @NotNull
        @JsonAlias({"paciente_id", "id_paciente"}) Long idPaciente,

        @NotNull
        @Future
        /*
        É possível usar a annotation JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        para definir o formato da data+hora passada no body da requisição
        */
        LocalDateTime data,
        Especialidade especialidade) {
}

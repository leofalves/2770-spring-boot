package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Não substitui o banco pelo banco em memória
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("Deveria retornar null quando medico cadastrado nao esta disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        // Given or Arrange
        LocalDateTime proximaSegundaFeiraAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = CadastrarMedico("Nome do Medico", "medico@medicina.com", "123456", Especialidade.CARDIOLOGIA);
        var paciente = CadastrarPaciente("Nome do Paciente", "paciente@paciencia.com", "11255566689");
        cadastrarConsulta(medico, paciente, proximaSegundaFeiraAs10);

        // When or Act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaFeiraAs10);

        // Then or Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria retornar medico quando medico estivar disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        // Given or Arrange
        LocalDateTime proximaSegundaFeiraAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = CadastrarMedico("Nome do Medico", "medico@medicina.com", "123456", Especialidade.CARDIOLOGIA);

        // When or Act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaFeiraAs10);

        // Then or Assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    // Metodos privados usados nos cenários de teste
    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico CadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosCadastroMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente CadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosCadastroPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "999000999",
                crm,
                especialidade,
                dadosEndereco()
                );
    }

    private DadosCadastroPaciente dadosCadastroPaciente(String nome, String email, String cpf){
        return new DadosCadastroPaciente(
                nome,
                email,
                "9998880000",
                cpf,
                dadosEndereco());
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua Teste",
                "Jardim Teste",
                "00000000",
                "Sao Paulo",
                "SP",
                null,
                "998"
                );
    }
}
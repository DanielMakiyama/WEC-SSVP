package com.daniel.wec_ssvp.adapter.in.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste ponta a ponta: HTTP -> controller -> caso de uso -> JPA -> H2 em memória.
 * Garante que a API continua respondendo como antes da arquitetura hexagonal.
 */
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:apitest;DB_CLOSE_DELAY=-1",
        "app.admin.email=admin@teste.com",
        "app.admin.password=admin123"
})
@AutoConfigureMockMvc
class ApiFluxoIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private String loginAdmin() throws Exception {
        String body = mvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\":\"admin@teste.com\",\"senha\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + JsonPath.read(body, "$.token");
    }

    private String criarConselho(String token, String nome) throws Exception {
        String body = mvc.perform(post("/conselhos/criar")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"nome\":\"" + nome + "\",\"cidade\":\"Mogi das Cruzes\",\"dataFundacao\":\"1950-01-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoReuniao").value("CONSELHO_PARTICULAR"))
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.id");
    }

    private String criarConferencia(String token, String nome, String conselhoId) throws Exception {
        String body = mvc.perform(post("/conferencias/criar")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"nome\":\"" + nome + "\",\"conselhoParticularId\":\"" + conselhoId + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoReuniao").value("CONFERENCIA"))
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.id");
    }

    private static String assistidoJson(String nome, String conferenciaId) {
        return """
                {
                  "dadosPessoais": {
                    "nome": "%s",
                    "endereco": "Rua A, 10",
                    "dataNascimento": "1980-05-20",
                    "estadoCivil": "Casado",
                    "religiao": "Católica",
                    "profissao": "Pedreiro"
                  },
                  "situacaoFamiliar": {
                    "quantidadeTrabalhadores": 1,
                    "rendaFamiliar": 1800.00,
                    "rendaLiquida": 1500.00,
                    "valorAluguel": 600.00,
                    "quantidadeAlfabetizados": 2,
                    "situacaoMoradia": "Alugada"
                  },
                  "situacaoIgrejaSaude": {
                    "problemaSaude": "Diabetes"
                  },
                  "conferenciaId": "%s"
                }
                """.formatted(nome, conferenciaId);
    }

    @Test
    void fluxoCompletoDeConselhoConferenciaEAssistido() throws Exception {
        String token = loginAdmin();
        String sufixo = UUID.randomUUID().toString().substring(0, 8);

        String conselhoId = criarConselho(token, "CP " + sufixo);
        String conferenciaId = criarConferencia(token, "Conf. " + sufixo, conselhoId);

        mvc.perform(get("/conferencias/buscar").param("conselhoId", conselhoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Conf. " + sufixo));

        String body = mvc.perform(post("/assistidos/criar")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content(assistidoJson("Maria " + sufixo, conferenciaId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoUsuario").value("BENEFICIARIO"))
                .andReturn().getResponse().getContentAsString();
        String assistidoId = JsonPath.read(body, "$.id");

        mvc.perform(get("/assistidos/buscar").param("id", assistidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria " + sufixo))
                .andExpect(jsonPath("$.problemaSaude").value("Diabetes"))
                .andExpect(jsonPath("$.conferencia").value("Conf. " + sufixo))
                .andExpect(jsonPath("$.status").value(true));

        mvc.perform(put("/assistidos/atualizar/" + assistidoId)
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content(assistidoJson("Maria da Silva " + sufixo, conferenciaId)))
                .andExpect(status().isOk());

        mvc.perform(get("/assistidos/buscar").param("id", assistidoId))
                .andExpect(jsonPath("$.nome").value("Maria da Silva " + sufixo));

        mvc.perform(delete("/assistidos/deletar/" + assistidoId)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void conselhoComNomeRepetidoRetorna409() throws Exception {
        String token = loginAdmin();
        String nome = "CP " + UUID.randomUUID();
        criarConselho(token, nome);

        mvc.perform(post("/conselhos/criar")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"nome\":\"" + nome + "\",\"cidade\":\"Mogi\",\"dataFundacao\":\"1950-01-01\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Já existe um Conselho com esse nome"));
    }

    @Test
    void loginComSenhaErradaRetorna401() throws Exception {
        mvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\":\"admin@teste.com\",\"senha\":\"errada\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void criarSemTokenRetorna401() throws Exception {
        mvc.perform(post("/conselhos/criar")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nome\":\"X\",\"cidade\":\"Mogi\",\"dataFundacao\":\"1950-01-01\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void buscarAssistidoInexistenteRetorna404() throws Exception {
        mvc.perform(get("/assistidos/buscar").param("id", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Assistido não encontrado"));
    }
}

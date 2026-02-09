package it.uniroma3.siw;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
// Questi servono per post(...) e status()
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.repository.RicettaRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class RecensioneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testInitBinderBloccaId() throws Exception {
        // 1. Prendiamo una ricetta esistente dal DB (almeno una deve esserci!)
        Long ricettaId = ricettaRepository.findAll().iterator().next().getId();

        // 2. L'ATTACCO: Inviamo una POST forzando l'ID a 999
        mockMvc.perform(post("/ricetta/" + ricettaId + "/recensione")
                .param("titolo", "Titolo Test Sicurezza")
                .param("testo", "Funziona?")
                .param("voto", "5")
                .param("id", "999")) // Campo che @InitBinder deve bloccare
                .andExpect(status().is3xxRedirection());

        // 3. VERIFICA: Cerchiamo la recensione appena creata
        Iterable<Recensione> recensioni = recensioneRepository.findAll();
        for (Recensione r : recensioni) {
            if ("Titolo Test Sicurezza".equals(r.getTitolo())) {
                // Se l'InitBinder funziona, l'ID NON sarà 999
                assertNotEquals(999L, r.getId());
                System.out.println("SISTEMA SICURO! L'ID forzato 999 è stato ignorato. ID reale assegnato: " + r.getId());
            }
        }
    }
}
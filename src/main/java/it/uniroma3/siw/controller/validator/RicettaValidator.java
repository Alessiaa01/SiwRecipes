package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;

@Component
public class RicettaValidator implements Validator {

    @Autowired
    private RicettaService ricettaService;

    @Override
    public void validate(Object o, Errors errors) {
        Ricetta ricetta = (Ricetta) o; //cast dell'oggetto generico a ricetta
        
        // Controllo se il titolo non è nullo e se esiste già nel database
        if (ricetta.getTitolo() != null) {
            // Puliamo il titolo che arriva dal form
            String titoloPulito = ricetta.getTitolo().trim();

            // Chiediamo al database un controllo che ignori maiuscole/minuscole
            if (ricettaService.existsByTitolo(titoloPulito)) {
                errors.rejectValue("titolo", "ricetta.duplicate", "Esiste già una ricetta con questo titolo (anche se scritto diversamente)");
            }
        }
    }
    @Override
    public boolean supports(Class<?> aClass) {
        // Indica che questo validatore lavora solo su oggetti di classe Ricetta
        return Ricetta.class.equals(aClass);
    }
}

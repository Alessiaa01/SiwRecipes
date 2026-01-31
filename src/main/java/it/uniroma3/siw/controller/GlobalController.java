package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;

@ControllerAdvice
public class GlobalController {

	@Autowired 
	private CredentialsService credentialsService;
	
	@ModelAttribute("currentUser")
    public Utente getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se non sei loggato, restituisci null
        if (authentication == null || authentication.getPrincipal() instanceof String) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        Credentials credentials = null;

        // 1. LOGIN CLASSICO (Username/Password)
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            credentials = credentialsService.getCredentials(username);
        }
        
        // 2. LOGIN GOOGLE (OAuth2)
        else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            String email = oauth2User.getAttribute("email");
            
            // Nel nostro sistema, per gli utenti Google, username = email
            if (email != null) {
                credentials = credentialsService.getCredentials(email);
            }
        }

        // Se abbiamo trovato le credenziali, restituiamo l'Utente (Dati anagrafici)
        if (credentials != null) {
            return credentials.getUtente();
        }

        return null;
    }

}

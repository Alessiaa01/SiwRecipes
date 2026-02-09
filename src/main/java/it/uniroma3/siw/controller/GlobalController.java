package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;

@ControllerAdvice //rende il controller disponibile per tutti i template
public class GlobalController {

	@Autowired //non devi creare tu l'oggetto con new, ma lo inietta Spring
	private CredentialsService credentialsService;
	
	@Autowired 
	private UtenteService utenteService;

	
	
	@ModelAttribute("currentUser")
    public Utente getCurrentUser() { //identifica chi sta navigando nel sito 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 1.UTENTE ANONIMO
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ) { 
            return null;
        }
        
        // 2.LOGIN CLASSICO (Username/Password)
        if (authentication.getPrincipal() instanceof UserDetails) {
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credentials credentials= credentialsService.getCredentials(userDetails.getUsername());
            if(credentials != null) {
            	return credentials.getUtente();
            }
        }
        
        //  CASO LOGIN GOOGLE (OAuth2)
         if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User principal = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String email = principal.getAttribute("email");
            
            Credentials credentials = credentialsService.getCredentials(email);
            
         
            if (credentials != null) {
            return credentials.getUtente();
          } else {
            	//utente nuovo, lo registriamo
            	Utente newUtente= new Utente();
            	
            	//con i dati che google mette a disposizione
            	newUtente.setNome(principal.getAttribute("given_name"));
            	newUtente.setCognome(principal.getAttribute("family_name"));
            	newUtente.setEmail(email);
            	
            	this.utenteService.saveUtente(newUtente);
            	
            	//creiamo le credenziali collegate
            	Credentials newCredentials= new Credentials();
            	newCredentials.setUsername(email);
            	newCredentials.setPassword("[OAUTH_USER]");//psw fittizia
            	newCredentials.setRuolo(Credentials.DEFAULT_ROLE);
            	newCredentials.setUtente(newUtente);
            	
            	this.credentialsService.saveCredentials(newCredentials);
            	
            	newUtente.setCredentials(newCredentials);
            	
            	return newUtente;
            
         }
         }
         return null;
      
	}

	}
	




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

@ControllerAdvice
public class GlobalController {

	@Autowired 
	private CredentialsService credentialsService;

	
	
	@ModelAttribute("currentUser")
    public Utente getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se non sei loggato, restituisci null
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ) { //recupero autenticazione
            return null;
        }
        
        // LOGIN CLASSICO (Username/Password)
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
          }
        }    
         return null;
	}  
	
}
            /*
            else {
            	//utente nuovo, lo registriamo
            	Utente newUtente= new Utente();
            	newUtente.setNome(principal.getAttribute("given_family"));
            	newUtente.setCognome(principal.getAttribute("family_name"));
            	newUtente.setEmail(email);
            	this.utenteService.saveUtente(newUtente);
            	
            	Credentials newCredentials= new Credentials();
            	newCredentials.setUsername(email);
            	
            	//psw fittizzia
            	newCredentials.setPassword("[OAUTH_USER]");
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
	
*/



package it.uniroma3.siw.authentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.service.CredentialsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Questa classe fa da ponte tra l'identità fornita da Google e il database locale
@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler { //ricorda dove l'utente stava cercando di andare prima del login

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        // 1. Recuperiamo i dati che Google ci ha inviato
    	//Viene fatto il casting di authentication in OAuth2AuthenticationToken, perchè sono questo tipo specifico di tokern contiene 
    	// i dettagli tecnici relativi al protocolo OAuth2 usato da Google
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        //Estrae il Pricipal, l'oggetto che rappresenta l'utente autenticato. 
        //In spring security OAuth2, l'utente è rappresentato come un OAuth2User, una mappa di attributi chiave-valore forniti dal provider Google
        OAuth2User oauthUser = token.getPrincipal();
        
        String email = oauthUser.getAttribute("email");
        String nome = oauthUser.getAttribute("given_name");
        String cognome = oauthUser.getAttribute("family_name");
        
        // 2. Passiamo i dati al Service per registrare l'utente se non esiste
            credentialsService.loginOrRegisterGoogleUser(email, nome, cognome); //i dati ricevuti vengono passati al CredentialsService
           //se l'utente non è mai entrato nel sistema, il service crea un nuovo profilo Utente e le relative Credentials nel db
             
           
            this.setDefaultTargetUrl("/success"); 
            this.setAlwaysUseDefaultTargetUrl(false);//se tenta di accedere ad una pagina protetta tipi (/admin/...) verrà riportato sempre a /success
        
        // Chiama il metodo della classe padre per finalizzare il redirect e chiudere il cprrettamente il ciclo di autenticazione
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
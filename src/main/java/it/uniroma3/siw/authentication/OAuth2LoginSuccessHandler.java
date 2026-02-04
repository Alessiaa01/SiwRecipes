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

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        // 1. Recuperiamo i dati che Google ci ha inviato
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = token.getPrincipal();
        
        String email = oauthUser.getAttribute("email");
        String nome = oauthUser.getAttribute("given_name");
        String cognome = oauthUser.getAttribute("family_name");
        
        // 2. Passiamo i dati al Service per registrare l'utente se non esiste
        
            credentialsService.loginOrRegisterGoogleUser(email, nome, cognome);
       

        // 3. Reindirizzamento intelligente
        // Se l'utente voleva andare su una pagina specifica, ci torna.
        // Altrimenti, va alla pagina predefinita (/index).
        this.setAlwaysUseDefaultTargetUrl(false);
        this.setDefaultTargetUrl("/");
        
        // Chiama il metodo della classe padre per gestire il redirect finale
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
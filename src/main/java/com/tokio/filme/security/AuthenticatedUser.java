package com.tokio.filme.security;

import com.tokio.filme.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*Esta classe retorna o utilizador autenticado na sessao atual
* */

@Component
public class AuthenticatedUser {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // fiz dessa forma para caso de for anónimo eu receber null
        if (!(principal instanceof CustomUserDetails customUserDetails)) {
            return null;
        }

        return customUserDetails.getUser();
    }
}

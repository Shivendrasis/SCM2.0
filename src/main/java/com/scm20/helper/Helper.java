package com.scm20.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    public static String getEmailOfLoggedlnUser(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        // If logged in with OAuth2
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from Google");
                DefaultOAuth2User oAuthUser = (DefaultOAuth2User) principal;
                return oAuthUser.getAttribute("email"); // Return email from OAuth2 user
            }
        }

        // If logged in with local database
        System.out.println("Getting data from local database");
        return authentication.getName(); // Returns the username or email
    }
}

package com.maoyou.cas.authentication.credential;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;

/**
 *
 */
public class CustomCredential extends UsernamePasswordCredential {

    @Size(min = 1, message = "require email")
    private String email;


    @Size(min = 1, message = "require telephone")
    private String telephone;

    @Size(min = 6, max = 6, message = "required.capcha")
    private String capcha;


    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getCapcha() {
        return capcha;
    }

    public void setCapcha(String capcha) {
        this.capcha = capcha;
    }
}

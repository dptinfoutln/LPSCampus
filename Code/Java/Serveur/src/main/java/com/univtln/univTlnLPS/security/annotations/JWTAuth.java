package com.univtln.univTlnLPS.security.annotations;

import com.univtln.univTlnLPS.security.filter.request.JsonWebTokenFilter;

import jakarta.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A annotation for method to be secured with Java Web Token (JWT)
 *
 * @see JsonWebTokenFilter
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface JWTAuth {
}
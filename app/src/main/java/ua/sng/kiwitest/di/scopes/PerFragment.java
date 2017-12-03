package ua.sng.kiwitest.di.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Oleksandr on 12.10.2017.
 */

@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}

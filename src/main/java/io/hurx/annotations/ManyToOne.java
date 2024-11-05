package io.hurx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.hurx.models.repository.Repository;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
/**
 * Marks a field as many to one, this serializes an array of the uuid/fileName's.
 * When deserializing, the uuid is used to construct the sub type of Repository<?>,
 * which checks for a constructor with a Repository<?> parameter as parameter 0, together
 * with or not together with a String fileName parameters as parameter 1. It will use
 * the Injects class to inject the correct repository, and it will preferrably use the
 * constructor with a String as parameter 1, in which the fileName will be filled in.
 */
public @interface ManyToOne {
    /**
     * Register the type of repository marked as ManyToOne
     */
    Class<? extends Repository<?>> type();
}

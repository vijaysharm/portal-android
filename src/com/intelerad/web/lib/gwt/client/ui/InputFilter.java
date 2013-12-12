package com.intelerad.web.lib.gwt.client.ui;

/**
 * An interface that defines an operation to match an object to a String input.
 */
public interface InputFilter<T>
{
    boolean matches( T item, String input );
}

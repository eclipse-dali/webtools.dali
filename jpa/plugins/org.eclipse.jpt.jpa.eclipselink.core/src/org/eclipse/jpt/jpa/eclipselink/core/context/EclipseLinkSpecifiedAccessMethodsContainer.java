/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

/**
 * Common interface that can be used by clients interested only in a type
 * or attribute's access setting (e.g. a UI composite).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2 
 */
public interface EclipseLinkSpecifiedAccessMethodsContainer
	extends EclipseLinkAccessMethodsContainer
{
	/**
	 * Return the specified get method;
	 */
	String getSpecifiedGetMethod();

	/**
	 * Set the specified access type.
	 */
	void setSpecifiedGetMethod(String newSpecifiedGetMethod);

	/**
	 * String constant associated with changes to the specified get method
	 */
	String SPECIFIED_GET_METHOD_PROPERTY = "specifiedGetMethod"; //$NON-NLS-1$

	/**
	 * Return the default get method
	 */
	String getDefaultGetMethod();

	/**
	 * String constant associated with changes to the default get method
	 */
	String DEFAULT_GET_METHOD_PROPERTY = "defaultGetMethod"; //$NON-NLS-1$

	/**
	 * String constant for the default get method
	 */
	String DEFAULT_GET_METHOD = "get"; //$NON-NLS-1$

	/**
	 * Return the specified set method;
	 */
	String getSpecifiedSetMethod();

	/**
	 * Set the specified access type.
	 */
	void setSpecifiedSetMethod(String newSpecifiedSetMethod);

	/**
	 * String constant associated with changes to the specified get method
	 */
	String SPECIFIED_SET_METHOD_PROPERTY = "specifiedSetMethod"; //$NON-NLS-1$

	/**
	 * Return the default set method, never null
	 */
	String getDefaultSetMethod();

	/**
	 * String constant associated with changes to the default set method
	 */
	String DEFAULT_SET_METHOD_PROPERTY = "defaultSetMethod"; //$NON-NLS-1$

	/**
	 * String constant for the default set method
	 */
	String DEFAULT_SET_METHOD = "set"; //$NON-NLS-1$

}

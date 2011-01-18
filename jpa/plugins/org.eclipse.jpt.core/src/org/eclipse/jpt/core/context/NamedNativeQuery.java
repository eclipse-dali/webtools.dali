/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * named native query
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface NamedNativeQuery
	extends Query
{
	// ********** result class **********

	String getResultClass();
	void setResultClass(String value);
		String RESULT_CLASS_PROPERTY = "resultClass"; //$NON-NLS-1$

	/**
	 * Return the character to be used for browsing or
	 * creating the result class {@link org.eclipse.jdt.core.IType IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getResultClassEnclosingTypeSeparator();


	// ********** result set mapping **********

	String getResultSetMapping();
	void setResultSetMapping(String value);
		String RESULT_SET_MAPPING_PROPERTY = "resultSetMapping"; //$NON-NLS-1$
}

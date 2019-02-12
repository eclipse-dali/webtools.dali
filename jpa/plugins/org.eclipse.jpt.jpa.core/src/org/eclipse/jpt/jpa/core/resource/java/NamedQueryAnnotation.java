/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.NamedQuery</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface NamedQueryAnnotation
	extends QueryAnnotation
{
	String ANNOTATION_NAME = JPA.NAMED_QUERY;

	// ********** query **********

	/**
	 * Corresponds to the 'query' element of the *Query annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getQuery();
		String QUERY_PROPERTY = "query"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'query' element of the *Query annotation.
	 * Set to null to remove the element.
	 */
	void setQuery(String query);

	/**
	 * Return the {@link TextRange} for the 'query' element. If element
	 * does not exist return the {@link TextRange} for the *Query annotation.
	 */
	List<TextRange> getQueryTextRanges();

}

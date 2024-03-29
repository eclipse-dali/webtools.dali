/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol among the following relationship mappings:<code><ul>
 * <li>javax.persistence.ManyToOne
 * <li>javax.persistence.OneToOne
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface SingleRelationshipMappingAnnotation
	extends RelationshipMappingAnnotation
{
	/**
	 * Corresponds to the optional element of a single relationship annotation.
	 * Returns null if the optional element does not exist in java.
	 */
	Boolean getOptional();

	/**
	 * Corresponds to the optional element of a single relationship annotation.
	 * Set to null to remove the optional element.
	 */
	void setOptional(Boolean optional);
		String OPTIONAL_PROPERTY = "optional"; //$NON-NLS-1$

	/**
	 * Return the {@link TextRange} for the optional element.  If the optional element 
	 * does not exist return the {@link TextRange} for the ManyToOne annotation.
	 */
	TextRange getOptionalTextRange();
}

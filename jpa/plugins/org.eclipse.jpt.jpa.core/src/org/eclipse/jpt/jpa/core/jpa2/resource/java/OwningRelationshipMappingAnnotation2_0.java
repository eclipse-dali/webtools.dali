/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Represents a relationship mapping annotation that may have the
 * <code>orphanRemoval</code> element:<code><ul>
 * <li>javax.persistence.OneToMany
 * <li>javax.persistence.OneToOne
 * </ul></code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OwningRelationshipMappingAnnotation2_0
	extends Annotation
{
	// ********** orphan removal **********
	/**
	 * Corresponds to the orphanRemoval element of a OneToMany or OneToOne annotation.
	 * Returns null if the orphanRemoval element does not exist in java.
	 */
	Boolean getOrphanRemoval();
		String ORPHAN_REMOVAL_PROPERTY = "orphanRemoval"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the orphanRemoval element of a OneToMany or OneToOne annotation.
	 * Set to null to remove the orphanRemoval element.
	 */
	void setOrphanRemoval(Boolean orphanRemoval);

	/**
	 * Return the {@link TextRange} for the orphanRemoval element.  If the orphanRemoval element 
	 * does not exist return the {@link TextRange} for the OneToMany or OneToOne annotation.
	 */
	TextRange getOrphanRemovalTextRange();
}

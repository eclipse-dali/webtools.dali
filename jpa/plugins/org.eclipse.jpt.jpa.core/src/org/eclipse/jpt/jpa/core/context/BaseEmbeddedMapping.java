/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Behavior common to embedded and embedded ID mappings.
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
public interface BaseEmbeddedMapping
		extends AttributeMapping {
	
	AttributeOverrideContainer getAttributeOverrideContainer();
	
	
	// ***** target embeddable *****
	
	/**
	 * Return the fully qualified name of the target embeddable.
	 * This may exist even if {@link #getTargetEmbeddable()} return null.
	 */
	String getTargetEmbeddableName();
	
	/**
	 * String associated with property change events for the target embeddable
	 */
	String TARGET_EMBEDDABLE_PROPERTY = "targetEmbeddable";  //$NON-NLS-1$
	
	/**
	 * Return the embeddable the embedded mapping references.
	 * Return <code>null</code> if there is none.
	 */
	Embeddable getTargetEmbeddable();
}

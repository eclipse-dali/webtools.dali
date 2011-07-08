/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;

/**
 * Maps ID derived identity strategy
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
public interface MapsIdDerivedIdentityStrategy2_0
	extends DerivedIdentityStrategy2_0
{
	/**
	 * Return the specified ID attribute name or, in its absence, the default
	 * ID attribute name. This the name of the ID attribute the relationship
	 * mapping also maps (e.g. a Child's parent mapping also maps part of
	 * the Child's primary key, the parent ID).
	 */
	String getIdAttributeName();
	
	/**
	 * String associated with changes to the specified ID attribute name property.
	 */
	String SPECIFIED_ID_ATTRIBUTE_NAME_PROPERTY = "specifiedIdAttributeName"; //$NON-NLS-1$
	
	/**
	 * Return the specified ID attribute name.
	 * Return <code>null</code> if no name is specified.
	 */
	String getSpecifiedIdAttributeName();
	
	/**
	 * Set the specified attribute name.
	 */
	void setSpecifiedIdAttributeName(String idAttributeName);
	
	/**
	 * Return whether a default value is ever used.
	 * (In some cases, there can be no default.)
	 */
	boolean defaultIdAttributeNameIsPossible();
	
	/**
	 * String associated with changes to the default ID attribute name property.
	 */
	String DEFAULT_ID_ATTRIBUTE_NAME_PROPERTY = "defaultIdAttributeName"; //$NON-NLS-1$
	
	/**
	 * Return the default ID attribute name.
	 */
	String getDefaultIdAttributeName();
	
	/**
	 * Return a sorted list of candidate ID attribute names.
	 */
	Iterable<String> getSortedCandidateIdAttributeNames();
	
	/**
	 * Return attribute mapping referenced by the ID attribute name,
	 * which may be a mapping on the entity
	 * or a mapping within the embeddable mapping referenced by the entity's
	 * embedded ID mapping.
	 */
	AttributeMapping getDerivedIdAttributeMapping();
}

/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;

/**
 * Common protocol among the following JPA 2.0 relationship mappings:<ul>
 * <li><code>javax.persistence.ManyToOne</code>
 * <li><code>javax.persistence.ManyToMany</code>
 * <li><code>javax.persistence.OneToMany</code>
 * <li><code>javax.persistence.OneToOne</code>
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface RelationshipMapping2_0Annotation
	extends RelationshipMappingAnnotation
{
	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	boolean isCascadeDetach();	
		String CASCADE_DETACH_PROPERTY = "cascadeDetach"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'cascade' element of the relationship annotations.
	 */
	void setCascadeDetach(boolean detach);
}

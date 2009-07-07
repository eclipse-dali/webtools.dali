/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.java;

import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;

/**
 * Corresponds to the JPA 2.0 annotation
 * javax.persistence.AssociationOverride
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AssociationOverride2_0Annotation
	extends AssociationOverrideAnnotation
{
	
	/**
	 * Corresponds to the 'joinTable' element of the AssociationOverride annotation.
	 * Return null if the element does not exist in Java.
	 */
	JoinTableAnnotation getJoinTable();
		String JOIN_TABLE_PROPERTY = "joinTable"; //$NON-NLS-1$

	JoinTableAnnotation getNonNullJoinTable();

	/**
	 * Add the 'joinTable' element to the AssociationOverride annotation.
	 */
	JoinTableAnnotation addJoinTable();
	
	JoinColumnAnnotation addJoinTable(AnnotationInitializer initializer);

	/**
	 * Remove the 'joinTable' element from the AssociationOverride annotation.
	 */
	void removeJoinTable();

}

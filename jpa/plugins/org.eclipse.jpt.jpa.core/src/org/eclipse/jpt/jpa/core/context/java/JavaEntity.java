/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;

/**
 * Java entity
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
public interface JavaEntity
	extends Entity, JavaTypeMapping
{
	EntityAnnotation getMappingAnnotation();

	JavaTable getTable();
	
	JavaIdClassReference getIdClassReference();
	
	JavaDiscriminatorColumn getDiscriminatorColumn();
	

	// ********** secondary tables **********

	ListIterable<JavaSecondaryTable> getSecondaryTables();	
	ListIterable<JavaSecondaryTable> getSpecifiedSecondaryTables();
	JavaSecondaryTable addSpecifiedSecondaryTable();
	JavaSecondaryTable addSpecifiedSecondaryTable(int index);
	

	// ********** primary key join columns **********

	ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
	ListIterable<JavaPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
	JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN_PROPERTY = "defaultPrimaryKeyJoinColumn"; //$NON-NLS-1$


	// ********** containers **********

	JavaAttributeOverrideContainer getAttributeOverrideContainer();
	JavaAssociationOverrideContainer getAssociationOverrideContainer();
	JavaQueryContainer getQueryContainer();
	JavaGeneratorContainer getGeneratorContainer();
}

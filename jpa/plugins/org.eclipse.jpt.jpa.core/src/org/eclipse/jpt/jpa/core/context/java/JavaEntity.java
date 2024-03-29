/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
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
	extends Entity, JavaIdTypeMapping
{
	EntityAnnotation getMappingAnnotation();

	JavaSpecifiedTable getTable();
	
	JavaSpecifiedDiscriminatorColumn getDiscriminatorColumn();
	

	// ********** secondary tables **********

	ListIterable<JavaSpecifiedSecondaryTable> getSecondaryTables();	
	ListIterable<JavaSpecifiedSecondaryTable> getSpecifiedSecondaryTables();
	JavaSpecifiedSecondaryTable addSpecifiedSecondaryTable();
	JavaSpecifiedSecondaryTable addSpecifiedSecondaryTable(int index);
	

	// ********** primary key join columns **********

	ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
	ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
	JavaSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	JavaSpecifiedPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN_PROPERTY = "defaultPrimaryKeyJoinColumn"; //$NON-NLS-1$


	// ********** containers **********

	JavaAttributeOverrideContainer getAttributeOverrideContainer();
	JavaAssociationOverrideContainer getAssociationOverrideContainer();
	JavaQueryContainer getQueryContainer();
	JavaGeneratorContainer getGeneratorContainer();
}

/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;

/**
 * 
 * 
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
	extends JavaTypeMapping, Entity
{
	JavaTable getTable();
	
	JavaIdClassReference getIdClassReference();
	
	JavaDiscriminatorColumn getDiscriminatorColumn();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaSecondaryTable> secondaryTables();	
	@SuppressWarnings("unchecked")
	ListIterator<JavaSecondaryTable> specifiedSecondaryTables();
	JavaSecondaryTable addSpecifiedSecondaryTable(int index);
	JavaSecondaryTable addSpecifiedSecondaryTable();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	

	JavaAttributeOverrideContainer getAttributeOverrideContainer();
	
	JavaAssociationOverrideContainer getAssociationOverrideContainer();
	
	JavaQueryContainer getQueryContainer();
	
	JavaGeneratorContainer getGeneratorContainer();
}

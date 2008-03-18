/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.Iterator;
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
 */
public interface JavaEntity extends JavaTypeMapping, Entity, JavaGeneratorHolder
{
	JavaTable getTable();
	
	JavaDiscriminatorColumn getDiscriminatorColumn();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaSecondaryTable> secondaryTables();	
	@SuppressWarnings("unchecked")
	ListIterator<JavaSecondaryTable> specifiedSecondaryTables();
	JavaSecondaryTable addSpecifiedSecondaryTable(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> attributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> specifiedAttributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> virtualAttributeOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> associationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> specifiedAssociationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> virtualAssociationOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedQuery> namedQueries();
	JavaNamedQuery addNamedQuery(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedNativeQuery> namedNativeQueries();
	JavaNamedNativeQuery addNamedNativeQuery(int index);
	
	Iterator<JavaPersistentAttribute> overridableAttributes();
	
	Iterator<JavaPersistentAttribute> overridableAssociations();

}

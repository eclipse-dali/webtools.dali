/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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

public interface JavaEntity extends JavaTypeMapping, Entity
{
	JavaTable getTable();
	
	JavaDiscriminatorColumn getDiscriminatorColumn();

	JavaTableGenerator getTableGenerator();	
	JavaTableGenerator addTableGenerator();
	
	JavaSequenceGenerator getSequenceGenerator();	
	JavaSequenceGenerator addSequenceGenerator();
	
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
	ListIterator<JavaAttributeOverride> defaultAttributeOverrides();
	JavaAttributeOverride addSpecifiedAttributeOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> associationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> specifiedAssociationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> defaultAssociationOverrides();
	JavaAssociationOverride addSpecifiedAssociationOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedQuery> namedQueries();
	JavaNamedQuery addNamedQuery(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedNativeQuery> namedNativeQueries();
	JavaNamedNativeQuery addNamedNativeQuery(int index);

}

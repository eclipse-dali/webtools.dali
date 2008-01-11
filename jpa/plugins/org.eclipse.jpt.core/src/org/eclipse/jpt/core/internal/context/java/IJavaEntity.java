/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;

public interface IJavaEntity extends IJavaTypeMapping, IEntity
{
	IJavaTable getTable();
	
	IJavaDiscriminatorColumn getDiscriminatorColumn();

	IJavaTableGenerator getTableGenerator();	
	IJavaTableGenerator addTableGenerator();
	
	IJavaSequenceGenerator getSequenceGenerator();	
	IJavaSequenceGenerator addSequenceGenerator();
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaSecondaryTable> secondaryTables();	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaSecondaryTable> specifiedSecondaryTables();
	IJavaSecondaryTable addSpecifiedSecondaryTable(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns();	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAttributeOverride> attributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAttributeOverride> defaultAttributeOverrides();
	IJavaAttributeOverride addSpecifiedAttributeOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAssociationOverride> associationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAssociationOverride> specifiedAssociationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaAssociationOverride> defaultAssociationOverrides();
	IJavaAssociationOverride addSpecifiedAssociationOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaNamedQuery> namedQueries();
	IJavaNamedQuery addNamedQuery(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaNamedNativeQuery> namedNativeQueries();
	IJavaNamedNativeQuery addNamedNativeQuery(int index);
}

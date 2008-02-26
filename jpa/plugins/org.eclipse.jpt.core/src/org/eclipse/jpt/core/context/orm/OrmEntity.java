/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

public interface OrmEntity extends Entity, OrmTypeMapping
{
	OrmTable getTable();
	
	OrmDiscriminatorColumn getDiscriminatorColumn();

	OrmTableGenerator getTableGenerator();	
	OrmTableGenerator addTableGenerator();
	
	OrmSequenceGenerator getSequenceGenerator();	
	OrmSequenceGenerator addSequenceGenerator();
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmSecondaryTable> secondaryTables();	
	@SuppressWarnings("unchecked")
	ListIterator<OrmSecondaryTable> specifiedSecondaryTables();
	OrmSecondaryTable addSpecifiedSecondaryTable(int index);

	ListIterator<OrmSecondaryTable> virtualSecondaryTables();
	boolean containsVirtualSecondaryTable(OrmSecondaryTable secondaryTable);
	//TODO this might need to move to IEntity, for the UI
	String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTablesList";
	
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns();
	@SuppressWarnings("unchecked")
	ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> attributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> specifiedAttributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> defaultAttributeOverrides();
	OrmAttributeOverride addSpecifiedAttributeOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> associationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> specifiedAssociationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> defaultAssociationOverrides();
	OrmAssociationOverride addSpecifiedAssociationOverride(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedQuery> namedQueries();
	OrmNamedQuery addNamedQuery(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedNativeQuery> namedNativeQueries();
	OrmNamedNativeQuery addNamedNativeQuery(int index);
	
	JavaEntity javaEntity();

	void initialize(XmlEntity entity);

	void update(XmlEntity entity);
}
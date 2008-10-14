/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmEntity 
	extends Entity, OrmTypeMapping, OrmGeneratorHolder
{
	/**
	 * Return a list iterator of the virtual(not specified) secondary tables.
	 * This will not be null.
	 */
	ListIterator<OrmSecondaryTable> virtualSecondaryTables();
		String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTablesList";
	
	/**
	 * Return the number of virtual secondary tables.
	 */
	int virtualSecondaryTablesSize();
	
	/**
	 * Return whether the entity contains the given secondary table in its list of
	 * virtual secondary tables
	 */	
	boolean containsVirtualSecondaryTable(OrmSecondaryTable secondaryTable);
	
	/**
	 * Return true if there are no virtual secondary tables on the orm entity.
	 * This is used to determine whether you can add specified secondary tables.
	 * You must first make sure all virtual secondary tables have been specified
	 * in xml before adding more.  This is because adding one secondary table to xml
	 * will override all the secondary tables specified in the java entity
	 */
	boolean secondaryTablesDefinedInXml();
	
	/**
	 * If true, then all virtual secondary tables are added in as specified secondary tables to the xml.
	 * If false, then all the specified secondary tables are remvoed from the xml.
	 */
	void setSecondaryTablesDefinedInXml(boolean defineInXml);
	
	/**
	 * Return the Java Entity this ORM Entity corresponds to.  Return null if there is no
	 * java entity.
	 */
	JavaEntity getJavaEntity();

	void initialize(XmlEntity entity);

	/**
	 * Update the OrmEntity context model object to match the XmlEntity 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlEntity entity);
	
	
	//************ covariant overrides *************
	
	OrmTable getTable();
	
	OrmDiscriminatorColumn getDiscriminatorColumn();
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmSecondaryTable> secondaryTables();	
	@SuppressWarnings("unchecked")
	ListIterator<OrmSecondaryTable> specifiedSecondaryTables();
	OrmSecondaryTable addSpecifiedSecondaryTable(int index);
	OrmSecondaryTable addSpecifiedSecondaryTable();

	
	@SuppressWarnings("unchecked")
	ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST = "defaultPrimaryKeyJoinColumnsList";
	@SuppressWarnings("unchecked")
	ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> attributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> specifiedAttributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> virtualAttributeOverrides();
	OrmAttributeOverride getAttributeOverrideNamed(String name);
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> associationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> specifiedAssociationOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<OrmAssociationOverride> virtualAssociationOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedQuery> namedQueries();
	OrmNamedQuery addNamedQuery(int index);
	
	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedNativeQuery> namedNativeQueries();
	OrmNamedNativeQuery addNamedNativeQuery(int index);
	
	Iterator<OrmPersistentAttribute> overridableAttributes();

	Iterator<OrmPersistentAttribute> overridableAssociations();

}
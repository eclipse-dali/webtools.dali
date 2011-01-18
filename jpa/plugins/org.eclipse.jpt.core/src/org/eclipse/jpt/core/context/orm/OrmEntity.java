/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

/**
 * <code>orm.xml</code> entity
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
public interface OrmEntity 
	extends Entity, OrmTypeMapping
{
	XmlEntity getXmlTypeMapping();

	JavaEntity getJavaTypeMapping();
	
	JavaEntity getJavaTypeMappingForDefaults();

	OrmTable getTable();
	
	OrmIdClassReference getIdClassReference();
	
	OrmDiscriminatorColumn getDiscriminatorColumn();


	// ********** secondary tables **********

	ListIterator<ReadOnlySecondaryTable> secondaryTables();	
	ListIterator<OrmSecondaryTable> specifiedSecondaryTables();
	OrmSecondaryTable addSpecifiedSecondaryTable();
	OrmSecondaryTable addSpecifiedSecondaryTable(int index);

	/**
	 * Return the virtual (not specified) secondary tables.
	 */
	ListIterator<OrmVirtualSecondaryTable> virtualSecondaryTables();
		String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTables"; //$NON-NLS-1$
	
	/**
	 * Return the number of virtual secondary tables.
	 */
	int virtualSecondaryTablesSize();
	
	/**
	 * Return true if there are no virtual secondary tables on the orm entity.
	 * This is used to determine whether you can add specified secondary tables.
	 * You must first make sure all virtual secondary tables have been specified
	 * in xml before adding more.  This is because adding one secondary table to xml
	 * will override all the secondary tables specified in the java entity
	 */
	boolean secondaryTablesAreDefinedInXml();
	
	/**
	 * If true, then all virtual secondary tables are added in as specified secondary tables to the xml.
	 * If false, then all the specified secondary tables are remvoed from the xml.
	 */
	void setSecondaryTablesAreDefinedInXml(boolean defineInXml);


	// ********** primary key join columns **********

	ListIterator<ReadOnlyPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn();
	OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	ListIterator<ReadOnlyPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST = "defaultPrimaryKeyJoinColumns"; //$NON-NLS-1$


	// ********** containers **********

	OrmAttributeOverrideContainer getAttributeOverrideContainer();
	OrmAssociationOverrideContainer getAssociationOverrideContainer();
	OrmQueryContainer getQueryContainer();
	OrmGeneratorContainer getGeneratorContainer();
}

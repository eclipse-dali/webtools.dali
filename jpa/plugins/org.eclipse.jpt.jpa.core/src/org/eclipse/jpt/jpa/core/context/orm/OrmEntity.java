/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;

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
	extends Entity, OrmIdTypeMapping
{
	XmlEntity getXmlTypeMapping();

	JavaEntity getJavaTypeMapping();
	
	JavaEntity getJavaTypeMappingForDefaults();

	OrmSpecifiedTable getTable();
	
	OrmSpecifiedDiscriminatorColumn getDiscriminatorColumn();


	// ********** secondary tables **********

	ListIterable<SecondaryTable> getSecondaryTables();	
	ListIterable<OrmSpecifiedSecondaryTable> getSpecifiedSecondaryTables();
	OrmSpecifiedSecondaryTable addSpecifiedSecondaryTable();
	OrmSpecifiedSecondaryTable addSpecifiedSecondaryTable(int index);

	/**
	 * Return the virtual (not specified) secondary tables.
	 */
	ListIterable<OrmVirtualSecondaryTable> getVirtualSecondaryTables();
		String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTables"; //$NON-NLS-1$
	
	/**
	 * Return the number of virtual secondary tables.
	 */
	int getVirtualSecondaryTablesSize();
	
	/**
	 * Return true if there are no virtual secondary tables on the orm entity.
	 * This is used to determine whether you can add specified secondary tables.
	 * You must first make sure all virtual secondary tables have been specified
	 * in xml before adding more.  This is because adding one secondary table to xml
	 * will override all the secondary tables specified in the java entity
	 */
	boolean secondaryTablesAreDefinedInXml();
	
	/**
	 * If the specified flag is <code>true</code>, then all the default
	 * secondary tables (as determined by the Java secondary tables) are
	 * converted to specified secondary tables in the XML.
	 * If the specified flag is <code>false</code>, then all the secondary
	 * tables specified in the XML are removed.
	 */
	void setSecondaryTablesAreDefinedInXml(boolean defineInXml);


	// ********** primary key join columns **********

	ListIterable<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
	ListIterable<OrmSpecifiedPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
	OrmSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn();
	OrmSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	ListIterable<PrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns();
	int getDefaultPrimaryKeyJoinColumnsSize();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST = "defaultPrimaryKeyJoinColumns"; //$NON-NLS-1$


	// ********** containers **********

	OrmAttributeOverrideContainer getAttributeOverrideContainer();
	OrmAssociationOverrideContainer getAssociationOverrideContainer();
	OrmQueryContainer getQueryContainer();
	OrmGeneratorContainer getGeneratorContainer();
}

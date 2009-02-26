/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jpt.db.Schema;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 */
public interface TypeMapping extends JpaContextNode {
	/**
	 * Return a unique key for the ITypeMapping. If this is defined in an
	 * extension they should be equal.
	 */
	String getKey();

	PersistentType getPersistentType();

	boolean isMapped();

	/**
	 * Return the type mapping's primary table name, null if a primary table
	 * does not apply.
	 */
	String getPrimaryTableName();

	/**
	 * Return the type mapping's primary database table.
	 */
	org.eclipse.jpt.db.Table getPrimaryDbTable();

	Schema getDbSchema();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables.
	 */
	Iterator<Table> associatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables, as well as all inherited
	 * "associated" tables.
	 */
	Iterator<Table> associatedTablesIncludingInherited();

	/**
	 * Return the identifiers of the type mapping's "associated" tables, which
	 * includes the primary table and the collection of secondary tables, as
	 * well as all the inherited "associated" tables.
	 */
	Iterator<String> associatedTableNamesIncludingInherited();

	/**
	 * return the resolved associated db table with the passed in name
	 */
	org.eclipse.jpt.db.Table getDbTable(String tableName);

	/**
	 * Return whether the specified table is invalid for any annotations
	 * associated with the type mapping.
	 */
	boolean tableNameIsInvalid(String tableName);
	
	/**
	 * Return whether database info (columns/tables) should be validated in this context.
	 * For example, mapped superclasses cannot validate column information
	 */
	boolean shouldValidateDbInfo();
	
	/**
	 * Return an Iterator of attributes. The attributes must be BasicMappings or
	 * IdMappings found in this type mapping
	 */
	<T extends PersistentAttribute> Iterator<T> overridableAttributes();

	/**
	 * Return an Iterator of attributes names. The attributes must be
	 * BasicMappings or IdMappings found in this type mapping
	 */
	Iterator<String> overridableAttributeNames();

	/**
	 * Return an Iterator of attributes. The attributes must be BasicMappings or
	 * IdMappings found in any MappedSuperclass in the inheritance hierarchy.
	 * See {@link TypeMapping#overridableAttributes()} and
	 * {@link PersistentType#inheritanceHierarchy()}
	 */
	Iterator<PersistentAttribute> allOverridableAttributes();

	/**
	 * Return an Iterator of attributes names. The attributes must be
	 * BasicMappings or IdMappings found in any MappedSuperclass in the
	 * inheritance hierarchy. See
	 * {@link TypeMapping#overridableAttributeNames()} and
	 * {@link PersistentType#inheritanceHierarchy()}
	 */
	Iterator<String> allOverridableAttributeNames();

	/**
	 * Return an Iterator of associations that can be overriden. The
	 * associations must be OneToOneMappings or ManyToOneMappings found in this
	 * type mapping
	 */
	<T extends PersistentAttribute> Iterator<T> overridableAssociations();

	/**
	 * Return an Iterator of association names. The associations must be
	 * OneToOneMappings or ManyToOneMappings found in any MappedSuperclass in
	 * the inheritance hierarchy
	 */
	Iterator<String> overridableAssociationNames();

	/**
	 * Return an Iterator of associations. The associations must be
	 * OneToOneMappings or ManyToOneMappings found in any MappedSuperclass in
	 * the inheritance hierarchy See
	 * {@link TypeMapping#overridableAssociations()} and
	 * {@link PersistentType#inheritanceHierarchy()}
	 */
	Iterator<PersistentAttribute> allOverridableAssociations();

	/**
	 * Return an Iterator of association names. The associations must be
	 * OneToOneMappings or ManyToOneMappings found in any MappedSuperclass in
	 * the inheritance hierarchy See
	 * {@link TypeMapping#overridableAssociationNames()} and
	 * {@link PersistentType#inheritanceHierarchy()}
	 */
	Iterator<String> allOverridableAssociationNames();

	/**
	 * Return whether the given attribute mapping key is valid for this
	 * particular type mapping (for example, id's are not valid for an
	 * embeddable type mapping)
	 */
	boolean attributeMappingKeyAllowed(String attributeMappingKey);
}

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

import java.util.ListIterator;

/**
 * Entity
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Entity
	extends TypeMapping
{
	// **************** name **************************************
	
	/**
	 * Return the name, specified or default if not specified.
	 */
	String getName();
	
	/**
	 * Return the specified name.
	 */
	String getSpecifiedName();
	
	/**
	 * Set the specified name on the entity.
	 */
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	
	/**
	 * Return the default name, based on the class name.
	 */
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$
	
	
	// **************** id class **********************************************
 	
 	/**
 	 * Return the (aggregate) class reference for configuring and validating id class
 	 */
 	IdClassReference getIdClassReference();
 	
 	
	// **************** table **************************************
	
	/**
	 * Return the table for this entity, either specified or default.
	 * This will not be null.
	 */
	Table getTable();
	
	/**
	 * Return the name of the entity's primary key column.
	 * Return null if the entity's primary key is "compound"
	 * (i.e. the primary key is composed of multiple columns).
	 */
	String getPrimaryKeyColumnName();
	
	/**
	 * Return the entity's default table name, which depends on the entity's
	 * inheritance hierarchy.
	 */
	String getDefaultTableName();
	
	/**
	 * Return the entity's default schema, which depends on the entity's
	 * inheritance hierarchy.
	 */
	String getDefaultSchema();
	
	/**
	 * Return the entity's default catalog, which depends on the entity's
	 * inheritance hierarchy.
	 */
	String getDefaultCatalog();
	
	
	// **************** secondary tables **************************************
	
	/**
	 * Return a list iterator of the secondary tables whether specified or default.
	 * This will not be null.
	 */
	<T extends SecondaryTable> ListIterator<T> secondaryTables();
	
	/**
	 * Return the number of secondary tables, both specified and default.
	 */
	int secondaryTablesSize();
	
	/**
	 * Return a list iterator of the specified secondary tables.
	 * This will not be null.
	 */
	<T extends SecondaryTable> ListIterator<T> specifiedSecondaryTables();
	
	/**
	 * Return the number of specified secondary tables.
	 */
	int specifiedSecondaryTablesSize();
	
	/**
	 * Add a specified secondary table to the entity return the object 
	 * representing it.
	 */
	SecondaryTable addSpecifiedSecondaryTable(int index);
	
	/**
	 * Add a specified secondary table to the entity return the object 
	 * representing it.
	 */
	SecondaryTable addSpecifiedSecondaryTable();
	
	/**
	 * Remove the specified secondary table from the entity.
	 */
	void removeSpecifiedSecondaryTable(int index);
	
	/**
	 * Remove the specified secondary table at the index from the entity.
	 */
	void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable);
	
	/**
	 * Move the specified secondary table from the source index to the target index.
	 */
	void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex);
		String SPECIFIED_SECONDARY_TABLES_LIST = "specifiedSecondaryTables"; //$NON-NLS-1$
	
	
	// **************** inheritance **************************************
	
	InheritanceType getInheritanceStrategy();
	
	InheritanceType getDefaultInheritanceStrategy();
		String DEFAULT_INHERITANCE_STRATEGY_PROPERTY = "defaultInheritanceStrategy"; //$NON-NLS-1$
	
	InheritanceType getSpecifiedInheritanceStrategy();
	void setSpecifiedInheritanceStrategy(InheritanceType newInheritanceType);
		String SPECIFIED_INHERITANCE_STRATEGY_PROPERTY = "specifiedInheritanceStrategy"; //$NON-NLS-1$
	
	/**
	 * The first parent in the class hierarchy that is an entity. 
	 * This is the parent in the entity (persistent) inheritance hierarchy
	 * (vs class inheritance hierarchy).  Return null if there is no parent entity.
	 */
	Entity getParentEntity();
	
	DiscriminatorColumn getDiscriminatorColumn();
	
	String getDiscriminatorValue();
	
	String getDefaultDiscriminatorValue();
		String DEFAULT_DISCRIMINATOR_VALUE_PROPERTY = "defaultDiscriminatorValue"; //$NON-NLS-1$
	
	String getSpecifiedDiscriminatorValue();
	void setSpecifiedDiscriminatorValue(String value);
		String SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY = "specifiedDiscriminatorValue"; //$NON-NLS-1$
	
	/** 
	 * Return whether a DiscriminatorValue is allowed for this Entity.
	 * It is allowed if the entity is not abstract and not part of 
	 * a table-per-class inheritance hierarchy
	 */
	boolean specifiedDiscriminatorValueIsAllowed();
 		String SPECIFIED_DISCRIMINATOR_VALUE_IS_ALLOWED_PROPERTY = "discriminatorValueIsAllowed"; //$NON-NLS-1$
 	
	 /**
	 * Return whether a DiscriminatorValue is undefined for this Entity.
	 * It is undefined if the entity is abstract or if it
	 * is part of a table-per-class inheritance hierarchy
	 */
	boolean discriminatorValueIsUndefined();
 		String DISCRIMINATOR_VALUE_IS_UNDEFINED_PROPERTY = "discriminatorValueIsUndefined"; //$NON-NLS-1$
 	
	/**
	 * Return whether a DiscriminatorColumn is allowed for this Entity.
	 * It is allowed if the entity is the root of the inheritance hierarchy (with descendant entities)
	 * and the strategy is not table-per-class
	 */
	boolean specifiedDiscriminatorColumnIsAllowed();
 		String SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY = "specifiedDiscriminatorColumnIsAllowed"; //$NON-NLS-1$
 	
	/**
	 * Return whether a DiscriminatorColumn is undefined for this Entity.
	 * It is undefined if the inheritance strategy is table-per-class
	 */
 	boolean discriminatorColumnIsUndefined();
		String DISCRIMINATOR_COLUMN_IS_UNDEFINED_PROPERTY = "discriminatorColumnIsUndefined"; //$NON-NLS-1$
	
	/**
	 * Return whether a Table is allowed for this Entity.
	 * If the inheritance strategy is single-table, Table is allowed only  
	 * on the root entity.
	 * If the inheritance strategy is table-per-class it is allowed only
	 * on concrete entities.
	 */
 	boolean specifiedTableIsAllowed();
 		String SPECIFIED_TABLE_IS_ALLOWED_PROPERTY = "specifiedTableIsAllowed"; //$NON-NLS-1$
 		
	/**
	 * Return whether a Table is undefined for this Entity.
	 * If the inheritance strategy is table-per-class and the entity is
	 * abstract, then a Table object is undefined
	 */
 	boolean tableIsUndefined();
 		String TABLE_IS_UNDEFINED_PROPERTY = "tableIsUndefined"; //$NON-NLS-1$
 	
 	
 	// **************** primary key join columns ******************************
 	
	<T extends PrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();
	
	int primaryKeyJoinColumnsSize();
	
	<T extends PrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumns"; //$NON-NLS-1$
	
	int specifiedPrimaryKeyJoinColumnsSize();
	
	PrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN = "defaultPrimaryKeyJoinColumn"; //$NON-NLS-1$
	
	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	void removeSpecifiedPrimaryKeyJoinColumn(int index);
	
	void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn);
	
	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);
	
	
	// ********** misc **********
	
	AttributeOverrideContainer getAttributeOverrideContainer();
	
	AssociationOverrideContainer getAssociationOverrideContainer();
		
	QueryContainer getQueryContainer();
	
	GeneratorContainer getGeneratorContainer();
	
	/**
	 * The given Entity has this entity as its root entity, add
	 * it as a sub entity.
	 * @see org.eclipse.jpt.core.context.persistence.PersistenceUnit#addRootWithSubEntities(String)
	 */
	void addSubEntity(Entity subEntity);
	
	/**
	 * Returns the attribute mapping that matches the name.
	 * In 2.0 this name could use dot-notation for nested mappings.
	 */
	AttributeMapping resolveAttributeMapping(String name);
	
	/**
	 * Return the entity's primary key attribute.
	 * Return null if the entity has multiple primary key attributes.
	 */
	PersistentAttribute getPrimaryKeyAttribute();
}

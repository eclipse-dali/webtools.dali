/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
 * entity
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
public interface Entity
	extends TypeMapping
{
	// ********** name **********

	String getSpecifiedName();
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	void setSpecifiedName(String name);
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$


	// ********** id class **********

 	/**
 	 * Return the (aggregate) class reference for configuring and validating id class
 	 */
 	IdClassReference getIdClassReference();


	// ********** table **********

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


	// ********** secondary tables **********

	/**
	 * Return the secondary tables whether specified or default.
	 */
	ListIterator<? extends ReadOnlySecondaryTable> secondaryTables();

	/**
	 * Return the number of secondary tables, both specified and default.
	 */
	int secondaryTablesSize();

	/**
	 * Return a list iterator of the specified secondary tables.
	 * This will not be null.
	 */
	ListIterator<? extends SecondaryTable> specifiedSecondaryTables();
		String SPECIFIED_SECONDARY_TABLES_LIST = "specifiedSecondaryTables"; //$NON-NLS-1$

	/**
	 * Return the number of specified secondary tables.
	 */
	int specifiedSecondaryTablesSize();

	/**
	 * Add a specified secondary table to the entity return the object
	 * representing it.
	 */
	SecondaryTable addSpecifiedSecondaryTable();

	/**
	 * Add a specified secondary table to the entity return the object
	 * representing it.
	 */
	SecondaryTable addSpecifiedSecondaryTable(int index);

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


	// ********** inheritance strategy **********

	/**
	 * Return the specified inheritance strategy if present, otherwise return the default
	 * inheritance strategy.
	 */
	InheritanceType getInheritanceStrategy();
	InheritanceType getSpecifiedInheritanceStrategy();
	void setSpecifiedInheritanceStrategy(InheritanceType inheritanceType);
		String SPECIFIED_INHERITANCE_STRATEGY_PROPERTY = "specifiedInheritanceStrategy"; //$NON-NLS-1$
	InheritanceType getDefaultInheritanceStrategy();
		String DEFAULT_INHERITANCE_STRATEGY_PROPERTY = "defaultInheritanceStrategy"; //$NON-NLS-1$


	/**
	 * The first parent in the class hierarchy that is an entity.
	 * This is the parent in the entity (persistent) inheritance hierarchy
	 * (vs class inheritance hierarchy).  Return null if there is no parent entity.
	 */
	Entity getParentEntity();

	DiscriminatorColumn getDiscriminatorColumn();

	String getDiscriminatorValue();
	String getSpecifiedDiscriminatorValue();
	void setSpecifiedDiscriminatorValue(String value);
		String SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY = "specifiedDiscriminatorValue"; //$NON-NLS-1$
	String getDefaultDiscriminatorValue();
		String DEFAULT_DISCRIMINATOR_VALUE_PROPERTY = "defaultDiscriminatorValue"; //$NON-NLS-1$

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


 	// ********** primary key join columns **********

	ListIterator<? extends ReadOnlyPrimaryKeyJoinColumn> primaryKeyJoinColumns();

	int primaryKeyJoinColumnsSize();

	ListIterator<? extends PrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumns"; //$NON-NLS-1$

	int specifiedPrimaryKeyJoinColumnsSize();

	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn();

	void removeSpecifiedPrimaryKeyJoinColumn(int index);

	void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn);

	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);


	// ********** containers **********

	AttributeOverrideContainer getAttributeOverrideContainer();

	AssociationOverrideContainer getAssociationOverrideContainer();

	QueryContainer getQueryContainer();

	GeneratorContainer getGeneratorContainer();


	// ********** entity inheritance **********

	/**
	 * Return whether this entity is a root entity in an inheritance hierarchy.
	 */
	boolean isRoot();

	/**
	 * Return the top of the entity's inheritance hierarchy.
	 * This method will never return <code>null</code>. The root
	 * is defined as the persistent type in the inheritance hierarchy
	 * that has no parent. The root will be an entity.
	 * <p>
	 * Non-entities in the hierarchy should be ignored; i.e. we skip
	 * over them in the search for the root.
	 */
	Entity getRootEntity();
		String ROOT_ENTITY_PROPERTY = "rootEntity"; //$NON-NLS-1$

	/**
	 * If the entity is a root entity, return the entity's descendant entities;
	 * if the entity is not a root entity, return an empty collection.
	 * <p>
	 * <strong>NB:</strong> An entity A is a "descendant" of another root
	 * entity B if the <em>name</em> of entity A's root entity's type matches
	 * the name of entity B's type. This means entity A can be a "descendant"
	 * of multiple root entities (typically both the <code>orm.xml</code>
	 * and Java root entities for a particular type); despite entity have only
	 * a single "root" entity (typically the <code>orm.xml</code> root entity).
	 */
	Iterable<Entity> getDescendants();
		String DESCENDANTS_COLLECTION = "descendants"; //$NON-NLS-1$


	// ********** misc **********

	/**
	 * Returns the attribute mapping that matches the name.
	 * In 2.0 this name could use dot-notation for nested mappings.
	 */
	AttributeMapping resolveAttributeMapping(String name);

	/**
	 * If an entity has a single id attribute, return that attribute.
	 * Else return null.
	 */
	PersistentAttribute getIdAttribute();
}

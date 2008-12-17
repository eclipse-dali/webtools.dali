/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Entity
	extends TypeMapping, GeneratorHolder, QueryContainer, IdClassHolder
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
	 * Return the ultimate top of the inheritance hierarchy 
	 * This method should never return null. The root
	 * is defined as the persistent type in the inheritance hierarchy
	 * that has no parent.  The root should be an entity
	 *  
	 * Non-entities in the hierarchy should be ignored, ie skip
	 * over them in the search for the root. 
	 */
	Entity getRootEntity();

	/**
	 * The first parent in the class hierarchy that is an entity. 
	 * This is the parent in the entity (persistent) inheritance hierarchy
	 * (vs class inheritance hierarchy)
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
	 * Return whether a DiscriminatorValue is allowed for this Entity
	 * It is allowed if the IType is concrete (not abstract)
	 */
	boolean isDiscriminatorValueAllowed();
 		String DISCRIMINATOR_VALUE_ALLOWED_PROPERTY = "discriminatorValueAllowed"; //$NON-NLS-1$


	// **************** primary key join columns **************************************

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


	// **************** attribute overrides **************************************

	/**
	 * Return a list iterator of the attribute overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> attributeOverrides();

	/**
	 * Return the number of attribute overrides, both specified and default.
	 */
	int attributeOverridesSize();

	/**
	 * Return a list iterator of the specified attribute overrides.
	 * This will not be null.  No add/remove for specified attribute overrides, the
	 * virtual attribute overrides will be populated from superclasses, then use
	 * {@link AttributeOverride#setVirtual(boolean)} to add/remove the attribute
	 * override from the source
	 */
	<T extends AttributeOverride> ListIterator<T> specifiedAttributeOverrides();

	/**
	 * Return the number of specified attribute overrides.
	 */
	int specifiedAttributeOverridesSize();

	/**
	 * Return a list iterator of the virtual attribute overrides, those not specified.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> virtualAttributeOverrides();

	/**
	 * Return the number of default attribute overrides.
	 */
	int virtualAttributeOverridesSize();

	/**
	 * Move the specified attribute override from the source index to the target index.
	 */
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverrides"; //$NON-NLS-1$
		String VIRTUAL_ATTRIBUTE_OVERRIDES_LIST = "virtualAttributeOverrides"; //$NON-NLS-1$

	/**
	 * Return the attribute overrides, whether specified or default,
	 * with the given name.
	 */
	AttributeOverride getAttributeOverrideNamed(String name);


	// **************** association overrides **************************************

	/**
	 * Return a list iterator of the association overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends AssociationOverride> ListIterator<T> associationOverrides();

	/**
	 * Return the number of association overrides, both specified and default.
	 */
	int associationOverridesSize();

	/**
	 * Return a list iterator of the specified association overrides.
	 * This will not be null.  No add/remove for specified association overrides, the
	 * virtual association overrides will be populated from superclasses, then use
	 * {@link AssociationOverride#setVirtual(boolean)} to add/remove the association
	 * override from the source
	 */
	<T extends AssociationOverride> ListIterator<T> specifiedAssociationOverrides();

	/**
	 * Return the number of specified association overrides.
	 */
	int specifiedAssociationOverridesSize();

	/**
	 * Return the number of default association overrides.
	 */
	<T extends AssociationOverride> ListIterator<T> virtualAssociationOverrides();

	/**
	 * Return the number of default association overrides.
	 */
	int virtualAssociationOverridesSize();

	/**
	 * Move the specified association override from the source index to the target index.
	 */
	void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ASSOCIATION_OVERRIDES_LIST = "specifiedAssociationOverrides"; //$NON-NLS-1$
		String VIRTUAL_ASSOCIATION_OVERRIDES_LIST = "virtualAssociationOverrides"; //$NON-NLS-1$

}

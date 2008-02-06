/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IEntity extends ITypeMapping
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
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";
	
	/**
	 * Return the default name, based on the class name.
	 */
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	
	// **************** table **************************************
	
	/**
	 * Return the table for this entity, either specified or default.
	 * This will not be null.
	 */
	ITable getTable();


	// **************** secondary tables **************************************

	/**
	 * Return a list iterator of the secondary tables whether specified or default.
	 * This will not be null.
	 */
	<T extends ISecondaryTable> ListIterator<T> secondaryTables();
	
	/**
	 * Return the number of secondary tables, both specified and default.
	 */
	int secondaryTablesSize();
	
	/**
	 * Return a list iterator of the specified secondary tables.
	 * This will not be null.
	 */
	<T extends ISecondaryTable> ListIterator<T> specifiedSecondaryTables();
	
	/**
	 * Return the number of specified secondary tables.
	 */
	int specifiedSecondaryTablesSize();
	
	/**
	 * Add a specified secondary table to the entity return the object 
	 * representing it.
	 */
	ISecondaryTable addSpecifiedSecondaryTable(int index);
	
	/**
	 * Remove the specified secondary table from the entity.
	 */
	void removeSpecifiedSecondaryTable(int index);
	
	/**
	 * Remove the specified secondary table at the index from the entity.
	 */
	void removeSpecifiedSecondaryTable(ISecondaryTable secondaryTable);
	
	/**
	 * Move the specified secondary table from the source index to the target index.
	 */
	void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex);
		String SPECIFIED_SECONDARY_TABLES_LIST = "specifiedSecondaryTablesList";
	
		
	// **************** inheritance strategy **************************************
	
	InheritanceType getInheritanceStrategy();
	
	InheritanceType getDefaultInheritanceStrategy();
		String DEFAULT_INHERITANCE_STRATEGY_PROPERTY = "defaultInheritanceStrategyProperty";
		
	InheritanceType getSpecifiedInheritanceStrategy();
	void setSpecifiedInheritanceStrategy(InheritanceType newInheritanceType);
		String SPECIFIED_INHERITANCE_STRATEGY_PROPERTY = "specifiedInheritanceStrategyProperty";
	
		
	// **************** discriminator column **************************************
		
	IDiscriminatorColumn getDiscriminatorColumn();

	
	// **************** discriminator value **************************************
	
	String getDiscriminatorValue();
	
	String getDefaultDiscriminatorValue();
		String DEFAULT_DISCRIMINATOR_VALUE_PROPERTY = "defaultDiscriminatorValueProperty";

	String getSpecifiedDiscriminatorValue();
	void setSpecifiedDiscriminatorValue(String value);
		String SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY = "specifiedDiscriminatorValueProperty";

	/**
	 * Return whether a DiscriminatorValue is allowed for this Entity
	 * It is allowed if the IType is concrete (not abstract)
	 */
	//TODO add tests in java and xml for this
 	boolean isDiscriminatorValueAllowed();
 		String DISCRIMINATOR_VALUE_ALLOWED_PROPERTY = "discriminatorValueAllowedProperty";

 	
 	// **************** table generator **************************************
	
	ITableGenerator getTableGenerator();
	ITableGenerator addTableGenerator();
	void removeTableGenerator();
		String TABLE_GENERATOR_PROPERTY = "tableGeneratorProperty";


	// **************** sequence generator **************************************
		
	ISequenceGenerator getSequenceGenerator();
	ISequenceGenerator addSequenceGenerator();
	void removeSequenceGenerator();
		String SEQUENCE_GENERATOR_PROPERTY = "sequenceGeneratorProperty";

	
	// **************** primary key join columns **************************************

	<T extends IPrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();

	int primaryKeyJoinColumnsSize();

	<T extends IPrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumnsList";

	int specifiedPrimaryKeyJoinColumnsSize();

	IPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN = "defaultPrimaryKeyJoinColumn";

	IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	void removeSpecifiedPrimaryKeyJoinColumn(int index);

	void removeSpecifiedPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn primaryKeyJoinColumn);

	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);

	
	// **************** attribute overrides **************************************

	/**
	 * Return a list iterator of the attribute overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> attributeOverrides();
	
	/**
	 * Return the number of attribute overrides, both specified and default.
	 */
	int attributeOverridesSize();

	/**
	 * Return a list iterator of the specified attribute overrides.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides();
	
	/**
	 * Return the number of specified attribute overrides.
	 */
	int specifiedAttributeOverridesSize();

	/**
	 * Return a list iterator of the default attribute overrides.
	 * This will not be null.
	 */
	<T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides();

	/**
	 * Return the number of default attribute overrides.
	 */
	int defaultAttributeOverridesSize();

	/**
	 * Add a specified attribute override to the entity return the object 
	 * representing it.
	 */
	IAttributeOverride addSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override from the entity.
	 */
	void removeSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override at the index from the entity.
	 */
	void removeSpecifiedAttributeOverride(IAttributeOverride attributeOverride);
	
	/**
	 * Move the specified attribute override from the source index to the target index.
	 */
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";

	// **************** association overrides **************************************
	
	/**
	 * Return a list iterator of the association overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends IAssociationOverride> ListIterator<T> associationOverrides();
	
	/**
	 * Return the number of association overrides, both specified and default.
	 */
	int associationOverridesSize();
	
	/**
	 * Return a list iterator of the specified association overrides.
	 * This will not be null.
	 */
	<T extends IAssociationOverride> ListIterator<T> specifiedAssociationOverrides();
	
	/**
	 * Return the number of specified association overrides.
	 */
	int specifiedAssociationOverridesSize();

	/**
	 * Return the number of default association overrides.
	 */
	<T extends IAssociationOverride> ListIterator<T> defaultAssociationOverrides();

	/**
	 * Return the number of default association overrides.
	 */
	int defaultAssociationOverridesSize();

	/**
	 * Add a specified association override to the entity return the object 
	 * representing it.
	 */
	IAssociationOverride addSpecifiedAssociationOverride(int index);
	
	/**
	 * Remove the specified association override at the index from the entity.
	 */
	void removeSpecifiedAssociationOverride(int index);
	
	/**
	 * Remove the specified association override from the entity.
	 */
	void removeSpecifiedAssociationOverride(IAssociationOverride associationOverride);

	/**
	 * Move the specified association override from the source index to the target index.
	 */
	void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ASSOCIATION_OVERRIDES_LIST = "specifiedAssociationOverridesList";
		String DEFAULT_ASSOCIATION_OVERRIDES_LIST = "defaulAssociationOverridesList";

	
	// **************** named queries **************************************

	/**
	 * Return a list iterator of the named queries.
	 * This will not be null.
	 */
	<T extends INamedQuery> ListIterator<T> namedQueries();
	
	/**
	 * Return the number of named queries.
	 */
	int namedQueriesSize();
	
	/**
	 * Add a named query to the entity return the object representing it.
	 */
	INamedQuery addNamedQuery(int index);
	
	/**
	 * Remove the named query at the index from the entity.
	 */
	void removeNamedQuery(int index);
	
	/**
	 * Remove the named query at from the entity.
	 */
	void removeNamedQuery(INamedQuery namedQuery);
	
	/**
	 * Move the named query from the source index to the target index.
	 */
	void moveNamedQuery(int targetIndex, int sourceIndex);
		String NAMED_QUERIES_LIST = "namedQueriesList";

		
	// **************** named native queries **************************************
	
	/**
	 * Return a list iterator of the specified named native queries.
	 * This will not be null.
	 */
	<T extends INamedNativeQuery> ListIterator<T> namedNativeQueries();
	
	/**
	 * Return the number of named native queries.
	 */
	int namedNativeQueriesSize();
	
	/**
	 * Add a named native query to the entity return the object representing it.
	 */
	INamedNativeQuery addNamedNativeQuery(int index);
	
	/**
	 * Remove the named native query at the index from the entity.
	 */
	void removeNamedNativeQuery(int index);
	
	/**
	 * Remove the named native query at from the entity.
	 */
	void removeNamedNativeQuery(INamedNativeQuery namedNativeQuery);

	/**
	 * Move the named native query from the source index to the target index.
	 */
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);
		String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";


	// **************** id class **************************************

	String getIdClass();
	void setIdClass(String value);
		String ID_CLASS_PROPERTY = "idClassProperty";

	/**
	 * Return the ultimate top of the inheritance hierarchy 
	 * This method should never return null. The root
	 * is defined as the persistent type in the inheritance hierarchy
	 * that has no parent.  The root should be an entity
	 *  
	 * Non-entities in the hierarchy should be ignored, ie skip
	 * over them in the search for the root. 
	 */
	IEntity rootEntity();

	/**
	 * The first parent in the class hierarchy that is an entity. 
	 * This is the parent in the entity (persistent) inheritance hierarchy
	 * (vs class inheritance hierarchy)
	 */
	IEntity parentEntity();

	/**
	 * Return the name of the entity's primary key column.
	 * Return null if the entity's primary key is "compound"
	 * (i.e. the primary key is composed of multiple columns).
	 */
	String primaryKeyColumnName();

//	/**
//	 * Return the name of the entity's primary key attribute.
//	 * Return null if the entity's primary key is "compound"
//	 * (i.e. the primary key is composed of multiple columns).
//	 */
//	String primaryKeyAttributeName();
//
//	IAttributeOverride attributeOverrideNamed(String name);
//
//	boolean containsAttributeOverride(String name);
//
//	boolean containsSpecifiedAttributeOverride(String name);
//
//	boolean containsAssociationOverride(String name);
//
//	boolean containsSpecifiedAssociationOverride(String name);
//
//	boolean containsSecondaryTable(String name);
//
//	boolean containsSpecifiedSecondaryTable(String name);
//
//	boolean containsSpecifiedPrimaryKeyJoinColumns();



//
//
//	class AssociationOverrideOwner extends OverrideOwner
//	{
//		public AssociationOverrideOwner(IEntity entity) {
//			super(entity);
//		}
//
//		public IAttributeMapping attributeMapping(String attributeName) {
//			for (Iterator<IPersistentAttribute> stream = this.entity.getPersistentType().allAttributes(); stream.hasNext();) {
//				IPersistentAttribute persAttribute = stream.next();
//				if (attributeName.equals(persAttribute.getName())) {
//					return persAttribute.getMapping();
//				}
//			}
//			return null;
//		}
//
//		public boolean isVirtual(IOverride override) {
//			return entity.getDefaultAssociationOverrides().contains(override);
//		}
//	}
//
//

}

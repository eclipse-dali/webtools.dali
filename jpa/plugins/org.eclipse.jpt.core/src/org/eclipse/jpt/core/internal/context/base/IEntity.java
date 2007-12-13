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

public interface IEntity extends ITypeMapping, IOverride.Owner
{
	String getName();
	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";
	
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	ITable getTable();

	<T extends ISecondaryTable> ListIterator<T> secondaryTables();
	int secondaryTablesSize();
	
	<T extends ISecondaryTable> ListIterator<T> specifiedSecondaryTables();
	int specifiedSecondaryTablesSize();
	ISecondaryTable addSpecifiedSecondaryTable(int index);
	void removeSpecifiedSecondaryTable(int index);
	void moveSpecifiedSecondaryTable(int oldIndex, int newIndex);
		String SPECIFIED_SECONDARY_TABLES_LIST = "specifiedSecondaryTablesList";
	
	InheritanceType getInheritanceStrategy();
	
	InheritanceType getDefaultInheritanceStrategy();
		String DEFAULT_INHERITANCE_STRATEGY_PROPERTY = "defaultInheritanceStrategyProperty";
		
	InheritanceType getSpecifiedInheritanceStrategy();
	void setSpecifiedInheritanceStrategy(InheritanceType newInheritanceType);
		String SPECIFIED_INHERITANCE_STRATEGY_PROPERTY = "specifiedInheritanceStrategyProperty";
	
	IDiscriminatorColumn getDiscriminatorColumn();

	
	String getDiscriminatorValue();
	
	String getDefaultDiscriminatorValue();
		String DEFAULT_DISCRIMINATOR_VALUE_PROPERTY = "defaultDiscriminatorValueProperty";

	String getSpecifiedDiscriminatorValue();
	void setSpecifiedDiscriminatorValue(String value);
		String SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY = "specifiedDiscriminatorValueProperty";
	
	
	ITableGenerator getTableGenerator();
	ITableGenerator addTableGenerator();
	void removeTableGenerator();
		String TABLE_GENERATOR_PROPERTY = "tableGeneratorProperty";

	ISequenceGenerator getSequenceGenerator();
	ISequenceGenerator addSequenceGenerator();
	void removeSequenceGenerator();
		String SEQUENCE_GENERATOR_PROPERTY = "sequenceGeneratorProperty";

	<T extends IPrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();
	<T extends IPrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns();
	<T extends IPrimaryKeyJoinColumn> ListIterator<T> defaultPrimaryKeyJoinColumns();
	int specifiedPrimaryKeyJoinColumnsSize();
	IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	void removeSpecifiedPrimaryKeyJoinColumn(int index);
	void moveSpecifiedPrimaryKeyJoinColumn(int oldIndex, int newIndex);
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumnsList";
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST = "defaultPrimaryKeyJoinColumnsList";

	<T extends IAttributeOverride> ListIterator<T> attributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides();
	int specifiedAttributeOverridesSize();
	IAttributeOverride addSpecifiedAttributeOverride(int index);
	void removeSpecifiedAttributeOverride(int index);
	void moveSpecifiedAttributeOverride(int oldIndex, int newIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";

	
	<T extends IAssociationOverride> ListIterator<T> associationOverrides();
	<T extends IAssociationOverride> ListIterator<T> specifiedAssociationOverrides();
	<T extends IAssociationOverride> ListIterator<T> defaultAssociationOverrides();
	int specifiedAssociationOverridesSize();
	IAssociationOverride addSpecifiedAssociationOverride(int index);
	void removeSpecifiedAssociationOverride(int index);
	void moveSpecifiedAssociationOverride(int oldIndex, int newIndex);
		String SPECIFIED_ASSOCIATION_OVERRIDES_LIST = "specifiedAssociationOverridesList";
		String DEFAULT_ASSOCIATION_OVERRIDES_LIST = "defaulAssociationOverridesList";


	<T extends INamedQuery> ListIterator<T> namedQueries();
	int namedQueriesSize();
	INamedQuery addNamedQuery(int index);
	void removeNamedQuery(int index);
	void moveNamedQuery(int oldIndex, int newIndex);
		String NAMED_QUERIES_LIST = "namedQueriesList";

	<T extends INamedNativeQuery> ListIterator<T> namedNativeQueries();
	int namedNativeQueriesSize();
	INamedNativeQuery addNamedNativeQuery(int index);
	void removeNamedNativeQuery(int index);
	void moveNamedNativeQuery(int oldIndex, int newIndex);
		String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";


	String getIdClass();
	void setIdClass(String value);
		String ID_CLASS_PROPERTY = "isClassProperty";

//	boolean discriminatorValueIsAllowed();
//
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

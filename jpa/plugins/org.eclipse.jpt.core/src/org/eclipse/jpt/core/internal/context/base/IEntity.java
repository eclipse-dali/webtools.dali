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



public interface IEntity extends ITypeMapping
{
	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";
	
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	ITable getTable();

//	EList<ISecondaryTable> getSpecifiedSecondaryTables();
//
//	EList<ISecondaryTable> getSecondaryTables();

	
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
	

//		
//	ISequenceGenerator getSequenceGenerator();
//
//	void setSequenceGenerator(ISequenceGenerator value);
//
//	ITableGenerator getTableGenerator();
//
//	void setTableGenerator(ITableGenerator value);
//

//	EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
//
//	EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
//
//	EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns();
//
//	EList<IAttributeOverride> getAttributeOverrides();
//
//	EList<IAttributeOverride> getSpecifiedAttributeOverrides();
//
//	EList<IAttributeOverride> getDefaultAttributeOverrides();
//
//	EList<IAssociationOverride> getAssociationOverrides();
//
//	EList<IAssociationOverride> getSpecifiedAssociationOverrides();
//
//	EList<IAssociationOverride> getDefaultAssociationOverrides();
//
//	EList<INamedQuery> getNamedQueries();
//
//	EList<INamedNativeQuery> getNamedNativeQueries();
//
//	String getIdClass();
//	
//	void setIdClass(String value);
//
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
//
//	/**
//	 * <!-- begin-user-doc -->
//	 * The first parent in the class hierarchy that is an entity. 
//	 * This is the parent in the entity (persistent) inheritance hierarchy
//	 * (vs class inheritance hierarchy)
//	 * <!-- end-user-doc -->
//	 */
//	IEntity parentEntity();
//
//	/**
//	 * Return the name of the entity's primary key column.
//	 * Return null if the entity's primary key is "compound"
//	 * (i.e. the primary key is composed of multiple columns).
//	 */
//	String primaryKeyColumnName();
//
//	/**
//	 * Return the name of the entity's primary key attribute.
//	 * Return null if the entity's primary key is "compound"
//	 * (i.e. the primary key is composed of multiple columns).
//	 */
//	String primaryKeyAttributeName();
//
//	IAttributeOverride createAttributeOverride(int index);
//
//	IAssociationOverride createAssociationOverride(int index);
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
//	ISecondaryTable createSecondaryTable(int index);
//
//	boolean containsSpecifiedPrimaryKeyJoinColumns();
//
//	IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index);
//
//	INamedQuery createNamedQuery(int index);
//
//	INamedNativeQuery createNamedNativeQuery(int index);
//
//
//	abstract class OverrideOwner implements IOverride.Owner
//	{
//		protected IEntity entity;
//
//		public OverrideOwner(IEntity entity) {
//			this.entity = entity;
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.entity;
//		}
//
//		public ITextRange validationTextRange() {
//			return entity.validationTextRange();
//		}
//	}
//
//
//	class AttributeOverrideOwner extends OverrideOwner
//	{
//		public AttributeOverrideOwner(IEntity entity) {
//			super(entity);
//		}
//
//		public IAttributeMapping attributeMapping(String attributeName) {
//			return (IAttributeMapping) columnMapping(attributeName);
//		}
//
//		private IColumnMapping columnMapping(String attributeName) {
//			if (attributeName == null) {
//				return null;
//			}
//			for (Iterator<IPersistentAttribute> stream = this.entity.getPersistentType().allAttributes(); stream.hasNext();) {
//				IPersistentAttribute persAttribute = stream.next();
//				if (attributeName.equals(persAttribute.getName())) {
//					if (persAttribute.getMapping() instanceof IColumnMapping) {
//						return (IColumnMapping) persAttribute.getMapping();
//					}
//				}
//			}
//			return null;
//		}
//
//		public boolean isVirtual(IOverride override) {
//			return entity.getDefaultAttributeOverrides().contains(override);
//		}
//	}
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
//	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
//	{
//		private IEntity entity;
//
//		public PrimaryKeyJoinColumnOwner(IEntity entity) {
//			this.entity = entity;
//		}
//
//		public ITextRange validationTextRange() {
//			return this.entity.validationTextRange();
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.entity;
//		}
//
//		public Table dbTable(String tableName) {
//			return this.entity.dbTable(tableName);
//		}
//
//		public Table dbReferencedColumnTable() {
//			IEntity parentEntity = this.entity.parentEntity();
//			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
//		}
//
//		public List<IPrimaryKeyJoinColumn> joinColumns() {
//			return this.entity.getPrimaryKeyJoinColumns();
//		}
//
//		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
//			return this.entity.getDefaultPrimaryKeyJoinColumns().contains(joinColumn);
//		}
//
//		public int indexOf(IAbstractJoinColumn joinColumn) {
//			return joinColumns().indexOf(joinColumn);
//		}
//	}
}

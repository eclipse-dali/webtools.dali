/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;


public interface IJoinTable extends ITable
{

	<T extends IJoinColumn> ListIterator<T> joinColumns();
	<T extends IJoinColumn> ListIterator<T> specifiedJoinColumns();
	<T extends IJoinColumn> ListIterator<T> defaultJoinColumns();
	int specifiedJoinColumnsSize();
	IJoinColumn addSpecifiedJoinColumn(int index);
	void removeSpecifiedJoinColumn(int index);
	void moveSpecifiedJoinColumn(int oldIndex, int newIndex);
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumnsList";
		String DEFAULT_JOIN_COLUMNS_LIST = "defaultJoinColumnsList";
	
	boolean containsSpecifiedJoinColumns();
	

	<T extends IJoinColumn> ListIterator<T> inverseJoinColumns();
	<T extends IJoinColumn> ListIterator<T> specifiedInverseJoinColumns();
	<T extends IJoinColumn> ListIterator<T> defaultInverseJoinColumns();
	int specifiedInverseJoinColumnsSize();
	IJoinColumn addSpecifiedInverseJoinColumn(int index);
	void removeSpecifiedInverseJoinColumn(int index);
	void moveSpecifiedInverseJoinColumn(int oldIndex, int newIndex);
		String SPECIFIED_INVERSE_JOIN_COLUMNS_LIST = "specifiedInverseJoinColumnsList";
		String DEFAULT_INVERSE_JOIN_COLUMNS_LIST = "defaultInverseJoinColumnsList";

	boolean containsSpecifiedInverseJoinColumns();

	IRelationshipMapping relationshipMapping();


	/**
//	 * just a little common behavior
//	 */
//	abstract class AbstractJoinColumnOwner implements IJoinColumn.Owner
//	{
//		private final IJoinTable joinTable;
//
//		AbstractJoinColumnOwner(IJoinTable joinTable) {
//			super();
//			this.joinTable = joinTable;
//		}
//
//		protected IJoinTable getJoinTable() {
//			return this.joinTable;
//		}
//
//		public IRelationshipMapping getRelationshipMapping() {
//			return this.joinTable.relationshipMapping();
//		}
//
//		/**
//		 * the default table name is always valid and a specified table name
//		 * is prohibited (which will be handled elsewhere)
//		 */
//		public boolean tableNameIsInvalid(String tableName) {
//			return false;
//		}
//
//		/**
//		 * the join column can only be on the join table itself
//		 */
//		public boolean tableIsAllowed() {
//			return false;
//		}
//
//		public ITextRange validationTextRange() {
//			return this.joinTable.validationTextRange();
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.joinTable.getOwner().getTypeMapping();
//		}
//
//		public Table dbTable(String tableName) {
//			if (this.joinTable.getName() == null) {
//				return null;
//			}
//			return (this.joinTable.getName().equals(tableName)) ? this.joinTable.dbTable() : null;
//		}
//	}
//
//
//	/**
//	 * owner for "forward-pointer" JoinColumns;
//	 * these point at the target/inverse entity
//	 */
//	class InverseJoinColumnOwner extends AbstractJoinColumnOwner
//	{
//		public InverseJoinColumnOwner(IJoinTable joinTable) {
//			super(joinTable);
//		}
//
//		public List<IJoinColumn> joinColumns() {
//			return getJoinTable().getInverseJoinColumns();
//		}
//		
//		public int indexOf(IAbstractJoinColumn joinColumn) {
//			return joinColumns().indexOf(joinColumn);
//		}
//
//		public IEntity targetEntity() {
//			return getJoinTable().relationshipMapping().getResolvedTargetEntity();
//		}
//
//		public String attributeName() {
//			return getJoinTable().relationshipMapping().getPersistentAttribute().getName();
//		}
//
//		@Override
//		public Table dbTable(String tableName) {
//			Table dbTable = super.dbTable(tableName);
//			if (dbTable != null) {
//				return dbTable;
//			}
//			IEntity targetEntity = targetEntity();
//			return (targetEntity == null) ? null : targetEntity.dbTable(tableName);
//		}
//
//		public Table dbReferencedColumnTable() {
//			IEntity targetEntity = targetEntity();
//			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
//		}
//		
//		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
//			return getJoinTable().getDefaultInverseJoinColumns().contains(joinColumn);
//		}
//	}
//
//
//	/**
//	 * owner for "back-pointer" JoinColumns;
//	 * these point at the source/owning entity
//	 */
//	class JoinColumnOwner extends AbstractJoinColumnOwner
//	{
//		public JoinColumnOwner(IJoinTable joinTable) {
//			super(joinTable);
//		}
//
//		public List<IJoinColumn> joinColumns() {
//			return getJoinTable().getJoinColumns();
//		}
//		
//		public int indexOf(IAbstractJoinColumn joinColumn) {
//			return joinColumns().indexOf(joinColumn);
//		}
//
//		public IEntity targetEntity() {
//			return getJoinTable().relationshipMapping().getEntity();
//		}
//
//		public String attributeName() {
//			IEntity targetEntity = getJoinTable().relationshipMapping().getResolvedTargetEntity();
//			if (targetEntity == null) {
//				return null;
//			}
//			String attributeName = getJoinTable().relationshipMapping().getPersistentAttribute().getName();
//			for (Iterator<IPersistentAttribute> stream = targetEntity.getPersistentType().allAttributes(); stream.hasNext();) {
//				IPersistentAttribute attribute = stream.next();
//				IAttributeMapping mapping = attribute.getMapping();
//				if (mapping instanceof INonOwningMapping) {
//					String mappedBy = ((INonOwningMapping) mapping).getMappedBy();
//					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
//						return attribute.getName();
//					}
//				}
//			}
//			return null;
//		}
//
//		@Override
//		public Table dbTable(String tableName) {
//			Table dbTable = super.dbTable(tableName);
//			if (dbTable != null) {
//				return dbTable;
//			}
//			return getTypeMapping().dbTable(tableName);
//		}
//
//		public Table dbReferencedColumnTable() {
//			return getTypeMapping().primaryDbTable();
//		}
//		
//		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
//			return getJoinTable().getDefaultJoinColumns().contains(joinColumn);
//		}
//	}
}

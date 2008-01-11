/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IAssociationOverride extends IOverride
{
	<T extends IJoinColumn> ListIterator<T> joinColumns();
	<T extends IJoinColumn> ListIterator<T> specifiedJoinColumns();
	<T extends IJoinColumn> ListIterator<T> defaultJoinColumns();
	int specifiedJoinColumnsSize();
	IJoinColumn addSpecifiedJoinColumn(int index);
	void removeSpecifiedJoinColumn(int index);
	void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex);
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumnsList";
		String DEFAULT_JOIN_COLUMNS_LIST = "defaultJoinColumnsList";
		
	boolean containsSpecifiedJoinColumns();


//	public class JoinColumnOwner implements IJoinColumn.Owner
//	{
//		private IAssociationOverride associationOverride;
//
//		public JoinColumnOwner(IAssociationOverride associationOverride) {
//			super();
//			this.associationOverride = associationOverride;
//		}
//
//		/**
//		 * by default, the join column is in the type mapping's primary table
//		 */
//		public String defaultTableName() {
//			return this.associationOverride.getOwner().getTypeMapping().getTableName();
//		}
//
//		public List<IJoinColumn> joinColumns() {
//			return this.associationOverride.getJoinColumns();
//		}
//
//		public int indexOf(IAbstractJoinColumn joinColumn) {
//			return joinColumns().indexOf(joinColumn);
//		}
//		
//		public IEntity targetEntity() {
//			return getRelationshipMapping().getResolvedTargetEntity();
//		}
//
//		public String attributeName() {
//			return this.associationOverride.getName();
//		}
//
//		public IRelationshipMapping getRelationshipMapping() {
//			//TODO cast or check instanceof first??
//			return (IRelationshipMapping) this.associationOverride.getOwner().attributeMapping(this.associationOverride.getName());
//		}
//
//		public boolean tableNameIsInvalid(String tableName) {
//			return getTypeMapping().tableNameIsInvalid(tableName);
//		}
//
//		/**
//		 * the join column can be on a secondary table
//		 */
//		public boolean tableIsAllowed() {
//			return true;
//		}
//
//		public ITextRange validationTextRange() {
//			return this.associationOverride.validationTextRange();
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.associationOverride.getOwner().getTypeMapping();
//		}
//
//		public Table dbTable(String tableName) {
//			return getTypeMapping().dbTable(tableName);
//		}
//
//		public Table dbReferencedColumnTable() {
//			IEntity targetEntity = targetEntity();
//			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
//		}
//		
//		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
//			return this.associationOverride.getDefaultJoinColumns().contains(joinColumn);
//		}
//
//	}
}
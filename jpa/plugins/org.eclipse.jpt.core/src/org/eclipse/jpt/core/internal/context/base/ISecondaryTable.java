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


public interface ISecondaryTable extends ITable
{
//	EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
//
//	EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
//
//	EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns();
//
//	ITypeMapping typeMapping();
//
//	/**
//	 * Create a primary key join column with the given index
//	 */
//	IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index);
//
//	boolean containsSpecifiedPrimaryKeyJoinColumns();
//
//	boolean isVirtual();
//
//	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
//	{
//		private ISecondaryTable secondaryTable;
//
//		public PrimaryKeyJoinColumnOwner(ISecondaryTable secondaryTable) {
//			this.secondaryTable = secondaryTable;
//		}
//
//		public ITextRange validationTextRange() {
//			return this.secondaryTable.validationTextRange();
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.secondaryTable.typeMapping();
//		}
//
//		public Table dbTable(String tableName) {
//			return this.secondaryTable.dbTable();
//		}
//
//		public Table dbReferencedColumnTable() {
//			return getTypeMapping().primaryDbTable();
//		}
//
//		public List<IPrimaryKeyJoinColumn> joinColumns() {
//			return this.secondaryTable.getPrimaryKeyJoinColumns();
//		}
//		
//		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
//			return this.secondaryTable.getDefaultPrimaryKeyJoinColumns().contains(joinColumn);
//		}
//		
//		public int indexOf(IAbstractJoinColumn joinColumn) {
//			return joinColumns().indexOf(joinColumn);
//		}
//	}
}

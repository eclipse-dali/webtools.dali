/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ISecondary Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getSpecifiedPrimaryKeyJoinColumns <em>Specified Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getDefaultPrimaryKeyJoinColumns <em>Default Primary Key Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ISecondaryTable extends ITable
{
	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable_PrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable_SpecifiedPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable_DefaultPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ITypeMapping typeMapping();

	/**
	 * Create a primary key join column with the given index
	 */
	IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index);

	boolean containsSpecifiedPrimaryKeyJoinColumns();


	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		private ISecondaryTable secondaryTable;

		public PrimaryKeyJoinColumnOwner(ISecondaryTable secondaryTable) {
			this.secondaryTable = secondaryTable;
		}

		public ITextRange getTextRange() {
			return this.secondaryTable.getTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return this.secondaryTable.typeMapping();
		}

		//TODO 1 do these 2 need to be swapped??
		public Table dbTable(String tableName) {
			return this.secondaryTable.dbTable();
		}

		//TODO 2 do these 2 need to be swapped??
		public Table dbReferencedColumnTable() {
			return getTypeMapping().primaryDbTable();
		}
	}
} // ISecondaryTable

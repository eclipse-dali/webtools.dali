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

import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IAssociation Override</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getSpecifiedJoinColumns <em>Specified Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getDefaultJoinColumns <em>Default Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IAssociationOverride extends IOverride
{
	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride_JoinColumns()
	 * @model containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IJoinColumn> getJoinColumns();

	/**
	 * Returns the value of the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride_SpecifiedJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getSpecifiedJoinColumns();

	/**
	 * Returns the value of the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride_DefaultJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getDefaultJoinColumns();

	IJoinColumn createJoinColumn(int index);

	boolean containsSpecifiedJoinColumns();

	ITypeMapping typeMapping();


	public class JoinColumnOwner implements IJoinColumn.Owner
	{
		private IAssociationOverride associationOverride;

		public JoinColumnOwner(IAssociationOverride associationOverride) {
			super();
			this.associationOverride = associationOverride;
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return this.associationOverride.getOwner().getTypeMapping().getTableName();
		}

		public List<IJoinColumn> joinColumns() {
			return this.associationOverride.getJoinColumns();
		}

		public int indexOf(IAbstractJoinColumn joinColumn) {
			return joinColumns().indexOf(joinColumn);
		}

		public IEntity targetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return this.associationOverride.getName();
		}

		public IRelationshipMapping getRelationshipMapping() {
			//TODO cast or check instanceof first??
			return (IRelationshipMapping) this.associationOverride.getOwner().attributeMapping(this.associationOverride.getName());
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public ITextRange validationTextRange() {
			return this.associationOverride.validationTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return this.associationOverride.getOwner().getTypeMapping();
		}

		public Table dbTable(String tableName) {
			return getTypeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return this.associationOverride.getDefaultJoinColumns().contains(joinColumn);
		}

	}
}
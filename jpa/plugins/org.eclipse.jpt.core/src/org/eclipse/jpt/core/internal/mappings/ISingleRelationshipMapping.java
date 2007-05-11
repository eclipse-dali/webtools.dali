/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
 * A representation of the model object '<em><b>ISingle Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getSpecifiedJoinColumns <em>Specified Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getDefaultJoinColumns <em>Default Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ISingleRelationshipMapping extends IRelationshipMapping
{
	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #setFetch(DefaultEagerFetchType)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	DefaultEagerFetchType getFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #getFetch()
	 * @generated
	 */
	void setFetch(DefaultEagerFetchType value);

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping_JoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true" transient="true" changeable="false" volatile="true"
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping_SpecifiedJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping_DefaultJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getDefaultJoinColumns();

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setOptional(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping_Optional()
	 * @model
	 * @generated
	 */
	DefaultTrueBoolean getOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getOptional()
	 * @generated
	 */
	void setOptional(DefaultTrueBoolean value);

	boolean containsSpecifiedJoinColumns();

	IJoinColumn createJoinColumn(int index);


	public class JoinColumnOwner implements IJoinColumn.Owner
	{
		private ISingleRelationshipMapping singleRelationshipMapping;

		public JoinColumnOwner(ISingleRelationshipMapping singleRelationshipMapping) {
			super();
			this.singleRelationshipMapping = singleRelationshipMapping;
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return this.singleRelationshipMapping.getPersistentAttribute().typeMapping().getTableName();
		}

		public List joinColumns() {
			return this.singleRelationshipMapping.getJoinColumns();
		}

		public IEntity targetEntity() {
			return this.singleRelationshipMapping.getResolvedTargetEntity();
		}

		public String attributeName() {
			return this.singleRelationshipMapping.getPersistentAttribute().getName();
		}

		public IRelationshipMapping getRelationshipMapping() {
			return singleRelationshipMapping;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.singleRelationshipMapping.getPersistentAttribute().typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public ITextRange validationTextRange() {
			return singleRelationshipMapping.validationTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return singleRelationshipMapping.typeMapping();
		}

		public Table dbTable(String tableName) {
			return getTypeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
		}
	}
}
/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJoin Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedJoinColumns <em>Specified Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultJoinColumns <em>Default Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedInverseJoinColumns <em>Specified Inverse Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultInverseJoinColumns <em>Default Inverse Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJoinTable extends ITable
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_JoinColumns()
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_SpecifiedJoinColumns()
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_DefaultJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getDefaultJoinColumns();

	/**
	 * Returns the value of the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_InverseJoinColumns()
	 * @model containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IJoinColumn> getInverseJoinColumns();

	/**
	 * Returns the value of the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_SpecifiedInverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getSpecifiedInverseJoinColumns();

	/**
	 * Returns the value of the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable_DefaultInverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IJoinColumn> getDefaultInverseJoinColumns();

	IJoinColumn createJoinColumn(int index);

	IJoinColumn createInverseJoinColumn(int index);

	boolean containsSpecifiedJoinColumns();

	boolean containsSpecifiedInverseJoinColumns();

	IRelationshipMapping relationshipMapping();


	/**
	 * just a little common behavior
	 */
	abstract class AbstractJoinColumnOwner implements IJoinColumn.Owner
	{
		private final IJoinTable joinTable;

		AbstractJoinColumnOwner(IJoinTable joinTable) {
			super();
			this.joinTable = joinTable;
		}

		protected IJoinTable getJoinTable() {
			return this.joinTable;
		}

		public IRelationshipMapping getRelationshipMapping() {
			return this.joinTable.relationshipMapping();
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public boolean tableIsAllowed() {
			return false;
		}

		public ITextRange validationTextRange() {
			return this.joinTable.validationTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return this.joinTable.getOwner().getTypeMapping();
		}

		public Table dbTable(String tableName) {
			if (this.joinTable.getName() == null) {
				return null;
			}
			return (this.joinTable.getName().equals(tableName)) ? this.joinTable.dbTable() : null;
		}
	}


	/**
	 * owner for "forward-pointer" JoinColumns;
	 * these point at the target/inverse entity
	 */
	class InverseJoinColumnOwner extends AbstractJoinColumnOwner
	{
		public InverseJoinColumnOwner(IJoinTable joinTable) {
			super(joinTable);
		}

		public List<IJoinColumn> joinColumns() {
			return getJoinTable().getInverseJoinColumns();
		}

		public int indexOf(IAbstractJoinColumn joinColumn) {
			return joinColumns().indexOf(joinColumn);
		}

		public IEntity targetEntity() {
			return getJoinTable().relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return getJoinTable().relationshipMapping().getPersistentAttribute().getName();
		}

		@Override
		public Table dbTable(String tableName) {
			Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}

		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return getJoinTable().getDefaultInverseJoinColumns().contains(joinColumn);
		}
	}


	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	class JoinColumnOwner extends AbstractJoinColumnOwner
	{
		public JoinColumnOwner(IJoinTable joinTable) {
			super(joinTable);
		}

		public List<IJoinColumn> joinColumns() {
			return getJoinTable().getJoinColumns();
		}

		public int indexOf(IAbstractJoinColumn joinColumn) {
			return joinColumns().indexOf(joinColumn);
		}

		public IEntity targetEntity() {
			return getJoinTable().relationshipMapping().getEntity();
		}

		public String attributeName() {
			IEntity targetEntity = getJoinTable().relationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			String attributeName = getJoinTable().relationshipMapping().getPersistentAttribute().getName();
			for (Iterator<IPersistentAttribute> stream = targetEntity.getPersistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute attribute = stream.next();
				IAttributeMapping mapping = attribute.getMapping();
				if (mapping instanceof INonOwningMapping) {
					String mappedBy = ((INonOwningMapping) mapping).getMappedBy();
					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
						return attribute.getName();
					}
				}
			}
			return null;
		}

		@Override
		public Table dbTable(String tableName) {
			Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			return getTypeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			return getTypeMapping().primaryDbTable();
		}

		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return getJoinTable().getDefaultJoinColumns().contains(joinColumn);
		}
	}
}

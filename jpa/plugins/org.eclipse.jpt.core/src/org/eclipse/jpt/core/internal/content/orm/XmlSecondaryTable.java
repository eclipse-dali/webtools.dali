/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Secondary Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSecondaryTable()
 * @model kind="class"
 * @generated
 */
public class XmlSecondaryTable extends AbstractXmlTable
	implements ISecondaryTable
{
	/**
	 * The cached value of the '{@link #getSpecifiedPrimaryKeyJoinColumns() <em>Specified Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultPrimaryKeyJoinColumns() <em>Default Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	private XmlSecondaryTable() {
		super();
	}

	protected XmlSecondaryTable(Owner owner) {
		super(owner);
		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_SECONDARY_TABLE;
	}

	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
	}

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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISecondaryTable_SpecifiedPrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		if (specifiedPrimaryKeyJoinColumns == null) {
			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return specifiedPrimaryKeyJoinColumns;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISecondaryTable_DefaultPrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		if (defaultPrimaryKeyJoinColumns == null) {
			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return defaultPrimaryKeyJoinColumns;
	}

	public ITypeMapping typeMapping() {
		return (ITypeMapping) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS :
				return getPrimaryKeyJoinColumns();
			case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return getSpecifiedPrimaryKeyJoinColumns();
			case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return getDefaultPrimaryKeyJoinColumns();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				getSpecifiedPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
			case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				getDefaultPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS :
				return !getPrimaryKeyJoinColumns().isEmpty();
			case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return specifiedPrimaryKeyJoinColumns != null && !specifiedPrimaryKeyJoinColumns.isEmpty();
			case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return defaultPrimaryKeyJoinColumns != null && !defaultPrimaryKeyJoinColumns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ISecondaryTable.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ISecondaryTable.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ISECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	private XmlEntityInternal entity() {
		return (XmlEntityInternal) eContainer();
	}

	@Override
	protected void makeTableForXmlNonNull() {
	//secondaryTables are part of a collection, the secondary-table element will be removed/added
	//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}

	@Override
	protected void makeTableForXmlNull() {
	//secondaryTables are part of a collection, the secondary-table element will be removed/added
	//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}

	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
	}

	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new ISecondaryTable.PrimaryKeyJoinColumnOwner(this));
	}
} // XmlSecondaryTable

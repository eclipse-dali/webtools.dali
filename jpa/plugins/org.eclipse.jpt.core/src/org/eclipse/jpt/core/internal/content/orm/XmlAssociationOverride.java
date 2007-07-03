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
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Association Override</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAssociationOverride()
 * @model kind="class"
 * @generated
 */
public class XmlAssociationOverride extends XmlOverride
	implements IAssociationOverride
{
	/**
	 * The cached value of the '{@link #getSpecifiedJoinColumns() <em>Specified Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> specifiedJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultJoinColumns() <em>Default Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> defaultJoinColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlAssociationOverride() {
		super();
	}

	protected XmlAssociationOverride(IOverride.Owner owner) {
		super(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_ASSOCIATION_OVERRIDE;
	}

	public EList<IJoinColumn> getJoinColumns() {
		return this.getSpecifiedJoinColumns().isEmpty() ? this.getDefaultJoinColumns() : this.getSpecifiedJoinColumns();
	}

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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAssociationOverride_SpecifiedJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS);
		}
		return specifiedJoinColumns;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAssociationOverride_DefaultJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultJoinColumns()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return getJoinColumns();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
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
		if (baseClass == IAssociationOverride.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__JOIN_COLUMNS;
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS;
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS;
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
		if (baseClass == IAssociationOverride.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__JOIN_COLUMNS :
					return OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
					return OrmPackage.XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
					return OrmPackage.XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	// ********** IAssociationOverride implementation **********
	public IJoinColumn createJoinColumn(int index) {
		return this.createXmlJoinColumn(index);
	}

	private XmlJoinColumn createXmlJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(this));
		//return XmlJoinColumn.createJoinTableJoinColumn(new JoinColumnOwner(), this.getMember(), index);
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
	}

	public ITypeMapping typeMapping() {
		return (ITypeMapping) eContainer();
	}
} // XmlAssociationOverride

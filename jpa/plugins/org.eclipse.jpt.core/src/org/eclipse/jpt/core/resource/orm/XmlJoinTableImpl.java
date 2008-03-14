/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join Table</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTableImpl()
 * @model kind="class"
 * @generated
 */
public class XmlJoinTableImpl extends AbstractXmlBaseTable implements XmlJoinTable
{
	/**
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> joinColumns;
	/**
	 * The cached value of the '{@link #getInverseJoinColumns() <em>Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> inverseJoinColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlJoinTableImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.XML_JOIN_TABLE_IMPL;
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable_InverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getInverseJoinColumns()
	{
		if (inverseJoinColumns == null)
		{
			inverseJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS);
		}
		return inverseJoinColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS:
				return ((InternalEList<?>)getInverseJoinColumns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS:
				return getInverseJoinColumns();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS:
				getInverseJoinColumns().clear();
				getInverseJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS:
				getInverseJoinColumns().clear();
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS:
				return inverseJoinColumns != null && !inverseJoinColumns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlJoinTable.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS: return OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS: return OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS;
				default: return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlJoinTable.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS: return OrmPackage.XML_JOIN_TABLE_IMPL__JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS: return OrmPackage.XML_JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public boolean isSpecified() {
		return true;
	}
} // JoinTable

/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sql Result Set Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.SqlResultSetMapping#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.SqlResultSetMapping#getEntityResults <em>Entity Results</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.SqlResultSetMapping#getColumnResults <em>Column Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getSqlResultSetMapping()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class SqlResultSetMapping extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntityResults() <em>Entity Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityResults()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityResult> entityResults;

	/**
	 * The cached value of the '{@link #getColumnResults() <em>Column Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnResults()
	 * @generated
	 * @ordered
	 */
	protected EList<ColumnResult> columnResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SqlResultSetMapping()
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
		return EclipseLinkOrmPackage.Literals.SQL_RESULT_SET_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getSqlResultSetMapping_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.SqlResultSetMapping#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Entity Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.EntityResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Results</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getSqlResultSetMapping_EntityResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EntityResult> getEntityResults()
	{
		if (entityResults == null)
		{
			entityResults = new EObjectContainmentEList<EntityResult>(EntityResult.class, this, EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS);
		}
		return entityResults;
	}

	/**
	 * Returns the value of the '<em><b>Column Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.ColumnResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Results</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getSqlResultSetMapping_ColumnResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<ColumnResult> getColumnResults()
	{
		if (columnResults == null)
		{
			columnResults = new EObjectContainmentEList<ColumnResult>(ColumnResult.class, this, EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS);
		}
		return columnResults;
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
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return ((InternalEList<?>)getEntityResults()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return ((InternalEList<?>)getColumnResults()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__NAME:
				return getName();
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return getEntityResults();
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return getColumnResults();
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
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				getEntityResults().clear();
				getEntityResults().addAll((Collection<? extends EntityResult>)newValue);
				return;
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				getColumnResults().clear();
				getColumnResults().addAll((Collection<? extends ColumnResult>)newValue);
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
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				getEntityResults().clear();
				return;
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				getColumnResults().clear();
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
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return entityResults != null && !entityResults.isEmpty();
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return columnResults != null && !columnResults.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // SqlResultSetMapping

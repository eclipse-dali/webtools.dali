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
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cascade Type Impl</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeTypeImpl()
 * @model kind="class"
 * @generated
 */
public class CascadeTypeImpl extends AbstractJpaEObject implements CascadeType
{
	/**
	 * The default value of the '{@link #isCascadeAll() <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_ALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeAll() <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeAll()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeAll = CASCADE_ALL_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadePersist = CASCADE_PERSIST_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeMerge() <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeMerge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_MERGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeMerge() <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeMerge()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeMerge = CASCADE_MERGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeRemove() <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRemove()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_REMOVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeRemove() <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRemove()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeRemove = CASCADE_REMOVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeRefresh() <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRefresh()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_REFRESH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeRefresh() <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRefresh()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeRefresh = CASCADE_REFRESH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CascadeTypeImpl()
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
		return EclipseLinkOrmPackage.Literals.CASCADE_TYPE_IMPL;
	}

	/**
	 * Returns the value of the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade All</em>' attribute.
	 * @see #setCascadeAll(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeType_CascadeAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeAll()
	{
		return cascadeAll;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.CascadeTypeImpl#isCascadeAll <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade All</em>' attribute.
	 * @see #isCascadeAll()
	 * @generated
	 */
	public void setCascadeAll(boolean newCascadeAll)
	{
		boolean oldCascadeAll = cascadeAll;
		cascadeAll = newCascadeAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_ALL, oldCascadeAll, cascadeAll));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' attribute.
	 * @see #setCascadePersist(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeType_CascadePersist()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.CascadeTypeImpl#isCascadePersist <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' attribute.
	 * @see #isCascadePersist()
	 * @generated
	 */
	public void setCascadePersist(boolean newCascadePersist)
	{
		boolean oldCascadePersist = cascadePersist;
		cascadePersist = newCascadePersist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_PERSIST, oldCascadePersist, cascadePersist));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Merge</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Merge</em>' attribute.
	 * @see #setCascadeMerge(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeType_CascadeMerge()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeMerge()
	{
		return cascadeMerge;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.CascadeTypeImpl#isCascadeMerge <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Merge</em>' attribute.
	 * @see #isCascadeMerge()
	 * @generated
	 */
	public void setCascadeMerge(boolean newCascadeMerge)
	{
		boolean oldCascadeMerge = cascadeMerge;
		cascadeMerge = newCascadeMerge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_MERGE, oldCascadeMerge, cascadeMerge));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Remove</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Remove</em>' attribute.
	 * @see #setCascadeRemove(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeType_CascadeRemove()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeRemove()
	{
		return cascadeRemove;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.CascadeTypeImpl#isCascadeRemove <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Remove</em>' attribute.
	 * @see #isCascadeRemove()
	 * @generated
	 */
	public void setCascadeRemove(boolean newCascadeRemove)
	{
		boolean oldCascadeRemove = cascadeRemove;
		cascadeRemove = newCascadeRemove;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REMOVE, oldCascadeRemove, cascadeRemove));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #setCascadeRefresh(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getCascadeType_CascadeRefresh()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeRefresh()
	{
		return cascadeRefresh;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.CascadeTypeImpl#isCascadeRefresh <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #isCascadeRefresh()
	 * @generated
	 */
	public void setCascadeRefresh(boolean newCascadeRefresh)
	{
		boolean oldCascadeRefresh = cascadeRefresh;
		cascadeRefresh = newCascadeRefresh;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REFRESH, oldCascadeRefresh, cascadeRefresh));
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
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_ALL:
				return isCascadeAll() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_PERSIST:
				return isCascadePersist() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_MERGE:
				return isCascadeMerge() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REMOVE:
				return isCascadeRemove() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REFRESH:
				return isCascadeRefresh() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_ALL:
				setCascadeAll(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_PERSIST:
				setCascadePersist(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_MERGE:
				setCascadeMerge(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REMOVE:
				setCascadeRemove(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REFRESH:
				setCascadeRefresh(((Boolean)newValue).booleanValue());
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
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_ALL:
				setCascadeAll(CASCADE_ALL_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_PERSIST:
				setCascadePersist(CASCADE_PERSIST_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_MERGE:
				setCascadeMerge(CASCADE_MERGE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REMOVE:
				setCascadeRemove(CASCADE_REMOVE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REFRESH:
				setCascadeRefresh(CASCADE_REFRESH_EDEFAULT);
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
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_ALL:
				return cascadeAll != CASCADE_ALL_EDEFAULT;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_PERSIST:
				return cascadePersist != CASCADE_PERSIST_EDEFAULT;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_MERGE:
				return cascadeMerge != CASCADE_MERGE_EDEFAULT;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REMOVE:
				return cascadeRemove != CASCADE_REMOVE_EDEFAULT;
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL__CASCADE_REFRESH:
				return cascadeRefresh != CASCADE_REFRESH_EDEFAULT;
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
		result.append(" (cascadeAll: ");
		result.append(cascadeAll);
		result.append(", cascadePersist: ");
		result.append(cascadePersist);
		result.append(", cascadeMerge: ");
		result.append(cascadeMerge);
		result.append(", cascadeRemove: ");
		result.append(cascadeRemove);
		result.append(", cascadeRefresh: ");
		result.append(cascadeRefresh);
		result.append(')');
		return result.toString();
	}

} // CascadeTypeImpl

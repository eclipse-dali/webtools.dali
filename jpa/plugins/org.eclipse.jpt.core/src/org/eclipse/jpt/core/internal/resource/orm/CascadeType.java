/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.common.IJpaEObject;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cascade Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeAll <em>Cascade All</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeMerge <em>Cascade Merge</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRemove <em>Cascade Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRefresh <em>Cascade Refresh</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType()
 * @model kind="class"
 * @extends IJpaEObject
 * @generated
 */
public class CascadeType extends JpaEObject implements IJpaEObject
{
	/**
	 * The cached value of the '{@link #getCascadeAll() <em>Cascade All</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeAll()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadeAll;

	/**
	 * The cached value of the '{@link #getCascadePersist() <em>Cascade Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadePersist;

	/**
	 * The cached value of the '{@link #getCascadeMerge() <em>Cascade Merge</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeMerge()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadeMerge;

	/**
	 * The cached value of the '{@link #getCascadeRemove() <em>Cascade Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeRemove()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadeRemove;

	/**
	 * The cached value of the '{@link #getCascadeRefresh() <em>Cascade Refresh</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeRefresh()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadeRefresh;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CascadeType()
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
		return OrmPackage.Literals.CASCADE_TYPE;
	}

	/**
	 * Returns the value of the '<em><b>Cascade All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade All</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade All</em>' containment reference.
	 * @see #setCascadeAll(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType_CascadeAll()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadeAll()
	{
		return cascadeAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadeAll(EmptyType newCascadeAll, NotificationChain msgs)
	{
		EmptyType oldCascadeAll = cascadeAll;
		cascadeAll = newCascadeAll;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_ALL, oldCascadeAll, newCascadeAll);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeAll <em>Cascade All</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade All</em>' containment reference.
	 * @see #getCascadeAll()
	 * @generated
	 */
	public void setCascadeAll(EmptyType newCascadeAll)
	{
		if (newCascadeAll != cascadeAll)
		{
			NotificationChain msgs = null;
			if (cascadeAll != null)
				msgs = ((InternalEObject)cascadeAll).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_ALL, null, msgs);
			if (newCascadeAll != null)
				msgs = ((InternalEObject)newCascadeAll).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_ALL, null, msgs);
			msgs = basicSetCascadeAll(newCascadeAll, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_ALL, newCascadeAll, newCascadeAll));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' containment reference.
	 * @see #setCascadePersist(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType_CascadePersist()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadePersist(EmptyType newCascadePersist, NotificationChain msgs)
	{
		EmptyType oldCascadePersist = cascadePersist;
		cascadePersist = newCascadePersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_PERSIST, oldCascadePersist, newCascadePersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadePersist <em>Cascade Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' containment reference.
	 * @see #getCascadePersist()
	 * @generated
	 */
	public void setCascadePersist(EmptyType newCascadePersist)
	{
		if (newCascadePersist != cascadePersist)
		{
			NotificationChain msgs = null;
			if (cascadePersist != null)
				msgs = ((InternalEObject)cascadePersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_PERSIST, null, msgs);
			if (newCascadePersist != null)
				msgs = ((InternalEObject)newCascadePersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_PERSIST, null, msgs);
			msgs = basicSetCascadePersist(newCascadePersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_PERSIST, newCascadePersist, newCascadePersist));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Merge</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Merge</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Merge</em>' containment reference.
	 * @see #setCascadeMerge(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType_CascadeMerge()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadeMerge()
	{
		return cascadeMerge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadeMerge(EmptyType newCascadeMerge, NotificationChain msgs)
	{
		EmptyType oldCascadeMerge = cascadeMerge;
		cascadeMerge = newCascadeMerge;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_MERGE, oldCascadeMerge, newCascadeMerge);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeMerge <em>Cascade Merge</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Merge</em>' containment reference.
	 * @see #getCascadeMerge()
	 * @generated
	 */
	public void setCascadeMerge(EmptyType newCascadeMerge)
	{
		if (newCascadeMerge != cascadeMerge)
		{
			NotificationChain msgs = null;
			if (cascadeMerge != null)
				msgs = ((InternalEObject)cascadeMerge).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_MERGE, null, msgs);
			if (newCascadeMerge != null)
				msgs = ((InternalEObject)newCascadeMerge).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_MERGE, null, msgs);
			msgs = basicSetCascadeMerge(newCascadeMerge, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_MERGE, newCascadeMerge, newCascadeMerge));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Remove</em>' containment reference.
	 * @see #setCascadeRemove(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType_CascadeRemove()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadeRemove()
	{
		return cascadeRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadeRemove(EmptyType newCascadeRemove, NotificationChain msgs)
	{
		EmptyType oldCascadeRemove = cascadeRemove;
		cascadeRemove = newCascadeRemove;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REMOVE, oldCascadeRemove, newCascadeRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRemove <em>Cascade Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Remove</em>' containment reference.
	 * @see #getCascadeRemove()
	 * @generated
	 */
	public void setCascadeRemove(EmptyType newCascadeRemove)
	{
		if (newCascadeRemove != cascadeRemove)
		{
			NotificationChain msgs = null;
			if (cascadeRemove != null)
				msgs = ((InternalEObject)cascadeRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_REMOVE, null, msgs);
			if (newCascadeRemove != null)
				msgs = ((InternalEObject)newCascadeRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_REMOVE, null, msgs);
			msgs = basicSetCascadeRemove(newCascadeRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REMOVE, newCascadeRemove, newCascadeRemove));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Refresh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Refresh</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Refresh</em>' containment reference.
	 * @see #setCascadeRefresh(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType_CascadeRefresh()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadeRefresh()
	{
		return cascadeRefresh;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadeRefresh(EmptyType newCascadeRefresh, NotificationChain msgs)
	{
		EmptyType oldCascadeRefresh = cascadeRefresh;
		cascadeRefresh = newCascadeRefresh;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REFRESH, oldCascadeRefresh, newCascadeRefresh);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRefresh <em>Cascade Refresh</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Refresh</em>' containment reference.
	 * @see #getCascadeRefresh()
	 * @generated
	 */
	public void setCascadeRefresh(EmptyType newCascadeRefresh)
	{
		if (newCascadeRefresh != cascadeRefresh)
		{
			NotificationChain msgs = null;
			if (cascadeRefresh != null)
				msgs = ((InternalEObject)cascadeRefresh).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_REFRESH, null, msgs);
			if (newCascadeRefresh != null)
				msgs = ((InternalEObject)newCascadeRefresh).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.CASCADE_TYPE__CASCADE_REFRESH, null, msgs);
			msgs = basicSetCascadeRefresh(newCascadeRefresh, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REFRESH, newCascadeRefresh, newCascadeRefresh));
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
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				return basicSetCascadeAll(null, msgs);
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				return basicSetCascadePersist(null, msgs);
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				return basicSetCascadeMerge(null, msgs);
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				return basicSetCascadeRemove(null, msgs);
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				return basicSetCascadeRefresh(null, msgs);
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
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				return getCascadeAll();
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				return getCascadePersist();
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				return getCascadeMerge();
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				return getCascadeRemove();
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				return getCascadeRefresh();
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
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				setCascadeAll((EmptyType)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				setCascadePersist((EmptyType)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				setCascadeMerge((EmptyType)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				setCascadeRemove((EmptyType)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				setCascadeRefresh((EmptyType)newValue);
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
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				setCascadeAll((EmptyType)null);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				setCascadePersist((EmptyType)null);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				setCascadeMerge((EmptyType)null);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				setCascadeRemove((EmptyType)null);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				setCascadeRefresh((EmptyType)null);
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
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				return cascadeAll != null;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				return cascadePersist != null;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				return cascadeMerge != null;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				return cascadeRemove != null;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				return cascadeRefresh != null;
		}
		return super.eIsSet(featureID);
	}

} // CascadeType

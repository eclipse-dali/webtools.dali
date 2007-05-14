/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.mappings.ICascade;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Cascade</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlCascade()
 * @model kind="class"
 * @generated
 */
public class XmlCascade extends XmlEObject implements ICascade
{
	/**
	 * The default value of the '{@link #isAll() <em>All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAll() <em>All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAll()
	 * @generated
	 * @ordered
	 */
	protected boolean all = ALL_EDEFAULT;

	/**
	 * The default value of the '{@link #isPersist() <em>Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPersist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PERSIST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPersist() <em>Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPersist()
	 * @generated
	 * @ordered
	 */
	protected boolean persist = PERSIST_EDEFAULT;

	/**
	 * The default value of the '{@link #isMerge() <em>Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMerge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MERGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMerge() <em>Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMerge()
	 * @generated
	 * @ordered
	 */
	protected boolean merge = MERGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isRemove() <em>Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemove()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMOVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRemove() <em>Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemove()
	 * @generated
	 * @ordered
	 */
	protected boolean remove = REMOVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isRefresh() <em>Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRefresh()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REFRESH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRefresh() <em>Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRefresh()
	 * @generated
	 * @ordered
	 */
	protected boolean refresh = REFRESH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlCascade() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_CASCADE;
	}

	/**
	 * Returns the value of the '<em><b>All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All</em>' attribute.
	 * @see #setAll(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getICascade_All()
	 * @model
	 * @generated
	 */
	public boolean isAll() {
		return all;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade#isAll <em>All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All</em>' attribute.
	 * @see #isAll()
	 * @generated
	 */
	public void setAll(boolean newAll) {
		boolean oldAll = all;
		all = newAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CASCADE__ALL, oldAll, all));
	}

	/**
	 * Returns the value of the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persist</em>' attribute.
	 * @see #setPersist(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getICascade_Persist()
	 * @model
	 * @generated
	 */
	public boolean isPersist() {
		return persist;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade#isPersist <em>Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persist</em>' attribute.
	 * @see #isPersist()
	 * @generated
	 */
	public void setPersist(boolean newPersist) {
		boolean oldPersist = persist;
		persist = newPersist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CASCADE__PERSIST, oldPersist, persist));
	}

	/**
	 * Returns the value of the '<em><b>Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merge</em>' attribute.
	 * @see #setMerge(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getICascade_Merge()
	 * @model
	 * @generated
	 */
	public boolean isMerge() {
		return merge;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade#isMerge <em>Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Merge</em>' attribute.
	 * @see #isMerge()
	 * @generated
	 */
	public void setMerge(boolean newMerge) {
		boolean oldMerge = merge;
		merge = newMerge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CASCADE__MERGE, oldMerge, merge));
	}

	/**
	 * Returns the value of the '<em><b>Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove</em>' attribute.
	 * @see #setRemove(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getICascade_Remove()
	 * @model
	 * @generated
	 */
	public boolean isRemove() {
		return remove;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade#isRemove <em>Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove</em>' attribute.
	 * @see #isRemove()
	 * @generated
	 */
	public void setRemove(boolean newRemove) {
		boolean oldRemove = remove;
		remove = newRemove;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CASCADE__REMOVE, oldRemove, remove));
	}

	/**
	 * Returns the value of the '<em><b>Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refresh</em>' attribute.
	 * @see #setRefresh(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getICascade_Refresh()
	 * @model
	 * @generated
	 */
	public boolean isRefresh() {
		return refresh;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade#isRefresh <em>Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh</em>' attribute.
	 * @see #isRefresh()
	 * @generated
	 */
	public void setRefresh(boolean newRefresh) {
		boolean oldRefresh = refresh;
		refresh = newRefresh;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CASCADE__REFRESH, oldRefresh, refresh));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_CASCADE__ALL :
				return isAll() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.XML_CASCADE__PERSIST :
				return isPersist() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.XML_CASCADE__MERGE :
				return isMerge() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.XML_CASCADE__REMOVE :
				return isRemove() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.XML_CASCADE__REFRESH :
				return isRefresh() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_CASCADE__ALL :
				setAll(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.XML_CASCADE__PERSIST :
				setPersist(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.XML_CASCADE__MERGE :
				setMerge(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.XML_CASCADE__REMOVE :
				setRemove(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.XML_CASCADE__REFRESH :
				setRefresh(((Boolean) newValue).booleanValue());
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
			case OrmPackage.XML_CASCADE__ALL :
				setAll(ALL_EDEFAULT);
				return;
			case OrmPackage.XML_CASCADE__PERSIST :
				setPersist(PERSIST_EDEFAULT);
				return;
			case OrmPackage.XML_CASCADE__MERGE :
				setMerge(MERGE_EDEFAULT);
				return;
			case OrmPackage.XML_CASCADE__REMOVE :
				setRemove(REMOVE_EDEFAULT);
				return;
			case OrmPackage.XML_CASCADE__REFRESH :
				setRefresh(REFRESH_EDEFAULT);
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
			case OrmPackage.XML_CASCADE__ALL :
				return all != ALL_EDEFAULT;
			case OrmPackage.XML_CASCADE__PERSIST :
				return persist != PERSIST_EDEFAULT;
			case OrmPackage.XML_CASCADE__MERGE :
				return merge != MERGE_EDEFAULT;
			case OrmPackage.XML_CASCADE__REMOVE :
				return remove != REMOVE_EDEFAULT;
			case OrmPackage.XML_CASCADE__REFRESH :
				return refresh != REFRESH_EDEFAULT;
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
		if (baseClass == ICascade.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_CASCADE__ALL :
					return JpaCoreMappingsPackage.ICASCADE__ALL;
				case OrmPackage.XML_CASCADE__PERSIST :
					return JpaCoreMappingsPackage.ICASCADE__PERSIST;
				case OrmPackage.XML_CASCADE__MERGE :
					return JpaCoreMappingsPackage.ICASCADE__MERGE;
				case OrmPackage.XML_CASCADE__REMOVE :
					return JpaCoreMappingsPackage.ICASCADE__REMOVE;
				case OrmPackage.XML_CASCADE__REFRESH :
					return JpaCoreMappingsPackage.ICASCADE__REFRESH;
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
		if (baseClass == ICascade.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ICASCADE__ALL :
					return OrmPackage.XML_CASCADE__ALL;
				case JpaCoreMappingsPackage.ICASCADE__PERSIST :
					return OrmPackage.XML_CASCADE__PERSIST;
				case JpaCoreMappingsPackage.ICASCADE__MERGE :
					return OrmPackage.XML_CASCADE__MERGE;
				case JpaCoreMappingsPackage.ICASCADE__REMOVE :
					return OrmPackage.XML_CASCADE__REMOVE;
				case JpaCoreMappingsPackage.ICASCADE__REFRESH :
					return OrmPackage.XML_CASCADE__REFRESH;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (all: ");
		result.append(all);
		result.append(", persist: ");
		result.append(persist);
		result.append(", merge: ");
		result.append(merge);
		result.append(", remove: ");
		result.append(remove);
		result.append(", refresh: ");
		result.append(refresh);
		result.append(')');
		return result.toString();
	}
} // XmlCascade

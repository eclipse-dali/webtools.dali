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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.mappings.InheritanceType;

/**
 * <!-- begin-user-doc -->
 * This class is purely used to hack around a problem with the translators and representing
 * the IEntity.inheritanceStrategy feature in the xml.  Added this object to correspend to the
 * inheritance type in the xml since the translator path approach caused the inheritance element
 * to be added even when the strategy was default was null.  This occurred when you added a new persistent
 * type to the orm.xml via the structure view.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance#getStrategy <em>Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlInheritance()
 * @model kind="class"
 * @generated
 */
public class XmlInheritance extends XmlEObject
{
	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final InheritanceType STRATEGY_EDEFAULT = InheritanceType.DEFAULT;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected InheritanceType strategy = STRATEGY_EDEFAULT;

	protected XmlInheritance() {
		super();
		this.eAdapters().add(this.buildListener());
	}

	protected Adapter buildListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				XmlInheritance.this.notifyChanged(notification);
			}
		};
	}

	protected void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(XmlInheritance.class)) {
			case OrmPackage.XML_INHERITANCE__STRATEGY :
				strategyChanged();
				break;
			default :
				break;
		}
	}

	private XmlEntityInternal xmlEntity() {
		return (XmlEntityInternal) eContainer();
	}

	protected void strategyChanged() {
		xmlEntity().setInheritanceStrategy(getStrategy());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_INHERITANCE;
	}

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.InheritanceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #setStrategy(InheritanceType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlInheritance_Strategy()
	 * @model
	 * @generated
	 */
	public InheritanceType getStrategy() {
		return strategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #getStrategy()
	 * @generated
	 */
	public void setStrategy(InheritanceType newStrategy) {
		InheritanceType oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_INHERITANCE__STRATEGY, oldStrategy, strategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_INHERITANCE__STRATEGY :
				return getStrategy();
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
			case OrmPackage.XML_INHERITANCE__STRATEGY :
				setStrategy((InheritanceType) newValue);
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
			case OrmPackage.XML_INHERITANCE__STRATEGY :
				setStrategy(STRATEGY_EDEFAULT);
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
			case OrmPackage.XML_INHERITANCE__STRATEGY :
				return strategy != STRATEGY_EDEFAULT;
		}
		return super.eIsSet(featureID);
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
		result.append(" (strategy: ");
		result.append(strategy);
		result.append(')');
		return result.toString();
	}
} // XmlInheritance

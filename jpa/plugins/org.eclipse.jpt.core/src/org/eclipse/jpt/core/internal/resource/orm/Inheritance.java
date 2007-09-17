/**
 * <copyright>
 * </copyright>
 *
 * $Id: Inheritance.java,v 1.1.2.1 2007/09/17 20:49:51 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.internal.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inheritance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy <em>Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritance()
 * @model kind="class"
 * @generated
 */
public class Inheritance extends JpaEObject implements EObject
{
	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final InheritanceType STRATEGY_EDEFAULT = InheritanceType.SINGLETABLE;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected InheritanceType strategy = STRATEGY_EDEFAULT;

	/**
	 * This is true if the Strategy attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean strategyESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Inheritance()
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
		return OrmPackage.Literals.INHERITANCE;
	}

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The default value is <code>"SINGLE_TABLE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.InheritanceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #setStrategy(InheritanceType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritance_Strategy()
	 * @model default="SINGLE_TABLE" unsettable="true"
	 * @generated
	 */
	public InheritanceType getStrategy()
	{
		return strategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @generated
	 */
	public void setStrategy(InheritanceType newStrategy)
	{
		InheritanceType oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		boolean oldStrategyESet = strategyESet;
		strategyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.INHERITANCE__STRATEGY, oldStrategy, strategy, !oldStrategyESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(InheritanceType)
	 * @generated
	 */
	public void unsetStrategy()
	{
		InheritanceType oldStrategy = strategy;
		boolean oldStrategyESet = strategyESet;
		strategy = STRATEGY_EDEFAULT;
		strategyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.INHERITANCE__STRATEGY, oldStrategy, STRATEGY_EDEFAULT, oldStrategyESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy <em>Strategy</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Strategy</em>' attribute is set.
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(InheritanceType)
	 * @generated
	 */
	public boolean isSetStrategy()
	{
		return strategyESet;
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
			case OrmPackage.INHERITANCE__STRATEGY:
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.INHERITANCE__STRATEGY:
				setStrategy((InheritanceType)newValue);
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
			case OrmPackage.INHERITANCE__STRATEGY:
				unsetStrategy();
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
			case OrmPackage.INHERITANCE__STRATEGY:
				return isSetStrategy();
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
		result.append(" (strategy: ");
		if (strategyESet) result.append(strategy); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // Inheritance

/**
 * <copyright>
 * </copyright>
 *
 * $Id: GeneratedValue.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generated Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy <em>Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class GeneratedValue extends JptEObject implements IJptEObject
{
	/**
	 * The default value of the '{@link #getGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenerator()
	 * @generated
	 * @ordered
	 */
	protected static final String GENERATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenerator()
	 * @generated
	 * @ordered
	 */
	protected String generator = GENERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final GenerationType STRATEGY_EDEFAULT = GenerationType.TABLE;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected GenerationType strategy = STRATEGY_EDEFAULT;

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
	protected GeneratedValue()
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
		return OrmPackage.Literals.GENERATED_VALUE;
	}

	/**
	 * Returns the value of the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator</em>' attribute.
	 * @see #setGenerator(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue_Generator()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getGenerator()
	{
		return generator;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getGenerator <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator</em>' attribute.
	 * @see #getGenerator()
	 * @generated
	 */
	public void setGenerator(String newGenerator)
	{
		String oldGenerator = generator;
		generator = newGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.GENERATED_VALUE__GENERATOR, oldGenerator, generator));
	}

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The default value is <code>"TABLE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.GenerationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #setStrategy(GenerationType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue_Strategy()
	 * @model default="TABLE" unsettable="true"
	 * @generated
	 */
	public GenerationType getStrategy()
	{
		return strategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @generated
	 */
	public void setStrategy(GenerationType newStrategy)
	{
		GenerationType oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		boolean oldStrategyESet = strategyESet;
		strategyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.GENERATED_VALUE__STRATEGY, oldStrategy, strategy, !oldStrategyESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(GenerationType)
	 * @generated
	 */
	public void unsetStrategy()
	{
		GenerationType oldStrategy = strategy;
		boolean oldStrategyESet = strategyESet;
		strategy = STRATEGY_EDEFAULT;
		strategyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.GENERATED_VALUE__STRATEGY, oldStrategy, STRATEGY_EDEFAULT, oldStrategyESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy <em>Strategy</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Strategy</em>' attribute is set.
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(GenerationType)
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
			case OrmPackage.GENERATED_VALUE__GENERATOR:
				return getGenerator();
			case OrmPackage.GENERATED_VALUE__STRATEGY:
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
			case OrmPackage.GENERATED_VALUE__GENERATOR:
				setGenerator((String)newValue);
				return;
			case OrmPackage.GENERATED_VALUE__STRATEGY:
				setStrategy((GenerationType)newValue);
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
			case OrmPackage.GENERATED_VALUE__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case OrmPackage.GENERATED_VALUE__STRATEGY:
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
			case OrmPackage.GENERATED_VALUE__GENERATOR:
				return GENERATOR_EDEFAULT == null ? generator != null : !GENERATOR_EDEFAULT.equals(generator);
			case OrmPackage.GENERATED_VALUE__STRATEGY:
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
		result.append(" (generator: ");
		result.append(generator);
		result.append(", strategy: ");
		if (strategyESet) result.append(strategy); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // GeneratedValue

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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generated Value</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getGeneratedValueImpl()
 * @model kind="class"
 * @generated
 */
public class GeneratedValueImpl extends AbstractJpaEObject implements XmlGeneratedValue
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
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final GenerationType STRATEGY_EDEFAULT = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GeneratedValueImpl()
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
		return OrmPackage.Literals.GENERATED_VALUE_IMPL;
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getGeneratedValue_Generator()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getGenerator()
	{
		return generator;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.GeneratedValueImpl#getGenerator <em>Generator</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.GENERATED_VALUE_IMPL__GENERATOR, oldGenerator, generator));
	}

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The default value is <code>"TABLE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.GenerationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @see #setStrategy(GenerationType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getGeneratedValue_Strategy()
	 * @model default="TABLE"
	 * @generated
	 */
	public GenerationType getStrategy()
	{
		return strategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.GeneratedValueImpl#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @see #getStrategy()
	 * @generated
	 */
	public void setStrategy(GenerationType newStrategy)
	{
		GenerationType oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.GENERATED_VALUE_IMPL__STRATEGY, oldStrategy, strategy));
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
			case OrmPackage.GENERATED_VALUE_IMPL__GENERATOR:
				return getGenerator();
			case OrmPackage.GENERATED_VALUE_IMPL__STRATEGY:
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
			case OrmPackage.GENERATED_VALUE_IMPL__GENERATOR:
				setGenerator((String)newValue);
				return;
			case OrmPackage.GENERATED_VALUE_IMPL__STRATEGY:
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
			case OrmPackage.GENERATED_VALUE_IMPL__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case OrmPackage.GENERATED_VALUE_IMPL__STRATEGY:
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.GENERATED_VALUE_IMPL__GENERATOR:
				return GENERATOR_EDEFAULT == null ? generator != null : !GENERATOR_EDEFAULT.equals(generator);
			case OrmPackage.GENERATED_VALUE_IMPL__STRATEGY:
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (generator: ");
		result.append(generator);
		result.append(", strategy: ");
		result.append(strategy);
		result.append(')');
		return result.toString();
	}

} // GeneratedValue

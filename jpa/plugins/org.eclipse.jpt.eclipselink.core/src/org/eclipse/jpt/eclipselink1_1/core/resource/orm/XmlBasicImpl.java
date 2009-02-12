/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic Impl</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicImpl()
 * @model kind="class"
 * @generated
 */
public class XmlBasicImpl extends org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl implements XmlBasic
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final AccessType ACCESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType access = ACCESS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGeneratedValue() <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneratedValue()
	 * @generated
	 * @ordered
	 */
	protected XmlGeneratedValue generatedValue;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected XmlTableGenerator tableGenerator;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected XmlSequenceGenerator sequenceGenerator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlBasicImpl()
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
		return EclipseLink1_1OrmPackage.Literals.XML_BASIC_IMPL;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"PROPERTY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlAttributeMapping_Access()
	 * @model default="PROPERTY"
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS, oldAccess, access));
	}

	/**
	 * Returns the value of the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Value</em>' containment reference.
	 * @see #setGeneratedValue(XmlGeneratedValue)
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasic_GeneratedValue()
	 * @model containment="true"
	 * @generated
	 */
	public XmlGeneratedValue getGeneratedValue()
	{
		return generatedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGeneratedValue(XmlGeneratedValue newGeneratedValue, NotificationChain msgs)
	{
		XmlGeneratedValue oldGeneratedValue = generatedValue;
		generatedValue = newGeneratedValue;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE, oldGeneratedValue, newGeneratedValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl#getGeneratedValue <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated Value</em>' containment reference.
	 * @see #getGeneratedValue()
	 * @generated
	 */
	public void setGeneratedValue(XmlGeneratedValue newGeneratedValue)
	{
		if (newGeneratedValue != generatedValue)
		{
			NotificationChain msgs = null;
			if (generatedValue != null)
				msgs = ((InternalEObject)generatedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE, null, msgs);
			if (newGeneratedValue != null)
				msgs = ((InternalEObject)newGeneratedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE, null, msgs);
			msgs = basicSetGeneratedValue(newGeneratedValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE, newGeneratedValue, newGeneratedValue));
	}

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(XmlTableGenerator)
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasic_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTableGenerator getTableGenerator()
	{
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(XmlTableGenerator newTableGenerator, NotificationChain msgs)
	{
		XmlTableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(XmlTableGenerator newTableGenerator)
	{
		if (newTableGenerator != tableGenerator)
		{
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject)tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject)newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(XmlSequenceGenerator)
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasic_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlSequenceGenerator getSequenceGenerator()
	{
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(XmlSequenceGenerator newSequenceGenerator, NotificationChain msgs)
	{
		XmlSequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(XmlSequenceGenerator newSequenceGenerator)
	{
		if (newSequenceGenerator != sequenceGenerator)
		{
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject)sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject)newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
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
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE:
				return basicSetGeneratedValue(null, msgs);
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR:
				return basicSetTableGenerator(null, msgs);
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR:
				return basicSetSequenceGenerator(null, msgs);
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
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS:
				return getAccess();
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE:
				return getGeneratedValue();
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR:
				return getTableGenerator();
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR:
				return getSequenceGenerator();
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
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE:
				setGeneratedValue((XmlGeneratedValue)newValue);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR:
				setTableGenerator((XmlTableGenerator)newValue);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR:
				setSequenceGenerator((XmlSequenceGenerator)newValue);
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
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE:
				setGeneratedValue((XmlGeneratedValue)null);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR:
				setTableGenerator((XmlTableGenerator)null);
				return;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR:
				setSequenceGenerator((XmlSequenceGenerator)null);
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
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS:
				return access != ACCESS_EDEFAULT;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE:
				return generatedValue != null;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR:
				return tableGenerator != null;
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR:
				return sequenceGenerator != null;
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS: return EclipseLink1_1OrmPackage.XML_ATTRIBUTE_MAPPING__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlBasic.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE: return EclipseLink1_1OrmPackage.XML_BASIC__GENERATED_VALUE;
				case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR: return EclipseLink1_1OrmPackage.XML_BASIC__TABLE_GENERATOR;
				case EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR: return EclipseLink1_1OrmPackage.XML_BASIC__SEQUENCE_GENERATOR;
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLink1_1OrmPackage.XML_ATTRIBUTE_MAPPING__ACCESS: return EclipseLink1_1OrmPackage.XML_BASIC_IMPL__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlBasic.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLink1_1OrmPackage.XML_BASIC__GENERATED_VALUE: return EclipseLink1_1OrmPackage.XML_BASIC_IMPL__GENERATED_VALUE;
				case EclipseLink1_1OrmPackage.XML_BASIC__TABLE_GENERATOR: return EclipseLink1_1OrmPackage.XML_BASIC_IMPL__TABLE_GENERATOR;
				case EclipseLink1_1OrmPackage.XML_BASIC__SEQUENCE_GENERATOR: return EclipseLink1_1OrmPackage.XML_BASIC_IMPL__SEQUENCE_GENERATOR;
				default: return -1;
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (access: ");
		result.append(access);
		result.append(')');
		return result.toString();
	}

} // XmlBasicImpl

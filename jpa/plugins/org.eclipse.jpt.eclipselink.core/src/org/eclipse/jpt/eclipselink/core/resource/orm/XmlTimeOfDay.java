/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Time Of Day</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getHour <em>Hour</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute <em>Minute</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond <em>Second</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond <em>Millisecond</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlTimeOfDay extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getHour() <em>Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHour()
	 * @generated
	 * @ordered
	 */
	protected static final Integer HOUR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHour() <em>Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHour()
	 * @generated
	 * @ordered
	 */
	protected Integer hour = HOUR_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinute() <em>Minute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinute()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MINUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinute() <em>Minute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinute()
	 * @generated
	 * @ordered
	 */
	protected Integer minute = MINUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecond() <em>Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecond()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SECOND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSecond() <em>Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecond()
	 * @generated
	 * @ordered
	 */
	protected Integer second = SECOND_EDEFAULT;

	/**
	 * The default value of the '{@link #getMillisecond() <em>Millisecond</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMillisecond()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MILLISECOND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMillisecond() <em>Millisecond</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMillisecond()
	 * @generated
	 * @ordered
	 */
	protected Integer millisecond = MILLISECOND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlTimeOfDay()
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
		return EclipseLinkOrmPackage.Literals.XML_TIME_OF_DAY;
	}

	/**
	 * Returns the value of the '<em><b>Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hour</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hour</em>' attribute.
	 * @see #setHour(Integer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay_Hour()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getHour()
	{
		return hour;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getHour <em>Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hour</em>' attribute.
	 * @see #getHour()
	 * @generated
	 */
	public void setHour(Integer newHour)
	{
		Integer oldHour = hour;
		hour = newHour;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TIME_OF_DAY__HOUR, oldHour, hour));
	}

	/**
	 * Returns the value of the '<em><b>Minute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minute</em>' attribute.
	 * @see #setMinute(Integer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay_Minute()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getMinute()
	{
		return minute;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute <em>Minute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minute</em>' attribute.
	 * @see #getMinute()
	 * @generated
	 */
	public void setMinute(Integer newMinute)
	{
		Integer oldMinute = minute;
		minute = newMinute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TIME_OF_DAY__MINUTE, oldMinute, minute));
	}

	/**
	 * Returns the value of the '<em><b>Second</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second</em>' attribute.
	 * @see #setSecond(Integer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay_Second()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getSecond()
	{
		return second;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond <em>Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second</em>' attribute.
	 * @see #getSecond()
	 * @generated
	 */
	public void setSecond(Integer newSecond)
	{
		Integer oldSecond = second;
		second = newSecond;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TIME_OF_DAY__SECOND, oldSecond, second));
	}

	/**
	 * Returns the value of the '<em><b>Millisecond</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Millisecond</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Millisecond</em>' attribute.
	 * @see #setMillisecond(Integer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay_Millisecond()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getMillisecond()
	{
		return millisecond;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond <em>Millisecond</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Millisecond</em>' attribute.
	 * @see #getMillisecond()
	 * @generated
	 */
	public void setMillisecond(Integer newMillisecond)
	{
		Integer oldMillisecond = millisecond;
		millisecond = newMillisecond;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TIME_OF_DAY__MILLISECOND, oldMillisecond, millisecond));
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
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__HOUR:
				return getHour();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MINUTE:
				return getMinute();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__SECOND:
				return getSecond();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MILLISECOND:
				return getMillisecond();
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
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__HOUR:
				setHour((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MINUTE:
				setMinute((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__SECOND:
				setSecond((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MILLISECOND:
				setMillisecond((Integer)newValue);
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
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__HOUR:
				setHour(HOUR_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MINUTE:
				setMinute(MINUTE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__SECOND:
				setSecond(SECOND_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MILLISECOND:
				setMillisecond(MILLISECOND_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__HOUR:
				return HOUR_EDEFAULT == null ? hour != null : !HOUR_EDEFAULT.equals(hour);
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MINUTE:
				return MINUTE_EDEFAULT == null ? minute != null : !MINUTE_EDEFAULT.equals(minute);
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__SECOND:
				return SECOND_EDEFAULT == null ? second != null : !SECOND_EDEFAULT.equals(second);
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY__MILLISECOND:
				return MILLISECOND_EDEFAULT == null ? millisecond != null : !MILLISECOND_EDEFAULT.equals(millisecond);
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
		result.append(" (hour: ");
		result.append(hour);
		result.append(", minute: ");
		result.append(minute);
		result.append(", second: ");
		result.append(second);
		result.append(", millisecond: ");
		result.append(millisecond);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildHourTranslator(),
			buildMinuteTranslator(),
			buildSecondTranslator(),
			buildMillisecondTranslator(),
		};
	}
	
	protected static Translator buildHourTranslator() {
		return new Translator(EclipseLink.EXPIRY_TIME_OF_DAY__HOUR, EclipseLinkOrmPackage.eINSTANCE.getXmlTimeOfDay_Hour(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMinuteTranslator() {
		return new Translator(EclipseLink.EXPIRY_TIME_OF_DAY__MINUTE, EclipseLinkOrmPackage.eINSTANCE.getXmlTimeOfDay_Minute(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildSecondTranslator() {
		return new Translator(EclipseLink.EXPIRY_TIME_OF_DAY__SECOND, EclipseLinkOrmPackage.eINSTANCE.getXmlTimeOfDay_Second(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMillisecondTranslator() {
		return new Translator(EclipseLink.EXPIRY_TIME_OF_DAY__MILLISECOND, EclipseLinkOrmPackage.eINSTANCE.getXmlTimeOfDay_Millisecond(), Translator.DOM_ATTRIBUTE);
	}
	
} // XmlTimeOfDay

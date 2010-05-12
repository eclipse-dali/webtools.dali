/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Fetch Group</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup()
 * @model kind="class"
 * @generated
 */
public class XmlFetchGroup extends AbstractJpaEObject implements XmlFetchGroup_2_1
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
	 * The default value of the '{@link #getLoad() <em>Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoad()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean LOAD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLoad() <em>Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoad()
	 * @generated
	 * @ordered
	 */
	protected Boolean load = LOAD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlFetchAttribute> attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlFetchGroup()
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
		return EclipseLinkOrmPackage.Literals.XML_FETCH_GROUP;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup_2_1_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchGroup#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_FETCH_GROUP__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup_2_1_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlFetchAttribute> getAttributes()
	{
		if (attributes == null)
		{
			attributes = new EObjectContainmentEList<XmlFetchAttribute>(XmlFetchAttribute.class, this, EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * Returns the value of the '<em><b>Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load</em>' attribute.
	 * @see #setLoad(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup_2_1_Load()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getLoad()
	{
		return load;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchGroup#getLoad <em>Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' attribute.
	 * @see #getLoad()
	 * @generated
	 */
	public void setLoad(Boolean newLoad)
	{
		Boolean oldLoad = load;
		load = newLoad;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_FETCH_GROUP__LOAD, oldLoad, load));
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
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__LOAD:
				return getLoad();
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES:
				return getAttributes();
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
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__LOAD:
				setLoad((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends XmlFetchAttribute>)newValue);
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
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__LOAD:
				setLoad(LOAD_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES:
				getAttributes().clear();
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
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__LOAD:
				return LOAD_EDEFAULT == null ? load != null : !LOAD_EDEFAULT.equals(load);
			case EclipseLinkOrmPackage.XML_FETCH_GROUP__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
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
		result.append(", load: ");
		result.append(load);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
				elementName,
				structuralFeature,
				buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
				buildNameTranslator(),
				buildLoadTranslator(),
				buildAttributesTranslator()};
	}
	
	private static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlFetchGroup_2_1_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	private static Translator buildAttributesTranslator() {
		return XmlFetchAttribute.buildTranslator(EclipseLink2_1.ATTRIBUTE, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlFetchGroup_2_1_Attributes());
	}
	
	protected static Translator buildLoadTranslator() {
		return new Translator(EclipseLink2_1.FETCH_GROUP__LOAD, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlFetchGroup_2_1_Load(), Translator.DOM_ATTRIBUTE);
	}

}

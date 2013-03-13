/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Any Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getXmlPath <em>Xml Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyAttribute()
 * @model kind="class"
 * @generated
 */
public class EXmlAnyAttribute extends EJavaAttribute implements EAccessibleJavaAttribute, EContainerJavaAttribute, EReadWriteJavaAttribute, EPropertyHolder
{
	/**
	 * The cached value of the '{@link #getXmlAccessMethods() <em>Xml Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessMethods()
	 * @generated
	 * @ordered
	 */
	protected EXmlAccessMethods xmlAccessMethods;
	/**
	 * The default value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected String containerType = CONTAINER_TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean readOnly = READ_ONLY_EDEFAULT;
	/**
	 * The default value of the '{@link #isWriteOnly() <em>Write Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWriteOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WRITE_ONLY_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isWriteOnly() <em>Write Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWriteOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean writeOnly = WRITE_ONLY_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlProperties() <em>Xml Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlProperty> xmlProperties;
	/**
	 * The default value of the '{@link #getXmlPath() <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlPath()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getXmlPath() <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlPath()
	 * @generated
	 * @ordered
	 */
	protected String xmlPath = XML_PATH_EDEFAULT;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlAnyAttribute()
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
		return OxmPackage.Literals.EXML_ANY_ATTRIBUTE;
	}
	
	
	/**
	 * Returns the value of the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Access Methods</em>' containment reference.
	 * @see #setXmlAccessMethods(EXmlAccessMethods)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAccessibleJavaAttribute_XmlAccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlAccessMethods getXmlAccessMethods()
	{
		return xmlAccessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlAccessMethods(EXmlAccessMethods newXmlAccessMethods, NotificationChain msgs)
	{
		EXmlAccessMethods oldXmlAccessMethods = xmlAccessMethods;
		xmlAccessMethods = newXmlAccessMethods;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS, oldXmlAccessMethods, newXmlAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getXmlAccessMethods <em>Xml Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Access Methods</em>' containment reference.
	 * @see #getXmlAccessMethods()
	 * @generated
	 */
	public void setXmlAccessMethods(EXmlAccessMethods newXmlAccessMethods)
	{
		if (newXmlAccessMethods != xmlAccessMethods)
		{
			NotificationChain msgs = null;
			if (xmlAccessMethods != null)
				msgs = ((InternalEObject)xmlAccessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS, null, msgs);
			if (newXmlAccessMethods != null)
				msgs = ((InternalEObject)newXmlAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS, null, msgs);
			msgs = basicSetXmlAccessMethods(newXmlAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS, newXmlAccessMethods, newXmlAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Type</em>' attribute.
	 * @see #setContainerType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEContainerJavaAttribute_ContainerType()
	 * @model
	 * @generated
	 */
	public String getContainerType()
	{
		return containerType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getContainerType <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Type</em>' attribute.
	 * @see #getContainerType()
	 * @generated
	 */
	public void setContainerType(String newContainerType)
	{
		String oldContainerType = containerType;
		containerType = newContainerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE, oldContainerType, containerType));
	}

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEReadWriteJavaAttribute_ReadOnly()
	 * @model
	 * @generated
	 */
	public boolean isReadOnly()
	{
		return readOnly;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	public void setReadOnly(boolean newReadOnly)
	{
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * Returns the value of the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Write Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Write Only</em>' attribute.
	 * @see #setWriteOnly(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEReadWriteJavaAttribute_WriteOnly()
	 * @model
	 * @generated
	 */
	public boolean isWriteOnly()
	{
		return writeOnly;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#isWriteOnly <em>Write Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Write Only</em>' attribute.
	 * @see #isWriteOnly()
	 * @generated
	 */
	public void setWriteOnly(boolean newWriteOnly)
	{
		boolean oldWriteOnly = writeOnly;
		writeOnly = newWriteOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY, oldWriteOnly, writeOnly));
	}

	/**
	 * Returns the value of the '<em><b>Xml Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Properties</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEPropertyHolder_XmlProperties()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlProperty> getXmlProperties()
	{
		if (xmlProperties == null)
		{
			xmlProperties = new EObjectContainmentEList<EXmlProperty>(EXmlProperty.class, this, OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES);
		}
		return xmlProperties;
	}

	/**
	 * Returns the value of the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Path</em>' attribute.
	 * @see #setXmlPath(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyAttribute_XmlPath()
	 * @model
	 * @generated
	 */
	public String getXmlPath()
	{
		return xmlPath;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getXmlPath <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Path</em>' attribute.
	 * @see #getXmlPath()
	 * @generated
	 */
	public void setXmlPath(String newXmlPath)
	{
		String oldXmlPath = xmlPath;
		xmlPath = newXmlPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ANY_ATTRIBUTE__XML_PATH, oldXmlPath, xmlPath));
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
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS:
				return basicSetXmlAccessMethods(null, msgs);
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES:
				return ((InternalEList<?>)getXmlProperties()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS:
				return getXmlAccessMethods();
			case OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE:
				return getContainerType();
			case OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY:
				return isReadOnly();
			case OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY:
				return isWriteOnly();
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES:
				return getXmlProperties();
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PATH:
				return getXmlPath();
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
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)newValue);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE:
				setContainerType((String)newValue);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY:
				setWriteOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES:
				getXmlProperties().clear();
				getXmlProperties().addAll((Collection<? extends EXmlProperty>)newValue);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PATH:
				setXmlPath((String)newValue);
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
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)null);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE:
				setContainerType(CONTAINER_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY:
				setWriteOnly(WRITE_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES:
				getXmlProperties().clear();
				return;
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PATH:
				setXmlPath(XML_PATH_EDEFAULT);
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
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS:
				return xmlAccessMethods != null;
			case OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE:
				return CONTAINER_TYPE_EDEFAULT == null ? containerType != null : !CONTAINER_TYPE_EDEFAULT.equals(containerType);
			case OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY:
				return writeOnly != WRITE_ONLY_EDEFAULT;
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES:
				return xmlProperties != null && !xmlProperties.isEmpty();
			case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PATH:
				return XML_PATH_EDEFAULT == null ? xmlPath != null : !XML_PATH_EDEFAULT.equals(xmlPath);
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
		if (baseClass == EAccessibleJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES: return OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES;
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
		if (baseClass == EAccessibleJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.EXML_ANY_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY: return OxmPackage.EXML_ANY_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EXML_ANY_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES: return OxmPackage.EXML_ANY_ATTRIBUTE__XML_PROPERTIES;
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
		result.append(" (containerType: ");
		result.append(containerType);
		result.append(", readOnly: ");
		result.append(readOnly);
		result.append(", writeOnly: ");
		result.append(writeOnly);
		result.append(", xmlPath: ");
		result.append(xmlPath);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** misc *****
	
	@Override
	public String getElementName() {
		return Oxm.XML_ANY_ATTRIBUTE;
	}
	
	
	// ***** translators *****
	
	static class XmlAnyAttributeTranslator
			extends AbstractJavaAttributeTranslator {
		
		XmlAnyAttributeTranslator(String domPathAndName, EStructuralFeature eStructuralFeature) {
			super(domPathAndName, eStructuralFeature, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
				buildJavaAttributeTranslator(),
				buildXmlAccessorTypeTranslator()
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlAnyAttribute();
		}
	}
}

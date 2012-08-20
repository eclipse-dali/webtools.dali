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
 * A representation of the model object '<em><b>EXml Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isCdata <em>Cdata</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlValue()
 * @model kind="class"
 * @generated
 */
public class EXmlValue extends EJavaAttribute implements EAccessibleJavaAttribute, EAdaptableJavaAttribute, EContainerJavaAttribute, EReadWriteJavaAttribute, ETypedJavaAttribute, EPropertyHolder
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
	 * The cached value of the '{@link #getXmlJavaTypeAdapter() <em>Xml Java Type Adapter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlJavaTypeAdapter()
	 * @generated
	 * @ordered
	 */
	protected EXmlJavaTypeAdapter xmlJavaTypeAdapter;
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;
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
	 * The default value of the '{@link #isCdata() <em>Cdata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCdata()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CDATA_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isCdata() <em>Cdata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCdata()
	 * @generated
	 * @ordered
	 */
	protected boolean cdata = CDATA_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlAbstractNullPolicy() <em>Xml Abstract Null Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAbstractNullPolicy()
	 * @generated
	 * @ordered
	 */
	protected EAbstractXmlNullPolicy xmlAbstractNullPolicy;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlValue()
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
		return OxmPackage.Literals.EXML_VALUE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_ACCESS_METHODS, oldXmlAccessMethods, newXmlAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlAccessMethods <em>Xml Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlAccessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_ACCESS_METHODS, null, msgs);
			if (newXmlAccessMethods != null)
				msgs = ((InternalEObject)newXmlAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_ACCESS_METHODS, null, msgs);
			msgs = basicSetXmlAccessMethods(newXmlAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_ACCESS_METHODS, newXmlAccessMethods, newXmlAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Java Type Adapter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Java Type Adapter</em>' containment reference.
	 * @see #setXmlJavaTypeAdapter(EXmlJavaTypeAdapter)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAdaptableJavaAttribute_XmlJavaTypeAdapter()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlJavaTypeAdapter getXmlJavaTypeAdapter()
	{
		return xmlJavaTypeAdapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlJavaTypeAdapter(EXmlJavaTypeAdapter newXmlJavaTypeAdapter, NotificationChain msgs)
	{
		EXmlJavaTypeAdapter oldXmlJavaTypeAdapter = xmlJavaTypeAdapter;
		xmlJavaTypeAdapter = newXmlJavaTypeAdapter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER, oldXmlJavaTypeAdapter, newXmlJavaTypeAdapter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Java Type Adapter</em>' containment reference.
	 * @see #getXmlJavaTypeAdapter()
	 * @generated
	 */
	public void setXmlJavaTypeAdapter(EXmlJavaTypeAdapter newXmlJavaTypeAdapter)
	{
		if (newXmlJavaTypeAdapter != xmlJavaTypeAdapter)
		{
			NotificationChain msgs = null;
			if (xmlJavaTypeAdapter != null)
				msgs = ((InternalEObject)xmlJavaTypeAdapter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			if (newXmlJavaTypeAdapter != null)
				msgs = ((InternalEObject)newXmlJavaTypeAdapter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			msgs = basicSetXmlJavaTypeAdapter(newXmlJavaTypeAdapter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER, newXmlJavaTypeAdapter, newXmlJavaTypeAdapter));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getContainerType <em>Container Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__CONTAINER_TYPE, oldContainerType, containerType));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isReadOnly <em>Read Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__READ_ONLY, oldReadOnly, readOnly));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isWriteOnly <em>Write Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__WRITE_ONLY, oldWriteOnly, writeOnly));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute_Type()
	 * @model
	 * @generated
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	public void setType(String newType)
	{
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__TYPE, oldType, type));
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
			xmlProperties = new EObjectContainmentEList<EXmlProperty>(EXmlProperty.class, this, OxmPackage.EXML_VALUE__XML_PROPERTIES);
		}
		return xmlProperties;
	}

	/**
	 * Returns the value of the '<em><b>Cdata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdata</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cdata</em>' attribute.
	 * @see #setCdata(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlValue_Cdata()
	 * @model
	 * @generated
	 */
	public boolean isCdata()
	{
		return cdata;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isCdata <em>Cdata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdata</em>' attribute.
	 * @see #isCdata()
	 * @generated
	 */
	public void setCdata(boolean newCdata)
	{
		boolean oldCdata = cdata;
		cdata = newCdata;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__CDATA, oldCdata, cdata));
	}

	/**
	 * Returns the value of the '<em><b>Xml Abstract Null Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Abstract Null Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Abstract Null Policy</em>' containment reference.
	 * @see #setXmlAbstractNullPolicy(EAbstractXmlNullPolicy)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlValue_XmlAbstractNullPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public EAbstractXmlNullPolicy getXmlAbstractNullPolicy()
	{
		return xmlAbstractNullPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlAbstractNullPolicy(EAbstractXmlNullPolicy newXmlAbstractNullPolicy, NotificationChain msgs)
	{
		EAbstractXmlNullPolicy oldXmlAbstractNullPolicy = xmlAbstractNullPolicy;
		xmlAbstractNullPolicy = newXmlAbstractNullPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY, oldXmlAbstractNullPolicy, newXmlAbstractNullPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Abstract Null Policy</em>' containment reference.
	 * @see #getXmlAbstractNullPolicy()
	 * @generated
	 */
	public void setXmlAbstractNullPolicy(EAbstractXmlNullPolicy newXmlAbstractNullPolicy)
	{
		if (newXmlAbstractNullPolicy != xmlAbstractNullPolicy)
		{
			NotificationChain msgs = null;
			if (xmlAbstractNullPolicy != null)
				msgs = ((InternalEObject)xmlAbstractNullPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY, null, msgs);
			if (newXmlAbstractNullPolicy != null)
				msgs = ((InternalEObject)newXmlAbstractNullPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY, null, msgs);
			msgs = basicSetXmlAbstractNullPolicy(newXmlAbstractNullPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY, newXmlAbstractNullPolicy, newXmlAbstractNullPolicy));
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
			case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS:
				return basicSetXmlAccessMethods(null, msgs);
			case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER:
				return basicSetXmlJavaTypeAdapter(null, msgs);
			case OxmPackage.EXML_VALUE__XML_PROPERTIES:
				return ((InternalEList<?>)getXmlProperties()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY:
				return basicSetXmlAbstractNullPolicy(null, msgs);
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
			case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS:
				return getXmlAccessMethods();
			case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER:
				return getXmlJavaTypeAdapter();
			case OxmPackage.EXML_VALUE__CONTAINER_TYPE:
				return getContainerType();
			case OxmPackage.EXML_VALUE__READ_ONLY:
				return isReadOnly();
			case OxmPackage.EXML_VALUE__WRITE_ONLY:
				return isWriteOnly();
			case OxmPackage.EXML_VALUE__TYPE:
				return getType();
			case OxmPackage.EXML_VALUE__XML_PROPERTIES:
				return getXmlProperties();
			case OxmPackage.EXML_VALUE__CDATA:
				return isCdata();
			case OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY:
				return getXmlAbstractNullPolicy();
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
			case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)newValue);
				return;
			case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)newValue);
				return;
			case OxmPackage.EXML_VALUE__CONTAINER_TYPE:
				setContainerType((String)newValue);
				return;
			case OxmPackage.EXML_VALUE__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_VALUE__WRITE_ONLY:
				setWriteOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_VALUE__TYPE:
				setType((String)newValue);
				return;
			case OxmPackage.EXML_VALUE__XML_PROPERTIES:
				getXmlProperties().clear();
				getXmlProperties().addAll((Collection<? extends EXmlProperty>)newValue);
				return;
			case OxmPackage.EXML_VALUE__CDATA:
				setCdata((Boolean)newValue);
				return;
			case OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY:
				setXmlAbstractNullPolicy((EAbstractXmlNullPolicy)newValue);
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
			case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)null);
				return;
			case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)null);
				return;
			case OxmPackage.EXML_VALUE__CONTAINER_TYPE:
				setContainerType(CONTAINER_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_VALUE__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_VALUE__WRITE_ONLY:
				setWriteOnly(WRITE_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_VALUE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_VALUE__XML_PROPERTIES:
				getXmlProperties().clear();
				return;
			case OxmPackage.EXML_VALUE__CDATA:
				setCdata(CDATA_EDEFAULT);
				return;
			case OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY:
				setXmlAbstractNullPolicy((EAbstractXmlNullPolicy)null);
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
			case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS:
				return xmlAccessMethods != null;
			case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER:
				return xmlJavaTypeAdapter != null;
			case OxmPackage.EXML_VALUE__CONTAINER_TYPE:
				return CONTAINER_TYPE_EDEFAULT == null ? containerType != null : !CONTAINER_TYPE_EDEFAULT.equals(containerType);
			case OxmPackage.EXML_VALUE__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case OxmPackage.EXML_VALUE__WRITE_ONLY:
				return writeOnly != WRITE_ONLY_EDEFAULT;
			case OxmPackage.EXML_VALUE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case OxmPackage.EXML_VALUE__XML_PROPERTIES:
				return xmlProperties != null && !xmlProperties.isEmpty();
			case OxmPackage.EXML_VALUE__CDATA:
				return cdata != CDATA_EDEFAULT;
			case OxmPackage.EXML_VALUE__XML_ABSTRACT_NULL_POLICY:
				return xmlAbstractNullPolicy != null;
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
				case OxmPackage.EXML_VALUE__XML_ACCESS_METHODS: return OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_VALUE__CONTAINER_TYPE: return OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_VALUE__READ_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EXML_VALUE__WRITE_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_VALUE__TYPE: return OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_VALUE__XML_PROPERTIES: return OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES;
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
				case OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EXML_VALUE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EXML_VALUE__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.EXML_VALUE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY: return OxmPackage.EXML_VALUE__READ_ONLY;
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EXML_VALUE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE: return OxmPackage.EXML_VALUE__TYPE;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES: return OxmPackage.EXML_VALUE__XML_PROPERTIES;
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
		result.append(", type: ");
		result.append(type);
		result.append(", cdata: ");
		result.append(cdata);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class XmlValueTranslator
			extends AbstractJavaAttributeTranslator {
		
		XmlValueTranslator(String domPathAndName, EStructuralFeature eStructuralFeature) {
			super(domPathAndName, eStructuralFeature, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlValue();
		}
	}
}

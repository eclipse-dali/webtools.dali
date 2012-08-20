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
 * A representation of the model object '<em><b>EXml Element Refs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isXmlMixed <em>Xml Mixed</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementRefs <em>Xml Element Refs</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementWrapper <em>Xml Element Wrapper</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs()
 * @model kind="class"
 * @generated
 */
public class EXmlElementRefs extends EJavaAttribute implements EAccessibleJavaAttribute, EAdaptableJavaAttribute, EReadWriteJavaAttribute, EPropertyHolder
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
	 * The default value of the '{@link #isXmlMixed() <em>Xml Mixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMixed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_MIXED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlMixed() <em>Xml Mixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMixed()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlMixed = XML_MIXED_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlElementRefs() <em>Xml Element Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlElementRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlElementRef> xmlElementRefs;
	/**
	 * The cached value of the '{@link #getXmlElementWrapper() <em>Xml Element Wrapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlElementWrapper()
	 * @generated
	 * @ordered
	 */
	protected EXmlElementWrapper xmlElementWrapper;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlElementRefs()
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
		return OxmPackage.Literals.EXML_ELEMENT_REFS;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS, oldXmlAccessMethods, newXmlAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlAccessMethods <em>Xml Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlAccessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS, null, msgs);
			if (newXmlAccessMethods != null)
				msgs = ((InternalEObject)newXmlAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS, null, msgs);
			msgs = basicSetXmlAccessMethods(newXmlAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS, newXmlAccessMethods, newXmlAccessMethods));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER, oldXmlJavaTypeAdapter, newXmlJavaTypeAdapter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlJavaTypeAdapter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER, null, msgs);
			if (newXmlJavaTypeAdapter != null)
				msgs = ((InternalEObject)newXmlJavaTypeAdapter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER, null, msgs);
			msgs = basicSetXmlJavaTypeAdapter(newXmlJavaTypeAdapter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER, newXmlJavaTypeAdapter, newXmlJavaTypeAdapter));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isReadOnly <em>Read Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__READ_ONLY, oldReadOnly, readOnly));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isWriteOnly <em>Write Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY, oldWriteOnly, writeOnly));
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
			xmlProperties = new EObjectContainmentEList<EXmlProperty>(EXmlProperty.class, this, OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES);
		}
		return xmlProperties;
	}

	/**
	 * Returns the value of the '<em><b>Xml Mixed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mixed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mixed</em>' attribute.
	 * @see #setXmlMixed(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs_XmlMixed()
	 * @model
	 * @generated
	 */
	public boolean isXmlMixed()
	{
		return xmlMixed;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isXmlMixed <em>Xml Mixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mixed</em>' attribute.
	 * @see #isXmlMixed()
	 * @generated
	 */
	public void setXmlMixed(boolean newXmlMixed)
	{
		boolean oldXmlMixed = xmlMixed;
		xmlMixed = newXmlMixed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_MIXED, oldXmlMixed, xmlMixed));
	}

	/**
	 * Returns the value of the '<em><b>Xml Element Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Element Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Element Refs</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs_XmlElementRefs()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlElementRef> getXmlElementRefs()
	{
		if (xmlElementRefs == null)
		{
			xmlElementRefs = new EObjectContainmentEList<EXmlElementRef>(EXmlElementRef.class, this, OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS);
		}
		return xmlElementRefs;
	}

	/**
	 * Returns the value of the '<em><b>Xml Element Wrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Element Wrapper</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Element Wrapper</em>' containment reference.
	 * @see #setXmlElementWrapper(EXmlElementWrapper)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs_XmlElementWrapper()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlElementWrapper getXmlElementWrapper()
	{
		return xmlElementWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlElementWrapper(EXmlElementWrapper newXmlElementWrapper, NotificationChain msgs)
	{
		EXmlElementWrapper oldXmlElementWrapper = xmlElementWrapper;
		xmlElementWrapper = newXmlElementWrapper;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER, oldXmlElementWrapper, newXmlElementWrapper);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementWrapper <em>Xml Element Wrapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Element Wrapper</em>' containment reference.
	 * @see #getXmlElementWrapper()
	 * @generated
	 */
	public void setXmlElementWrapper(EXmlElementWrapper newXmlElementWrapper)
	{
		if (newXmlElementWrapper != xmlElementWrapper)
		{
			NotificationChain msgs = null;
			if (xmlElementWrapper != null)
				msgs = ((InternalEObject)xmlElementWrapper).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER, null, msgs);
			if (newXmlElementWrapper != null)
				msgs = ((InternalEObject)newXmlElementWrapper).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER, null, msgs);
			msgs = basicSetXmlElementWrapper(newXmlElementWrapper, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER, newXmlElementWrapper, newXmlElementWrapper));
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
			case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS:
				return basicSetXmlAccessMethods(null, msgs);
			case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER:
				return basicSetXmlJavaTypeAdapter(null, msgs);
			case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES:
				return ((InternalEList<?>)getXmlProperties()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS:
				return ((InternalEList<?>)getXmlElementRefs()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER:
				return basicSetXmlElementWrapper(null, msgs);
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
			case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS:
				return getXmlAccessMethods();
			case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER:
				return getXmlJavaTypeAdapter();
			case OxmPackage.EXML_ELEMENT_REFS__READ_ONLY:
				return isReadOnly();
			case OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY:
				return isWriteOnly();
			case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES:
				return getXmlProperties();
			case OxmPackage.EXML_ELEMENT_REFS__XML_MIXED:
				return isXmlMixed();
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS:
				return getXmlElementRefs();
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER:
				return getXmlElementWrapper();
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
			case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY:
				setWriteOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES:
				getXmlProperties().clear();
				getXmlProperties().addAll((Collection<? extends EXmlProperty>)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_MIXED:
				setXmlMixed((Boolean)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS:
				getXmlElementRefs().clear();
				getXmlElementRefs().addAll((Collection<? extends EXmlElementRef>)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER:
				setXmlElementWrapper((EXmlElementWrapper)newValue);
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
			case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)null);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)null);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY:
				setWriteOnly(WRITE_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES:
				getXmlProperties().clear();
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_MIXED:
				setXmlMixed(XML_MIXED_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS:
				getXmlElementRefs().clear();
				return;
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER:
				setXmlElementWrapper((EXmlElementWrapper)null);
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
			case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS:
				return xmlAccessMethods != null;
			case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER:
				return xmlJavaTypeAdapter != null;
			case OxmPackage.EXML_ELEMENT_REFS__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY:
				return writeOnly != WRITE_ONLY_EDEFAULT;
			case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES:
				return xmlProperties != null && !xmlProperties.isEmpty();
			case OxmPackage.EXML_ELEMENT_REFS__XML_MIXED:
				return xmlMixed != XML_MIXED_EDEFAULT;
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_REFS:
				return xmlElementRefs != null && !xmlElementRefs.isEmpty();
			case OxmPackage.EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER:
				return xmlElementWrapper != null;
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
				case OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS: return OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ELEMENT_REFS__READ_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES: return OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES;
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
				case OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EXML_ELEMENT_REFS__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY: return OxmPackage.EXML_ELEMENT_REFS__READ_ONLY;
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EXML_ELEMENT_REFS__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES: return OxmPackage.EXML_ELEMENT_REFS__XML_PROPERTIES;
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
		result.append(" (readOnly: ");
		result.append(readOnly);
		result.append(", writeOnly: ");
		result.append(writeOnly);
		result.append(", xmlMixed: ");
		result.append(xmlMixed);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class XmlElementRefsTranslator
			extends AbstractJavaAttributeTranslator {
		
		XmlElementRefsTranslator(String domPathAndName, EStructuralFeature eStructuralFeature) {
			super(domPathAndName, eStructuralFeature, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlElementRefs();
		}
	}
} 

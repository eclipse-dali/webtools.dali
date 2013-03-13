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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isRequired <em>Required</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlId <em>Xml Id</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlIdRef <em>Xml Id Ref</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlKey <em>Xml Key</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlList <em>Xml List</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlAttachmentRef <em>Xml Attachment Ref</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlMimeType <em>Xml Mime Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlPath <em>Xml Path</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlSchemaType <em>Xml Schema Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute()
 * @model kind="class"
 * @generated
 */
public class EXmlAttribute extends EJavaAttribute implements EAccessibleJavaAttribute, EAdaptableJavaAttribute, EContainerJavaAttribute, EReadWriteJavaAttribute, ETypedJavaAttribute, EPropertyHolder
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
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;
	/**
	 * The default value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REQUIRED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequired()
	 * @generated
	 * @ordered
	 */
	protected boolean required = REQUIRED_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlId() <em>Xml Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlId()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_ID_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlId() <em>Xml Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlId()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlId = XML_ID_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlIdRef() <em>Xml Id Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlIdRef()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_ID_REF_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlIdRef() <em>Xml Id Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlIdRef()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlIdRef = XML_ID_REF_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlKey() <em>Xml Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlKey()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_KEY_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlKey() <em>Xml Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlKey()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlKey = XML_KEY_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlList() <em>Xml List</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlList()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_LIST_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlList() <em>Xml List</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlList()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlList = XML_LIST_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlInlineBinaryData() <em>Xml Inline Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlInlineBinaryData()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_INLINE_BINARY_DATA_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlInlineBinaryData() <em>Xml Inline Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlInlineBinaryData()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlInlineBinaryData = XML_INLINE_BINARY_DATA_EDEFAULT;
	/**
	 * The default value of the '{@link #isXmlAttachmentRef() <em>Xml Attachment Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlAttachmentRef()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_ATTACHMENT_REF_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isXmlAttachmentRef() <em>Xml Attachment Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlAttachmentRef()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlAttachmentRef = XML_ATTACHMENT_REF_EDEFAULT;
	/**
	 * The default value of the '{@link #getXmlMimeType() <em>Xml Mime Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlMimeType()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_MIME_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getXmlMimeType() <em>Xml Mime Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlMimeType()
	 * @generated
	 * @ordered
	 */
	protected String xmlMimeType = XML_MIME_TYPE_EDEFAULT;
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
	 * The cached value of the '{@link #getXmlAbstractNullPolicy() <em>Xml Abstract Null Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAbstractNullPolicy()
	 * @generated
	 * @ordered
	 */
	protected EAbstractXmlNullPolicy xmlAbstractNullPolicy;

	/**
	 * The cached value of the '{@link #getXmlSchemaType() <em>Xml Schema Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlSchemaType()
	 * @generated
	 * @ordered
	 */
	protected EXmlSchemaType xmlSchemaType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlAttribute()
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
		return OxmPackage.Literals.EXML_ATTRIBUTE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS, oldXmlAccessMethods, newXmlAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlAccessMethods <em>Xml Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlAccessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS, null, msgs);
			if (newXmlAccessMethods != null)
				msgs = ((InternalEObject)newXmlAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS, null, msgs);
			msgs = basicSetXmlAccessMethods(newXmlAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS, newXmlAccessMethods, newXmlAccessMethods));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER, oldXmlJavaTypeAdapter, newXmlJavaTypeAdapter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlJavaTypeAdapter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			if (newXmlJavaTypeAdapter != null)
				msgs = ((InternalEObject)newXmlJavaTypeAdapter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			msgs = basicSetXmlJavaTypeAdapter(newXmlJavaTypeAdapter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER, newXmlJavaTypeAdapter, newXmlJavaTypeAdapter));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getContainerType <em>Container Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE, oldContainerType, containerType));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isReadOnly <em>Read Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__READ_ONLY, oldReadOnly, readOnly));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isWriteOnly <em>Write Only</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY, oldWriteOnly, writeOnly));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getType <em>Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__TYPE, oldType, type));
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
			xmlProperties = new EObjectContainmentEList<EXmlProperty>(EXmlProperty.class, this, OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES);
		}
		return xmlProperties;
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_Namespace()
	 * @model
	 * @generated
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	public void setNamespace(String newNamespace)
	{
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * Returns the value of the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' attribute.
	 * @see #setRequired(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_Required()
	 * @model
	 * @generated
	 */
	public boolean isRequired()
	{
		return required;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isRequired <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' attribute.
	 * @see #isRequired()
	 * @generated
	 */
	public void setRequired(boolean newRequired)
	{
		boolean oldRequired = required;
		required = newRequired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__REQUIRED, oldRequired, required));
	}

	/**
	 * Returns the value of the '<em><b>Xml Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Id</em>' attribute.
	 * @see #setXmlId(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlId()
	 * @model
	 * @generated
	 */
	public boolean isXmlId()
	{
		return xmlId;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlId <em>Xml Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Id</em>' attribute.
	 * @see #isXmlId()
	 * @generated
	 */
	public void setXmlId(boolean newXmlId)
	{
		boolean oldXmlId = xmlId;
		xmlId = newXmlId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ID, oldXmlId, xmlId));
	}

	/**
	 * Returns the value of the '<em><b>Xml Id Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Id Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Id Ref</em>' attribute.
	 * @see #setXmlIdRef(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlIdRef()
	 * @model
	 * @generated
	 */
	public boolean isXmlIdRef()
	{
		return xmlIdRef;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlIdRef <em>Xml Id Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Id Ref</em>' attribute.
	 * @see #isXmlIdRef()
	 * @generated
	 */
	public void setXmlIdRef(boolean newXmlIdRef)
	{
		boolean oldXmlIdRef = xmlIdRef;
		xmlIdRef = newXmlIdRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ID_REF, oldXmlIdRef, xmlIdRef));
	}

	/**
	 * Returns the value of the '<em><b>Xml Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Key</em>' attribute.
	 * @see #setXmlKey(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlKey()
	 * @model
	 * @generated
	 */
	public boolean isXmlKey()
	{
		return xmlKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlKey <em>Xml Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Key</em>' attribute.
	 * @see #isXmlKey()
	 * @generated
	 */
	public void setXmlKey(boolean newXmlKey)
	{
		boolean oldXmlKey = xmlKey;
		xmlKey = newXmlKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_KEY, oldXmlKey, xmlKey));
	}

	/**
	 * Returns the value of the '<em><b>Xml List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml List</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml List</em>' attribute.
	 * @see #setXmlList(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlList()
	 * @model
	 * @generated
	 */
	public boolean isXmlList()
	{
		return xmlList;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlList <em>Xml List</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml List</em>' attribute.
	 * @see #isXmlList()
	 * @generated
	 */
	public void setXmlList(boolean newXmlList)
	{
		boolean oldXmlList = xmlList;
		xmlList = newXmlList;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_LIST, oldXmlList, xmlList));
	}

	/**
	 * Returns the value of the '<em><b>Xml Inline Binary Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Inline Binary Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Inline Binary Data</em>' attribute.
	 * @see #setXmlInlineBinaryData(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlInlineBinaryData()
	 * @model
	 * @generated
	 */
	public boolean isXmlInlineBinaryData()
	{
		return xmlInlineBinaryData;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Inline Binary Data</em>' attribute.
	 * @see #isXmlInlineBinaryData()
	 * @generated
	 */
	public void setXmlInlineBinaryData(boolean newXmlInlineBinaryData)
	{
		boolean oldXmlInlineBinaryData = xmlInlineBinaryData;
		xmlInlineBinaryData = newXmlInlineBinaryData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA, oldXmlInlineBinaryData, xmlInlineBinaryData));
	}

	/**
	 * Returns the value of the '<em><b>Xml Attachment Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Attachment Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Attachment Ref</em>' attribute.
	 * @see #setXmlAttachmentRef(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlAttachmentRef()
	 * @model
	 * @generated
	 */
	public boolean isXmlAttachmentRef()
	{
		return xmlAttachmentRef;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlAttachmentRef <em>Xml Attachment Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Attachment Ref</em>' attribute.
	 * @see #isXmlAttachmentRef()
	 * @generated
	 */
	public void setXmlAttachmentRef(boolean newXmlAttachmentRef)
	{
		boolean oldXmlAttachmentRef = xmlAttachmentRef;
		xmlAttachmentRef = newXmlAttachmentRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ATTACHMENT_REF, oldXmlAttachmentRef, xmlAttachmentRef));
	}

	/**
	 * Returns the value of the '<em><b>Xml Mime Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mime Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mime Type</em>' attribute.
	 * @see #setXmlMimeType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlMimeType()
	 * @model
	 * @generated
	 */
	public String getXmlMimeType()
	{
		return xmlMimeType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlMimeType <em>Xml Mime Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mime Type</em>' attribute.
	 * @see #getXmlMimeType()
	 * @generated
	 */
	public void setXmlMimeType(String newXmlMimeType)
	{
		String oldXmlMimeType = xmlMimeType;
		xmlMimeType = newXmlMimeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_MIME_TYPE, oldXmlMimeType, xmlMimeType));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlPath()
	 * @model
	 * @generated
	 */
	public String getXmlPath()
	{
		return xmlPath;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlPath <em>Xml Path</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_PATH, oldXmlPath, xmlPath));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlAbstractNullPolicy()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY, oldXmlAbstractNullPolicy, newXmlAbstractNullPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlAbstractNullPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY, null, msgs);
			if (newXmlAbstractNullPolicy != null)
				msgs = ((InternalEObject)newXmlAbstractNullPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY, null, msgs);
			msgs = basicSetXmlAbstractNullPolicy(newXmlAbstractNullPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY, newXmlAbstractNullPolicy, newXmlAbstractNullPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Xml Schema Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Schema Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Schema Type</em>' containment reference.
	 * @see #setXmlSchemaType(EXmlSchemaType)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute_XmlSchemaType()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlSchemaType getXmlSchemaType()
	{
		return xmlSchemaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlSchemaType(EXmlSchemaType newXmlSchemaType, NotificationChain msgs)
	{
		EXmlSchemaType oldXmlSchemaType = xmlSchemaType;
		xmlSchemaType = newXmlSchemaType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE, oldXmlSchemaType, newXmlSchemaType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlSchemaType <em>Xml Schema Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Schema Type</em>' containment reference.
	 * @see #getXmlSchemaType()
	 * @generated
	 */
	public void setXmlSchemaType(EXmlSchemaType newXmlSchemaType)
	{
		if (newXmlSchemaType != xmlSchemaType)
		{
			NotificationChain msgs = null;
			if (xmlSchemaType != null)
				msgs = ((InternalEObject)xmlSchemaType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE, null, msgs);
			if (newXmlSchemaType != null)
				msgs = ((InternalEObject)newXmlSchemaType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE, null, msgs);
			msgs = basicSetXmlSchemaType(newXmlSchemaType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE, newXmlSchemaType, newXmlSchemaType));
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
			case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS:
				return basicSetXmlAccessMethods(null, msgs);
			case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER:
				return basicSetXmlJavaTypeAdapter(null, msgs);
			case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES:
				return ((InternalEList<?>)getXmlProperties()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY:
				return basicSetXmlAbstractNullPolicy(null, msgs);
			case OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE:
				return basicSetXmlSchemaType(null, msgs);
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
			case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS:
				return getXmlAccessMethods();
			case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER:
				return getXmlJavaTypeAdapter();
			case OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE:
				return getContainerType();
			case OxmPackage.EXML_ATTRIBUTE__READ_ONLY:
				return isReadOnly();
			case OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY:
				return isWriteOnly();
			case OxmPackage.EXML_ATTRIBUTE__TYPE:
				return getType();
			case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES:
				return getXmlProperties();
			case OxmPackage.EXML_ATTRIBUTE__NAME:
				return getName();
			case OxmPackage.EXML_ATTRIBUTE__NAMESPACE:
				return getNamespace();
			case OxmPackage.EXML_ATTRIBUTE__REQUIRED:
				return isRequired();
			case OxmPackage.EXML_ATTRIBUTE__XML_ID:
				return isXmlId();
			case OxmPackage.EXML_ATTRIBUTE__XML_ID_REF:
				return isXmlIdRef();
			case OxmPackage.EXML_ATTRIBUTE__XML_KEY:
				return isXmlKey();
			case OxmPackage.EXML_ATTRIBUTE__XML_LIST:
				return isXmlList();
			case OxmPackage.EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA:
				return isXmlInlineBinaryData();
			case OxmPackage.EXML_ATTRIBUTE__XML_ATTACHMENT_REF:
				return isXmlAttachmentRef();
			case OxmPackage.EXML_ATTRIBUTE__XML_MIME_TYPE:
				return getXmlMimeType();
			case OxmPackage.EXML_ATTRIBUTE__XML_PATH:
				return getXmlPath();
			case OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY:
				return getXmlAbstractNullPolicy();
			case OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE:
				return getXmlSchemaType();
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
			case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE:
				setContainerType((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY:
				setWriteOnly((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__TYPE:
				setType((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES:
				getXmlProperties().clear();
				getXmlProperties().addAll((Collection<? extends EXmlProperty>)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__NAME:
				setName((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__REQUIRED:
				setRequired((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID:
				setXmlId((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID_REF:
				setXmlIdRef((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_KEY:
				setXmlKey((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_LIST:
				setXmlList((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA:
				setXmlInlineBinaryData((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ATTACHMENT_REF:
				setXmlAttachmentRef((Boolean)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_MIME_TYPE:
				setXmlMimeType((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_PATH:
				setXmlPath((String)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY:
				setXmlAbstractNullPolicy((EAbstractXmlNullPolicy)newValue);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE:
				setXmlSchemaType((EXmlSchemaType)newValue);
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
			case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS:
				setXmlAccessMethods((EXmlAccessMethods)null);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)null);
				return;
			case OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE:
				setContainerType(CONTAINER_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY:
				setWriteOnly(WRITE_ONLY_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES:
				getXmlProperties().clear();
				return;
			case OxmPackage.EXML_ATTRIBUTE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID:
				setXmlId(XML_ID_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID_REF:
				setXmlIdRef(XML_ID_REF_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_KEY:
				setXmlKey(XML_KEY_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_LIST:
				setXmlList(XML_LIST_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA:
				setXmlInlineBinaryData(XML_INLINE_BINARY_DATA_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ATTACHMENT_REF:
				setXmlAttachmentRef(XML_ATTACHMENT_REF_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_MIME_TYPE:
				setXmlMimeType(XML_MIME_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_PATH:
				setXmlPath(XML_PATH_EDEFAULT);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY:
				setXmlAbstractNullPolicy((EAbstractXmlNullPolicy)null);
				return;
			case OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE:
				setXmlSchemaType((EXmlSchemaType)null);
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
			case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS:
				return xmlAccessMethods != null;
			case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER:
				return xmlJavaTypeAdapter != null;
			case OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE:
				return CONTAINER_TYPE_EDEFAULT == null ? containerType != null : !CONTAINER_TYPE_EDEFAULT.equals(containerType);
			case OxmPackage.EXML_ATTRIBUTE__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY:
				return writeOnly != WRITE_ONLY_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES:
				return xmlProperties != null && !xmlProperties.isEmpty();
			case OxmPackage.EXML_ATTRIBUTE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OxmPackage.EXML_ATTRIBUTE__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case OxmPackage.EXML_ATTRIBUTE__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID:
				return xmlId != XML_ID_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_ID_REF:
				return xmlIdRef != XML_ID_REF_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_KEY:
				return xmlKey != XML_KEY_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_LIST:
				return xmlList != XML_LIST_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA:
				return xmlInlineBinaryData != XML_INLINE_BINARY_DATA_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_ATTACHMENT_REF:
				return xmlAttachmentRef != XML_ATTACHMENT_REF_EDEFAULT;
			case OxmPackage.EXML_ATTRIBUTE__XML_MIME_TYPE:
				return XML_MIME_TYPE_EDEFAULT == null ? xmlMimeType != null : !XML_MIME_TYPE_EDEFAULT.equals(xmlMimeType);
			case OxmPackage.EXML_ATTRIBUTE__XML_PATH:
				return XML_PATH_EDEFAULT == null ? xmlPath != null : !XML_PATH_EDEFAULT.equals(xmlPath);
			case OxmPackage.EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY:
				return xmlAbstractNullPolicy != null;
			case OxmPackage.EXML_ATTRIBUTE__XML_SCHEMA_TYPE:
				return xmlSchemaType != null;
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
				case OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ATTRIBUTE__READ_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ATTRIBUTE__TYPE: return OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES: return OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES;
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
				case OxmPackage.EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS: return OxmPackage.EXML_ATTRIBUTE__XML_ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == EAdaptableJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER: return OxmPackage.EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER;
				default: return -1;
			}
		}
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.EXML_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == EReadWriteJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY: return OxmPackage.EXML_ATTRIBUTE__READ_ONLY;
				case OxmPackage.EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY: return OxmPackage.EXML_ATTRIBUTE__WRITE_ONLY;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE: return OxmPackage.EXML_ATTRIBUTE__TYPE;
				default: return -1;
			}
		}
		if (baseClass == EPropertyHolder.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES: return OxmPackage.EXML_ATTRIBUTE__XML_PROPERTIES;
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
		result.append(", name: ");
		result.append(name);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(", required: ");
		result.append(required);
		result.append(", xmlId: ");
		result.append(xmlId);
		result.append(", xmlIdRef: ");
		result.append(xmlIdRef);
		result.append(", xmlKey: ");
		result.append(xmlKey);
		result.append(", xmlList: ");
		result.append(xmlList);
		result.append(", xmlInlineBinaryData: ");
		result.append(xmlInlineBinaryData);
		result.append(", xmlAttachmentRef: ");
		result.append(xmlAttachmentRef);
		result.append(", xmlMimeType: ");
		result.append(xmlMimeType);
		result.append(", xmlPath: ");
		result.append(xmlPath);
		result.append(')');
		return result.toString();
	}


	// ***** misc *****

	@Override
	public String getElementName() {
		return Oxm.XML_ATTRIBUTE;
	}


	// ***** translators *****
	
	static class XmlAttributeTranslator
			extends AbstractJavaAttributeTranslator {
		
		XmlAttributeTranslator(String domPathAndName, EStructuralFeature eStructuralFeature) {
			super(domPathAndName, eStructuralFeature, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
				buildJavaAttributeTranslator(),
				buildXmlAccessorTypeTranslator(),
				new EAbstractXmlNullPolicy.AbstractXmlNullPolicyTranslator()
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlAttribute();
		}
	}
}

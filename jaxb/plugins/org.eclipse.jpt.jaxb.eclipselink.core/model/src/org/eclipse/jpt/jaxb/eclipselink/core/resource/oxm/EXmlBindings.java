/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Collection;
import java.util.HashMap;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleRootTranslator;
import org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin.JptJaxbEclipseLinkCorePlugin;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Bindings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorType <em>Xml Accessor Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorOrder <em>Xml Accessor Order</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlNameTransformer <em>Xml Name Transformer</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchema <em>Xml Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaType <em>Xml Schema Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaTypes <em>Xml Schema Types</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlJavaTypeAdapters <em>Xml Java Type Adapters</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlRegistries <em>Xml Registries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlEnums <em>Xml Enums</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getJavaTypes <em>Java Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings()
 * @model kind="class"
 * @generated
 */
public class EXmlBindings extends ERootObjectImpl
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EXmlAccessType XML_ACCESSOR_TYPE_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getXmlAccessorType() <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorType()
	 * @generated
	 * @ordered
	 */
	protected EXmlAccessType xmlAccessorType = XML_ACCESSOR_TYPE_EDEFAULT;
	
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EXmlAccessOrder XML_ACCESSOR_ORDER_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getXmlAccessorOrder() <em>Xml Accessor Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorOrder()
	 * @generated
	 * @ordered
	 */
	protected EXmlAccessOrder xmlAccessorOrder = XML_ACCESSOR_ORDER_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean XML_MAPPING_METADATA_COMPLETE_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected Boolean xmlMappingMetadataComplete = XML_MAPPING_METADATA_COMPLETE_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getXmlNameTransformer() <em>Xml Name Transformer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlNameTransformer()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_NAME_TRANSFORMER_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getXmlNameTransformer() <em>Xml Name Transformer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlNameTransformer()
	 * @generated
	 * @ordered
	 */
	protected String xmlNameTransformer = XML_NAME_TRANSFORMER_EDEFAULT;
	
	/**
	 * The cached value of the '{@link #getXmlSchema() <em>Xml Schema</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlSchema()
	 * @generated
	 * @ordered
	 */
	protected EXmlSchema xmlSchema;
	
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
	 * The cached value of the '{@link #getXmlSchemaTypes() <em>Xml Schema Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlSchemaTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlSchemaType> xmlSchemaTypes;
	
	/**
	 * The cached value of the '{@link #getXmlJavaTypeAdapters() <em>Xml Java Type Adapters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlJavaTypeAdapters()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlJavaTypeAdapter> xmlJavaTypeAdapters;
	
	/**
	 * The cached value of the '{@link #getXmlRegistries() <em>Xml Registries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlRegistries()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlRegistry> xmlRegistries;
	
	/**
	 * The cached value of the '{@link #getXmlEnums() <em>Xml Enums</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlEnums()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlEnum> xmlEnums;
	
	/**
	 * The cached value of the '{@link #getJavaTypes() <em>Java Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<EJavaType> javaTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlBindings()
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
		return OxmPackage.Literals.EXML_BINDINGS;
	}

	/**
	 * Returns the value of the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Accessor Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Accessor Type</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @see #setXmlAccessorType(EXmlAccessType)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlAccessorType()
	 * @model
	 * @generated
	 */
	public EXmlAccessType getXmlAccessorType()
	{
		return xmlAccessorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorType <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Accessor Type</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @see #getXmlAccessorType()
	 * @generated
	 */
	public void setXmlAccessorType(EXmlAccessType newXmlAccessorType)
	{
		EXmlAccessType oldXmlAccessorType = xmlAccessorType;
		xmlAccessorType = newXmlAccessorType == null ? XML_ACCESSOR_TYPE_EDEFAULT : newXmlAccessorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_ACCESSOR_TYPE, oldXmlAccessorType, xmlAccessorType));
	}

	/**
	 * Returns the value of the '<em><b>Xml Accessor Order</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Accessor Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Accessor Order</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder
	 * @see #setXmlAccessorOrder(EXmlAccessOrder)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlAccessorOrder()
	 * @model
	 * @generated
	 */
	public EXmlAccessOrder getXmlAccessorOrder()
	{
		return xmlAccessorOrder;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorOrder <em>Xml Accessor Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Accessor Order</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder
	 * @see #getXmlAccessorOrder()
	 * @generated
	 */
	public void setXmlAccessorOrder(EXmlAccessOrder newXmlAccessorOrder)
	{
		EXmlAccessOrder oldXmlAccessorOrder = xmlAccessorOrder;
		xmlAccessorOrder = newXmlAccessorOrder == null ? XML_ACCESSOR_ORDER_EDEFAULT : newXmlAccessorOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_ACCESSOR_ORDER, oldXmlAccessorOrder, xmlAccessorOrder));
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #setXmlMappingMetadataComplete(Boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlMappingMetadataComplete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getXmlMappingMetadataComplete()
	{
		return xmlMappingMetadataComplete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #getXmlMappingMetadataComplete()
	 * @generated
	 */
	public void setXmlMappingMetadataComplete(Boolean newXmlMappingMetadataComplete)
	{
		Boolean oldXmlMappingMetadataComplete = xmlMappingMetadataComplete;
		xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE, oldXmlMappingMetadataComplete, xmlMappingMetadataComplete));
	}

	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_PackageName()
	 * @model
	 * @generated
	 */
	public String getPackageName()
	{
		return packageName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	public void setPackageName(String newPackageName)
	{
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * Returns the value of the '<em><b>Xml Name Transformer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Name Transformer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Name Transformer</em>' attribute.
	 * @see #setXmlNameTransformer(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlNameTransformer()
	 * @model
	 * @generated
	 */
	public String getXmlNameTransformer()
	{
		return xmlNameTransformer;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlNameTransformer <em>Xml Name Transformer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Name Transformer</em>' attribute.
	 * @see #getXmlNameTransformer()
	 * @generated
	 */
	public void setXmlNameTransformer(String newXmlNameTransformer)
	{
		String oldXmlNameTransformer = xmlNameTransformer;
		xmlNameTransformer = newXmlNameTransformer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_NAME_TRANSFORMER, oldXmlNameTransformer, xmlNameTransformer));
	}

	/**
	 * Returns the value of the '<em><b>Xml Schema</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Schema</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Schema</em>' containment reference.
	 * @see #setXmlSchema(EXmlSchema)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlSchema()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlSchema getXmlSchema()
	{
		return xmlSchema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlSchema(EXmlSchema newXmlSchema, NotificationChain msgs)
	{
		EXmlSchema oldXmlSchema = xmlSchema;
		xmlSchema = newXmlSchema;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_SCHEMA, oldXmlSchema, newXmlSchema);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchema <em>Xml Schema</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Schema</em>' containment reference.
	 * @see #getXmlSchema()
	 * @generated
	 */
	public void setXmlSchema(EXmlSchema newXmlSchema)
	{
		if (newXmlSchema != xmlSchema)
		{
			NotificationChain msgs = null;
			if (xmlSchema != null)
				msgs = ((InternalEObject)xmlSchema).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_BINDINGS__XML_SCHEMA, null, msgs);
			if (newXmlSchema != null)
				msgs = ((InternalEObject)newXmlSchema).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_BINDINGS__XML_SCHEMA, null, msgs);
			msgs = basicSetXmlSchema(newXmlSchema, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_SCHEMA, newXmlSchema, newXmlSchema));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlSchemaType()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE, oldXmlSchemaType, newXmlSchemaType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaType <em>Xml Schema Type</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlSchemaType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE, null, msgs);
			if (newXmlSchemaType != null)
				msgs = ((InternalEObject)newXmlSchemaType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE, null, msgs);
			msgs = basicSetXmlSchemaType(newXmlSchemaType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE, newXmlSchemaType, newXmlSchemaType));
	}

	/**
	 * Returns the value of the '<em><b>Xml Schema Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Schema Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Schema Types</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlSchemaTypes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlSchemaType> getXmlSchemaTypes()
	{
		if (xmlSchemaTypes == null)
		{
			xmlSchemaTypes = new EObjectContainmentEList<EXmlSchemaType>(EXmlSchemaType.class, this, OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES);
		}
		return xmlSchemaTypes;
	}

	/**
	 * Returns the value of the '<em><b>Xml Java Type Adapters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Java Type Adapters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Java Type Adapters</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlJavaTypeAdapters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlJavaTypeAdapter> getXmlJavaTypeAdapters()
	{
		if (xmlJavaTypeAdapters == null)
		{
			xmlJavaTypeAdapters = new EObjectContainmentEList<EXmlJavaTypeAdapter>(EXmlJavaTypeAdapter.class, this, OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS);
		}
		return xmlJavaTypeAdapters;
	}

	/**
	 * Returns the value of the '<em><b>Xml Registries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Registries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Registries</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlRegistries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlRegistry> getXmlRegistries()
	{
		if (xmlRegistries == null)
		{
			xmlRegistries = new EObjectContainmentEList<EXmlRegistry>(EXmlRegistry.class, this, OxmPackage.EXML_BINDINGS__XML_REGISTRIES);
		}
		return xmlRegistries;
	}

	/**
	 * Returns the value of the '<em><b>Xml Enums</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Enums</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Enums</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_XmlEnums()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlEnum> getXmlEnums()
	{
		if (xmlEnums == null)
		{
			xmlEnums = new EObjectContainmentEList<EXmlEnum>(EXmlEnum.class, this, OxmPackage.EXML_BINDINGS__XML_ENUMS);
		}
		return xmlEnums;
	}

	/**
	 * Returns the value of the '<em><b>Java Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Types</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings_JavaTypes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EJavaType> getJavaTypes()
	{
		if (javaTypes == null)
		{
			javaTypes = new EObjectContainmentEList<EJavaType>(EJavaType.class, this, OxmPackage.EXML_BINDINGS__JAVA_TYPES);
		}
		return javaTypes;
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
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA:
				return basicSetXmlSchema(null, msgs);
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE:
				return basicSetXmlSchemaType(null, msgs);
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES:
				return ((InternalEList<?>)getXmlSchemaTypes()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS:
				return ((InternalEList<?>)getXmlJavaTypeAdapters()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_BINDINGS__XML_REGISTRIES:
				return ((InternalEList<?>)getXmlRegistries()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_BINDINGS__XML_ENUMS:
				return ((InternalEList<?>)getXmlEnums()).basicRemove(otherEnd, msgs);
			case OxmPackage.EXML_BINDINGS__JAVA_TYPES:
				return ((InternalEList<?>)getJavaTypes()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_TYPE:
				return getXmlAccessorType();
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_ORDER:
				return getXmlAccessorOrder();
			case OxmPackage.EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE:
				return getXmlMappingMetadataComplete();
			case OxmPackage.EXML_BINDINGS__PACKAGE_NAME:
				return getPackageName();
			case OxmPackage.EXML_BINDINGS__XML_NAME_TRANSFORMER:
				return getXmlNameTransformer();
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA:
				return getXmlSchema();
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE:
				return getXmlSchemaType();
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES:
				return getXmlSchemaTypes();
			case OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS:
				return getXmlJavaTypeAdapters();
			case OxmPackage.EXML_BINDINGS__XML_REGISTRIES:
				return getXmlRegistries();
			case OxmPackage.EXML_BINDINGS__XML_ENUMS:
				return getXmlEnums();
			case OxmPackage.EXML_BINDINGS__JAVA_TYPES:
				return getJavaTypes();
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
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_TYPE:
				setXmlAccessorType((EXmlAccessType)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_ORDER:
				setXmlAccessorOrder((EXmlAccessOrder)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete((Boolean)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_NAME_TRANSFORMER:
				setXmlNameTransformer((String)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA:
				setXmlSchema((EXmlSchema)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE:
				setXmlSchemaType((EXmlSchemaType)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES:
				getXmlSchemaTypes().clear();
				getXmlSchemaTypes().addAll((Collection<? extends EXmlSchemaType>)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS:
				getXmlJavaTypeAdapters().clear();
				getXmlJavaTypeAdapters().addAll((Collection<? extends EXmlJavaTypeAdapter>)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_REGISTRIES:
				getXmlRegistries().clear();
				getXmlRegistries().addAll((Collection<? extends EXmlRegistry>)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__XML_ENUMS:
				getXmlEnums().clear();
				getXmlEnums().addAll((Collection<? extends EXmlEnum>)newValue);
				return;
			case OxmPackage.EXML_BINDINGS__JAVA_TYPES:
				getJavaTypes().clear();
				getJavaTypes().addAll((Collection<? extends EJavaType>)newValue);
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
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_TYPE:
				setXmlAccessorType(XML_ACCESSOR_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_ORDER:
				setXmlAccessorOrder(XML_ACCESSOR_ORDER_EDEFAULT);
				return;
			case OxmPackage.EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete(XML_MAPPING_METADATA_COMPLETE_EDEFAULT);
				return;
			case OxmPackage.EXML_BINDINGS__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_BINDINGS__XML_NAME_TRANSFORMER:
				setXmlNameTransformer(XML_NAME_TRANSFORMER_EDEFAULT);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA:
				setXmlSchema((EXmlSchema)null);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE:
				setXmlSchemaType((EXmlSchemaType)null);
				return;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES:
				getXmlSchemaTypes().clear();
				return;
			case OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS:
				getXmlJavaTypeAdapters().clear();
				return;
			case OxmPackage.EXML_BINDINGS__XML_REGISTRIES:
				getXmlRegistries().clear();
				return;
			case OxmPackage.EXML_BINDINGS__XML_ENUMS:
				getXmlEnums().clear();
				return;
			case OxmPackage.EXML_BINDINGS__JAVA_TYPES:
				getJavaTypes().clear();
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
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_TYPE:
				return xmlAccessorType != XML_ACCESSOR_TYPE_EDEFAULT;
			case OxmPackage.EXML_BINDINGS__XML_ACCESSOR_ORDER:
				return xmlAccessorOrder != XML_ACCESSOR_ORDER_EDEFAULT;
			case OxmPackage.EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE:
				return XML_MAPPING_METADATA_COMPLETE_EDEFAULT == null ? xmlMappingMetadataComplete != null : !XML_MAPPING_METADATA_COMPLETE_EDEFAULT.equals(xmlMappingMetadataComplete);
			case OxmPackage.EXML_BINDINGS__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case OxmPackage.EXML_BINDINGS__XML_NAME_TRANSFORMER:
				return XML_NAME_TRANSFORMER_EDEFAULT == null ? xmlNameTransformer != null : !XML_NAME_TRANSFORMER_EDEFAULT.equals(xmlNameTransformer);
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA:
				return xmlSchema != null;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPE:
				return xmlSchemaType != null;
			case OxmPackage.EXML_BINDINGS__XML_SCHEMA_TYPES:
				return xmlSchemaTypes != null && !xmlSchemaTypes.isEmpty();
			case OxmPackage.EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS:
				return xmlJavaTypeAdapters != null && !xmlJavaTypeAdapters.isEmpty();
			case OxmPackage.EXML_BINDINGS__XML_REGISTRIES:
				return xmlRegistries != null && !xmlRegistries.isEmpty();
			case OxmPackage.EXML_BINDINGS__XML_ENUMS:
				return xmlEnums != null && !xmlEnums.isEmpty();
			case OxmPackage.EXML_BINDINGS__JAVA_TYPES:
				return javaTypes != null && !javaTypes.isEmpty();
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
		result.append(" (xmlAccessorType: ");
		result.append(xmlAccessorType);
		result.append(", xmlAccessorOrder: ");
		result.append(xmlAccessorOrder);
		result.append(", xmlMappingMetadataComplete: ");
		result.append(xmlMappingMetadataComplete);
		result.append(", packageName: ");
		result.append(packageName);
		result.append(", xmlNameTransformer: ");
		result.append(xmlNameTransformer);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** version -> schema location mapping *****
	
	private static final HashMap<String, String> SCHEMA_LOCATIONS = buildSchemaLocations();
	
	private static HashMap<String, String> buildSchemaLocations() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Oxm.SCHEMA_VERSION_2_1, Oxm.SCHEMA_LOCATION_2_1);
		map.put(Oxm.SCHEMA_VERSION_2_2, Oxm.SCHEMA_LOCATION_2_2);
		map.put(Oxm.SCHEMA_VERSION_2_3, Oxm.SCHEMA_LOCATION_2_3);
		map.put(Oxm.SCHEMA_VERSION_2_4, Oxm.SCHEMA_LOCATION_2_4);
		map.put(Oxm.SCHEMA_VERSION_2_5, Oxm.SCHEMA_LOCATION_2_5);
		return map;
	}
	
	@Override
	protected HashMap<String, String> schemaLocations() {
		return SCHEMA_LOCATIONS;
	}

	// ***** version -> namespace mapping *****
	
	private static final HashMap<String, String> NAMESPACES = buildNamespaces();
	
	private static HashMap<String, String> buildNamespaces() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Oxm.SCHEMA_VERSION_2_1, Oxm.SCHEMA_NAMESPACE);
		map.put(Oxm.SCHEMA_VERSION_2_2, Oxm.SCHEMA_NAMESPACE);
		map.put(Oxm.SCHEMA_VERSION_2_3, Oxm.SCHEMA_NAMESPACE);
		map.put(Oxm.SCHEMA_VERSION_2_4, Oxm.SCHEMA_NAMESPACE);
		map.put(Oxm.SCHEMA_VERSION_2_5, Oxm.SCHEMA_NAMESPACE);
		return map;
	}

	@Override
	protected HashMap<String, String> namespaces() {
		return NAMESPACES;
	}
	
	
	// ***** content type *****
	
	/**
	 * The content type for <code>oxm.xml</code> mapping files.
	 */
	public static final IContentType CONTENT_TYPE = JptJaxbEclipseLinkCorePlugin.instance().getContentType("oxm"); //$NON-NLS-1$
	
	
	// ***** validation *****
	
	public TextRange getPackageNameTextRange() {
		return getAttributeTextRange(Oxm.PACKAGE_NAME);
	}
	
	
	// ***** translators *****
	
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();
	
	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	
	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(Oxm.XML_BINDINGS, OxmPackage.eINSTANCE.getEXmlBindings(), buildTranslatorChildren());
	}
	
	protected static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildVersionTranslator(SCHEMA_LOCATIONS),
			buildNamespaceTranslator(),
			buildSchemaNamespaceTranslator(),
			buildSchemaLocationTranslator(SCHEMA_LOCATIONS),
			buildXmlAccessorTypeTranslator(),
			buildXmlAccessorOrderTranslator(),
			buildXmlMappingMetadataCompleteTranslator(),
			buildPackageNameTranslator(),
			EXmlSchema.buildTranslator(),
			EXmlEnum.buildTranslator(),
			EJavaType.buildTranslator()
		};
	}
	
	protected static Translator buildXmlAccessorTypeTranslator() {
		return new Translator(
			Oxm.XML_ACCESSOR_TYPE, 
			OxmPackage.eINSTANCE.getEXmlBindings_XmlAccessorType(),
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildXmlAccessorOrderTranslator() {
		return new Translator(
			Oxm.XML_ACCESSOR_ORDER, 
			OxmPackage.eINSTANCE.getEXmlBindings_XmlAccessorOrder(),
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildXmlMappingMetadataCompleteTranslator() {
		return new Translator(
			Oxm.XML_MAPPING_METADATA_COMPLETE,
			OxmPackage.eINSTANCE.getEXmlBindings_XmlMappingMetadataComplete(),
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildPackageNameTranslator() {
		return new Translator(
			Oxm.PACKAGE_NAME, 
			OxmPackage.eINSTANCE.getEXmlBindings_PackageName(), 
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
}
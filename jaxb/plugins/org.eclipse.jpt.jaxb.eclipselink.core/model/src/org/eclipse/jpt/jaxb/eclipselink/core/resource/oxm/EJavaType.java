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
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EJava Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorOrder <em>Xml Accessor Order</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorType <em>Xml Accessor Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlCustomizer <em>Xml Customizer</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorNode <em>Xml Discriminator Node</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorValue <em>Xml Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlNameTransformer <em>Xml Name Transformer</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlVirtualAccessMethods <em>Xml Virtual Access Methods</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlClassExtractor <em>Xml Class Extractor</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getJavaAttributes <em>Java Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType()
 * @model kind="class"
 * @generated
 */
public class EJavaType extends EAbstractTypeMapping implements EPropertyHolder
{
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
	 * The default value of the '{@link #getSuperType() <em>Super Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected String superType = SUPER_TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getXmlAccessorOrder() <em>Xml Accessor Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorOrder()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlAccessOrder XML_ACCESSOR_ORDER_EDEFAULT = EXmlAccessOrder.ALPHABETICAL;
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
	 * The default value of the '{@link #getXmlAccessorType() <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorType()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlAccessType XML_ACCESSOR_TYPE_EDEFAULT = EXmlAccessType.FIELD;
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
	 * The default value of the '{@link #getXmlCustomizer() <em>Xml Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlCustomizer()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_CUSTOMIZER_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getXmlCustomizer() <em>Xml Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlCustomizer()
	 * @generated
	 * @ordered
	 */
	protected String xmlCustomizer = XML_CUSTOMIZER_EDEFAULT;
	/**
	 * The default value of the '{@link #getXmlDiscriminatorNode() <em>Xml Discriminator Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlDiscriminatorNode()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_DISCRIMINATOR_NODE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getXmlDiscriminatorNode() <em>Xml Discriminator Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlDiscriminatorNode()
	 * @generated
	 * @ordered
	 */
	protected String xmlDiscriminatorNode = XML_DISCRIMINATOR_NODE_EDEFAULT;
	/**
	 * The default value of the '{@link #getXmlDiscriminatorValue() <em>Xml Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_DISCRIMINATOR_VALUE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getXmlDiscriminatorValue() <em>Xml Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String xmlDiscriminatorValue = XML_DISCRIMINATOR_VALUE_EDEFAULT;
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
	 * The cached value of the '{@link #getXmlVirtualAccessMethods() <em>Xml Virtual Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlVirtualAccessMethods()
	 * @generated
	 * @ordered
	 */
	protected EXmlVirtualAccessMethods xmlVirtualAccessMethods;
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
	 * The cached value of the '{@link #getXmlClassExtractor() <em>Xml Class Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlClassExtractor()
	 * @generated
	 * @ordered
	 */
	protected EXmlClassExtractor xmlClassExtractor;
	/**
	 * The cached value of the '{@link #getJavaAttributes() <em>Java Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<EJavaAttribute> javaAttributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EJavaType()
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
		return OxmPackage.Literals.EJAVA_TYPE;
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
			xmlProperties = new EObjectContainmentEList<EXmlProperty>(EXmlProperty.class, this, OxmPackage.EJAVA_TYPE__XML_PROPERTIES);
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' attribute.
	 * @see #setSuperType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_SuperType()
	 * @model
	 * @generated
	 */
	public String getSuperType()
	{
		return superType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getSuperType <em>Super Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' attribute.
	 * @see #getSuperType()
	 * @generated
	 */
	public void setSuperType(String newSuperType)
	{
		String oldSuperType = superType;
		superType = newSuperType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__SUPER_TYPE, oldSuperType, superType));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlAccessorOrder()
	 * @model
	 * @generated
	 */
	public EXmlAccessOrder getXmlAccessorOrder()
	{
		return xmlAccessorOrder;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorOrder <em>Xml Accessor Order</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_ACCESSOR_ORDER, oldXmlAccessorOrder, xmlAccessorOrder));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlAccessorType()
	 * @model
	 * @generated
	 */
	public EXmlAccessType getXmlAccessorType()
	{
		return xmlAccessorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorType <em>Xml Accessor Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_ACCESSOR_TYPE, oldXmlAccessorType, xmlAccessorType));
	}

	/**
	 * Returns the value of the '<em><b>Xml Customizer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Customizer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Customizer</em>' attribute.
	 * @see #setXmlCustomizer(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlCustomizer()
	 * @model
	 * @generated
	 */
	public String getXmlCustomizer()
	{
		return xmlCustomizer;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlCustomizer <em>Xml Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Customizer</em>' attribute.
	 * @see #getXmlCustomizer()
	 * @generated
	 */
	public void setXmlCustomizer(String newXmlCustomizer)
	{
		String oldXmlCustomizer = xmlCustomizer;
		xmlCustomizer = newXmlCustomizer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_CUSTOMIZER, oldXmlCustomizer, xmlCustomizer));
	}

	/**
	 * Returns the value of the '<em><b>Xml Discriminator Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Discriminator Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Discriminator Node</em>' attribute.
	 * @see #setXmlDiscriminatorNode(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlDiscriminatorNode()
	 * @model
	 * @generated
	 */
	public String getXmlDiscriminatorNode()
	{
		return xmlDiscriminatorNode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorNode <em>Xml Discriminator Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Discriminator Node</em>' attribute.
	 * @see #getXmlDiscriminatorNode()
	 * @generated
	 */
	public void setXmlDiscriminatorNode(String newXmlDiscriminatorNode)
	{
		String oldXmlDiscriminatorNode = xmlDiscriminatorNode;
		xmlDiscriminatorNode = newXmlDiscriminatorNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_NODE, oldXmlDiscriminatorNode, xmlDiscriminatorNode));
	}

	/**
	 * Returns the value of the '<em><b>Xml Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Discriminator Value</em>' attribute.
	 * @see #setXmlDiscriminatorValue(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlDiscriminatorValue()
	 * @model
	 * @generated
	 */
	public String getXmlDiscriminatorValue()
	{
		return xmlDiscriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorValue <em>Xml Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Discriminator Value</em>' attribute.
	 * @see #getXmlDiscriminatorValue()
	 * @generated
	 */
	public void setXmlDiscriminatorValue(String newXmlDiscriminatorValue)
	{
		String oldXmlDiscriminatorValue = xmlDiscriminatorValue;
		xmlDiscriminatorValue = newXmlDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_VALUE, oldXmlDiscriminatorValue, xmlDiscriminatorValue));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlInlineBinaryData()
	 * @model
	 * @generated
	 */
	public boolean isXmlInlineBinaryData()
	{
		return xmlInlineBinaryData;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_INLINE_BINARY_DATA, oldXmlInlineBinaryData, xmlInlineBinaryData));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlNameTransformer()
	 * @model
	 * @generated
	 */
	public String getXmlNameTransformer()
	{
		return xmlNameTransformer;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlNameTransformer <em>Xml Name Transformer</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_NAME_TRANSFORMER, oldXmlNameTransformer, xmlNameTransformer));
	}

	/**
	 * Returns the value of the '<em><b>Xml Virtual Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Virtual Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Virtual Access Methods</em>' containment reference.
	 * @see #setXmlVirtualAccessMethods(EXmlVirtualAccessMethods)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlVirtualAccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlVirtualAccessMethods getXmlVirtualAccessMethods()
	{
		return xmlVirtualAccessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlVirtualAccessMethods(EXmlVirtualAccessMethods newXmlVirtualAccessMethods, NotificationChain msgs)
	{
		EXmlVirtualAccessMethods oldXmlVirtualAccessMethods = xmlVirtualAccessMethods;
		xmlVirtualAccessMethods = newXmlVirtualAccessMethods;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS, oldXmlVirtualAccessMethods, newXmlVirtualAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlVirtualAccessMethods <em>Xml Virtual Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Virtual Access Methods</em>' containment reference.
	 * @see #getXmlVirtualAccessMethods()
	 * @generated
	 */
	public void setXmlVirtualAccessMethods(EXmlVirtualAccessMethods newXmlVirtualAccessMethods)
	{
		if (newXmlVirtualAccessMethods != xmlVirtualAccessMethods)
		{
			NotificationChain msgs = null;
			if (xmlVirtualAccessMethods != null)
				msgs = ((InternalEObject)xmlVirtualAccessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS, null, msgs);
			if (newXmlVirtualAccessMethods != null)
				msgs = ((InternalEObject)newXmlVirtualAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS, null, msgs);
			msgs = basicSetXmlVirtualAccessMethods(newXmlVirtualAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS, newXmlVirtualAccessMethods, newXmlVirtualAccessMethods));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlJavaTypeAdapter()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER, oldXmlJavaTypeAdapter, newXmlJavaTypeAdapter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}' containment reference.
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
				msgs = ((InternalEObject)xmlJavaTypeAdapter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			if (newXmlJavaTypeAdapter != null)
				msgs = ((InternalEObject)newXmlJavaTypeAdapter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER, null, msgs);
			msgs = basicSetXmlJavaTypeAdapter(newXmlJavaTypeAdapter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER, newXmlJavaTypeAdapter, newXmlJavaTypeAdapter));
	}

	/**
	 * Returns the value of the '<em><b>Xml Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Class Extractor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Class Extractor</em>' containment reference.
	 * @see #setXmlClassExtractor(EXmlClassExtractor)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_XmlClassExtractor()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlClassExtractor getXmlClassExtractor()
	{
		return xmlClassExtractor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlClassExtractor(EXmlClassExtractor newXmlClassExtractor, NotificationChain msgs)
	{
		EXmlClassExtractor oldXmlClassExtractor = xmlClassExtractor;
		xmlClassExtractor = newXmlClassExtractor;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR, oldXmlClassExtractor, newXmlClassExtractor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlClassExtractor <em>Xml Class Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Class Extractor</em>' containment reference.
	 * @see #getXmlClassExtractor()
	 * @generated
	 */
	public void setXmlClassExtractor(EXmlClassExtractor newXmlClassExtractor)
	{
		if (newXmlClassExtractor != xmlClassExtractor)
		{
			NotificationChain msgs = null;
			if (xmlClassExtractor != null)
				msgs = ((InternalEObject)xmlClassExtractor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR, null, msgs);
			if (newXmlClassExtractor != null)
				msgs = ((InternalEObject)newXmlClassExtractor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR, null, msgs);
			msgs = basicSetXmlClassExtractor(newXmlClassExtractor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR, newXmlClassExtractor, newXmlClassExtractor));
	}

	/**
	 * Returns the value of the '<em><b>Java Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Attributes</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType_JavaAttributes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EJavaAttribute> getJavaAttributes()
	{
		if (javaAttributes == null)
		{
			javaAttributes = new EObjectContainmentEList<EJavaAttribute>(EJavaAttribute.class, this, OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES);
		}
		return javaAttributes;
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
			case OxmPackage.EJAVA_TYPE__XML_PROPERTIES:
				return ((InternalEList<?>)getXmlProperties()).basicRemove(otherEnd, msgs);
			case OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS:
				return basicSetXmlVirtualAccessMethods(null, msgs);
			case OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER:
				return basicSetXmlJavaTypeAdapter(null, msgs);
			case OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR:
				return basicSetXmlClassExtractor(null, msgs);
			case OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES:
				return ((InternalEList<?>)getJavaAttributes()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EJAVA_TYPE__XML_PROPERTIES:
				return getXmlProperties();
			case OxmPackage.EJAVA_TYPE__NAME:
				return getName();
			case OxmPackage.EJAVA_TYPE__SUPER_TYPE:
				return getSuperType();
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_ORDER:
				return getXmlAccessorOrder();
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_TYPE:
				return getXmlAccessorType();
			case OxmPackage.EJAVA_TYPE__XML_CUSTOMIZER:
				return getXmlCustomizer();
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_NODE:
				return getXmlDiscriminatorNode();
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_VALUE:
				return getXmlDiscriminatorValue();
			case OxmPackage.EJAVA_TYPE__XML_INLINE_BINARY_DATA:
				return isXmlInlineBinaryData();
			case OxmPackage.EJAVA_TYPE__XML_NAME_TRANSFORMER:
				return getXmlNameTransformer();
			case OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS:
				return getXmlVirtualAccessMethods();
			case OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER:
				return getXmlJavaTypeAdapter();
			case OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR:
				return getXmlClassExtractor();
			case OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES:
				return getJavaAttributes();
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
			case OxmPackage.EJAVA_TYPE__XML_PROPERTIES:
				getXmlProperties().clear();
				getXmlProperties().addAll((Collection<? extends EXmlProperty>)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__NAME:
				setName((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__SUPER_TYPE:
				setSuperType((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_ORDER:
				setXmlAccessorOrder((EXmlAccessOrder)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_TYPE:
				setXmlAccessorType((EXmlAccessType)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_CUSTOMIZER:
				setXmlCustomizer((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_NODE:
				setXmlDiscriminatorNode((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_VALUE:
				setXmlDiscriminatorValue((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_INLINE_BINARY_DATA:
				setXmlInlineBinaryData((Boolean)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_NAME_TRANSFORMER:
				setXmlNameTransformer((String)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS:
				setXmlVirtualAccessMethods((EXmlVirtualAccessMethods)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR:
				setXmlClassExtractor((EXmlClassExtractor)newValue);
				return;
			case OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES:
				getJavaAttributes().clear();
				getJavaAttributes().addAll((Collection<? extends EJavaAttribute>)newValue);
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
			case OxmPackage.EJAVA_TYPE__XML_PROPERTIES:
				getXmlProperties().clear();
				return;
			case OxmPackage.EJAVA_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__SUPER_TYPE:
				setSuperType(SUPER_TYPE_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_ORDER:
				setXmlAccessorOrder(XML_ACCESSOR_ORDER_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_TYPE:
				setXmlAccessorType(XML_ACCESSOR_TYPE_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_CUSTOMIZER:
				setXmlCustomizer(XML_CUSTOMIZER_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_NODE:
				setXmlDiscriminatorNode(XML_DISCRIMINATOR_NODE_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_VALUE:
				setXmlDiscriminatorValue(XML_DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_INLINE_BINARY_DATA:
				setXmlInlineBinaryData(XML_INLINE_BINARY_DATA_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_NAME_TRANSFORMER:
				setXmlNameTransformer(XML_NAME_TRANSFORMER_EDEFAULT);
				return;
			case OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS:
				setXmlVirtualAccessMethods((EXmlVirtualAccessMethods)null);
				return;
			case OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER:
				setXmlJavaTypeAdapter((EXmlJavaTypeAdapter)null);
				return;
			case OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR:
				setXmlClassExtractor((EXmlClassExtractor)null);
				return;
			case OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES:
				getJavaAttributes().clear();
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
			case OxmPackage.EJAVA_TYPE__XML_PROPERTIES:
				return xmlProperties != null && !xmlProperties.isEmpty();
			case OxmPackage.EJAVA_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OxmPackage.EJAVA_TYPE__SUPER_TYPE:
				return SUPER_TYPE_EDEFAULT == null ? superType != null : !SUPER_TYPE_EDEFAULT.equals(superType);
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_ORDER:
				return xmlAccessorOrder != XML_ACCESSOR_ORDER_EDEFAULT;
			case OxmPackage.EJAVA_TYPE__XML_ACCESSOR_TYPE:
				return xmlAccessorType != XML_ACCESSOR_TYPE_EDEFAULT;
			case OxmPackage.EJAVA_TYPE__XML_CUSTOMIZER:
				return XML_CUSTOMIZER_EDEFAULT == null ? xmlCustomizer != null : !XML_CUSTOMIZER_EDEFAULT.equals(xmlCustomizer);
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_NODE:
				return XML_DISCRIMINATOR_NODE_EDEFAULT == null ? xmlDiscriminatorNode != null : !XML_DISCRIMINATOR_NODE_EDEFAULT.equals(xmlDiscriminatorNode);
			case OxmPackage.EJAVA_TYPE__XML_DISCRIMINATOR_VALUE:
				return XML_DISCRIMINATOR_VALUE_EDEFAULT == null ? xmlDiscriminatorValue != null : !XML_DISCRIMINATOR_VALUE_EDEFAULT.equals(xmlDiscriminatorValue);
			case OxmPackage.EJAVA_TYPE__XML_INLINE_BINARY_DATA:
				return xmlInlineBinaryData != XML_INLINE_BINARY_DATA_EDEFAULT;
			case OxmPackage.EJAVA_TYPE__XML_NAME_TRANSFORMER:
				return XML_NAME_TRANSFORMER_EDEFAULT == null ? xmlNameTransformer != null : !XML_NAME_TRANSFORMER_EDEFAULT.equals(xmlNameTransformer);
			case OxmPackage.EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS:
				return xmlVirtualAccessMethods != null;
			case OxmPackage.EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER:
				return xmlJavaTypeAdapter != null;
			case OxmPackage.EJAVA_TYPE__XML_CLASS_EXTRACTOR:
				return xmlClassExtractor != null;
			case OxmPackage.EJAVA_TYPE__JAVA_ATTRIBUTES:
				return javaAttributes != null && !javaAttributes.isEmpty();
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
		if (baseClass == EPropertyHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EJAVA_TYPE__XML_PROPERTIES: return OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES;
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
		if (baseClass == EPropertyHolder.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.EPROPERTY_HOLDER__XML_PROPERTIES: return OxmPackage.EJAVA_TYPE__XML_PROPERTIES;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", superType: ");
		result.append(superType);
		result.append(", xmlAccessorOrder: ");
		result.append(xmlAccessorOrder);
		result.append(", xmlAccessorType: ");
		result.append(xmlAccessorType);
		result.append(", xmlCustomizer: ");
		result.append(xmlCustomizer);
		result.append(", xmlDiscriminatorNode: ");
		result.append(xmlDiscriminatorNode);
		result.append(", xmlDiscriminatorValue: ");
		result.append(xmlDiscriminatorValue);
		result.append(", xmlInlineBinaryData: ");
		result.append(xmlInlineBinaryData);
		result.append(", xmlNameTransformer: ");
		result.append(xmlNameTransformer);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** text range *****
	
	public TextRange getNameTextRange() {
		return getAttributeTextRange(Oxm.NAME);
	}
	

	// ***** translators *****
	
	public static Translator buildTranslator() {
		return new SimpleTranslator(Oxm.JAVA_TYPES + "/" + Oxm.JAVA_TYPE, OxmPackage.eINSTANCE.getEXmlBindings_JavaTypes(), buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildXmlTransientTranslator(),
			EXmlType.buildTranslator(),
			EXmlSeeAlso.buildTranslator(),
			new EJavaAttribute.JavaAttributesTranslator()
		};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(
			Oxm.NAME,
			OxmPackage.eINSTANCE.getEJavaType_Name(), 
			Translator.DOM_ATTRIBUTE);
	}
}
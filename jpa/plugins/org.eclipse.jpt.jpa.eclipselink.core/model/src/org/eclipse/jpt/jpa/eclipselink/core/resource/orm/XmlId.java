/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

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
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIndex;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Id</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId()
 * @model kind="class"
 * @generated
 */
public class XmlId extends org.eclipse.jpt.jpa.core.resource.orm.XmlId implements XmlId_2_2, XmlId_2_4, XmlAttributeMapping, XmlMutable, XmlConverterContainer
{
	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected XmlIndex index;

	/**
	 * The cached value of the '{@link #getUuidGenerator() <em>Uuid Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUuidGenerator()
	 * @generated
	 * @ordered
	 */
	protected XmlUuidGenerator uuidGenerator;

	/**
	 * The cached value of the '{@link #getField() <em>Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getField()
	 * @generated
	 * @ordered
	 */
	protected XmlField field;

	/**
	 * The cached value of the '{@link #getCacheIndex() <em>Cache Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheIndex()
	 * @generated
	 * @ordered
	 */
	protected XmlCacheIndex cacheIndex;

	/**
	 * The cached value of the '{@link #getAccessMethods() <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessMethods()
	 * @generated
	 * @ordered
	 */
	protected XmlAccessMethods accessMethods;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlProperty> properties;

	/**
	 * The default value of the '{@link #getAttributeType() <em>Attribute Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeType()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeType() <em>Attribute Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeType()
	 * @generated
	 * @ordered
	 */
	protected String attributeType = ATTRIBUTE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMutable() <em>Mutable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMutable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean MUTABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMutable() <em>Mutable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMutable()
	 * @generated
	 * @ordered
	 */
	protected Boolean mutable = MUTABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConverters() <em>Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConverter> converters;

	/**
	 * The cached value of the '{@link #getTypeConverters() <em>Type Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTypeConverter> typeConverters;

	/**
	 * The cached value of the '{@link #getObjectTypeConverters() <em>Object Type Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectTypeConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlObjectTypeConverter> objectTypeConverters;

	/**
	 * The cached value of the '{@link #getStructConverters() <em>Struct Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlStructConverter> structConverters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlId()
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
		return EclipseLinkOrmPackage.Literals.XML_ID;
	}

	/**
	 * Returns the value of the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Type</em>' attribute.
	 * @see #setAttributeType(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeMapping_AttributeType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getAttributeType()
	{
		return attributeType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getAttributeType <em>Attribute Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Type</em>' attribute.
	 * @see #getAttributeType()
	 * @generated
	 */
	public void setAttributeType(String newAttributeType)
	{
		String oldAttributeType = attributeType;
		attributeType = newAttributeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE, oldAttributeType, attributeType));
	}

	/**
	 * Returns the value of the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' containment reference.
	 * @see #setIndex(XmlIndex)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId_2_2_Index()
	 * @model containment="true"
	 * @generated
	 */
	public XmlIndex getIndex()
	{
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndex(XmlIndex newIndex, NotificationChain msgs)
	{
		XmlIndex oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__INDEX, oldIndex, newIndex);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getIndex <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' containment reference.
	 * @see #getIndex()
	 * @generated
	 */
	public void setIndex(XmlIndex newIndex)
	{
		if (newIndex != index)
		{
			NotificationChain msgs = null;
			if (index != null)
				msgs = ((InternalEObject)index).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__INDEX, null, msgs);
			if (newIndex != null)
				msgs = ((InternalEObject)newIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__INDEX, null, msgs);
			msgs = basicSetIndex(newIndex, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__INDEX, newIndex, newIndex));
	}

	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field</em>' containment reference.
	 * @see #setField(XmlField)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId_2_4_Field()
	 * @model containment="true"
	 * @generated
	 */
	public XmlField getField()
	{
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetField(XmlField newField, NotificationChain msgs)
	{
		XmlField oldField = field;
		field = newField;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__FIELD, oldField, newField);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getField <em>Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field</em>' containment reference.
	 * @see #getField()
	 * @generated
	 */
	public void setField(XmlField newField)
	{
		if (newField != field)
		{
			NotificationChain msgs = null;
			if (field != null)
				msgs = ((InternalEObject)field).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__FIELD, null, msgs);
			if (newField != null)
				msgs = ((InternalEObject)newField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__FIELD, null, msgs);
			msgs = basicSetField(newField, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__FIELD, newField, newField));
	}

	/**
	 * Returns the value of the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Index</em>' containment reference.
	 * @see #setCacheIndex(XmlCacheIndex)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId_2_4_CacheIndex()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCacheIndex getCacheIndex()
	{
		return cacheIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheIndex(XmlCacheIndex newCacheIndex, NotificationChain msgs)
	{
		XmlCacheIndex oldCacheIndex = cacheIndex;
		cacheIndex = newCacheIndex;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__CACHE_INDEX, oldCacheIndex, newCacheIndex);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getCacheIndex <em>Cache Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Index</em>' containment reference.
	 * @see #getCacheIndex()
	 * @generated
	 */
	public void setCacheIndex(XmlCacheIndex newCacheIndex)
	{
		if (newCacheIndex != cacheIndex)
		{
			NotificationChain msgs = null;
			if (cacheIndex != null)
				msgs = ((InternalEObject)cacheIndex).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__CACHE_INDEX, null, msgs);
			if (newCacheIndex != null)
				msgs = ((InternalEObject)newCacheIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__CACHE_INDEX, null, msgs);
			msgs = basicSetCacheIndex(newCacheIndex, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__CACHE_INDEX, newCacheIndex, newCacheIndex));
	}

	/**
	 * Returns the value of the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid Generator</em>' containment reference.
	 * @see #setUuidGenerator(XmlUuidGenerator)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlGeneratorContainer2_4_UuidGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlUuidGenerator getUuidGenerator()
	{
		return uuidGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUuidGenerator(XmlUuidGenerator newUuidGenerator, NotificationChain msgs)
	{
		XmlUuidGenerator oldUuidGenerator = uuidGenerator;
		uuidGenerator = newUuidGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR, oldUuidGenerator, newUuidGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getUuidGenerator <em>Uuid Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid Generator</em>' containment reference.
	 * @see #getUuidGenerator()
	 * @generated
	 */
	public void setUuidGenerator(XmlUuidGenerator newUuidGenerator)
	{
		if (newUuidGenerator != uuidGenerator)
		{
			NotificationChain msgs = null;
			if (uuidGenerator != null)
				msgs = ((InternalEObject)uuidGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR, null, msgs);
			if (newUuidGenerator != null)
				msgs = ((InternalEObject)newUuidGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR, null, msgs);
			msgs = basicSetUuidGenerator(newUuidGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR, newUuidGenerator, newUuidGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mutable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mutable</em>' attribute.
	 * @see #setMutable(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMutable_Mutable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getMutable()
	{
		return mutable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getMutable <em>Mutable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mutable</em>' attribute.
	 * @see #getMutable()
	 * @generated
	 */
	public void setMutable(Boolean newMutable)
	{
		Boolean oldMutable = mutable;
		mutable = newMutable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__MUTABLE, oldMutable, mutable));
	}

	/**
	 * Returns the value of the '<em><b>Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer_2_1_Converters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConverter> getConverters()
	{
		if (converters == null)
		{
			converters = new EObjectContainmentEList<XmlConverter>(XmlConverter.class, this, EclipseLinkOrmPackage.XML_ID__CONVERTERS);
		}
		return converters;
	}

	/**
	 * Returns the value of the '<em><b>Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer_TypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTypeConverter> getTypeConverters()
	{
		if (typeConverters == null)
		{
			typeConverters = new EObjectContainmentEList<XmlTypeConverter>(XmlTypeConverter.class, this, EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS);
		}
		return typeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer_ObjectTypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlObjectTypeConverter> getObjectTypeConverters()
	{
		if (objectTypeConverters == null)
		{
			objectTypeConverters = new EObjectContainmentEList<XmlObjectTypeConverter>(XmlObjectTypeConverter.class, this, EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS);
		}
		return objectTypeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Struct Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer_StructConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStructConverter> getStructConverters()
	{
		if (structConverters == null)
		{
			structConverters = new EObjectContainmentEList<XmlStructConverter>(XmlStructConverter.class, this, EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS);
		}
		return structConverters;
	}

	/**
	 * Returns the value of the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Methods</em>' containment reference.
	 * @see #setAccessMethods(XmlAccessMethods)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder_AccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	public XmlAccessMethods getAccessMethods()
	{
		return accessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessMethods(XmlAccessMethods newAccessMethods, NotificationChain msgs)
	{
		XmlAccessMethods oldAccessMethods = accessMethods;
		accessMethods = newAccessMethods;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId#getAccessMethods <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Methods</em>' containment reference.
	 * @see #getAccessMethods()
	 * @generated
	 */
	public void setAccessMethods(XmlAccessMethods newAccessMethods)
	{
		if (newAccessMethods != accessMethods)
		{
			NotificationChain msgs = null;
			if (accessMethods != null)
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS, newAccessMethods, newAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPropertyContainer_Properties()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new EObjectContainmentEList<XmlProperty>(XmlProperty.class, this, EclipseLinkOrmPackage.XML_ID__PROPERTIES);
		}
		return properties;
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
			case EclipseLinkOrmPackage.XML_ID__INDEX:
				return basicSetIndex(null, msgs);
			case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR:
				return basicSetUuidGenerator(null, msgs);
			case EclipseLinkOrmPackage.XML_ID__FIELD:
				return basicSetField(null, msgs);
			case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX:
				return basicSetCacheIndex(null, msgs);
			case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_ID__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ID__CONVERTERS:
				return ((InternalEList<?>)getConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS:
				return ((InternalEList<?>)getTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS:
				return ((InternalEList<?>)getObjectTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS:
				return ((InternalEList<?>)getStructConverters()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_ID__INDEX:
				return getIndex();
			case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR:
				return getUuidGenerator();
			case EclipseLinkOrmPackage.XML_ID__FIELD:
				return getField();
			case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX:
				return getCacheIndex();
			case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_ID__PROPERTIES:
				return getProperties();
			case EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE:
				return getAttributeType();
			case EclipseLinkOrmPackage.XML_ID__MUTABLE:
				return getMutable();
			case EclipseLinkOrmPackage.XML_ID__CONVERTERS:
				return getConverters();
			case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS:
				return getTypeConverters();
			case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS:
				return getObjectTypeConverters();
			case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS:
				return getStructConverters();
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
			case EclipseLinkOrmPackage.XML_ID__INDEX:
				setIndex((XmlIndex)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR:
				setUuidGenerator((XmlUuidGenerator)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__FIELD:
				setField((XmlField)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX:
				setCacheIndex((XmlCacheIndex)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends XmlProperty>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE:
				setAttributeType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__MUTABLE:
				setMutable((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__CONVERTERS:
				getConverters().clear();
				getConverters().addAll((Collection<? extends XmlConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS:
				getTypeConverters().clear();
				getTypeConverters().addAll((Collection<? extends XmlTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				getObjectTypeConverters().addAll((Collection<? extends XmlObjectTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS:
				getStructConverters().clear();
				getStructConverters().addAll((Collection<? extends XmlStructConverter>)newValue);
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
			case EclipseLinkOrmPackage.XML_ID__INDEX:
				setIndex((XmlIndex)null);
				return;
			case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR:
				setUuidGenerator((XmlUuidGenerator)null);
				return;
			case EclipseLinkOrmPackage.XML_ID__FIELD:
				setField((XmlField)null);
				return;
			case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX:
				setCacheIndex((XmlCacheIndex)null);
				return;
			case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_ID__PROPERTIES:
				getProperties().clear();
				return;
			case EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE:
				setAttributeType(ATTRIBUTE_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ID__MUTABLE:
				setMutable(MUTABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ID__CONVERTERS:
				getConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS:
				getTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS:
				getStructConverters().clear();
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
			case EclipseLinkOrmPackage.XML_ID__INDEX:
				return index != null;
			case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR:
				return uuidGenerator != null;
			case EclipseLinkOrmPackage.XML_ID__FIELD:
				return field != null;
			case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX:
				return cacheIndex != null;
			case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_ID__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE:
				return ATTRIBUTE_TYPE_EDEFAULT == null ? attributeType != null : !ATTRIBUTE_TYPE_EDEFAULT.equals(attributeType);
			case EclipseLinkOrmPackage.XML_ID__MUTABLE:
				return MUTABLE_EDEFAULT == null ? mutable != null : !MUTABLE_EDEFAULT.equals(mutable);
			case EclipseLinkOrmPackage.XML_ID__CONVERTERS:
				return converters != null && !converters.isEmpty();
			case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS:
				return typeConverters != null && !typeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS:
				return objectTypeConverters != null && !objectTypeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS:
				return structConverters != null && !structConverters.isEmpty();
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
		if (baseClass == XmlId_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__INDEX: return EclipseLinkOrmV2_2Package.XML_ID_22__INDEX;
				default: return -1;
			}
		}
		if (baseClass == XmlGeneratorContainer2_4.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR: return EclipseLinkOrmV2_4Package.XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR;
				default: return -1;
			}
		}
		if (baseClass == XmlId_2_4.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__FIELD: return EclipseLinkOrmV2_4Package.XML_ID_24__FIELD;
				case EclipseLinkOrmPackage.XML_ID__CACHE_INDEX: return EclipseLinkOrmV2_4Package.XML_ID_24__CACHE_INDEX;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__PROPERTIES: return EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE: return EclipseLinkOrmPackage.XML_ATTRIBUTE_MAPPING__ATTRIBUTE_TYPE;
				default: return -1;
			}
		}
		if (baseClass == XmlMutable.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__MUTABLE: return EclipseLinkOrmPackage.XML_MUTABLE__MUTABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterContainer_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__CONVERTERS: return OrmV2_1Package.XML_CONVERTER_CONTAINER_21__CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__STRUCT_CONVERTERS;
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
		if (baseClass == XmlId_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_ID_22__INDEX: return EclipseLinkOrmPackage.XML_ID__INDEX;
				default: return -1;
			}
		}
		if (baseClass == XmlGeneratorContainer2_4.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_4Package.XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR: return EclipseLinkOrmPackage.XML_ID__UUID_GENERATOR;
				default: return -1;
			}
		}
		if (baseClass == XmlId_2_4.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_4Package.XML_ID_24__FIELD: return EclipseLinkOrmPackage.XML_ID__FIELD;
				case EclipseLinkOrmV2_4Package.XML_ID_24__CACHE_INDEX: return EclipseLinkOrmPackage.XML_ID__CACHE_INDEX;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES: return EclipseLinkOrmPackage.XML_ID__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ATTRIBUTE_MAPPING__ATTRIBUTE_TYPE: return EclipseLinkOrmPackage.XML_ID__ATTRIBUTE_TYPE;
				default: return -1;
			}
		}
		if (baseClass == XmlMutable.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MUTABLE__MUTABLE: return EclipseLinkOrmPackage.XML_ID__MUTABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterContainer_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_CONVERTER_CONTAINER_21__CONVERTERS: return EclipseLinkOrmPackage.XML_ID__CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTER_CONTAINER__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTERS;
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
		result.append(" (attributeType: ");
		result.append(attributeType);
		result.append(", mutable: ");
		result.append(mutable);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getMutableTextRange() {
		return getAttributeTextRange(EclipseLink.MUTABLE);
	}

	public TextRange getAttributeTypeTextRange() {
		return getAttributeTextRange(EclipseLink2_1.ATTRIBUTE_TYPE);
	}

	
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlId(), 
			buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildAccessTranslator(),
			buildMutableTranslator(),
			buildAttributeTypeTranslator(),
			buildColumnTranslator(),
			buildFieldTranslator(),
			buildIndexTranslator(),
			buildCacheIndexTranslator(),
			buildGeneratedValueTranslator(),
			buildTemporalTranslator(),
			buildEnumeratedTranslator(),
			buildConvertTranslator(),
			buildConverterTranslator(),
			buildTypeConverterTranslator(),
			buildObjectTypeConverterTranslator(),
			buildStructConverterTranslator(),
			buildTableGeneratorTranslator(),
			buildSequenceGeneratorTranslator(),
			buildUuidGeneratorTranslator(),
			buildPropertyTranslator(),
			buildAccessMethodsTranslator()
		};
	}
	
	protected static Translator buildMutableTranslator() {
		return new Translator(EclipseLink.MUTABLE, EclipseLinkOrmPackage.eINSTANCE.getXmlMutable_Mutable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildIndexTranslator() {
		return XmlIndex.buildTranslator(EclipseLink2_2.INDEX, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlId_2_2_Index());
	}

	protected static Translator buildCacheIndexTranslator() {
		return XmlCacheIndex.buildTranslator(EclipseLink2_4.CACHE_INDEX, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlId_2_4_CacheIndex());
	}
	
	protected static Translator buildConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.CONVERT, OrmV2_1Package.eINSTANCE.getXmlConvertibleMapping_2_1_Convert());
	}
	
	protected static Translator buildConverterTranslator() {
		return XmlConverter.buildTranslator(EclipseLink.CONVERTER, OrmV2_1Package.eINSTANCE.getXmlConverterContainer_2_1_Converters());
	}
	
	protected static Translator buildTypeConverterTranslator() {
		return XmlTypeConverter.buildTranslator(EclipseLink.TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConverterContainer_TypeConverters());
	}
	
	protected static Translator buildObjectTypeConverterTranslator() {
		return XmlObjectTypeConverter.buildTranslator(EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConverterContainer_ObjectTypeConverters());
	}
	
	protected static Translator buildStructConverterTranslator() {
		return XmlStructConverter.buildTranslator(EclipseLink.STRUCT_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConverterContainer_StructConverters());
	}
	
	protected static Translator buildPropertyTranslator() {
		return XmlProperty.buildTranslator(EclipseLink.PROPERTY, EclipseLinkOrmPackage.eINSTANCE.getXmlPropertyContainer_Properties());
	}
	
	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

	protected static Translator buildAttributeTypeTranslator() {
		return new Translator(EclipseLink2_1.ATTRIBUTE_TYPE,  EclipseLinkOrmPackage.eINSTANCE.getXmlAttributeMapping_AttributeType(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildEnumeratedTranslator() {
		return new Translator(JPA.ENUMERATED, OrmPackage.eINSTANCE.getXmlConvertibleMapping_Enumerated());
	}

	protected static Translator buildTableGeneratorTranslator() {
		return XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_TableGenerator());
	}

	protected static Translator buildFieldTranslator() {
		return XmlField.buildTranslator(EclipseLink2_4.FIELD, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlId_2_4_Field());
	}
	
	protected static Translator buildUuidGeneratorTranslator() {
		return XmlUuidGenerator.buildTranslator(EclipseLink2_4.UUID_GENERATOR, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlGeneratorContainer2_4_UuidGenerator());
	}

	
	// ******** virtual attribute ************
	
	public void setVirtualAttributeTypes(String attributeType, String targetType) {
		this.setAttributeType(attributeType);
	}

	// *********** content assist ************
	
	protected TextRange getAttributeTypeCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(EclipseLink2_1.ATTRIBUTE_TYPE);
	}
	
	public boolean attributeTypeTouches(int pos) {
		TextRange textRange = this.getAttributeTypeCodeAssistTextRange();
		return (textRange != null) && (textRange.touches(pos));
	}
}

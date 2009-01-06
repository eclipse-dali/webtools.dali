/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.EclipseLinkOrmXmlMapper;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Entity</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getOptimisticLocking <em>Optimistic Locking</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCopyPolicy <em>Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCloneCopyPolicy <em>Clone Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getNamedStoredProcedureQueries <em>Named Stored Procedure Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
 * @model kind="class"
 * @generated
 */
public class XmlEntity extends org.eclipse.jpt.core.resource.orm.XmlEntity implements XmlReadOnly, XmlCustomizerHolder, XmlChangeTrackingHolder, XmlCacheHolder, XmlConvertersHolder
{
	/**
	 * The default value of the '{@link #getReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean READ_ONLY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadOnly()
	 * @generated
	 * @ordered
	 */
	protected Boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCustomizer() <em>Customizer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomizer()
	 * @generated
	 * @ordered
	 */
	protected XmlCustomizer customizer;

	/**
	 * The cached value of the '{@link #getChangeTracking() <em>Change Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeTracking()
	 * @generated
	 * @ordered
	 */
	protected XmlChangeTracking changeTracking;

	/**
	 * The cached value of the '{@link #getCache() <em>Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCache()
	 * @generated
	 * @ordered
	 */
	protected XmlCache cache;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final ExistenceType EXISTENCE_CHECKING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExistenceChecking() <em>Existence Checking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExistenceChecking()
	 * @generated
	 * @ordered
	 */
	protected ExistenceType existenceChecking = EXISTENCE_CHECKING_EDEFAULT;

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
	 * The cached value of the '{@link #getOptimisticLocking() <em>Optimistic Locking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptimisticLocking()
	 * @generated
	 * @ordered
	 */
	protected XmlOptimisticLocking optimisticLocking;

	/**
	 * The cached value of the '{@link #getCopyPolicy() <em>Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCopyPolicy copyPolicy;

	/**
	 * The cached value of the '{@link #getInstantiationCopyPolicy() <em>Instantiation Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstantiationCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCopyPolicy instantiationCopyPolicy;

	/**
	 * The cached value of the '{@link #getCloneCopyPolicy() <em>Clone Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCloneCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCopyPolicy cloneCopyPolicy;

	/**
	 * The cached value of the '{@link #getNamedStoredProcedureQueries() <em>Named Stored Procedure Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedStoredProcedureQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedStoredProcedureQuery> namedStoredProcedureQueries;

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
	 * The default value of the '{@link #getExcludeDefaultMappings() <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExcludeDefaultMappings() <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 * @ordered
	 */
	protected Boolean excludeDefaultMappings = EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntity()
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
		return EclipseLinkOrmPackage.Literals.XML_ENTITY;
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
	 * @see #setReadOnly(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReadOnly_ReadOnly()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getReadOnly()
	{
		return readOnly;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #getReadOnly()
	 * @generated
	 */
	public void setReadOnly(Boolean newReadOnly)
	{
		Boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * Returns the value of the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Customizer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Customizer</em>' containment reference.
	 * @see #setCustomizer(XmlCustomizer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder_Customizer()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCustomizer getCustomizer()
	{
		return customizer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomizer(XmlCustomizer newCustomizer, NotificationChain msgs)
	{
		XmlCustomizer oldCustomizer = customizer;
		customizer = newCustomizer;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER, oldCustomizer, newCustomizer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCustomizer <em>Customizer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Customizer</em>' containment reference.
	 * @see #getCustomizer()
	 * @generated
	 */
	public void setCustomizer(XmlCustomizer newCustomizer)
	{
		if (newCustomizer != customizer)
		{
			NotificationChain msgs = null;
			if (customizer != null)
				msgs = ((InternalEObject)customizer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER, null, msgs);
			if (newCustomizer != null)
				msgs = ((InternalEObject)newCustomizer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER, null, msgs);
			msgs = basicSetCustomizer(newCustomizer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER, newCustomizer, newCustomizer));
	}

	/**
	 * Returns the value of the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Tracking</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Tracking</em>' containment reference.
	 * @see #setChangeTracking(XmlChangeTracking)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder_ChangeTracking()
	 * @model containment="true"
	 * @generated
	 */
	public XmlChangeTracking getChangeTracking()
	{
		return changeTracking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangeTracking(XmlChangeTracking newChangeTracking, NotificationChain msgs)
	{
		XmlChangeTracking oldChangeTracking = changeTracking;
		changeTracking = newChangeTracking;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING, oldChangeTracking, newChangeTracking);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getChangeTracking <em>Change Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change Tracking</em>' containment reference.
	 * @see #getChangeTracking()
	 * @generated
	 */
	public void setChangeTracking(XmlChangeTracking newChangeTracking)
	{
		if (newChangeTracking != changeTracking)
		{
			NotificationChain msgs = null;
			if (changeTracking != null)
				msgs = ((InternalEObject)changeTracking).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING, null, msgs);
			if (newChangeTracking != null)
				msgs = ((InternalEObject)newChangeTracking).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING, null, msgs);
			msgs = basicSetChangeTracking(newChangeTracking, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING, newChangeTracking, newChangeTracking));
	}

	/**
	 * Returns the value of the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache</em>' containment reference.
	 * @see #setCache(XmlCache)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder_Cache()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCache getCache()
	{
		return cache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCache(XmlCache newCache, NotificationChain msgs)
	{
		XmlCache oldCache = cache;
		cache = newCache;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CACHE, oldCache, newCache);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCache <em>Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache</em>' containment reference.
	 * @see #getCache()
	 * @generated
	 */
	public void setCache(XmlCache newCache)
	{
		if (newCache != cache)
		{
			NotificationChain msgs = null;
			if (cache != null)
				msgs = ((InternalEObject)cache).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CACHE, null, msgs);
			if (newCache != null)
				msgs = ((InternalEObject)newCache).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CACHE, null, msgs);
			msgs = basicSetCache(newCache, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CACHE, newCache, newCache));
	}

	/**
	 * Returns the value of the '<em><b>Existence Checking</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Existence Checking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Existence Checking</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType
	 * @see #setExistenceChecking(ExistenceType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder_ExistenceChecking()
	 * @model default=""
	 * @generated
	 */
	public ExistenceType getExistenceChecking()
	{
		return existenceChecking;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getExistenceChecking <em>Existence Checking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Existence Checking</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType
	 * @see #getExistenceChecking()
	 * @generated
	 */
	public void setExistenceChecking(ExistenceType newExistenceChecking)
	{
		ExistenceType oldExistenceChecking = existenceChecking;
		existenceChecking = newExistenceChecking == null ? EXISTENCE_CHECKING_EDEFAULT : newExistenceChecking;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING, oldExistenceChecking, existenceChecking));
	}

	/**
	 * Returns the value of the '<em><b>Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_Converters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConverter> getConverters()
	{
		if (converters == null)
		{
			converters = new EObjectContainmentEList<XmlConverter>(XmlConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS);
		}
		return converters;
	}

	/**
	 * Returns the value of the '<em><b>Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_TypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTypeConverter> getTypeConverters()
	{
		if (typeConverters == null)
		{
			typeConverters = new EObjectContainmentEList<XmlTypeConverter>(XmlTypeConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS);
		}
		return typeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_ObjectTypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlObjectTypeConverter> getObjectTypeConverters()
	{
		if (objectTypeConverters == null)
		{
			objectTypeConverters = new EObjectContainmentEList<XmlObjectTypeConverter>(XmlObjectTypeConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS);
		}
		return objectTypeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Struct Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_StructConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStructConverter> getStructConverters()
	{
		if (structConverters == null)
		{
			structConverters = new EObjectContainmentEList<XmlStructConverter>(XmlStructConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS);
		}
		return structConverters;
	}

	/**
	 * Returns the value of the '<em><b>Optimistic Locking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optimistic Locking</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optimistic Locking</em>' containment reference.
	 * @see #setOptimisticLocking(XmlOptimisticLocking)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_OptimisticLocking()
	 * @model containment="true"
	 * @generated
	 */
	public XmlOptimisticLocking getOptimisticLocking()
	{
		return optimisticLocking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOptimisticLocking(XmlOptimisticLocking newOptimisticLocking, NotificationChain msgs)
	{
		XmlOptimisticLocking oldOptimisticLocking = optimisticLocking;
		optimisticLocking = newOptimisticLocking;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING, oldOptimisticLocking, newOptimisticLocking);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getOptimisticLocking <em>Optimistic Locking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optimistic Locking</em>' containment reference.
	 * @see #getOptimisticLocking()
	 * @generated
	 */
	public void setOptimisticLocking(XmlOptimisticLocking newOptimisticLocking)
	{
		if (newOptimisticLocking != optimisticLocking)
		{
			NotificationChain msgs = null;
			if (optimisticLocking != null)
				msgs = ((InternalEObject)optimisticLocking).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING, null, msgs);
			if (newOptimisticLocking != null)
				msgs = ((InternalEObject)newOptimisticLocking).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING, null, msgs);
			msgs = basicSetOptimisticLocking(newOptimisticLocking, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING, newOptimisticLocking, newOptimisticLocking));
	}

	/**
	 * Returns the value of the '<em><b>Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copy Policy</em>' containment reference.
	 * @see #setCopyPolicy(XmlCopyPolicy)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_CopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCopyPolicy getCopyPolicy()
	{
		return copyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCopyPolicy(XmlCopyPolicy newCopyPolicy, NotificationChain msgs)
	{
		XmlCopyPolicy oldCopyPolicy = copyPolicy;
		copyPolicy = newCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY, oldCopyPolicy, newCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCopyPolicy <em>Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Copy Policy</em>' containment reference.
	 * @see #getCopyPolicy()
	 * @generated
	 */
	public void setCopyPolicy(XmlCopyPolicy newCopyPolicy)
	{
		if (newCopyPolicy != copyPolicy)
		{
			NotificationChain msgs = null;
			if (copyPolicy != null)
				msgs = ((InternalEObject)copyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY, null, msgs);
			if (newCopyPolicy != null)
				msgs = ((InternalEObject)newCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY, null, msgs);
			msgs = basicSetCopyPolicy(newCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY, newCopyPolicy, newCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Instantiation Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instantiation Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instantiation Copy Policy</em>' containment reference.
	 * @see #setInstantiationCopyPolicy(XmlCopyPolicy)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_InstantiationCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCopyPolicy getInstantiationCopyPolicy()
	{
		return instantiationCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInstantiationCopyPolicy(XmlCopyPolicy newInstantiationCopyPolicy, NotificationChain msgs)
	{
		XmlCopyPolicy oldInstantiationCopyPolicy = instantiationCopyPolicy;
		instantiationCopyPolicy = newInstantiationCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY, oldInstantiationCopyPolicy, newInstantiationCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instantiation Copy Policy</em>' containment reference.
	 * @see #getInstantiationCopyPolicy()
	 * @generated
	 */
	public void setInstantiationCopyPolicy(XmlCopyPolicy newInstantiationCopyPolicy)
	{
		if (newInstantiationCopyPolicy != instantiationCopyPolicy)
		{
			NotificationChain msgs = null;
			if (instantiationCopyPolicy != null)
				msgs = ((InternalEObject)instantiationCopyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY, null, msgs);
			if (newInstantiationCopyPolicy != null)
				msgs = ((InternalEObject)newInstantiationCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY, null, msgs);
			msgs = basicSetInstantiationCopyPolicy(newInstantiationCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY, newInstantiationCopyPolicy, newInstantiationCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Clone Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clone Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clone Copy Policy</em>' containment reference.
	 * @see #setCloneCopyPolicy(XmlCopyPolicy)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_CloneCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCopyPolicy getCloneCopyPolicy()
	{
		return cloneCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCloneCopyPolicy(XmlCopyPolicy newCloneCopyPolicy, NotificationChain msgs)
	{
		XmlCopyPolicy oldCloneCopyPolicy = cloneCopyPolicy;
		cloneCopyPolicy = newCloneCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY, oldCloneCopyPolicy, newCloneCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCloneCopyPolicy <em>Clone Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clone Copy Policy</em>' containment reference.
	 * @see #getCloneCopyPolicy()
	 * @generated
	 */
	public void setCloneCopyPolicy(XmlCopyPolicy newCloneCopyPolicy)
	{
		if (newCloneCopyPolicy != cloneCopyPolicy)
		{
			NotificationChain msgs = null;
			if (cloneCopyPolicy != null)
				msgs = ((InternalEObject)cloneCopyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY, null, msgs);
			if (newCloneCopyPolicy != null)
				msgs = ((InternalEObject)newCloneCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY, null, msgs);
			msgs = basicSetCloneCopyPolicy(newCloneCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY, newCloneCopyPolicy, newCloneCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Stored Procedure Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Stored Procedure Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_NamedStoredProcedureQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedStoredProcedureQuery> getNamedStoredProcedureQueries()
	{
		if (namedStoredProcedureQueries == null)
		{
			namedStoredProcedureQueries = new EObjectContainmentEList<XmlNamedStoredProcedureQuery>(XmlNamedStoredProcedureQuery.class, this, EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES);
		}
		return namedStoredProcedureQueries;
	}

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_Properties()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new EObjectContainmentEList<XmlProperty>(XmlProperty.class, this, EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES);
		}
		return properties;
	}

	/**
	 * Returns the value of the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Default Mappings</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Default Mappings</em>' attribute.
	 * @see #setExcludeDefaultMappings(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_ExcludeDefaultMappings()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getExcludeDefaultMappings()
	{
		return excludeDefaultMappings;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Default Mappings</em>' attribute.
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 */
	public void setExcludeDefaultMappings(Boolean newExcludeDefaultMappings)
	{
		Boolean oldExcludeDefaultMappings = excludeDefaultMappings;
		excludeDefaultMappings = newExcludeDefaultMappings;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS, oldExcludeDefaultMappings, excludeDefaultMappings));
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
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				return basicSetCustomizer(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING:
				return basicSetChangeTracking(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE:
				return basicSetCache(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS:
				return ((InternalEList<?>)getConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS:
				return ((InternalEList<?>)getTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS:
				return ((InternalEList<?>)getObjectTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS:
				return ((InternalEList<?>)getStructConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return basicSetOptimisticLocking(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return basicSetCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return basicSetInstantiationCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return basicSetCloneCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return ((InternalEList<?>)getNamedStoredProcedureQueries()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				return getReadOnly();
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				return getCustomizer();
			case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING:
				return getChangeTracking();
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE:
				return getCache();
			case EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING:
				return getExistenceChecking();
			case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS:
				return getConverters();
			case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS:
				return getTypeConverters();
			case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS:
				return getObjectTypeConverters();
			case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS:
				return getStructConverters();
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return getOptimisticLocking();
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return getCopyPolicy();
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return getInstantiationCopyPolicy();
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return getCloneCopyPolicy();
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return getNamedStoredProcedureQueries();
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return getProperties();
			case EclipseLinkOrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS:
				return getExcludeDefaultMappings();
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
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				setCustomizer((XmlCustomizer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING:
				setChangeTracking((XmlChangeTracking)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE:
				setCache((XmlCache)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING:
				setExistenceChecking((ExistenceType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS:
				getConverters().clear();
				getConverters().addAll((Collection<? extends XmlConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS:
				getTypeConverters().clear();
				getTypeConverters().addAll((Collection<? extends XmlTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				getObjectTypeConverters().addAll((Collection<? extends XmlObjectTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS:
				getStructConverters().clear();
				getStructConverters().addAll((Collection<? extends XmlStructConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				setOptimisticLocking((XmlOptimisticLocking)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				getNamedStoredProcedureQueries().addAll((Collection<? extends XmlNamedStoredProcedureQuery>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends XmlProperty>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS:
				setExcludeDefaultMappings((Boolean)newValue);
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
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				setCustomizer((XmlCustomizer)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING:
				setChangeTracking((XmlChangeTracking)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE:
				setCache((XmlCache)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING:
				setExistenceChecking(EXISTENCE_CHECKING_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS:
				getConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS:
				getTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS:
				getStructConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				setOptimisticLocking((XmlOptimisticLocking)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				getProperties().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS:
				setExcludeDefaultMappings(EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				return READ_ONLY_EDEFAULT == null ? readOnly != null : !READ_ONLY_EDEFAULT.equals(readOnly);
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				return customizer != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING:
				return changeTracking != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE:
				return cache != null;
			case EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING:
				return existenceChecking != EXISTENCE_CHECKING_EDEFAULT;
			case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS:
				return converters != null && !converters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS:
				return typeConverters != null && !typeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS:
				return objectTypeConverters != null && !objectTypeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS:
				return structConverters != null && !structConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return optimisticLocking != null;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return copyPolicy != null;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return instantiationCopyPolicy != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return cloneCopyPolicy != null;
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return namedStoredProcedureQueries != null && !namedStoredProcedureQueries.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS:
				return EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT == null ? excludeDefaultMappings != null : !EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT.equals(excludeDefaultMappings);
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
		if (baseClass == XmlReadOnly.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY: return EclipseLinkOrmPackage.XML_READ_ONLY__READ_ONLY;
				default: return -1;
			}
		}
		if (baseClass == XmlCustomizerHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER: return EclipseLinkOrmPackage.XML_CUSTOMIZER_HOLDER__CUSTOMIZER;
				default: return -1;
			}
		}
		if (baseClass == XmlChangeTrackingHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING: return EclipseLinkOrmPackage.XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING;
				default: return -1;
			}
		}
		if (baseClass == XmlCacheHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CACHE: return EclipseLinkOrmPackage.XML_CACHE_HOLDER__CACHE;
				case EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING: return EclipseLinkOrmPackage.XML_CACHE_HOLDER__EXISTENCE_CHECKING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS;
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
		if (baseClass == XmlReadOnly.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_READ_ONLY__READ_ONLY: return EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY;
				default: return -1;
			}
		}
		if (baseClass == XmlCustomizerHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CUSTOMIZER_HOLDER__CUSTOMIZER: return EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER;
				default: return -1;
			}
		}
		if (baseClass == XmlChangeTrackingHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING: return EclipseLinkOrmPackage.XML_ENTITY__CHANGE_TRACKING;
				default: return -1;
			}
		}
		if (baseClass == XmlCacheHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CACHE_HOLDER__CACHE: return EclipseLinkOrmPackage.XML_ENTITY__CACHE;
				case EclipseLinkOrmPackage.XML_CACHE_HOLDER__EXISTENCE_CHECKING: return EclipseLinkOrmPackage.XML_ENTITY__EXISTENCE_CHECKING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY__CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY__STRUCT_CONVERTERS;
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
		result.append(", existenceChecking: ");
		result.append(existenceChecking);
		result.append(", excludeDefaultMappings: ");
		result.append(excludeDefaultMappings);
		result.append(')');
		return result.toString();
	}

	public TextRange getReadOnlyTextRange() {
		return getAttributeTextRange(EclipseLinkOrmXmlMapper.READ_ONLY);
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCache_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Cache</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiry <em>Expiry</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getSize <em>Size</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getShared <em>Shared</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh <em>Always Refresh</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer <em>Refresh Only If Newer</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getDisableHits <em>Disable Hits</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getCoordinationType <em>Coordination Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay <em>Expiry Time Of Day</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache()
 * @model kind="class"
 * @generated
 */
public class XmlCache extends AbstractJpaEObject implements XmlCache_2_2, XmlCache_2_4
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final CacheIsolationType ISOLATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsolation() <em>Isolation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsolation()
	 * @generated
	 * @ordered
	 */
	protected CacheIsolationType isolation = ISOLATION_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final DatabaseChangeNotificationType DATABASE_CHANGE_NOTIFICATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDatabaseChangeNotificationType() <em>Database Change Notification Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDatabaseChangeNotificationType()
	 * @generated
	 * @ordered
	 */
	protected DatabaseChangeNotificationType databaseChangeNotificationType = DATABASE_CHANGE_NOTIFICATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getExpiry() <em>Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiry()
	 * @generated
	 * @ordered
	 */
	protected static final Integer EXPIRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpiry() <em>Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiry()
	 * @generated
	 * @ordered
	 */
	protected Integer expiry = EXPIRY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SIZE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected Integer size = SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getShared() <em>Shared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShared()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SHARED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getShared() <em>Shared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShared()
	 * @generated
	 * @ordered
	 */
	protected Boolean shared = SHARED_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final CacheType TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected CacheType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlwaysRefresh() <em>Always Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlwaysRefresh()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ALWAYS_REFRESH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlwaysRefresh() <em>Always Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlwaysRefresh()
	 * @generated
	 * @ordered
	 */
	protected Boolean alwaysRefresh = ALWAYS_REFRESH_EDEFAULT;

	/**
	 * The default value of the '{@link #getRefreshOnlyIfNewer() <em>Refresh Only If Newer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshOnlyIfNewer()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean REFRESH_ONLY_IF_NEWER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRefreshOnlyIfNewer() <em>Refresh Only If Newer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshOnlyIfNewer()
	 * @generated
	 * @ordered
	 */
	protected Boolean refreshOnlyIfNewer = REFRESH_ONLY_IF_NEWER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisableHits() <em>Disable Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisableHits()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean DISABLE_HITS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisableHits() <em>Disable Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisableHits()
	 * @generated
	 * @ordered
	 */
	protected Boolean disableHits = DISABLE_HITS_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final CacheCoordinationType COORDINATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCoordinationType() <em>Coordination Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoordinationType()
	 * @generated
	 * @ordered
	 */
	protected CacheCoordinationType coordinationType = COORDINATION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpiryTimeOfDay() <em>Expiry Time Of Day</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiryTimeOfDay()
	 * @generated
	 * @ordered
	 */
	protected XmlTimeOfDay expiryTimeOfDay;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlCache()
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
		return EclipseLinkOrmPackage.Literals.XML_CACHE;
	}

	/**
	 * Returns the value of the '<em><b>Isolation</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Isolation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isolation</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType
	 * @see #setIsolation(CacheIsolationType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_2_2_Isolation()
	 * @model default=""
	 * @generated
	 */
	public CacheIsolationType getIsolation()
	{
		return isolation;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getIsolation <em>Isolation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isolation</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType
	 * @see #getIsolation()
	 * @generated
	 */
	public void setIsolation(CacheIsolationType newIsolation)
	{
		CacheIsolationType oldIsolation = isolation;
		isolation = newIsolation == null ? ISOLATION_EDEFAULT : newIsolation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__ISOLATION, oldIsolation, isolation));
	}

	/**
	 * Returns the value of the '<em><b>Database Change Notification Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Database Change Notification Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Database Change Notification Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
	 * @see #setDatabaseChangeNotificationType(DatabaseChangeNotificationType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_2_4_DatabaseChangeNotificationType()
	 * @model default=""
	 * @generated
	 */
	public DatabaseChangeNotificationType getDatabaseChangeNotificationType()
	{
		return databaseChangeNotificationType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getDatabaseChangeNotificationType <em>Database Change Notification Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Database Change Notification Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
	 * @see #getDatabaseChangeNotificationType()
	 * @generated
	 */
	public void setDatabaseChangeNotificationType(DatabaseChangeNotificationType newDatabaseChangeNotificationType)
	{
		DatabaseChangeNotificationType oldDatabaseChangeNotificationType = databaseChangeNotificationType;
		databaseChangeNotificationType = newDatabaseChangeNotificationType == null ? DATABASE_CHANGE_NOTIFICATION_TYPE_EDEFAULT : newDatabaseChangeNotificationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE, oldDatabaseChangeNotificationType, databaseChangeNotificationType));
	}

	/**
	 * Returns the value of the '<em><b>Expiry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expiry</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiry</em>' attribute.
	 * @see #setExpiry(Integer)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_Expiry()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getExpiry()
	{
		return expiry;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiry <em>Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry</em>' attribute.
	 * @see #getExpiry()
	 * @generated
	 */
	public void setExpiry(Integer newExpiry)
	{
		Integer oldExpiry = expiry;
		expiry = newExpiry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__EXPIRY, oldExpiry, expiry));
	}

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(Integer)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_Size()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getSize()
	{
		return size;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	public void setSize(Integer newSize)
	{
		Integer oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__SIZE, oldSize, size));
	}

	/**
	 * Returns the value of the '<em><b>Shared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared</em>' attribute.
	 * @see #setShared(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_Shared()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getShared()
	{
		return shared;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getShared <em>Shared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared</em>' attribute.
	 * @see #getShared()
	 * @generated
	 */
	public void setShared(Boolean newShared)
	{
		Boolean oldShared = shared;
		shared = newShared;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__SHARED, oldShared, shared));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType
	 * @see #setType(CacheType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_Type()
	 * @model
	 * @generated
	 */
	public CacheType getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType
	 * @see #getType()
	 * @generated
	 */
	public void setType(CacheType newType)
	{
		CacheType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__TYPE, oldType, type));
	}

	/**
	 * Returns the value of the '<em><b>Always Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Always Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Always Refresh</em>' attribute.
	 * @see #setAlwaysRefresh(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_AlwaysRefresh()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getAlwaysRefresh()
	{
		return alwaysRefresh;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh <em>Always Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Always Refresh</em>' attribute.
	 * @see #getAlwaysRefresh()
	 * @generated
	 */
	public void setAlwaysRefresh(Boolean newAlwaysRefresh)
	{
		Boolean oldAlwaysRefresh = alwaysRefresh;
		alwaysRefresh = newAlwaysRefresh;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__ALWAYS_REFRESH, oldAlwaysRefresh, alwaysRefresh));
	}

	/**
	 * Returns the value of the '<em><b>Refresh Only If Newer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refresh Only If Newer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refresh Only If Newer</em>' attribute.
	 * @see #setRefreshOnlyIfNewer(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_RefreshOnlyIfNewer()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getRefreshOnlyIfNewer()
	{
		return refreshOnlyIfNewer;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer <em>Refresh Only If Newer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh Only If Newer</em>' attribute.
	 * @see #getRefreshOnlyIfNewer()
	 * @generated
	 */
	public void setRefreshOnlyIfNewer(Boolean newRefreshOnlyIfNewer)
	{
		Boolean oldRefreshOnlyIfNewer = refreshOnlyIfNewer;
		refreshOnlyIfNewer = newRefreshOnlyIfNewer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__REFRESH_ONLY_IF_NEWER, oldRefreshOnlyIfNewer, refreshOnlyIfNewer));
	}

	/**
	 * Returns the value of the '<em><b>Disable Hits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disable Hits</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disable Hits</em>' attribute.
	 * @see #setDisableHits(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_DisableHits()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getDisableHits()
	{
		return disableHits;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getDisableHits <em>Disable Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disable Hits</em>' attribute.
	 * @see #getDisableHits()
	 * @generated
	 */
	public void setDisableHits(Boolean newDisableHits)
	{
		Boolean oldDisableHits = disableHits;
		disableHits = newDisableHits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__DISABLE_HITS, oldDisableHits, disableHits));
	}

	/**
	 * Returns the value of the '<em><b>Coordination Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Coordination Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Coordination Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType
	 * @see #setCoordinationType(CacheCoordinationType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_CoordinationType()
	 * @model default=""
	 * @generated
	 */
	public CacheCoordinationType getCoordinationType()
	{
		return coordinationType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getCoordinationType <em>Coordination Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Coordination Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType
	 * @see #getCoordinationType()
	 * @generated
	 */
	public void setCoordinationType(CacheCoordinationType newCoordinationType)
	{
		CacheCoordinationType oldCoordinationType = coordinationType;
		coordinationType = newCoordinationType == null ? COORDINATION_TYPE_EDEFAULT : newCoordinationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__COORDINATION_TYPE, oldCoordinationType, coordinationType));
	}

	/**
	 * Returns the value of the '<em><b>Expiry Time Of Day</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expiry Time Of Day</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiry Time Of Day</em>' containment reference.
	 * @see #setExpiryTimeOfDay(XmlTimeOfDay)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache_ExpiryTimeOfDay()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTimeOfDay getExpiryTimeOfDay()
	{
		return expiryTimeOfDay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpiryTimeOfDay(XmlTimeOfDay newExpiryTimeOfDay, NotificationChain msgs)
	{
		XmlTimeOfDay oldExpiryTimeOfDay = expiryTimeOfDay;
		expiryTimeOfDay = newExpiryTimeOfDay;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY, oldExpiryTimeOfDay, newExpiryTimeOfDay);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay <em>Expiry Time Of Day</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry Time Of Day</em>' containment reference.
	 * @see #getExpiryTimeOfDay()
	 * @generated
	 */
	public void setExpiryTimeOfDay(XmlTimeOfDay newExpiryTimeOfDay)
	{
		if (newExpiryTimeOfDay != expiryTimeOfDay)
		{
			NotificationChain msgs = null;
			if (expiryTimeOfDay != null)
				msgs = ((InternalEObject)expiryTimeOfDay).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY, null, msgs);
			if (newExpiryTimeOfDay != null)
				msgs = ((InternalEObject)newExpiryTimeOfDay).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY, null, msgs);
			msgs = basicSetExpiryTimeOfDay(newExpiryTimeOfDay, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY, newExpiryTimeOfDay, newExpiryTimeOfDay));
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
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY:
				return basicSetExpiryTimeOfDay(null, msgs);
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
			case EclipseLinkOrmPackage.XML_CACHE__ISOLATION:
				return getIsolation();
			case EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE:
				return getDatabaseChangeNotificationType();
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY:
				return getExpiry();
			case EclipseLinkOrmPackage.XML_CACHE__SIZE:
				return getSize();
			case EclipseLinkOrmPackage.XML_CACHE__SHARED:
				return getShared();
			case EclipseLinkOrmPackage.XML_CACHE__TYPE:
				return getType();
			case EclipseLinkOrmPackage.XML_CACHE__ALWAYS_REFRESH:
				return getAlwaysRefresh();
			case EclipseLinkOrmPackage.XML_CACHE__REFRESH_ONLY_IF_NEWER:
				return getRefreshOnlyIfNewer();
			case EclipseLinkOrmPackage.XML_CACHE__DISABLE_HITS:
				return getDisableHits();
			case EclipseLinkOrmPackage.XML_CACHE__COORDINATION_TYPE:
				return getCoordinationType();
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY:
				return getExpiryTimeOfDay();
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
			case EclipseLinkOrmPackage.XML_CACHE__ISOLATION:
				setIsolation((CacheIsolationType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE:
				setDatabaseChangeNotificationType((DatabaseChangeNotificationType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY:
				setExpiry((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__SIZE:
				setSize((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__SHARED:
				setShared((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__TYPE:
				setType((CacheType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__ALWAYS_REFRESH:
				setAlwaysRefresh((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__REFRESH_ONLY_IF_NEWER:
				setRefreshOnlyIfNewer((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__DISABLE_HITS:
				setDisableHits((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__COORDINATION_TYPE:
				setCoordinationType((CacheCoordinationType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY:
				setExpiryTimeOfDay((XmlTimeOfDay)newValue);
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
			case EclipseLinkOrmPackage.XML_CACHE__ISOLATION:
				setIsolation(ISOLATION_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE:
				setDatabaseChangeNotificationType(DATABASE_CHANGE_NOTIFICATION_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY:
				setExpiry(EXPIRY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__SHARED:
				setShared(SHARED_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__ALWAYS_REFRESH:
				setAlwaysRefresh(ALWAYS_REFRESH_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__REFRESH_ONLY_IF_NEWER:
				setRefreshOnlyIfNewer(REFRESH_ONLY_IF_NEWER_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__DISABLE_HITS:
				setDisableHits(DISABLE_HITS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__COORDINATION_TYPE:
				setCoordinationType(COORDINATION_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY:
				setExpiryTimeOfDay((XmlTimeOfDay)null);
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
			case EclipseLinkOrmPackage.XML_CACHE__ISOLATION:
				return isolation != ISOLATION_EDEFAULT;
			case EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE:
				return databaseChangeNotificationType != DATABASE_CHANGE_NOTIFICATION_TYPE_EDEFAULT;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY:
				return EXPIRY_EDEFAULT == null ? expiry != null : !EXPIRY_EDEFAULT.equals(expiry);
			case EclipseLinkOrmPackage.XML_CACHE__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case EclipseLinkOrmPackage.XML_CACHE__SHARED:
				return SHARED_EDEFAULT == null ? shared != null : !SHARED_EDEFAULT.equals(shared);
			case EclipseLinkOrmPackage.XML_CACHE__TYPE:
				return type != TYPE_EDEFAULT;
			case EclipseLinkOrmPackage.XML_CACHE__ALWAYS_REFRESH:
				return ALWAYS_REFRESH_EDEFAULT == null ? alwaysRefresh != null : !ALWAYS_REFRESH_EDEFAULT.equals(alwaysRefresh);
			case EclipseLinkOrmPackage.XML_CACHE__REFRESH_ONLY_IF_NEWER:
				return REFRESH_ONLY_IF_NEWER_EDEFAULT == null ? refreshOnlyIfNewer != null : !REFRESH_ONLY_IF_NEWER_EDEFAULT.equals(refreshOnlyIfNewer);
			case EclipseLinkOrmPackage.XML_CACHE__DISABLE_HITS:
				return DISABLE_HITS_EDEFAULT == null ? disableHits != null : !DISABLE_HITS_EDEFAULT.equals(disableHits);
			case EclipseLinkOrmPackage.XML_CACHE__COORDINATION_TYPE:
				return coordinationType != COORDINATION_TYPE_EDEFAULT;
			case EclipseLinkOrmPackage.XML_CACHE__EXPIRY_TIME_OF_DAY:
				return expiryTimeOfDay != null;
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
		if (baseClass == XmlCache_2_4.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE: return EclipseLinkOrmV2_4Package.XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE;
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
		if (baseClass == XmlCache_2_4.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_4Package.XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE: return EclipseLinkOrmPackage.XML_CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE;
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
		result.append(" (isolation: ");
		result.append(isolation);
		result.append(", databaseChangeNotificationType: ");
		result.append(databaseChangeNotificationType);
		result.append(", expiry: ");
		result.append(expiry);
		result.append(", size: ");
		result.append(size);
		result.append(", shared: ");
		result.append(shared);
		result.append(", type: ");
		result.append(type);
		result.append(", alwaysRefresh: ");
		result.append(alwaysRefresh);
		result.append(", refreshOnlyIfNewer: ");
		result.append(refreshOnlyIfNewer);
		result.append(", disableHits: ");
		result.append(disableHits);
		result.append(", coordinationType: ");
		result.append(coordinationType);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildExpiryTranslator(),
			buildExpiryTimeOfDayTranslator(),
			buildSizeTranslator(),
			buildSharedTranslator(),
			buildIsolationTranslator(),
			buildTypeTranslator(),
			buildAlwaysRefreshTranslator(),
			buildRefreshOnlyIfNewerTranslator(),
			buildDisableHitsTranslator(),
			buildCoordinationTypeTranslator(),
			buildDatabaseChangeNotificationTypeTranslator(),
		};
	}

	protected static Translator buildExpiryTranslator() {
		return new Translator(EclipseLink.CACHE__EXPIRY, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_Expiry());
	}
	
	protected static Translator buildExpiryTimeOfDayTranslator() {
		return XmlTimeOfDay.buildTranslator(EclipseLink.EXPIRY_TIME_OF_DAY, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_ExpiryTimeOfDay());
	}
	
	protected static Translator buildSizeTranslator() {
		return new Translator(EclipseLink.CACHE__SIZE, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_Size(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildSharedTranslator() {
		return new Translator(EclipseLink.CACHE__SHARED, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_Shared(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildTypeTranslator() {
		return new Translator(EclipseLink.CACHE__TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_Type(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildAlwaysRefreshTranslator() {
		return new Translator(EclipseLink.CACHE__ALWAYS_REFRESH, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_AlwaysRefresh(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildRefreshOnlyIfNewerTranslator() {
		return new Translator(EclipseLink.CACHE__REFRESH_ONLY_IF_NEWER, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_RefreshOnlyIfNewer(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDisableHitsTranslator() {
		return new Translator(EclipseLink.CACHE__DISABLE_HITS, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_DisableHits(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildCoordinationTypeTranslator() {
		return new Translator(EclipseLink.CACHE__COORDINATION_TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlCache_CoordinationType(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildIsolationTranslator() {
		return new Translator(EclipseLink2_2.CACHE__ISOLATION, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlCache_2_2_Isolation(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDatabaseChangeNotificationTypeTranslator() {
		return new Translator(EclipseLink2_4.CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlCache_2_4_DatabaseChangeNotificationType(), Translator.DOM_ATTRIBUTE);
	}

} // XmlCache

/*******************************************************************************
 *  Copyright (c) 2008, 2010  Oracle. 
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
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlEntity_2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2;
import org.eclipse.wst.common.internal.emf.resource.Translator;

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
 * @version 2.3
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
 * @model kind="class"
 * @generated
 */
public class XmlEntity extends org.eclipse.jpt.core.resource.orm.XmlEntity implements XmlEntity_1_1, XmlEntity_2_0, XmlEntity_2_1, XmlEntity_2_2, XmlReadOnly, XmlCustomizerHolder, XmlChangeTrackingHolder, XmlCacheHolder, XmlConvertersHolder, XmlQueryContainer, XmlPropertyContainer
{
	/**
	 * The cached value of the '{@link #getPrimaryKey() <em>Primary Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKey()
	 * @generated
	 * @ordered
	 */
	protected XmlPrimaryKey primaryKey;

	/**
	 * The cached value of the '{@link #getCacheInterceptor() <em>Cache Interceptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheInterceptor()
	 * @generated
	 * @ordered
	 */
	protected XmlClassReference cacheInterceptor;

	/**
	 * The cached value of the '{@link #getQueryRedirectors() <em>Query Redirectors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryRedirectors()
	 * @generated
	 * @ordered
	 */
	protected XmlQueryRedirectors queryRedirectors;

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
	 * The cached value of the '{@link #getFetchGroups() <em>Fetch Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetchGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlFetchGroup> fetchGroups;

	/**
	 * The cached value of the '{@link #getClassExtractor() <em>Class Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassExtractor()
	 * @generated
	 * @ordered
	 */
	protected XmlClassReference classExtractor;

	/**
	 * The default value of the '{@link #getParentClass() <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClass()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentClass() <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClass()
	 * @generated
	 * @ordered
	 */
	protected String parentClass = PARENT_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPartitioning() <em>Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlPartitioning_2_2 partitioning;

	/**
	 * The cached value of the '{@link #getReplicationPartitioning() <em>Replication Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplicationPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlReplicationPartitioning_2_2 replicationPartitioning;

	/**
	 * The cached value of the '{@link #getRoundRobinPartitioning() <em>Round Robin Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlRoundRobinPartitioning_2_2 roundRobinPartitioning;

	/**
	 * The cached value of the '{@link #getPinnedPartitioning() <em>Pinned Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinnedPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlPinnedPartitioning_2_2 pinnedPartitioning;

	/**
	 * The cached value of the '{@link #getRangePartitioning() <em>Range Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRangePartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlRangePartitioning_2_2 rangePartitioning;

	/**
	 * The cached value of the '{@link #getValuePartitioning() <em>Value Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuePartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlValuePartitioning_2_2 valuePartitioning;

	/**
	 * The cached value of the '{@link #getHashPartitioning() <em>Hash Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlHashPartitioning_2_2 hashPartitioning;

	/**
	 * The cached value of the '{@link #getUnionPartitioning() <em>Union Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnionPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlUnionPartitioning_2_2 unionPartitioning;

	/**
	 * The default value of the '{@link #getPartitioned() <em>Partitioned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitioned()
	 * @generated
	 * @ordered
	 */
	protected static final String PARTITIONED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPartitioned() <em>Partitioned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitioned()
	 * @generated
	 * @ordered
	 */
	protected String partitioned = PARTITIONED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAdditionalCriteria() <em>Additional Criteria</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalCriteria()
	 * @generated
	 * @ordered
	 */
	protected XmlAdditionalCriteria_2_2 additionalCriteria;

	/**
	 * The default value of the '{@link #getCascadeOnDelete() <em>Cascade On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeOnDelete()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CASCADE_ON_DELETE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCascadeOnDelete() <em>Cascade On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadeOnDelete()
	 * @generated
	 * @ordered
	 */
	protected Boolean cascadeOnDelete = CASCADE_ON_DELETE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected XmlIndex_2_2 index;

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
	protected XmlClassReference customizer;

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
	protected XmlInstantiationCopyPolicy instantiationCopyPolicy;

	/**
	 * The cached value of the '{@link #getCloneCopyPolicy() <em>Clone Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCloneCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCloneCopyPolicy cloneCopyPolicy;

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
	 * @see #setCustomizer(XmlClassReference)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder_Customizer()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getCustomizer()
	{
		return customizer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomizer(XmlClassReference newCustomizer, NotificationChain msgs)
	{
		XmlClassReference oldCustomizer = customizer;
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
	public void setCustomizer(XmlClassReference newCustomizer)
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
	 * @see #setInstantiationCopyPolicy(XmlInstantiationCopyPolicy)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_InstantiationCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlInstantiationCopyPolicy getInstantiationCopyPolicy()
	{
		return instantiationCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInstantiationCopyPolicy(XmlInstantiationCopyPolicy newInstantiationCopyPolicy, NotificationChain msgs)
	{
		XmlInstantiationCopyPolicy oldInstantiationCopyPolicy = instantiationCopyPolicy;
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
	public void setInstantiationCopyPolicy(XmlInstantiationCopyPolicy newInstantiationCopyPolicy)
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
	 * @see #setCloneCopyPolicy(XmlCloneCopyPolicy)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_CloneCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCloneCopyPolicy getCloneCopyPolicy()
	{
		return cloneCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCloneCopyPolicy(XmlCloneCopyPolicy newCloneCopyPolicy, NotificationChain msgs)
	{
		XmlCloneCopyPolicy oldCloneCopyPolicy = cloneCopyPolicy;
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
	public void setCloneCopyPolicy(XmlCloneCopyPolicy newCloneCopyPolicy)
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryContainer_NamedStoredProcedureQueries()
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPropertyContainer_Properties()
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
	 * Returns the value of the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' containment reference.
	 * @see #setPrimaryKey(XmlPrimaryKey)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_1_1_PrimaryKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPrimaryKey getPrimaryKey()
	{
		return primaryKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryKey(XmlPrimaryKey newPrimaryKey, NotificationChain msgs)
	{
		XmlPrimaryKey oldPrimaryKey = primaryKey;
		primaryKey = newPrimaryKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY, oldPrimaryKey, newPrimaryKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getPrimaryKey <em>Primary Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' containment reference.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	public void setPrimaryKey(XmlPrimaryKey newPrimaryKey)
	{
		if (newPrimaryKey != primaryKey)
		{
			NotificationChain msgs = null;
			if (primaryKey != null)
				msgs = ((InternalEObject)primaryKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY, null, msgs);
			if (newPrimaryKey != null)
				msgs = ((InternalEObject)newPrimaryKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY, null, msgs);
			msgs = basicSetPrimaryKey(newPrimaryKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY, newPrimaryKey, newPrimaryKey));
	}

	/**
	 * Returns the value of the '<em><b>Cache Interceptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Interceptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Interceptor</em>' containment reference.
	 * @see #setCacheInterceptor(XmlClassReference)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_0_CacheInterceptor()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getCacheInterceptor()
	{
		return cacheInterceptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheInterceptor(XmlClassReference newCacheInterceptor, NotificationChain msgs)
	{
		XmlClassReference oldCacheInterceptor = cacheInterceptor;
		cacheInterceptor = newCacheInterceptor;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR, oldCacheInterceptor, newCacheInterceptor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCacheInterceptor <em>Cache Interceptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Interceptor</em>' containment reference.
	 * @see #getCacheInterceptor()
	 * @generated
	 */
	public void setCacheInterceptor(XmlClassReference newCacheInterceptor)
	{
		if (newCacheInterceptor != cacheInterceptor)
		{
			NotificationChain msgs = null;
			if (cacheInterceptor != null)
				msgs = ((InternalEObject)cacheInterceptor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR, null, msgs);
			if (newCacheInterceptor != null)
				msgs = ((InternalEObject)newCacheInterceptor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR, null, msgs);
			msgs = basicSetCacheInterceptor(newCacheInterceptor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR, newCacheInterceptor, newCacheInterceptor));
	}

	/**
	 * Returns the value of the '<em><b>Query Redirectors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Redirectors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Redirectors</em>' containment reference.
	 * @see #setQueryRedirectors(XmlQueryRedirectors)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_0_QueryRedirectors()
	 * @model containment="true"
	 * @generated
	 */
	public XmlQueryRedirectors getQueryRedirectors()
	{
		return queryRedirectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueryRedirectors(XmlQueryRedirectors newQueryRedirectors, NotificationChain msgs)
	{
		XmlQueryRedirectors oldQueryRedirectors = queryRedirectors;
		queryRedirectors = newQueryRedirectors;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS, oldQueryRedirectors, newQueryRedirectors);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getQueryRedirectors <em>Query Redirectors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Redirectors</em>' containment reference.
	 * @see #getQueryRedirectors()
	 * @generated
	 */
	public void setQueryRedirectors(XmlQueryRedirectors newQueryRedirectors)
	{
		if (newQueryRedirectors != queryRedirectors)
		{
			NotificationChain msgs = null;
			if (queryRedirectors != null)
				msgs = ((InternalEObject)queryRedirectors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS, null, msgs);
			if (newQueryRedirectors != null)
				msgs = ((InternalEObject)newQueryRedirectors).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS, null, msgs);
			msgs = basicSetQueryRedirectors(newQueryRedirectors, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS, newQueryRedirectors, newQueryRedirectors));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder_AccessMethods()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getAccessMethods <em>Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS, newAccessMethods, newAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch Groups</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroupContainer_2_1_FetchGroups()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlFetchGroup> getFetchGroups()
	{
		if (fetchGroups == null)
		{
			fetchGroups = new EObjectContainmentEList<XmlFetchGroup>(XmlFetchGroup.class, this, EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS);
		}
		return fetchGroups;
	}

	/**
	 * Returns the value of the '<em><b>Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Extractor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Extractor</em>' containment reference.
	 * @see #setClassExtractor(XmlClassReference)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_1_ClassExtractor()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getClassExtractor()
	{
		return classExtractor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClassExtractor(XmlClassReference newClassExtractor, NotificationChain msgs)
	{
		XmlClassReference oldClassExtractor = classExtractor;
		classExtractor = newClassExtractor;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR, oldClassExtractor, newClassExtractor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getClassExtractor <em>Class Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Extractor</em>' containment reference.
	 * @see #getClassExtractor()
	 * @generated
	 */
	public void setClassExtractor(XmlClassReference newClassExtractor)
	{
		if (newClassExtractor != classExtractor)
		{
			NotificationChain msgs = null;
			if (classExtractor != null)
				msgs = ((InternalEObject)classExtractor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR, null, msgs);
			if (newClassExtractor != null)
				msgs = ((InternalEObject)newClassExtractor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR, null, msgs);
			msgs = basicSetClassExtractor(newClassExtractor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR, newClassExtractor, newClassExtractor));
	}

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' attribute.
	 * @see #setParentClass(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_1_ParentClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getParentClass()
	{
		return parentClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getParentClass <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' attribute.
	 * @see #getParentClass()
	 * @generated
	 */
	public void setParentClass(String newParentClass)
	{
		String oldParentClass = parentClass;
		parentClass = newParentClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS, oldParentClass, parentClass));
	}

	/**
	 * Returns the value of the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partitioning</em>' containment reference.
	 * @see #setPartitioning(XmlPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_Partitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPartitioning_2_2 getPartitioning()
	{
		return partitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPartitioning(XmlPartitioning_2_2 newPartitioning, NotificationChain msgs)
	{
		XmlPartitioning_2_2 oldPartitioning = partitioning;
		partitioning = newPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING, oldPartitioning, newPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getPartitioning <em>Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioning</em>' containment reference.
	 * @see #getPartitioning()
	 * @generated
	 */
	public void setPartitioning(XmlPartitioning_2_2 newPartitioning)
	{
		if (newPartitioning != partitioning)
		{
			NotificationChain msgs = null;
			if (partitioning != null)
				msgs = ((InternalEObject)partitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING, null, msgs);
			if (newPartitioning != null)
				msgs = ((InternalEObject)newPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING, null, msgs);
			msgs = basicSetPartitioning(newPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING, newPartitioning, newPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replication Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replication Partitioning</em>' containment reference.
	 * @see #setReplicationPartitioning(XmlReplicationPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_ReplicationPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlReplicationPartitioning_2_2 getReplicationPartitioning()
	{
		return replicationPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReplicationPartitioning(XmlReplicationPartitioning_2_2 newReplicationPartitioning, NotificationChain msgs)
	{
		XmlReplicationPartitioning_2_2 oldReplicationPartitioning = replicationPartitioning;
		replicationPartitioning = newReplicationPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING, oldReplicationPartitioning, newReplicationPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getReplicationPartitioning <em>Replication Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replication Partitioning</em>' containment reference.
	 * @see #getReplicationPartitioning()
	 * @generated
	 */
	public void setReplicationPartitioning(XmlReplicationPartitioning_2_2 newReplicationPartitioning)
	{
		if (newReplicationPartitioning != replicationPartitioning)
		{
			NotificationChain msgs = null;
			if (replicationPartitioning != null)
				msgs = ((InternalEObject)replicationPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING, null, msgs);
			if (newReplicationPartitioning != null)
				msgs = ((InternalEObject)newReplicationPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING, null, msgs);
			msgs = basicSetReplicationPartitioning(newReplicationPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING, newReplicationPartitioning, newReplicationPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Round Robin Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Round Robin Partitioning</em>' containment reference.
	 * @see #setRoundRobinPartitioning(XmlRoundRobinPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_RoundRobinPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlRoundRobinPartitioning_2_2 getRoundRobinPartitioning()
	{
		return roundRobinPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoundRobinPartitioning(XmlRoundRobinPartitioning_2_2 newRoundRobinPartitioning, NotificationChain msgs)
	{
		XmlRoundRobinPartitioning_2_2 oldRoundRobinPartitioning = roundRobinPartitioning;
		roundRobinPartitioning = newRoundRobinPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING, oldRoundRobinPartitioning, newRoundRobinPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Round Robin Partitioning</em>' containment reference.
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 */
	public void setRoundRobinPartitioning(XmlRoundRobinPartitioning_2_2 newRoundRobinPartitioning)
	{
		if (newRoundRobinPartitioning != roundRobinPartitioning)
		{
			NotificationChain msgs = null;
			if (roundRobinPartitioning != null)
				msgs = ((InternalEObject)roundRobinPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING, null, msgs);
			if (newRoundRobinPartitioning != null)
				msgs = ((InternalEObject)newRoundRobinPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING, null, msgs);
			msgs = basicSetRoundRobinPartitioning(newRoundRobinPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING, newRoundRobinPartitioning, newRoundRobinPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pinned Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pinned Partitioning</em>' containment reference.
	 * @see #setPinnedPartitioning(XmlPinnedPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_PinnedPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPinnedPartitioning_2_2 getPinnedPartitioning()
	{
		return pinnedPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPinnedPartitioning(XmlPinnedPartitioning_2_2 newPinnedPartitioning, NotificationChain msgs)
	{
		XmlPinnedPartitioning_2_2 oldPinnedPartitioning = pinnedPartitioning;
		pinnedPartitioning = newPinnedPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING, oldPinnedPartitioning, newPinnedPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getPinnedPartitioning <em>Pinned Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pinned Partitioning</em>' containment reference.
	 * @see #getPinnedPartitioning()
	 * @generated
	 */
	public void setPinnedPartitioning(XmlPinnedPartitioning_2_2 newPinnedPartitioning)
	{
		if (newPinnedPartitioning != pinnedPartitioning)
		{
			NotificationChain msgs = null;
			if (pinnedPartitioning != null)
				msgs = ((InternalEObject)pinnedPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING, null, msgs);
			if (newPinnedPartitioning != null)
				msgs = ((InternalEObject)newPinnedPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING, null, msgs);
			msgs = basicSetPinnedPartitioning(newPinnedPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING, newPinnedPartitioning, newPinnedPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Range Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Range Partitioning</em>' containment reference.
	 * @see #setRangePartitioning(XmlRangePartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_RangePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlRangePartitioning_2_2 getRangePartitioning()
	{
		return rangePartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRangePartitioning(XmlRangePartitioning_2_2 newRangePartitioning, NotificationChain msgs)
	{
		XmlRangePartitioning_2_2 oldRangePartitioning = rangePartitioning;
		rangePartitioning = newRangePartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING, oldRangePartitioning, newRangePartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getRangePartitioning <em>Range Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range Partitioning</em>' containment reference.
	 * @see #getRangePartitioning()
	 * @generated
	 */
	public void setRangePartitioning(XmlRangePartitioning_2_2 newRangePartitioning)
	{
		if (newRangePartitioning != rangePartitioning)
		{
			NotificationChain msgs = null;
			if (rangePartitioning != null)
				msgs = ((InternalEObject)rangePartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING, null, msgs);
			if (newRangePartitioning != null)
				msgs = ((InternalEObject)newRangePartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING, null, msgs);
			msgs = basicSetRangePartitioning(newRangePartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING, newRangePartitioning, newRangePartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Partitioning</em>' containment reference.
	 * @see #setValuePartitioning(XmlValuePartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_ValuePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlValuePartitioning_2_2 getValuePartitioning()
	{
		return valuePartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValuePartitioning(XmlValuePartitioning_2_2 newValuePartitioning, NotificationChain msgs)
	{
		XmlValuePartitioning_2_2 oldValuePartitioning = valuePartitioning;
		valuePartitioning = newValuePartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING, oldValuePartitioning, newValuePartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getValuePartitioning <em>Value Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Partitioning</em>' containment reference.
	 * @see #getValuePartitioning()
	 * @generated
	 */
	public void setValuePartitioning(XmlValuePartitioning_2_2 newValuePartitioning)
	{
		if (newValuePartitioning != valuePartitioning)
		{
			NotificationChain msgs = null;
			if (valuePartitioning != null)
				msgs = ((InternalEObject)valuePartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING, null, msgs);
			if (newValuePartitioning != null)
				msgs = ((InternalEObject)newValuePartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING, null, msgs);
			msgs = basicSetValuePartitioning(newValuePartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING, newValuePartitioning, newValuePartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hash Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hash Partitioning</em>' containment reference.
	 * @see #setHashPartitioning(XmlHashPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_HashPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlHashPartitioning_2_2 getHashPartitioning()
	{
		return hashPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHashPartitioning(XmlHashPartitioning_2_2 newHashPartitioning, NotificationChain msgs)
	{
		XmlHashPartitioning_2_2 oldHashPartitioning = hashPartitioning;
		hashPartitioning = newHashPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING, oldHashPartitioning, newHashPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getHashPartitioning <em>Hash Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash Partitioning</em>' containment reference.
	 * @see #getHashPartitioning()
	 * @generated
	 */
	public void setHashPartitioning(XmlHashPartitioning_2_2 newHashPartitioning)
	{
		if (newHashPartitioning != hashPartitioning)
		{
			NotificationChain msgs = null;
			if (hashPartitioning != null)
				msgs = ((InternalEObject)hashPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING, null, msgs);
			if (newHashPartitioning != null)
				msgs = ((InternalEObject)newHashPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING, null, msgs);
			msgs = basicSetHashPartitioning(newHashPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING, newHashPartitioning, newHashPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Union Partitioning</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Union Partitioning</em>' containment reference.
	 * @see #setUnionPartitioning(XmlUnionPartitioning_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_UnionPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlUnionPartitioning_2_2 getUnionPartitioning()
	{
		return unionPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnionPartitioning(XmlUnionPartitioning_2_2 newUnionPartitioning, NotificationChain msgs)
	{
		XmlUnionPartitioning_2_2 oldUnionPartitioning = unionPartitioning;
		unionPartitioning = newUnionPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING, oldUnionPartitioning, newUnionPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getUnionPartitioning <em>Union Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Union Partitioning</em>' containment reference.
	 * @see #getUnionPartitioning()
	 * @generated
	 */
	public void setUnionPartitioning(XmlUnionPartitioning_2_2 newUnionPartitioning)
	{
		if (newUnionPartitioning != unionPartitioning)
		{
			NotificationChain msgs = null;
			if (unionPartitioning != null)
				msgs = ((InternalEObject)unionPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING, null, msgs);
			if (newUnionPartitioning != null)
				msgs = ((InternalEObject)newUnionPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING, null, msgs);
			msgs = basicSetUnionPartitioning(newUnionPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING, newUnionPartitioning, newUnionPartitioning));
	}

	/**
	 * Returns the value of the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partitioned</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partitioned</em>' attribute.
	 * @see #setPartitioned(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_Partitioned()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPartitioned()
	{
		return partitioned;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getPartitioned <em>Partitioned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioned</em>' attribute.
	 * @see #getPartitioned()
	 * @generated
	 */
	public void setPartitioned(String newPartitioned)
	{
		String oldPartitioned = partitioned;
		partitioned = newPartitioned;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED, oldPartitioned, partitioned));
	}

	/**
	 * Returns the value of the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade On Delete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #setCascadeOnDelete(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_2_CascadeOnDelete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getCascadeOnDelete()
	{
		return cascadeOnDelete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getCascadeOnDelete <em>Cascade On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #getCascadeOnDelete()
	 * @generated
	 */
	public void setCascadeOnDelete(Boolean newCascadeOnDelete)
	{
		Boolean oldCascadeOnDelete = cascadeOnDelete;
		cascadeOnDelete = newCascadeOnDelete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE, oldCascadeOnDelete, cascadeOnDelete));
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
	 * @see #setIndex(XmlIndex_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_2_Index()
	 * @model containment="true"
	 * @generated
	 */
	public XmlIndex_2_2 getIndex()
	{
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndex(XmlIndex_2_2 newIndex, NotificationChain msgs)
	{
		XmlIndex_2_2 oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__INDEX, oldIndex, newIndex);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getIndex <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' containment reference.
	 * @see #getIndex()
	 * @generated
	 */
	public void setIndex(XmlIndex_2_2 newIndex)
	{
		if (newIndex != index)
		{
			NotificationChain msgs = null;
			if (index != null)
				msgs = ((InternalEObject)index).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__INDEX, null, msgs);
			if (newIndex != null)
				msgs = ((InternalEObject)newIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__INDEX, null, msgs);
			msgs = basicSetIndex(newIndex, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__INDEX, newIndex, newIndex));
	}

	/**
	 * Returns the value of the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Criteria</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #setAdditionalCriteria(XmlAdditionalCriteria_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity_2_2_AdditionalCriteria()
	 * @model containment="true"
	 * @generated
	 */
	public XmlAdditionalCriteria_2_2 getAdditionalCriteria()
	{
		return additionalCriteria;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdditionalCriteria(XmlAdditionalCriteria_2_2 newAdditionalCriteria, NotificationChain msgs)
	{
		XmlAdditionalCriteria_2_2 oldAdditionalCriteria = additionalCriteria;
		additionalCriteria = newAdditionalCriteria;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA, oldAdditionalCriteria, newAdditionalCriteria);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity#getAdditionalCriteria <em>Additional Criteria</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #getAdditionalCriteria()
	 * @generated
	 */
	public void setAdditionalCriteria(XmlAdditionalCriteria_2_2 newAdditionalCriteria)
	{
		if (newAdditionalCriteria != additionalCriteria)
		{
			NotificationChain msgs = null;
			if (additionalCriteria != null)
				msgs = ((InternalEObject)additionalCriteria).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA, null, msgs);
			if (newAdditionalCriteria != null)
				msgs = ((InternalEObject)newAdditionalCriteria).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA, null, msgs);
			msgs = basicSetAdditionalCriteria(newAdditionalCriteria, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA, newAdditionalCriteria, newAdditionalCriteria));
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
			case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY:
				return basicSetPrimaryKey(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR:
				return basicSetCacheInterceptor(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS:
				return basicSetQueryRedirectors(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS:
				return ((InternalEList<?>)getFetchGroups()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR:
				return basicSetClassExtractor(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING:
				return basicSetPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING:
				return basicSetReplicationPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING:
				return basicSetRoundRobinPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING:
				return basicSetPinnedPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING:
				return basicSetRangePartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING:
				return basicSetValuePartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING:
				return basicSetHashPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING:
				return basicSetUnionPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA:
				return basicSetAdditionalCriteria(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__INDEX:
				return basicSetIndex(null, msgs);
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
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return ((InternalEList<?>)getNamedStoredProcedureQueries()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return basicSetOptimisticLocking(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return basicSetCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return basicSetInstantiationCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return basicSetCloneCopyPolicy(null, msgs);
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
			case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY:
				return getPrimaryKey();
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR:
				return getCacheInterceptor();
			case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS:
				return getQueryRedirectors();
			case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS:
				return getFetchGroups();
			case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR:
				return getClassExtractor();
			case EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS:
				return getParentClass();
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING:
				return getPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING:
				return getReplicationPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING:
				return getRoundRobinPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING:
				return getPinnedPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING:
				return getRangePartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING:
				return getValuePartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING:
				return getHashPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING:
				return getUnionPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED:
				return getPartitioned();
			case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA:
				return getAdditionalCriteria();
			case EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE:
				return getCascadeOnDelete();
			case EclipseLinkOrmPackage.XML_ENTITY__INDEX:
				return getIndex();
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
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return getNamedStoredProcedureQueries();
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return getProperties();
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return getOptimisticLocking();
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return getCopyPolicy();
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return getInstantiationCopyPolicy();
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return getCloneCopyPolicy();
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
			case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY:
				setPrimaryKey((XmlPrimaryKey)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR:
				setCacheInterceptor((XmlClassReference)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS:
				setQueryRedirectors((XmlQueryRedirectors)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS:
				getFetchGroups().clear();
				getFetchGroups().addAll((Collection<? extends XmlFetchGroup>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR:
				setClassExtractor((XmlClassReference)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS:
				setParentClass((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING:
				setPartitioning((XmlPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING:
				setReplicationPartitioning((XmlReplicationPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING:
				setRoundRobinPartitioning((XmlRoundRobinPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING:
				setPinnedPartitioning((XmlPinnedPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING:
				setRangePartitioning((XmlRangePartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING:
				setValuePartitioning((XmlValuePartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING:
				setHashPartitioning((XmlHashPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING:
				setUnionPartitioning((XmlUnionPartitioning_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED:
				setPartitioned((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA:
				setAdditionalCriteria((XmlAdditionalCriteria_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE:
				setCascadeOnDelete((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INDEX:
				setIndex((XmlIndex_2_2)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				setCustomizer((XmlClassReference)newValue);
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
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				getNamedStoredProcedureQueries().addAll((Collection<? extends XmlNamedStoredProcedureQuery>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends XmlProperty>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				setOptimisticLocking((XmlOptimisticLocking)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlInstantiationCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCloneCopyPolicy)newValue);
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
			case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY:
				setPrimaryKey((XmlPrimaryKey)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR:
				setCacheInterceptor((XmlClassReference)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS:
				setQueryRedirectors((XmlQueryRedirectors)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS:
				getFetchGroups().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR:
				setClassExtractor((XmlClassReference)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS:
				setParentClass(PARENT_CLASS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING:
				setPartitioning((XmlPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING:
				setReplicationPartitioning((XmlReplicationPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING:
				setRoundRobinPartitioning((XmlRoundRobinPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING:
				setPinnedPartitioning((XmlPinnedPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING:
				setRangePartitioning((XmlRangePartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING:
				setValuePartitioning((XmlValuePartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING:
				setHashPartitioning((XmlHashPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING:
				setUnionPartitioning((XmlUnionPartitioning_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED:
				setPartitioned(PARTITIONED_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA:
				setAdditionalCriteria((XmlAdditionalCriteria_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE:
				setCascadeOnDelete(CASCADE_ON_DELETE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INDEX:
				setIndex((XmlIndex_2_2)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CUSTOMIZER:
				setCustomizer((XmlClassReference)null);
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
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				getProperties().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				setOptimisticLocking((XmlOptimisticLocking)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlInstantiationCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCloneCopyPolicy)null);
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
			case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY:
				return primaryKey != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR:
				return cacheInterceptor != null;
			case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS:
				return queryRedirectors != null;
			case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS:
				return fetchGroups != null && !fetchGroups.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR:
				return classExtractor != null;
			case EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS:
				return PARENT_CLASS_EDEFAULT == null ? parentClass != null : !PARENT_CLASS_EDEFAULT.equals(parentClass);
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING:
				return partitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING:
				return replicationPartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING:
				return roundRobinPartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING:
				return pinnedPartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING:
				return rangePartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING:
				return valuePartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING:
				return hashPartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING:
				return unionPartitioning != null;
			case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED:
				return PARTITIONED_EDEFAULT == null ? partitioned != null : !PARTITIONED_EDEFAULT.equals(partitioned);
			case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA:
				return additionalCriteria != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE:
				return CASCADE_ON_DELETE_EDEFAULT == null ? cascadeOnDelete != null : !CASCADE_ON_DELETE_EDEFAULT.equals(cascadeOnDelete);
			case EclipseLinkOrmPackage.XML_ENTITY__INDEX:
				return index != null;
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
			case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return namedStoredProcedureQueries != null && !namedStoredProcedureQueries.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY__OPTIMISTIC_LOCKING:
				return optimisticLocking != null;
			case EclipseLinkOrmPackage.XML_ENTITY__COPY_POLICY:
				return copyPolicy != null;
			case EclipseLinkOrmPackage.XML_ENTITY__INSTANTIATION_COPY_POLICY:
				return instantiationCopyPolicy != null;
			case EclipseLinkOrmPackage.XML_ENTITY__CLONE_COPY_POLICY:
				return cloneCopyPolicy != null;
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
		if (baseClass == XmlEntity_1_1.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY: return EclipseLinkOrmV1_1Package.XML_ENTITY_11__PRIMARY_KEY;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR: return EclipseLinkOrmV2_0Package.XML_ENTITY_20__CACHE_INTERCEPTOR;
				case EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS: return EclipseLinkOrmV2_0Package.XML_ENTITY_20__QUERY_REDIRECTORS;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlFetchGroupContainer_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS: return EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_CONTAINER_21__FETCH_GROUPS;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR: return EclipseLinkOrmV2_1Package.XML_ENTITY_21__CLASS_EXTRACTOR;
				case EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS: return EclipseLinkOrmV2_1Package.XML_ENTITY_21__PARENT_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlPartitioningGroup_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONED;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA: return EclipseLinkOrmV2_2Package.XML_ENTITY_22__ADDITIONAL_CRITERIA;
				case EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE: return EclipseLinkOrmV2_2Package.XML_ENTITY_22__CASCADE_ON_DELETE;
				case EclipseLinkOrmPackage.XML_ENTITY__INDEX: return EclipseLinkOrmV2_2Package.XML_ENTITY_22__INDEX;
				default: return -1;
			}
		}
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
		if (baseClass == XmlQueryContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES: return EclipseLinkOrmPackage.XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES: return EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES;
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
		if (baseClass == XmlEntity_1_1.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV1_1Package.XML_ENTITY_11__PRIMARY_KEY: return EclipseLinkOrmPackage.XML_ENTITY__PRIMARY_KEY;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_0.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_0Package.XML_ENTITY_20__CACHE_INTERCEPTOR: return EclipseLinkOrmPackage.XML_ENTITY__CACHE_INTERCEPTOR;
				case EclipseLinkOrmV2_0Package.XML_ENTITY_20__QUERY_REDIRECTORS: return EclipseLinkOrmPackage.XML_ENTITY__QUERY_REDIRECTORS;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ENTITY__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlFetchGroupContainer_2_1.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_CONTAINER_21__FETCH_GROUPS: return EclipseLinkOrmPackage.XML_ENTITY__FETCH_GROUPS;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_1.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_1Package.XML_ENTITY_21__CLASS_EXTRACTOR: return EclipseLinkOrmPackage.XML_ENTITY__CLASS_EXTRACTOR;
				case EclipseLinkOrmV2_1Package.XML_ENTITY_21__PARENT_CLASS: return EclipseLinkOrmPackage.XML_ENTITY__PARENT_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlPartitioningGroup_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__REPLICATION_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__PINNED_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__RANGE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__VALUE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__HASH_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__HASH_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__UNION_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY__UNION_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONED: return EclipseLinkOrmPackage.XML_ENTITY__PARTITIONED;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_ENTITY_22__ADDITIONAL_CRITERIA: return EclipseLinkOrmPackage.XML_ENTITY__ADDITIONAL_CRITERIA;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_22__CASCADE_ON_DELETE: return EclipseLinkOrmPackage.XML_ENTITY__CASCADE_ON_DELETE;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_22__INDEX: return EclipseLinkOrmPackage.XML_ENTITY__INDEX;
				default: return -1;
			}
		}
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
		if (baseClass == XmlQueryContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES: return EclipseLinkOrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES: return EclipseLinkOrmPackage.XML_ENTITY__PROPERTIES;
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
		result.append(" (parentClass: ");
		result.append(parentClass);
		result.append(", partitioned: ");
		result.append(partitioned);
		result.append(", cascadeOnDelete: ");
		result.append(cascadeOnDelete);
		result.append(", readOnly: ");
		result.append(readOnly);
		result.append(", existenceChecking: ");
		result.append(existenceChecking);
		result.append(", excludeDefaultMappings: ");
		result.append(excludeDefaultMappings);
		result.append(')');
		return result.toString();
	}

	public TextRange getReadOnlyTextRange() {
		return getAttributeTextRange(EclipseLink.READ_ONLY);
	}
	
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlEntity(), 
			buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildClassTranslator(),
			buildParentClassTranslator(),
			buildAccessTranslator(),
			buildCacheableTranslator(),
			buildMetadataCompleteTranslator(),
			buildReadOnlyTranslator(),
			buildExistenceCheckingTranslator(),
			buildExcludeDefaultMappingsTranslator(),
			buildDescriptionTranslator(),
			buildAccessMethodsTranslator(),
			buildAdditionalCriteriaTranslator(),
			buildCustomizerTranslator(),
			buildChangeTrackingTranslator(),
			buildTableTranslator(),
			buildSecondaryTableTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildCascadeOnDeleteTranslator(),
			buildIndexTranslator(),
			buildIdClassTranslator(),
			buildPrimaryKeyTranslator(),
			buildInheritanceTranslator(),
			buildDiscriminatorValueTranslator(),
			buildDiscriminatorColumnTranslator(),
			buildClassExtractorTranslator(),
			buildOptimisticLockingTranslator(),
			buildCacheTranslator(),
			buildCacheInterceptorTranslator(),
			buildFetchGroupsTranslator(),
			buildConverterTranslator(),
			buildTypeConverterTranslator(),
			buildObjectTypeConverterTranslator(),
			buildStructConverterTranslator(),
			buildCopyPolicyTranslator(),
			buildInstantiationCoypPolicyTranslator(),
			buildCloneCopyPolicyTranslator(),
			buildSequenceGeneratorTranslator(),
			buildTableGeneratorTranslator(),
			buildNamedQueryTranslator(),
			buildNamedNativeQueryTranslator(),
			buildNamedStoredProcedureQueryTranslator(),
			buildSqlResultSetMappingTranslator(),
			buildQueryRedirectorsTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			buildPrePersistTranslator(),
			buildPostPersistTranslator(),
			buildPreRemoveTranslator(),
			buildPostRemoveTranslator(),
			buildPreUpdateTranslator(),
			buildPostUpdateTranslator(),
			buildPostLoadTranslator(),
			buildPropertyTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			Attributes.buildTranslator()};
	}
	
	protected static Translator buildCustomizerTranslator() {
		return XmlClassReference.buildTranslator(EclipseLink.CUSTOMIZER, EclipseLinkOrmPackage.eINSTANCE.getXmlCustomizerHolder_Customizer());
	}
	
	protected static Translator buildChangeTrackingTranslator() {
		return XmlChangeTracking.buildTranslator(EclipseLink.CHANGE_TRACKING, EclipseLinkOrmPackage.eINSTANCE.getXmlChangeTrackingHolder_ChangeTracking());
	}
	
	protected static Translator buildPrimaryKeyTranslator() {
		return XmlPrimaryKey.buildTranslator(EclipseLink1_1.PRIMARY_KEY, EclipseLinkOrmV1_1Package.eINSTANCE.getXmlEntity_1_1_PrimaryKey());
	}
	
	protected static Translator buildClassExtractorTranslator() {
		return XmlClassReference.buildTranslator(EclipseLink2_1.CLASS_EXTRACTOR, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlEntity_2_1_ClassExtractor());
	}
	
	protected static Translator buildOptimisticLockingTranslator() {
		return XmlOptimisticLocking.buildTranslator(EclipseLink.OPTIMISTIC_LOCKING, EclipseLinkOrmPackage.eINSTANCE.getXmlEntity_OptimisticLocking());
	}
	
	protected static Translator buildCacheTranslator() {
		return XmlCache.buildTranslator(EclipseLink.CACHE, EclipseLinkOrmPackage.eINSTANCE.getXmlCacheHolder_Cache());
	}
	
	protected static Translator buildCacheInterceptorTranslator() {
		return XmlClassReference.buildTranslator(EclipseLink2_0.CACHE_INTERCEPTOR, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlEntity_2_0_CacheInterceptor());
	}
	
	protected static Translator buildFetchGroupsTranslator() {
		return XmlFetchGroup.buildTranslator(EclipseLink2_1.FETCH_GROUP, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlFetchGroupContainer_2_1_FetchGroups());
	}
	
	protected static Translator buildQueryRedirectorsTranslator() {
		return XmlQueryRedirectors.buildTranslator(EclipseLink2_0.QUERY_REDIRECTORS, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlEntity_2_0_QueryRedirectors());
	}
	
	protected static Translator buildConverterTranslator() {
		return XmlConverter.buildTranslator(EclipseLink.CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_Converters());
	}
	
	protected static Translator buildTypeConverterTranslator() {
		return XmlTypeConverter.buildTranslator(EclipseLink.TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_TypeConverters());
	}
	
	protected static Translator buildObjectTypeConverterTranslator() {
		return XmlObjectTypeConverter.buildTranslator(EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_ObjectTypeConverters());
	}
	
	protected static Translator buildStructConverterTranslator() {
		return XmlStructConverter.buildTranslator(EclipseLink.STRUCT_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_StructConverters());
	}
	
	protected static Translator buildCopyPolicyTranslator() {
		return XmlCopyPolicy.buildTranslator(EclipseLink.COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEntity_CopyPolicy());
	}
	
	protected static Translator buildInstantiationCoypPolicyTranslator() {
		return XmlInstantiationCopyPolicy.buildTranslator(EclipseLink.INSTANTIATION_COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEntity_InstantiationCopyPolicy());
	}
	
	protected static Translator buildCloneCopyPolicyTranslator() {
		return XmlCloneCopyPolicy.buildTranslator(EclipseLink.CLONE_COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEntity_CloneCopyPolicy());
	}
	
	protected static Translator buildReadOnlyTranslator() {
		return new Translator(EclipseLink.READ_ONLY, EclipseLinkOrmPackage.eINSTANCE.getXmlReadOnly_ReadOnly(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildExistenceCheckingTranslator() {
		return new Translator(EclipseLink.EXISTENCE_CHECKING, EclipseLinkOrmPackage.eINSTANCE.getXmlCacheHolder_ExistenceChecking(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildExcludeDefaultMappingsTranslator() {
		return new Translator(EclipseLink.EXCLUDE_DEFAULT_MAPPINGS, EclipseLinkOrmPackage.eINSTANCE.getXmlEntity_ExcludeDefaultMappings(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildNamedStoredProcedureQueryTranslator() {
		return XmlNamedStoredProcedureQuery.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY, EclipseLinkOrmPackage.eINSTANCE.getXmlQueryContainer_NamedStoredProcedureQueries());
	}
	
	protected static Translator buildPropertyTranslator() {
		return XmlProperty.buildTranslator(EclipseLink.PROPERTY, EclipseLinkOrmPackage.eINSTANCE.getXmlPropertyContainer_Properties());
	}
	
	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

	protected static Translator buildParentClassTranslator() {
		return new Translator(EclipseLink2_1.PARENT_CLASS, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlEntity_2_1_ParentClass(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildAdditionalCriteriaTranslator() {
		return XmlAdditionalCriteria.buildTranslator(EclipseLink2_2.ADDITIONAL_CRITERIA, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntity_2_2_AdditionalCriteria());
	}

	protected static Translator buildCascadeOnDeleteTranslator() {
		return CascadeType.buildTranslator(EclipseLink2_2.CASCADE_ON_DELETE, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntity_2_2_CascadeOnDelete());
	}

	protected static Translator buildIndexTranslator() {
		return XmlIndex.buildTranslator(EclipseLink2_2.INDEX, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntity_2_2_Index());
	}
	
	protected static Translator buildTableTranslator() {
		return XmlTable.buildTranslator(JPA.TABLE, OrmPackage.eINSTANCE.getXmlEntity_Table());
	}

	protected static Translator buildSecondaryTableTranslator() {
		return XmlSecondaryTable.buildTranslator(JPA.SECONDARY_TABLE, OrmPackage.eINSTANCE.getXmlEntity_SecondaryTables());
	}	
	
	protected static Translator buildTableGeneratorTranslator() {
		return XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_TableGenerator());
	}

}

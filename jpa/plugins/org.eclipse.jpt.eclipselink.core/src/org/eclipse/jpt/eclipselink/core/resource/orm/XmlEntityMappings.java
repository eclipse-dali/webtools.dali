/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2;
import org.eclipse.jpt.core.internal.utility.translators.SimpleRootTranslator;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.SqlResultSetMapping;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Entity Mappings</b></em>'.
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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings()
 * @model kind="class"
 * @generated
 */
public class XmlEntityMappings extends org.eclipse.jpt.core.resource.orm.XmlEntityMappings implements XmlEntityMappings_2_1, XmlEntityMappings_2_2, XmlConvertersHolder, XmlQueryContainer
{
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
	 * The cached value of the '{@link #getPartitioning() <em>Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPartitioning_2_2> partitioning;

	/**
	 * The cached value of the '{@link #getReplicationPartitioning() <em>Replication Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplicationPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlReplicationPartitioning_2_2> replicationPartitioning;

	/**
	 * The cached value of the '{@link #getRoundRobinPartitioning() <em>Round Robin Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlRoundRobinPartitioning_2_2> roundRobinPartitioning;

	/**
	 * The cached value of the '{@link #getPinnedPartitioning() <em>Pinned Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinnedPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPinnedPartitioning_2_2> pinnedPartitioning;

	/**
	 * The cached value of the '{@link #getRangePartitioning() <em>Range Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRangePartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlRangePartitioning_2_2> rangePartitioning;

	/**
	 * The cached value of the '{@link #getValuePartitioning() <em>Value Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuePartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlValuePartitioning_2_2> valuePartitioning;

	/**
	 * The cached value of the '{@link #getHashPartitioning() <em>Hash Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlHashPartitioning_2_2> hashPartitioning;

	/**
	 * The cached value of the '{@link #getUnionPartitioning() <em>Union Partitioning</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnionPartitioning()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlUnionPartitioning_2_2> unionPartitioning;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntityMappings()
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
		return EclipseLinkOrmPackage.Literals.XML_ENTITY_MAPPINGS;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings#getAccessMethods <em>Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS, newAccessMethods, newAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_Partitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPartitioning_2_2> getPartitioning()
	{
		if (partitioning == null)
		{
			partitioning = new EObjectContainmentEList<XmlPartitioning_2_2>(XmlPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING);
		}
		return partitioning;
	}

	/**
	 * Returns the value of the '<em><b>Replication Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replication Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replication Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_ReplicationPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlReplicationPartitioning_2_2> getReplicationPartitioning()
	{
		if (replicationPartitioning == null)
		{
			replicationPartitioning = new EObjectContainmentEList<XmlReplicationPartitioning_2_2>(XmlReplicationPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING);
		}
		return replicationPartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Round Robin Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Round Robin Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Round Robin Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_RoundRobinPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlRoundRobinPartitioning_2_2> getRoundRobinPartitioning()
	{
		if (roundRobinPartitioning == null)
		{
			roundRobinPartitioning = new EObjectContainmentEList<XmlRoundRobinPartitioning_2_2>(XmlRoundRobinPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING);
		}
		return roundRobinPartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Pinned Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pinned Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pinned Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_PinnedPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPinnedPartitioning_2_2> getPinnedPartitioning()
	{
		if (pinnedPartitioning == null)
		{
			pinnedPartitioning = new EObjectContainmentEList<XmlPinnedPartitioning_2_2>(XmlPinnedPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING);
		}
		return pinnedPartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Range Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Range Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Range Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_RangePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlRangePartitioning_2_2> getRangePartitioning()
	{
		if (rangePartitioning == null)
		{
			rangePartitioning = new EObjectContainmentEList<XmlRangePartitioning_2_2>(XmlRangePartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING);
		}
		return rangePartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Value Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_ValuePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlValuePartitioning_2_2> getValuePartitioning()
	{
		if (valuePartitioning == null)
		{
			valuePartitioning = new EObjectContainmentEList<XmlValuePartitioning_2_2>(XmlValuePartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING);
		}
		return valuePartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Hash Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hash Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hash Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_HashPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlHashPartitioning_2_2> getHashPartitioning()
	{
		if (hashPartitioning == null)
		{
			hashPartitioning = new EObjectContainmentEList<XmlHashPartitioning_2_2>(XmlHashPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING);
		}
		return hashPartitioning;
	}

	/**
	 * Returns the value of the '<em><b>Union Partitioning</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Union Partitioning</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Union Partitioning</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings_2_2_UnionPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlUnionPartitioning_2_2> getUnionPartitioning()
	{
		if (unionPartitioning == null)
		{
			unionPartitioning = new EObjectContainmentEList<XmlUnionPartitioning_2_2>(XmlUnionPartitioning_2_2.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING);
		}
		return unionPartitioning;
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
			converters = new EObjectContainmentEList<XmlConverter>(XmlConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS);
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
			typeConverters = new EObjectContainmentEList<XmlTypeConverter>(XmlTypeConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS);
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
			objectTypeConverters = new EObjectContainmentEList<XmlObjectTypeConverter>(XmlObjectTypeConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS);
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
			structConverters = new EObjectContainmentEList<XmlStructConverter>(XmlStructConverter.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS);
		}
		return structConverters;
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
			namedStoredProcedureQueries = new EObjectContainmentEList<XmlNamedStoredProcedureQuery>(XmlNamedStoredProcedureQuery.class, this, EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES);
		}
		return namedStoredProcedureQueries;
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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING:
				return ((InternalEList<?>)getPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING:
				return ((InternalEList<?>)getReplicationPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING:
				return ((InternalEList<?>)getRoundRobinPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING:
				return ((InternalEList<?>)getPinnedPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING:
				return ((InternalEList<?>)getRangePartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING:
				return ((InternalEList<?>)getValuePartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING:
				return ((InternalEList<?>)getHashPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING:
				return ((InternalEList<?>)getUnionPartitioning()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS:
				return ((InternalEList<?>)getConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS:
				return ((InternalEList<?>)getTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS:
				return ((InternalEList<?>)getObjectTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS:
				return ((InternalEList<?>)getStructConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES:
				return ((InternalEList<?>)getNamedStoredProcedureQueries()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING:
				return getPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING:
				return getReplicationPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING:
				return getRoundRobinPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING:
				return getPinnedPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING:
				return getRangePartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING:
				return getValuePartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING:
				return getHashPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING:
				return getUnionPartitioning();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS:
				return getConverters();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS:
				return getTypeConverters();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS:
				return getObjectTypeConverters();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS:
				return getStructConverters();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES:
				return getNamedStoredProcedureQueries();
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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING:
				getPartitioning().clear();
				getPartitioning().addAll((Collection<? extends XmlPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING:
				getReplicationPartitioning().clear();
				getReplicationPartitioning().addAll((Collection<? extends XmlReplicationPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING:
				getRoundRobinPartitioning().clear();
				getRoundRobinPartitioning().addAll((Collection<? extends XmlRoundRobinPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING:
				getPinnedPartitioning().clear();
				getPinnedPartitioning().addAll((Collection<? extends XmlPinnedPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING:
				getRangePartitioning().clear();
				getRangePartitioning().addAll((Collection<? extends XmlRangePartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING:
				getValuePartitioning().clear();
				getValuePartitioning().addAll((Collection<? extends XmlValuePartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING:
				getHashPartitioning().clear();
				getHashPartitioning().addAll((Collection<? extends XmlHashPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING:
				getUnionPartitioning().clear();
				getUnionPartitioning().addAll((Collection<? extends XmlUnionPartitioning_2_2>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS:
				getConverters().clear();
				getConverters().addAll((Collection<? extends XmlConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS:
				getTypeConverters().clear();
				getTypeConverters().addAll((Collection<? extends XmlTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				getObjectTypeConverters().addAll((Collection<? extends XmlObjectTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS:
				getStructConverters().clear();
				getStructConverters().addAll((Collection<? extends XmlStructConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				getNamedStoredProcedureQueries().addAll((Collection<? extends XmlNamedStoredProcedureQuery>)newValue);
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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING:
				getPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING:
				getReplicationPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING:
				getRoundRobinPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING:
				getPinnedPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING:
				getRangePartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING:
				getValuePartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING:
				getHashPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING:
				getUnionPartitioning().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS:
				getConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS:
				getTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS:
				getStructConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING:
				return partitioning != null && !partitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING:
				return replicationPartitioning != null && !replicationPartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING:
				return roundRobinPartitioning != null && !roundRobinPartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING:
				return pinnedPartitioning != null && !pinnedPartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING:
				return rangePartitioning != null && !rangePartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING:
				return valuePartitioning != null && !valuePartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING:
				return hashPartitioning != null && !hashPartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING:
				return unionPartitioning != null && !unionPartitioning.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS:
				return converters != null && !converters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS:
				return typeConverters != null && !typeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS:
				return objectTypeConverters != null && !objectTypeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS:
				return structConverters != null && !structConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES:
				return namedStoredProcedureQueries != null && !namedStoredProcedureQueries.isEmpty();
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
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlEntityMappings_2_1.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEntityMappings_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__REPLICATION_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__PINNED_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__RANGE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__VALUE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__HASH_PARTITIONING;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__UNION_PARTITIONING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES: return EclipseLinkOrmPackage.XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES;
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
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlEntityMappings_2_1.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEntityMappings_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__REPLICATION_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__PINNED_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PINNED_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__RANGE_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__RANGE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__VALUE_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VALUE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__HASH_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__HASH_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_ENTITY_MAPPINGS_22__UNION_PARTITIONING: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__UNION_PARTITIONING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES: return EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}
	
	
	// **************** version -> schema location mapping ********************
	
	private static String namespace = EclipseLink.SCHEMA_NAMESPACE;
	
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	private static Map<String, String> versionsToSchemaLocations = buildVersionsToSchemaLocations();
	
	private static Map<String, String> buildVersionsToSchemaLocations() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(EclipseLink.SCHEMA_VERSION, EclipseLink.SCHEMA_LOCATION);
		map.put(EclipseLink1_1.SCHEMA_VERSION, EclipseLink1_1.SCHEMA_LOCATION);
		map.put(EclipseLink1_2.SCHEMA_VERSION, EclipseLink1_2.SCHEMA_LOCATION);
		map.put(EclipseLink2_0.SCHEMA_VERSION, EclipseLink2_0.SCHEMA_LOCATION);
		map.put(EclipseLink2_1.SCHEMA_VERSION, EclipseLink2_1.SCHEMA_LOCATION);
		map.put(EclipseLink2_2.SCHEMA_VERSION, EclipseLink2_2.SCHEMA_LOCATION);
		return map;
	}
	
	@Override
	protected String getSchemaLocationForVersion(String version) {
		return versionsToSchemaLocations.get(version);
	}
	
	
	// **************** translators *******************************************
	
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();
	
	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	
	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA.ENTITY_MAPPINGS,
				EclipseLinkOrmPackage.eINSTANCE.getXmlEntityMappings(),
				buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildVersionTranslator(versionsToSchemaLocations),
			buildNamespaceTranslator(namespace),
			buildSchemaNamespaceTranslator(),
			buildSchemaLocationTranslator(namespace, versionsToSchemaLocations),
			buildDescriptionTranslator(),
			XmlPersistenceUnitMetadata.buildTranslator(JPA.PERSISTENCE_UNIT_METADATA, OrmPackage.eINSTANCE.getXmlEntityMappings_PersistenceUnitMetadata()),
			buildPackageTranslator(),
			buildSchemaTranslator(),
			buildCatalogTranslator(),
			buildAccessTranslator(),
			buildAccessMethodsTranslator(),
			XmlConverter.buildTranslator(EclipseLink.CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_Converters()),
			XmlTypeConverter.buildTranslator(EclipseLink.TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_TypeConverters()),
			XmlObjectTypeConverter.buildTranslator(EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_ObjectTypeConverters()),
			XmlStructConverter.buildTranslator(EclipseLink.STRUCT_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_StructConverters()),
			XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_SequenceGenerators()),
			XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_TableGenerators()),
			XmlPartitioning.buildTranslator(EclipseLink2_2.PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_Partitioning()),
			XmlReplicationPartitioning.buildTranslator(EclipseLink2_2.REPLICATION_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_ReplicationPartitioning()),
			XmlRoundRobinPartitioning.buildTranslator(EclipseLink2_2.ROUND_ROBIN_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_RoundRobinPartitioning()),
			XmlPinnedPartitioning.buildTranslator(EclipseLink2_2.PINNED_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_PinnedPartitioning()),
			XmlRangePartitioning.buildTranslator(EclipseLink2_2.RANGE_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_RangePartitioning()),
			XmlValuePartitioning.buildTranslator(EclipseLink2_2.VALUE_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_ValuePartitioning()),
			XmlHashPartitioning.buildTranslator(EclipseLink2_2.HASH_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_HashPartitioning()),
			XmlUnionPartitioning.buildTranslator(EclipseLink2_2.UNION_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEntityMappings_2_2_UnionPartitioning()),
			XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries()),
			XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries()),
			XmlNamedStoredProcedureQuery.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY, EclipseLinkOrmPackage.eINSTANCE.getXmlQueryContainer_NamedStoredProcedureQueries()),
			SqlResultSetMapping.buildTranslator(JPA.SQL_RESULT_SET_MAPPING, OrmPackage.eINSTANCE.getXmlEntityMappings_SqlResultSetMappings()),
			XmlMappedSuperclass.buildTranslator(JPA.MAPPED_SUPERCLASS, OrmPackage.eINSTANCE.getXmlEntityMappings_MappedSuperclasses()),
			XmlEntity.buildTranslator(JPA.ENTITY, OrmPackage.eINSTANCE.getXmlEntityMappings_Entities()),
			XmlEmbeddable.buildTranslator(JPA.EMBEDDABLE, OrmPackage.eINSTANCE.getXmlEntityMappings_Embeddables()),
		};
	}
	
	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

}

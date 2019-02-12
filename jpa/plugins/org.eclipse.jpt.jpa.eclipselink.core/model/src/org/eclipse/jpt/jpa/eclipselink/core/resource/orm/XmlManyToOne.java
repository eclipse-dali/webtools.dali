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
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumnContainer;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.common.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlForeignKey;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Many To One</b></em>'.
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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOne extends org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne implements XmlManyToOne_2_1, XmlManyToOne_2_2, XmlManyToOne_2_4, XmlAttributeMapping, XmlJoinFetch, XmlManyToOne_2_5
{
	/**
	 * The cached value of the '{@link #getBatchFetch() <em>Batch Fetch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBatchFetch()
	 * @generated
	 * @ordered
	 */
	protected XmlBatchFetch batchFetch;

	/**
	 * The cached value of the '{@link #getPartitioning() <em>Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlPartitioning partitioning;

	/**
	 * The cached value of the '{@link #getReplicationPartitioning() <em>Replication Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplicationPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlReplicationPartitioning replicationPartitioning;

	/**
	 * The cached value of the '{@link #getRoundRobinPartitioning() <em>Round Robin Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlRoundRobinPartitioning roundRobinPartitioning;

	/**
	 * The cached value of the '{@link #getPinnedPartitioning() <em>Pinned Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinnedPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlPinnedPartitioning pinnedPartitioning;

	/**
	 * The cached value of the '{@link #getRangePartitioning() <em>Range Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRangePartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlRangePartitioning rangePartitioning;

	/**
	 * The cached value of the '{@link #getValuePartitioning() <em>Value Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuePartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlValuePartitioning valuePartitioning;

	/**
	 * The cached value of the '{@link #getHashPartitioning() <em>Hash Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlHashPartitioning hashPartitioning;

	/**
	 * The cached value of the '{@link #getUnionPartitioning() <em>Union Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnionPartitioning()
	 * @generated
	 * @ordered
	 */
	protected XmlUnionPartitioning unionPartitioning;

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
	 * The default value of the '{@link #isNonCacheable() <em>Non Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNonCacheable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NON_CACHEABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNonCacheable() <em>Non Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNonCacheable()
	 * @generated
	 * @ordered
	 */
	protected boolean nonCacheable = NON_CACHEABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJoinFields() <em>Join Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinFields()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinField> joinFields;

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
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlJoinFetchType JOIN_FETCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJoinFetch() <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinFetch()
	 * @generated
	 * @ordered
	 */
	protected XmlJoinFetchType joinFetch = JOIN_FETCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPrimaryKeyJoinColumns() <em>Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getPrimaryKeyForeignKey() <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey primaryKeyForeignKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToOne()
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
		return EclipseLinkOrmPackage.Literals.XML_MANY_TO_ONE;
	}

	/**
	 * Returns the value of the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Batch Fetch</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Batch Fetch</em>' containment reference.
	 * @see #setBatchFetch(XmlBatchFetch)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetchHolder_BatchFetch()
	 * @model containment="true"
	 * @generated
	 */
	public XmlBatchFetch getBatchFetch()
	{
		return batchFetch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBatchFetch(XmlBatchFetch newBatchFetch, NotificationChain msgs)
	{
		XmlBatchFetch oldBatchFetch = batchFetch;
		batchFetch = newBatchFetch;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH, oldBatchFetch, newBatchFetch);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getBatchFetch <em>Batch Fetch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Batch Fetch</em>' containment reference.
	 * @see #getBatchFetch()
	 * @generated
	 */
	public void setBatchFetch(XmlBatchFetch newBatchFetch)
	{
		if (newBatchFetch != batchFetch)
		{
			NotificationChain msgs = null;
			if (batchFetch != null)
				msgs = ((InternalEObject)batchFetch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH, null, msgs);
			if (newBatchFetch != null)
				msgs = ((InternalEObject)newBatchFetch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH, null, msgs);
			msgs = basicSetBatchFetch(newBatchFetch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH, newBatchFetch, newBatchFetch));
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
	 * @see #setPartitioning(XmlPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_Partitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPartitioning getPartitioning()
	{
		return partitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPartitioning(XmlPartitioning newPartitioning, NotificationChain msgs)
	{
		XmlPartitioning oldPartitioning = partitioning;
		partitioning = newPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING, oldPartitioning, newPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getPartitioning <em>Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioning</em>' containment reference.
	 * @see #getPartitioning()
	 * @generated
	 */
	public void setPartitioning(XmlPartitioning newPartitioning)
	{
		if (newPartitioning != partitioning)
		{
			NotificationChain msgs = null;
			if (partitioning != null)
				msgs = ((InternalEObject)partitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING, null, msgs);
			if (newPartitioning != null)
				msgs = ((InternalEObject)newPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING, null, msgs);
			msgs = basicSetPartitioning(newPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING, newPartitioning, newPartitioning));
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
	 * @see #setReplicationPartitioning(XmlReplicationPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_ReplicationPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlReplicationPartitioning getReplicationPartitioning()
	{
		return replicationPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReplicationPartitioning(XmlReplicationPartitioning newReplicationPartitioning, NotificationChain msgs)
	{
		XmlReplicationPartitioning oldReplicationPartitioning = replicationPartitioning;
		replicationPartitioning = newReplicationPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING, oldReplicationPartitioning, newReplicationPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getReplicationPartitioning <em>Replication Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replication Partitioning</em>' containment reference.
	 * @see #getReplicationPartitioning()
	 * @generated
	 */
	public void setReplicationPartitioning(XmlReplicationPartitioning newReplicationPartitioning)
	{
		if (newReplicationPartitioning != replicationPartitioning)
		{
			NotificationChain msgs = null;
			if (replicationPartitioning != null)
				msgs = ((InternalEObject)replicationPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING, null, msgs);
			if (newReplicationPartitioning != null)
				msgs = ((InternalEObject)newReplicationPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING, null, msgs);
			msgs = basicSetReplicationPartitioning(newReplicationPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING, newReplicationPartitioning, newReplicationPartitioning));
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
	 * @see #setRoundRobinPartitioning(XmlRoundRobinPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_RoundRobinPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlRoundRobinPartitioning getRoundRobinPartitioning()
	{
		return roundRobinPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoundRobinPartitioning(XmlRoundRobinPartitioning newRoundRobinPartitioning, NotificationChain msgs)
	{
		XmlRoundRobinPartitioning oldRoundRobinPartitioning = roundRobinPartitioning;
		roundRobinPartitioning = newRoundRobinPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING, oldRoundRobinPartitioning, newRoundRobinPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Round Robin Partitioning</em>' containment reference.
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 */
	public void setRoundRobinPartitioning(XmlRoundRobinPartitioning newRoundRobinPartitioning)
	{
		if (newRoundRobinPartitioning != roundRobinPartitioning)
		{
			NotificationChain msgs = null;
			if (roundRobinPartitioning != null)
				msgs = ((InternalEObject)roundRobinPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING, null, msgs);
			if (newRoundRobinPartitioning != null)
				msgs = ((InternalEObject)newRoundRobinPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING, null, msgs);
			msgs = basicSetRoundRobinPartitioning(newRoundRobinPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING, newRoundRobinPartitioning, newRoundRobinPartitioning));
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
	 * @see #setPinnedPartitioning(XmlPinnedPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_PinnedPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPinnedPartitioning getPinnedPartitioning()
	{
		return pinnedPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPinnedPartitioning(XmlPinnedPartitioning newPinnedPartitioning, NotificationChain msgs)
	{
		XmlPinnedPartitioning oldPinnedPartitioning = pinnedPartitioning;
		pinnedPartitioning = newPinnedPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING, oldPinnedPartitioning, newPinnedPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getPinnedPartitioning <em>Pinned Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pinned Partitioning</em>' containment reference.
	 * @see #getPinnedPartitioning()
	 * @generated
	 */
	public void setPinnedPartitioning(XmlPinnedPartitioning newPinnedPartitioning)
	{
		if (newPinnedPartitioning != pinnedPartitioning)
		{
			NotificationChain msgs = null;
			if (pinnedPartitioning != null)
				msgs = ((InternalEObject)pinnedPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING, null, msgs);
			if (newPinnedPartitioning != null)
				msgs = ((InternalEObject)newPinnedPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING, null, msgs);
			msgs = basicSetPinnedPartitioning(newPinnedPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING, newPinnedPartitioning, newPinnedPartitioning));
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
	 * @see #setRangePartitioning(XmlRangePartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_RangePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlRangePartitioning getRangePartitioning()
	{
		return rangePartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRangePartitioning(XmlRangePartitioning newRangePartitioning, NotificationChain msgs)
	{
		XmlRangePartitioning oldRangePartitioning = rangePartitioning;
		rangePartitioning = newRangePartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING, oldRangePartitioning, newRangePartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getRangePartitioning <em>Range Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range Partitioning</em>' containment reference.
	 * @see #getRangePartitioning()
	 * @generated
	 */
	public void setRangePartitioning(XmlRangePartitioning newRangePartitioning)
	{
		if (newRangePartitioning != rangePartitioning)
		{
			NotificationChain msgs = null;
			if (rangePartitioning != null)
				msgs = ((InternalEObject)rangePartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING, null, msgs);
			if (newRangePartitioning != null)
				msgs = ((InternalEObject)newRangePartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING, null, msgs);
			msgs = basicSetRangePartitioning(newRangePartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING, newRangePartitioning, newRangePartitioning));
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
	 * @see #setValuePartitioning(XmlValuePartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_ValuePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlValuePartitioning getValuePartitioning()
	{
		return valuePartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValuePartitioning(XmlValuePartitioning newValuePartitioning, NotificationChain msgs)
	{
		XmlValuePartitioning oldValuePartitioning = valuePartitioning;
		valuePartitioning = newValuePartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING, oldValuePartitioning, newValuePartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getValuePartitioning <em>Value Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Partitioning</em>' containment reference.
	 * @see #getValuePartitioning()
	 * @generated
	 */
	public void setValuePartitioning(XmlValuePartitioning newValuePartitioning)
	{
		if (newValuePartitioning != valuePartitioning)
		{
			NotificationChain msgs = null;
			if (valuePartitioning != null)
				msgs = ((InternalEObject)valuePartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING, null, msgs);
			if (newValuePartitioning != null)
				msgs = ((InternalEObject)newValuePartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING, null, msgs);
			msgs = basicSetValuePartitioning(newValuePartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING, newValuePartitioning, newValuePartitioning));
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
	 * @see #setHashPartitioning(XmlHashPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_HashPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlHashPartitioning getHashPartitioning()
	{
		return hashPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHashPartitioning(XmlHashPartitioning newHashPartitioning, NotificationChain msgs)
	{
		XmlHashPartitioning oldHashPartitioning = hashPartitioning;
		hashPartitioning = newHashPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING, oldHashPartitioning, newHashPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getHashPartitioning <em>Hash Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash Partitioning</em>' containment reference.
	 * @see #getHashPartitioning()
	 * @generated
	 */
	public void setHashPartitioning(XmlHashPartitioning newHashPartitioning)
	{
		if (newHashPartitioning != hashPartitioning)
		{
			NotificationChain msgs = null;
			if (hashPartitioning != null)
				msgs = ((InternalEObject)hashPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING, null, msgs);
			if (newHashPartitioning != null)
				msgs = ((InternalEObject)newHashPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING, null, msgs);
			msgs = basicSetHashPartitioning(newHashPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING, newHashPartitioning, newHashPartitioning));
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
	 * @see #setUnionPartitioning(XmlUnionPartitioning)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_UnionPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	public XmlUnionPartitioning getUnionPartitioning()
	{
		return unionPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnionPartitioning(XmlUnionPartitioning newUnionPartitioning, NotificationChain msgs)
	{
		XmlUnionPartitioning oldUnionPartitioning = unionPartitioning;
		unionPartitioning = newUnionPartitioning;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING, oldUnionPartitioning, newUnionPartitioning);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getUnionPartitioning <em>Union Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Union Partitioning</em>' containment reference.
	 * @see #getUnionPartitioning()
	 * @generated
	 */
	public void setUnionPartitioning(XmlUnionPartitioning newUnionPartitioning)
	{
		if (newUnionPartitioning != unionPartitioning)
		{
			NotificationChain msgs = null;
			if (unionPartitioning != null)
				msgs = ((InternalEObject)unionPartitioning).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING, null, msgs);
			if (newUnionPartitioning != null)
				msgs = ((InternalEObject)newUnionPartitioning).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING, null, msgs);
			msgs = basicSetUnionPartitioning(newUnionPartitioning, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING, newUnionPartitioning, newUnionPartitioning));
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioningGroup_2_2_Partitioned()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPartitioned()
	{
		return partitioned;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getPartitioned <em>Partitioned</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED, oldPartitioned, partitioned));
	}

	/**
	 * Returns the value of the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Cacheable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Cacheable</em>' attribute.
	 * @see #setNonCacheable(boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne_2_2_NonCacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isNonCacheable()
	{
		return nonCacheable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#isNonCacheable <em>Non Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Cacheable</em>' attribute.
	 * @see #isNonCacheable()
	 * @generated
	 */
	public void setNonCacheable(boolean newNonCacheable)
	{
		boolean oldNonCacheable = nonCacheable;
		nonCacheable = newNonCacheable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE, oldNonCacheable, nonCacheable));
	}

	/**
	 * Returns the value of the '<em><b>Join Fields</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinField}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fields</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne_2_4_JoinFields()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinField> getJoinFields()
	{
		if (joinFields == null)
		{
			joinFields = new EObjectContainmentEList<XmlJoinField>(XmlJoinField.class, this, EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS);
		}
		return joinFields;
	}

	/**
	 * Returns the value of the '<em><b>Join Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #setJoinFetch(XmlJoinFetchType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch_JoinFetch()
	 * @model
	 * @generated
	 */
	public XmlJoinFetchType getJoinFetch()
	{
		return joinFetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getJoinFetch <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #getJoinFetch()
	 * @generated
	 */
	public void setJoinFetch(XmlJoinFetchType newJoinFetch)
	{
		XmlJoinFetchType oldJoinFetch = joinFetch;
		joinFetch = newJoinFetch == null ? JOIN_FETCH_EDEFAULT : newJoinFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH, oldJoinFetch, joinFetch));
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKeyJoinColumnContainer_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);
		}
		return primaryKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #setPrimaryKeyForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne_2_5_PrimaryKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getPrimaryKeyForeignKey()
	{
		return primaryKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldPrimaryKeyForeignKey = primaryKeyForeignKey;
		primaryKeyForeignKey = newPrimaryKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, oldPrimaryKeyForeignKey, newPrimaryKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 */
	public void setPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey)
	{
		if (newPrimaryKeyForeignKey != primaryKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (primaryKeyForeignKey != null)
				msgs = ((InternalEObject)primaryKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			if (newPrimaryKeyForeignKey != null)
				msgs = ((InternalEObject)newPrimaryKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetPrimaryKeyForeignKey(newPrimaryKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, newPrimaryKeyForeignKey, newPrimaryKeyForeignKey));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getAccessMethods <em>Access Methods</em>}' containment reference.
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
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS, newAccessMethods, newAccessMethods));
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
			properties = new EObjectContainmentEList<XmlProperty>(XmlProperty.class, this, EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES);
		}
		return properties;
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
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne#getAttributeType <em>Attribute Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE, oldAttributeType, attributeType));
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH:
				return basicSetBatchFetch(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING:
				return basicSetPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING:
				return basicSetReplicationPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING:
				return basicSetRoundRobinPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING:
				return basicSetPinnedPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING:
				return basicSetRangePartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING:
				return basicSetValuePartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING:
				return basicSetHashPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING:
				return basicSetUnionPartitioning(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS:
				return ((InternalEList<?>)getJoinFields()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return basicSetPrimaryKeyForeignKey(null, msgs);
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH:
				return getBatchFetch();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING:
				return getPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING:
				return getReplicationPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING:
				return getRoundRobinPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING:
				return getPinnedPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING:
				return getRangePartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING:
				return getValuePartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING:
				return getHashPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING:
				return getUnionPartitioning();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED:
				return getPartitioned();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE:
				return isNonCacheable();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS:
				return getJoinFields();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES:
				return getProperties();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE:
				return getAttributeType();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH:
				return getJoinFetch();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return getPrimaryKeyForeignKey();
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH:
				setBatchFetch((XmlBatchFetch)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING:
				setPartitioning((XmlPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING:
				setReplicationPartitioning((XmlReplicationPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING:
				setRoundRobinPartitioning((XmlRoundRobinPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING:
				setPinnedPartitioning((XmlPinnedPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING:
				setRangePartitioning((XmlRangePartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING:
				setValuePartitioning((XmlValuePartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING:
				setHashPartitioning((XmlHashPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING:
				setUnionPartitioning((XmlUnionPartitioning)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED:
				setPartitioned((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE:
				setNonCacheable((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS:
				getJoinFields().clear();
				getJoinFields().addAll((Collection<? extends XmlJoinField>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends XmlProperty>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE:
				setAttributeType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH:
				setJoinFetch((XmlJoinFetchType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends XmlPrimaryKeyJoinColumn>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)newValue);
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH:
				setBatchFetch((XmlBatchFetch)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING:
				setPartitioning((XmlPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING:
				setReplicationPartitioning((XmlReplicationPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING:
				setRoundRobinPartitioning((XmlRoundRobinPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING:
				setPinnedPartitioning((XmlPinnedPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING:
				setRangePartitioning((XmlRangePartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING:
				setValuePartitioning((XmlValuePartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING:
				setHashPartitioning((XmlHashPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING:
				setUnionPartitioning((XmlUnionPartitioning)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED:
				setPartitioned(PARTITIONED_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE:
				setNonCacheable(NON_CACHEABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS:
				getJoinFields().clear();
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES:
				getProperties().clear();
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE:
				setAttributeType(ATTRIBUTE_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH:
				setJoinFetch(JOIN_FETCH_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)null);
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH:
				return batchFetch != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING:
				return partitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING:
				return replicationPartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING:
				return roundRobinPartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING:
				return pinnedPartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING:
				return rangePartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING:
				return valuePartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING:
				return hashPartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING:
				return unionPartitioning != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED:
				return PARTITIONED_EDEFAULT == null ? partitioned != null : !PARTITIONED_EDEFAULT.equals(partitioned);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE:
				return nonCacheable != NON_CACHEABLE_EDEFAULT;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS:
				return joinFields != null && !joinFields.isEmpty();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE:
				return ATTRIBUTE_TYPE_EDEFAULT == null ? attributeType != null : !ATTRIBUTE_TYPE_EDEFAULT.equals(attributeType);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH:
				return joinFetch != JOIN_FETCH_EDEFAULT;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return primaryKeyForeignKey != null;
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
		if (baseClass == XmlBatchFetchHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH: return EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_1.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlPartitioningGroup_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED: return EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONED;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE: return EclipseLinkOrmV2_2Package.XML_MANY_TO_ONE_22__NON_CACHEABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_4.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS: return EclipseLinkOrmV2_4Package.XML_MANY_TO_ONE_24__JOIN_FIELDS;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES: return EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE: return EclipseLinkOrmPackage.XML_ATTRIBUTE_MAPPING__ATTRIBUTE_TYPE;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinFetch.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH: return EclipseLinkOrmPackage.XML_JOIN_FETCH__JOIN_FETCH;
				default: return -1;
			}
		}
		if (baseClass == XmlPrimaryKeyJoinColumnContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS: return OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER__PRIMARY_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_5.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY: return EclipseLinkOrmV2_5Package.XML_MANY_TO_ONE_25__PRIMARY_KEY_FOREIGN_KEY;
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
		if (baseClass == XmlBatchFetchHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__BATCH_FETCH;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_1.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlPartitioningGroup_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__REPLICATION_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PINNED_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__RANGE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__VALUE_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__HASH_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__HASH_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__UNION_PARTITIONING: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__UNION_PARTITIONING;
				case EclipseLinkOrmV2_2Package.XML_PARTITIONING_GROUP_22__PARTITIONED: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PARTITIONED;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_MANY_TO_ONE_22__NON_CACHEABLE: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__NON_CACHEABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_4.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_4Package.XML_MANY_TO_ONE_24__JOIN_FIELDS: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FIELDS;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ATTRIBUTE_MAPPING__ATTRIBUTE_TYPE: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__ATTRIBUTE_TYPE;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinFetch.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_JOIN_FETCH__JOIN_FETCH: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH;
				default: return -1;
			}
		}
		if (baseClass == XmlPrimaryKeyJoinColumnContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER__PRIMARY_KEY_JOIN_COLUMNS: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_5.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_5Package.XML_MANY_TO_ONE_25__PRIMARY_KEY_FOREIGN_KEY: return EclipseLinkOrmPackage.XML_MANY_TO_ONE__PRIMARY_KEY_FOREIGN_KEY;
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
		result.append(" (partitioned: ");
		result.append(partitioned);
		result.append(", nonCacheable: ");
		result.append(nonCacheable);
		result.append(", attributeType: ");
		result.append(attributeType);
		result.append(", joinFetch: ");
		result.append(joinFetch);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getJoinFetchTextRange() {
		return getElementTextRange(EclipseLink.JOIN_FETCH);
	}

	public TextRange getAttributeTypeTextRange() {
		//no attribute-type for many-to-one
		return getValidationTextRange();
	}
	
	
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlManyToOne(), 
			buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildOptionalTranslator(),
			buildAccessTranslator(),
			buildMapsIdTranslator(),
			buildIdTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildPrimaryKeyForeignKeyTranslator(),
			buildJoinColumnTranslator(),
			buildForeignKeyTranslator(),
			buildJoinTableTranslator(),
			buildJoinFieldTranslator(),
			buildCascadeTranslator(),
			buildJoinFetchTranslator(),
			buildBatchFetchTranslator(),
			buildPropertyTranslator(),
			buildAccessMethodsTranslator(),
			buildNoncacheableTranslator(),
		    XmlPartitioning.buildTranslator(EclipseLink2_2.PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_Partitioning()),
			XmlReplicationPartitioning.buildTranslator(EclipseLink2_2.REPLICATION_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_ReplicationPartitioning()),
			XmlRoundRobinPartitioning.buildTranslator(EclipseLink2_2.ROUND_ROBIN_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_RoundRobinPartitioning()),
			XmlPinnedPartitioning.buildTranslator(EclipseLink2_2.PINNED_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_PinnedPartitioning()),
			XmlRangePartitioning.buildTranslator(EclipseLink2_2.RANGE_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_RangePartitioning()),
			XmlValuePartitioning.buildTranslator(EclipseLink2_2.VALUE_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_ValuePartitioning()),
			XmlHashPartitioning.buildTranslator(EclipseLink2_2.HASH_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_HashPartitioning()),
			XmlUnionPartitioning.buildTranslator(EclipseLink2_2.UNION_PARTITIONING, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_UnionPartitioning()),
			buildPartitionedTranslator(),
		};
	}
	
	protected static Translator buildPrivateOwnedTranslator() {
		return new EmptyTagBooleanTranslator(EclipseLink.PRIVATE_OWNED, EclipseLinkOrmPackage.eINSTANCE.getXmlPrivateOwned_PrivateOwned());
	}
	
	protected static Translator buildJoinFetchTranslator() {
		return new Translator(EclipseLink.JOIN_FETCH, EclipseLinkOrmPackage.eINSTANCE.getXmlJoinFetch_JoinFetch());
	}
	
	protected static Translator buildPropertyTranslator() {
		return XmlProperty.buildTranslator(EclipseLink.PROPERTY, EclipseLinkOrmPackage.eINSTANCE.getXmlPropertyContainer_Properties());
	}
	
	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

	protected static Translator buildBatchFetchTranslator() {
		return XmlBatchFetch.buildTranslator(EclipseLink2_1.BATCH_FETCH, EclipseLinkOrmPackage.eINSTANCE.getXmlBatchFetchHolder_BatchFetch());
	}

	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableContainer_JoinTable());
	}

	protected static Translator buildNoncacheableTranslator() {
		return new EmptyTagBooleanTranslator(EclipseLink2_2.NONCACHEABLE, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlManyToOne_2_2_NonCacheable());
	}

    protected static Translator buildPartitionedTranslator() {
		return new Translator(EclipseLink2_2.PARTITIONING_GROUP__PARTITIONED, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlPartitioningGroup_2_2_Partitioned());
	}

	protected static Translator buildJoinFieldTranslator() {
		return XmlJoinField.buildTranslator(EclipseLink2_4.JOIN_FIELD, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlManyToOne_2_4_JoinFields());
	}
	
	protected static Translator buildPrimaryKeyJoinColumnTranslator() {
		return XmlPrimaryKeyJoinColumn.buildTranslator(JPA.PRIMARY_KEY_JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlPrimaryKeyJoinColumnContainer_PrimaryKeyJoinColumns());
	}
	
	protected static Translator buildPrimaryKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.PRIMARY_KEY_FOREIGN_KEY, EclipseLinkOrmV2_5Package.eINSTANCE.getXmlManyToOne_2_5_PrimaryKeyForeignKey());
	}
	
	// ******** virtual attribute ************
	
	public void setVirtualAttributeTypes(String attributeType, String targetEntity) {
		this.setTargetEntity(targetEntity);
	}
}

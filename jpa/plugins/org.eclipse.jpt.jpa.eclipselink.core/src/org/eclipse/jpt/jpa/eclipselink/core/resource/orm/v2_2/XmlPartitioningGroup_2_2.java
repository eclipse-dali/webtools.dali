/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Partitioning Group2 2</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioning <em>Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getReplicationPartitioning <em>Replication Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPinnedPartitioning <em>Pinned Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRangePartitioning <em>Range Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getValuePartitioning <em>Value Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getHashPartitioning <em>Hash Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getUnionPartitioning <em>Union Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioned <em>Partitioned</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlPartitioningGroup_2_2 extends JpaEObject
{
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_Partitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlPartitioning_2_2 getPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioning <em>Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioning</em>' containment reference.
	 * @see #getPartitioning()
	 * @generated
	 */
	void setPartitioning(XmlPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_ReplicationPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlReplicationPartitioning_2_2 getReplicationPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getReplicationPartitioning <em>Replication Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replication Partitioning</em>' containment reference.
	 * @see #getReplicationPartitioning()
	 * @generated
	 */
	void setReplicationPartitioning(XmlReplicationPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_RoundRobinPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlRoundRobinPartitioning_2_2 getRoundRobinPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Round Robin Partitioning</em>' containment reference.
	 * @see #getRoundRobinPartitioning()
	 * @generated
	 */
	void setRoundRobinPartitioning(XmlRoundRobinPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_PinnedPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlPinnedPartitioning_2_2 getPinnedPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPinnedPartitioning <em>Pinned Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pinned Partitioning</em>' containment reference.
	 * @see #getPinnedPartitioning()
	 * @generated
	 */
	void setPinnedPartitioning(XmlPinnedPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_RangePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlRangePartitioning_2_2 getRangePartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRangePartitioning <em>Range Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range Partitioning</em>' containment reference.
	 * @see #getRangePartitioning()
	 * @generated
	 */
	void setRangePartitioning(XmlRangePartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_ValuePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlValuePartitioning_2_2 getValuePartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getValuePartitioning <em>Value Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Partitioning</em>' containment reference.
	 * @see #getValuePartitioning()
	 * @generated
	 */
	void setValuePartitioning(XmlValuePartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_HashPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlHashPartitioning_2_2 getHashPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getHashPartitioning <em>Hash Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash Partitioning</em>' containment reference.
	 * @see #getHashPartitioning()
	 * @generated
	 */
	void setHashPartitioning(XmlHashPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_UnionPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	XmlUnionPartitioning_2_2 getUnionPartitioning();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getUnionPartitioning <em>Union Partitioning</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Union Partitioning</em>' containment reference.
	 * @see #getUnionPartitioning()
	 * @generated
	 */
	void setUnionPartitioning(XmlUnionPartitioning_2_2 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2_Partitioned()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getPartitioned();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioned <em>Partitioned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioned</em>' attribute.
	 * @see #getPartitioned()
	 * @generated
	 */
	void setPartitioned(String value);

} // XmlPartitioningGroup2_2

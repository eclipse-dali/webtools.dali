/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity Mappings 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPartitioning <em>Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getReplicationPartitioning <em>Replication Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPinnedPartitioning <em>Pinned Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRangePartitioning <em>Range Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getValuePartitioning <em>Value Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getHashPartitioning <em>Hash Partitioning</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getUnionPartitioning <em>Union Partitioning</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlEntityMappings_2_2 extends JpaEObject
{
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_Partitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPartitioning_2_2> getPartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_ReplicationPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlReplicationPartitioning_2_2> getReplicationPartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_RoundRobinPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlRoundRobinPartitioning_2_2> getRoundRobinPartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_PinnedPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPinnedPartitioning_2_2> getPinnedPartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_RangePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlRangePartitioning_2_2> getRangePartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_ValuePartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlValuePartitioning_2_2> getValuePartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_HashPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlHashPartitioning_2_2> getHashPartitioning();

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2_UnionPartitioning()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlUnionPartitioning_2_2> getUnionPartitioning();

} // XmlEntityMappings_2_2

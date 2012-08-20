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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Converter Container</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer#getConverters <em>Converters</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer#getTypeConverters <em>Type Converters</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer#getObjectTypeConverters <em>Object Type Converters</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer#getStructConverters <em>Struct Converters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlConverterContainer extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterContainer_Converters()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlConverter> getConverters();

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
	EList<XmlTypeConverter> getTypeConverters();

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
	EList<XmlObjectTypeConverter> getObjectTypeConverters();

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
	EList<XmlStructConverter> getStructConverters();

} // XmlConverterContainer

/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.jpt.jpa.core.resource.orm;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Id Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#getEntityListeners <em>Entity Listeners</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlIdTypeMapping extends XmlTypeMapping, XmlIdClassContainer
{
	/**
	 * Returns the value of the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Default Listeners</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Default Listeners</em>' attribute.
	 * @see #setExcludeDefaultListeners(boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_ExcludeDefaultListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isExcludeDefaultListeners();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Default Listeners</em>' attribute.
	 * @see #isExcludeDefaultListeners()
	 * @generated
	 */
	void setExcludeDefaultListeners(boolean value);

	/**
	 * Returns the value of the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Superclass Listeners</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Superclass Listeners</em>' attribute.
	 * @see #setExcludeSuperclassListeners(boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_ExcludeSuperclassListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isExcludeSuperclassListeners();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Superclass Listeners</em>' attribute.
	 * @see #isExcludeSuperclassListeners()
	 * @generated
	 */
	void setExcludeSuperclassListeners(boolean value);

	/**
	 * Returns the value of the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Listeners</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Listeners</em>' containment reference.
	 * @see #setEntityListeners(EntityListeners)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_EntityListeners()
	 * @model containment="true"
	 * @generated
	 */
	EntityListeners getEntityListeners();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping#getEntityListeners <em>Entity Listeners</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Listeners</em>' containment reference.
	 * @see #getEntityListeners()
	 * @generated
	 */
	void setEntityListeners(EntityListeners value);
}

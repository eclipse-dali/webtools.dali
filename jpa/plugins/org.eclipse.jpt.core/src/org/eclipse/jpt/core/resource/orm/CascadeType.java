/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cascade Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface CascadeType extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade All</em>' attribute.
	 * @see #setCascadeAll(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadeAll();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade All</em>' attribute.
	 * @see #isCascadeAll()
	 * @generated
	 */
	void setCascadeAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' attribute.
	 * @see #setCascadePersist(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadePersist()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadePersist();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' attribute.
	 * @see #isCascadePersist()
	 * @generated
	 */
	void setCascadePersist(boolean value);

	/**
	 * Returns the value of the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Merge</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Merge</em>' attribute.
	 * @see #setCascadeMerge(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeMerge()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadeMerge();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Merge</em>' attribute.
	 * @see #isCascadeMerge()
	 * @generated
	 */
	void setCascadeMerge(boolean value);

	/**
	 * Returns the value of the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Remove</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Remove</em>' attribute.
	 * @see #setCascadeRemove(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeRemove()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadeRemove();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Remove</em>' attribute.
	 * @see #isCascadeRemove()
	 * @generated
	 */
	void setCascadeRemove(boolean value);

	/**
	 * Returns the value of the '<em><b>Cascade Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #setCascadeRefresh(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeRefresh()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadeRefresh();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #isCascadeRefresh()
	 * @generated
	 */
	void setCascadeRefresh(boolean value);

} // CascadeType

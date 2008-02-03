/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJpa Root Content Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.IJpaRootContentNode#getJpaFile <em>Jpa File</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaRootContentNode()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaRootContentNode extends IJpaContentNode
{
	/**
	 * Returns the value of the '<em><b>Jpa File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jpa File</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jpa File</em>' reference.
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaRootContentNode_JpaFile()
	 * @model changeable="false"
	 * @generated
	 */
	IJpaFile getJpaFile();

	/**
	 * Return the content node corresponding to the given offset in the source.
	 * This may (and often will) be <code>null</code>.
	 */
	IJpaContentNode getContentNode(int offset);

	/**
	 * Handle java change as befits this file content
	 */
	void javaElementChanged(ElementChangedEvent event);

	/**
	 * Dispose before removed from model
	 */
	void dispose();
}

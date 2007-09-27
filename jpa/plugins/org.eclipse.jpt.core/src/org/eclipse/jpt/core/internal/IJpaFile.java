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

import org.eclipse.core.resources.IFile;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPersistence File</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaFile()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaFile extends IJpaEObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getContentId();

	/**
	 * Return the IFile associated with this JPA file
	 */
	IFile getFile();

	/**
	 * Return the resource model represented by this JPA file
	 */
	IResourceModel getResourceModel();

	/**
	 * Return the content node corresponding to the given offset in the source.
	 * This may (and often will) be <code>null</code>.
	 */
	IJpaContentNode getContentNode(int offset);
}

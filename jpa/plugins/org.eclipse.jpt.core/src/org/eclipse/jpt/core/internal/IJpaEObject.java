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

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPersistence Element</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaEObject()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaEObject extends EObject
{
	IJpaProject getJpaProject();

	/**
	 * Return the resource that most directly contains this object
	 */
	IResource getResource();

	IJpaPlatform jpaPlatform();
}

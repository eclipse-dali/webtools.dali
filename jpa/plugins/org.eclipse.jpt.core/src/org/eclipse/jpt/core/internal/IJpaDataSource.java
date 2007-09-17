/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJpa Data Source</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaDataSource()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaDataSource extends IJpaEObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getConnectionProfileName();

	void setConnectionProfileName(String newConnectionProfileName);

	boolean isConnected();

	boolean hasAConnection();
}

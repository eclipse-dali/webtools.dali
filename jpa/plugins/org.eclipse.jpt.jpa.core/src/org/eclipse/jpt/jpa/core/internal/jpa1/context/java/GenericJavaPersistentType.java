/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;

/**
 * JPA 1.0 Java persistent type.
 * The specified access is always null.
 */
public class GenericJavaPersistentType
	extends AbstractJavaPersistentType
{
	public GenericJavaPersistentType(JavaPersistentType.Parent parent, JavaResourceType jrt) {
		super(parent, jrt);
	}


	// ********** access **********

	/**
	 * Return <code>null</code> - JPA 1.0 does not support a specified access.
	 */
	@Override
	protected AccessType buildSpecifiedAccess() {
		return null;
	}

	/**
	 * JPA 1.0 does not support a specified access.
	 */
	@Override
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException("JPA 1.0 does not support a specified access type in java."); //$NON-NLS-1$
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

/**
 * JPA 1.0 Java persistent type.
 * The specified access is always null.
 */
public class GenericJavaPersistentType
	extends AbstractJavaPersistentType
{
	public GenericJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
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

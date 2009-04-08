/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public class GenericJavaPersistentType
	extends AbstractJavaPersistentType
{

	public GenericJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
	}
	
	
	//****************** PersistentType implementation *******************
	/**
	 * GenericJavaPersistentType does not support specified access (no Access annotation in 1.0), so we return null
	 */
	public AccessType getSpecifiedAccess() {
		return null;
	}
	
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}
}

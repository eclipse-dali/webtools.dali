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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * Generic Java persistent attribute
 */
public class GenericJavaPersistentAttribute
	extends AbstractJavaPersistentAttribute
{

	public GenericJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		super(parent, jrpa);
	}

	
	// ********** AccessHolder implementation **********
	
	/**
	 * GenericJavaPersistentAttribute does not support specified access (no access element in 1.0), so we return null
	 */
	public AccessType getSpecifiedAccess() {
		return null;
	}
	
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}

}

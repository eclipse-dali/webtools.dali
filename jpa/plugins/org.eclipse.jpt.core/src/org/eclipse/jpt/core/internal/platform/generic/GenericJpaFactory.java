/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.generic;

import org.eclipse.jpt.core.internal.platform.BaseJpaFactory;

public class GenericJpaFactory extends BaseJpaFactory 
{
	// **************** singleton *********************************************
	
	private static GenericJpaFactory INSTANCE;  // lazily-final
	
	/**
	 * Return the singleton Generic JPA factory
	 */
	public static final synchronized GenericJpaFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJpaFactory();
		}
		return INSTANCE;
	}
	
	
	
	// nothing for now... just take everything from the abstract superclass
}

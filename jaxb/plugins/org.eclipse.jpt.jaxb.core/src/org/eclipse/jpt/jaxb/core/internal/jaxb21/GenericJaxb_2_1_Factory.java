/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbFactory;

/**
 * Generic JAXB version 2.1
 */
public class GenericJaxb_2_1_Factory
		extends AbstractJaxbFactory {
	
	// singleton
	private static final JaxbFactory INSTANCE = new GenericJaxb_2_1_Factory();

	/**
	 * Return the singleton.
	 */
	public static JaxbFactory instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJaxb_2_1_Factory() {
		super();
	}
}

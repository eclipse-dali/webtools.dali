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

import org.eclipse.jpt.jaxb.core.AnnotationDefinitionProvider;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbPlatformProvider;
import org.eclipse.jpt.jaxb.core.internal.GenericJaxbAnnotationDefinitionProvider;
import org.eclipse.jpt.jaxb.core.internal.GenericJaxbPlatformProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;

public class GenericJaxbPlatformDefinition
	implements JaxbPlatformDefinition
{
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new GenericJaxbPlatformDefinition();

	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJaxbPlatformDefinition() {
		super();
	}

	public JaxbFactory buildFactory() {
		return GenericJaxbFactory.instance();
	}

	public JaxbPlatformProvider buildPlatformProvider() {
		return GenericJaxbPlatformProvider.instance();
	}

	public AnnotationDefinitionProvider[] getAnnotationDefinitionProviders() {
		return new AnnotationDefinitionProvider[] {GenericJaxbAnnotationDefinitionProvider.instance()};
	}

}

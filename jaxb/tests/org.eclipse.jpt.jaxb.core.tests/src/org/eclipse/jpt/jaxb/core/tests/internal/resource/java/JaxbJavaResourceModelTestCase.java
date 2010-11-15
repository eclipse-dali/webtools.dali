/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import org.eclipse.jpt.jaxb.core.internal.jaxb21.Generic_2_1_JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotationDefinition;

public class JaxbJavaResourceModelTestCase
		extends JavaResourceModelTestCase {	

	public JaxbJavaResourceModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return Generic_2_1_JaxbPlatformDefinition.instance().getAnnotationDefinitions();
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return Generic_2_1_JaxbPlatformDefinition.instance().getNestableAnnotationDefinitions();
	}
}

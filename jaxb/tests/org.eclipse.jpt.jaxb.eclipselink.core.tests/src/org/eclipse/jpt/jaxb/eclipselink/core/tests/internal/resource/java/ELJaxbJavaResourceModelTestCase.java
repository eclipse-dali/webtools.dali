/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.JaxbProjectTestHarness;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.ELJaxb_2_1_PlatformDefinition;


public class ELJaxbJavaResourceModelTestCase 
		extends JavaResourceModelTestCase {	
	
	public ELJaxbJavaResourceModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaProjectTestHarness.addJar(JaxbProjectTestHarness.eclipseLinkJarName());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// nothing as of yet
	}
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return ELJaxb_2_1_PlatformDefinition.instance().getAnnotationDefinitions();
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return ELJaxb_2_1_PlatformDefinition.instance().getNestableAnnotationDefinitions();
	}
}

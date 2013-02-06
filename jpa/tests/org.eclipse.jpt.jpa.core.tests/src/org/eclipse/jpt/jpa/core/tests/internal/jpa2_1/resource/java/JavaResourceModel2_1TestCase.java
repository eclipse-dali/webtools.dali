/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.resource.java;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.Generic2_1JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.tests.internal.resource.java.JpaJavaResourceModelTestCase;

public class JavaResourceModel2_1TestCase
	extends JpaJavaResourceModelTestCase
{
	
	
	public JavaResourceModel2_1TestCase(String name) {
		super(name);
	}

	@Override
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
			Generic2_1JpaAnnotationDefinitionProvider.instance());
	}

}

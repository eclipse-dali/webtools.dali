/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;

public class JavaTestAttributeMapping
	extends AbstractJavaAttributeMapping<Annotation>
{
	public static final String TEST_ATTRIBUTE_MAPPING_KEY = "testAttribute"; //$NON-NLS-1$
	public static final String TEST_ATTRIBUTE_ANNOTATION_NAME = "test.TestAttribute"; //$NON-NLS-1$


	public JavaTestAttributeMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_ANNOTATION_NAME;
	}
}

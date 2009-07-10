/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public class JavaTestAttributeMapping extends AbstractJavaAttributeMapping<Annotation>
{
	public static final String TEST_ATTRIBUTE_MAPPING_KEY = "testAttribute"; //$NON-NLS-1$
	public static final String TEST_ATTRIBUTE_ANNOTATION_NAME = "test.TestAttribute"; //$NON-NLS-1$


	public JavaTestAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_ANNOTATION_NAME;
	}

	public Iterator<String> supportingAnnotationNames() {
		return EmptyListIterator.instance();
	}
}

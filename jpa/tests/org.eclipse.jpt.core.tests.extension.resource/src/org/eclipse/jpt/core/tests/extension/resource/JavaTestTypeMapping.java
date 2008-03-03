/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTypeMapping;

public class JavaTestTypeMapping extends AbstractJavaTypeMapping
{
	public static final String TEST_TYPE_MAPPING_KEY = "test";
	public static final String TEST_TYPE_ANNOTATION_NAME = "test.Test";


	public JavaTestTypeMapping(JavaPersistentType parent) {
		super(parent);
	}

	public String annotationName() {
		return TEST_TYPE_ANNOTATION_NAME;
	}

	public String getKey() {
		return TEST_TYPE_MAPPING_KEY;
	}

	public Iterator<String> correspondingAnnotationNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isMapped() {
		return true;
	}

}

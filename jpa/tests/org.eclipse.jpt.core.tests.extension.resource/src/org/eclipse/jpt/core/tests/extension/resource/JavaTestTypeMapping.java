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
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTypeMapping;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaTestTypeMapping extends AbstractJavaTypeMapping
{
	public static final String TEST_TYPE_MAPPING_KEY = "test"; //$NON-NLS-1$
	public static final String TEST_TYPE_ANNOTATION_NAME = "test.Test"; //$NON-NLS-1$


	public JavaTestTypeMapping(JavaPersistentType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return TEST_TYPE_ANNOTATION_NAME;
	}

	public String getKey() {
		return TEST_TYPE_MAPPING_KEY;
	}

	public Iterator<String> supportingAnnotationNames() {
		return EmptyIterator.instance();
	}

	public boolean isMapped() {
		return true;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	public boolean shouldValidateDbInfo() {
		return false;
	}
}

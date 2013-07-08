/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaTypeMapping;

public class JavaTestTypeMapping
		extends AbstractJavaTypeMapping<Annotation> {
	
	public static final String TEST_TYPE_MAPPING_KEY = "test"; //$NON-NLS-1$
	public static final String TEST_TYPE_ANNOTATION_NAME = "test.Test"; //$NON-NLS-1$
	
	
	public JavaTestTypeMapping(JavaPersistentType parent) {
		super(parent, null);
	}
	
	
	public String getKey() {
		return TEST_TYPE_MAPPING_KEY;
	}
	
	public boolean isMapped() {
		return true;
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	public Iterable<Query> getQueries() {
		return EmptyIterable.instance();
	}
	
	public IdTypeMapping getSuperTypeMapping() {
		return null;
	}
	
	public Iterable<IdTypeMapping> getAncestors() {
		return EmptyIterable.instance();
	}
	
	public Iterable<? extends TypeMapping> getInheritanceHierarchy() {
		return new SingleElementIterable(this);
	}
}

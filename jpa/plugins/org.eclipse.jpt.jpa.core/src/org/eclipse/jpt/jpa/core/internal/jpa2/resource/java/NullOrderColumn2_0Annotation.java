/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullNamedColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumn2_0Annotation;

/**
 * <code>javax.persistence.OrderColumn</code>
 */
public final class NullOrderColumn2_0Annotation
	extends NullNamedColumnAnnotation<OrderColumn2_0Annotation>
	implements OrderColumn2_0Annotation
{	
	public NullOrderColumn2_0Annotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ***** updatable
	public Boolean getUpdatable() {
		return null;
	}

	public void setUpdatable(Boolean updatable) {
		if (updatable != null) {
			this.addAnnotation().setUpdatable(updatable);
		}
	}

	public TextRange getUpdatableTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** insertable
	public Boolean getInsertable() {
		return null;
	}

	public void setInsertable(Boolean insertable) {
		if (insertable != null) {
			this.addAnnotation().setInsertable(insertable);
		}
	}

	public TextRange getInsertableTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** nullable
	public Boolean getNullable() {
		return null;
	}

	public void setNullable(Boolean nullable) {
		if (nullable != null) {
			this.addAnnotation().setNullable(nullable);
		}
	}

	public TextRange getNullableTextRange(CompilationUnit astRoot) {
		return null;
	}
}

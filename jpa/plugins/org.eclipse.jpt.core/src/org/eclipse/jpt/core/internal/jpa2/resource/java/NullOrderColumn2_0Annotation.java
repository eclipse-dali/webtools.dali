/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullNamedColumnAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OrderColumn
 */
public final class NullOrderColumn2_0Annotation
	extends NullNamedColumnAnnotation
	implements OrderColumn2_0Annotation
{	
	public NullOrderColumn2_0Annotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected OrderColumn2_0Annotation addAnnotation() {
		return (OrderColumn2_0Annotation) super.addAnnotation();
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

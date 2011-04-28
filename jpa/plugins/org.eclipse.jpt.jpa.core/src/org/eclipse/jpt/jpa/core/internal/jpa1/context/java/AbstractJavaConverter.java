/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

public abstract class AbstractJavaConverter
	extends AbstractJavaJpaContextNode
	implements JavaConverter
{
	protected AbstractJavaConverter(JavaAttributeMapping parent) {
		super(parent);
	}


	// ********** misc **********

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	protected JavaAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getAttributeMapping().getResourcePersistentAttribute();
	}

	public Annotation getConverterAnnotation() {
		return this.getResourcePersistentAttribute().getAnnotation(this.getAnnotationName());
	}

	protected abstract String getAnnotationName();

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange(astRoot);
	}

	protected abstract TextRange getAnnotationTextRange(CompilationUnit astRoot);

	public void dispose() {
		// NOP
	}
}

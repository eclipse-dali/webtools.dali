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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaConverter
	extends AbstractJavaJpaContextNode
	implements JavaConverter
{

	protected final JavaConverter.Owner owner;

	protected AbstractJavaConverter(JavaAttributeMapping parent, JavaConverter.Owner owner) {
		super(parent);
		this.owner = owner;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.owner.buildValidator(this, this.buildConverterTextRangeResolver(astRoot)).validate(messages, reporter);
	}


	protected ConverterTextRangeResolver buildConverterTextRangeResolver(final CompilationUnit astRoot) {
		return new ConverterTextRangeResolver() {
			public TextRange getConverterTextRange() {
				return getValidationTextRange(astRoot);
			}
		};
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange(astRoot);
	}

	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		return this.getConverterAnnotation().getTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	protected JavaAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getAttributeMapping().getResourceAttribute();
	}

	public void dispose() {
		// NOP
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TransformerAnnotation;

/**
 * <code><ul>
 * <li>org.eclipse.persistence.annotations.ReadTransformer
 * <li>org.eclipse.persistence.annotations.WriteTransformer
 * </ul></code>
 */
abstract class EclipseLinkBinaryTransformerAnnotation
	extends BinaryAnnotation
	implements TransformerAnnotation
{
	private String transformerClass;
	private String method;


	EclipseLinkBinaryTransformerAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.transformerClass = this.buildTransformerClass();
		this.method = this.buildMethod();
	}

	@Override
	public void update() {
		super.update();
		this.setTransformerClass_(this.buildTransformerClass());
		this.setMethod_(this.buildMethod());
	}


	// ********** TransformerAnnotation implementation **********

	// ***** transformer class
	public String getTransformerClass() {
		return this.transformerClass;
	}

	public void setTransformerClass(String transformerClass) {
		throw new UnsupportedOperationException();
	}

	private void setTransformerClass_(String transformerClass) {
		String old = this.transformerClass;
		this.transformerClass = transformerClass;
		this.firePropertyChanged(TRANSFORMER_CLASS_PROPERTY, old, transformerClass);
	}

	private String buildTransformerClass() {
		return (String) this.getJdtMemberValue(this.getTransformerClassElementName());
	}

	public TextRange getTransformerClassTextRange() {
		throw new UnsupportedOperationException();
	}

	abstract String getTransformerClassElementName();

	// ***** method
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		throw new UnsupportedOperationException();
	}

	private void setMethod_(String method) {
		String old = this.method;
		this.method = method;
		this.firePropertyChanged(METHOD_PROPERTY, old, method);
	}

	private String buildMethod() {
		return (String) this.getJdtMemberValue(this.getMethodElementName());
	}

	public TextRange getMethodTextRange() {
		throw new UnsupportedOperationException();
	}

	abstract String getMethodElementName();
}

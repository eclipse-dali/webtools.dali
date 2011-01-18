/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaIdDerivedIdentityStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaIdDerivedIdentityStrategy2_0
{
	protected boolean value;


	public GenericJavaIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setValue_(this.buildValue());
	}


	// ********** value **********

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		if (value != this.value) {
			if (value) {
				this.addAnnotation();
			} else {
				this.removeAnnotation();
			}
			this.setValue_(value);
		}
	}

	protected void setValue_(boolean value) {
		boolean old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	protected boolean buildValue() {
		return this.getAnnotation() != null;
	}


	// ********** annotation **********

	protected IdAnnotation getAnnotation() {
		return (IdAnnotation) this.getResourceAttribute().getAnnotation(this.getAnnotationName());
	}

	protected void addAnnotation() {
		this.getResourceAttribute().addAnnotation(this.getAnnotationName());
	}

	protected void removeAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getAnnotationName());
	}

	protected String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	@Override
	public JavaDerivedIdentity2_0 getParent() {
		return (JavaDerivedIdentity2_0) super.getParent();
	}

	protected JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.getParent();
	}

	protected JavaSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return this.getMapping().getPersistentAttribute().getResourcePersistentAttribute();
	}

	public boolean isSpecified() {
		return this.value;
	}

	public void addStrategy() {
		this.setValue(true);
	}

	public void removeStrategy() {
		this.setValue(false);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// no validation rules
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		IdAnnotation annotation = this.getAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}

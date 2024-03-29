/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.IdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaIdDerivedIdentityStrategy2_0
	extends AbstractJavaContextModel<JavaDerivedIdentity2_0>
	implements IdDerivedIdentityStrategy2_0
{
	protected boolean value;


	public GenericJavaIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
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

	protected JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.parent;
	}

	protected JavaSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getMapping().getPersistentAttribute().getResourceAttribute();
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
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		IdAnnotation annotation = this.getAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}

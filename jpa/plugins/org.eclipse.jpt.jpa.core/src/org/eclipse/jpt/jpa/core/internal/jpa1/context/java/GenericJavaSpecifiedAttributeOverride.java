/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java attribute override
 */
public class GenericJavaSpecifiedAttributeOverride
	extends AbstractJavaSpecifiedOverride<JavaAttributeOverrideContainer, AttributeOverrideAnnotation>
	implements JavaSpecifiedAttributeOverride, JavaSpecifiedColumn.Owner
{
	protected final JavaSpecifiedColumn column;


	public GenericJavaSpecifiedAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation) {
		super(parent, annotation);
		this.column = this.buildColumn();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
	}


	// ********** specified/virtual **********

	@Override
	public JavaVirtualAttributeOverride convertToVirtual() {
		return (JavaVirtualAttributeOverride) super.convertToVirtual();
	}


	// ********** column **********

	public JavaSpecifiedColumn getColumn() {
		return this.column;
	}

	protected JavaSpecifiedColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this, this);
	}


	// ********** misc **********

	public void initializeFrom(AttributeOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.column.initializeFrom(oldOverride.getColumn());
	}

	public void initializeFromVirtual(AttributeOverride oldOverride) {
		super.initializeFromVirtual(oldOverride);
		this.column.initializeFromVirtual(oldOverride.getColumn());
	}


	// ********** column owner implementation **********

	public String getDefaultColumnName(NamedColumn col) {
		return this.name;
	}

	public JptValidator buildColumnValidator(NamedColumn col) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this);
	}

	public CompleteColumnAnnotation getColumnAnnotation() {
		return this.getOverrideAnnotation().getNonNullColumn();
	}

	public void removeColumnAnnotation() {
		this.getOverrideAnnotation().removeColumn();
	}


	// ********** derived **********

	protected  boolean attributeIsDerivedId() {
		return this.getTypeMapping().attributeIsDerivedId(this.name);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.column.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected Iterable<String> getCandidateNames() {
		return this.getContainer().getAllOverridableNames();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (this.attributeIsDerivedId()) {
			messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED
					)
				);

			// validate the column if it is specified
			if (this.columnAnnotationIsSpecified()) {
				this.column.validate(messages, reporter);
			}
		} else {
			this.column.validate(messages, reporter);
		}
	}

	protected boolean columnAnnotationIsSpecified() {
		return this.getOverrideAnnotation().getColumn() != null;
	}
}

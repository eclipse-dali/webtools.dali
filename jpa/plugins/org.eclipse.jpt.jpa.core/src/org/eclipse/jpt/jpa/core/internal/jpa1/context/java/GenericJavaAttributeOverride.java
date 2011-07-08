/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java attribute override
 */
public class GenericJavaAttributeOverride
	extends AbstractJavaOverride<JavaAttributeOverrideContainer, AttributeOverrideAnnotation>
	implements JavaAttributeOverride, JavaColumn.Owner
{
	protected final JavaColumn column;


	public GenericJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation) {
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

	public JavaColumn getColumn() {
		return this.column;
	}

	protected JavaColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this, this);
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.column.initializeFrom(oldOverride.getColumn());
	}

	public void initializeFromVirtual(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFromVirtual(oldOverride);
		this.column.initializeFromVirtual(oldOverride.getColumn());
	}


	// ********** column owner implementation **********

	public String getDefaultColumnName() {
		return this.name;
	}

	public JptValidator buildColumnValidator(ReadOnlyNamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this, (BaseColumnTextRangeResolver) textRangeResolver);
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
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.column.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected Iterator<String> candidateNames() {
		return this.getContainer().allOverridableNames();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		if (this.attributeIsDerivedId()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange(astRoot)
					)
				);

			// validate the column if it is specified
			if (this.columnAnnotationIsSpecified()) {
				this.column.validate(messages, reporter, astRoot);
			}
		} else {
			this.column.validate(messages, reporter, astRoot);
		}
	}

	protected boolean columnAnnotationIsSpecified() {
		return this.getOverrideAnnotation().getColumn() != null;
	}
}

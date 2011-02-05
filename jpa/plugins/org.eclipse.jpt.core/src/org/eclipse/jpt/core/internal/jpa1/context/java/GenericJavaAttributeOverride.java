/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.db.Table;
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

	public TypeMapping getTypeMapping() {
		return this.getContainer().getTypeMapping();
	}

	public String getDefaultTableName() {
		return this.getContainer().getDefaultTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getContainer().resolveDbTable(tableName);
	}

	public String getDefaultColumnName() {
		return this.name;
	}

	public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, (BaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.getContainer().candidateTableNames();
	}

	public CompleteColumnAnnotation getColumnAnnotation() {
		return this.getOverrideAnnotation().getNonNullColumn();
	}

	public void removeColumnAnnotation() {
		this.getOverrideAnnotation().removeColumn();
	}


	// ********** mapped by relationship **********

	protected  boolean isMappedByRelationship() {
		return CollectionTools.contains(this.getMappedByRelationshipAttributeNames(), this.buildQualifier());
	}

	protected Iterable<String> getMappedByRelationshipAttributeNames() {
		return TypeMappingTools.getMappedByRelationshipAttributeNames(this.getTypeMapping());
	}

	/**
	 * overridable names are (usually?) qualified with a container mapping,
	 * which may also be the one mapped by a relationship
	 */
	protected String buildQualifier() {
		if (this.name == null) {
			return null;
		}
		int index = this.name.indexOf('.');
		return (index == -1) ? this.name : this.name.substring(0, index);
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

		// [JPA 2.0] if the column is specified, or if the override is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the override is never mapped by a
		//  relationship)
		if (this.columnAnnotationIsSpecified() || ! this.isMappedByRelationship()) {
			this.column.validate(messages, reporter, astRoot);
		}

		// [JPA 2.0] if the override is mapped by a relationship, then that actually is in itself
		// a validation error
		// (We prevent implied overrides that are mapped by a relationship ... hopefully)
		// (In JPA 1.0, this will never occur)
		if (this.isMappedByRelationship()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ATTRIBUTE_OVERRIDE_MAPPED_BY_RELATIONSHIP_AND_SPECIFIED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange(astRoot)
					)
				);
		}
	}

	protected boolean columnAnnotationIsSpecified() {
		return this.getOverrideAnnotation().getColumn() != null;
	}
}

/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.TenantDiscriminatorColumnValidator;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkMultitenancyImpl
	extends AbstractJavaJpaContextNode
	implements JavaEclipseLinkMultitenancy
{
	protected EclipseLinkMultitenantType specifiedType;
	protected EclipseLinkMultitenantType defaultType;

	protected Boolean specifiedIncludeCriteria;

	protected final JavaReadOnlyTenantDiscriminatorColumn.Owner tenantDiscriminatorColumnOwner;
	protected final ContextListContainer<JavaTenantDiscriminatorColumn, EclipseLinkTenantDiscriminatorColumnAnnotation> specifiedTenantDiscriminatorColumnContainer;

	public JavaEclipseLinkMultitenancyImpl(JavaEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);

		EclipseLinkMultitenantAnnotation multitenantAnnotation = this.getMultitenantAnnotation();
		this.specifiedType = EclipseLinkMultitenantType.fromJavaResourceModel(multitenantAnnotation.getValue());
		this.specifiedIncludeCriteria = multitenantAnnotation.getIncludeCriteria();
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = this.buildSpecifiedTenantDiscriminatorColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		EclipseLinkMultitenantAnnotation multitenantAnnotation = this.getMultitenantAnnotation();
		this.setSpecifiedType_(EclipseLinkMultitenantType.fromJavaResourceModel(multitenantAnnotation.getValue()));
		this.setSpecifiedIncludeCriteria_(multitenantAnnotation.getIncludeCriteria());
		this.syncSpecifiedTenantDiscriminatorColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedTenantDiscriminatorColumns());
		this.setDefaultType(isMultitenant() ? DEFAULT_TYPE : null);
	}


	// ********** type **********

	public EclipseLinkMultitenantType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public EclipseLinkMultitenantType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType type) {
		this.getMultitenantAnnotation().setValue(EclipseLinkMultitenantType.toJavaResourceModel(type));
		this.setSpecifiedType_(type);
	}

	protected void setSpecifiedType_(EclipseLinkMultitenantType type) {
		EclipseLinkMultitenantType old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	public EclipseLinkMultitenantType getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(EclipseLinkMultitenantType type) {
		EclipseLinkMultitenantType old = this.defaultType;
		this.defaultType = type;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, type);
	}


	// ********** include criteria **********

	public boolean isIncludeCriteria() {
		return (this.specifiedIncludeCriteria != null) ? this.specifiedIncludeCriteria.booleanValue() : this.isDefaultIncludeCriteria();
	}

	public Boolean getSpecifiedIncludeCriteria() {
		return this.specifiedIncludeCriteria;
	}

	public void setSpecifiedIncludeCriteria(Boolean includeCriteria) {
		this.getMultitenantAnnotation().setIncludeCriteria(includeCriteria);
		this.setSpecifiedIncludeCriteria_(includeCriteria);
	}

	protected void setSpecifiedIncludeCriteria_(Boolean includeCriteria) {
		Boolean old = this.specifiedIncludeCriteria;
		this.specifiedIncludeCriteria = includeCriteria;
		this.firePropertyChanged(SPECIFIED_INCLUDE_CRITERIA_PROPERTY, old, includeCriteria);
	}

	public boolean isDefaultIncludeCriteria() {
		return DEFAULT_INCLUDE_CRITERIA;
	}


	// ********** tenant discriminator columns **********

	public ListIterable<JavaTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index) {
		EclipseLinkTenantDiscriminatorColumnAnnotation annotation = this.addTenantDiscriminatorColumnAnnotation(index);
		return this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOfContextElement((JavaTenantDiscriminatorColumn) tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.removeTenantDiscriminatorColumnAnnotation(index);
		this.specifiedTenantDiscriminatorColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.moveTenantDiscriminatorColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedTenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaTenantDiscriminatorColumn buildTenantDiscriminatorColumn(EclipseLinkTenantDiscriminatorColumnAnnotation discriminatorColumnAnnotation) {
		return new EclipseLinkJavaTenantDiscriminatorColumn(this, this.tenantDiscriminatorColumnOwner, discriminatorColumnAnnotation);
	}

	protected void syncSpecifiedTenantDiscriminatorColumns() {
		this.specifiedTenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ContextListContainer<JavaTenantDiscriminatorColumn, EclipseLinkTenantDiscriminatorColumnAnnotation> buildSpecifiedTenantDiscriminatorColumnContainer() {
		return new SpecifiedTenantDiscriminatorColumnContainer();
	}

	protected JavaReadOnlyTenantDiscriminatorColumn.Owner buildTenantDiscriminatorColumnOwner() {
		return new TenantDiscriminatorColumnOwner();
	}

	/**
	 * specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<JavaTenantDiscriminatorColumn, EclipseLinkTenantDiscriminatorColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected JavaTenantDiscriminatorColumn buildContextElement(EclipseLinkTenantDiscriminatorColumnAnnotation resourceElement) {
			return JavaEclipseLinkMultitenancyImpl.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl.this.getTenantDiscriminatorColumnAnnotations();
		}
		@Override
		protected EclipseLinkTenantDiscriminatorColumnAnnotation getResourceElement(JavaTenantDiscriminatorColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}


	// ********** JavaReadOnlyTenantDiscriminatorColumn.Owner implementation **********

	protected class TenantDiscriminatorColumnOwner 
		implements JavaReadOnlyTenantDiscriminatorColumn.Owner
	{

		public String getDefaultContextPropertyName() {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_PRIMARY_KEY;
		}

		public int getDefaultLength() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_NAME;
		}

		public Table resolveDbTable(String tableName) {
			return getTypeMapping().resolveDbTable(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return getTypeMapping().getAllAssociatedTableNames();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new TenantDiscriminatorColumnValidator(column, textRangeResolver);
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return JavaEclipseLinkMultitenancyImpl.this.getValidationTextRange(astRoot);
		}
	}


	// ********** multitenant annotation **********

	protected EclipseLinkMultitenantAnnotation getMultitenantAnnotation() {
		return (EclipseLinkMultitenantAnnotation) this.getJavaResourceType().getNonNullAnnotation(this.getMultitenantAnnotationName());
	}

	protected TextRange getMultitenantAnnotationTextRange(CompilationUnit astRoot) {
		return getMultitenantAnnotation().getTextRange(astRoot);
	}

	public boolean isMultitenantAnnotationSpecified() {
		return this.getMultitenantAnnotation().isSpecified();
	}

	protected String getMultitenantAnnotationName() {
		return EclipseLinkMultitenantAnnotation.ANNOTATION_NAME;
	}

	public boolean isMultitenant() {
		return isMultitenantAnnotationSpecified();
	}


	// ********** tenant discriminator column annotations **********

	protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation> getTenantDiscriminatorColumnAnnotations() {
		return this.getTenantDiscriminatorColumnAnnotations_();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation> getTenantDiscriminatorColumnAnnotations_() {
		return new SubListIterableWrapper<NestableAnnotation, EclipseLinkTenantDiscriminatorColumnAnnotation>(this.getNestableTenantDiscriminatorColumnAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableTenantDiscriminatorColumnAnnotations_() {
		return this.getJavaResourceType().getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}

	protected EclipseLinkTenantDiscriminatorColumnAnnotation addTenantDiscriminatorColumnAnnotation(int index) {
		return (EclipseLinkTenantDiscriminatorColumnAnnotation) this.getJavaResourceType().addAnnotation(index, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}

	protected void removeTenantDiscriminatorColumnAnnotation(int index) {
		this.getJavaResourceType().removeAnnotation(index, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}

	protected void moveTenantDiscriminatorColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getJavaResourceType().moveAnnotation(targetIndex, sourceIndex, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}


	// ********** misc **********

	@Override
	public JavaEclipseLinkNonEmbeddableTypeMapping getParent() {
		return (JavaEclipseLinkNonEmbeddableTypeMapping) super.getParent();
	}

	protected JavaEclipseLinkNonEmbeddableTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	public JavaResourceType getJavaResourceType() {
		return this.getTypeMapping().getJavaResourceType();
	}

	protected boolean isParentInheritanceStrategySingleTableOrJoined() {
		InheritanceType inheritanceStrategy = getParent().getInheritanceStrategy();
		return inheritanceStrategy == InheritanceType.SINGLE_TABLE || inheritanceStrategy == InheritanceType.JOINED;
	}

	protected boolean isParentRootEntity() {
		return getParent().isRootEntity();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaTenantDiscriminatorColumn tenantDiscriminatorColumn : this.getSpecifiedTenantDiscriminatorColumns()) {
			result = tenantDiscriminatorColumn.getJavaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}

		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		//TODO validate 2.3 eclipselink does not use TABLE_PER_CLASS (and the new PROTECTED?? this wouldn't be in the 2.3 source)
		super.validate(messages, reporter, astRoot);
		if (getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			if (this.isParentInheritanceStrategySingleTableOrJoined() && !isParentRootEntity()) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.MULTIENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY,
						EMPTY_STRING_ARRAY,
						this,
						this.getJavaResourceType().getTextRange(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME, astRoot)
					)
				);
			}
			else if (isMultitenantAnnotationSpecified()) {
				for (JavaTenantDiscriminatorColumn column : this.getSpecifiedTenantDiscriminatorColumns()) {
					column.validate(messages, reporter, astRoot);
				}
			}
			else {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.MULTIENANT_NOT_SPECIFIED_WITH_TENANT_DISCRIMINATOR_COLUMNS,
						EMPTY_STRING_ARRAY,
						this,
						this.getJavaResourceType().getTextRange(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME, astRoot)
					)
				);
			}
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getMultitenantAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange(astRoot);
	}
}

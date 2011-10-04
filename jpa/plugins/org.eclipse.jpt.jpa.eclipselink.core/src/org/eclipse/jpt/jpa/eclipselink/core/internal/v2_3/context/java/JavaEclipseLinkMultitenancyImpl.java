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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.MappingFileRoot;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.TenantDiscriminatorColumnValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.NullEclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaVirtualTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkMultitenancyImpl
	extends AbstractJavaJpaContextNode
	implements JavaEclipseLinkMultitenancy
{
	protected boolean defaultMultitenant;
	protected boolean specifiedMultitenant;

	protected EclipseLinkMultitenantType specifiedType;
	protected EclipseLinkMultitenantType defaultType;

	protected Boolean specifiedIncludeCriteria;

	protected final JavaReadOnlyTenantDiscriminatorColumn.Owner tenantDiscriminatorColumnOwner;
	protected final ContextListContainer<JavaTenantDiscriminatorColumn, EclipseLinkTenantDiscriminatorColumnAnnotation> specifiedTenantDiscriminatorColumnContainer;
	protected final ContextListContainer<JavaVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn> defaultTenantDiscriminatorColumnContainer;

	protected final JavaReadOnlyTenantDiscriminatorColumn defaultTenantDiscriminatorColumn;

	public JavaEclipseLinkMultitenancyImpl(JavaEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);

		EclipseLinkMultitenantAnnotation multitenantAnnotation = this.getMultitenantAnnotation();
		this.specifiedMultitenant = multitenantAnnotation.isSpecified();
		this.specifiedType = EclipseLinkMultitenantType.fromJavaResourceModel(multitenantAnnotation.getValue());
		this.specifiedIncludeCriteria = multitenantAnnotation.getIncludeCriteria();
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = this.buildSpecifiedTenantDiscriminatorColumnContainer();
		this.defaultTenantDiscriminatorColumn = this.buildTenantDiscriminatorColumn(this.buildNullTenantDiscriminatorColumnAnnotation());
		this.defaultTenantDiscriminatorColumnContainer = this.buildDefaultTenantDiscriminatorColumnContainer();
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
		this.setSpecifiedMultitenant_(this.isMultitenantAnnotationSpecified());
		this.setDefaultMultitenant(this.buildDefaultMultitenant());
		this.setDefaultType(this.buildDefaultType());
		this.updateDefaultTenantDiscriminatorColumns();
	}


	// ********** multitenant **********

	public boolean isMultitenant() {
		return this.isSpecifiedMultitenant() || this.isDefaultMultitenant();
	}

	public boolean isDefaultMultitenant() {
		return this.defaultMultitenant;
	}

	public void setDefaultMultitenant(boolean defaultMultitenant) {
		boolean old = this.defaultMultitenant;
		this.defaultMultitenant = defaultMultitenant;
		this.firePropertyChanged(DEFAULT_MULTITENANT_PROPERTY, old, defaultMultitenant);
	}

	protected boolean buildDefaultMultitenant() {
		return this.isMultitenantInheritanceHierarchy() || this.isSuperMappedSuperclassMultitenant();
	}

	public boolean isSpecifiedMultitenant() {
		return this.specifiedMultitenant;
	}

	public void setSpecifiedMultitenant(boolean isMultitenant) {
		if (isMultitenant) {
			if (getMultitenantAnnotation().isSpecified()) {
				throw new IllegalStateException("Multitenant annotation already specified"); //$NON-NLS-1$
			}
			this.addMultitenantAnnotation();
		}
		else {
			if (!getMultitenantAnnotation().isSpecified()) {
				throw new IllegalStateException("Multitenant annotation does not exist"); //$NON-NLS-1$
			}
			this.removeMultitenantAnnotation();
		}
		this.setSpecifiedMultitenant_(isMultitenant);
	}

	protected void setSpecifiedMultitenant_(boolean isMultitenant) {
		boolean old = this.specifiedMultitenant;
		this.specifiedMultitenant = isMultitenant;
		this.firePropertyChanged(SPECIFIED_MULTITENANT_PROPERTY, old, isMultitenant);
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
		if (getType() != EclipseLinkMultitenantType.SINGLE_TABLE) {
			this.specifiedTenantDiscriminatorColumnContainer.clearContextList();
		}
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

	protected EclipseLinkMultitenantType buildDefaultType() {
		if (!isMultitenant()) {
			return null;
		}
		if (isSpecifiedMultitenant()) {
			return DEFAULT_TYPE;
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return getRootEntity().getMultitenancy().getType();
		}
		EclipseLinkMappedSuperclass superMappedSuperclass = getSuperMappedSuperclass();
		return superMappedSuperclass != null ? superMappedSuperclass.getMultitenancy().getType() : DEFAULT_TYPE;
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

	public ListIterable<JavaReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns() {
		return this.hasSpecifiedTenantDiscriminatorColumns() ?
			this.getReadOnlySpecifiedTenantDiscriminatorColumns() : 
			this.getReadOnlyDefaultTenantDiscriminatorColumns();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return this.hasSpecifiedTenantDiscriminatorColumns() ?
			this.getSpecifiedTenantDiscriminatorColumnsSize() : 
			this.getDefaultTenantDiscriminatorColumnsSize();
	}


	// ********** specified tenant discriminator columns **********

	public ListIterable<JavaTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<JavaReadOnlyTenantDiscriminatorColumn> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<JavaReadOnlyTenantDiscriminatorColumn>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
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

	protected EclipseLinkTenantDiscriminatorColumnAnnotation buildNullTenantDiscriminatorColumnAnnotation() {
		return new NullEclipseLinkTenantDiscriminatorColumnAnnotation(this.getJavaResourceType());
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


	// ********** default tenant discriminator columns **********

	public ListIterable<JavaVirtualTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<JavaReadOnlyTenantDiscriminatorColumn> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<JavaReadOnlyTenantDiscriminatorColumn>(this.getDefaultTenantDiscriminatorColumns());
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	protected void clearDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.clearContextList();
	}

	/**
	 * If there are any specified tenant discriminator columns, then there are no default
	 * tenant discriminator columns.
	 * @see #getTenantDiscriminatorColumnsForDefaults()
	 */
	protected void updateDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.update();
	}

	protected ContextListContainer<JavaVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn> buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}

	protected JavaVirtualTenantDiscriminatorColumn buildVirtualTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn tenantDiscriminatorColumn) {
		return new GenericJavaVirtualTenantDiscriminatorColumn(this, this.tenantDiscriminatorColumnOwner, tenantDiscriminatorColumn);
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getType() != EclipseLinkMultitenantType.SINGLE_TABLE) {
			return EmptyListIterable.instance();
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn>(this.getRootEntity().getMultitenancy().getTenantDiscriminatorColumns());
		}
		if (!this.isSpecifiedMultitenant()) {
			EclipseLinkMappedSuperclass superMappedSuperclass = this.getSuperMappedSuperclass();
			if (superMappedSuperclass != null && superMappedSuperclass.getMultitenancy().isMultitenant()) {
				return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn>(superMappedSuperclass.getMultitenancy().getTenantDiscriminatorColumns());
			}
		}
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() == 0) {
			ListIterable<ReadOnlyTenantDiscriminatorColumn> contextColumns = this.getContextDefaultTenantDiscriminatorColumns();
			if (CollectionTools.isEmpty(contextColumns)) {
				return new SingleElementListIterable<ReadOnlyTenantDiscriminatorColumn>(this.defaultTenantDiscriminatorColumn);
			}
			return contextColumns;
		}
		return EmptyListIterable.instance();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getContextDefaultTenantDiscriminatorColumns() {
		EclipseLinkEntityMappings entityMappings = this.getEclipseLinkEntityMappings();
		return (entityMappings != null) ? entityMappings.getTenantDiscriminatorColumns() : this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<JavaVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected JavaVirtualTenantDiscriminatorColumn buildContextElement(ReadOnlyTenantDiscriminatorColumn resourceElement) {
			return JavaEclipseLinkMultitenancyImpl.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected ReadOnlyTenantDiscriminatorColumn getResourceElement(JavaVirtualTenantDiscriminatorColumn contextElement) {
			return contextElement.getOverriddenColumn();
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
			return new TenantDiscriminatorColumnValidator((ReadOnlyTenantDiscriminatorColumn) column, textRangeResolver);
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return JavaEclipseLinkMultitenancyImpl.this.getValidationTextRange(astRoot);
		}
	}


	// ********** multitenant annotation **********

	protected EclipseLinkMultitenantAnnotation addMultitenantAnnotation() {
		return (EclipseLinkMultitenantAnnotation) this.getJavaResourceType().addAnnotation(this.getMultitenantAnnotationName());
	}

	protected void removeMultitenantAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getMultitenantAnnotationName());
	}

	protected EclipseLinkMultitenantAnnotation getMultitenantAnnotation() {
		return (EclipseLinkMultitenantAnnotation) this.getJavaResourceType().getNonNullAnnotation(this.getMultitenantAnnotationName());
	}

	protected TextRange getMultitenantAnnotationTextRange(CompilationUnit astRoot) {
		return this.getMultitenantAnnotation().getTextRange(astRoot);
	}

	public boolean isMultitenantAnnotationSpecified() {
		return this.getMultitenantAnnotation().isSpecified();
	}

	protected String getMultitenantAnnotationName() {
		return EclipseLinkMultitenantAnnotation.ANNOTATION_NAME;
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

	public EclipseLinkEntityMappings getEclipseLinkEntityMappings() {
		MappingFileRoot mfRoot = super.getMappingFileRoot();
		if (mfRoot instanceof EclipseLinkEntityMappings) {
			return (EclipseLinkEntityMappings) mfRoot;
		}
		return null;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	protected boolean isMultitenantMetadataAllowed() {
		return this.isRootEntity() || this.isInheritanceStrategyTablePerClass();
	}

	protected boolean isRootEntity() {
		return this.getParent().isRootEntity();
	}

	protected boolean isInheritanceStrategyTablePerClass() {
		return this.getParent().getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}

	protected boolean isMultitenantInheritanceHierarchy() {
		if (this.isInheritanceStrategyTablePerClass()) {
			return false;
		}
		return this.isRootEntityMultitenant();
	}

	protected boolean isRootEntityMultitenant() {
		EclipseLinkEntity rootEntity = this.getRootEntity();
		return rootEntity != null && rootEntity != getParent() && rootEntity.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkEntity getRootEntity() {
		//instanceof check in case the rootEntity is in an orm.xml instead of an eclipselinkorm.xml file.
		Entity entity = getParent().getRootEntity();
		return entity instanceof EclipseLinkEntity ? (EclipseLinkEntity) entity : null;
	}


	protected boolean isSuperMappedSuperclassMultitenant() {
		EclipseLinkMappedSuperclass mappedSuperclass = this.getSuperMappedSuperclass(getParent());
		return mappedSuperclass != null && mappedSuperclass.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkMappedSuperclass getSuperMappedSuperclass() {
		return this.getSuperMappedSuperclass(this.getParent());
	}

	protected EclipseLinkMappedSuperclass getSuperMappedSuperclass(TypeMapping typeMapping) {
		TypeMapping superTypeMapping = typeMapping.getSuperTypeMapping();
		if (superTypeMapping == null) {
			return null;
		}
		//instanceof check in case the mapped superclass is in an orm.xml instead of an eclipselinkorm.xml file.
		if (superTypeMapping instanceof EclipseLinkMappedSuperclass) {
			return (EclipseLinkMappedSuperclass) superTypeMapping;
		}
		return this.getSuperMappedSuperclass(superTypeMapping);
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
			if (!this.isMultitenantMetadataAllowed()) {
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
		for (JavaVirtualTenantDiscriminatorColumn column : this.getDefaultTenantDiscriminatorColumns()) {
			column.validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getMultitenantAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange(astRoot);
	}
}

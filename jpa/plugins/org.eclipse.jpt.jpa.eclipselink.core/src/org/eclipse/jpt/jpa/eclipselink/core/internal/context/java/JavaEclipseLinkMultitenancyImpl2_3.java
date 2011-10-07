/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.TenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.TenantDiscriminatorColumnValidator2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkMultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkMultitenancyImpl2_3
	extends AbstractJavaJpaContextNode
	implements JavaEclipseLinkMultitenancy2_3
{
	protected boolean defaultMultitenant;
	protected boolean specifiedMultitenant;

	protected EclipseLinkMultitenantType2_3 specifiedType;
	protected EclipseLinkMultitenantType2_3 defaultType;

	protected Boolean specifiedIncludeCriteria;

	protected final JavaReadOnlyTenantDiscriminatorColumn2_3.Owner tenantDiscriminatorColumnOwner;
	protected final ContextListContainer<JavaTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumnAnnotation2_3> specifiedTenantDiscriminatorColumnContainer;
	protected final ContextListContainer<JavaVirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumnContainer;

	protected final JavaReadOnlyTenantDiscriminatorColumn2_3 defaultTenantDiscriminatorColumn;

	public JavaEclipseLinkMultitenancyImpl2_3(JavaEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);

		EclipseLinkMultitenantAnnotation2_3 multitenantAnnotation = this.getMultitenantAnnotation();
		this.specifiedMultitenant = multitenantAnnotation.isSpecified();
		this.specifiedType = EclipseLinkMultitenantType2_3.fromJavaResourceModel(multitenantAnnotation.getValue());
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

		EclipseLinkMultitenantAnnotation2_3 multitenantAnnotation = this.getMultitenantAnnotation();
		this.setSpecifiedType_(EclipseLinkMultitenantType2_3.fromJavaResourceModel(multitenantAnnotation.getValue()));
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

	public EclipseLinkMultitenantType2_3 getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public EclipseLinkMultitenantType2_3 getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType2_3 type) {
		this.getMultitenantAnnotation().setValue(EclipseLinkMultitenantType2_3.toJavaResourceModel(type));
		this.setSpecifiedType_(type);
		if (getType() != EclipseLinkMultitenantType2_3.SINGLE_TABLE) {
			this.specifiedTenantDiscriminatorColumnContainer.clearContextList();
		}
	}

	protected void setSpecifiedType_(EclipseLinkMultitenantType2_3 type) {
		EclipseLinkMultitenantType2_3 old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	public EclipseLinkMultitenantType2_3 getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(EclipseLinkMultitenantType2_3 type) {
		EclipseLinkMultitenantType2_3 old = this.defaultType;
		this.defaultType = type;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkMultitenantType2_3 buildDefaultType() {
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

	public ListIterable<JavaReadOnlyTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
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

	public ListIterable<JavaTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<JavaReadOnlyTenantDiscriminatorColumn2_3> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<JavaReadOnlyTenantDiscriminatorColumn2_3>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
	}

	public JavaTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public JavaTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		EclipseLinkTenantDiscriminatorColumnAnnotation2_3 annotation = this.addTenantDiscriminatorColumnAnnotation(index);
		return this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOfContextElement((JavaTenantDiscriminatorColumn2_3) tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.removeTenantDiscriminatorColumnAnnotation(index);
		this.specifiedTenantDiscriminatorColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.moveTenantDiscriminatorColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedTenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaTenantDiscriminatorColumn2_3 buildTenantDiscriminatorColumn(EclipseLinkTenantDiscriminatorColumnAnnotation2_3 discriminatorColumnAnnotation) {
		return new EclipseLinkJavaTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, discriminatorColumnAnnotation);
	}

	protected EclipseLinkTenantDiscriminatorColumnAnnotation2_3 buildNullTenantDiscriminatorColumnAnnotation() {
		return new NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3(this.getJavaResourceType());
	}

	protected void syncSpecifiedTenantDiscriminatorColumns() {
		this.specifiedTenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ContextListContainer<JavaTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumnAnnotation2_3> buildSpecifiedTenantDiscriminatorColumnContainer() {
		return new SpecifiedTenantDiscriminatorColumnContainer();
	}

	protected JavaReadOnlyTenantDiscriminatorColumn2_3.Owner buildTenantDiscriminatorColumnOwner() {
		return new TenantDiscriminatorColumnOwner();
	}

	/**
	 * specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<JavaTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumnAnnotation2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected JavaTenantDiscriminatorColumn2_3 buildContextElement(EclipseLinkTenantDiscriminatorColumnAnnotation2_3 resourceElement) {
			return JavaEclipseLinkMultitenancyImpl2_3.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation2_3> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getTenantDiscriminatorColumnAnnotations();
		}
		@Override
		protected EclipseLinkTenantDiscriminatorColumnAnnotation2_3 getResourceElement(JavaTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}


	// ********** default tenant discriminator columns **********

	public ListIterable<JavaVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<JavaReadOnlyTenantDiscriminatorColumn2_3> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<JavaReadOnlyTenantDiscriminatorColumn2_3>(this.getDefaultTenantDiscriminatorColumns());
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

	protected ContextListContainer<JavaVirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}

	protected JavaVirtualTenantDiscriminatorColumn2_3 buildVirtualTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		return new EclipseLinkJavaVirtualTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, tenantDiscriminatorColumn);
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getType() != EclipseLinkMultitenantType2_3.SINGLE_TABLE) {
			return EmptyListIterable.instance();
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(this.getRootEntity().getMultitenancy().getTenantDiscriminatorColumns());
		}
		if (!this.isSpecifiedMultitenant()) {
			EclipseLinkMappedSuperclass superMappedSuperclass = this.getSuperMappedSuperclass();
			if (superMappedSuperclass != null && superMappedSuperclass.getMultitenancy().isMultitenant()) {
				return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(superMappedSuperclass.getMultitenancy().getTenantDiscriminatorColumns());
			}
		}
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() == 0) {
			ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> contextColumns = this.getContextDefaultTenantDiscriminatorColumns();
			if (CollectionTools.isEmpty(contextColumns)) {
				return new SingleElementListIterable<ReadOnlyTenantDiscriminatorColumn2_3>(this.defaultTenantDiscriminatorColumn);
			}
			return contextColumns;
		}
		return EmptyListIterable.instance();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getContextDefaultTenantDiscriminatorColumns() {
		EclipseLinkEntityMappings entityMappings = this.getEclipseLinkEntityMappings();
		return (entityMappings != null) ? entityMappings.getTenantDiscriminatorColumns() : this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<JavaVirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected JavaVirtualTenantDiscriminatorColumn2_3 buildContextElement(ReadOnlyTenantDiscriminatorColumn2_3 resourceElement) {
			return JavaEclipseLinkMultitenancyImpl2_3.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected ReadOnlyTenantDiscriminatorColumn2_3 getResourceElement(JavaVirtualTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}



	// ********** JavaReadOnlyTenantDiscriminatorColumn.Owner implementation **********

	protected class TenantDiscriminatorColumnOwner 
		implements JavaReadOnlyTenantDiscriminatorColumn2_3.Owner
	{

		public String getDefaultContextPropertyName() {
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY;
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
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME;
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
			return new TenantDiscriminatorColumnValidator2_3((ReadOnlyTenantDiscriminatorColumn2_3) column, textRangeResolver);
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getValidationTextRange(astRoot);
		}
	}


	// ********** multitenant annotation **********

	protected EclipseLinkMultitenantAnnotation2_3 addMultitenantAnnotation() {
		return (EclipseLinkMultitenantAnnotation2_3) this.getJavaResourceType().addAnnotation(this.getMultitenantAnnotationName());
	}

	protected void removeMultitenantAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getMultitenantAnnotationName());
	}

	protected EclipseLinkMultitenantAnnotation2_3 getMultitenantAnnotation() {
		return (EclipseLinkMultitenantAnnotation2_3) this.getJavaResourceType().getNonNullAnnotation(this.getMultitenantAnnotationName());
	}

	protected TextRange getMultitenantAnnotationTextRange(CompilationUnit astRoot) {
		return this.getMultitenantAnnotation().getTextRange(astRoot);
	}

	public boolean isMultitenantAnnotationSpecified() {
		return this.getMultitenantAnnotation().isSpecified();
	}

	protected String getMultitenantAnnotationName() {
		return EclipseLinkMultitenantAnnotation2_3.ANNOTATION_NAME;
	}


	// ********** tenant discriminator column annotations **********

	protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation2_3> getTenantDiscriminatorColumnAnnotations() {
		return this.getTenantDiscriminatorColumnAnnotations_();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumnAnnotation2_3> getTenantDiscriminatorColumnAnnotations_() {
		return new SubListIterableWrapper<NestableAnnotation, EclipseLinkTenantDiscriminatorColumnAnnotation2_3>(this.getNestableTenantDiscriminatorColumnAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableTenantDiscriminatorColumnAnnotations_() {
		return this.getJavaResourceType().getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected EclipseLinkTenantDiscriminatorColumnAnnotation2_3 addTenantDiscriminatorColumnAnnotation(int index) {
		return (EclipseLinkTenantDiscriminatorColumnAnnotation2_3) this.getJavaResourceType().addAnnotation(index, EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected void removeTenantDiscriminatorColumnAnnotation(int index) {
		this.getJavaResourceType().removeAnnotation(index, EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected void moveTenantDiscriminatorColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getJavaResourceType().moveAnnotation(targetIndex, sourceIndex, EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
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
		for (JavaTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn : this.getSpecifiedTenantDiscriminatorColumns()) {
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
						this.getJavaResourceType().getTextRange(EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME, astRoot)
					)
				);
			}
			else if (isMultitenantAnnotationSpecified()) {
				for (JavaTenantDiscriminatorColumn2_3 column : this.getSpecifiedTenantDiscriminatorColumns()) {
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
						this.getJavaResourceType().getTextRange(EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME, astRoot)
					)
				);
			}
		}
		for (JavaVirtualTenantDiscriminatorColumn2_3 column : this.getDefaultTenantDiscriminatorColumns()) {
			column.validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getMultitenantAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange(astRoot);
	}
}

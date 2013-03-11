/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.TargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_4JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.TenantDiscriminatorColumnValidator2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkMultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkMultitenancyImpl2_3
	extends AbstractJavaContextModel<EclipseLinkJavaNonEmbeddableTypeMapping>
	implements EclipseLinkJavaMultitenancy2_3
{
	protected boolean defaultMultitenant;
	protected boolean specifiedMultitenant;

	protected EclipseLinkMultitenantType2_3 specifiedType;
	protected EclipseLinkMultitenantType2_3 defaultType;

	protected Boolean specifiedIncludeCriteria;
	protected boolean defaultIncludeCriteria = DEFAULT_INCLUDE_CRITERIA;

	protected final EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter tenantDiscriminatorColumnParentAdapter;
	protected final ContextListContainer<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3, TenantDiscriminatorColumnAnnotation2_3> specifiedTenantDiscriminatorColumnContainer;
	protected final ContextListContainer<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumnContainer;

	protected final EclipseLinkTenantDiscriminatorColumn2_3 defaultTenantDiscriminatorColumn;

	protected boolean specifiedTenantDiscriminatorColumnsAllowed;

	public JavaEclipseLinkMultitenancyImpl2_3(EclipseLinkJavaNonEmbeddableTypeMapping parent) {
		super(parent);

		EclipseLinkMultitenantAnnotation2_3 multitenantAnnotation = this.getMultitenantAnnotation();
		this.specifiedMultitenant = multitenantAnnotation.isSpecified();
		this.specifiedType = EclipseLinkMultitenantType2_3.fromJavaResourceModel(multitenantAnnotation.getValue());
		this.specifiedIncludeCriteria = multitenantAnnotation.getIncludeCriteria();
		this.tenantDiscriminatorColumnParentAdapter = this.buildTenantDiscriminatorColumnParentAdapter();
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
		this.updateModels(this.getSpecifiedTenantDiscriminatorColumns());
		this.setSpecifiedMultitenant_(this.isMultitenantAnnotationSpecified());
		this.setDefaultMultitenant(this.buildDefaultMultitenant());
		this.setDefaultType(this.buildDefaultType());
		this.setDefaultIncludeCriteria(this.buildDefaultIncludeCriteria());
		this.setSpecifiedTenantDiscriminatorColumnsAllowed(this.buildSpecifiedTenantDiscriminatorColumnsAllowed());
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
			this.setSpecifiedType(null);
			for (int i = this.getSpecifiedTenantDiscriminatorColumnsSize(); i-- > 0; ) {
				this.removeSpecifiedTenantDiscriminatorColumn(i);
			}
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
		return this.defaultIncludeCriteria;
	}

	protected void setDefaultIncludeCriteria(boolean includeCriteria) {
		boolean old = this.defaultIncludeCriteria;
		this.defaultIncludeCriteria = includeCriteria;
		this.firePropertyChanged(DEFAULT_INCLUDE_CRITERIA_PROPERTY, old, includeCriteria);
	}

	protected boolean buildDefaultIncludeCriteria() {
		if (getType() == EclipseLinkMultitenantType2_3.VPD) {
			return false;
		}
		return DEFAULT_INCLUDE_CRITERIA;
	}


	// ********** tenant discriminator columns **********

	public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
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

	public ListIterable<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
	}

	public EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		TenantDiscriminatorColumnAnnotation2_3 annotation = this.addTenantDiscriminatorColumnAnnotation(index);
		return this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedTenantDiscriminatorColumn(EclipseLinkSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOfContextElement((EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3) tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.removeTenantDiscriminatorColumnAnnotation(index);
		this.specifiedTenantDiscriminatorColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.moveTenantDiscriminatorColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedTenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 buildTenantDiscriminatorColumn(TenantDiscriminatorColumnAnnotation2_3 discriminatorColumnAnnotation) {
		return new EclipseLinkJavaTenantDiscriminatorColumn2_3(this.tenantDiscriminatorColumnParentAdapter, discriminatorColumnAnnotation);
	}

	protected TenantDiscriminatorColumnAnnotation2_3 buildNullTenantDiscriminatorColumnAnnotation() {
		return new NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3(this.getJavaResourceType());
	}

	protected void syncSpecifiedTenantDiscriminatorColumns() {
		this.specifiedTenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ContextListContainer<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3, TenantDiscriminatorColumnAnnotation2_3> buildSpecifiedTenantDiscriminatorColumnContainer() {
		SpecifiedTenantDiscriminatorColumnContainer container = new SpecifiedTenantDiscriminatorColumnContainer();
		container.initialize();
		return container;
	}

	protected EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter buildTenantDiscriminatorColumnParentAdapter() {
		return new TenantDiscriminatorColumnParentAdapter();
	}

	/**
	 * specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3, TenantDiscriminatorColumnAnnotation2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 buildContextElement(TenantDiscriminatorColumnAnnotation2_3 resourceElement) {
			return JavaEclipseLinkMultitenancyImpl2_3.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<TenantDiscriminatorColumnAnnotation2_3> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getTenantDiscriminatorColumnAnnotations();
		}
		@Override
		protected TenantDiscriminatorColumnAnnotation2_3 getResourceElement(EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}


	// ********** default tenant discriminator columns **********

	public ListIterable<EclipseLinkVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.getDefaultTenantDiscriminatorColumns());
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

	protected ContextListContainer<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}

	protected EclipseLinkVirtualTenantDiscriminatorColumn2_3 buildVirtualTenantDiscriminatorColumn(EclipseLinkTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		return new EclipseLinkJavaVirtualTenantDiscriminatorColumn2_3(this.tenantDiscriminatorColumnParentAdapter, tenantDiscriminatorColumn);
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getType() == null || this.getType() == EclipseLinkMultitenantType2_3.TABLE_PER_TENANT) {
			return EmptyListIterable.instance();
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.getRootEntity().getMultitenancy().getTenantDiscriminatorColumns());
		}
		if (!this.isSpecifiedMultitenant()) {
			EclipseLinkMappedSuperclass superMappedSuperclass = this.getSuperMappedSuperclass();
			if (superMappedSuperclass != null && superMappedSuperclass.getMultitenancy().isMultitenant()) {
				return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(superMappedSuperclass.getMultitenancy().getTenantDiscriminatorColumns());
			}
		}
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() == 0) {
			ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> contextColumns = this.getContextDefaultTenantDiscriminatorColumns();
			if (IterableTools.isEmpty(contextColumns)) {
				return new SingleElementListIterable<EclipseLinkTenantDiscriminatorColumn2_3>(this.defaultTenantDiscriminatorColumn);
			}
			return contextColumns;
		}
		return EmptyListIterable.instance();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getContextDefaultTenantDiscriminatorColumns() {
		EclipseLinkEntityMappings entityMappings = this.getEclipseLinkEntityMappings();
		return (entityMappings != null) ? entityMappings.getTenantDiscriminatorColumns() : this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}

	public boolean specifiedTenantDiscriminatorColumnsAllowed() {
		return this.specifiedTenantDiscriminatorColumnsAllowed;
	}

	public void setSpecifiedTenantDiscriminatorColumnsAllowed(boolean allowed) {
		boolean old = this.specifiedTenantDiscriminatorColumnsAllowed;
		this.specifiedTenantDiscriminatorColumnsAllowed = allowed;
		this.firePropertyChanged(SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_ALLOWED_PROPERTY, old, allowed);
	}

	protected boolean buildSpecifiedTenantDiscriminatorColumnsAllowed() {
		return this.parent.isMultitenantMetadataAllowed();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected EclipseLinkVirtualTenantDiscriminatorColumn2_3 buildContextElement(EclipseLinkTenantDiscriminatorColumn2_3 resourceElement) {
			return JavaEclipseLinkMultitenancyImpl2_3.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getResourceElements() {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected EclipseLinkTenantDiscriminatorColumn2_3 getResourceElement(EclipseLinkVirtualTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** tenant discriminator column parent adapter **********

	public class TenantDiscriminatorColumnParentAdapter
		implements EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return JavaEclipseLinkMultitenancyImpl2_3.this;
		}

		public String getDefaultContextPropertyName() {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY;
		}

		public int getDefaultLength() {
			return NamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return NamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME;
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

		public JptValidator buildColumnValidator(NamedColumn column) {
			return new TenantDiscriminatorColumnValidator2_3((EclipseLinkTenantDiscriminatorColumn2_3) column);
		}

		public TextRange getValidationTextRange() {
			return JavaEclipseLinkMultitenancyImpl2_3.this.getValidationTextRange();
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

	protected TextRange getMultitenantAnnotationTextRange() {
		return this.getMultitenantAnnotation().getTextRange();
	}

	public boolean isMultitenantAnnotationSpecified() {
		return this.getMultitenantAnnotation().isSpecified();
	}

	protected String getMultitenantAnnotationName() {
		return EclipseLinkMultitenantAnnotation2_3.ANNOTATION_NAME;
	}


	// ********** tenant discriminator column annotations **********

	protected ListIterable<TenantDiscriminatorColumnAnnotation2_3> getTenantDiscriminatorColumnAnnotations() {
		return this.getTenantDiscriminatorColumnAnnotations_();
	}

	protected ListIterable<TenantDiscriminatorColumnAnnotation2_3> getTenantDiscriminatorColumnAnnotations_() {
		return new SubListIterableWrapper<NestableAnnotation, TenantDiscriminatorColumnAnnotation2_3>(this.getNestableTenantDiscriminatorColumnAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableTenantDiscriminatorColumnAnnotations_() {
		return this.getJavaResourceType().getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected TenantDiscriminatorColumnAnnotation2_3 addTenantDiscriminatorColumnAnnotation(int index) {
		return (TenantDiscriminatorColumnAnnotation2_3) this.getJavaResourceType().addAnnotation(index, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected void removeTenantDiscriminatorColumnAnnotation(int index) {
		this.getJavaResourceType().removeAnnotation(index, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}

	protected void moveTenantDiscriminatorColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getJavaResourceType().moveAnnotation(targetIndex, sourceIndex, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
	}


	// ********** misc **********

	protected EclipseLinkJavaNonEmbeddableTypeMapping getTypeMapping() {
		return this.parent;
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	public JavaResourceType getJavaResourceType() {
		return this.getTypeMapping().getJavaResourceType();
	}

	public EclipseLinkEntityMappings getEclipseLinkEntityMappings() {
		MappingFile.Root mfRoot = super.getMappingFileRoot();
		if (mfRoot instanceof EclipseLinkEntityMappings) {
			return (EclipseLinkEntityMappings) mfRoot;
		}
		return null;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	protected boolean isMultitenantInheritanceHierarchy() {
		if (this.isInheritanceStrategyTablePerClass()) {
			return false;
		}
		return this.isRootEntityMultitenant();
	}

	protected boolean isInheritanceStrategyTablePerClass() {
		return this.parent.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}

	protected boolean isRootEntityMultitenant() {
		EclipseLinkEntity rootEntity = this.getRootEntity();
		return rootEntity != null && rootEntity != this.parent && rootEntity.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkEntity getRootEntity() {
		//instanceof check in case the rootEntity is in an orm.xml instead of an eclipselinkorm.xml file.
		Entity entity = this.parent.getRootEntity();
		return entity instanceof EclipseLinkEntity ? (EclipseLinkEntity) entity : null;
	}


	protected boolean isSuperMappedSuperclassMultitenant() {
		EclipseLinkMappedSuperclass mappedSuperclass = this.getSuperMappedSuperclass(this.parent);
		return mappedSuperclass != null && mappedSuperclass.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkMappedSuperclass getSuperMappedSuperclass() {
		return this.getSuperMappedSuperclass(this.parent);
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

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		for (EclipseLinkTenantDiscriminatorColumn2_3 column : getTenantDiscriminatorColumns()) {
			if (column.isPrimaryKey()) {
				return true;
			}
		}
		return false;
	}

	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn : this.getSpecifiedTenantDiscriminatorColumns()) {
			result = tenantDiscriminatorColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}

		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getSpecifiedType() == EclipseLinkMultitenantType2_3.TABLE_PER_TENANT && ! this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLink2_4JpaPlatformFactory.VERSION)) {
			messages.add(
				this.buildValidationMessage(
					this.getMultitenantAnnotationTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_TABLE_PER_TENANT_NOT_SUPPORTED
				)
			);			
		}
		if (getSpecifiedType() == EclipseLinkMultitenantType2_3.VPD) {
			String targetDatabase = getPersistenceUnit().getEclipseLinkOptions().getTargetDatabase();
			if (targetDatabase == null) {
				messages.add(
					this.buildValidationMessage(
						this.getMultitenantAnnotationTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_MIGHT_NOT_BE_NOT_SUPPORTED
					)
				);
			}
			else if (!TargetDatabase.isOracleDatabase(targetDatabase)) {
				messages.add(
					this.buildValidationMessage(
						this.getMultitenantAnnotationTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_NOT_SUPPORTED_ON_NON_ORACLE_DATABASE_PLATFORM,
						targetDatabase
					)
				);
			}
		}
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			if (!this.specifiedTenantDiscriminatorColumnsAllowed()) {
				messages.add(
					this.buildValidationMessage(
						this.getJavaResourceType().getTextRange(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME),
						JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY
					)
				);
			}
			else if (this.isMultitenantAnnotationSpecified()) {
				for (EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 column : this.getSpecifiedTenantDiscriminatorColumns()) {
					column.validate(messages, reporter);
				}
			}
			else {
				messages.add(
					this.buildValidationMessage(
						this.getJavaResourceType().getTextRange(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME),
						JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_NOT_SPECIFIED_WITH_TENANT_DISCRIMINATOR_COLUMNS
					)
				);
			}
		}
		if (this.specifiedTenantDiscriminatorColumnsAllowed()) {
			//bug 360731 no need to validate, they will be validated where they are specified 
			for (EclipseLinkVirtualTenantDiscriminatorColumn2_3 column : this.getDefaultTenantDiscriminatorColumns()) {
				column.validate(messages, reporter);
			}
		}
		if (this.getSpecifiedIncludeCriteria() == Boolean.TRUE && getType() == EclipseLinkMultitenantType2_3.VPD) {
			messages.add(
				this.buildValidationMessage(
					this.getMultitenantAnnotation().getIncludeCriteriaTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_INCLUDE_CRITERIA_WILL_BE_IGNORED
				)
			);			
		}
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getMultitenantAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}
}

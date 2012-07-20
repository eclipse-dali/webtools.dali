/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_3JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkDynamicTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.plugin.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkClassExtractorAnnotation2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * <code>orm.xml</code> entity type mapping
 */
public class OrmEclipseLinkEntityImpl
	extends AbstractOrmEntity<XmlEntity>
	implements
		OrmEclipseLinkEntity,
		OrmEclipseLinkConverterContainer.Owner
{
	protected final OrmEclipseLinkReadOnly readOnly;

	protected final OrmEclipseLinkCustomizer customizer;

	protected final OrmEclipseLinkChangeTracking changeTracking;

	protected final OrmEclipseLinkCaching caching;

	protected final OrmEclipseLinkConverterContainer converterContainer;

	protected final OrmEclipseLinkMultitenancy2_3 multitenancy;


	public OrmEclipseLinkEntityImpl(OrmPersistentType parent, XmlEntity xmlEntity) {
		super(parent, xmlEntity);
		this.caching = this.buildCaching();
		this.readOnly = this.buildReadOnly();
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
		this.multitenancy = this.buildMultitenancy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.caching.synchronizeWithResourceModel();
		this.readOnly.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.changeTracking.synchronizeWithResourceModel();
		this.customizer.synchronizeWithResourceModel();
		this.multitenancy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.caching.update();
		this.readOnly.update();
		this.converterContainer.update();
		this.changeTracking.update();
		this.customizer.update();
		this.multitenancy.update();
	}


	// ********** caching **********

	public OrmEclipseLinkCaching getCaching() {
		return this.caching;
	}

	protected OrmEclipseLinkCaching buildCaching() {
		return new OrmEclipseLinkCachingImpl(this);
	}


	// ********** read only **********

	public OrmEclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}

	protected OrmEclipseLinkReadOnly buildReadOnly() {
		return new OrmEclipseLinkReadOnly(this);
	}


	// ********** converter container **********

	public OrmEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected OrmEclipseLinkConverterContainer buildConverterContainer() {
		return new OrmEclipseLinkConverterContainerImpl(this, this, this.xmlTypeMapping);
	}

	public int getNumberSupportedConverters() {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getConverters() {
		return new CompositeIterable<EclipseLinkConverter>(
					this.converterContainer.getConverters(),
					this.getAttributeMappingConverters()
				);
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters() {
		return new FilteringIterable<EclipseLinkConverter>(this.getAttributeMappingConverters_(), NotNullFilter.<EclipseLinkConverter>instance());
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters_() {
		return new CompositeIterable<EclipseLinkConverter>(this.getAttributeMappingConverterLists());
	}

	protected Iterable<Iterable<EclipseLinkConverter>> getAttributeMappingConverterLists() {
		return new TransformationIterable<AttributeMapping, Iterable<EclipseLinkConverter>>(
				this.getAttributeMappings(),
				ATTRIBUTE_MAPPING_CONVERTER_TRANSFORMER
			);
	}


	// ********** change tracking **********

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	protected OrmEclipseLinkChangeTracking buildChangeTracking() {
		return new OrmEclipseLinkChangeTracking(this);
	}


	// ********** customizer **********

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	protected OrmEclipseLinkCustomizer buildCustomizer() {
		return new OrmEclipseLinkCustomizer(this);
	}


	// ********** multitenancy **********

	public OrmEclipseLinkMultitenancy2_3 getMultitenancy() {
		return this.multitenancy;
	}

	protected OrmEclipseLinkMultitenancy2_3 buildMultitenancy() {
		return this.isEclipseLink2_3Compatible() ?
			new OrmEclipseLinkMultitenancyImpl2_3(this) :
			new NullOrmEclipseLinkMultitenancy2_3(this);
	}

	protected boolean isEclipseLink2_3Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLink2_3JpaPlatformFactory.VERSION);
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}

	public boolean isMultitenantMetadataAllowed() {
		return this.isRootEntity() || this.isInheritanceStrategyTablePerClass();
	}

	protected boolean isInheritanceStrategyTablePerClass() {
		return this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}


	// ********** parent class **********

	@Override
	protected String buildSpecifiedParentClass() {
		return this.xmlTypeMapping.getParentClass();
	}

	@Override
	public void setSpecifiedParentClassInXml(String parentClass) {
		this.xmlTypeMapping.setParentClass(parentClass);
	}


	// ********** misc **********

	@Override
	public JavaEclipseLinkEntity getJavaTypeMapping() {
		return (JavaEclipseLinkEntity) super.getJavaTypeMapping();
	}

	@Override
	public JavaEclipseLinkEntity getJavaTypeMappingForDefaults() {
		return (JavaEclipseLinkEntity) super.getJavaTypeMappingForDefaults();
	}

	@Override
	public EclipseLinkOrmPersistentType getPersistentType() {
		return (EclipseLinkOrmPersistentType) super.getPersistentType();
	}

	public boolean usesPrimaryKeyColumns() {
		return (this.getXmlTypeMapping().getPrimaryKey() != null)
				|| this.usesJavaPrimaryKeyColumns();
	}

	protected boolean usesJavaPrimaryKeyColumns() {
		JavaEclipseLinkEntity javaEntity = this.getJavaTypeMappingForDefaults();
		return (javaEntity != null) && javaEntity.usesPrimaryKeyColumns();
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return getMultitenancy().usesPrimaryKeyTenantDiscriminatorColumns();
	}

	@Override
	protected boolean buildSpecifiedDiscriminatorColumnIsAllowed() {
		return super.buildSpecifiedDiscriminatorColumnIsAllowed() && ! this.classExtractorIsUsed();
	}

	protected boolean classExtractorIsUsed() {
		return (this.getXmlClassExtractor() != null) || (this.getClassExtractorAnnotation() != null);
	}

	protected XmlClassReference getXmlClassExtractor() {
		return this.getXmlTypeMapping().getClassExtractor();
	}

	protected EclipseLinkClassExtractorAnnotation2_1 getClassExtractorAnnotation() {
		if (this.getJavaTypeMappingForDefaults() != null) {
			JavaResourceType jrpt = this.getJavaPersistentType().getJavaResourceType();
			return (EclipseLinkClassExtractorAnnotation2_1) jrpt.getAnnotation(EclipseLinkClassExtractorAnnotation2_1.ANNOTATION_NAME);
		}
		return null;
	}

	public OrmCacheable2_0 getCacheable() {
		return this.getCacheableHolder().getCacheable();
	}

	public boolean calculateDefaultCacheable() {
		return this.getCacheableHolder().calculateDefaultCacheable();
	}

	protected OrmCacheableHolder2_0 getCacheableHolder() {
		return (OrmCacheableHolder2_0) this.caching;
	}

	public XmlCacheable_2_0 getXmlCacheable() {
		return this.getXmlTypeMapping();
	}

	protected JavaResourceAbstractType getResourceClassExtractorType() {
		XmlClassReference classExtractorClassRef = this.getXmlClassExtractor();
		if (classExtractorClassRef == null) {
			return null;
		}

		String className = classExtractorClassRef.getClassName();
		if (className == null) {
			return null;
		}

		return this.getMappingFileRoot().resolveJavaResourceType(className);
	}

	protected boolean classExtractorIsFor(String typeName) {
		JavaResourceAbstractType classExtractorType = this.getResourceClassExtractorType();
		return (classExtractorType != null) && classExtractorType.getTypeBinding().getQualifiedName().equals(typeName);
	}

	protected boolean classExtractorIsIn(IPackageFragment packageFragment) {
		JavaResourceAbstractType classExtractorType = this.getResourceClassExtractorType();
		return (classExtractorType != null) && classExtractorType.isIn(packageFragment);
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenameTypeEdits(originalType, newName),
				this.createCustomizerRenameTypeEdits(originalType, newName),
				this.createConverterHolderRenameTypeEdits(originalType, newName),
				this.createClassExtractorRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenameTypeEdits(IType originalType, String newName) {
		return this.customizer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createConverterHolderRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createClassExtractorRenameTypeEdits(IType originalType, String newName) {
		return this.classExtractorIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.getXmlClassExtractor().createRenameEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createCustomizerMoveTypeEdits(originalType, newPackage),
				this.createConverterHolderMoveTypeEdits(originalType, newPackage),
				this.createClassExtractorMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.customizer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createConverterHolderMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createClassExtractorMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.classExtractorIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.getXmlClassExtractor().createRenamePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createCustomizerRenamePackageEdits(originalPackage, newName),
				this.createConverterHolderRenamePackageEdits(originalPackage, newName),
				this.createClassExtractorRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.customizer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createConverterHolderRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterContainer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createClassExtractorRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.classExtractorIsIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.getXmlClassExtractor().createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateParentClass(messages, reporter);
		this.caching.validate(messages, reporter);
		this.readOnly.validate(messages, reporter);
		this.converterContainer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.multitenancy.validate(messages, reporter);
	}

	@Override
	protected JptValidator buildPrimaryKeyValidator() {
		return new EclipseLinkEntityPrimaryKeyValidator(this, buildTextRangeResolver());
	}

	@Override
	protected JptValidator buildTypeMappingValidator() {
		if (this.isDynamicType()) {
			return new EclipseLinkDynamicTypeMappingValidator(this);
		}
		return new EclipseLinkTypeMappingValidator(this, getJavaResourceType(), buildTextRangeResolver());
	}

	protected boolean isDynamicType() {
		return this.getPersistentType().isDynamic();
	}

	protected void validateParentClass(List<IMessage> messages, IReporter reporter) {
		if (this.specifiedParentClass == null) {
			return;
		}
		if (this.getResolvedParentClass() == null) {
			IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedParentClass());
			if (jdtType == null) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						EclipseLinkJpaValidationMessages.VIRTUAL_TYPE_PARENT_CLASS_DOES_NOT_EXIST,
						new String[] {this.getFullyQualifiedParentClass()},
						this,
						this.getParentClassTextRange()
					)
				);
			}
		}
	}

	protected TextRange getParentClassTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getParentClassTextRange());
	}
	

	// ********** completion proposals **********

	@Override
	public Iterable<String> getXmlCompletionProposals(int pos) {
		Iterable<String> result = super.getXmlCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.multitenancy.getXmlCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

}

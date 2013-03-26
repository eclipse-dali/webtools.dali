/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ClassExtractorAnnotation2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * <code>orm.xml</code> entity type mapping
 */
public class EclipseLinkOrmEntityImpl
	extends AbstractOrmEntity<XmlEntity>
	implements EclipseLinkOrmEntity, EclipseLinkOrmConverterContainer.Parent
{
	protected final EclipseLinkOrmReadOnly readOnly;

	protected final EclipseLinkOrmCustomizer customizer;

	protected final EclipseLinkOrmChangeTracking changeTracking;

	protected final EclipseLinkCaching caching;

	protected final EclipseLinkOrmConverterContainer converterContainer;

	protected final EclipseLinkOrmMultitenancy2_3 multitenancy;


	public EclipseLinkOrmEntityImpl(OrmPersistentType parent, XmlEntity xmlEntity) {
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

	public EclipseLinkCaching getCaching() {
		return this.caching;
	}

	protected EclipseLinkCaching buildCaching() {
		return new EclipseLinkOrmCachingImpl(this);
	}


	// ********** read only **********

	public EclipseLinkOrmReadOnly getReadOnly() {
		return this.readOnly;
	}

	protected EclipseLinkOrmReadOnly buildReadOnly() {
		return new EclipseLinkOrmReadOnly(this);
	}


	// ********** converter container **********

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkOrmMappingConverterContainer(this, this.xmlTypeMapping);
	}

	public int getMaximumAllowedConverters() {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getConverters() {
		return IterableTools.concatenate(
					this.converterContainer.getConverters(),
					this.getAttributeMappingConverters()
				);
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters() {
		return IterableTools.removeNulls(this.getAttributeMappingConverters_());
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters_() {
		return IterableTools.children(this.getAttributeMappings(), EclipseLinkConvertibleMapping.ATTRIBUTE_MAPPING_CONVERTERS_TRANSFORMER);
	}


	// ********** change tracking **********

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	protected EclipseLinkOrmChangeTracking buildChangeTracking() {
		return new EclipseLinkOrmChangeTracking(this);
	}


	// ********** customizer **********

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	protected EclipseLinkOrmCustomizer buildCustomizer() {
		return new EclipseLinkOrmCustomizer(this);
	}


	// ********** multitenancy **********

	public EclipseLinkOrmMultitenancy2_3 getMultitenancy() {
		return this.multitenancy;
	}

	protected EclipseLinkOrmMultitenancy2_3 buildMultitenancy() {
		return this.isEclipseLink2_3Compatible() ?
			new EclipseLinkOrmMultitenancyImpl2_3(this) :
			new NullOrmEclipseLinkMultitenancy2_3(this);
	}

	protected boolean isEclipseLink2_3Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLinkJpaPlatformFactory2_3.VERSION);
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
	public EclipseLinkJavaEntity getJavaTypeMapping() {
		return (EclipseLinkJavaEntity) super.getJavaTypeMapping();
	}

	@Override
	public EclipseLinkJavaEntity getJavaTypeMappingForDefaults() {
		return (EclipseLinkJavaEntity) super.getJavaTypeMappingForDefaults();
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
		EclipseLinkJavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
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

	protected ClassExtractorAnnotation2_1 getClassExtractorAnnotation() {
		if (this.getJavaTypeMappingForDefaults() != null) {
			JavaResourceType jrpt = this.getJavaPersistentType().getJavaResourceType();
			return (ClassExtractorAnnotation2_1) jrpt.getAnnotation(ClassExtractorAnnotation2_1.ANNOTATION_NAME);
		}
		return null;
	}

	public Cacheable2_0 getCacheable() {
		return this.getCacheableReference().getCacheable();
	}

	public boolean calculateDefaultCacheable() {
		return this.getCacheableReference().calculateDefaultCacheable();
	}

	protected OrmCacheableReference2_0 getCacheableReference() {
		return (OrmCacheableReference2_0) this.caching;
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
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.createCustomizerRenameTypeEdits(originalType, newName),
				this.createConverterContainerRenameTypeEdits(originalType, newName),
				this.createClassExtractorRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenameTypeEdits(IType originalType, String newName) {
		return this.customizer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createConverterContainerRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createClassExtractorRenameTypeEdits(IType originalType, String newName) {
		return this.classExtractorIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.getXmlClassExtractor().createRenameEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createCustomizerMoveTypeEdits(originalType, newPackage),
				this.createConverterContainerMoveTypeEdits(originalType, newPackage),
				this.createClassExtractorMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.customizer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createConverterContainerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createClassExtractorMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.classExtractorIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.getXmlClassExtractor().createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createCustomizerRenamePackageEdits(originalPackage, newName),
				this.createConverterContainerRenamePackageEdits(originalPackage, newName),
				this.createClassExtractorRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.customizer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createConverterContainerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterContainer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createClassExtractorRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.classExtractorIsIn(originalPackage) ?
				IterableTools.singletonIterable(this.getXmlClassExtractor().createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
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
	protected JpaValidator buildPrimaryKeyValidator() {
		return new EclipseLinkEntityPrimaryKeyValidator(this);
	}

	@Override
	protected JpaValidator buildTypeMappingValidator() {
		return new EclipseLinkTypeMappingValidator(this);
	}

	protected void validateParentClass(List<IMessage> messages, IReporter reporter) {
		if (this.specifiedParentClass == null) {
			return;
		}
		if (this.getResolvedParentClass() == null) {
			IType jdtType = JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedParentClass());
			if (jdtType == null) {
				messages.add(
					this.buildValidationMessage(
						this.getParentClassTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TYPE_PARENT_CLASS_DOES_NOT_EXIST,
						this.getFullyQualifiedParentClass()
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
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.multitenancy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.customizer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.converterContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.xmlTypeMapping.parentClassTouches(pos)) {
			return this.getCandidateParentClassNames();
		}
		if (this.classExtractorTouches(pos)) {
			return this.getCandidateClassNames();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateParentClassNames() {
		return IterableTools.concatenate(
				super.getCandidateClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
	
	protected boolean classExtractorTouches(int pos) {
		return this.getXmlClassExtractor() == null? false : this.getXmlClassExtractor().classNameTouches(pos);
	}
}

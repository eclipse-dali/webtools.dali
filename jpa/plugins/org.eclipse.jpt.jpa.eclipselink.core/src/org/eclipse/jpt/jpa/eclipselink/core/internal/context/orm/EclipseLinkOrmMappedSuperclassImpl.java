/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkMappedSuperclassValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * <code>orm.xml</code> mapped superclass
 */
public class EclipseLinkOrmMappedSuperclassImpl
	extends AbstractOrmMappedSuperclass<XmlMappedSuperclass>
	implements EclipseLinkOrmMappedSuperclass, OrmCacheableReference2_0, EclipseLinkOrmConverterContainer.Parent
{
	protected final EclipseLinkOrmReadOnly readOnly;

	protected final EclipseLinkOrmCustomizer customizer;

	protected final EclipseLinkOrmChangeTracking changeTracking;

	protected final EclipseLinkCaching caching;

	protected final EclipseLinkOrmConverterContainer converterContainer;

	protected final EclipseLinkOrmMultitenancy2_3 multitenancy;

	protected final OrmQueryContainer queryContainer;

	protected final OrmGeneratorContainer generatorContainer;//supported in EL 2.1 and higher

	public EclipseLinkOrmMappedSuperclassImpl(OrmPersistentType parent, XmlMappedSuperclass xmlMappedSuperclass) {
		super(parent, xmlMappedSuperclass);
		this.caching = this.buildCaching();
		this.readOnly = this.buildReadOnly();
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
		this.multitenancy = this.buildMultitenancy();
		this.queryContainer = this.buildQueryContainer();
		this.generatorContainer = this.buildGeneratorContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.caching.synchronizeWithResourceModel(monitor);
		this.readOnly.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
		this.changeTracking.synchronizeWithResourceModel(monitor);
		this.customizer.synchronizeWithResourceModel(monitor);
		this.multitenancy.synchronizeWithResourceModel(monitor);
		this.queryContainer.synchronizeWithResourceModel(monitor);
		this.generatorContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.caching.update(monitor);
		this.readOnly.update(monitor);
		this.converterContainer.update(monitor);
		this.changeTracking.update(monitor);
		this.customizer.update(monitor);
		this.multitenancy.update(monitor);
		this.queryContainer.update(monitor);
		this.generatorContainer.update(monitor);
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
			new EclipseLinkNullOrmMultitenancy2_3(this);
	}

	protected boolean isEclipseLink2_3Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLinkJpaPlatformFactory2_3.VERSION);
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}


	// ********** generator container **********

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return this.getContextModelFactory().buildOrmGeneratorContainer(this, this.xmlTypeMapping);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Generator> getGenerators() {
		return IterableTools.concatenate(
					super.getGenerators(),
					this.generatorContainer.getGenerators()
				);
	}

	// ********** query container **********

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected OrmQueryContainer buildQueryContainer() {
		return this.getContextModelFactory().buildOrmQueryContainer(this, this.xmlTypeMapping);
	}

	@Override
	public Iterable<Query> getQueries() {
		return this.queryContainer.getQueries();
	}

	public boolean isMultitenantMetadataAllowed() {
		return true;
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
	public EclipseLinkJavaMappedSuperclass getJavaTypeMapping() {
		return (EclipseLinkJavaMappedSuperclass) super.getJavaTypeMapping();
	}

	@Override
	public EclipseLinkJavaMappedSuperclass getJavaTypeMappingForDefaults() {
		return (EclipseLinkJavaMappedSuperclass) super.getJavaTypeMappingForDefaults();
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
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = this.getJavaTypeMappingForDefaults();
		return (javaMappedSuperclass != null) && javaMappedSuperclass.usesPrimaryKeyColumns();
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return getMultitenancy().usesPrimaryKeyTenantDiscriminatorColumns();
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


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.createCustomizerRenameTypeEdits(originalType, newName),
				this.createConverterContainerRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenameTypeEdits(IType originalType, String newName) {
		return this.customizer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createConverterContainerRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}


	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createCustomizerMoveTypeEdits(originalType, newPackage),
				this.createConverterContainerMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.customizer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createConverterContainerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createCustomizerRenamePackageEdits(originalPackage, newName),
				this.createConverterContainerRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createCustomizerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.customizer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createConverterContainerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterContainer.createRenamePackageEdits(originalPackage, newName);
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
		this.generatorContainer.validate(messages, reporter);
	}
	
	@Override
	protected JpaValidator buildPrimaryKeyValidator() {
		return new EclipseLinkMappedSuperclassPrimaryKeyValidator(this);
	}

	@Override
	protected JpaValidator buildTypeMappingValidator() {
		return new EclipseLinkMappedSuperclassValidator(this);
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
		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.xmlTypeMapping.parentClassTouches(pos)) {
			return this.getCandidateParentClassNames();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateParentClassNames() {
		return IterableTools.concatenate(
				this.getCandidateClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * <code>orm.xml</code> embeddable type mapping
 */
public class EclipseLinkOrmEmbeddableImpl
	extends AbstractOrmEmbeddable<XmlEmbeddable>
	implements EclipseLinkOrmEmbeddable, EclipseLinkOrmConverterContainer.Parent
{
	protected final EclipseLinkOrmConverterContainer converterContainer;

	protected final EclipseLinkOrmChangeTracking changeTracking;

	protected final EclipseLinkOrmCustomizer customizer;


	public EclipseLinkOrmEmbeddableImpl(OrmPersistentType parent, XmlEmbeddable xmlEmbeddable) {
		super(parent, xmlEmbeddable);
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
		this.changeTracking.synchronizeWithResourceModel(monitor);
		this.customizer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.converterContainer.update(monitor);
		this.changeTracking.update(monitor);
		this.customizer.update(monitor);
	}


	// ********** converter container **********

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkOrmMappingConverterContainer(this, this.getXmlTypeMapping());
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
	public EclipseLinkJavaEmbeddable getJavaTypeMapping() {
		return (EclipseLinkJavaEmbeddable) super.getJavaTypeMapping();
	}

	@Override
	public EclipseLinkJavaEmbeddable getJavaTypeMappingForDefaults() {
		return (EclipseLinkJavaEmbeddable) super.getJavaTypeMappingForDefaults();
	}

	@Override
	public EclipseLinkOrmPersistentType getPersistentType() {
		return (EclipseLinkOrmPersistentType) super.getPersistentType();
	}

	public boolean usesPrimaryKeyColumns() {
		return false;
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return false;
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	@Override
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
		this.customizer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.converterContainer.validate(messages, reporter);
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
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateParentClassNames() {
		return IterableTools.concatenate(
				this.getCandidateClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
	
	// *********** attribtue mappings ************
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return ArrayTools.contains(EclipseLinkJavaEmbeddableImpl.ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}
}

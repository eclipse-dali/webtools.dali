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
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValueMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBasicMapping;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkBasicMapping
	extends AbstractOrmBasicMapping<XmlBasic>
	implements EclipseLinkBasicMapping, EclipseLinkOrmConvertibleMapping, OrmGeneratedValueMapping
{
	protected final OrmEclipseLinkMutable mutable;
	
	protected final EclipseLinkOrmConverterContainer converterContainer;

	protected final OrmGeneratorContainer generatorContainer;//supported in EL 1.1 and higher

	protected OrmGeneratedValue generatedValue;//supported in EL 1.1 and higher

	public OrmEclipseLinkBasicMapping(OrmSpecifiedPersistentAttribute parent, XmlBasic xmlMapping) {
		super(parent, xmlMapping);
		this.mutable = new OrmEclipseLinkMutable(this);
		this.converterContainer = this.buildConverterContainer();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mutable.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.generatorContainer.synchronizeWithResourceModel();
		this.syncGeneratedValue();
	}

	@Override
	public void update() {
		super.update();
		this.mutable.update();
		this.converterContainer.update();
		this.generatorContainer.update();
		if (this.generatedValue != null) {
			this.generatedValue.update();
		}
	}


	// ********** attribute type **********

	@Override
	protected String buildSpecifiedAttributeType() {
		return this.xmlAttributeMapping.getAttributeType();
	}

	@Override
	protected void setSpecifiedAttributeTypeInXml(String attributeType) {
		this.xmlAttributeMapping.setAttributeType(attributeType);
	}


	// ********** mutable **********

	public EclipseLinkMutable getMutable() {
		return this.mutable;
	}


	// ********** converters **********

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkOrmMappingConverterContainer(this, this.xmlAttributeMapping);
	}

	public int getMaximumAllowedConverters() {
		return 1;
	}

	// ********** generator container **********

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return this.getContextModelFactory().buildOrmGeneratorContainer(this, this.xmlAttributeMapping);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Generator> getGenerators() {
		return IterableTools.concatenate(
					super.getGenerators(),
					this.generatorContainer.getGenerators()
				);
	}


	// ********** generated value **********

	public OrmGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}

	public OrmGeneratedValue addGeneratedValue() {
		if (this.generatedValue != null) {
			throw new IllegalStateException("generated value already exists: " + this.generatedValue); //$NON-NLS-1$
		}
		XmlGeneratedValue xmlGeneratedValue = this.buildXmlGeneratedValue();
		OrmGeneratedValue value = this.buildGeneratedValue(xmlGeneratedValue);
		this.setGeneratedValue(value);
		this.xmlAttributeMapping.setGeneratedValue(xmlGeneratedValue);
		return value;
	}

	protected XmlGeneratedValue buildXmlGeneratedValue() {
		return OrmFactory.eINSTANCE.createXmlGeneratedValue();
	}

	public void removeGeneratedValue() {
		if (this.generatedValue == null) {
			throw new IllegalStateException("generated value does not exist"); //$NON-NLS-1$
		}
		this.setGeneratedValue(null);
		this.xmlAttributeMapping.setGeneratedValue(null);
	}

	protected void setGeneratedValue(OrmGeneratedValue value) {
		OrmGeneratedValue old = this.generatedValue;
		this.generatedValue = value;
		this.firePropertyChanged(GENERATED_VALUE_PROPERTY, old, value);
	}

	protected OrmGeneratedValue buildGeneratedValue() {
		XmlGeneratedValue xmlGeneratedValue = this.xmlAttributeMapping.getGeneratedValue();
		return (xmlGeneratedValue == null) ? null : this.buildGeneratedValue(xmlGeneratedValue);
	}

	protected OrmGeneratedValue buildGeneratedValue(XmlGeneratedValue xmlGeneratedValue) {
		return this.getContextModelFactory().buildOrmGeneratedValue(this, xmlGeneratedValue);
	}

	protected void syncGeneratedValue() {
		XmlGeneratedValue xmlGeneratedValue = this.xmlAttributeMapping.getGeneratedValue();
		if (xmlGeneratedValue == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		} else {
			if ((this.generatedValue != null) && (this.generatedValue.getXmlGeneratedValue() == xmlGeneratedValue)) {
				this.generatedValue.synchronizeWithResourceModel();
			} else {
				this.setGeneratedValue(this.buildGeneratedValue(xmlGeneratedValue));
			}
		}
	}


	// ********** converter adapters **********

	/**
	 * put the EclipseLink convert adapter first - this is the order EclipseLink searches
	 */
	@Override
	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return IterableTools.insert(
				OrmEclipseLinkConvert.Adapter.instance(),
				super.getConverterAdapters()
			);
	}


	//************ refactoring ************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
			super.createMoveTypeEdits(originalType, newPackage),
			this.converterContainer.createMoveTypeEdits(originalType, newPackage)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
			super.createRenamePackageEdits(originalPackage, newName),
			this.converterContainer.createRenamePackageEdits(originalPackage, newName)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
			super.createRenameTypeEdits(originalType, newName),
			this.converterContainer.createRenameTypeEdits(originalType, newName)
		);
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttributeType(messages);

		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
		// TODO mutable validation
	}

	protected void validateAttributeType(List<IMessage> messages) {
		if (this.isVirtualAccess()) {
			if (StringTools.isBlank(this.getAttributeType())) {
				messages.add(
					this.buildValidationMessage(
						this.getAttributeTypeTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED,
						this.getName()
					)
				);
				return;
			}
			if (MappingTools.typeIsBasic(this.getJavaProject(), this.getFullyQualifiedAttributeType())) {
				return;
			}
			if (this.getResolvedAttributeType() == null) {
				IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedAttributeType());
				if (jdtType == null) {
					messages.add(
						this.buildValidationMessage(
							this.getAttributeTypeTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST,
							this.getFullyQualifiedAttributeType()
						)
					);
				}
				return;
			}
		}
	}

	protected boolean isVirtualAccess() {
		return getPersistentAttribute().getAccess() == EclipseLinkAccessType.VIRTUAL;
	}

	protected TextRange getAttributeTypeTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getAttributeTypeTextRange());
	}
	
	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
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
		if (this.generatedValue != null) {
			result = this.generatedValue.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		if (this.attributeTypeTouches(pos)) {
			return this.getCandidateAttributeTypeNames();
		}
		return null;
	}
	
	protected boolean attributeTypeTouches(int pos) {
		return this.xmlAttributeMapping.attributeTypeTouches(pos);
	}
	
	protected Iterable<String> getCandidateAttributeTypeNames() {
		return MappingTools.getAllBasicTypeNames();
	}
}

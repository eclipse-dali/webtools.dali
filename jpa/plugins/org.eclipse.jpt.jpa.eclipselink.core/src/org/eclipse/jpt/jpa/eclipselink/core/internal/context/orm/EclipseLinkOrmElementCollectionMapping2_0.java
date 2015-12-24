/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.AbstractOrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmElementCollectionMapping2_0
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
	implements EclipseLinkElementCollectionMapping2_0, EclipseLinkOrmConvertibleMapping
{
	protected final EclipseLinkOrmJoinFetch joinFetch;
	
	protected final EclipseLinkOrmConverterContainer converterContainer;


	public EclipseLinkOrmElementCollectionMapping2_0(OrmSpecifiedPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.joinFetch = new EclipseLinkOrmJoinFetch(this);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.joinFetch.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.joinFetch.update(monitor);
		this.converterContainer.update(monitor);
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


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		if (getJpaPlatformVersion().getVersion().equals(EclipseLinkJpaPlatformFactory2_0.VERSION)) {
			throw new UnsupportedOperationException("join-fetch not supported in EclipseLink 2.0 platform"); //$NON-NLS-1$
		}
		return this.joinFetch;
	}


	// ********** converters **********

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkOrmMappingConverterContainer(this, this.xmlAttributeMapping);
	}

	public int getMaximumAllowedConverters() {
		return 2;
	}

	// ********** converter adapters **********

	/**
	 * put the EclipseLink convert adapter first - this is the order EclipseLink searches
	 */
	@Override
	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return IterableTools.insert(
				EclipseLinkOrmConvert.Adapter.instance(),
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
		// TODO join fetch validation
	}

	@Override
	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		if (this.isVirtualAccess()) {
			if (StringTools.isBlank(this.getAttributeType())) {
				messages.add(
					ValidationMessageTools.buildValidationMessage(
						this.getResource(),
						this.getAttributeTypeTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED,
						this.getName()
					)
				);
				return;
			}
			if (this.getResolvedAttributeType() == null) {
				IType jdtType = JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedAttributeType());
				if (jdtType == null) {
					messages.add(
						ValidationMessageTools.buildValidationMessage(
							this.getResource(),
							this.getAttributeTypeTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST,
							this.getFullyQualifiedAttributeType()
						)
					);
					return;
				}
			}
		}
		super.validateAttributeType(messages, reporter);
	}


	protected boolean isVirtualAccess() {
		return getPersistentAttribute().getAccess() == EclipseLinkAccessType.VIRTUAL;
	}

	@Override
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
		if (this.attributeTypeTouches(pos)) {
			return this.getCandidateAttributeTypeNames();
		}
		return null;
	}

	protected boolean attributeTypeTouches(int pos) {
		return this.xmlAttributeMapping.attributeTypeTouches(pos);
	}
	
	protected Iterable<String> getCandidateAttributeTypeNames() {
		return MappingTools.getCollectionTypeNames();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateClassNames() {
		return IterableTools.concatenate(
				super.getCandidateClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
}

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
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkOneToManyMapping
	extends AbstractOrmOneToManyMapping<XmlOneToMany>
	implements
		EclipseLinkOneToManyMapping2_0,
		EclipseLinkOrmConvertibleMapping,
		OrmEclipseLinkConverterContainer.Owner
{
	protected final OrmEclipseLinkPrivateOwned privateOwned;

	protected final OrmEclipseLinkJoinFetch joinFetch;
	
	protected final OrmEclipseLinkConverterContainer converterContainer;


	public OrmEclipseLinkOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany xmlMapping) {
		super(parent, xmlMapping);
		this.privateOwned = new OrmEclipseLinkPrivateOwned(this);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.privateOwned.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
		this.converterContainer.update();
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


	// ********** private owned **********

	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}


	// ********** converters **********

	public OrmEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected OrmEclipseLinkConverterContainer buildConverterContainer() {
		return new OrmEclipseLinkConverterContainerImpl(this, this, this.xmlAttributeMapping);
	}

	public int getNumberSupportedConverters() {
		return 1;
	}


	// ********** relationship **********

	@Override
	protected OrmOneToManyRelationship2_0 buildRelationship() {
		return new EclipseLinkOrmOneToManyRelationship(this);
	}

	@Override
	public EclipseLinkOrmOneToManyRelationship2_0 getRelationship() {
		return (EclipseLinkOrmOneToManyRelationship) super.getRelationship();
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
		// TODO - private owned, join fetch validation
	}


	@Override
	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		if (this.isVirtualAccess()) {
			if (StringTools.isBlank(this.getAttributeType())) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED,
						new String[] {this.getName()},
						this,
						this.getAttributeTypeTextRange()
					)
				);
				return;
			}
			if (this.getResolvedAttributeType() == null) {
				IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedAttributeType());
				if (jdtType == null) {
					messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST,
							new String[] {this.getFullyQualifiedAttributeType()},
							this,
							this.getAttributeTypeTextRange()
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
	protected Iterable<String> getCandidateTargetEntityClassNames() {
		return IterableTools.concatenate(
				super.getCandidateTargetEntityClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateMapKeyClassNames() {
		return IterableTools.concatenate(
				super.getCandidateMapKeyClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
}

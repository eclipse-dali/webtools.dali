/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.AbstractOrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmTemporalConverter
	extends AbstractOrmConverter
	implements OrmTemporalConverter
{
	protected TemporalType temporalType;


	public GenericOrmTemporalConverter(OrmAttributeMapping parent) {
		super(parent);
		this.temporalType = this.buildTemporalType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setTemporalType_(this.buildTemporalType());
	}


	// ********** temporal type **********

	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.setTemporalType_(temporalType);
		this.setXmlTemporal(temporalType);
	}

	protected void setTemporalType_(TemporalType temporalType) {
		TemporalType old = this.temporalType;
		this.temporalType = temporalType;
		this.firePropertyChanged(TEMPORAL_TYPE_PROPERTY, old, temporalType);
	}

	protected void setXmlTemporal(TemporalType temporalType) {
		this.getXmlConvertibleMapping().setTemporal(TemporalType.toOrmResourceModel(temporalType));
	}

	protected TemporalType buildTemporalType() {
		return TemporalType.fromOrmResourceModel(this.getXmlConvertibleMapping().getTemporal());
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return TemporalConverter.class;
	}

	public void initialize() {
		// start with DATE(?)
		this.temporalType = TemporalType.DATE;
		this.setXmlTemporal(this.temporalType);
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttributeTypeWithTemporal(messages, reporter);
	}
	
	protected void validateAttributeTypeWithTemporal(List<IMessage> messages, IReporter reporter) {
		if (this.getAttributeMapping().getKey() == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			@SuppressWarnings("rawtypes")
			String typeName = ((AbstractOrmElementCollectionMapping2_0) this.getAttributeMapping()).getTargetClass();
			if (!ArrayTools.contains(TEMPORAL_MAPPING_SUPPORTED_TYPES, typeName)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE,
								new String[] {},
								this,
								this.getValidationTextRange()
						)
				);
			}
		} else {
			String typeName = this.getAttributeMapping().getPersistentAttribute().getTypeName();
			if (!ArrayTools.contains(TEMPORAL_MAPPING_SUPPORTED_TYPES, typeName)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE,
								new String[] {},
								this,
								this.getValidationTextRange()
						)
				);
			}

		}
	}

	public TextRange getValidationTextRange() {
		return this.getXmlConvertibleMapping().getTemporalTextRange();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}
}

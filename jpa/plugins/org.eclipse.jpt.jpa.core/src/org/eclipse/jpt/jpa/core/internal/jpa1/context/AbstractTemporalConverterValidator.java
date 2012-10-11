/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTemporalConverterValidator
	implements JptValidator
{
	protected final BaseTemporalConverter converter;

	protected AbstractTemporalConverterValidator(BaseTemporalConverter converter) {
		super();
		this.converter = converter;
	}

	protected AttributeMapping getAttributeMapping() {
		return this.converter.getParent();
	}

	protected PersistentAttribute getPersistentAttribute() {
		return this.getAttributeMapping().getPersistentAttribute();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		return this.validateAttributeTypeWithTemporal(messages);
	}

	protected boolean validateAttributeTypeWithTemporal(List<IMessage> messages) {
		String typeName = this.getTypeName();
		if (typeName == null) {
			//validation for a null type name is handled elsewhere, no need to have a temporal validation error
			return true;
		}
		if (!ArrayTools.contains(BaseTemporalConverter.TEMPORAL_MAPPING_SUPPORTED_TYPES, typeName)) {
			messages.add(this.buildInvalidTemporalMappingTypeMessage());
			return false;
		}
		return true;
	}

	protected abstract String getTypeName();

	protected IMessage buildInvalidTemporalMappingTypeMessage() {
		return this.getPersistentAttribute().isVirtual() ?
				this.buildVirtualAttributeInvalidTemporalMappingTypeMessage() :
				this.buildInvalidTemporalMappingTypeMessage_();
	}

	protected IMessage buildInvalidTemporalMappingTypeMessage_() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getInvalidTemporalMappingType(),
			StringTools.EMPTY_STRING_ARRAY,
			this.converter,
			this.converter.getValidationTextRange()
		);
	}

	protected abstract String getInvalidTemporalMappingType();

	protected IMessage buildVirtualAttributeInvalidTemporalMappingTypeMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeInvalidTemporalMappingType(),
			new String[] {this.getPersistentAttribute().getName()},
			this.converter,
			this.getPersistentAttribute().getValidationTextRange()
		);
	}

	protected abstract String getVirtualAttributeInvalidTemporalMappingType();

}

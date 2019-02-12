/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTemporalConverterValidator
	implements JpaValidator
{
	protected final BaseTemporalConverter converter;

	protected AbstractTemporalConverterValidator(BaseTemporalConverter converter) {
		super();
		this.converter = converter;
	}

	protected AttributeMapping getAttributeMapping() {
		return this.converter.getParent();
	}

	protected SpecifiedPersistentAttribute getPersistentAttribute() {
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
		return ValidationMessageTools.buildValidationMessage(
				this.converter.getResource(),
				this.converter.getValidationTextRange(),
				this.getInvalidTemporalMappingTypeMessage()
			);
	}

	protected abstract ValidationMessage getInvalidTemporalMappingTypeMessage();

	protected IMessage buildVirtualAttributeInvalidTemporalMappingTypeMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.converter.getResource(),
				this.getPersistentAttribute().getValidationTextRange(),
				this.getVirtualAttributeInvalidTemporalMappingTypeMessage(),
				this.getPersistentAttribute().getName()
			);
	}

	protected abstract ValidationMessage getVirtualAttributeInvalidTemporalMappingTypeMessage();

}

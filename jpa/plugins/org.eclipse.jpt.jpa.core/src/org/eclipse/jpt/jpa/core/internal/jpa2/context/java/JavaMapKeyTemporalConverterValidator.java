/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class JavaMapKeyTemporalConverterValidator
	extends AbstractTemporalConverterValidator
{
	public JavaMapKeyTemporalConverterValidator(BaseTemporalConverter converter) {
		super(converter);
	}

	@Override
	protected JavaCollectionMapping2_0 getAttributeMapping() {
		return (JavaCollectionMapping2_0) super.getAttributeMapping();
	}

	@Override
	protected String getTypeName() {
		return this.getAttributeMapping().getFullyQualifiedMapKeyClass();
	}

	@Override
	protected ValidationMessage getInvalidTemporalMappingTypeMessage() {
		return JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE;
	}

	@Override
	protected ValidationMessage getVirtualAttributeInvalidTemporalMappingTypeMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE;
	}
}

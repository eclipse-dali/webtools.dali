/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class OrmTemporalConverterValidator
	extends AbstractTemporalConverterValidator
{
	public OrmTemporalConverterValidator(BaseTemporalConverter converter) {
		super(converter);
	}

	@Override
	protected String getTypeName() {
		return this.getPersistentAttribute().getTypeName();
	}

	@Override
	protected ValidationMessage getInvalidTemporalMappingTypeMessage() {
		return JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

	@Override
	protected ValidationMessage getVirtualAttributeInvalidTemporalMappingTypeMessage() {
		throw new UnsupportedOperationException();
	}
}

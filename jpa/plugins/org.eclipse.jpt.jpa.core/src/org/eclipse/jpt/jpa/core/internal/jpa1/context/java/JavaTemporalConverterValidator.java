/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class JavaTemporalConverterValidator extends AbstractTemporalConverterValidator
{

	public JavaTemporalConverterValidator(BaseTemporalConverter converter, ConverterTextRangeResolver textRangeResolver) {
		super(converter, textRangeResolver);
	}

	@Override
	protected String getTypeName() {
		return this.getPersistentAttribute().getTypeName();
	}

	@Override
	protected String getInvalidTemporalMappingType() {
		return JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

	@Override
	protected String getVirtualAttributeInvalidTemporalMappingType() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

}

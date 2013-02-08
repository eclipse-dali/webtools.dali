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
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class JavaTemporalConverterValidator extends AbstractTemporalConverterValidator
{

	public JavaTemporalConverterValidator(BaseTemporalConverter converter) {
		super(converter);
	}

	@Override
	protected String getTypeName() {
		return this.getPersistentAttribute().getTypeName();
	}

	@Override
	protected String getInvalidTemporalMappingType() {
		return JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

	@Override
	protected String getVirtualAttributeInvalidTemporalMappingType() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

}

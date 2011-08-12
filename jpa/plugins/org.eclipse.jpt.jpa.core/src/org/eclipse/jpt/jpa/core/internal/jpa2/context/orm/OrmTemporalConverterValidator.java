/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class OrmTemporalConverterValidator extends AbstractTemporalConverterValidator
{

	public OrmTemporalConverterValidator(TemporalConverter converter, ConverterTextRangeResolver textRangeResolver) {
		super(converter, textRangeResolver);
	}

	@Override
	protected String getTypeName() {
		//no need to worry about the orm.xml package element because we are
		//only looking for java.util classes, we don't need to append the package element
		return this.getPersistentAttribute().getTypeName();
	}

	@Override
	protected String getInvalidTemporalMappingType() {
		return JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE;
	}

	@Override
	protected String getVirtualAttributeInvalidTemporalMappingType() {
		throw new UnsupportedOperationException();
	}

}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionMapping2_0;

public class OrmMapKeyTemporalConverterValidator extends AbstractTemporalConverterValidator
{

	public OrmMapKeyTemporalConverterValidator(TemporalConverter converter, ConverterTextRangeResolver textRangeResolver) {
		super(converter, textRangeResolver);
	}

	@Override
	protected OrmCollectionMapping2_0 getAttributeMapping() {
		return (OrmCollectionMapping2_0) super.getAttributeMapping();
	}

	@Override
	protected String getTypeName() {
		return this.getAttributeMapping().getFullyQualifiedMapKeyClass();
	}

	@Override
	protected String getInvalidTemporalMappingType() {
		return JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE;
	}

	@Override
	protected String getVirtualAttributeInvalidTemporalMappingType() {
		throw new UnsupportedOperationException();
	}

}

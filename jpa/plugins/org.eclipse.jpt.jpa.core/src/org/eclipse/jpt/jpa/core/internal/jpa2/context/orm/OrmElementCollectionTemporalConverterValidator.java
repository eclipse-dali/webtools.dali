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
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class OrmElementCollectionTemporalConverterValidator
	extends AbstractTemporalConverterValidator
{
	public OrmElementCollectionTemporalConverterValidator(BaseTemporalConverter converter) {
		super(converter);
	}

	@Override
	protected OrmElementCollectionMapping2_0 getAttributeMapping() {
		return (OrmElementCollectionMapping2_0) super.getAttributeMapping();
	}

	@Override
	protected String getTypeName() {
		return this.getAttributeMapping().getFullyQualifiedTargetClass();
	}

	@Override
	protected ValidationMessage getInvalidTemporalMappingTypeMessage() {
		return JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE;
	}

	@Override
	protected ValidationMessage getVirtualAttributeInvalidTemporalMappingTypeMessage() {
		throw new UnsupportedOperationException();
	}
}

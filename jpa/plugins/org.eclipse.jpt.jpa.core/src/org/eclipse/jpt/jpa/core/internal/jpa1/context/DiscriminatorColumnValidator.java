/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class DiscriminatorColumnValidator
	extends AbstractNamedColumnValidator<ReadOnlyNamedColumn, NamedColumnTextRangeResolver>
{
	public DiscriminatorColumnValidator(
			ReadOnlyNamedColumn namedColumn,
			NamedColumnTextRangeResolver textRangeResolver) {
		super(namedColumn, textRangeResolver);
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}
}

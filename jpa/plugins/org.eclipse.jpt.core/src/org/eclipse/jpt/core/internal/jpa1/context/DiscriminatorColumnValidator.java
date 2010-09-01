/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;

public class DiscriminatorColumnValidator extends AbstractNamedColumnValidator
{
	public DiscriminatorColumnValidator(
			NamedColumn namedColumn,
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

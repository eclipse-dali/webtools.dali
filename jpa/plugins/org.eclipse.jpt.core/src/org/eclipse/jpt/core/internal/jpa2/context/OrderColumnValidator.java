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
package org.eclipse.jpt.core.internal.jpa2.context;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.AbstractNamedColumnValidator;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;

public class OrderColumnValidator extends AbstractNamedColumnValidator
{
	public OrderColumnValidator(
				OrderColumn2_0 column,
				NamedColumnTextRangeResolver textRangeResolver) {
		super(column, textRangeResolver);
	}

	public OrderColumnValidator(
				PersistentAttribute persistentAttribute,
				OrderColumn2_0 column,
				NamedColumnTextRangeResolver textRangeResolver) {
		super(persistentAttribute, column, textRangeResolver);
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME;
	}
}

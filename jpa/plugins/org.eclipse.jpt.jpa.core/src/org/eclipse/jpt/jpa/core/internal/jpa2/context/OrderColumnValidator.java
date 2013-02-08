/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractNamedColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class OrderColumnValidator
	extends AbstractNamedColumnValidator<OrderColumn2_0>
{
	public OrderColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				OrderColumn2_0 column) {
		super(persistentAttribute, column);
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME;
	}
}

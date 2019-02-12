/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class NamedColumnValidator
	extends AbstractNamedColumnValidator<BaseColumn>
{

	protected NamedColumnValidator(
				BaseColumn column,
				TableDescriptionProvider provider) {
		super(column, provider);
	}

	public NamedColumnValidator(
				PersistentAttribute persistentAttribute,
				BaseColumn column,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, provider);
	}

	@Override
	protected JpaValidator buildTableValidator() {
		return new BaseColumnTableValidator();
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME;
	}
}

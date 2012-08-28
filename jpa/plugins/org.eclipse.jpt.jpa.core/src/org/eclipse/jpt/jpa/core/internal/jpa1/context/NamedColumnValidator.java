/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class NamedColumnValidator
	extends AbstractNamedColumnValidator<ReadOnlyBaseColumn>
{

	protected NamedColumnValidator(
				ReadOnlyBaseColumn column,
				TableDescriptionProvider provider) {
		super(column, provider);
	}

	public NamedColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyBaseColumn column,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, provider);
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new BaseColumnTableValidator();
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME;
	}
}

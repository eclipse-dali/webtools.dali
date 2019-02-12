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
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class AttributeOverrideValidator
	extends OverrideValidator
{
	public AttributeOverrideValidator(
				AttributeOverride override,
				AttributeOverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(override, container, overrideDescriptionProvider);
	}

	public AttributeOverrideValidator(
				PersistentAttribute persistentAttribute,
				AttributeOverride override,
				AttributeOverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(persistentAttribute, override, container, overrideDescriptionProvider);
	}

	@Override
	protected ValidationMessage getVirtualOverrideUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_NAME;
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_INVALID_NAME;
	}
}

/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class AssociationOverrideValidator
	extends OverrideValidator
{
	public AssociationOverrideValidator(
				AssociationOverride override,
				AssociationOverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(override, container, textRangeResolver, overrideDescriptionProvider);
	}

	public AssociationOverrideValidator(
				PersistentAttribute persistentAttribute,
				AssociationOverride override,
				AssociationOverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(persistentAttribute, override, container, textRangeResolver, overrideDescriptionProvider);
	}

	@Override
	protected String getVirtualOverrideUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVALID_NAME;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.ASSOCIATION_OVERRIDE_INVALID_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVALID_NAME;
	}
}

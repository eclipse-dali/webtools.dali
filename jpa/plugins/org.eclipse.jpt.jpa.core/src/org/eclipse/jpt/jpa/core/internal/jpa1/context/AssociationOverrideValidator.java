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

import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class AssociationOverrideValidator
	extends OverrideValidator
{
	public AssociationOverrideValidator(
				ReadOnlyAssociationOverride override,
				AssociationOverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(override, container, overrideDescriptionProvider);
	}

	public AssociationOverrideValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyAssociationOverride override,
				AssociationOverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(persistentAttribute, override, container, overrideDescriptionProvider);
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

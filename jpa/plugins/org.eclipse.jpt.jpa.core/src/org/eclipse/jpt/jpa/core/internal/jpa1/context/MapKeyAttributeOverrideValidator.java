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

import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class MapKeyAttributeOverrideValidator
	extends AttributeOverrideValidator
{
	public MapKeyAttributeOverrideValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyAttributeOverride override,
				AttributeOverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(persistentAttribute, override, container, overrideDescriptionProvider);
	}

	@Override
	protected String getVirtualOverrideUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME;
	}
}

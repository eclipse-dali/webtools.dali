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

import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;

public class MapKeyAttributeOverrideValidator
	extends AttributeOverrideValidator
{

	public MapKeyAttributeOverrideValidator(
				AttributeOverride override,
				AttributeOverride.Owner owner,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(override, owner, textRangeResolver, overrideDescriptionProvider);
	}

	public MapKeyAttributeOverrideValidator(
				PersistentAttribute persistentAttribute,
				AttributeOverride override,
				AttributeOverride.Owner owner,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		super(persistentAttribute, override, owner, textRangeResolver, overrideDescriptionProvider);
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

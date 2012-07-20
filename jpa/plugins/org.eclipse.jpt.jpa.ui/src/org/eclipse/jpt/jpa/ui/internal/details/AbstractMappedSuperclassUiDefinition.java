/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;

public abstract class AbstractMappedSuperclassUiDefinition<M, T extends MappedSuperclass>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractMappedSuperclassUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages.MappedSuperclassUiProvider_label;
	}

	public String getLinkLabel() {
		return JptUiDetailsMessages.MappedSuperclassUiProvider_linkLabel;
	}

	@Override
	protected String getImageKey() {
		return JptUiIcons.MAPPED_SUPERCLASS;
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkArrayMapping2_3;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.JptUiDetailsMessages2_0;

public abstract class AbstractEclipseLinkArrayMapping2_3UiDefinition<M, T extends EclipseLinkArrayMapping2_3>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractEclipseLinkArrayMapping2_3UiDefinition() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.ARRAY_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages2_0.ArrayMapping2_3_label;
	}

	public String getLinkLabel() {
		return JptUiDetailsMessages2_0.ArrayMapping2_3_linkLabel;
	}
}

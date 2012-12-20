/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

public abstract class AbstractMappingUiDefinition
	implements MappingUiDefinition
{
	protected AbstractMappingUiDefinition() {
		super();
	}

	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.JPA_CONTENT;
	}

	public boolean isEnabledFor(JpaContextNode node) {
		return true;
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.swt.widgets.Composite;

public class EntityMappingsDetailsPageManager
	extends AbstractEntityMappingsDetailsPageManager
{
	public EntityMappingsDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}
}

/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractEntityMappingsDetailsPageManager;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.EntityMappingsGeneratorsComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.QueriesComposite2_1;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class EntityMappingsDetailsPageManager2_1
	extends AbstractEntityMappingsDetailsPageManager
{

	protected EntityMappingsDetailsPageManager2_1(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}
	
	@Override
	protected Control initializeGeneratorsSection(Composite container) {
		return new EntityMappingsGeneratorsComposite2_0(this, container).getControl();
	}
	
	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_1(this, this.buildQueryContainerHolder(), container).getControl();
	}

}

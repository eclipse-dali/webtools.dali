/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkAbstractManyToManyMappingComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJavaManyToManyMappingComposite
	extends EclipseLinkAbstractManyToManyMappingComposite<EclipseLinkManyToManyMapping, Cascade>
{
	public EclipseLinkJavaManyToManyMappingComposite(
			PropertyValueModel<? extends EclipseLinkManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class EclipseLinkOrmMappedSuperclassComposite2_0
	extends EclipseLinkAbstractOrmMappedSuperclassComposite<EclipseLinkOrmMappedSuperclass>
{
	public EclipseLinkOrmMappedSuperclassComposite2_0(
			PropertyValueModel<? extends EclipseLinkOrmMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeCachingSection(Composite container) {
		return new EclipseLinkOrmCachingComposite2_0(this, this.buildCachingModel(), container).getControl();
	}
}

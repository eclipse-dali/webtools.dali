/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EntityOverridesComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.GenerationComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.QueriesComposite2_0;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class EclipseLinkAbstractOrmEntityComposite2_x<T extends EclipseLinkOrmEntity>
	extends EclipseLinkAbstractOrmEntityComposite<T>
{
	protected EclipseLinkAbstractOrmEntityComposite2_x(
			PropertyValueModel<? extends T> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeAttributeOverridesSection(Composite container) {
		return new EntityOverridesComposite2_0(this, container).getControl();
	}

	@Override
	protected Control initializeGeneratorsSection(Composite container) {
		return new GenerationComposite2_0(this, this.buildGeneratorContainerModel(), container).getControl();
	}

	@Override
	protected Control initializeCachingSection(Composite container) {
		return new EclipseLinkOrmCachingComposite2_0(this, this.buildCachingModel(), container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_0(this, this.buildQueryContainerModel(), container).getControl();
	}
}

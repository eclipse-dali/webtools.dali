/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkIdMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkIdMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMutableTriStateCheckBox;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.IdMappingMappedByRelationshipPane2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.IdMappingGenerationComposite2_0;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class EclipseLinkJavaIdMappingComposite2_0
	extends EclipseLinkIdMappingComposite<EclipseLinkIdMapping2_0>
{
	public EclipseLinkJavaIdMappingComposite2_0(
			PropertyValueModel<? extends EclipseLinkIdMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	

	@Override
	protected void initializeLayout(Composite container) {
		initializeIdCollapsibleSection(container);
		initializeTypeCollapsibleSection(container);
		initializeConvertersCollapsibleSection(container);
		initializeGenerationCollapsibleSection(container);
	}
	
	@Override
	protected Control initializeIdSection(Composite container) {
		container = this.addSubPane(container);

		new IdMappingMappedByRelationshipPane2_0(this, getSubjectHolder(), container);
		new ColumnComposite(this, buildColumnModel(), container);

		new EclipseLinkMutableTriStateCheckBox(this, buildMutableModel(), container);

		return container;
	}	
	
	@Override
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGenerationComposite2_0(this, container);
	}
}

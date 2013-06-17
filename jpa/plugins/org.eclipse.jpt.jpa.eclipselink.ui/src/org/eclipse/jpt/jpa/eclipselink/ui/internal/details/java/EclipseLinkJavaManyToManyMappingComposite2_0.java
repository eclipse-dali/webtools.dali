/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkAbstractManyToManyMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComboViewer;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.TargetEntityClassChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CascadePane2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.OrderingComposite2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class EclipseLinkJavaManyToManyMappingComposite2_0
	extends EclipseLinkAbstractManyToManyMappingComposite<EclipseLinkManyToManyMapping2_0, Cascade2_0>
{
	public EclipseLinkJavaManyToManyMappingComposite2_0(
			PropertyValueModel<? extends EclipseLinkManyToManyMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToManyCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
		initializeConvertersCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}

	@Override
	protected Control initializeManyToManySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target entity widgets
		Hyperlink targetEntityHyperlink = this.addHyperlink(container, JptJpaUiDetailsMessages.TARGET_ENTITY_CHOOSER_LABEL);
		new TargetEntityClassChooser(this, container, targetEntityHyperlink);

		// Fetch type widgets
		this.addLabel(container, JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_FETCH_LABEL);
		new FetchTypeComboViewer(this, container);

		// Join fetch widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_JOIN_FETCHCOMPOSITE_LABEL);
		new EclipseLinkJoinFetchComboViewer(this, this.buildJoinFetchModel(), container);

		// Cascade widgets
		CascadePane2_0 cascadePane = new CascadePane2_0(this, buildCascadeModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		cascadePane.getControl().setLayoutData(gridData);

		return container;
	}

	@Override
	protected Control initializeOrderingSection(Composite container) {
		return new OrderingComposite2_0(this, container).getControl();
	}
}

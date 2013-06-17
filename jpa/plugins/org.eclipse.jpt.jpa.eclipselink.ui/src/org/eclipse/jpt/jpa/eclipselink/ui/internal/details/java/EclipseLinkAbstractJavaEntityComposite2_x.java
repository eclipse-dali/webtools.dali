/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.EntityNameCombo;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.TableComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EntityOverridesComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.GenerationComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.QueriesComposite2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public abstract class EclipseLinkAbstractJavaEntityComposite2_x<T extends EclipseLinkJavaEntity>
	extends EclipseLinkAbstractJavaEntityComposite<T>
{
	protected EclipseLinkAbstractJavaEntityComposite2_x(
			PropertyValueModel<? extends T> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeEntitySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Table widgets
		TableComposite tableComposite = new TableComposite(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableComposite.getControl().setLayoutData(gridData);

		// Entity name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ENTITY_NAME_COMPOSITE_NAME);
		new EntityNameCombo(this, container);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);	

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
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
		return new EclipseLinkJavaCachingComposite2_0(this, this.buildCachingModel(), container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_0(this, this.buildQueryContainerModel(), container).getControl();
	}
}

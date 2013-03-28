/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.EntityMappingsGeneratorsComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.QueriesComposite2_1;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkEntityMappingsDetailsPageManager2_5
	extends AbstractEclipseLinkEntityMappingsDetailsPageManager
{
	public EclipseLinkEntityMappingsDetailsPageManager2_5(
			Composite parent, 
			WidgetFactory widgetFactory, 
			ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityMappingsCollapsibleSection(container);
		this.initializePersistenceUnitMetadataCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeMultitenancyCollapsibleSection(container);
	}

	@Override
	protected Control initializeGeneratorsSection(Composite container) {
		return new EntityMappingsGeneratorsComposite2_0(this, container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_1(this, this.buildQueryContainerHolder(), container).getControl();
	}

	@Override
	protected Control initializePersistenceUnitMetadataSection(Composite container) {
		return new EclipseLink2_3PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadataHolder(),
			container
		).getControl();
	}

	protected void initializeMultitenancyCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_MULTITENANCY);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeMultitenancySection(section));
				}
			}
		});
	}

	protected Control initializeMultitenancySection(Composite container) {
		return new EclipseLinkEntityMappingsTenantDiscriminatorColumnsComposite(this, container).getControl();
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.EntityMappingsGenerators2_0Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | XmlPackageChooser                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |              ------------------------------------------------------------ |
 * | Schema:      | SchemaCombo                                              | |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Catalog:     | CatalogCombo                                             | |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Access Type: |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PersistenceUnitMetadataComposite                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmGeneratorsComposite                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmQueriesComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ConvertersComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TenantDiscriminatorColumnsComposite                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 *
 * @version 3.1
 * @since 3.1
 */
public class EclipseLinkEntityMappings2_3DetailsPage extends AbstractEclipseLinkEntityMappingsDetailsPage
{
	/**
	 * Creates a new <code>EclipseLinkEntityMappingsDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkEntityMappings2_3DetailsPage(Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
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
		return new EntityMappingsGenerators2_0Composite(this, container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new Queries2_0Composite(this, this.buildQueryContainerHolder(), container).getControl();
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
		section.setText(EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_multitenancy);

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
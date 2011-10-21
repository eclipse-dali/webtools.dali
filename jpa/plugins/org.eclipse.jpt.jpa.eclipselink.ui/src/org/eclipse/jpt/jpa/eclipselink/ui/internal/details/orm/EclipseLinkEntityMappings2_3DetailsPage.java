/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.EntityMappingsGenerators2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmQueries2_0Composite;
import org.eclipse.swt.widgets.Composite;

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
	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		new EntityMappingsGenerators2_0Composite(this, container);
	}

	@Override
	protected void initializeQueriesCollapsibleSection(Composite container) {
		new OrmQueries2_0Composite(this, container);
	}

	@Override
	protected void initializePersistenceUnitMetadataCollapsibleSection(Composite container) {
		new EclipseLink2_3PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadataHolder(),
			addSubPane(container, 5)
		);
	}

	protected void initializeMultitenancyCollapsibleSection(Composite container) {
		new EclipseLinkEntityMappingsTenantDiscriminatorColumnsComposite(this, container).getControl();
	}

}
/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.details.orm.AbstractEntityMappingsDetailsPage;
import org.eclipse.jpt.ui.internal.details.orm.EntityMappingsGeneratorsComposite;
import org.eclipse.jpt.ui.internal.details.orm.JptUiDetailsOrmMessages;
import org.eclipse.jpt.ui.internal.details.orm.OrmPackageChooser;
import org.eclipse.jpt.ui.internal.details.orm.OrmQueriesComposite;
import org.eclipse.jpt.ui.internal.details.orm.PersistenceUnitMetadataComposite;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EntityMappings
 * @see AbstractEclipseLinkEntityMappingsDetailsPage - The parent container
 * @see CatalogCombo
 * @see EnumFormComboViewer
 * @see EntityMappingsGeneratorsComposite
 * @see OrmPackageChooser
 * @see OrmQueriesComposite
 * @see PersistenceUnitMetadataComposite
 * @see SchemaCombo
 *
 * @version 2.2
 * @since 2.1
 */
public abstract class AbstractEclipseLinkEntityMappingsDetailsPage extends AbstractEntityMappingsDetailsPage
{
	/**
	 * Creates a new <code>EclipseLinkEntityMappingsDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractEclipseLinkEntityMappingsDetailsPage(Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Package widgets
		new OrmPackageChooser(this, container);

		// Schema widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_schema,
			this.addSchemaCombo(container),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_catalog,
			this.addCatalogCombo(container),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_access,
			this.addAccessTypeCombo(container),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);

		// Persistence Unit Metadata widgets
		new PersistenceUnitMetadataComposite(
			this,
			this.buildPersistentUnitMetadataHolder(),
			this.addSubPane(container, 5)
		);

		// Generators pane
		this.buildEntityMappingsGeneratorsComposite(container);

		// Queries pane
		this.buildOrmQueriesComposite(container);
		
		// Converters section
		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_Label
		);
		
		new OrmEclipseLinkConvertersComposite(
			this,
			this.buildConverterHolder(),
			container
		);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<EntityMappings, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return ((EclipseLinkEntityMappings) this.subject).getConverterHolder();
			}
		};
	}

}
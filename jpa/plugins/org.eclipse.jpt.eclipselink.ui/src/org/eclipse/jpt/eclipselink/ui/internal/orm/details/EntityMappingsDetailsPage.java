/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.orm.details.OrmGeneratorsComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmPackageChooser;
import org.eclipse.jpt.ui.internal.orm.details.OrmQueriesComposite;
import org.eclipse.jpt.ui.internal.orm.details.PersistenceUnitMetadataComposite;
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
 * @see EntityMappingsDetailsPage - The parent container
 * @see CatalogCombo
 * @see EnumFormComboViewer
 * @see OrmGeneratorsComposite
 * @see OrmPackageChooser
 * @see OrmQueriesComposite
 * @see PersistenceUnitMetadataComposite
 * @see SchemaCombo
 *
 * @version 2.1
 * @since 2.1
 */
public class EntityMappingsDetailsPage extends org.eclipse.jpt.ui.internal.orm.details.EntityMappingsDetailsPage
{
	/**
	 * Creates a new <code>XmlEntityMappingsDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EntityMappingsDetailsPage(Composite parent,
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
			JptUiOrmMessages.EntityMappingsDetailsPage_schema,
			addSchemaCombo(container),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.EntityMappingsDetailsPage_catalog,
			addCatalogCombo(container),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.EntityMappingsDetailsPage_access,
			addAccessTypeCombo(container),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);

		// Persistence Unit Metadata widgets
		new PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadataHolder(),
			addSubPane(container, 5)
		);

		// Generators pane
		new OrmGeneratorsComposite(
			this,
			container
		);

		// Queries pane
		new OrmQueriesComposite(
			this,
			container
		);
		
		// Converters section
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.ConvertersComposite_Label
		);
		
		new ConvertersComposite(
			this,
			buildConverterHolder(),
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
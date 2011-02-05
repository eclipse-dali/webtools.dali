/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.details.orm.AbstractEntityMappingsDetailsPage;
import org.eclipse.jpt.ui.internal.details.orm.EntityMappingsGeneratorsComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmPackageChooser;
import org.eclipse.jpt.ui.internal.details.orm.OrmQueriesComposite;
import org.eclipse.jpt.ui.internal.details.orm.PersistenceUnitMetadataComposite;
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
 * @version 2.3
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
		this.initializeEntityMappingsCollapsibleSection(container);
		this.initializePersistenceUnitMetadataCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters
		);

		this.initializeConvertersSection(container);
	}

	protected void initializeConvertersSection(Composite container) {
		new OrmEclipseLinkConvertersComposite(
			this,
			this.buildConverterHolder(),
			container
		);
	}
	
	private PropertyValueModel<OrmEclipseLinkConverterContainer> buildConverterHolder() {
		return new PropertyAspectAdapter<EntityMappings, OrmEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkConverterContainer buildValue_() {
				return ((EclipseLinkEntityMappings) this.subject).getConverterContainer();
			}
		};
	}

}
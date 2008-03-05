/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractJpaDetailsPage;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

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
 * | Schema:      |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Catalog:     |                                                        |v| |
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
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EntityMappings
 * @see EntityMappingsDetailsPage - The parent container
 * @see PersistenceUnitMetadataComposite
 * @see OrmPackageChooser
 *
 * @version 2.0
 * @since 2.0
 */
public class EntityMappingsDetailsPage extends AbstractJpaDetailsPage<EntityMappings>
{
	/**
	 * Creates a new <code>XmlEntityMappingsDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EntityMappingsDetailsPage(Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	private EnumFormComboViewer<EntityMappings, AccessType> buildAccessTypeCombo(Composite container) {

		return new EnumFormComboViewer<EntityMappings, AccessType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_ACCESS_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_ACCESS_PROPERTY);
			}

			@Override
			protected AccessType[] choices() {
				return AccessType.values();
			}

			@Override
			protected AccessType defaultValue() {
				return subject().getDefaultAccess();
			}

			@Override
			protected String displayString(AccessType value) {
				return buildDisplayString(
					JptUiOrmMessages.class,
					EntityMappingsDetailsPage.this,
					value
				);
			}

			@Override
			protected AccessType getValue() {
				return subject().getAccess();
			}

			@Override
			protected void setValue(AccessType value) {
				subject().setSpecifiedAccess(value);
			}
		};
	}

	private EnumFormComboViewer<EntityMappings, String> buildCatalogComboViewer(Composite container) {

		return new EnumFormComboViewer<EntityMappings, String>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String[] choices() {
				return new String[0];
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultCatalog();
			}

			@Override
			protected String displayString(String value) {
				return value;
			}

			@Override
			protected String getValue() {
				return subject().getSpecifiedCatalog();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedCatalog(value);
			}
		};
	}

	private PropertyValueModel<PersistenceUnitMetadata> buildPersistentUnitMetadaHolder() {
		return new TransformationPropertyValueModel<EntityMappings, PersistenceUnitMetadata>(getSubjectHolder()) {
			@Override
			protected PersistenceUnitMetadata transform_(EntityMappings value) {
				return value.getPersistenceUnitMetadata();
			}
		};
	}

	private EnumFormComboViewer<EntityMappings, String> buildSchemaComboViewer(Composite container) {

		return new EnumFormComboViewer<EntityMappings, String>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_SCHEMA_PROPERTY);
			}

			@Override
			protected String[] choices() {
				return new String[0];
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultSchema();
			}

			@Override
			protected String displayString(String value) {
				return value;
			}

			@Override
			protected String getValue() {
				return subject().getSpecifiedSchema();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedSchema(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Package widgets
		new OrmPackageChooser(
			this,
			container
		);

		// Schema widgets
		EnumFormComboViewer<EntityMappings, String> schemaComboViewer =
			buildSchemaComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmSchemaChooser_SchemaChooser,
			schemaComboViewer.getControl(),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		EnumFormComboViewer<EntityMappings, String> catalogComboViewer =
			buildCatalogComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmCatalogChooser_CatalogChooser,
			catalogComboViewer.getControl(),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		EnumFormComboViewer<EntityMappings, AccessType> accessTypeComposite =
			buildAccessTypeCombo(container);

		buildLabeledComposite(
			container,
			JptUiOrmMessages.PersistentTypePage_AccessLabel,
			accessTypeComposite.getControl(),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);

		// Persistence Unit Metadata widgets
		new PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadaHolder(),
			buildSubPane(container, 5)
		);
	}
}
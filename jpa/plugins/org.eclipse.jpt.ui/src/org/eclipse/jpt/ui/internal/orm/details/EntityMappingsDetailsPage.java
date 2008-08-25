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
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractJpaDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.mappings.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
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
	                                 WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	private EnumFormComboViewer<EntityMappings, AccessType> addAccessTypeCombo(Composite container) {

		return new EnumFormComboViewer<EntityMappings, AccessType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_ACCESS_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_ACCESS_PROPERTY);
			}

			@Override
			protected AccessType[] getChoices() {
				return AccessType.values();
			}

			@Override
			protected AccessType getDefaultValue() {
				return getSubject().getDefaultAccess();
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
				return getSubject().getAccess();
			}

			@Override
			protected void setValue(AccessType value) {
				getSubject().setSpecifiedAccess(value);
			}
		};
	}

	private CatalogCombo<EntityMappings> addCatalogCombo(Composite container) {

		return new CatalogCombo<EntityMappings>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultCatalog();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedCatalog();
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

	private SchemaCombo<EntityMappings> addSchemaCombo(Composite container) {

		return new SchemaCombo<EntityMappings>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EntityMappings.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(EntityMappings.SPECIFIED_SCHEMA_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultSchema();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedSchema();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
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
			buildPersistentUnitMetadaHolder(),
			addSubPane(container, 5)
		);

		// Generators pane
		new OrmGeneratorsComposite(
			this,
			addSubPane(container, 5)
		);

		// Queries pane
		new OrmQueriesComposite(
			this,
			addSubPane(container, 5)
		);
	}
}
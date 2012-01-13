/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.Collection;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractJpaDetailsPage;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
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
 * @see AbstractEntityMappingsDetailsPage - The parent container
 * @see CatalogCombo
 * @see EnumFormComboViewer
 * @see EntityMappingsGeneratorsComposite
 * @see OrmPackageChooser
 * @see OrmQueriesComposite
 * @see PersistenceUnitMetadataComposite
 * @see SchemaCombo
 *
 * @version 2.3
 * @since 2.0
 */
public abstract class AbstractEntityMappingsDetailsPage extends AbstractJpaDetailsPage<EntityMappings>
{
	/**
	 * Creates a new <code>EntityMappingsDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractEntityMappingsDetailsPage(Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityMappingsCollapsibleSection(container);
		this.initializePersistenceUnitMetadataCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
	}
	
	protected void initializeEntityMappingsCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsOrmMessages.EntityMappingsSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeEntityMappingsSection(container);
	}
	
	protected void initializeEntityMappingsSection(Composite container) {
		// Package widgets
		new OrmPackageChooser(this, container);

		// Schema widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_schema,
			addSchemaCombo(container),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_catalog,
			addCatalogCombo(container),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		new AccessTypeComposite(this, getSubjectHolder(), container);
	}

	protected void initializePersistenceUnitMetadataCollapsibleSection(Composite container) {
		new PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadataHolder(),
			addSubPane(container, 5)
		);
	}

	protected CatalogCombo<EntityMappings> addCatalogCombo(Composite container) {

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

	protected PropertyValueModel<OrmPersistenceUnitMetadata> buildPersistentUnitMetadataHolder() {
		return new TransformationPropertyValueModel<EntityMappings, OrmPersistenceUnitMetadata>(getSubjectHolder()) {
			@Override
			protected OrmPersistenceUnitMetadata transform_(EntityMappings value) {
				return value.getPersistenceUnitMetadata();
			}
		};
	}

	protected SchemaCombo<EntityMappings> addSchemaCombo(Composite container) {

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

			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				return this.getSubject().getDbSchemaContainer();
			}

		};
	}

	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		new EntityMappingsGeneratorsComposite(
			this,
			container
		);
	}

	protected void initializeQueriesCollapsibleSection(Composite container) {
		new OrmQueriesComposite(this, container);
	}
}
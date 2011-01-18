/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import java.util.Collection;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
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

		// Access Type widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.EntityMappingsDetailsPage_access,
			addAccessTypeCombo(container),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);
	}

	protected void initializePersistenceUnitMetadataCollapsibleSection(Composite container) {
		new PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadataHolder(),
			addSubPane(container, 5)
		);
	}
	
	protected EnumFormComboViewer<EntityMappings, AccessType> addAccessTypeCombo(Composite container) {

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
				return value == AccessType.FIELD ?
						JptUiDetailsOrmMessages.EntityMappingsDetailsPage_field :
						JptUiDetailsOrmMessages.EntityMappingsDetailsPage_property;
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
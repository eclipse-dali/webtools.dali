/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaDetailsPage;
import org.eclipse.jpt.ui.internal.widgets.EnumComboViewer;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |              ------------------------------------------------------------ |
 * | Package:     |                                                        |v| |
 * |              ------------------------------------------------------------ |
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
 * @see XmlEntityMappingsDetailsPage - The parent container
 * @see PersistenceUnitMetadataComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class XmlEntityMappingsDetailsPage extends BaseJpaDetailsPage<EntityMappings>
{
	/**
	 * Creates a new <code>XmlEntityMappingsDetailsPage</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public XmlEntityMappingsDetailsPage(PropertyValueModel<? extends EntityMappings> subjectHolder,
	                                    Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private EnumComboViewer<EntityMappings, AccessType> buildAccessTypeCombo(Composite container) {

		return new EnumComboViewer<EntityMappings, AccessType>(this, container) {
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
					JptUiXmlMessages.class,
					XmlEntityMappingsDetailsPage.this,
					value
				);
			}

			@Override
			protected AccessType getValue() {
				return subject().getAccess();
			}

			@Override
			protected String propertyName() {
				return EntityMappings.SPECIFIED_ACCESS_PROPERTY;
			}

			@Override
			protected void setValue(AccessType value) {
				subject().setSpecifiedAccess(value);
			}
		};
	}

	private EnumComboViewer<EntityMappings, String> buildCatalogComboViewer(Composite container) {

		return new EnumComboViewer<EntityMappings, String>(this, container) {
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
			protected String propertyName() {
				return EntityMappings.SPECIFIED_CATALOG_PROPERTY;
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

	private EnumComboViewer<EntityMappings, String> buildSchemaComboViewer(Composite container) {

		return new EnumComboViewer<EntityMappings, String>(this, container) {
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
			protected String propertyName() {
				return EntityMappings.SPECIFIED_SCHEMA_PROPERTY;
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
		XmlPackageChooser xmlCatalogChooser = new XmlPackageChooser(
			this,
			container
		);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.XmlEntityMappingsDetailsPage_package,
			xmlCatalogChooser.getControl(),
			IJpaHelpContextIds.ENTITY_ORM_PACKAGE
		);

		// Schema widgets
		EnumComboViewer<EntityMappings, String> schemaComboViewer =
			buildSchemaComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.XmlSchemaChooser_SchemaChooser,
			schemaComboViewer.getControl(),
			IJpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		EnumComboViewer<EntityMappings, String> catalogComboViewer =
			buildCatalogComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.XmlCatalogChooser_CatalogChooser,
			catalogComboViewer.getControl(),
			IJpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		EnumComboViewer<EntityMappings, AccessType> accessTypeComposite =
			buildAccessTypeCombo(container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.PersistentTypePage_AccessLabel,
			accessTypeComposite.getControl(),
			IJpaHelpContextIds.ENTITY_ORM_ACCESS
		);

		// Persistence Unit Metadata widgets
		new PersistenceUnitMetadataComposite(
			this,
			buildPersistentUnitMetadaHolder(),
			container
		);
	}
}
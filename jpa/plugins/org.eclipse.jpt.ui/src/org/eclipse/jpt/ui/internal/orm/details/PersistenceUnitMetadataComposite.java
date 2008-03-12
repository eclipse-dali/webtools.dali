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
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - Persistence Unit ------------------------------------------------------ |
 * |                                                                           |
 * | x XML Mapping Metadata Complete                                           |
 * |                                                                           |
 * | x Cascade Persist                                                         |
 * |                                                                           |
 * |              ------------------------------------------------------------ |
 * | Schema:      |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Catalog:     |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Access Type: |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnitMetadata
 * @see PersistenceUnitDefaults
 * @see EntityMappingsDetailsPage - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistenceUnitMetadataComposite extends AbstractFormPane<PersistenceUnitMetadata>
{
	/**
	 * Creates a new <code>PersistenceUnitMetadataComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PersistenceUnitMetadataComposite(AbstractFormPane<?> parentPane,
	                                        PropertyValueModel<? extends PersistenceUnitMetadata> subjectHolder,
	                                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private EnumFormComboViewer<PersistenceUnitDefaults, AccessType> buildAccessTypeCombo(Composite container) {

		return new EnumFormComboViewer<PersistenceUnitDefaults, AccessType>(this, buildPersistenceUnitDefaultsHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnitDefaults.ACCESS_PROPERTY);
			}

			@Override
			protected AccessType[] choices() {
				return AccessType.values();
			}

			@Override
			protected AccessType defaultValue() {
				return null;
			}

			@Override
			protected String displayString(AccessType value) {
				return buildDisplayString(
					JptUiOrmMessages.class,
					PersistenceUnitMetadataComposite.this,
					value
				);
			}

			@Override
			protected AccessType getValue() {
				return subject().getAccess();
			}

			@Override
			protected void setValue(AccessType value) {
				subject().setAccess(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadePersistHolder() {
		return new PropertyAspectAdapter<PersistenceUnitDefaults, Boolean>(buildPersistenceUnitDefaultsHolder(), PersistenceUnitDefaults.CASCADE_PERSIST_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isCascadePersist();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setCascadePersist(value);
			}
		};
	}

	private EnumFormComboViewer<PersistenceUnitDefaults, String> buildCatalogComboViewer(Composite container) {

		return new EnumFormComboViewer<PersistenceUnitDefaults, String>(this, buildPersistenceUnitDefaultsHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnitDefaults.CATALOG_PROPERTY);
			}

			@Override
			protected String[] choices() {
				return new String[0];
			}

			@Override
			protected String defaultValue() {
				return "default";
			}

			@Override
			protected String displayString(String value) {
				return value;
			}

			@Override
			protected String getValue() {
				return subject().getCatalog();
			}

			@Override
			protected void setValue(String value) {
				subject().setCatalog(value);
			}
		};
	}

	private PropertyValueModel<PersistenceUnitDefaults> buildPersistenceUnitDefaultsHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitMetadata, PersistenceUnitDefaults>(getSubjectHolder()) {
			@Override
			protected PersistenceUnitDefaults transform_(PersistenceUnitMetadata value) {
				return value.getPersistenceUnitDefaults();
			}
		};
	}

	private EnumFormComboViewer<PersistenceUnitDefaults, String> buildSchemaComboViewer(Composite container) {

		return new EnumFormComboViewer<PersistenceUnitDefaults, String>(this, buildPersistenceUnitDefaultsHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnitDefaults.SCHEMA_PROPERTY);
			}

			@Override
			protected String[] choices() {
				return new String[0];
			}

			@Override
			protected String defaultValue() {
				return "default";
			}

			@Override
			protected String displayString(String value) {
				return value;
			}

			@Override
			protected String getValue() {
				return subject().getSchema();
			}

			@Override
			protected void setValue(String value) {
				subject().setSchema(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildXmlMappingMetadataCompleteHolder() {
		return new PropertyAspectAdapter<PersistenceUnitMetadata, Boolean>(getSubjectHolder(), PersistenceUnitMetadata.XML_MAPPING_METADATA_COMPLETE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isXmlMappingMetadataComplete();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setXmlMappingMetadataComplete(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Section
		container = buildCollapsableSection(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_persistenceUnitSection
		);

		// XML mapping metadata complete check box
		buildCheckBox(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_xmlMappingMetadataCompleteCheckBox,
			buildXmlMappingMetadataCompleteHolder(),
			JpaHelpContextIds.ENTITY_ORM_XML
		);

		// Cascade Persist widgets
		buildCheckBox(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_cascadePersistCheckBox,
			buildCascadePersistHolder(),
			JpaHelpContextIds.ENTITY_ORM_CASCADE
		);

		// Schema widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_schema,
			buildSchemaComboViewer(container),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_catalog,
			buildCatalogComboViewer(container),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.PersistenceUnitMetadataComposite_access,
			buildAccessTypeCombo(container),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);
	}
}

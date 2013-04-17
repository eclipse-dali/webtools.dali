/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.swt.bind.SWTBindTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTenantDiscriminatorColumnsComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.orm.EntityMappingsDetailsPageManager;
import org.eclipse.jpt.jpa.ui.internal.details.orm.PersistenceUnitMetadataComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

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
 * | Schema:      | SchemaCombo                                              | |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Catalog:     | CatalogCombo                                             | |
 * |              ------------------------------------------------------------ |
 * |              ------------------------------------------------------------ |
 * | Access Type: |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnitMetadata
 * @see OrmPersistenceUnitDefaults
 * @see EntityMappingsDetailsPageManager - The parent container
 * @see CatalogCombo
 * @see SchemaCombo
 * @see EnumFormComboViewer
 *
 * @version 3.1
 * @since 3.1
 */
public class EclipseLink2_3PersistenceUnitMetadataComposite extends PersistenceUnitMetadataComposite
{

	protected EclipseLinkTenantDiscriminatorColumnsComposite<OrmPersistenceUnitDefaults> tenantDiscriminatorColumnsComposite;

	public EclipseLink2_3PersistenceUnitMetadataComposite(Pane<?> parentPane,
	                                        PropertyValueModel<? extends OrmPersistenceUnitMetadata> subjectHolder,
	                                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// XML mapping metadata complete check box
		Button metadataCompleteCheckBox = addCheckBox(
			container,
			JptJpaUiDetailsOrmMessages.PERSISTENCE_UNIT_METADATA_COMPOSITE_XML_MAPPING_METADATA_COMPLETE_CHECK_BOX,
			buildXmlMappingMetadataCompleteHolder(),
			JpaHelpContextIds.ENTITY_ORM_XML
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		metadataCompleteCheckBox.setLayoutData(gridData);

		// Cascade Persist check-box
		Button cascadePersistCheckBox = addCheckBox(
			container,
			JptJpaUiDetailsOrmMessages.PERSISTENCE_UNIT_METADATA_COMPOSITE_CASCADE_PERSIST_CHECK_BOX,
			buildCascadePersistHolder(),
			JpaHelpContextIds.ENTITY_ORM_CASCADE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		cascadePersistCheckBox.setLayoutData(gridData);

		// Schema widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.PERSISTENCE_UNIT_METADATA_COMPOSITE_SCHEMA);
		this.addSchemaCombo(container);

		// Catalog widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.PERSISTENCE_UNIT_METADATA_COMPOSITE_CATALOG);
		this.addCatalogCombo(container);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.getPersistenceUnitDefaultsHolder(), container);

		// Delimited Identifiers check-box
		Button diCheckBox = this.addCheckBox(
			container,
			JptJpaUiDetailsOrmMessages.PERSISTENCE_UNIT_METADATA_COMPOSITE_DELIMITED_IDENTIFIERS_CHECK_BOX,
			this.buildDelimitedIdentifiersHolder(),
			JpaHelpContextIds.ENTITY_ORM_DELIMITED_IDENTIFIERS
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		diCheckBox.setLayoutData(gridData);

		SWTBindTools.controlVisibleState(this.buildDelimitedIdentifiersCheckBoxIsVisibleModel(), diCheckBox);


		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMNS_GROUP_LABEL
		);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tenantDiscriminatorColumnGroupPane.setLayoutData(gridData);

		this.tenantDiscriminatorColumnsComposite = new EclipseLinkTenantDiscriminatorColumnsComposite<OrmPersistenceUnitDefaults>(
				this,
				this.getPersistenceUnitDefaultsHolder(),
				this.buildPaneEnablerHolder(),
				tenantDiscriminatorColumnGroupPane,
				this.buildTenantDiscriminatorColumnsEditor()
			);
	}

	private PropertyValueModel<Boolean> buildDelimitedIdentifiersCheckBoxIsVisibleModel() {
		return new TransformationPropertyValueModel<OrmPersistenceUnitMetadata, Boolean>(this.getSubjectHolder(), this.buildPUMetadataIsCompatibleWithJpa2_0Transformer());
	}

	private Transformer<OrmPersistenceUnitMetadata, Boolean> buildPUMetadataIsCompatibleWithJpa2_0Transformer() {
		return TransformerTools.adapt(new JpaModel.JpaVersionIsCompatibleWith(JpaProject2_0.FACET_VERSION_STRING));
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder() {
		return new TransformationPropertyValueModel<OrmPersistenceUnitDefaults, Boolean>(getPersistenceUnitDefaultsHolder()) {
			@Override
			protected Boolean transform(OrmPersistenceUnitDefaults value) {
				return Boolean.valueOf(value != null);
			}
		};
	}

	protected TenantDiscriminatorColumnsEditor<OrmPersistenceUnitDefaults> buildTenantDiscriminatorColumnsEditor() {
		return new TenantDiscriminatorColumnsProvider();
	}

	class TenantDiscriminatorColumnsProvider implements TenantDiscriminatorColumnsEditor<OrmPersistenceUnitDefaults> {

		public EclipseLinkTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn(OrmPersistenceUnitDefaults subject) {
			EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 column = ((EclipseLinkPersistenceUnitDefaults) subject).addTenantDiscriminatorColumn();
			column.setSpecifiedName(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME);
			return column;
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject) {
			return EmptyListIterable.instance();
		}

		public int getDefaultTenantDiscriminatorColumnsSize(OrmPersistenceUnitDefaults subject) {
			return 0;
		}

		public String getDefaultTenantDiscriminatorsListPropertyName() {
			return ""; //$NON-NLS-1$
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject) {
			return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(((EclipseLinkPersistenceUnitDefaults) subject).getTenantDiscriminatorColumns());
		}

		public int getSpecifiedTenantDiscriminatorColumnsSize(OrmPersistenceUnitDefaults subject) {
			return ((EclipseLinkPersistenceUnitDefaults) subject).getTenantDiscriminatorColumnsSize();
		}

		public String getSpecifiedTenantDiscriminatorsListPropertyName() {
			return EclipseLinkPersistenceUnitDefaults.TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public boolean hasSpecifiedTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject) {
			return ((EclipseLinkPersistenceUnitDefaults) subject).hasTenantDiscriminatorColumns();
		}

		public void removeTenantDiscriminatorColumn(OrmPersistenceUnitDefaults subject, EclipseLinkTenantDiscriminatorColumn2_3 column) {
			((EclipseLinkPersistenceUnitDefaults) subject).removeTenantDiscriminatorColumn((EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3) column);
		}
	}

}

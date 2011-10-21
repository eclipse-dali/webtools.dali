/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.TenantDiscriminatorColumnsComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.TenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.orm.EntityMappingsDetailsPage;
import org.eclipse.jpt.jpa.ui.internal.details.orm.JptUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.PersistenceUnitMetadataComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.Jpa2_0ProjectFlagModel;
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
 * @see EntityMappingsDetailsPage - The parent container
 * @see CatalogCombo
 * @see SchemaCombo
 * @see EnumFormComboViewer
 *
 * @version 3.1
 * @since 3.1
 */
public class EclipseLink2_3PersistenceUnitMetadataComposite extends PersistenceUnitMetadataComposite
{

	protected TenantDiscriminatorColumnsComposite<OrmPersistenceUnitDefaults> tenantDiscriminatorColumnsComposite;

	public EclipseLink2_3PersistenceUnitMetadataComposite(Pane<?> parentPane,
	                                        PropertyValueModel<? extends OrmPersistenceUnitMetadata> subjectHolder,
	                                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Section
		container = addCollapsibleSection(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_persistenceUnitSection
		);

		// XML mapping metadata complete check box
		addCheckBox(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_xmlMappingMetadataCompleteCheckBox,
			buildXmlMappingMetadataCompleteHolder(),
			JpaHelpContextIds.ENTITY_ORM_XML
		);

		// Cascade Persist check-box
		addCheckBox(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_cascadePersistCheckBox,
			buildCascadePersistHolder(),
			JpaHelpContextIds.ENTITY_ORM_CASCADE
		);

		// Schema widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_schema,
			addSchemaCombo(container),
			JpaHelpContextIds.ENTITY_ORM_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_catalog,
			addCatalogCombo(container),
			JpaHelpContextIds.ENTITY_ORM_CATALOG
		);

		// Access Type widgets
		addLabeledComposite(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_access,
			addAccessTypeCombo(container),
			JpaHelpContextIds.ENTITY_ORM_ACCESS
		);

		// Delimited Identifiers check-box
		Button diCheckBox = this.addCheckBox(
			container,
			JptUiDetailsOrmMessages.PersistenceUnitMetadataComposite_delimitedIdentifiersCheckBox,
			this.buildDelimitedIdentifiersHolder(),
			JpaHelpContextIds.ENTITY_ORM_DELIMITED_IDENTIFIERS
		);

		SWTTools.controlVisibleState(new Jpa2_0ProjectFlagModel<OrmPersistenceUnitMetadata>(this.getSubjectHolder()), diCheckBox);


		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.TenantDiscriminatorColumns_groupLabel
		);

		this.tenantDiscriminatorColumnsComposite = new TenantDiscriminatorColumnsComposite<OrmPersistenceUnitDefaults>(
			getPersistenceUnitDefaultsHolder(),
			tenantDiscriminatorColumnGroupPane,
			getWidgetFactory(),
			buildTenantDiscriminatorColumnsEditor()
		);

		installTenantDiscriminatorColumnsPaneEnabler(this.tenantDiscriminatorColumnsComposite);
	}

	protected void installTenantDiscriminatorColumnsPaneEnabler(TenantDiscriminatorColumnsComposite<OrmPersistenceUnitDefaults> pane) {
		pane.installListPaneEnabler(buildPaneEnablerHolder());
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

		public void addTenantDiscriminatorColumn(OrmPersistenceUnitDefaults subject) {
			OrmTenantDiscriminatorColumn2_3 column = ((EclipseLinkPersistenceUnitDefaults) subject).addTenantDiscriminatorColumn();
			column.setSpecifiedName(ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME);
		}

		public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject) {
			return EmptyListIterable.instance();
		}

		public int getDefaultTenantDiscriminatorColumnsSize(OrmPersistenceUnitDefaults subject) {
			return 0;
		}

		public String getDefaultTenantDiscriminatorsListPropertyName() {
			return ""; //$NON-NLS-1$
		}

		public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(((EclipseLinkPersistenceUnitDefaults) subject).getTenantDiscriminatorColumns());
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

		public void removeTenantDiscriminatorColumns(OrmPersistenceUnitDefaults subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; index-- > 0; ) {
				((EclipseLinkPersistenceUnitDefaults) subject).removeTenantDiscriminatorColumn(selectedIndices[index]);
			}
		}
	}

}

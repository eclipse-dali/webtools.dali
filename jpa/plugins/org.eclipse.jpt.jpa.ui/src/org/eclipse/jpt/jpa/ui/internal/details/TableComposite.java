/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.SpecifiedTable;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.TableCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - Table ----------------------------------------------------------------- |
 * | |          ------------------------------------------------------------ | |
 * | | Table:   | TableCombo                                               | | |
 * | |          ------------------------------------------------------------ | |
 * | |          ------------------------------------------------------------ | |
 * | | Catalog: | CatalogCombo                                             | | |
 * | |          ------------------------------------------------------------ | |
 * | |          ------------------------------------------------------------ | |
 * | | Schema:  | SchemaCombo                                              | | |
 * | |          ------------------------------------------------------------ | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see SpecifiedTable
 * @see TableCombo
 * @see CatalogCombo
 * @see SchemaCombo
 *
 * @TODO repopulate this panel based on the Entity table changing
 *
 * @version 2.0
 * @since 1.0
 */
public class TableComposite extends Pane<Entity>
{
	/**
	 * Creates a new <code>TableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableComposite(Pane<? extends Entity> parentPane,
	                      Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		Group tableGroupPane = this.addTitledGroup(
			parent,
			JptJpaUiDetailsMessages.TABLE_COMPOSITE_TABLE_SECTION,
			2,
			null
		);
		return tableGroupPane;
	}

	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<SpecifiedTable> subjectHolder = buildTableHolder();
		PropertyValueModel<Boolean> enabledModel = buildTableEnabledModel();

		// Table widgets
		this.addLabel(container, JptJpaUiDetailsMessages.TABLE_CHOOSER_LABEL, enabledModel);
		this.addTableCombo(subjectHolder, enabledModel, container);

		// Catalog widgets
		this.addLabel(container, JptJpaUiDetailsMessages.CATALOG_CHOOSER_LABEL, enabledModel);
		this.addCatalogCombo(subjectHolder, enabledModel, container);

		// Schema widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SCHEMA_CHOOSER_LABEL, enabledModel);
		this.addSchemaCombo(subjectHolder, enabledModel, container);
	}
	
	protected ModifiablePropertyValueModel<SpecifiedTable> buildTableHolder() {
		
		return new PropertyAspectAdapter<Entity, SpecifiedTable>(getSubjectHolder(), Entity.TABLE_IS_UNDEFINED_PROPERTY) {
			@Override
			protected SpecifiedTable buildValue_() {
				return this.subject.tableIsUndefined() ? null : this.subject.getTable();
			}
		};
	}
	
	protected PropertyValueModel<Boolean> buildTableEnabledModel() {
		return new PropertyAspectAdapter<Entity, Boolean>(getSubjectHolder(), Entity.SPECIFIED_TABLE_IS_ALLOWED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.specifiedTableIsAllowed());
			}
		};
	}

	private CatalogCombo<SpecifiedTable> addCatalogCombo(PropertyValueModel<SpecifiedTable> tableHolder, PropertyValueModel<Boolean> enabledModel, Composite container) {
		return new CatalogCombo<SpecifiedTable>(this, tableHolder, enabledModel, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(Table.SPECIFIED_CATALOG_PROPERTY);
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

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_CATALOG;
			}

			@Override
			public String toString() {
				return "TableComposite.catalogCombo"; //$NON-NLS-1$
			}
		};
	}

	private SchemaCombo<SpecifiedTable> addSchemaCombo(PropertyValueModel<SpecifiedTable> subjectHolder, PropertyValueModel<Boolean> enabledModel, Composite container) {
		return new SchemaCombo<SpecifiedTable>(this, subjectHolder, enabledModel, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(Table.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.addAll(SCHEMA_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (SCHEMA_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
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

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_SCHEMA;
			}

			@Override
			public String toString() {
				return "TableComposite.schemaCombo"; //$NON-NLS-1$
			}
		};
	}

	/* CU private */ static final Collection<String> SCHEMA_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		Table.DEFAULT_CATALOG_PROPERTY,
		Table.SPECIFIED_CATALOG_PROPERTY
	});

	private TableCombo<SpecifiedTable> addTableCombo(PropertyValueModel<SpecifiedTable> subjectHolder, PropertyValueModel<Boolean> enabledModel, Composite container) {
		return new TableCombo<SpecifiedTable>(this, subjectHolder, enabledModel, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Table.SPECIFIED_NAME_PROPERTY);
				propertyNames.addAll(TABLE_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (TABLE_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
			}
			
			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				this.getSubject().setSpecifiedName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedName();
			}

			@Override
			protected Schema getDbSchema_() {
				return this.getSubject().getDbSchema();
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_TABLE;
			}

			@Override
			public String toString() {
				return "TableComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}

	/* CU private */ static final Collection<String> TABLE_PICK_LIST_PROPERTIES = Arrays.asList(ArrayTools.addAll(SCHEMA_PICK_LIST_PROPERTIES.toArray(new String[0]),
		Table.DEFAULT_SCHEMA_PROPERTY,
		Table.SPECIFIED_SCHEMA_PROPERTY
	));
}

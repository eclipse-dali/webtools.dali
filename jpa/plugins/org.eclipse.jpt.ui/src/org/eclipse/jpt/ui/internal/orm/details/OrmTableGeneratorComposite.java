/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                           ----------------------------------------------- |
 * | Name:                     | I                                           | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Table:                    | I                                         |v| |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Catalog:                  | CatalogCombo                                | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Schema:                   | SchemaCombo                                 | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Primary Key Column:       | I                                         |v| |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Value Column:             | I                                         |v| |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Primary Key Column Value: | I                                         |v| |
 * |                           ----------------------------------------------- |
 * |                           -------------                                   |
 * | Allocation Size:          | I       |I|  Default (XXX)                    |
 * |                           -------------                                   |
 * |                           -------------                                   |
 * | Initial Value:            | I       |I|  Default (XXX)                    |
 * |                           -------------                                   |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmTableGenerator
 * @see OrmGeneratorsComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmTableGeneratorComposite extends OrmGeneratorComposite<OrmTableGenerator>
{
	/**
	 * Creates a new <code>OrmTableGeneratorComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmTableGeneratorComposite(Pane<?> parentPane,
	                                  PropertyValueModel<OrmTableGenerator> subjectHolder,
	                                  Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_table,
			addTableNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Schema widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_schema,
			addSchemaCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_catalog,
			addCatalogCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_CATALOG
		);

		// Primary Key Column widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_pkColumn,
			addPkColumnNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_valueColumn,
			addValueColumnCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		addLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_pkColumnValue,
			addPkColumnValueCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);

		addAllocationSizeCombo(container);
		addInitialValueCombo(container);
	}
	
	private ColumnCombo<OrmTableGenerator> addPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_PK_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultPkColumnName();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedPkColumnName(value);
			}

			@Override
			protected Table getDbTable_() {
				return getSubject().getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedPkColumnName();
			}
		};
	}

	private ColumnCombo<OrmTableGenerator> addPkColumnValueCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_PK_COLUMN_VALUE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_PK_COLUMN_VALUE_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultPkColumnValue();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedPkColumnValue(value);
			}

			@Override
			protected Table getDbTable_() {
				return getSubject().getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedPkColumnValue();
			}
		};
	}

	private TableCombo<OrmTableGenerator> addTableNameCombo(Composite parent) {

		return new TableCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_TABLE_PROPERTY);
				propertyNames.add(TableGenerator.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				super.propertyChanged(propertyName);
				if (propertyName == TableGenerator.DEFAULT_SCHEMA_PROPERTY 
					|| propertyName == TableGenerator.SPECIFIED_SCHEMA_PROPERTY
					|| propertyName == TableGenerator.DEFAULT_CATALOG_PROPERTY
					|| propertyName == TableGenerator.SPECIFIED_CATALOG_PROPERTY ) {
					repopulate();
				}
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultTable();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedTable(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedTable();
			}

			@Override
			protected Schema getDbSchema_() {
				return this.getSubject().getDbSchema();
			}

		};
	}
	
	private SchemaCombo<TableGenerator> addSchemaCombo(Composite parent) {
		return new SchemaCombo<TableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_SCHEMA_PROPERTY);
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
	
	private CatalogCombo<TableGenerator> addCatalogCombo(Composite parent) {
		return new CatalogCombo<TableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_CATALOG_PROPERTY);
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

	private ColumnCombo<OrmTableGenerator> addValueColumnCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_VALUE_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultValueColumnName();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedValueColumnName(value);
			}

			@Override
			protected Table getDbTable_() {
				return getSubject().getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedValueColumnName();
			}
		};
	}
}

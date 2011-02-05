/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.details.db.TableCombo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                           ----------------------------------------------- |
 * | Name:                     | I                                           | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Table:                    | TableCombo                                  | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Catalog:                  | CatalogCombo                                | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Schema:                   | SchemaCombo                                 | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Primary Key Column:       | ColumnCombo                                 | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Value Column:             | ColumnCombo                                 | |
 * |                           ----------------------------------------------- |
 * |                           ----------------------------------------------- |
 * | Primary Key Column Value: | ColumnCombo                                 | |
 * |                           ----------------------------------------------- |
 * |                           -------------                                   |
 * | Allocation Size:          | I       |I|  Default (XX)                     |
 * |                           -------------                                   |
 * |                           -------------                                   |
 * | Initial Value:            | I       |I|  Default (XX)                     |
 * |                           -------------                                   |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see TableGenerator
 * @see IdMappingGenerationComposite - The parent container
 * @see CatalogCombo
 * @see ColumnCombo
 * @see SchemaCombo
 * @see TableCombo
 *
 * @version 2.2
 * @since 1.0
 */
public class TableGeneratorComposite extends GeneratorComposite<TableGenerator>
{

	
	public TableGeneratorComposite(Pane<?> parentPane,
        							PropertyValueModel<TableGenerator> subjectHolder,
        							Composite parent,
        							GeneratorBuilder<TableGenerator> builder) {

		super(parentPane, subjectHolder, parent, builder);
	}

	@Override
	protected String getPropertyName() {
		return GeneratorContainer.TABLE_GENERATOR_PROPERTY;
	}

	
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_table,
			addTableNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Schema widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_schema,
			addSchemaCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_SCHEMA
		);

		// Catalog widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_catalog,
			addCatalogCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_CATALOG
		);

		// Primary Key Column widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_pkColumn,
			addPkColumnNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_valueColumn,
			addValueColumnCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.TableGeneratorComposite_pkColumnValue,
			addPkColumnValueCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);

		addAllocationSizeCombo(container);
		addInitialValueCombo(container);
	}	

	private CatalogCombo<TableGenerator> addCatalogCombo(Composite container) {

		return new CatalogCombo<TableGenerator>(this, getSubjectHolder(), container) {

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
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedCatalog();
			}
		};
	}

	private ColumnCombo<TableGenerator> addPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, getSubjectHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == TableGenerator.DEFAULT_TABLE_PROPERTY ||
				    propertyName == TableGenerator.SPECIFIED_TABLE_PROPERTY) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultPkColumnName();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedPkColumnName(value);
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

	private ColumnCombo<TableGenerator> addPkColumnValueCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, getSubjectHolder(), parent) {

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
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedPkColumnValue(value);
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

	private SchemaCombo<TableGenerator> addSchemaCombo(Composite container) {

		return new SchemaCombo<TableGenerator>(this, getSubjectHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == TableGenerator.DEFAULT_CATALOG_PROPERTY
					|| propertyName == TableGenerator.SPECIFIED_CATALOG_PROPERTY ) {
					repopulateComboBox();
				}
				else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultSchema();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedSchema();
			}

			@Override
			protected SchemaContainer getDbSchemaContainer() {
				TableGenerator tg = this.getSubject();
				if (tg != null) {
					return tg.getDbSchemaContainer();
				}
				return TableGeneratorComposite.this.getSubject().getContextDefaultDbSchemaContainer();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				// we overrode #getDbSchemaContainer() instead
				throw new UnsupportedOperationException();
			}
		};
	}

	private TableCombo<TableGenerator> addTableNameCombo(Composite parent) {

		return new TableCombo<TableGenerator>(this, getSubjectHolder(), parent) {

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
				if (propertyName == TableGenerator.DEFAULT_SCHEMA_PROPERTY 
					|| propertyName == TableGenerator.SPECIFIED_SCHEMA_PROPERTY
					|| propertyName == TableGenerator.DEFAULT_CATALOG_PROPERTY
					|| propertyName == TableGenerator.SPECIFIED_CATALOG_PROPERTY ) {
					repopulateComboBox();
				}
				else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTable();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedTable(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTable();
			}

			@Override
			protected Schema getDbSchema() {
				TableGenerator tg = this.getSubject();
				if (tg != null) {
					return tg.getDbSchema();
				}
				return TableGeneratorComposite.this.getSubject().getContextDefaultDbSchema();
			}

			@Override
			protected Schema getDbSchema_() {
				// we overrode #getDbSchema() instead
				throw new UnsupportedOperationException();
			}

		};
	}

	private ColumnCombo<TableGenerator> addValueColumnCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, getSubjectHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == TableGenerator.DEFAULT_TABLE_PROPERTY ||
				    propertyName == TableGenerator.SPECIFIED_TABLE_PROPERTY) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultValueColumnName();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return TableGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedValueColumnName(value);
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
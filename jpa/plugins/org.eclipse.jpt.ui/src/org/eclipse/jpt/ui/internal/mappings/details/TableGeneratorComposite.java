/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.GeneratorHolder;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
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
 * | Allocation Size:          | I       |I|  Default (XXX)                    |
 * |                           -------------                                   |
 * |                           -------------                                   |
 * | Initial Value:            | I       |I|  Default (XXX)                    |
 * |                           -------------                                   |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see TableGenerator
 * @see GenerationComposite - The parent container
 * @see CatalogCombo
 * @see ColumnCombo
 * @see SchemaCombo
 * @see TableCombo
 *
 * @version 2.0
 * @since 1.0
 */
public class TableGeneratorComposite extends GeneratorComposite<TableGenerator>
{
	/**
	 * Creates a new <code>TableGeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableGeneratorComposite(Pane<? extends GeneratorHolder> parentPane,
	                               Composite parent) {

		super(parentPane, parent);
	}

	private CatalogCombo<TableGenerator> addCatalogCombo(Composite container) {

		return new CatalogCombo<TableGenerator>(this, buildTableGeneratorHolder(), container) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedCatalog();
			}
		};
	}

	@Override
	protected TableGenerator buildGenerator(GeneratorHolder subject) {
		return subject.addTableGenerator();
	}

	protected TableGenerator buildGenerator() {
		return this.buildGenerator(this.getSubject());
	}

	private ColumnCombo<TableGenerator> addPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedPkColumnName(value);
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

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedPkColumnValue(value);
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

		return new SchemaCombo<TableGenerator>(this, buildTableGeneratorHolder(), container) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedSchema(value);
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

	private PropertyValueModel<TableGenerator> buildTableGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorHolder, TableGenerator>(getSubjectHolder(), GeneratorHolder.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected TableGenerator buildValue_() {
				return subject.getTableGenerator();
			}
		};
	}

	private TableCombo<TableGenerator> addTableNameCombo(Composite parent) {

		return new TableCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedTable(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTable();
			}

			@Override
			protected Schema getDbSchema() {
				TableGenerator tg = this.getSubject();
				if (tg != null) {
					tg.getDbSchema();
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

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

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
				TableGenerator tg = this.getSubject();
				if (tg == null) {
					tg = TableGeneratorComposite.this.buildGenerator();
				}
				tg.setSpecifiedValueColumnName(value);
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected TableGenerator getGenerator(GeneratorHolder subject) {
		return (subject != null) ? subject.getTableGenerator() : null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_table,
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
			JptUiMappingsMessages.TableGeneratorComposite_pkColumn,
			addPkColumnNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_valueColumn,
			addValueColumnCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumnValue,
			addPkColumnValueCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);

		// Allocation Size widgets
		initializeAllocationSizeWidgets(container);

		// Initial Value widgets
		initializeInitialValueWidgets(container);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String getPropertyName() {
		return GeneratorHolder.TABLE_GENERATOR_PROPERTY;
	}
}
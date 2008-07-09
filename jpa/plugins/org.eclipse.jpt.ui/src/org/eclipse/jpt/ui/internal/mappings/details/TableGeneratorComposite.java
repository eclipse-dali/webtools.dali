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
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
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
	public TableGeneratorComposite(AbstractPane<? extends GeneratorHolder> parentPane,
	                               Composite parent) {

		super(parentPane, parent);
	}

	private CatalogCombo<TableGenerator> buildCatalogCombo(Composite container) {

		return new CatalogCombo<TableGenerator>(this, buildTableGeneratorHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultCatalog();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedCatalog(value);
			}

			@Override
			protected String value() {
				return subject().getSpecifiedCatalog();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected TableGenerator buildGenerator(GeneratorHolder subject) {
		return subject.addTableGenerator();
	}

	private ColumnCombo<TableGenerator> buildPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_PK_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultPkColumnName();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedPkColumnName(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedPkColumnName();
			}
		};
	}

	private ColumnCombo<TableGenerator> buildPkColumnValueCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_PK_COLUMN_VALUE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_PK_COLUMN_VALUE_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultPkColumnValue();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedPkColumnValue(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedPkColumnValue();
			}
		};
	}

	private SchemaCombo<TableGenerator> buildSchemaCombo(Composite container) {

		return new SchemaCombo<TableGenerator>(this, buildTableGeneratorHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_SCHEMA_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultSchema();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedSchema(value);
			}

			@Override
			protected String value() {
				return subject().getSpecifiedSchema();
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

	private TableCombo<TableGenerator> buildTableNameCombo(Composite parent) {

		return new TableCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultTable();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected String schemaName() {
				if (subject() != null) {
					return subject().getSchema();
				}
				if (TableGeneratorComposite.this.subject().getEntityMappings() != null) {
					return TableGeneratorComposite.this.subject().getEntityMappings().getSchema();
				}
				return TableGeneratorComposite.this.subject().getPersistenceUnit().getDefaultSchema();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedTable(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedTable();
			}
		};
	}

	private ColumnCombo<TableGenerator> buildValueColumnCombo(Composite parent) {

		return new ColumnCombo<TableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableGenerator.DEFAULT_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(TableGenerator.SPECIFIED_VALUE_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator(
					TableGeneratorComposite.this.subject()
				);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultValueColumnName();
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected JpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedValueColumnName(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedValueColumnName();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected TableGenerator generator(GeneratorHolder subject) {
		return (subject != null) ? subject.getTableGenerator() : null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		buildLabeledText(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_table,
			buildTableNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Schema widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_schema,
			buildSchemaCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_SCHEMA
		);

		// Catalog widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_catalog,
			buildCatalogCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_CATALOG
		);

		// Primary Key Column widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumn,
			buildPkColumnNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_valueColumn,
			buildValueColumnCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumnValue,
			buildPkColumnValueCombo(container),
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
	protected String propertyName() {
		return GeneratorHolder.TABLE_GENERATOR_PROPERTY;
	}
}
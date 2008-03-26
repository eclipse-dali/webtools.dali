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
import java.util.Iterator;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.GeneratorHolder;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
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
 * | Table:                    | I                                         |v| |
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
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see TableGenerator
 * @see GenerationComposite - The parent container
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

			@Override
			protected Iterator<String> values() {
				if ((subject() == null) || (table() == null)) {
					return EmptyIterator.instance();
				}
				return table().columnNames();
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

			@Override
			protected Iterator<String> values() {
				if ((subject() == null) || (table() == null)) {
					return EmptyIterator.instance();
				}
				return table().columnNames();
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

			@Override
			protected Iterator<String> values() {

				if (subject() == null) {
					return EmptyIterator.instance();
				}

				String schemaName = subject().getSchema();
				Database database = database();

				if ((schemaName != null) && (database != null)) {
					Schema schema = database.schemaNamed(schemaName);

					if (schema != null) {
						return schema.tableNames();
					}
				}

				return EmptyIterator.instance();
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

			@Override
			protected Iterator<String> values() {
				if ((subject() == null) || (table() == null)) {
					return EmptyIterator.instance();
				}
				return table().columnNames();
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
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return GeneratorHolder.TABLE_GENERATOR_PROPERTY;
	}
}
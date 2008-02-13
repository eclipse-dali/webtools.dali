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
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

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
 * @see IIdMapping
 * @see ITableGenerator
 * @see GenerationComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class TableGeneratorComposite extends GeneratorComposite<ITableGenerator>
{
	/**
	 * Creates a new <code>TableGeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableGeneratorComposite(AbstractFormPane<? extends IIdMapping> parentPane,
	                               Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ITableGenerator buildGenerator() {
		return subject().addTableGenerator();
	}

	private ColumnCombo<ITableGenerator> buildPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<ITableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ITableGenerator.DEFAULT_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(ITableGenerator.SPECIFIED_PK_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator();
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
			protected IJpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedPkColumnName(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
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

	private ColumnCombo<ITableGenerator> buildPkColumnValueCombo(Composite parent) {

		return new ColumnCombo<ITableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ITableGenerator.DEFAULT_PK_COLUMN_VALUE_PROPERTY);
				propertyNames.add(ITableGenerator.SPECIFIED_PK_COLUMN_VALUE_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator();
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
			protected IJpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedPkColumnValue(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
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

	private PropertyValueModel<ITableGenerator> buildTableGeneratorHolder() {
		return new PropertyAspectAdapter<IIdMapping, ITableGenerator>(getSubjectHolder(), propertyName()) {
			@Override
			protected ITableGenerator buildValue_() {
				return subject.getTableGenerator();
			}
		};
	}

	private TableCombo<ITableGenerator> buildTableNameCombo(Composite parent) {

		return new TableCombo<ITableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ITableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(ITableGenerator.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator();
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
			protected IJpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedTable(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
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

	private ColumnCombo<ITableGenerator> buildValueColumnCombo(Composite parent) {

		return new ColumnCombo<ITableGenerator>(this, buildTableGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ITableGenerator.DEFAULT_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(ITableGenerator.SPECIFIED_VALUE_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected void buildSubject() {
				TableGeneratorComposite.this.buildGenerator();
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
			protected IJpaProject jpaProject() {
				return TableGeneratorComposite.this.jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedValueColumnName(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
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
	protected ITableGenerator generator(IIdMapping subject) {
		return (subject != null) ? subject.getTableGenerator() : null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Text nameText = buildNameText(container);
		setNameText(nameText);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_name,
			nameText.getParent(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_table,
			buildTableNameCombo(container),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Primary Key Column widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumn,
			buildPkColumnNameCombo(container),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_valueColumn,
			buildValueColumnCombo(container),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumnValue,
			buildPkColumnValueCombo(container),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return IIdMapping.TABLE_GENERATOR_PROPERTY;
	}
}
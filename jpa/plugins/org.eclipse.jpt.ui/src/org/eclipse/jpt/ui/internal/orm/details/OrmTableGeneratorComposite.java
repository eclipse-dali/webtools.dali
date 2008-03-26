/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see OrmTableGenerator
 * @see OrmGeneratorsComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmTableGeneratorComposite extends AbstractPane<OrmTableGenerator>
{
	/**
	 * Creates a new <code>OrmTableGeneratorComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmTableGeneratorComposite(AbstractPane<?> parentPane,
	                                  PropertyValueModel<OrmTableGenerator> subjectHolder,
	                                  Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	private WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<OrmTableGenerator, String>(getSubjectHolder(), OrmTableGenerator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			public void setValue_(String value) {
				subject.setName(value);
			}
		};
	}

	private ColumnCombo<OrmTableGenerator> buildPkColumnNameCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmTableGenerator.DEFAULT_PK_COLUMN_NAME_PROPERTY);
				propertyNames.add(OrmTableGenerator.SPECIFIED_PK_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultPkColumnName();
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

	private ColumnCombo<OrmTableGenerator> buildPkColumnValueCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmTableGenerator.DEFAULT_PK_COLUMN_VALUE_PROPERTY);
				propertyNames.add(OrmTableGenerator.SPECIFIED_PK_COLUMN_VALUE_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultPkColumnValue();
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

	private TableCombo<OrmTableGenerator> buildTableNameCombo(Composite parent) {

		return new TableCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmTableGenerator.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(OrmTableGenerator.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultTable();
			}

			@Override
			protected String schemaName() {
				return subject().getSchema();
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

	private ColumnCombo<OrmTableGenerator> buildValueColumnCombo(Composite parent) {

		return new ColumnCombo<OrmTableGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmTableGenerator.DEFAULT_VALUE_COLUMN_NAME_PROPERTY);
				propertyNames.add(OrmTableGenerator.SPECIFIED_VALUE_COLUMN_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultValueColumnName();
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
	protected void initializeLayout(Composite container) {

		// Name widgets
		buildLabeledText(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_table,
			buildTableNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Primary Key Column widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_pkColumn,
			buildPkColumnNameCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_valueColumn,
			buildValueColumnCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmTableGeneratorComposite_pkColumnValue,
			buildPkColumnValueCombo(container),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);
	}
}

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

import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
	private CCombo pkColumnNameCombo;
	private CCombo pkColumnValueCombo;
	private CCombo tableNameCombo;
	private CCombo valueColumnNameCombo;

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

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {
			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				populate();
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				populate();
			}

			public void modified(ConnectionProfile profile) {
				populate();
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				populate();
			}

			private void populate() {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						populateTableNameCombo();
						populatePkColumnChoices();
						populateValueColumnNameCombo();
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				populate();
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				// not interested to this event.
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ITableGenerator buildGenerator() {
		return subject().addTableGenerator();
	}

	private CCombo buildPkColumnNameCombo(Composite parent) {
		CCombo combo = buildCombo(parent);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildPkColumnNameListener());
		return combo;
	}

	private ModifyListener buildPkColumnNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && pkColumnNameCombo.getItemCount() > 0 && text.equals(pkColumnNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = buildGenerator();
				}
				generator.setSpecifiedPkColumnName(text);
			}
		};
	}

	private CCombo buildPkColumnValueCombo(Composite parent) {
		CCombo combo = buildCombo(parent);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildPkColumnValueListener());
		return combo;
	}

	private ModifyListener buildPkColumnValueListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && pkColumnValueCombo.getItemCount() > 0 && text.equals(pkColumnValueCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = buildGenerator();
				}
				generator.setSpecifiedPkColumnValue(text);
			}
		};
	}

	private CCombo buildTableNameCombo(Composite parent) {
		CCombo combo = buildCombo(parent);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildTableNameListener());
		return combo;
	}

	private ModifyListener buildTableNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && tableNameCombo.getItemCount() > 0 && text.equals(tableNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = buildGenerator();
				}
				generator.setSpecifiedTable(text);
			}
		};
	}

	private CCombo buildValueColumnNameCombo(Composite parent) {
		CCombo combo = buildCombo(parent);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildValueColumnNameListener());
		return combo;
	}

	private ModifyListener buildValueColumnNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && valueColumnNameCombo.getItemCount() > 0 && text.equals(valueColumnNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = buildGenerator();
				}
				generator.setSpecifiedValueColumnName(text);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void clear() {
		super.clear();
		this.tableNameCombo.select(0);
		this.pkColumnNameCombo.select(0);
		this.pkColumnValueCombo.select(0);
		this.valueColumnNameCombo.select(0);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		populateTableNameCombo();
		populatePkColumnNameCombo();
		populateValueColumnNameCombo();
		populatePkColumnValueCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ITableGenerator getGenerator() {
		return (subject() != null) ? subject().getTableGenerator() : null;
	}

	protected Schema getSchema() {
		if (getGenerator() != null) {
			return null;// this.getConnectionProfile().getDatabase().schemaNamed(getGenerator().getSchema());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Text nameText = this.buildNameText(container);
		this.setNameText(nameText);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_name,
			nameText,
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME
		);

		// Table widgets
		this.tableNameCombo = buildTableNameCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_table,
			tableNameCombo.getParent(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE
		);

		// Primary Key Column widgets
		this.pkColumnNameCombo = buildPkColumnNameCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumn,
			pkColumnNameCombo.getParent(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN
		);

		// Value Column widgets
		this.valueColumnNameCombo = buildValueColumnNameCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_valueColumn,
			valueColumnNameCombo.getParent(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN
		);

		// Primary Key Column Value widgets
		this.pkColumnValueCombo = buildPkColumnValueCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.TableGeneratorComposite_pkColumnValue,
			pkColumnValueCombo.getParent(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE
		);
	}

	private void populatePkColumnChoices() {
		this.pkColumnNameCombo.remove(1, this.pkColumnNameCombo.getItemCount() - 1);

//		if (this.getConnectionProfile().isConnected()) {
//			if (!this.tableNameCombo.getText().equals(JptUiMappingsMessages.TableGeneratorComposite_default)) { // hmm,
//				// if they actually set the table to Default??
//				String tableName = this.tableNameCombo.getText();
//				Schema schema = getSchema();
//				if (schema != null) {
//					Table table = schema.tableNamed(tableName);
//					if (table != null) {
//						for (Iterator<String> stream = CollectionTools.sort(table.columnNames()); stream.hasNext();) {
//							this.pkColumnNameCombo.add(stream.next());
//						}
//					}
//				}
//			}
//		}
	}

	private void populatePkColumnName() {
		String pkColumnName = this.getGenerator().getSpecifiedPkColumnName();
		if (pkColumnName != null) {
			if (!this.pkColumnNameCombo.getText().equals(pkColumnName)) {
				this.pkColumnNameCombo.setText(pkColumnName);
			}
		}
		else {
			this.pkColumnNameCombo.select(0);
		}
	}

	private void populatePkColumnNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		populatePkColumnChoices();
		populatePkColumnName();
	}

	private void populatePkColumnValueCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		String pkColumnValue = this.getGenerator().getSpecifiedPkColumnValue();
		if (pkColumnValue != null) {
			if (!this.pkColumnValueCombo.getText().equals(pkColumnValue)) {
				this.pkColumnValueCombo.setText(pkColumnValue);
			}
		}
		else {
			this.pkColumnValueCombo.select(0);
		}
	}

	private void populateTableNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
//		if (this.getConnectionProfile().isConnected()) {
//			this.tableNameCombo.remove(1, this.tableNameCombo.getItemCount()-1);
//			Schema schema = this.getSchema();
//			if (schema != null) {
//				Iterator<String> tables = schema.tableNames();
//				for (Iterator<String> stream = CollectionTools.sort(tables); stream.hasNext(); ) {
//					this.tableNameCombo.add(stream.next());
//				}
//			}
//		}
		String tableName = this.getGenerator().getSpecifiedTable();
		if (tableName != null) {
			if (!this.tableNameCombo.getText().equals(tableName)) {
				this.tableNameCombo.setText(tableName);
			}
		}
		else {
			this.tableNameCombo.select(0);
		}
	}

	private void populateValueColumnNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
//		if (this.getConnectionProfile().isConnected()) {
//			this.valueColumnNameCombo.remove(1, this.valueColumnNameCombo.getItemCount() - 1);
//			if (!this.tableNameCombo.getText().equals(JptUiMappingsMessages.TableGeneratorComposite_default)) { // hmm,
//				// if they actually set the table to Default??
//				String tableName = this.tableNameCombo.getText();
//				Schema schema = getSchema();
//				if (schema != null) {
//					Table table = schema.tableNamed(tableName);
//					if (table != null) {
//						for (Iterator<String> stream = CollectionTools.sort(table.columnNames()); stream.hasNext();) {
//							this.valueColumnNameCombo.add(stream.next());
//						}
//					}
//				}
//			}
//		}
		String valueColumnName = this.getGenerator().getSpecifiedValueColumnName();
		if (valueColumnName != null) {
			if (!this.valueColumnNameCombo.getText().equals(valueColumnName)) {
				this.valueColumnNameCombo.setText(valueColumnName);
			}
		}
		else {
			this.valueColumnNameCombo.select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return IIdMapping.TABLE_GENERATOR_PROPERTY;
	}
}
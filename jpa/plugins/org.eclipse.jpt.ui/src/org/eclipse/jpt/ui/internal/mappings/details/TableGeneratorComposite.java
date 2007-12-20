/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class TableGeneratorComposite extends GeneratorComposite<ITableGenerator>
{
	private CCombo tableNameCombo;
	private CCombo pkColumnNameCombo;
	private CCombo valueColumnNameCombo;
	private CCombo pkColumnValueCombo;
	private ConnectionListener connectionListener;
	private ConnectionProfile connectionProfile;

	public TableGeneratorComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
		this.connectionListener = this.buildConnectionListener();
	}

	@Override
	protected ITableGenerator createGenerator() {
		ITableGenerator tableGenerator = idMapping().createTableGenerator();
		idMapping().setTableGenerator(tableGenerator);
		return tableGenerator;
	}

	@Override
	protected ITableGenerator generator(IIdMapping idMapping) {
		return idMapping.getTableGenerator();
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.TableGeneratorComposite_name);

		this.nameTextWidget = buildNameText(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.nameTextWidget.setLayoutData(gridData);
		helpSystem.setHelp(this.nameTextWidget, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_NAME);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.TableGeneratorComposite_table);

		this.tableNameCombo = buildTableNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.tableNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.tableNameCombo, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_TABLE);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.TableGeneratorComposite_pkColumn);

		this.pkColumnNameCombo = buildPkColumnNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.pkColumnNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.pkColumnNameCombo, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.TableGeneratorComposite_valueColumn);

		this.valueColumnNameCombo = buildValueColumnNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.valueColumnNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.valueColumnNameCombo, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_VALUE_COLUMN);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.TableGeneratorComposite_pkColumnValue);

		this.pkColumnValueCombo = buildPkColumnValueCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.pkColumnValueCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.pkColumnValueCombo, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE);
	}

	private CCombo buildTableNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildTableNameListener());
		return combo;
	}

	private CCombo buildPkColumnNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildPkColumnNameListener());
		return combo;
	}

	private CCombo buildValueColumnNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildValueColumnNameListener());
		return combo;
	}

	private CCombo buildPkColumnValueCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JptUiMappingsMessages.TableGeneratorComposite_default);
		combo.select(0);
		combo.addModifyListener(buildPkColumnValueListener());
		return combo;
	}

	private ModifyListener buildTableNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && tableNameCombo.getItemCount() > 0 && text.equals(tableNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = createGenerator();
				}
				generator.setSpecifiedTable(text);
			}
		};
	}

	private ModifyListener buildPkColumnNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && pkColumnNameCombo.getItemCount() > 0 && text.equals(pkColumnNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = createGenerator();
				}
				generator.setSpecifiedPkColumnName(text);
			}
		};
	}

	private ModifyListener buildValueColumnNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && valueColumnNameCombo.getItemCount() > 0 && text.equals(valueColumnNameCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = createGenerator();
				}
				generator.setSpecifiedValueColumnName(text);
			}
		};
	}

	private ModifyListener buildPkColumnValueListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String text = ((CCombo) e.getSource()).getText();
				if (text != null && pkColumnValueCombo.getItemCount() > 0 && text.equals(pkColumnValueCombo.getItem(0))) {
					text = null;
				}
				ITableGenerator generator = getGenerator();
				if (generator == null) {
					generator = createGenerator();
				}
				generator.setSpecifiedPkColumnValue(text);
			}
		};
	}

	@Override
	protected void generatorChanged(Notification notification) {
		super.generatorChanged(notification);
		if (notification.getFeatureID(ITableGenerator.class) == JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_TABLE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					String tableName = getGenerator().getSpecifiedTable();
					if (tableName == null) {
						tableNameCombo.select(0);
					}
					else if (!tableNameCombo.getText().equals(tableName)) {
						tableNameCombo.setText(tableName);
					}
					populatePkColumnNameCombo();
					populateValueColumnNameCombo();
				}
			});
		}
		else if (notification.getFeatureID(ITableGenerator.class) == JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populatePkColumnName();

				}
			});
		}
		else if (notification.getFeatureID(ITableGenerator.class) == JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					String columnName = getGenerator().getSpecifiedValueColumnName();
					if (columnName == null) {
						valueColumnNameCombo.select(0);
					}
					else if (!valueColumnNameCombo.getText().equals(columnName)) {
						valueColumnNameCombo.setText(columnName);
					}
				}
			});
		}
		else if (notification.getFeatureID(ITableGenerator.class) == JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					String columnValue = getGenerator().getSpecifiedPkColumnValue();
					if (columnValue == null) {
						pkColumnValueCombo.select(0);
					}
					else if (!pkColumnValueCombo.getText().equals(columnValue)) {
						pkColumnValueCombo.setText(columnValue);
					}
				}
			});
		}
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();

		if (subject() == null) {
			this.connectionProfile = null;
		}
		else {
			populateTableNameCombo();
			populatePkColumnNameCombo();
			populateValueColumnNameCombo();
			populatePkColumnValueCombo();
		}
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
		if (getGenerator() != null) {
			addConnectionListener();
		}
	}

	@Override
	protected void disengageListeners() {
		if (getGenerator() != null) {
			removeConnectionListener();
		}
		super.disengageListeners();
	}

	private ConnectionProfile getConnectionProfile() {
		if(this.connectionProfile == null) {
			IJpaProject jpaProject = idMapping().getJpaProject();
			this.connectionProfile = jpaProject.connectionProfile();
		}
		return this.connectionProfile;
	}


	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}

	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}

	protected Schema getSchema() {
		if (getGenerator() != null) {
			return this.getConnectionProfile().getDatabase().schemaNamed(getGenerator().getSchema());
		}
		return null;
	}
	private void populateTableNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		if (this.getConnectionProfile().isConnected()) {
			this.tableNameCombo.remove(1, this.tableNameCombo.getItemCount()-1);
			Schema schema = this.getSchema();
			if (schema != null) {
				Iterator<String> tables = schema.tableNames();
				for (Iterator<String> stream = CollectionTools.sort(tables); stream.hasNext(); ) {
					this.tableNameCombo.add(stream.next());
				}
			}
		}
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

	private void populatePkColumnNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		populatePkColumnChoices();
		populatePkColumnName();
	}
	private void populatePkColumnChoices() {
		this.pkColumnNameCombo.remove(1, this.pkColumnNameCombo.getItemCount() - 1);

		if (this.getConnectionProfile().isConnected()) {
			if (!this.tableNameCombo.getText().equals(JptUiMappingsMessages.TableGeneratorComposite_default)) { // hmm,
				// if
				// they
				// actually
				// set
				// the
				// table
				// to
				// Default??
				String tableName = this.tableNameCombo.getText();
				Schema schema = getSchema();
				if (schema != null) {
					Table table = schema.tableNamed(tableName);
					if (table != null) {
						for (Iterator<String> stream = CollectionTools.sort(table.columnNames()); stream.hasNext();) {
							this.pkColumnNameCombo.add(stream.next());
						}
					}
				}
			}
		}
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

	private void populateValueColumnNameCombo() {
		if (this.getGenerator() == null) {
			return;
		}
		if (this.getConnectionProfile().isConnected()) {
			this.valueColumnNameCombo.remove(1, this.valueColumnNameCombo.getItemCount() - 1);
			if (!this.tableNameCombo.getText().equals(JptUiMappingsMessages.TableGeneratorComposite_default)) { // hmm,
				// if
				// they
				// actually
				// set
				// the
				// table
				// to
				// Default??
				String tableName = this.tableNameCombo.getText();
				Schema schema = getSchema();
				if (schema != null) {
					Table table = schema.tableNamed(tableName);
					if (table != null) {
						for (Iterator<String> stream = CollectionTools.sort(table.columnNames()); stream.hasNext();) {
							this.valueColumnNameCombo.add(stream.next());
						}
					}
				}
			}
		}
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

	@Override
	protected void clear() {
		super.clear();
		this.tableNameCombo.select(0);
		this.pkColumnNameCombo.select(0);
		this.pkColumnValueCombo.select(0);
		this.valueColumnNameCombo.select(0);
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {
			public void closed(ConnectionProfile profile) {
				populate();
			}

			public void modified(ConnectionProfile profile) {
				populate();
			}

			public void opened(ConnectionProfile profile) {
				populate();
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				populate();
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
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

			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				// not interested to this event.
			}
		};
	}
}

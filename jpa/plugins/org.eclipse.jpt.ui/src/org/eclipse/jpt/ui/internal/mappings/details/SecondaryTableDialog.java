/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SecondaryTableDialog extends Dialog {

	//if creating a new SecondaryTable, this will be null,
	//specify 'defaultSchema' and 'defaultCatalog' instead in the appropriate constructor
	private SecondaryTable secondaryTable;
	private JpaProject jpaProject;
	private String defaultSchema;
	private String defaultCatalog;
	
	protected Combo nameCombo;
	protected Combo catalogCombo;
	protected Combo schemaCombo;

	private String selectedName;
	private String selectedSchema;
	private String selectedCatalog;

	private boolean defaultSchemaSelected;
	private boolean defaultCatalogSelected;

	public SecondaryTableDialog(Shell parent, JpaProject jpaProject, String defaultSchema, String defaultCatalog) {
		super(parent);
		this.jpaProject = jpaProject;
		this.defaultSchema = defaultSchema;
		this.defaultCatalog = defaultCatalog;
	}

	public SecondaryTableDialog(Shell parent, SecondaryTable secondaryTable, JpaProject jpaProject) {
		super(parent);
		this.secondaryTable = secondaryTable;
		this.jpaProject = jpaProject;
	}

	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		size.x = convertWidthInCharsToPixels(50);
		return size;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getTitle());
	}

	protected String getTitle() {
		if (this.secondaryTable != null) {
			return JptUiMappingsMessages.SecondaryTableDialog_editSecondaryTable;
		}
		return JptUiMappingsMessages.SecondaryTableDialog_addSecondaryTable;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 2;

		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_name);
		GridData gridData = new GridData();
		nameLabel.setLayoutData(gridData);

		this.nameCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.nameCombo.setLayoutData(gridData);
		populateNameCombo();

		Label catalogLabel = new Label(composite, SWT.LEFT);
		catalogLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_catalog);
		gridData = new GridData();
		catalogLabel.setLayoutData(gridData);

		this.catalogCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.catalogCombo.setLayoutData(gridData);
		populateCatalogCombo();

		Label schemaLabel = new Label(composite, SWT.LEFT);
		schemaLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_schema);
		gridData = new GridData();
		schemaLabel.setLayoutData(gridData);

		this.schemaCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.schemaCombo.setLayoutData(gridData);
		populateSchemaCombo();

		
		this.schemaCombo.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				repopulateNameCombo();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
				repopulateNameCombo();
			}
		});

		return composite;
	}

	protected Database getDatabase() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDatabase();
	}

	private ConnectionProfile getConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

	protected Schema getSchemaNamed(String name) {
		Database db = this.getDatabase();
		return (db == null) ? null : db.getSchemaNamed(name);
	}

	protected Schema getDefaultTableSchema() {
		return this.getSchemaNamed(this.getDefaultTableSchemaName());
	}

	protected String getDefaultTableSchemaName() {
		return (this.secondaryTable != null) ? this.secondaryTable.getDefaultSchema() : this.defaultSchema;
	}

	protected Schema getTableSchema() {
		return this.getSchemaNamed(this.getTableSchemaName());
	}

	protected String getTableSchemaName() {
		return (this.secondaryTable != null) ? this.secondaryTable.getSchema() : this.defaultSchema;
	}

	protected void populateNameCombo() {
		Schema schema = this.getTableSchema();
		if (schema != null) {
			for (String name : CollectionTools.sortedSet(schema.tableNames())) {
				this.nameCombo.add(name);
			}
		}

		if (this.secondaryTable != null) {
			if (this.secondaryTable.getSpecifiedName() != null) {
				this.nameCombo.setText(this.secondaryTable.getSpecifiedName());
			}
		}
	}

	protected void repopulateNameCombo() {
		String nameText = this.nameCombo.getText();
		this.nameCombo.removeAll();
		
		Schema schema = null;
		if (this.schemaCombo.getSelectionIndex() == 0) {
			schema = this.getDefaultTableSchema();
		}
		else if (this.schemaCombo.getText() != null) {
			schema = this.getSchemaNamed(this.schemaCombo.getText());
		}
		else {
			schema = this.getTableSchema();
		}
		
		if (schema != null) {
			for (String name : CollectionTools.sortedSet(schema.tableNames())) {
				this.nameCombo.add(name);
			}
		}
		
		this.nameCombo.setText(nameText);
	}
	
	protected void populateSchemaCombo() {
		String defaultSchemaName = this.getDefaultTableSchemaName();
		if (defaultSchemaName != null) {
			this.schemaCombo.add(NLS.bind(JptUiMappingsMessages.SecondaryTableDialog_defaultSchema, defaultSchemaName));
		}
		Database database = this.getDatabase();

		if (database != null) {
			for (String name : CollectionTools.sortedSet(database.schemaNames())) {
				this.schemaCombo.add(name);
			}
		}

		if (this.secondaryTable != null) {
			if (this.secondaryTable.getSpecifiedSchema() != null) {
				this.schemaCombo.setText(this.secondaryTable.getSpecifiedSchema());
			}
			else {
				this.schemaCombo.select(0);
			}
		}
		else {
			this.schemaCombo.select(0);
		}
	}

	protected String getDefaultTableCatalogName() {
		return (this.secondaryTable != null) ? this.secondaryTable.getDefaultCatalog() : this.defaultCatalog;
	}

	protected void populateCatalogCombo() {
		String defaultCatalogName = this.getDefaultTableCatalogName();
		if (defaultCatalogName != null) {
			this.catalogCombo.add(NLS.bind(JptUiMappingsMessages.SecondaryTableDialog_defaultCatalog, defaultCatalogName));
		}

		Database database = this.getDatabase();

		if (database != null) {
			for (String name : CollectionTools.sortedSet(database.catalogNames())) {
				this.catalogCombo.add(name);
			}
		}

		if (this.secondaryTable != null) {
			if (this.secondaryTable.getSpecifiedCatalog() != null) {
				this.catalogCombo.setText(this.secondaryTable.getSpecifiedCatalog());
			}
			else {
				this.catalogCombo.select(0);
			}
		}
		else {
			this.catalogCombo.select(0);			
		}
	}

	protected Combo getNameCombo() {
		return this.nameCombo;
	}

	protected Combo getSchemaCombo() {
		return this.schemaCombo;
	}

	protected Combo getCatalogCombo() {
		return this.catalogCombo;
	}

	protected SecondaryTable getSecondaryTable() {
		return this.secondaryTable;
	}


	public String getSelectedName() {
		return this.selectedName;
	}

	public String getSelectedCatalog() {
		return this.selectedCatalog;
	}

	public String getSelectedSchema() {
		return this.selectedSchema;
	}

	public boolean isDefaultSchemaSelected() {
		return this.defaultSchemaSelected;
	}

	public boolean isDefaultCatalogSelected() {
		return this.defaultCatalogSelected;
	}

	@Override
	public boolean close() {
		this.selectedName = this.nameCombo.getText();
		this.selectedSchema = this.schemaCombo.getText();
		if (this.selectedSchema.equals("")) { //$NON-NLS-1$
			this.selectedSchema = null;
		}
		this.selectedCatalog = this.catalogCombo.getText();
		if (this.selectedCatalog.equals("")) { //$NON-NLS-1$
			this.selectedCatalog = null;
		}
		this.defaultSchemaSelected = this.schemaCombo.getSelectionIndex() == 0;
		this.defaultCatalogSelected = this.catalogCombo.getSelectionIndex() == 0;
		return super.close();
	}
}

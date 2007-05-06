/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SecondaryTableDialog extends Dialog {

	//if creating a new JoinColumn, this will be null, 
	//specify the JoinColumnOwner instead in the appropriate construtor
	private ISecondaryTable secondaryTable;
	private IEntity entity;
	
	protected Combo nameCombo;
	protected Combo catalogCombo;
	protected Combo schemaCombo;
	
	private String selectedName;
	private String selectedSchema;
	private String selectedCatalog;
	
	private boolean defaultSchemaSelected;
	private boolean defaultCatalogSelected;
	
	SecondaryTableDialog(Shell parent, IEntity entity) {
		super(parent);
		this.entity = entity;
	}

	SecondaryTableDialog(Shell parent, ISecondaryTable secondaryTable, IEntity entity) {
		super(parent);
		this.secondaryTable = secondaryTable;
		this.entity = entity;
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getTitle());
	}
	
	protected String getTitle() {
		return JpaUiMappingsMessages.SecondaryTableDialog_editSecondaryTable;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 2;
		
		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(JpaUiMappingsMessages.SecondaryTableDialog_name);
		GridData gridData = new GridData();
		nameLabel.setLayoutData(gridData);
		
		this.nameCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.nameCombo.setLayoutData(gridData);
		populateNameCombo();
	
		Label catalogLabel = new Label(composite, SWT.LEFT);
		catalogLabel.setText(JpaUiMappingsMessages.SecondaryTableDialog_catalog);
		gridData = new GridData();
		catalogLabel.setLayoutData(gridData);
		
		this.catalogCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.catalogCombo.setLayoutData(gridData);
		populateCatalogCombo();

		Label schemaLabel = new Label(composite, SWT.LEFT);
		schemaLabel.setText(JpaUiMappingsMessages.SecondaryTableDialog_schema);
		gridData = new GridData();
		schemaLabel.setLayoutData(gridData);
		
		this.schemaCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.schemaCombo.setLayoutData(gridData);
		populateSchemaCombo();

		return composite;
	}
	
	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}
	
	private ConnectionProfile getConnectionProfile() {
		IJpaProject project = (this.secondaryTable == null) ? this.entity.getJpaProject() : this.secondaryTable.getJpaProject();
		return project.connectionProfile();
	}
	
	protected void populateNameCombo() {
		//TODO populate with the tables from the secondaryTable's schema
//		Table table = getNameTable();
//		if (table != null) {
//			for (Iterator i = table.columnNames(); i.hasNext(); ) {
//				this.nameCombo.add((String) i.next());
//			}
//		}
		if (getSecondaryTable() != null) {
			if (getSecondaryTable().getSpecifiedName() != null) {
				this.nameCombo.setText(getSecondaryTable().getSpecifiedName());
			}
		}
	}

	protected void populateSchemaCombo() {
		if (getSecondaryTable() != null) {
			this.schemaCombo.add(NLS.bind(JpaUiMappingsMessages.SecondaryTableDialog_defaultSchema, getSecondaryTable().getDefaultSchema()));
		}
		
		Database database = this.getDatabase();
		
		if (database != null) {
			Iterator<String> schemata = database.schemaNames();
			for (Iterator<String> stream = CollectionTools.sort(schemata); stream.hasNext(); ) {
				this.schemaCombo.add(stream.next());
			}
		}

		if (getSecondaryTable() != null) {
			if (getSecondaryTable().getSpecifiedSchema() != null) {
				this.schemaCombo.setText(getSecondaryTable().getSpecifiedSchema());
			}
			else {
				this.schemaCombo.select(0);
			}
		}
	}
	
	protected void populateCatalogCombo() {
		if (getSecondaryTable() != null) {
			this.catalogCombo.add(NLS.bind(JpaUiMappingsMessages.SecondaryTableDialog_defaultCatalog, getSecondaryTable().getDefaultCatalog()));
		}
		Database database = this.getDatabase();
		
		if (database != null) {
			Iterator<String> catalogs = database.catalogNames();
			for (Iterator<String> stream = CollectionTools.sort(catalogs); stream.hasNext(); ) {
				this.catalogCombo.add(stream.next());
			}
		}

		if (getSecondaryTable() != null) {
			if (getSecondaryTable().getSpecifiedCatalog() != null) {
				this.catalogCombo.setText(getSecondaryTable().getSpecifiedCatalog());
			}
			else {
				this.catalogCombo.select(0);
			}
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
	
	protected ISecondaryTable getSecondaryTable() {
		return this.secondaryTable;
	}
	
	
	protected String getSelectedName() {
		return this.selectedName;
	}
	
	protected String getSelectedCatalog() {
		return this.selectedCatalog;
	}
	
	protected String getSelectedSchema() {
		return this.selectedSchema;
	}
	
	protected boolean isDefaultSchemaSelected() {
		return this.defaultSchemaSelected;
	}
	
	protected boolean isDefaultCatalogSelected() {
		return this.defaultCatalogSelected;
	}
		
	public boolean close() {
		this.selectedName = this.nameCombo.getText();
		this.selectedSchema = this.schemaCombo.getText();
		if (this.selectedSchema.equals("")) {
			this.selectedSchema = null;
		}
		this.selectedCatalog = this.catalogCombo.getText();
		if (this.selectedCatalog.equals("")) {
			this.selectedCatalog = null;
		}
		this.defaultSchemaSelected = this.schemaCombo.getSelectionIndex() == 0;
		this.defaultCatalogSelected = this.catalogCombo.getSelectionIndex() == 0;
		return super.close();
	}
}

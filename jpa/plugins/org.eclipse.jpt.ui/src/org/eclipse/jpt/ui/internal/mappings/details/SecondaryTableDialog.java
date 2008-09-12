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

import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
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

/**
 * Clients can use this dialog to prompt the user for SecondaryTable settings.
 * Use the following once the dialog is closed:
 *     @see #getSelectedTableIdentifier()
 *     @see #getSelectedCatalogIdentifier()
 *     @see #getSelectedSchemaIdentifier()
 */
public class SecondaryTableDialog extends Dialog {

	private final JpaProject jpaProject;

	/**
	 * when creating a new SecondaryTable, 'secondaryTable' will be null
	 */
	private final SecondaryTable secondaryTable;
	private final String defaultCatalogIdentifier;
	private final String defaultSchemaIdentifier;
	
	protected Combo tableCombo;
	protected Combo catalogCombo;
	protected Combo schemaCombo;

	// these values are set upon close
	private String selectedTableIdentifier;
	private String selectedSchemaIdentifier;
	private String selectedCatalogIdentifier;


	// ********** constructors **********

	/**
	 * Use this constructor to create a new secondary table
	 */
	public SecondaryTableDialog(Shell parent, JpaProject jpaProject, String defaultCatalogIdentifier, String defaultSchemaIdentifier) {
		this(parent, jpaProject, null, defaultCatalogIdentifier, defaultSchemaIdentifier);
	}

	/**
	 * Use this constructor to edit an existing secondary table
	 */
	public SecondaryTableDialog(Shell parent, JpaProject jpaProject, SecondaryTable secondaryTable) {
		this(parent, jpaProject, secondaryTable, secondaryTable.getDefaultCatalog(), secondaryTable.getDefaultSchema());
	}

	/**
	 * internal constructor
	 */
	protected SecondaryTableDialog(Shell parent, JpaProject jpaProject, SecondaryTable secondaryTable, String defaultCatalogIdentifier, String defaultSchemaIdentifier) {
		super(parent);
		this.jpaProject = jpaProject;
		this.secondaryTable = secondaryTable;
		this.defaultCatalogIdentifier = defaultCatalogIdentifier;
		this.defaultSchemaIdentifier = defaultSchemaIdentifier;
	}


	// ********** open **********

	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		size.x = this.convertWidthInCharsToPixels(50);  // ???
		return size;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	protected String getTitle() {
		return (this.secondaryTable == null) ?
						JptUiMappingsMessages.SecondaryTableDialog_addSecondaryTable
					:
						JptUiMappingsMessages.SecondaryTableDialog_editSecondaryTable;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 2;

		// table
		Label tableLabel = new Label(composite, SWT.LEFT);
		tableLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_name);
		GridData gridData = new GridData();
		tableLabel.setLayoutData(gridData);

		this.tableCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.tableCombo.setLayoutData(gridData);

		// catalog
		Label catalogLabel = new Label(composite, SWT.LEFT);
		catalogLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_catalog);
		gridData = new GridData();
		catalogLabel.setLayoutData(gridData);

		this.catalogCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.catalogCombo.setLayoutData(gridData);

		// schema
		Label schemaLabel = new Label(composite, SWT.LEFT);
		schemaLabel.setText(JptUiMappingsMessages.SecondaryTableDialog_schema);
		gridData = new GridData();
		schemaLabel.setLayoutData(gridData);

		this.schemaCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.schemaCombo.setLayoutData(gridData);

		this.initializeCatalogCombo();
		this.initializeSchemaCombo();
		this.initializeTableCombo();

		this.catalogCombo.addSelectionListener(this.buildCatalogSelectionListener());
		this.schemaCombo.addSelectionListener(this.buildSchemaSelectionListener());

		return composite;
	}

	protected void initializeCatalogCombo() {
		this.populateCatalogCombo();

		if (this.isAddDialog()) {
			this.catalogCombo.select(0);  // out-of-bounds index is ignored
		} else {
			String specifiedCatalog = this.secondaryTable.getSpecifiedCatalog();
			if (specifiedCatalog == null) {
				this.catalogCombo.select(0);  // out-of-bounds index is ignored
			} else {
				this.catalogCombo.setText(specifiedCatalog);
			}
		}
	}

	protected void populateCatalogCombo() {
		Database database = this.getDatabase();
		if ((database != null) && ! database.supportsCatalogs()) {
			this.catalogCombo.setEnabled(false);
			return;
		}

		// add the default catalog first
		if (this.defaultCatalogIdentifier != null) {
			this.catalogCombo.add(NLS.bind(JptUiMappingsMessages.SecondaryTableDialog_defaultCatalog, this.defaultCatalogIdentifier));
		}

		if (database != null) {
			for (Iterator<String> stream = database.sortedCatalogIdentifiers(); stream.hasNext(); ) {
				this.catalogCombo.add(stream.next());
			}
		}
	}

	protected void initializeSchemaCombo() {
		this.populateSchemaCombo();

		if (this.isAddDialog()) {
			this.schemaCombo.select(0);  // out-of-bounds index is ignored
		} else {
			String specifiedSchema = this.secondaryTable.getSpecifiedSchema();
			if (specifiedSchema == null) {
				this.schemaCombo.select(0);  // out-of-bounds index is ignored
			} else {
				this.schemaCombo.setText(specifiedSchema);
			}
		}
	}

	// assume the catalog combo has been populated by now
	protected void populateSchemaCombo() {
		// add the default schema first
		if (this.defaultSchemaIdentifier != null) {
			this.schemaCombo.add(NLS.bind(JptUiMappingsMessages.SecondaryTableDialog_defaultSchema, this.defaultSchemaIdentifier));
		}

		SchemaContainer schemaContainer = this.getCurrentSchemaContainer();
		if (schemaContainer != null) {
			for (Iterator<String> stream = schemaContainer.sortedSchemaIdentifiers(); stream.hasNext(); ) {
				this.schemaCombo.add(stream.next());
			}
		}
	}

	protected void initializeTableCombo() {
		this.populateTableCombo();

		if (this.isEditDialog()) {
			String specifiedName = this.secondaryTable.getSpecifiedName();
			if (specifiedName != null) {
				this.tableCombo.setText(specifiedName);
			}
		}
	}

	// assume the schema combo has been populated by now
	protected void populateTableCombo() {
		// we don't need to add a "default" to the table combo
		Schema schema = this.getCurrentSchema();
		if (schema != null) {
			for (Iterator<String> stream = schema.sortedTableIdentifiers(); stream.hasNext(); ) {
				this.tableCombo.add(stream.next());
			}
		}
	}


	// ********** listeners **********

	protected SelectionListener buildCatalogSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				SecondaryTableDialog.this.selectedCatalogChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				SecondaryTableDialog.this.selectedCatalogChanged();
			}
			@Override
			public String toString() {
				return "catalog selection listener"; //$NON-NLS-1$
			}
		};
	}

	protected void selectedCatalogChanged() {
		this.refreshSchemaCombo();
		this.refreshTableCombo();
	}

	protected void refreshSchemaCombo() {
		String schemaIdentifier = this.schemaCombo.getText();
		this.schemaCombo.removeAll();
		this.populateSchemaCombo();
		this.schemaCombo.setText(schemaIdentifier);
	}

	protected SelectionListener buildSchemaSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				SecondaryTableDialog.this.selectedSchemaChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				SecondaryTableDialog.this.selectedSchemaChanged();
			}
			@Override
			public String toString() {
				return "schema selection listener"; //$NON-NLS-1$
			}
		};
	}

	protected void selectedSchemaChanged() {
		this.refreshTableCombo();
	}

	protected void refreshTableCombo() {
		String tableIdentifier = this.tableCombo.getText();
		this.tableCombo.removeAll();
		this.populateTableCombo();
		this.tableCombo.setText(tableIdentifier);
	}


	// ********** convenience methods **********

	protected boolean isAddDialog() {
		return this.secondaryTable == null;
	}

	protected boolean isEditDialog() {
		return ! this.isAddDialog();
	}

	protected Database getDatabase() {
		return this.jpaProject.getDataSource().getDatabase();
	}

	protected SchemaContainer getCurrentSchemaContainer() {
		Database database = this.getDatabase();
		if (database == null) {
			return null;
		}
		if ( ! database.supportsCatalogs()) {
			return database;
		}
		String catalogIdentifier = this.getCurrentCatalogIdentifier();
		return (catalogIdentifier == null) ? null : database.getCatalogForIdentifier(catalogIdentifier);
	}

	protected String getCurrentCatalogIdentifier() {
		if ((this.defaultCatalogIdentifier != null) && (this.catalogCombo.getSelectionIndex() == 0)) {
			return this.defaultCatalogIdentifier;
		}
		return convertText(this.catalogCombo);
	}

	protected Schema getCurrentSchema() {
		String schemaIdentifier = this.getCurrentSchemaIdentifier();
		if (schemaIdentifier == null) {
			return null;
		}
		SchemaContainer schemaContainer = this.getCurrentSchemaContainer();
		return (schemaContainer == null) ? null : schemaContainer.getSchemaForIdentifier(schemaIdentifier);
	}

	protected String getCurrentSchemaIdentifier() {
		if ((this.defaultSchemaIdentifier != null) && (this.schemaCombo.getSelectionIndex() == 0)) {
			return this.defaultSchemaIdentifier;
		}
		return convertText(this.schemaCombo);
	}


	// ********** close **********

	/**
	 * set all the various values queried by clients once the dialog is closed
	 */
	@Override
	public boolean close() {
		this.selectedTableIdentifier = this.tableCombo.getText();
		this.selectedCatalogIdentifier = convertText(this.catalogCombo, this.defaultCatalogIdentifier);
		this.selectedSchemaIdentifier = convertText(this.schemaCombo, this.defaultSchemaIdentifier);
		return super.close();
	}

	/**
	 * return null if:
	 *   - the default value is selected
	 *   - the combo's text is empty
	 */
	protected static String convertText(Combo combo, String defaultText) {
		// if the default text is present, then it will be the combo's first selection
		if ((defaultText != null) && (combo.getSelectionIndex() == 0)) {
			return null;
		}
		return convertText(combo);
	}

	/**
	 * return null if the combo's text is empty
	 */
	protected static String convertText(Combo combo) {
		String text = combo.getText();
		return (text.length() == 0) ? null : text;
	}


	// ********** public API **********

	/**
	 * Return the selected table identifier. Return an empty string if nothing
	 * is selected (since there is no default).
	 */
	public String getSelectedTableIdentifier() {
		return this.selectedTableIdentifier;
	}

	/**
	 * Return the selected catalog identifier. Return null if either nothing or
	 * the default catalog is selected.
	 */
	public String getSelectedCatalogIdentifier() {
		return this.selectedCatalogIdentifier;
	}

	/**
	 * Return the selected schema identifier. Return null if either nothing or
	 * the default schema is selected.
	 */
	public String getSelectedSchemaIdentifier() {
		return this.selectedSchemaIdentifier;
	}

}

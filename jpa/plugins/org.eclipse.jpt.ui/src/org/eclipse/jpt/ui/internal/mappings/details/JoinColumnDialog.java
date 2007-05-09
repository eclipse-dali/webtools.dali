/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public abstract class JoinColumnDialog extends AbstractJoinColumnDialog<IJoinColumn> {
	
	private Combo tableCombo;
	private ComboViewer insertableComboViewer;
	private ComboViewer updatableComboViewer;

	private boolean defaultTableSelected;
	private String selectedTable;
	private DefaultTrueBoolean insertable;
	private DefaultTrueBoolean updatable;
	
	JoinColumnDialog(Shell parent) {
		super(parent);
	}

	JoinColumnDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
	}

	protected Control createDialogArea(Composite parent) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		Composite composite = (Composite) super.createDialogArea(parent);
	
		Label tableLabel = new Label(composite, SWT.LEFT);
		tableLabel.setText(JptUiMappingsMessages.JoinColumnDialog_table);
		GridData gridData = new GridData();
		tableLabel.setLayoutData(gridData);

		this.tableCombo = new Combo(composite, SWT.LEFT);
		this.tableCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				populateNameCombo();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				populateNameCombo();
			}
		});
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.tableCombo.setLayoutData(gridData);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this.tableCombo, IJpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN);
		populateTableCombo();

		Label insertableLabel = new Label(composite, SWT.LEFT);
		insertableLabel.setText(JptUiMappingsMessages.JoinColumnDialog_insertable);
		insertableLabel.setLayoutData(new GridData());
		
		this.insertableComboViewer = this.buildInsertableComboViewer(composite);
		this.insertableComboViewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		helpSystem.setHelp(this.insertableComboViewer.getCombo(), IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE);
	
		Label updatableLabel = new Label(composite, SWT.LEFT);
		updatableLabel.setText(JptUiMappingsMessages.JoinColumnDialog_updatable);
		updatableLabel.setLayoutData(new GridData());
		
		this.updatableComboViewer = this.buildUpdatableComboViewer(composite);
		this.updatableComboViewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		helpSystem.setHelp(this.updatableComboViewer.getCombo(), IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE);
	
		return composite;
	}

	private ComboViewer buildInsertableComboViewer(Composite parent) {
		ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY);
		viewer.add(DefaultTrueBoolean.VALUES.toArray());
		DefaultTrueBoolean sel = (this.joinColumn() == null) ? DefaultTrueBoolean.DEFAULT : this.joinColumn().getInsertable();
		viewer.setSelection(new StructuredSelection(sel));
		return viewer;
	}

	private ComboViewer buildUpdatableComboViewer(Composite parent) {
		ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY);
		viewer.add(DefaultTrueBoolean.VALUES.toArray());
		DefaultTrueBoolean sel = (this.joinColumn() == null) ? DefaultTrueBoolean.DEFAULT : this.joinColumn().getUpdatable();
		viewer.setSelection(new StructuredSelection(sel));
		return viewer;
	}

	
	protected void populateTableCombo() {
		this.tableCombo.add(NLS.bind(JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam, defaultTableName()));
		Schema schema = this.getSchema();
		if (schema != null) {
			Iterator<String> tables = schema.tableNames();
			for (Iterator<String> stream = CollectionTools.sort( tables); stream.hasNext(); ) {
				this.tableCombo.add(stream.next());
			}
		}
		if (specifiedTableName() != null) {
			this.tableCombo.setText(specifiedTableName());
		}
		else {
			this.tableCombo.select(0);
		}
	}
	
	protected abstract String defaultTableName();
	
	protected String specifiedTableName() {
		if (getJoinColumn() != null) {
			return getJoinColumn().getSpecifiedTable();
		}
		return null;
	}
	
	protected String tableName() {
		if (this.tableCombo != null) {
			if (this.tableCombo.getSelectionIndex() == 0) {
				return defaultTableName();
			}
			return this.tableCombo.getText();
		}
		return (this.specifiedTableName() == null) ? defaultTableName() : this.specifiedTableName();
	}
	
	protected abstract Schema getSchema();
	
	private IJoinColumn joinColumn() {
		return this.getJoinColumn();
	}

	protected boolean isDefaultTableSelected() {
		return this.defaultTableSelected;
	}

	public String getSelectedTable() {
		return this.selectedTable;
	}

	public DefaultTrueBoolean getInsertable() {
		return this.insertable;
	}

	public DefaultTrueBoolean getUpdatable() {
		return this.updatable;
	}

	public boolean close() {
		this.defaultTableSelected = this.tableCombo.getSelectionIndex() == 0;
		this.selectedTable = this.tableCombo.getText();

		ISelection selection = this.insertableComboViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			this.insertable = (DefaultTrueBoolean) ((IStructuredSelection) selection).getFirstElement();
		}

		selection = this.updatableComboViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			this.updatable = (DefaultTrueBoolean) ((IStructuredSelection) selection).getFirstElement();
		}

		return super.close();
	}

}

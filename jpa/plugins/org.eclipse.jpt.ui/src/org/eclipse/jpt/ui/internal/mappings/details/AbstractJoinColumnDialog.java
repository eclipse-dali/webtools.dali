/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

//if there is only 1 joinColumn and the user is editing it, they should be
//able to define defaults.  otherwise, we probably shouldn't allow it.
public abstract class AbstractJoinColumnDialog<E extends IAbstractJoinColumn> extends Dialog {

	//if creating a new JoinColumn, this will be null, 
	//specify the JoinColumnOwner instead in the appropriate construtor
	private E joinColumn;
	
	private Combo nameCombo;
	private Combo referencedColumnNameCombo;

	
	private boolean defaultNameSelected;
	private String selectedName;
	private boolean defaultReferencedColumnNameSelected;
	private String selectedReferencedColumnName;
	
	AbstractJoinColumnDialog(Shell parent) {
		super(parent);
	}

	AbstractJoinColumnDialog(Shell parent, E joinColumn) {
		super(parent);
		this.joinColumn = joinColumn;
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getTitle());
	}
	
	protected String getTitle() {
		return JptUiMappingsMessages.JoinColumnDialog_editJoinColumn;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 2;
		
		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(JptUiMappingsMessages.JoinColumnDialog_name);
		GridData gridData = new GridData();
		nameLabel.setLayoutData(gridData);
		
		this.nameCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.nameCombo.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.nameCombo, IJpaHelpContextIds.MAPPING_JOIN_COLUMN_NAME);
		populateNameCombo();
	
		Label referencedColumnNameLabel = new Label(composite, SWT.LEFT);
		referencedColumnNameLabel.setText(JptUiMappingsMessages.JoinColumnDialog_referencedColumnName);
		gridData = new GridData();
		referencedColumnNameLabel.setLayoutData(gridData);

		this.referencedColumnNameCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.referencedColumnNameCombo.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.referencedColumnNameCombo, IJpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN);
		populateReferencedNameCombo();

		return composite;
	}
	
	protected void populateNameCombo() {
		if (getJoinColumn() != null) {
			this.nameCombo.add(NLS.bind(JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam, getJoinColumn().getDefaultName()));
		}
		Table table = getNameTable();
		if (table != null) {
			for (Iterator i = table.columnNames(); i.hasNext(); ) {
				this.nameCombo.add((String) i.next());
			}
		}
		if (getJoinColumn() != null) {
			if (getJoinColumn().getSpecifiedName() != null) {
				this.nameCombo.setText(getJoinColumn().getSpecifiedName());
			}
			else {
				this.nameCombo.select(0);
			}
		}
	}
	
	protected Combo getNameCombo() {
		return this.nameCombo;
	}
	
	protected Combo getReferencedColumnNameCombo() {
		return this.referencedColumnNameCombo;
	}
	
	protected abstract Table getNameTable();
	
	protected abstract Table getReferencedNameTable();

	protected void populateReferencedNameCombo() {
		if (getJoinColumn() != null) {
			this.referencedColumnNameCombo.add(NLS.bind(JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam, getJoinColumn().getDefaultReferencedColumnName()));
		}
		Table referencedNameTable = getReferencedNameTable();
		if (referencedNameTable != null) {
			for (Iterator i = referencedNameTable.columnNames(); i.hasNext(); ) {
				this.referencedColumnNameCombo.add((String) i.next());
			}
		}
		if (getJoinColumn() != null) {
			if (getJoinColumn().getSpecifiedReferencedColumnName() != null) {
				this.referencedColumnNameCombo.setText(getJoinColumn().getSpecifiedReferencedColumnName());
			}
			else {
				this.referencedColumnNameCombo.select(0);
			}
		}
	}
	
	protected E getJoinColumn() {
		return this.joinColumn;
	}
	
	protected boolean isDefaultNameSelected() {
		return this.defaultNameSelected;
	}
	
	protected String getSelectedName() {
		return this.selectedName;
	}
	
	protected boolean isDefaultReferencedColumnNameSelected() {
		return this.defaultReferencedColumnNameSelected;
	}
	
	protected String getReferencedColumnName() {
		return this.selectedReferencedColumnName;			
	}
	
	public boolean close() {
		this.defaultNameSelected = this.nameCombo.getSelectionIndex() == 0;
		this.selectedName = this.nameCombo.getText();
		this.defaultReferencedColumnNameSelected = this.referencedColumnNameCombo.getSelectionIndex() == 0;
		this.selectedReferencedColumnName = this.referencedColumnNameCombo.getText();
		return super.close();
	}

}

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

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public class JoinColumnDialog extends AbstractJoinColumnDialog<IJoinColumn> {
	private DefaultTrueBoolean insertable;
	private ComboViewer insertableComboViewer;

	private DefaultTrueBoolean updatable;
	private ComboViewer updatableComboViewer;

	private ISingleRelationshipMapping singleRelationshipMapping;
	
	JoinColumnDialog(Shell parent, ISingleRelationshipMapping singleRelationshipMapping) {
		super(parent);
		this.singleRelationshipMapping = singleRelationshipMapping;
	}

	JoinColumnDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.singleRelationshipMapping = (ISingleRelationshipMapping) joinColumn.eContainer();
	}

	protected Control createDialogArea(Composite parent) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		Composite composite = (Composite) super.createDialogArea(parent);
		
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

	protected Table getNameTable() {
		return this.singleRelationshipMapping.typeMapping().primaryDbTable();
	}
	
	protected Table getReferencedNameTable() {
		IEntity targetEntity = this.singleRelationshipMapping.getResolvedTargetEntity();
		if (targetEntity != null) {
			return targetEntity.primaryDbTable();
		}
		return null;
	}

	private IJoinColumn joinColumn() {
		return this.getJoinColumn();
	}

	public DefaultTrueBoolean getInsertable() {
		return this.insertable;
	}

	public DefaultTrueBoolean getUpdatable() {
		return this.updatable;
	}

	public boolean close() {
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

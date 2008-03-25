/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * EntityDialog
 */
public class EntityDialog extends Dialog
{
	private Entity entity;

	private JpaProject jpaProject;

	protected Combo nameCombo;

	private String selectedName;

	public EntityDialog(Shell parent, JpaProject jpaProject) {
		super(parent);
		this.jpaProject = jpaProject;
	}

	public EntityDialog(Shell parent, Entity entity, JpaProject jpaProject) {
		super(parent);
		this.entity = entity;
		this.jpaProject = jpaProject;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getTitle());
	}

	protected String getTitle() {
		return EclipseLinkUiMessages.EntityDialog_selectEntity;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 2;

		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(EclipseLinkUiMessages.EntityDialog_name);
		GridData gridData = new GridData();
		nameLabel.setLayoutData(gridData);

		this.nameCombo = new Combo(composite, SWT.LEFT);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.nameCombo.setLayoutData(gridData);

		populateNameCombo();
		return composite;
	}

	protected void populateNameCombo() {
		if (selectedName != null) {
			this.nameCombo.setText(selectedName);
		}
	}

	protected Combo getNameCombo() {
		return this.nameCombo;
	}

	protected Entity getEntity() {
		return this.entity;
	}

	public String getSelectedName() {
		return this.selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
	}

	@Override
	public boolean close() {
		this.selectedName = this.nameCombo.getText();
		return super.close();
	}
}

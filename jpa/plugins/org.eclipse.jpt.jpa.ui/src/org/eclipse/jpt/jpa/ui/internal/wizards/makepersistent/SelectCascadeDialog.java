/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class SelectCascadeDialog extends Dialog
{
	private Button allCheckbox;
	private Button persistCheckbox;
	private Button mergeCheckbox;
	private Button removeCheckbox;
	private Button refreshCheckbox;
	private List<String> cascadeValues;
	
	public SelectCascadeDialog(Shell shell, List<String> initialValues)
	{
		super(shell);
		cascadeValues = new ArrayList<String>();
		cascadeValues.addAll(initialValues);
	}
	
	@Override
	protected void configureShell(Shell newShell) 
	{
		newShell.setText(JptJpaUiMakePersistentMessages.SELECT_CASCADE_DLG_TITLE); 
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		Composite composite = (Composite)super.createDialogArea(parent);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 250;
		gd.heightHint = 200;
		composite.setLayoutData(gd);
		allCheckbox = new Button(composite, SWT.CHECK);
		allCheckbox.setText(JPA.CASCADE_ALL);
		persistCheckbox = new Button(composite, SWT.CHECK);
		persistCheckbox.setText(JPA.CASCADE_PERSIST);
		mergeCheckbox = new Button(composite, SWT.CHECK);
		mergeCheckbox.setText(JPA.CASCADE_MERGE);
		removeCheckbox = new Button(composite, SWT.CHECK);
		removeCheckbox.setText(JPA.CASCADE_REMOVE);
		refreshCheckbox = new Button(composite, SWT.CHECK);
		refreshCheckbox.setText(JPA.CASCADE_REFRESH);
		
		
		SelectionAdapter selectionAdapater = new SelectionAdapter()
		{
            @Override
            public void widgetSelected( SelectionEvent e )
            {
            	selectOtherCascade();
            }			
		};
		SelectionAdapter selectionAdapater2 = new SelectionAdapter()
		{
            @Override
            public void widgetSelected( SelectionEvent e )
            {
            	selectAllCascade();
            }			
		};
		persistCheckbox.addSelectionListener(selectionAdapater);
		mergeCheckbox.addSelectionListener(selectionAdapater);
		removeCheckbox.addSelectionListener(selectionAdapater);
		refreshCheckbox.addSelectionListener(selectionAdapater);
		allCheckbox.addSelectionListener(selectionAdapater2);
		
		init();
		return composite;
	}
	
	private void init()
	{
		if (cascadeValues.contains(JPA.CASCADE_ALL))
			allCheckbox.setSelection(true);
		if (cascadeValues.contains(JPA.CASCADE_PERSIST))
			persistCheckbox.setSelection(true);
		if (cascadeValues.contains(JPA.CASCADE_MERGE))
			mergeCheckbox.setSelection(true);
		if (cascadeValues.contains(JPA.CASCADE_MERGE))
			removeCheckbox.setSelection(true);
		if (cascadeValues.contains(JPA.CASCADE_REFRESH))
			refreshCheckbox.setSelection(true);
		
	}
	@Override
	protected boolean isResizable() 
	{
		return true;
	}
	
	public List<String> getAllCascades()
	{
		return cascadeValues;
	}
	
	private void selectAllCascade()
	{
		cascadeValues.clear();
		if (allCheckbox.getSelection())
		{
			cascadeValues.add(JPA.CASCADE_ALL);
			persistCheckbox.setSelection(false);
			mergeCheckbox.setSelection(false);
			removeCheckbox.setSelection(false);
			refreshCheckbox.setSelection(false);
		}
	}
	
	private void selectOtherCascade()
	{
		cascadeValues.clear();
		if (persistCheckbox.getSelection())
		{
			cascadeValues.add(JPA.CASCADE_PERSIST);
			allCheckbox.setSelection(false);
		}
		if (mergeCheckbox.getSelection())
		{
			cascadeValues.add(JPA.CASCADE_MERGE);
			allCheckbox.setSelection(false);
		}			
		if (removeCheckbox.getSelection())
		{
			cascadeValues.add(JPA.CASCADE_REMOVE);
			allCheckbox.setSelection(false);
		}
		if (refreshCheckbox.getSelection())
		{
			cascadeValues.add(JPA.CASCADE_REFRESH);
			allCheckbox.setSelection(false);
		}		
	}
	
}

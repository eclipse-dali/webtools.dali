/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class TargetEntityChooser extends BaseJpaController
{
	private IRelationshipMapping relationshipMapping;
	private Adapter relationshipMappingListener;
	
	protected CCombo targetEntityCombo;

	
	private Composite composite;
	
	public TargetEntityChooser(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildRelationshipMappingListener();
	}
	
	
	private void buildRelationshipMappingListener() {
		relationshipMappingListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				relationshipMappingChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		this.composite = getWidgetFactory().createComposite(parent);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.marginHeight = 0;
	    gridLayout.marginWidth = 0;
	    gridLayout.numColumns = 3;
	    this.composite.setLayout(gridLayout);
		
		CommonWidgets.buildTargetEntityLabel(this.composite, getWidgetFactory());
		
		this.targetEntityCombo = buildTargetEntityCombo(this.composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.targetEntityCombo.setLayoutData(gridData);

		buildTargetEntitySelectionButton(this.composite);

	}
	
	protected CCombo buildTargetEntityCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JpaUiMappingsMessages.TargetEntityChooser_defaultEmpty);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(combo, IJpaHelpContextIds.MAPPING_TARGET_ENTITY);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String targetEntityName = ((CCombo) e.getSource()).getText();
				if (targetEntityName.equals(combo.getItem(0)) || targetEntityName.equals("")) { //$NON-NLS-1$
					targetEntityName = null;
				}
				else if (!relationshipMapping.targetEntityIsValid(targetEntityName)) {
					return;
				}
				relationshipMapping.setSpecifiedTargetEntity(targetEntityName);
			}
		});
		return combo;
	}


	private void relationshipMappingChanged(Notification notification) {
		if (notification.getFeatureID(IRelationshipMapping.class) == 
				JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						populate();
					}
				});
		}
		else if (notification.getFeatureID(IRelationshipMapping.class) == 
			JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY) {
		Display.getDefault().asyncExec(
			new Runnable() {
				public void run() {
					populate();
				}
			});
	}
	}
	
	@Override
	protected void engageListeners() {
		if (relationshipMapping != null) {
			relationshipMapping.eAdapters().add(relationshipMappingListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.relationshipMapping != null) {
			this.relationshipMapping.eAdapters().remove(relationshipMappingListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.relationshipMapping = (IRelationshipMapping) obj;
		populateCombo();
	}
	
	@Override
	protected void doPopulate() {
		populateCombo();
	}
	
	private void populateCombo() {
		if (relationshipMapping == null) {
			targetEntityCombo.clearSelection();
			return;
		}
		String targetEntity = this.relationshipMapping.getSpecifiedTargetEntity();
		this.targetEntityCombo.setItem(0, NLS.bind(JpaUiMappingsMessages.TargetEntityChooser_defaultWithOneParam, this.relationshipMapping.getDefaultTargetEntity()));
		if (targetEntity != null) {
			if (!this.targetEntityCombo.getText().equals(targetEntity)) {
				this.targetEntityCombo.setText(targetEntity);
			}
		}
		else {
			if (this.targetEntityCombo.getSelectionIndex() != 0) {
				this.targetEntityCombo.select(0);
			}
		}
	}
	
	@Override
	public Control getControl() {
		return this.composite;
	}	
	
	//see  org.eclipse.pde.internal.ui.editor.plugin.rows.ClassAttributeRow
	//for example of the hyperlink opening a resource
	protected Button buildTargetEntitySelectionButton(Composite parent) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(JpaUiMappingsMessages.TargetEntityChooser_browse);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browse();
			}
		});
		return button;
	}
	
	protected void browse() {
		BusyIndicator.showWhile(this.targetEntityCombo.getDisplay(), new Runnable() {
			public void run() {
				doOpenSelectionDialog();
			}
		});
	}

	private void doOpenSelectionDialog() {
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(getControl().getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createWorkspaceScope(),
					IJavaElementSearchConstants.CONSIDER_ALL_TYPES, 
			        false,
					""); //$NON-NLS-1$
		}
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		} 
		dialog.setTitle("Select Type"); //$NON-NLS-1$
		if (dialog.open() == Window.OK) {
			IType type = (IType) dialog.getResult()[0];
			this.targetEntityCombo.setText(type.getFullyQualifiedName('$'));
		}
	}
}

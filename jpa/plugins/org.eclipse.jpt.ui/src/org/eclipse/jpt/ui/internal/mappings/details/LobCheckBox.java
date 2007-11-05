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
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class LobCheckBox extends BaseJpaController
{
	private IBasic basicMapping;
	private Adapter basicMappingListener;
	
	private Button button;


	public LobCheckBox(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildBasicMappingListener();
	}
	
	private void buildBasicMappingListener() {
		this.basicMappingListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				basicMappingChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		this.button = getWidgetFactory().createButton(
						parent, 
						JptUiMappingsMessages.BasicGeneralSection_lobLabel,
						SWT.CHECK);
		
		this.button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				LobCheckBox.this.lobSelectionChanged();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
				LobCheckBox.this.lobSelectionChanged();
			}
		});
	}
	
	void lobSelectionChanged() {
		boolean lob = this.button.getSelection();
		if (this.basicMapping.isLob() != lob) {
			this.basicMapping.setLob(lob);
		}
	}

	private void basicMappingChanged(Notification notification) {
		// ui thread
		if (notification.getFeatureID(IBasic.class) == 
				JpaCoreMappingsPackage.IBASIC__LOB) {
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
		if (this.basicMapping != null) {
			this.basicMapping.eAdapters().add(this.basicMappingListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.basicMapping != null) {
			this.basicMapping.eAdapters().remove(this.basicMappingListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.basicMapping = (IBasic) obj;
		populateButton();
	}
	
	@Override
	protected void doPopulate() {
		populateButton();
	}
	
	private void populateButton() {
		boolean lob = false;
		if (this.basicMapping != null) {
			lob  = this.basicMapping.isLob();
		}
		
		if (this.button.getSelection() != lob) {
			this.button.setSelection(lob);
		}
	}

	
	@Override
	public Control getControl() {
		return this.button;
	}
}

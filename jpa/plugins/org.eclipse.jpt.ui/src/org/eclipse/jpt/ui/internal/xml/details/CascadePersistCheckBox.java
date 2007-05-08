/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CascadePersistCheckBox extends BaseJpaController
{
	private PersistenceUnitDefaults persistenceUnitDefaults;
	private Adapter persistenceUnitDefaultsListener;
	
	private Button button;


	public CascadePersistCheckBox(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildPeristenceUnitDefaultsListener();
	}
	
	private void buildPeristenceUnitDefaultsListener() {
		this.persistenceUnitDefaultsListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				persistenceUnitDefaultsChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		this.button = getWidgetFactory().createButton(
						parent, 
						JptUiXmlMessages.XMLEntityMappingsPage_CascadePersistCheckBox,
						SWT.CHECK);
		
		this.button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				CascadePersistCheckBox.this.cascadePersistSelectionChanged();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadePersistCheckBox.this.cascadePersistSelectionChanged();
			}
		});
	}
	
	void cascadePersistSelectionChanged() {
		boolean cascadePersist = this.button.getSelection();
		if (this.persistenceUnitDefaults.isCascadePersist() != cascadePersist) {
			this.persistenceUnitDefaults.setCascadePersist(cascadePersist);
		}
	}

	private void persistenceUnitDefaultsChanged(Notification notification) {
		if (notification.getFeatureID(PersistenceUnitDefaults.class) == 
				OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST) {
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
		if (this.persistenceUnitDefaults != null) {
			this.persistenceUnitDefaults.eAdapters().add(this.persistenceUnitDefaultsListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.persistenceUnitDefaults != null) {
			this.persistenceUnitDefaults.eAdapters().remove(this.persistenceUnitDefaultsListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.persistenceUnitDefaults = (PersistenceUnitDefaults) obj;
		populateButton();
	}
	
	@Override
	protected void doPopulate() {
		populateButton();
	}
	
	private void populateButton() {
		boolean cascadePersist = false;
		if (this.persistenceUnitDefaults != null) {
			cascadePersist  = this.persistenceUnitDefaults.isCascadePersist();
		}
		
		if (this.button.getSelection() != cascadePersist) {
			this.button.setSelection(cascadePersist);
		}
	}

	
	@Override
	public Control getControl() {
		return this.button;
	}
}

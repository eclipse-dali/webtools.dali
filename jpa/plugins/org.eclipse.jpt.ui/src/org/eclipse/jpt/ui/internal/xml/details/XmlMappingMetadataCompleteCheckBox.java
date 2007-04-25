/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.xml.JpaUiXmlMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlMappingMetadataCompleteCheckBox extends BaseJpaController
{
	private PersistenceUnitMetadata persistenceUnitMetadata;
	private Adapter persistenceUnitMetadataListener;
	
	private Button button;


	public XmlMappingMetadataCompleteCheckBox(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildPeristenceUnitMetadataListener();
	}
	
	private void buildPeristenceUnitMetadataListener() {
		this.persistenceUnitMetadataListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				persistenceUnitMetadataChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		this.button = getWidgetFactory().createButton(
						parent, 
						JpaUiXmlMessages.XMLEntityMappingsPage_XmlMappingMetadataCompleteCheckBox,
						SWT.CHECK);
		
		this.button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				XmlMappingMetadataCompleteCheckBox.this.xmlMappingMetadataCompleteSelectionChanged();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
				XmlMappingMetadataCompleteCheckBox.this.xmlMappingMetadataCompleteSelectionChanged();
			}
		});
	}
	
	void xmlMappingMetadataCompleteSelectionChanged() {
		boolean xmlMappingMetadataComplete = this.button.getSelection();
		if (this.persistenceUnitMetadata.isXmlMappingMetadataComplete() != xmlMappingMetadataComplete) {
			this.persistenceUnitMetadata.setXmlMappingMetadataComplete(xmlMappingMetadataComplete);
		}
	}

	private void persistenceUnitMetadataChanged(Notification notification) {
		if (notification.getFeatureID(PersistenceUnitMetadata.class) == 
				OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE) {
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
		if (this.persistenceUnitMetadata != null) {
			this.persistenceUnitMetadata.eAdapters().add(this.persistenceUnitMetadataListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.persistenceUnitMetadata != null) {
			this.persistenceUnitMetadata.eAdapters().remove(this.persistenceUnitMetadataListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.persistenceUnitMetadata = (PersistenceUnitMetadata) obj;
		populateButton();
	}
	
	@Override
	protected void doPopulate() {
		populateButton();
	}
	
	private void populateButton() {
		boolean xmlMappingMetadataComplete = false;
		if (this.persistenceUnitMetadata != null) {
			xmlMappingMetadataComplete  = this.persistenceUnitMetadata.isXmlMappingMetadataComplete();
		}
		
		if (this.button.getSelection() != xmlMappingMetadataComplete) {
			this.button.setSelection(xmlMappingMetadataComplete);
		}
	}

	
	@Override
	public Control getControl() {
		return this.button;
	}
}

/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlMappingMetadataCompleteCheckBox extends BaseJpaController<PersistenceUnitMetadata>
{
	private Button button;
	private PersistenceUnitMetadata persistenceUnitMetadata;
	private Adapter persistenceUnitMetadataListener;

	public XmlMappingMetadataCompleteCheckBox(PropertyValueModel<? extends PersistenceUnitMetadata> subjectHolder,
	                                          Composite parent,
	                                          TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		buildPeristenceUnitMetadataListener();
	}

	private void buildPeristenceUnitMetadataListener() {
		this.persistenceUnitMetadataListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistenceUnitMetadataChanged(notification);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.button = getWidgetFactory().createButton(
			container,
			JptUiXmlMessages.XMLEntityMappingsPage_XmlMappingMetadataCompleteCheckBox,
			SWT.CHECK);

		this.button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				XmlMappingMetadataCompleteCheckBox.this.xmlMappingMetadataCompleteSelectionChanged();
			}

			public void widgetSelected(SelectionEvent event) {
				XmlMappingMetadataCompleteCheckBox.this.xmlMappingMetadataCompleteSelectionChanged();
			}
		});
	}

	@Override
	protected void disengageListeners() {
		super.disengageListeners();
//		if (this.persistenceUnitMetadata != null) {
//			this.persistenceUnitMetadata.eAdapters().remove(this.persistenceUnitMetadataListener);
//		}
	}

	@Override
	public void doPopulate() {
		super.doPopulate();
		populateButton();
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.persistenceUnitMetadata != null) {
//			this.persistenceUnitMetadata.eAdapters().add(this.persistenceUnitMetadataListener);
//		}
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

	private void populateButton() {
		boolean xmlMappingMetadataComplete = false;
		if (this.persistenceUnitMetadata != null) {
			xmlMappingMetadataComplete  = this.persistenceUnitMetadata.isXmlMappingMetadataComplete();
		}

		if (this.button.getSelection() != xmlMappingMetadataComplete) {
			this.button.setSelection(xmlMappingMetadataComplete);
		}
	}

	void xmlMappingMetadataCompleteSelectionChanged() {
		boolean xmlMappingMetadataComplete = this.button.getSelection();
		if (this.persistenceUnitMetadata.isXmlMappingMetadataComplete() != xmlMappingMetadataComplete) {
			this.persistenceUnitMetadata.setXmlMappingMetadataComplete(xmlMappingMetadataComplete);
		}
	}
}
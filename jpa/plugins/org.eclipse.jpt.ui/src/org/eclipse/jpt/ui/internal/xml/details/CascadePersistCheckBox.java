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
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
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

public class CascadePersistCheckBox extends BaseJpaController<PersistenceUnitDefaults>
{
	private Adapter persistenceUnitDefaultsListener;
	private Button button;

	public CascadePersistCheckBox(PropertyValueModel<? extends PersistenceUnitDefaults> subjectHolder,
	                              Composite parent,
	                              TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		buildPeristenceUnitDefaultsListener();
	}

	private void buildPeristenceUnitDefaultsListener() {
		this.persistenceUnitDefaultsListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistenceUnitDefaultsChanged(notification);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.button = getWidgetFactory().createButton(
			container,
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
		if (this.subject().isCascadePersist() != cascadePersist) {
			this.subject().setCascadePersist(cascadePersist);
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.subject() != null) {
//			this.subject().eAdapters().add(this.persistenceUnitDefaultsListener);
//		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();
//		if (this.subject() != null) {
//			this.subject().eAdapters().remove(this.persistenceUnitDefaultsListener);
//		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doPopulate() {
		super.doPopulate();
		populateButton();
	}

	private void populateButton() {
		boolean cascadePersist = false;
		if (this.subject() != null) {
			cascadePersist  = this.subject().isCascadePersist();
		}

		if (this.button.getSelection() != cascadePersist) {
			this.button.setSelection(cascadePersist);
		}
	}
}
/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
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

public class LobCheckBox extends BaseJpaController<IBasicMapping>
{
	private Adapter basicMappingListener;
	private Button button;

	public LobCheckBox(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
		buildBasicMappingListener();
	}

	private void bsaicMappingChanged(Notification notification) {
		if (notification.getFeatureID(IBasicMapping.class) ==
				JpaCoreMappingsPackage.IBASIC__LOB) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						populate();
					}
				});
		}
	}

	private void buildBasicMappingListener() {
		this.basicMappingListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				bsaicMappingChanged(notification);
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
			public void widgetDefaultSelected(SelectionEvent e) {
				LobCheckBox.this.lobSelectionChanged();
			}

			public void widgetSelected(SelectionEvent event) {
				LobCheckBox.this.lobSelectionChanged();
			}
		});
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().remove(this.basicMappingListener);
		}
	}

	@Override
	protected void doPopulate() {
		populateButton();
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(this.basicMappingListener);
		}
	}

	@Override
	public Control getControl() {
		return this.button;
	}

	void lobSelectionChanged() {
		boolean lob = this.button.getSelection();
		if (this.subject().isLob() != lob) {
			this.subject().setLob(lob);
		}
	}

	private void populateButton() {
		boolean lob = false;
		if (this.subject() != null) {
			lob  = this.subject().isLob();
		}

		if (this.button.getSelection() != lob) {
			this.button.setSelection(lob);
		}
	}
}
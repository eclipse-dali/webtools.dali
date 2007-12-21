/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 *
 */
public class OrderingComposite extends BaseJpaComposite<IMultiRelationshipMapping> {

	private Button customOrderingRadioButton;
	private Adapter mappingListener;
	private Button noOrderingRadioButton;
	private ITextViewer orderingTextViewer;
	private Button primaryKeyOrderingRadioButton;

	// short circuit flag for user typing
	private boolean updatingCustomOrderBy = false;

	public OrderingComposite(PropertyValueModel<? extends IMultiRelationshipMapping> subjectHolder,
	                         Composite parent,
	                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		mappingListener = buildMappingListener();
	}

	private Button buildCustomOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent,
			JptUiMappingsMessages.OrderByComposite_customOrdering,
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderingComposite.this.customOrderingRadioButtonSelected(e);
			}
		});

		return button;
	}

	private Adapter buildMappingListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				mappingChanged(notification);
			}
		};
	}

	private Button buildNoOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent,
			JptUiMappingsMessages.OrderByComposite_noOrdering,
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderingComposite.this.noOrderingRadioButtonSelected(e);
			}
		});

		return button;
	}

	private ITextViewer buildOrderByTestViewer(Composite parent) {
		final TextViewer textViewer = new TextViewer(parent, SWT.SINGLE | SWT.BORDER);
		textViewer.setDocument(new Document());
		textViewer.addTextListener(new ITextListener() {
			public void textChanged(TextEvent event) {
				orderingTextViewerChanged();
			}
		});
		return textViewer;
	}

	private Button buildPrimaryKeyOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent,
			JptUiMappingsMessages.OrderByComposite_primaryKeyOrdering,
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderingComposite.this.primaryKeyOrderingRadioButtonSelected(e);
			}
		});
		return button;
	}

	void customOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		setTextViewerEnabled(true);
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().remove(mappingListener);
		}
	}

	@Override
	protected void doPopulate() {
		if (this.subject() != null) {
			populateOrderingRadioButtons();
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(mappingListener);
		}
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		Group orderByGroup = getWidgetFactory().createGroup(composite, JptUiMappingsMessages.OrderByComposite_orderByGroup);
		orderByGroup.setLayout(new GridLayout(1, false));
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		orderByGroup.setLayoutData(gridData);
		helpSystem.setHelp(orderByGroup, IJpaHelpContextIds.MAPPING_ORDER_BY);


		this.noOrderingRadioButton = buildNoOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.noOrderingRadioButton.setLayoutData(gridData);
//		helpSystem().setHelp(this.noOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING);


		this.primaryKeyOrderingRadioButton = buildPrimaryKeyOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.primaryKeyOrderingRadioButton.setLayoutData(gridData);
//		helpSystem().setHelp(this.primaryKeyOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING);

		this.customOrderingRadioButton = buildCustomOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.customOrderingRadioButton.setLayoutData(gridData);
//		helpSystem().setHelp(this.customOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING);


		this.orderingTextViewer = buildOrderByTestViewer(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = 15;
		this.orderingTextViewer.getTextWidget().setLayoutData(gridData);
		helpSystem.setHelp(this.orderingTextViewer.getTextWidget(), IJpaHelpContextIds.MAPPING_ORDER_BY);
	}

	private void mappingChanged(Notification notification) {
		if (notification.getFeatureID(IMultiRelationshipMapping.class) == JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					populate();
				}
			});
		}
	}

	void noOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		if (this.subject().isNoOrdering()) {
			return;
		}
		this.subject().setNoOrdering();
	}

	private void orderingTextViewerChanged() {
		if (isPopulating()) {
			return;
		}
		String orderByValue = this.orderingTextViewer.getDocument().get();
		if (orderByValue.equals(this.subject().getOrderBy())) {
			return;
		}

		this.updatingCustomOrderBy = true;
		this.subject().setOrderBy(orderByValue);
	}

	private void populateOrderingRadioButtons() {
		// short circuit if user is typing
		if (updatingCustomOrderBy) {
			updatingCustomOrderBy = false;
			return;
		}

		if (this.subject().isNoOrdering()) {
			this.primaryKeyOrderingRadioButton.setSelection(false);
			this.customOrderingRadioButton.setSelection(false);
			this.noOrderingRadioButton.setSelection(true);
			if (! "".equals(this.orderingTextViewer.getDocument().get())) {
				this.orderingTextViewer.getDocument().set("");
			}
		}
		else if (this.subject().isOrderByPk()) {
			this.customOrderingRadioButton.setSelection(false);
			this.noOrderingRadioButton.setSelection(false);
			this.primaryKeyOrderingRadioButton.setSelection(true);
//			String value = this.ordering.getValue();
//			if (value != null && !value.equals(this.orderingTextViewer.getDocument().get())) {
//				this.orderingTextViewer.getDocument().set(value);
//			}
//			else {
				if (!"".equals(this.orderingTextViewer.getDocument().get())) {
					this.orderingTextViewer.getDocument().set("");
				}
//			}
		}
		else if (this.subject().isCustomOrdering()) {
			this.noOrderingRadioButton.setSelection(false);
			this.primaryKeyOrderingRadioButton.setSelection(false);
			this.customOrderingRadioButton.setSelection(true);
			String value = this.subject().getOrderBy();
			if (value != null && !value.equals(this.orderingTextViewer.getDocument().get())) {
				this.orderingTextViewer.getDocument().set(value);
			}
		}

		setTextViewerEnabled(this.subject().isCustomOrdering());
	}

	void primaryKeyOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		if (! this.subject().isOrderByPk()) {
			this.subject().setOrderByPk();
		}
		setTextViewerEnabled(false);
	}

	private void setTextViewerEnabled(boolean enabled) {
		this.orderingTextViewer.setEditable(enabled);
		this.orderingTextViewer.getTextWidget().setEnabled(enabled);
	}
}
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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jpt.core.internal.mappings.IOrderBy;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.OrderingType;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
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
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 *
 */
public class OrderByComposite extends BaseJpaComposite  {

	private IOrderBy orderBy;
	private Adapter orderingListener;
	
	private Button noOrderingRadioButton;
	private Button primaryKeyOrderingRadioButton;
	private Button customOrderingRadioButton;
	
	private ITextViewer orderingTextViewer;
	

	public OrderByComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, commandStack, widgetFactory);
		this.orderingListener = buildOrderingListener();
	}

	private Adapter buildOrderingListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				orderByChanged(notification);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		Group orderByGroup = getWidgetFactory().createGroup(composite, JpaUiMappingsMessages.OrderByComposite_orderByGroup);
		orderByGroup.setLayout(new GridLayout(1, false));
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		orderByGroup.setLayoutData(gridData);

		this.noOrderingRadioButton = buildNoOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.noOrderingRadioButton.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.noOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING);


		this.primaryKeyOrderingRadioButton = buildPrimaryKeyOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.primaryKeyOrderingRadioButton.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.primaryKeyOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING);

		this.customOrderingRadioButton = buildCustomOrderingRadioButton(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.customOrderingRadioButton.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.customOrderingRadioButton, IJpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING);

		
		this.orderingTextViewer = buildOrderByTestViewer(orderByGroup);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = 15;
		this.orderingTextViewer.getTextWidget().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.orderingTextViewer.getTextWidget(), IJpaHelpContextIds.MAPPING_ORDER_BY);
	}
	
	private Button buildNoOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent, 
			JpaUiMappingsMessages.OrderByComposite_noOrdering, 
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderByComposite.this.noOrderingRadioButtonSelected(e);
			}
		});

		return button;
	}

	void noOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		if (this.orderBy.getType() == OrderingType.NONE) {
			return;
		}
		this.orderBy.setType(OrderingType.NONE);
		this.orderBy.setValue(null);
	}
	
	private Button buildPrimaryKeyOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent, 
			JpaUiMappingsMessages.OrderByComposite_primaryKeyOrdering, 
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderByComposite.this.primaryKeyOrderingRadioButtonSelected(e);
			}
		});
		return button;
	}
	
	void primaryKeyOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		if (this.orderBy.getType() == OrderingType.PRIMARY_KEY) {
			return;
		}
		this.orderBy.setType(OrderingType.PRIMARY_KEY);
	}

	private Button buildCustomOrderingRadioButton(Composite parent) {
		Button button = getWidgetFactory().createButton(
			parent, 
			JpaUiMappingsMessages.OrderByComposite_customOrdering, 
			SWT.RADIO);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
			public void widgetSelected(SelectionEvent e) {
				OrderByComposite.this.customOrderingRadioButtonSelected(e);
			}
		});

		return button;
	}
	
	void customOrderingRadioButtonSelected(SelectionEvent e) {
		if (!((Button) e.widget).getSelection()) {
			//ignore case where radio button is deselected
			return;
		}
		if (this.orderBy.getType() == OrderingType.CUSTOM) {
			return;
		}
		this.orderBy.setType(OrderingType.CUSTOM);
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
	
	private void orderingTextViewerChanged() {
		if (isPopulating()) {
			return;
		}
		String orderByValue = this.orderingTextViewer.getDocument().get();
		if (orderByValue.equals(this.orderBy.getValue())) {
			return;
		}
		this.orderBy.setValue(orderByValue);
	}

	private void orderByChanged(Notification notification) {
		if (notification.getFeatureID(IOrderBy.class) == JpaCoreMappingsPackage.IORDER_BY__VALUE
			|| notification.getFeatureID(IOrderBy.class) == JpaCoreMappingsPackage.IORDER_BY__TYPE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					populate();
				}
			});
		}		
	}

	@Override
	protected void doPopulate(EObject obj) {
		this.orderBy = (IOrderBy) obj;
		if (this.orderBy == null) {
			return;
		}
		populateOrderingRadioButtons();		
	}
	
	@Override
	protected void doPopulate() {
		populateOrderingRadioButtons();		
	}

	@Override
	protected void engageListeners() {
		if (this.orderBy != null) {
			this.orderBy.eAdapters().add(this.orderingListener);
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.orderBy != null) {
			this.orderBy.eAdapters().remove(this.orderingListener);
		}
	}
	
	private void populateOrderingRadioButtons() {
		if (this.orderBy.getType() == OrderingType.NONE) {
			this.primaryKeyOrderingRadioButton.setSelection(false);			
			this.customOrderingRadioButton.setSelection(false);
			this.noOrderingRadioButton.setSelection(true);
			if (!"".equals(this.orderingTextViewer.getDocument().get())) {
				this.orderingTextViewer.getDocument().set("");
			}
		}
		else if (this.orderBy.getType() == OrderingType.PRIMARY_KEY) {
			this.customOrderingRadioButton.setSelection(false);
			this.noOrderingRadioButton.setSelection(false);
			this.primaryKeyOrderingRadioButton.setSelection(true);			
			String value = this.orderBy.getValue();
			if (value != null && !value.equals(this.orderingTextViewer.getDocument().get())) {
				this.orderingTextViewer.getDocument().set(value);
			}
			else {
				if (!"".equals(this.orderingTextViewer.getDocument().get())) {
					this.orderingTextViewer.getDocument().set("");				
				}
			}
		}
		else if (this.orderBy.getType() == OrderingType.CUSTOM) {
			this.noOrderingRadioButton.setSelection(false);
			this.primaryKeyOrderingRadioButton.setSelection(false);			
			this.customOrderingRadioButton.setSelection(true);
			String value = this.orderBy.getValue();
			if (value != null && !value.equals(this.orderingTextViewer.getDocument().get())) {
				this.orderingTextViewer.getDocument().set(value);
			}
		}
		
		this.orderingTextViewer.setEditable(this.orderBy.getType() == OrderingType.CUSTOM);
		this.orderingTextViewer.getTextWidget().setEnabled(this.orderBy.getType() == OrderingType.CUSTOM);
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.InheritanceType;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class InheritanceComposite extends BaseJpaComposite {
	
	private IEntity entity;
	private final Adapter entityListener;
	
	private CComboViewer strategyViewer;
	private DiscriminatorColumnComposite discriminatorColumnComposite;
	private CCombo discriminatorValueCombo;
	
	private PrimaryKeyJoinColumnsComposite pkJoinColumnsComposite;
	
	public InheritanceComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.entityListener = buildEntityListener();
	}
	
	private Adapter buildEntityListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				entityChanged(notification);
			}
		};
	}
	
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);	

		GridData gridData;

		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.InheritanceComposite_strategy);

		this.strategyViewer = buildStrategyCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.strategyViewer.getCombo().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.strategyViewer.getCombo(), IJpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY);

		this.discriminatorColumnComposite = new DiscriminatorColumnComposite(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.discriminatorColumnComposite.getControl().setLayoutData(gridData);
	
		
		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.InheritanceComposite_discriminatorValue);

		this.discriminatorValueCombo = buildDiscriminatorValueCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.discriminatorValueCombo.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.discriminatorValueCombo, IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE);
		
		this.pkJoinColumnsComposite = new PrimaryKeyJoinColumnsComposite(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		this.pkJoinColumnsComposite.getControl().setLayoutData(gridData);
	}
	
	private CComboViewer buildStrategyCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		CComboViewer strategyViewer = new CComboViewer(combo);
		strategyViewer.add(InheritanceType.VALUES.toArray());
		
		strategyViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				InheritanceComposite.this.strategySelectionChanged(event.getSelection());
			}
		});
		
		return strategyViewer;
	}
	
	void strategySelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			InheritanceType inheritanceType = (InheritanceType) ((IStructuredSelection) selection).getFirstElement();
			if ( ! this.entity.getInheritanceStrategy().equals(inheritanceType)) {
				this.entity.setInheritanceStrategy(inheritanceType);
			}
		}
	}
	
	
	private CCombo buildDiscriminatorValueCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String discriminatorValue = entity.getSpecifiedDiscriminatorValue();
				String value = ((CCombo) e.getSource()).getText();
				if (value.equals("")) { //$NON-NLS-1$
					value = null;
					if (discriminatorValue == null || discriminatorValue.equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if (value != null && combo.getItemCount() > 0 && value.equals(combo.getItem(0))) {
					value = null;
				}

				if (discriminatorValue == null || !discriminatorValue.equals(value)) {
					entity.setSpecifiedDiscriminatorValue(value);
				}
			}
		});
		return combo;
	}

	public void doPopulate(EObject obj) {
		this.entity = (IEntity) obj;
		if (this.entity != null) {
			this.discriminatorColumnComposite.populate(this.entity.getDiscriminatorColumn());		
			populateStrategyComboViewer();
			populateDiscriminatorValueCombo();
			this.pkJoinColumnsComposite.populate(this.entity);	
		}
		else {
			this.discriminatorColumnComposite.populate(null);					
		}
	}
	
	public void doPopulate() {
		if (this.entity != null) {
			this.discriminatorColumnComposite.populate();
//			popuplateStrategyComboViewer();
//			popuplateDiscriminatorValueCombo();
		}
	}

	@Override
	protected void engageListeners() {
		if (this.entity != null) {
			this.entity.eAdapters().add(this.entityListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.entity != null) {
			this.entity.eAdapters().remove(this.entityListener);
		}
	}

	private void populateStrategyComboViewer() {
		if (this.entity.getInheritanceStrategy() == InheritanceType.DEFAULT) {
			if (((StructuredSelection) this.strategyViewer.getSelection()).getFirstElement() != InheritanceType.DEFAULT) {
				this.strategyViewer.setSelection(new StructuredSelection(InheritanceType.DEFAULT));
			}
		}
		else if (this.entity.getInheritanceStrategy() == InheritanceType.JOINED) {
			if (((StructuredSelection) this.strategyViewer.getSelection()).getFirstElement() != InheritanceType.JOINED) {
				this.strategyViewer.setSelection(new StructuredSelection(InheritanceType.JOINED));
			}
		}
		else if (this.entity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
			if (((StructuredSelection) this.strategyViewer.getSelection()).getFirstElement() != InheritanceType.SINGLE_TABLE) {
				this.strategyViewer.setSelection(new StructuredSelection(InheritanceType.SINGLE_TABLE));
			}
		}
		else {
			if (((StructuredSelection) this.strategyViewer.getSelection()).getFirstElement() != InheritanceType.TABLE_PER_CLASS) {
				this.strategyViewer.setSelection(new StructuredSelection(InheritanceType.TABLE_PER_CLASS));
			}
		}		
	}

	private void populateDiscriminatorValueCombo() {
		String specifiedValue = this.entity.getSpecifiedDiscriminatorValue();
		String defaultValue = this.entity.getDefaultDiscriminatorValue();

		if (this.entity.discriminatorValueIsAllowed()) {
			this.discriminatorValueCombo.setEnabled(true);
			if (this.discriminatorValueCombo.getItemCount() == 0) {
				this.discriminatorValueCombo.add(JpaUiMappingsMessages.DiscriminatorColumnComposite_defaultEmpty);
			}
			if (defaultValue != null) {
				this.discriminatorValueCombo.setItem(0, NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultValue));
			}
			else {
				this.discriminatorValueCombo.setItem(0, JpaUiMappingsMessages.DiscriminatorColumnComposite_defaultEmpty);
			}
		}
		else {
			this.discriminatorValueCombo.setEnabled(false);
			if (this.discriminatorValueCombo.getItemCount() == 1) {
				this.discriminatorValueCombo.setText("");
				this.discriminatorValueCombo.removeAll();
			}
		}
			
		if (specifiedValue != null) {
			if (!this.discriminatorValueCombo.getText().equals(specifiedValue)) {
				this.discriminatorValueCombo.setText(specifiedValue);
			}
		}
		else {
			if (this.discriminatorValueCombo.getSelectionIndex() != 0) {
				this.discriminatorValueCombo.select(0);
			}
		}
	}
	
	private void entityChanged(Notification notification) {	
		if (notification.getFeatureID(IEntity.class) == JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY) {
			final InheritanceType inheritanceType = (InheritanceType) notification.getNewValue();
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (((StructuredSelection) strategyViewer.getSelection()).getFirstElement() != inheritanceType) {
						strategyViewer.setSelection(new StructuredSelection(inheritanceType));
					}					
				}
			});
		}
		else if (notification.getFeatureID(IEntity.class) == JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE) {
			final String columnName = (String) notification.getNewValue();
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (discriminatorValueCombo.getText() == null || !discriminatorValueCombo.getText().equals(columnName)) {
						if (columnName == null) {
							discriminatorValueCombo.select(0);
						}
						else {
							discriminatorValueCombo.setText(columnName);
						}
					}			
				}
			});
		}
		else if (notification.getFeatureID(IEntity.class) == JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					populateDiscriminatorValueCombo();
				}
			});
		}
	}

	public void dispose() {
		this.discriminatorColumnComposite.dispose();
		this.pkJoinColumnsComposite.dispose();
		super.dispose();
	}
}

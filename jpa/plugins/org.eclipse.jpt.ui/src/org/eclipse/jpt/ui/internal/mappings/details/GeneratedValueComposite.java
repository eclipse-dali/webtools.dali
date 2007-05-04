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

import java.util.Iterator;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.mappings.GenerationType;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.IGeneratorRepository;
import org.eclipse.jpt.core.internal.platform.NullGeneratorRepository;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class GeneratedValueComposite extends BaseJpaComposite
{
	private IId id;
	private IGeneratedValue generatedValue;
	private Adapter generatedValueListener;

	private CComboViewer strategyComboViewer;

	private CCombo generatorNameCombo;

	protected boolean populating;

	public GeneratedValueComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.generatedValueListener = buildGeneratedValueListener();
	}
	
	private Adapter buildGeneratedValueListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				generatedValueChanged(notification);
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.GeneratedValueComposite_strategy);
		
		this.strategyComboViewer = buildStrategyComboViewer(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.strategyComboViewer.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(this.strategyComboViewer.getCombo(), IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY);
		
		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.GeneratedValueComposite_generatorName);
		
		this.generatorNameCombo = buildGeneratorNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.generatorNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.generatorNameCombo, IJpaHelpContextIds.MAPPING_GENERATED_VALUE_GENERATOR_NAME);
		
		// TODO
		// buildGeneratorNameSelectionButton( this);
	}

	private CComboViewer buildStrategyComboViewer(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		CComboViewer viewer = new CComboViewer(combo);

		viewer.add(GenerationType.VALUES.toArray());
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (populating) {
					return;
				}
				if (event.getSelection() instanceof StructuredSelection) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					GenerationType selectedType = (GenerationType) selection.getFirstElement();
					if (generatedValue == null) {
						createGeneratedValue();
					}
					if (!generatedValue.getStrategy().equals(selectedType)) {
						generatedValue.setStrategy(selectedType);
					}
				}
			}
		});
		return viewer;
	}

	protected CCombo buildGeneratorNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JpaUiMappingsMessages.TableComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String generatorName = ((CCombo) e.getSource()).getText();
				
				if (generatorName.equals("")) { //$NON-NLS-1$
					if (generatedValue.getGenerator() == null || generatedValue.getGenerator().equals("")) {
						return;
					}
					generatorName = null;
				}
				if (generatedValue == null) {
					createGeneratedValue();
				}
				generatedValue.setGenerator(generatorName);
			}
		});
		return combo;
	}

	private void createGeneratedValue() {
		IGeneratedValue generatedValue = this.id.createGeneratedValue();
		this.id.setGeneratedValue(generatedValue);
	}
	

	protected void generatedValueChanged(Notification notification) {
		if (notification.getFeatureID(IGeneratedValue.class) == JpaCoreMappingsPackage.IGENERATED_VALUE__STRATEGY) {
			final GenerationType strategy = (GenerationType) notification.getNewValue();
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					if (selectedStrategy() != strategy) {
						strategyComboViewer.setSelection(new StructuredSelection(strategy));
					}
				}
			});
		}
		else if (notification.getFeatureID(IGeneratedValue.class) == JpaCoreMappingsPackage.IGENERATED_VALUE__GENERATOR) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateGeneratorName();
				}
			});
		}
	}

	@Override
	protected void doPopulate(EObject obj) {
		this.id = (IId) obj;
		if (this.id == null) {
			this.generatedValue= null;
		}
		else {
			this.generatedValue = this.id.getGeneratedValue();
		}
		if (this.generatedValue == null) {
			this.strategyComboViewer.getCombo().deselectAll();
			this.populating = false;
			return;
		}
		populateStrategyCombo();
		populateGeneratorNameCombo();
	}

	@Override
	protected void doPopulate() {
		
	}
	
	protected void engageListeners() {
		if (this.generatedValue != null) {
			this.generatedValue.eAdapters().add(this.generatedValueListener);
		}
	}

	protected void disengageListeners() {
		if (this.generatedValue != null) {
			this.generatedValue.eAdapters().remove(this.generatedValueListener);
		}
	}

	private IGeneratorRepository getGeneratorRepository() {
		return NullGeneratorRepository.instance(); //this.id.getJpaProject().getPlatform().generatorRepository(this.id.typeMapping().getPersistentType());
	}
	
	private void populateGeneratorNameCombo() {
		this.generatorNameCombo.removeAll();
		for (Iterator<String> i = getGeneratorRepository().generatorNames(); i.hasNext(); ){
			this.generatorNameCombo.add(i.next());
		}

		populateGeneratorName();
	}
	private void populateGeneratorName() {
		String generatorName = this.generatedValue.getGenerator();
		if (generatorName == null || generatorName.equals("")) {
			this.generatorNameCombo.clearSelection();
		}
		else if (!this.generatorNameCombo.getText().equals(generatorName)) {
			this.generatorNameCombo.setText(generatorName);
		}
	}
	
	private void populateStrategyCombo() {
		GenerationType selectedType = selectedStrategy();
		GenerationType strategy = this.generatedValue.getStrategy();
		if (strategy == GenerationType.AUTO) {
			if (selectedType != GenerationType.AUTO) {
				this.strategyComboViewer.setSelection(new StructuredSelection(GenerationType.AUTO));
			}
		}
		else if (strategy == GenerationType.SEQUENCE) {
			if (selectedType != GenerationType.SEQUENCE) {
				this.strategyComboViewer.setSelection(new StructuredSelection(GenerationType.SEQUENCE));
			}
		}
		else if (strategy == GenerationType.IDENTITY) {
			if (selectedType != GenerationType.IDENTITY) {
				this.strategyComboViewer.setSelection(new StructuredSelection(GenerationType.IDENTITY));
			}
		}
		else if (strategy == GenerationType.TABLE) {
			if (selectedType != GenerationType.TABLE) {
				this.strategyComboViewer.setSelection(new StructuredSelection(GenerationType.TABLE));
			}
		}
		else {
			if (selectedType != GenerationType.DEFAULT) {
				this.strategyComboViewer.setSelection(new StructuredSelection(GenerationType.DEFAULT));
			}
		}
		// TODO first initialization
	}

	private GenerationType selectedStrategy() {
		return (GenerationType) ((StructuredSelection) this.strategyComboViewer.getSelection()).getFirstElement();
	}
}

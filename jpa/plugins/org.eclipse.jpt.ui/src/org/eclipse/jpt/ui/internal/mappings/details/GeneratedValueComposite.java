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
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.mappings.GenerationType;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
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
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class GeneratedValueComposite extends BaseJpaComposite
{
	private IIdMapping id;
	private IGeneratedValue generatedValue;
	private Adapter generatedValueListener;

	private ComboViewer strategyComboViewer;

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
		
		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.GeneratedValueComposite_strategy);
		
		this.strategyComboViewer = buildStrategyComboViewer(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.strategyComboViewer.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(this.strategyComboViewer.getCombo(), IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY);
		
		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.GeneratedValueComposite_generatorName);
		
		this.generatorNameCombo = buildGeneratorNameCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.generatorNameCombo.setLayoutData(gridData);
		helpSystem.setHelp(this.generatorNameCombo, IJpaHelpContextIds.MAPPING_GENERATED_VALUE_GENERATOR_NAME);
		
		// TODO
		// buildGeneratorNameSelectionButton( this);
	}

	private ComboViewer buildStrategyComboViewer(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setLabelProvider(buildStrategyLabelProvider());
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
	
	private IBaseLabelProvider buildStrategyLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == GenerationType.DEFAULT) {
					//TODO need to move this to the model, don't want hardcoded String
					return NLS.bind(JptUiMappingsMessages.GeneratedValueComposite_default, "Auto");
				}
				return super.getText(element);
			}
		};
	}
	

	protected CCombo buildGeneratorNameCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		combo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
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
		this.generatedValue = this.id.createGeneratedValue();
		this.id.setGeneratedValue(this.generatedValue);
	}
	

	protected void generatedValueChanged(Notification notification) {
		if (notification.getFeatureID(IGeneratedValue.class) == JpaCoreMappingsPackage.IGENERATED_VALUE__STRATEGY) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					if (selectedStrategy() != generatedValue.getStrategy()) {
						strategyComboViewer.setSelection(new StructuredSelection(generatedValue.getStrategy()));
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
		this.id = (IIdMapping) obj;
		if (this.id == null) {
			this.generatedValue= null;
		}
		else {
			this.generatedValue = this.id.getGeneratedValue();
		}
		if (this.generatedValue == null) {
			this.strategyComboViewer.getCombo().setText("");
			this.generatorNameCombo.setText("");
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
			this.generatorNameCombo.setText("");
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

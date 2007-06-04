/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class IdComposite extends BaseJpaComposite 
{
	private IId id;
	private Adapter idListener;
	
	private ColumnComposite columnComposite;

	private EnumComboViewer temporalTypeViewer;
	
	private Section pkGenerationSection;
	private Button primaryKeyGenerationCheckBox;
	private GeneratedValueComposite generatedValueComposite;
	
	private Section tableGenSection;
	private Button tableGeneratorCheckBox;
	private TableGeneratorComposite tableGeneratorComposite;
	
	private Section sequenceGenSection;
	private Button sequenceGeneratorCheckBox;
	private SequenceGeneratorComposite sequenceGeneratorComposite;

	
	public IdComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.idListener = buildIdListener();
	}
	
	private Adapter buildIdListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				idMappingChanged(notification);
			}
		};
	}
	void idMappingChanged(Notification notification) {
		switch (notification.getFeatureID(IId.class)) {
			case JpaCoreMappingsPackage.IID__TABLE_GENERATOR :
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						IdComposite.this.populateTableGeneratorComposite();
					}
				});
				break;
			case JpaCoreMappingsPackage.IID__SEQUENCE_GENERATOR :
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						IdComposite.this.populateSequenceGeneratorComposite();
					}
				});
				break;
			case JpaCoreMappingsPackage.IID__GENERATED_VALUE :
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						IdComposite.this.populateGeneratedValueComposite();
					}
				});
				break;
		}
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		
		Control generalControl = buildGeneralComposite(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		generalControl.setLayoutData(gridData);

		Control generationControl = buildGenerationComposite(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		generationControl.setLayoutData(gridData);
	}
	
	private Control buildGeneralComposite(Composite composite) {
		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);	
		
		this.columnComposite = new ColumnComposite(generalComposite, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.columnComposite.getControl().setLayoutData(gridData);	

		CommonWidgets.buildTemporalLabel(generalComposite, getWidgetFactory());
		this.temporalTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.temporalTypeViewer.getControl().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(temporalTypeViewer.getControl(), IJpaHelpContextIds.MAPPING_TEMPORAL);

		return generalComposite;
	}

	private Control buildGenerationComposite(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		this.pkGenerationSection = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    this.pkGenerationSection.setText(JptUiMappingsMessages.IdMappingComposite_primaryKeyGeneration);

		Composite generationClient = getWidgetFactory().createComposite(this.pkGenerationSection);
		this.pkGenerationSection.setClient(generationClient);
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		generationClient.setLayout(layout);

		this.primaryKeyGenerationCheckBox = buildPrimaryKeyGenerationCheckBox(generationClient);
		GridData gridData = new GridData();
		this.primaryKeyGenerationCheckBox.setLayoutData(gridData);
		helpSystem.setHelp(primaryKeyGenerationCheckBox, IJpaHelpContextIds.MAPPING_PRIMARY_KEY_GENERATION);
		
		this.generatedValueComposite = new GeneratedValueComposite(generationClient, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = 20;
		this.generatedValueComposite.getControl().setLayoutData(gridData);
		
	    this.tableGenSection = getWidgetFactory().createSection(generationClient, SWT.FLAT | ExpandableComposite.TWISTIE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.tableGenSection.setLayoutData(gridData);
		
	    this.tableGenSection.setText(JptUiMappingsMessages.IdMappingComposite_tableGenerator);

		Composite tableGenClient = getWidgetFactory().createComposite(this.tableGenSection);
		this.tableGenSection.setClient(tableGenClient);
		
		layout = new GridLayout();
		layout.marginWidth = 0;
		tableGenClient.setLayout(layout);
		
		this.tableGeneratorCheckBox = buildTableGeneratorCheckBox(tableGenClient);
		gridData = new GridData();
		this.tableGeneratorCheckBox.setLayoutData(gridData);
		helpSystem.setHelp(tableGeneratorCheckBox, IJpaHelpContextIds.MAPPING_TABLE_GENERATOR);
		
		this.tableGeneratorComposite = new TableGeneratorComposite(tableGenClient, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = 20;
		this.tableGeneratorComposite.getControl().setLayoutData(gridData);
		

		
	    this.sequenceGenSection = getWidgetFactory().createSection(generationClient, SWT.FLAT | ExpandableComposite.TWISTIE);
	    this.sequenceGenSection.setText(JptUiMappingsMessages.IdMappingComposite_sequenceGenerator);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.sequenceGenSection.setLayoutData(gridData);

		Composite sequenceGenClient = getWidgetFactory().createComposite(this.sequenceGenSection);
		this.sequenceGenSection.setClient(sequenceGenClient);
		
		layout = new GridLayout();
		layout.marginWidth = 0;
		sequenceGenClient.setLayout(layout);
	
		this.sequenceGeneratorCheckBox = buildSequenceGeneratorCheckBox(sequenceGenClient);
		gridData = new GridData();
		this.sequenceGeneratorCheckBox.setLayoutData(gridData);
		helpSystem.setHelp(sequenceGeneratorCheckBox, IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR);

		this.sequenceGeneratorComposite = new SequenceGeneratorComposite(sequenceGenClient, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = 20;
		this.sequenceGeneratorComposite.getControl().setLayoutData(gridData);

		return this.pkGenerationSection;
	}
	
	private Button buildPrimaryKeyGenerationCheckBox(Composite parent) {
		Button button = getWidgetFactory().createButton(parent,JptUiMappingsMessages.IdMappingComposite_primaryKeyGeneration, SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			// ignore
			}

			public void widgetSelected(SelectionEvent e) {
				IdComposite.this.primaryKeyGenerationCheckBoxClicked(e);
			}
		});
		return button;
	}

	void primaryKeyGenerationCheckBoxClicked(SelectionEvent e) {
		boolean mappingHasGeneratedValue = this.id.getGeneratedValue() != null;
		boolean checked = this.primaryKeyGenerationCheckBox.getSelection();
		if (checked == mappingHasGeneratedValue) {
			return;
		}
		IGeneratedValue generatedValue = null;
		if (checked) {
			generatedValue = this.id.createGeneratedValue();
		}
		this.id.setGeneratedValue(generatedValue);
	}

	private Button buildTableGeneratorCheckBox(Composite parent) {
		Button button = getWidgetFactory().createButton(parent,JptUiMappingsMessages.IdMappingComposite_tableGenerator, SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			// ignore
			}

			public void widgetSelected(SelectionEvent e) {
				IdComposite.this.tableGeneratorCheckBoxClicked(e);
			}
		});
		return button;
	}

	void tableGeneratorCheckBoxClicked(SelectionEvent e) {
		boolean mappingHasTableGenerator = this.id.getTableGenerator() != null;
		boolean checked = this.tableGeneratorCheckBox.getSelection();
		if (checked == mappingHasTableGenerator) {
			return;
		}
		ITableGenerator tableGenerator = null;
		if (checked) {
			tableGenerator = this.id.createTableGenerator();
		}
		this.id.setTableGenerator(tableGenerator);
		if (checked) {
			IGeneratedValue generatedValue = this.id.getGeneratedValue();
			if (generatedValue != null && generatedValue.getGenerator() != null) {
				tableGenerator.setName(generatedValue.getGenerator());
			}
		}
	}

	private Button buildSequenceGeneratorCheckBox(Composite parent) {
		Button button = getWidgetFactory().createButton(parent,JptUiMappingsMessages.IdMappingComposite_sequenceGenerator, SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			// ignore
			}

			public void widgetSelected(SelectionEvent e) {
				IdComposite.this.sequenceGeneratorCheckBoxClicked(e);
			}
		});
		return button;
	}

	void sequenceGeneratorCheckBoxClicked(SelectionEvent e) {
		boolean mappingHasSequenceGenerator = this.id.getSequenceGenerator() != null;
		boolean checked = this.sequenceGeneratorCheckBox.getSelection();
		if (checked == mappingHasSequenceGenerator) {
			return;
		}
		ISequenceGenerator sequenceGenerator = null;
		if (checked) {
			sequenceGenerator = this.id.createSequenceGenerator();
		}
		this.id.setSequenceGenerator(sequenceGenerator);
		if (checked) {
			IGeneratedValue generatedValue = this.id.getGeneratedValue();
			if (generatedValue != null && generatedValue.getGenerator() != null) {
				sequenceGenerator.setName(generatedValue.getGenerator());
			}
		}
	}

	
	
	public void doPopulate(EObject obj) {
		this.id = (IId) obj;
		if (this.id != null) {
			this.columnComposite.populate(this.id.getColumn());
		}
		else {
			this.columnComposite.populate(null);
			this.generatedValueComposite.populate(null);
			this.tableGeneratorComposite.populate(null);
			this.sequenceGeneratorComposite.populate(null);
			return;
		}
	    this.pkGenerationSection.setExpanded(true);
		this.temporalTypeViewer.populate(new TemporalTypeHolder(this.id));
		populateGeneratedValueComposite();
		populateSequenceGeneratorComposite();
		populateTableGeneratorComposite();
	}
	
	public void doPopulate() {
		this.columnComposite.populate();
		this.temporalTypeViewer.populate();
		this.generatedValueComposite.populate();
		this.tableGeneratorComposite.populate();
		this.sequenceGeneratorComposite.populate();
	}
	
	private void populateTableGeneratorComposite() {
		this.tableGeneratorComposite.populate(this.id);
		boolean tableGeneratorExists = this.id.getTableGenerator() != null;
		this.tableGeneratorCheckBox.setSelection(tableGeneratorExists);
		if (tableGeneratorExists) {
			this.tableGenSection.setExpanded(true);
		}
	}

	private void populateSequenceGeneratorComposite() {
		this.sequenceGeneratorComposite.populate(this.id);
		boolean sequenceGeneratorExists = this.id.getSequenceGenerator() != null;
		this.sequenceGeneratorCheckBox.setSelection(sequenceGeneratorExists);
		if (sequenceGeneratorExists) {
			this.sequenceGenSection.setExpanded(true);
		}
	}

	private void populateGeneratedValueComposite() {
		this.generatedValueComposite.populate(this.id);
		this.primaryKeyGenerationCheckBox.setSelection(this.id.getGeneratedValue() != null);
	}

	
	protected void engageListeners() {
		if (this.id !=null) {
			this.id.eAdapters().add(this.idListener);
		}
	}
	
	protected void disengageListeners() {
		if (this.id !=null) {
			this.id.eAdapters().remove(this.idListener);
		}
	}
	
	@Override
	public void dispose() {
		this.columnComposite.dispose();
		this.temporalTypeViewer.dispose();
		this.generatedValueComposite.dispose();
		this.tableGeneratorComposite.dispose();
		this.sequenceGeneratorComposite.dispose();
		super.dispose();
	}
	
	protected IId getId() {
		return this.id;
	}
	
	private class TemporalTypeHolder extends EObjectImpl implements EnumHolder {
		
		private IId id;
		
		TemporalTypeHolder(IId id) {
			super();
			this.id = id;
		}
		
		public Object get() {
			return this.id.getTemporal();
		}
		
		public void set(Object enumSetting) {
			this.id.setTemporal((TemporalType) enumSetting);
		}
		
		public Class featureClass() {
			return IId.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IID__TEMPORAL;
		}
		
		public EObject wrappedObject() {
			return this.id;
		}
		
		public Object[] enumValues() {
			return TemporalType.VALUES.toArray();
		}
		
		/**
		 * TemporalType has no Default, return null
		 */
		public Object defaultValue() {
			return null;
		}
		
		/**
		 * TemporalType has no Default, return null
		 */
		public String defaultString() {
			return null;
		}
	}
}

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IOneToMany;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class OneToManyComposite extends BaseJpaComposite 
{
	private IOneToMany oneToMany;
	
	private EnumComboViewer fetchTypeComboViewer;
	
	private TargetEntityChooser targetEntityChooser;
	
	private MappedByCombo mappedByCombo;

	private CascadeComposite cascadeComposite;
	
	private OrderByComposite orderByComposite;

	private JoinTableComposite joinTableComposite;
	
	public OneToManyComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		
		Control generalControl = buildGeneralControl(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		generalControl.setLayoutData(gridData);

		Control joinTableControl = buildJoinTableControl(composite);
	    gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		joinTableControl.setLayoutData(gridData);
	}
	
	private Control buildGeneralControl(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);	

		GridData gridData;
		
		this.targetEntityChooser = CommonWidgets.buildTargetEntityChooser(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.targetEntityChooser.getControl().setLayoutData(gridData);

		CommonWidgets.buildFetchLabel(generalComposite, getWidgetFactory());
		this.fetchTypeComboViewer = CommonWidgets.buildFetchTypeComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.fetchTypeComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(this.fetchTypeComboViewer.getControl(), IJpaHelpContextIds.MAPPING_FETCH_TYPE);

		CommonWidgets.buildMappedByLabel(generalComposite, getWidgetFactory());
		this.mappedByCombo = new MappedByCombo(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.mappedByCombo.getControl().setLayoutData(gridData);
		helpSystem.setHelp(this.mappedByCombo.getControl(), IJpaHelpContextIds.MAPPING_MAPPED_BY);
		
		this.cascadeComposite = new CascadeComposite(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.cascadeComposite.getControl().setLayoutData(gridData);
	
		this.orderByComposite = new OrderByComposite(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.orderByComposite.getControl().setLayoutData(gridData);
		

		return generalComposite;
	}
	
	private Control buildJoinTableControl(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JptUiMappingsMessages.MultiRelationshipMappingComposite_joinTable);

		Composite joinTableClient = getWidgetFactory().createComposite(section);
		section.setClient(joinTableClient);
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		joinTableClient.setLayout(layout);

		this.joinTableComposite = new JoinTableComposite(joinTableClient, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.joinTableComposite.getControl().setLayoutData(gridData);
		
		return section;
	}

	public void doPopulate(EObject obj) {
		this.oneToMany = (IOneToMany) obj;
		this.fetchTypeComboViewer.populate(CommonWidgets.buildMultiRelationshipMappingFetchEnumHolder(this.oneToMany));
		this.targetEntityChooser.populate(this.oneToMany);
		this.mappedByCombo.populate(this.oneToMany);
		this.cascadeComposite.populate(this.oneToMany);
		if (this.oneToMany != null) {
			this.joinTableComposite.populate(this.oneToMany.getJoinTable());
			this.orderByComposite.populate(this.oneToMany.getOrderBy());
		}
		else {
			this.joinTableComposite.populate(null);
			this.orderByComposite.populate(null);
		}
	}
	
	public void doPopulate() {
		this.fetchTypeComboViewer.populate();
		this.targetEntityChooser.populate();
		this.mappedByCombo.populate();
		this.cascadeComposite.populate();
		this.joinTableComposite.populate();
		this.orderByComposite.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.fetchTypeComboViewer.dispose();
		this.targetEntityChooser.dispose();
		this.mappedByCombo.dispose();
		this.cascadeComposite.dispose();
		this.joinTableComposite.dispose();
		this.orderByComposite.dispose();
		super.dispose();
	}
}

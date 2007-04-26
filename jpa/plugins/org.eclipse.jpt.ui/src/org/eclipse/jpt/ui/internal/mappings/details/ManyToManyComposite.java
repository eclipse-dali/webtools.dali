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
import org.eclipse.jpt.core.internal.mappings.IManyToMany;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ManyToManyComposite extends BaseJpaComposite 
{
	private IManyToMany manyToMany;
	
	private TargetEntityChooser targetEntityChooser;

	private EnumComboViewer fetchTypeComboViewer;

	private MappedByCombo mappedByCombo;

	private JoinTableComposite joinTableComposite;
	
	private OrderByComposite orderByComposite;

	public ManyToManyComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		Control generalControl = buildGeneralComposite(composite);
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
	
	private Control buildGeneralComposite(Composite composite) {
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);	

		this.targetEntityChooser = CommonWidgets.buildTargetEntityChooser(generalComposite, commandStack, getWidgetFactory());
		GridData gridData = new GridData();
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


		CommonWidgets.buildMappedByLabel(generalComposite, getWidgetFactory());
		this.mappedByCombo = new MappedByCombo(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.mappedByCombo.getControl().setLayoutData(gridData);

		this.orderByComposite = new OrderByComposite(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.orderByComposite.getControl().setLayoutData(gridData);

		return generalComposite;
	}
	
	private Control buildJoinTableControl(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JpaUiMappingsMessages.MultiRelationshipMappingComposite_joinTable);

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
		this.manyToMany = (IManyToMany) obj;
		this.targetEntityChooser.populate(this.manyToMany);
		this.fetchTypeComboViewer.populate(CommonWidgets.buildMultiRelationshipMappingFetchEnumHolder(this.manyToMany));
		this.mappedByCombo.populate(this.manyToMany);
		if (this.manyToMany != null) {
			this.joinTableComposite.populate(this.manyToMany.getJoinTable());
			this.orderByComposite.populate(this.manyToMany.getOrderBy());
		}
		else {
			this.joinTableComposite.populate(null);
			this.orderByComposite.populate(null);
		}
	}
	
	public void doPopulate() {
		this.targetEntityChooser.populate();
		this.fetchTypeComboViewer.populate();
		this.mappedByCombo.populate();
		this.joinTableComposite.populate();
		this.orderByComposite.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.targetEntityChooser.dispose();
		this.fetchTypeComboViewer.dispose();
		this.mappedByCombo.dispose();
		this.joinTableComposite.dispose();
		this.orderByComposite.dispose();
		super.dispose();
	}

}

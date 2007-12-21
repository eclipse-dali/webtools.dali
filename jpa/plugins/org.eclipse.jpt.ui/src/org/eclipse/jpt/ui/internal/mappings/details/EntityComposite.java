/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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

public class EntityComposite extends BaseJpaComposite<IEntity>
{
	private EntityNameCombo entityNameCombo;
	private TableComposite tableComposite;
	private InheritanceComposite inheritanceComposite;
	private SecondaryTablesComposite secondaryTablesComposite;
	private OverridesComposite attributeOverridesComposite;

	public EntityComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                       Composite parent,
	                       TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
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

		Control attributeOverridesControl = buildAttributeOverridesComposite(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		attributeOverridesControl.setLayoutData(gridData);

		Control secondaryTablesControl = buildSecondaryTablesComposite(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		secondaryTablesControl.setLayoutData(gridData);

		Control inheritanceControl = buildInheritanceComposite(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		inheritanceControl.setLayoutData(gridData);
	}

	private Control buildGeneralComposite(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		CommonWidgets.buildEntityNameLabel(generalComposite, getWidgetFactory());

		this.entityNameCombo =
	    	CommonWidgets.buildEntityNameCombo(generalComposite, getWidgetFactory());
	    GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.entityNameCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(this.entityNameCombo.getCombo(), IJpaHelpContextIds.ENTITY_NAME);


		this.tableComposite = new TableComposite(generalComposite, getWidgetFactory());
	    gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.tableComposite.getControl().setLayoutData(gridData);

		return generalComposite;
	}

	private Control buildSecondaryTablesComposite(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JptUiMappingsMessages.SecondaryTablesComposite_secondaryTables);

		Composite client = getWidgetFactory().createComposite(section);
		section.setClient(client);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		client.setLayout(layout);

		this.secondaryTablesComposite = new SecondaryTablesComposite(client, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.secondaryTablesComposite.getControl().setLayoutData(gridData);

		return section;
	}

	private Control buildInheritanceComposite(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JptUiMappingsMessages.EntityComposite_inheritance);

		Composite inheritanceClient = getWidgetFactory().createComposite(section);
		section.setClient(inheritanceClient);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		inheritanceClient.setLayout(layout);

		this.inheritanceComposite = new InheritanceComposite(inheritanceClient, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.inheritanceComposite.getControl().setLayoutData(gridData);

		return section;
	}

	private Control buildAttributeOverridesComposite(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides);
	    //section.setExpanded(true); //not going to expand this for now, it causes the scroll bar not to appear
		Composite client = getWidgetFactory().createComposite(section);
		section.setClient(client);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		client.setLayout(layout);

		this.attributeOverridesComposite = new OverridesComposite(client, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.attributeOverridesComposite.getControl().setLayoutData(gridData);

		return section;
	}

	//TODO talk to JavaEditor people about what we can do to hook in TabbedProperties for the JavaEditor

	@Override
	public void doPopulate() {
		this.entityNameCombo.populate(subject());
		this.attributeOverridesComposite.populate(subject());
		this.secondaryTablesComposite.populate(subject());
		this.inheritanceComposite.populate(subject());
		if (this.subject() != null) {
			this.tableComposite.populate(this.subject().getTable());
		}
		else {
			this.tableComposite.populate(null);
		}
	}

	@Override
	protected void engageListeners() {
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.entityNameCombo.dispose();
		this.tableComposite.dispose();
		this.attributeOverridesComposite.dispose();
		this.secondaryTablesComposite.dispose();
		this.inheritanceComposite.dispose();
		super.dispose();
	}
}
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

import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
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

public class OneToManyMappingComposite extends BaseJpaComposite<IOneToManyMapping>
{
	private CascadeComposite cascadeComposite;
	private EnumComboViewer fetchTypeComboViewer;
	private JoinTableComposite joinTableComposite;
	private MappedByCombo mappedByCombo;
	private OrderingComposite orderingComposite;
	private TargetEntityChooser targetEntityChooser;

	public OneToManyMappingComposite(PropertyValueModel<? extends IOneToManyMapping> subjectHolder,
	                          Composite parent,
	                          TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	private Control buildGeneralControl(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		GridData gridData;

		this.targetEntityChooser = CommonWidgets.buildTargetEntityChooser(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.targetEntityChooser.getControl().setLayoutData(gridData);

		CommonWidgets.buildFetchLabel(generalComposite, getWidgetFactory());
		this.fetchTypeComboViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.fetchTypeComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(this.fetchTypeComboViewer.getControl(), IJpaHelpContextIds.MAPPING_FETCH_TYPE);

		CommonWidgets.buildMappedByLabel(generalComposite, getWidgetFactory());
		this.mappedByCombo = new MappedByCombo(getSubjectHolder(), generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.mappedByCombo.getControl().setLayoutData(gridData);
		helpSystem.setHelp(this.mappedByCombo.getControl(), IJpaHelpContextIds.MAPPING_MAPPED_BY);

		this.cascadeComposite = new CascadeComposite(getSubjectHolder(), generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.cascadeComposite.getControl().setLayoutData(gridData);

		this.orderingComposite = new OrderingComposite(getSubjectHolder(), generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.orderingComposite.getControl().setLayoutData(gridData);


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

		this.joinTableComposite = new JoinTableComposite(buildJointTableHolder(), joinTableClient, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.joinTableComposite.getControl().setLayoutData(gridData);

		return section;
	}

	private PropertyValueModel<? extends IJoinTable> buildJointTableHolder() {
		return new PropertyAspectAdapter<IOneToManyMapping, IJoinTable>(getSubjectHolder(), "TODO") {
			@Override
			protected IJoinTable buildValue_() {
				return subject.getJoinTable();
			}
		};
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.fetchTypeComboViewer.dispose();
		this.targetEntityChooser.dispose();
		this.mappedByCombo.dispose();
		this.cascadeComposite.dispose();
		this.joinTableComposite.dispose();
		this.orderingComposite.dispose();
		super.dispose();
	}

	@Override
	protected void doPopulate() {
		this.fetchTypeComboViewer.populate(CommonWidgets.buildMultiRelationshipMappingFetchEnumHolder(this.subject()));
		this.targetEntityChooser.populate(this.subject());
		this.mappedByCombo.populate(this.subject());
		this.cascadeComposite.populate(this.subject());
		if (this.subject() != null) {
			this.joinTableComposite.populate(this.subject().getJoinTable());
			this.orderingComposite.populate(this.subject());
		}
		else {
			this.joinTableComposite.populate(null);
			this.orderingComposite.populate(null);
		}
	}

	@Override
	protected void engageListeners() {
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
}
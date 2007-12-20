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

import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ManyToOneComposite extends BaseJpaComposite<IManyToOneMapping>
{
	private CascadeComposite cascadeComposite;
	private EnumComboViewer fetchTypeComboViewer;
	private JoinColumnComposite joinColumnComposite;
	private EnumComboViewer optionalComboViewer;
	private TargetEntityChooser targetEntityChooser;

	public ManyToOneComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
	}

	private Control buildGeneralComposite(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		this.targetEntityChooser = CommonWidgets.buildTargetEntityChooser(generalComposite, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.targetEntityChooser.getControl().setLayoutData(gridData);
		helpSystem.setHelp(targetEntityChooser.getControl(), IJpaHelpContextIds.MAPPING_TARGET_ENTITY);

		CommonWidgets.buildFetchLabel(generalComposite, getWidgetFactory());
		this.fetchTypeComboViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.fetchTypeComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(fetchTypeComboViewer.getControl(), IJpaHelpContextIds.MAPPING_FETCH_TYPE);

		CommonWidgets.buildOptionalLabel(generalComposite, getWidgetFactory());
		this.optionalComboViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.optionalComboViewer.getControl().setLayoutData(gridData);

		this.cascadeComposite = new CascadeComposite(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.cascadeComposite.getControl().setLayoutData(gridData);

		this.joinColumnComposite = new JoinColumnComposite(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.joinColumnComposite.getControl().setLayoutData(gridData);

		return generalComposite;
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.targetEntityChooser.dispose();
		this.fetchTypeComboViewer.dispose();
		this.optionalComboViewer.dispose();
		this.cascadeComposite.dispose();
		this.joinColumnComposite.dispose();
		super.dispose();
	}

	@Override
	protected void doPopulate() {
		this.targetEntityChooser.populate(subject());
		this.fetchTypeComboViewer.populate(CommonWidgets.buildSingleRelationshipMappingFetchEnumHolder(subject()));
		this.optionalComboViewer.populate(CommonWidgets.buildOptionalHolder(subject()));
		this.cascadeComposite.populate(subject());
		this.joinColumnComposite.populate(subject());
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

		Control generalControl = buildGeneralComposite(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		generalControl.setLayoutData(gridData);
	}
}

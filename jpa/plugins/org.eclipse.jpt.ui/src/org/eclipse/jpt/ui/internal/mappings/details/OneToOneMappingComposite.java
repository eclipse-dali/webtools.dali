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

import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class OneToOneMappingComposite extends BaseJpaComposite<IOneToOneMapping>
{
	private CascadeComposite cascadeComposite;
	private EnumComboViewer fetchTypeComboViewer;
	private JoinColumnComposite joinColumnComposite;
	private MappedByCombo mappedByCombo;
	private EnumComboViewer optionalComboViewer;
	private TargetEntityChooser targetEntityChooser;

	public OneToOneMappingComposite(PropertyValueModel<? extends IOneToOneMapping> subjectHolder,
	                         Composite parent,
	                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	protected Control buildGeneralComposite(Composite composite) {
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		TargetEntityChooser targetEntityChooser = getTargetEntityChooser(generalComposite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		targetEntityChooser.getControl().setLayoutData(gridData);


		CommonWidgets.buildFetchLabel(generalComposite, getWidgetFactory());
		EnumComboViewer fetchTypeComboViewer = getFetchTypeComboViewer(generalComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		fetchTypeComboViewer.getControl().setLayoutData(gridData);

		CommonWidgets.buildMappedByLabel(generalComposite, getWidgetFactory());
		MappedByCombo mappedByCombo = getMappedByCombo(generalComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		mappedByCombo.getControl().setLayoutData(gridData);

		CommonWidgets.buildOptionalLabel(generalComposite, getWidgetFactory());
		EnumComboViewer optionalComboViewer = getOptionalComboViewer(generalComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		optionalComboViewer.getControl().setLayoutData(gridData);

		CascadeComposite cascadeComposite = getCascadeComposite(generalComposite);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		cascadeComposite.getControl().setLayoutData(gridData);

		JoinColumnComposite joinColumnComposite = getJoinColumnComposite(generalComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		joinColumnComposite.getControl().setLayoutData(gridData);

		return generalComposite;
	}

	protected CascadeComposite createCascadeComposite(Composite parentComposite) {
		return new CascadeComposite(getSubjectHolder(), parentComposite, getWidgetFactory());
	}

	protected EnumComboViewer createFetchTypeComboViewer(Composite parentComposite) {
		return CommonWidgets.buildEnumComboViewer(parentComposite, getWidgetFactory());
	}

	protected JoinColumnComposite createJoinColumnComposite(Composite parentComposite) {
		return new JoinColumnComposite(getSubjectHolder(), parentComposite, getWidgetFactory());
	}

	protected MappedByCombo createMappedByCombo(Composite parentComposite) {
		return new MappedByCombo(getSubjectHolder(), parentComposite, getWidgetFactory());
	}

	protected EnumComboViewer createOptionalComboViewer(Composite parentComposite) {
		return CommonWidgets.buildEnumComboViewer(parentComposite, getWidgetFactory());
	}

	protected TargetEntityChooser createTargetEntityChooser(Composite parentComposite) {
		return CommonWidgets.buildTargetEntityChooser(parentComposite, getWidgetFactory());
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.targetEntityChooser.dispose();
		this.fetchTypeComboViewer.dispose();
		this.mappedByCombo.dispose();
		this.optionalComboViewer.dispose();
		this.cascadeComposite.dispose();
		this.joinColumnComposite.dispose();
		super.dispose();
	}

	@Override
	protected void doPopulate() {
		this.targetEntityChooser.populate();
		this.fetchTypeComboViewer.populate(CommonWidgets.buildSingleRelationshipMappingFetchEnumHolder(subject()));
		this.mappedByCombo.populate();
		this.optionalComboViewer.populate(CommonWidgets.buildOptionalHolder(subject()));
		this.cascadeComposite.populate();
		this.joinColumnComposite.populate();
	}

	@Override
	protected void engageListeners() {
	}

	protected CascadeComposite getCascadeComposite(Composite parentComposite) {
		if (this.cascadeComposite == null) {
			this.cascadeComposite = createCascadeComposite(parentComposite);
		}
		return this.cascadeComposite;
	}

	protected EnumComboViewer getFetchTypeComboViewer(Composite parentComposite) {
		if (this.fetchTypeComboViewer == null) {
			this.fetchTypeComboViewer = createFetchTypeComboViewer(parentComposite);
		}
		return this.fetchTypeComboViewer;
	}


	protected JoinColumnComposite getJoinColumnComposite(Composite parentComposite) {
		if (this.joinColumnComposite == null) {
			this.joinColumnComposite = createJoinColumnComposite(parentComposite);
		}
		return this.joinColumnComposite;
	}

	protected MappedByCombo getMappedByCombo(Composite parentComposite) {
		if (this.mappedByCombo == null) {
			this.mappedByCombo = createMappedByCombo(parentComposite);
		}
		return this.mappedByCombo;
	}

	protected EnumComboViewer getOptionalComboViewer(Composite parentComposite) {
		if (this.optionalComboViewer == null) {
			this.optionalComboViewer = createOptionalComboViewer(parentComposite);
		}
		return this.optionalComboViewer;
	}

	protected TargetEntityChooser getTargetEntityChooser(Composite parentComposite) {
		if (this.targetEntityChooser == null) {
			this.targetEntityChooser = createTargetEntityChooser(parentComposite);
		}
		return this.targetEntityChooser;
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

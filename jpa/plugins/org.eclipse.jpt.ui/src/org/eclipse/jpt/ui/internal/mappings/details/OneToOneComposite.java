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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class OneToOneComposite extends BaseJpaComposite 
{
	private IOneToOne oneToOne;
	
	private TargetEntityChooser targetEntityChooser;

	private EnumComboViewer fetchTypeComboViewer;
	
	private MappedByCombo mappedByCombo;

	private EnumComboViewer optionalComboViewer;

	private CascadeComposite cascadeComposite;

	private JoinColumnComposite joinColumnComposite;
		
	public OneToOneComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}
	
	protected IOneToOne getOneToOne() {
		return this.oneToOne;
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

	protected TargetEntityChooser getTargetEntityChooser(Composite parentComposite) {
		if (this.targetEntityChooser == null) {
			this.targetEntityChooser = createTargetEntityChooser(parentComposite);
		}
		return this.targetEntityChooser;
	}
	
	protected TargetEntityChooser createTargetEntityChooser(Composite parentComposite) {
		return CommonWidgets.buildTargetEntityChooser(parentComposite, this.commandStack, getWidgetFactory());
	}
	
	protected EnumComboViewer getFetchTypeComboViewer(Composite parentComposite) {
		if (this.fetchTypeComboViewer == null) {
			this.fetchTypeComboViewer = createFetchTypeComboViewer(parentComposite);
		}
		return this.fetchTypeComboViewer;
	}
	
	protected EnumComboViewer createFetchTypeComboViewer(Composite parentComposite) {
		return CommonWidgets.buildEnumComboViewer(parentComposite, this.commandStack, getWidgetFactory());
	}

	protected MappedByCombo getMappedByCombo(Composite parentComposite) {
		if (this.mappedByCombo == null) {
			this.mappedByCombo = createMappedByCombo(parentComposite);
		}
		return this.mappedByCombo;
	}
	
	protected MappedByCombo createMappedByCombo(Composite parentComposite) {
		return new MappedByCombo(parentComposite, this.commandStack, getWidgetFactory());
	}
	
	protected EnumComboViewer getOptionalComboViewer(Composite parentComposite) {
		if (this.optionalComboViewer == null) {
			this.optionalComboViewer = createOptionalComboViewer(parentComposite);
		}
		return this.optionalComboViewer;
	}
	
	protected EnumComboViewer createOptionalComboViewer(Composite parentComposite) {
		return CommonWidgets.buildEnumComboViewer(parentComposite, this.commandStack, getWidgetFactory());
	}
	
	protected CascadeComposite getCascadeComposite(Composite parentComposite) {
		if (this.cascadeComposite == null) {
			this.cascadeComposite = createCascadeComposite(parentComposite);
		}
		return this.cascadeComposite;
	}
	
	protected CascadeComposite createCascadeComposite(Composite parentComposite) {
		return new CascadeComposite(parentComposite, this.commandStack, getWidgetFactory());
	}
	
	protected JoinColumnComposite getJoinColumnComposite(Composite parentComposite) {
		if (this.joinColumnComposite == null) {
			this.joinColumnComposite = createJoinColumnComposite(parentComposite);
		}
		return this.joinColumnComposite;
	}
	
	protected JoinColumnComposite createJoinColumnComposite(Composite parentComposite) {
		return new JoinColumnComposite(parentComposite, this.commandStack, getWidgetFactory());
	}

	
	public void doPopulate(EObject obj) {
		this.oneToOne = (IOneToOne) obj;
		this.targetEntityChooser.populate(getOneToOne());
		this.fetchTypeComboViewer.populate(CommonWidgets.buildSingleRelationshipMappingFetchEnumHolder(getOneToOne()));
		this.mappedByCombo.populate(getOneToOne());
		this.optionalComboViewer.populate(CommonWidgets.buildOptionalHolder(getOneToOne()));
		this.cascadeComposite.populate(getOneToOne());
		this.joinColumnComposite.populate(getOneToOne());
	}
	
	public void doPopulate() {
		this.targetEntityChooser.populate();
		this.fetchTypeComboViewer.populate();
		this.mappedByCombo.populate();
		this.optionalComboViewer.populate();
		this.cascadeComposite.populate();
		this.joinColumnComposite.populate();
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
		this.optionalComboViewer.dispose();
		this.cascadeComposite.dispose();
		this.joinColumnComposite.dispose();
		super.dispose();
	}


}

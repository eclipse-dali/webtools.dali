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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IManyToOne;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ManyToOneComposite extends BaseJpaComposite 
{
	private IManyToOne manyToOne;
	
	private TargetEntityChooser targetEntityChooser;

	private EnumComboViewer fetchTypeComboViewer;
	
	private EnumComboViewer optionalComboViewer;

	private OrderByComposite orderByComposite;

	private CascadeComposite cascadeComposite;

	private JoinColumnComposite joinColumnComposite;
		
	public ManyToOneComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
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
	
	private Control buildGeneralComposite(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
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
		helpSystem.setHelp(targetEntityChooser.getControl(), IJpaHelpContextIds.MAPPING_TARGET_ENTITY);
		
		CommonWidgets.buildFetchLabel(generalComposite, getWidgetFactory());
		this.fetchTypeComboViewer = CommonWidgets.buildFetchTypeComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.fetchTypeComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(fetchTypeComboViewer.getControl(), IJpaHelpContextIds.MAPPING_FETCH_TYPE);
		
		CommonWidgets.buildOptionalLabel(generalComposite, getWidgetFactory());
		this.optionalComboViewer = CommonWidgets.buildEnumComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.optionalComboViewer.getControl().setLayoutData(gridData);

		this.cascadeComposite = new CascadeComposite(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.cascadeComposite.getControl().setLayoutData(gridData);

		this.joinColumnComposite = new JoinColumnComposite(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.joinColumnComposite.getControl().setLayoutData(gridData);	

		return generalComposite;
	}
	
	public void doPopulate(EObject obj) {
		this.manyToOne = (IManyToOne) obj;
		this.targetEntityChooser.populate(this.manyToOne);
		this.fetchTypeComboViewer.populate(CommonWidgets.buildSingleRelationshipMappingFetchEnumHolder(this.manyToOne));
		this.optionalComboViewer.populate(new OptionalHolder(this.manyToOne));
		this.cascadeComposite.populate(this.manyToOne);
		this.joinColumnComposite.populate(this.manyToOne);
	}
	
	public void doPopulate() {
		this.targetEntityChooser.populate();
		this.fetchTypeComboViewer.populate();
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
		this.optionalComboViewer.dispose();
		this.cascadeComposite.dispose();
		this.joinColumnComposite.dispose();
		super.dispose();
	}
	
	private class OptionalHolder extends EObjectImpl implements EnumHolder {
		
		private IManyToOne manyToOne;
		
		OptionalHolder(IManyToOne manyToOne) {
			super();
			this.manyToOne = manyToOne;
		}
		
		public Object get() {
			return this.manyToOne.getOptional();
		}
		
		public void set(Object enumSetting) {
			this.manyToOne.setOptional((DefaultTrueBoolean) enumSetting);
		}
		
		public Class featureClass() {
			return IManyToOne.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IMANY_TO_ONE__OPTIONAL;
		}
		
		public EObject wrappedObject() {
			return this.manyToOne;
		}
		
		public Object[] enumValues() {
			return DefaultTrueBoolean.VALUES.toArray();
		}
	}
}

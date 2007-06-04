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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
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
	
	private IOneToOne getOneToOne() {
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
	
	private Control buildGeneralComposite(Composite composite) {
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);	
		
		this.targetEntityChooser = CommonWidgets.buildTargetEntityChooser(generalComposite, this.commandStack, getWidgetFactory());
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
		this.oneToOne = (IOneToOne) obj;
		this.targetEntityChooser.populate(this.oneToOne);
		this.fetchTypeComboViewer.populate(CommonWidgets.buildSingleRelationshipMappingFetchEnumHolder(this.oneToOne));
		this.mappedByCombo.populate(this.oneToOne);
		this.optionalComboViewer.populate(new OptionalHolder(this.oneToOne));
		this.cascadeComposite.populate(this.oneToOne);
		this.joinColumnComposite.populate(this.oneToOne);
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

	private class OptionalHolder extends EObjectImpl implements EnumHolder {
		
		private IOneToOne oneToOne;
		
		OptionalHolder(IOneToOne oneToOne) {
			super();
			this.oneToOne = oneToOne;
		}
		
		public Object get() {
			return this.oneToOne.getOptional();
		}
		
		public void set(Object enumSetting) {
			this.oneToOne.setOptional((DefaultTrueBoolean) enumSetting);
		}
		
		public Class featureClass() {
			return IOneToOne.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IONE_TO_ONE__OPTIONAL;
		}
		
		public EObject wrappedObject() {
			return this.oneToOne;
		}
		
		public Object[] enumValues() {
			return DefaultTrueBoolean.VALUES.toArray();
		}
		
		public Object defaultValue() {
			return DefaultTrueBoolean.DEFAULT;
		}
		
		public String defaultString() {
			//TODO move this out of the UI into the model
			return "True";
		}

	}

}

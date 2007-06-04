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
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.EnumType;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
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

public class BasicComposite extends BaseJpaComposite 
{
	private IBasic basic;
	
	private ColumnComposite columnComposite;

	private EnumComboViewer fetchTypeComboViewer;
	private EnumComboViewer optionalComboViewer;
	private LobCheckBox lobCheckBox;
	private EnumComboViewer temporalTypeViewer;
	private EnumComboViewer enumeratedTypeViewer;
		
	public BasicComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
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

		this.columnComposite = new ColumnComposite(generalComposite, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.columnComposite.getControl().setLayoutData(gridData);		
		
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
		helpSystem.setHelp(optionalComboViewer.getControl(), IJpaHelpContextIds.MAPPING_OPTIONAL);
			
		CommonWidgets.buildTemporalLabel(generalComposite, getWidgetFactory());
		this.temporalTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.temporalTypeViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(temporalTypeViewer.getControl(), IJpaHelpContextIds.MAPPING_TEMPORAL);

		CommonWidgets.buildEnumeratedLabel(generalComposite, getWidgetFactory());
		this.enumeratedTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.enumeratedTypeViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(enumeratedTypeViewer.getControl(), IJpaHelpContextIds.MAPPING_ENUMERATED);

	    this.lobCheckBox = buildLobCheckBox(generalComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.lobCheckBox.getControl().setLayoutData(gridData);
		helpSystem.setHelp(lobCheckBox.getControl(), IJpaHelpContextIds.MAPPING_LOB);

		return generalComposite;
	}
	
	private LobCheckBox buildLobCheckBox(Composite parent) {
		return new LobCheckBox(parent, this.commandStack, getWidgetFactory());
	}
	
	public void doPopulate(EObject obj) {
		this.basic = (IBasic) obj;
		if (this.basic != null) {
			this.columnComposite.populate(this.basic.getColumn());
		}
		else {
			this.columnComposite.populate(null);
		}
		this.fetchTypeComboViewer.populate(new FetchHolder(this.basic));
		this.optionalComboViewer.populate(new OptionalHolder(this.basic));
		this.lobCheckBox.populate(this.basic);
		this.temporalTypeViewer.populate(new TemporalTypeHolder(this.basic));
		this.enumeratedTypeViewer.populate(new EnumeratedTypeHolder(this.basic));
	}
	
	public void doPopulate() {
		this.columnComposite.populate();
		this.fetchTypeComboViewer.populate();
		this.optionalComboViewer.populate();
		this.lobCheckBox.populate();
		this.temporalTypeViewer.populate();
		this.enumeratedTypeViewer.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.columnComposite.dispose();
		this.fetchTypeComboViewer.dispose();
		this.optionalComboViewer.dispose();
		this.lobCheckBox.dispose();
		this.temporalTypeViewer.dispose();
		this.enumeratedTypeViewer.dispose();
		super.dispose();
	}
	
	protected IBasic getBasic() {
		return this.basic;
	}
	
	private class FetchHolder extends EObjectImpl implements EnumHolder {
		
		private IBasic basic;
		
		FetchHolder(IBasic basic) {
			super();
			this.basic = basic;
		}
		
		public Object get() {
			return this.basic.getFetch();
		}
		
		public void set(Object enumSetting) {
			this.basic.setFetch((DefaultEagerFetchType) enumSetting);
			
		}
		
		public Class featureClass() {
			return IBasic.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__FETCH;
		}
		
		public EObject wrappedObject() {
			return this.basic;
		}
		
		public Object[] enumValues() {
			return DefaultEagerFetchType.VALUES.toArray();
		}
		
		public Object defaultValue() {
			return DefaultEagerFetchType.DEFAULT;
		}
		
		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Eager";
		}
	}

	
	private class OptionalHolder extends EObjectImpl implements EnumHolder {
		
		private IBasic basic;
		
		OptionalHolder(IBasic basic) {
			super();
			this.basic = basic;
		}
		
		public Object get() {
			return this.basic.getOptional();
		}
		
		public void set(Object enumSetting) {
			this.basic.setOptional((DefaultTrueBoolean) enumSetting);
		}
		
		public Class featureClass() {
			return IBasic.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__OPTIONAL;
		}
		
		public EObject wrappedObject() {
			return this.basic;
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

	
	private class TemporalTypeHolder extends EObjectImpl implements EnumHolder {
		
		private IBasic basic;
		
		TemporalTypeHolder(IBasic basic) {
			super();
			this.basic = basic;
		}
		
		public Object get() {
			return this.basic.getTemporal();
		}
		
		public void set(Object enumSetting) {
			this.basic.setTemporal((TemporalType) enumSetting);
		}
		
		public Class featureClass() {
			return IBasic.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__TEMPORAL;
		}
		
		public EObject wrappedObject() {
			return this.basic;
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
	
	private class EnumeratedTypeHolder extends EObjectImpl implements EnumHolder {
		
		private IBasic basic;
		
		EnumeratedTypeHolder(IBasic basic) {
			super();
			this.basic = basic;
		}
		
		public Object get() {
			return this.basic.getEnumerated();
		}
		
		public void set(Object enumSetting) {
			this.basic.setEnumerated((EnumType) enumSetting);
		}
		
		public Class featureClass() {
			return IBasic.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__ENUMERATED;
		}
		
		public EObject wrappedObject() {
			return this.basic;
		}
		
		public Object[] enumValues() {
			return EnumType.VALUES.toArray();
		}
		
		public Object defaultValue() {
			return EnumType.DEFAULT;
		}
		
		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Ordinal";
		}
	}
}

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
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IVersion;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class VersionComposite extends BaseJpaComposite 
{
	private IVersion version;
	
	private ColumnComposite columnComposite;

	private EnumComboViewer temporalTypeViewer;
	

	public VersionComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
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
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
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
		
		CommonWidgets.buildTemporalLabel(generalComposite, getWidgetFactory());
		this.temporalTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.temporalTypeViewer.getControl().setLayoutData(gridData);

		return generalComposite;
	}
	
	public void doPopulate(EObject obj) {
		this.version = (IVersion) obj;
		if (this.version != null) {
			this.columnComposite.populate(this.version.getColumn());
		}
		else {
			this.columnComposite.populate(null);
		}
		this.temporalTypeViewer.populate(new TemporalTypeHolder(this.version));
	}
	
	public void doPopulate() {
		this.columnComposite.populate();
		this.temporalTypeViewer.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.columnComposite.dispose();
		this.temporalTypeViewer.dispose();
		super.dispose();
	}
	
	protected IVersion getVersion() {
		return this.version;
	}
	


	
	private class TemporalTypeHolder extends EObjectImpl implements EnumHolder {
		
		private IVersion version;
		
		TemporalTypeHolder(IVersion version) {
			super();
			this.version = version;
		}
		
		public Object get() {
			return this.version.getTemporal();
		}
		
		public void set(Object enumSetting) {
			this.version.setTemporal((TemporalType) enumSetting);
		}
		
		public Class featureClass() {
			return IBasic.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IVERSION__TEMPORAL;
		}
		
		public EObject wrappedObject() {
			return this.version;
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

}

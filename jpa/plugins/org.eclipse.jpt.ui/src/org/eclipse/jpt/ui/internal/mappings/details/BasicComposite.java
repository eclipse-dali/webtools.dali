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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
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

public class BasicComposite extends BaseJpaComposite<IBasicMapping>
{
	private ColumnComposite columnComposite;
	private EnumComboViewer enumeratedTypeViewer;
	private EnumComboViewer fetchTypeComboViewer;
	private LobCheckBox lobCheckBox;
	private EnumComboViewer optionalComboViewer;
	private EnumComboViewer temporalTypeViewer;

	public BasicComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
	}

	private Control buildGeneralComposite(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		this.columnComposite = new ColumnComposite(generalComposite, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.columnComposite.getControl().setLayoutData(gridData);

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
		helpSystem.setHelp(optionalComboViewer.getControl(), IJpaHelpContextIds.MAPPING_OPTIONAL);

		CommonWidgets.buildTemporalLabel(generalComposite, getWidgetFactory());
		this.temporalTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.temporalTypeViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(temporalTypeViewer.getControl(), IJpaHelpContextIds.MAPPING_TEMPORAL);

		CommonWidgets.buildEnumeratedLabel(generalComposite, getWidgetFactory());
		this.enumeratedTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
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
		return new LobCheckBox(parent, getWidgetFactory());
	}

	@Override
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

	@Override
	public void doPopulate() {
		if (this.subject() != null) {
			this.columnComposite.populate(this.subject().getColumn());
		}
		else {
			this.columnComposite.populate(null);
		}
		this.fetchTypeComboViewer.populate(new FetchHolder(this.subject()));
		this.optionalComboViewer.populate(new OptionalHolder(this.subject()));
		this.lobCheckBox.populate(this.subject());
		this.temporalTypeViewer.populate(new TemporalTypeHolder(this.subject()));
		this.enumeratedTypeViewer.populate(new EnumeratedTypeHolder(this.subject()));
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

	private class EnumeratedTypeHolder implements EnumHolder<IBasicMapping, EnumType> {

		private IBasicMapping basic;

		EnumeratedTypeHolder(IBasicMapping basic) {
			super();
			this.basic = basic;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Ordinal";
		}

		public EnumType defaultValue() {
			return EnumType.DEFAULT;
		}

		public EnumType[] enumValues() {
			return EnumType.values();
		}

		public Class<IBasicMapping> featureClass() {
			return IBasicMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__ENUMERATED;
		}

		public EnumType get() {
			return this.basic.getEnumerated();
		}

		public void set(EnumType enumSetting) {
			this.basic.setEnumerated(enumSetting);
		}

		public IBasicMapping wrappedObject() {
			return this.basic;
		}
	}


	private class FetchHolder extends EObjectImpl implements EnumHolder {

		private IBasicMapping basic;

		FetchHolder(IBasicMapping basic) {
			super();
			this.basic = basic;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Eager";
		}

		public Object defaultValue() {
			return DefaultEagerFetchType.DEFAULT;
		}

		public Object[] enumValues() {
			return DefaultEagerFetchType.VALUES.toArray();
		}

		public Class featureClass() {
			return IBasicMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__FETCH;
		}

		public Object get() {
			return this.basic.getFetch();
		}

		public void set(Object enumSetting) {
			this.basic.setFetch((DefaultEagerFetchType) enumSetting);

		}

		public EObject wrappedObject() {
			return this.basic;
		}
	}


	private class OptionalHolder extends EObjectImpl implements EnumHolder {

		private IBasicMapping basic;

		OptionalHolder(IBasicMapping basic) {
			super();
			this.basic = basic;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "True";
		}

		public Object defaultValue() {
			return DefaultTrueBoolean.DEFAULT;
		}

		public Object[] enumValues() {
			return DefaultTrueBoolean.VALUES.toArray();
		}

		public Class featureClass() {
			return IBasicMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__OPTIONAL;
		}

		public Object get() {
			return this.basic.getOptional();
		}

		public void set(Object enumSetting) {
			this.basic.setOptional((DefaultTrueBoolean) enumSetting);
		}

		public EObject wrappedObject() {
			return this.basic;
		}

	}

	private class TemporalTypeHolder extends EObjectImpl implements EnumHolder {

		private IBasicMapping basic;

		TemporalTypeHolder(IBasicMapping basic) {
			super();
			this.basic = basic;
		}

		/**
		 * TemporalType has no Default, return null
		 */
		public String defaultString() {
			return null;
		}

		/**
		 * TemporalType has no Default, return null
		 */
		public Object defaultValue() {
			return null;
		}

		public Object[] enumValues() {
			return TemporalType.VALUES.toArray();
		}

		public Class featureClass() {
			return IBasicMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IBASIC__TEMPORAL;
		}

		public Object get() {
			return this.basic.getTemporal();
		}

		public void set(Object enumSetting) {
			this.basic.setTemporal((TemporalType) enumSetting);
		}

		public EObject wrappedObject() {
			return this.basic;
		}
	}
}
/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class VersionComposite extends BaseJpaComposite<IVersionMapping> {

	private ColumnComposite columnComposite;
	private EnumComboViewer temporalTypeViewer;

	public VersionComposite(PropertyValueModel<? extends IVersionMapping> subjectHolder,
	                        Composite parent,
	                        TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	private Control buildGeneralComposite(Composite composite) {
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);

		this.columnComposite = new ColumnComposite(buildColumnHolder(), generalComposite, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.columnComposite.getControl().setLayoutData(gridData);

		CommonWidgets.buildTemporalLabel(generalComposite, getWidgetFactory());
		this.temporalTypeViewer = CommonWidgets.buildEnumComboViewer(generalComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.temporalTypeViewer.getControl().setLayoutData(gridData);

		return generalComposite;
	}

	private PropertyValueModel<? extends IColumn> buildColumnHolder() {
		// TODO: Maybe have TransformationPropertyValueModel and
		// TransformationWritablePropertyValueModel
		return new TransformationPropertyValueModel<IVersionMapping, IColumn>(getSubjectHolder()) {
			@Override
			protected IColumn transform_(IVersionMapping value) {
				return value.getColumn();
			}
		};
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.columnComposite.dispose();
		this.temporalTypeViewer.dispose();
		super.dispose();
	}

	@Override
	public void doPopulate() {
		this.columnComposite.populate();
		this.temporalTypeViewer.populate(new TemporalTypeHolder(this.subject()));
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

	private class TemporalTypeHolder implements EnumHolder<IVersionMapping, TemporalType> {

		private IVersionMapping version;

		TemporalTypeHolder(IVersionMapping version) {
			super();
			this.version = version;
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
		public TemporalType defaultValue() {
			return null;
		}

		public TemporalType[] enumValues() {
			return TemporalType.values();
		}

		public Class<IBasicMapping> featureClass() {
			return IBasicMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IVERSION__TEMPORAL;
		}

		public TemporalType get() {
			return this.version.getTemporal();
		}

		public void set(TemporalType enumSetting) {
			this.version.setTemporal(enumSetting);
		}

		public IVersionMapping subject() {
			return this.version;
		}
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??                                                                       �?? �??
 * �?? �?? ColumnComposite                                                       �?? �??
 * �?? �??                                                                       �?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??                                                                       �?? �??
 * �?? �?? FetchTypeComposite                                                    �?? �??
 * �?? �??                                                                       �?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??                                                                       �?? �??
 * �?? �?? OptionalComposite                                                     �?? �??
 * �?? �??                                                                       �?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??                                                                       �?? �??
 * �?? �?? TemporalTypeComposite                                                 �?? �??
 * �?? �??                                                                       �?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �?? �??                                                                       �?? �??
 * �?? �?? EnumTypeComposite                                                     �?? �??
 * �?? �??                                                                       �?? �??
 * �?? �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?? �??
 * �??                                                                           �??
 * �?? x Lob                                                                     �??
 * �??                                                                           �??
 * �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??</pre>
 *
 * @see IBasicMapping
 * @see BaseJpaUiFactory
 * @see ColumnComposite
 * @see EnumTypeComposite
 * @see FetchTypeComposite
 * @see OptionalComposite
 * @see TemporalTypeComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class BasicComposite extends BaseJpaComposite<IBasicMapping>
{
	private ColumnComposite columnComposite;
	private EnumTypeComposite enumTypeComposite;
	private FetchTypeComposite fetchTypeComposite;
	private LobCheckBox lobCheckBox;
	private OptionalComposite optionalComposite;
	private TemporalTypeComposite temporalTypeComposite;

	/**
	 * Creates a new <code>BasicComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IBasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public BasicComposite(PropertyValueModel<IBasicMapping> subjectHolder,
	                      Composite parent,
	                      TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	private PropertyValueModel<IColumn> buildColumnHolder() {
		// TODO: Have a TransformationPropertyValueModel and
		// TransformationWritablePropertyValue
		return new TransformationPropertyValueModel<IBasicMapping, IColumn>(getSubjectHolder()) {
			@Override
			protected IColumn transform_(IBasicMapping value) {
				return value.getColumn();
			}
		};
	}

	private LobCheckBox buildLobCheckBox(Composite parent) {
		return new LobCheckBox(getSubjectHolder(), parent, getWidgetFactory());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {
		this.columnComposite.dispose();
		this.fetchTypeComposite.dispose();
		this.optionalComposite.dispose();
		this.lobCheckBox.dispose();
		this.temporalTypeComposite.dispose();
		this.enumTypeComposite.dispose();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doPopulate() {
		this.columnComposite.populate();
		this.fetchTypeComposite.populate();
		this.optionalComposite.populate();
		this.lobCheckBox.populate();
		this.temporalTypeComposite.populate();
		this.enumTypeComposite.populate();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Column widgets
		this.columnComposite = new ColumnComposite(buildColumnHolder(), container, getWidgetFactory());
		this.addPaneForAlignment(columnComposite);

		// Fetch Type widgets
		this.fetchTypeComposite = new FetchTypeComposite(this, container);

		// Optional widgets
		this.optionalComposite = new OptionalComposite(this, container);

		// Temporal Type widgets
		this.temporalTypeComposite = new TemporalTypeComposite(this, container);

		// Enumerated widgets
		this.enumTypeComposite = new EnumTypeComposite(this, container);

		// Lob
		this.lobCheckBox = buildLobCheckBox(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;

		this.lobCheckBox.getControl().setLayoutData(gridData);
		this.helpSystem().setHelp(lobCheckBox.getControl(), IJpaHelpContextIds.MAPPING_LOB);
	}
}
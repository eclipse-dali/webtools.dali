/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * Here is the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------
 * |                      ---------------                                      |
 * |   Size:              | I         |I|  Default (XXX)                       |
 * |                      ---------------                                      |
 * ----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class CacheSizeComposite extends FormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheSizeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CacheSizeComposite(FormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Size widgets
		Spinner sizeSpinner = addLabeledSpinner(
			container,
			EclipseLinkUiMappingsMessages.CacheSizeComposite_size,
			buildSizeHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			addDefaultSizeLabel(container),
			null//TODO JpaHelpContextIds.MAPPING_COLUMN_LENGTH
		);

		updateGridData(container, sizeSpinner);
	}
	
	private WritablePropertyValueModel<Integer> buildSizeHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedSize();
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedSize(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultSizeHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder(), EclipseLinkCaching.DEFAULT_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultSize();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Integer.MIN_VALUE, newValue);
				}
			}
		};
	}

	private Control addDefaultSizeLabel(Composite container) {

		Label label = addLabel(
			container,
			JptUiMappingsMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultSizeLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultSizeLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultSizeHolder()) {

			@Override
			protected String transform(Integer value) {

				Integer defaultValue = (getSubject() != null) ? getSubject().getDefaultSize() :
								EclipseLinkCaching.DEFAULT_SIZE;

				return NLS.bind(
					JptUiMappingsMessages.DefaultWithValue,
					defaultValue
				);
			}
		};
	}

	/**
	 * Changes the layout of the given container by changing which widget will
	 * grab the excess of horizontal space. By default, the center control grabs
	 * the excess space, we change it to be the right control.
	 *
	 * @param container The container containing the controls needing their
	 * <code>GridData</code> to be modified from the default values
	 * @param spinner The spinner that got created
	 */
	private void updateGridData(Composite container, Spinner spinner) {

		// It is possible the spinner's parent is not the container of the
		// label, spinner and right control (a pane is sometimes required for
		// painting the spinner's border)
		Composite paneContainer = spinner.getParent();

		while (container != paneContainer.getParent()) {
			paneContainer = paneContainer.getParent();
		}

		Control[] controls = paneContainer.getChildren();

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment       = GridData.BEGINNING;
		controls[1].setLayoutData(gridData);

		controls[2].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeAlignRight(controls[2]);
	}
}

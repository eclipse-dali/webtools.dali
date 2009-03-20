/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.Pane;
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

 * @see OrmGenerator
 * @see OrmGeneratorsComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class OrmGeneratorComposite<T extends OrmGenerator> extends Pane<T>
{
	/**
	 * Creates a new <code>OrmTableGeneratorComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmGeneratorComposite(Pane<?> parentPane,
	                                  PropertyValueModel<T> subjectHolder,
	                                  Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	protected WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<OrmGenerator, String>(getSubjectHolder(), Generator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}

			@Override
			protected void setValue_(String value) {
				this.subject.setName(value);
			}
		};
	}


	
	/**
	 * Creates the labeled spinner responsible to edit the allocation size. The
	 * default value will be shown after the spinner. A value of -1 means the
	 * default value and the model has <code>null</code>.
	 *
	 * @param container The parent container
	 */
	protected void initializeAllocationSizeWidgets(Composite container) {

		Spinner spinner = addLabeledSpinner(
			container,
			JptUiMappingsMessages.GeneratorComposite_allocationSize,
			buildAllocationSizeHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			addDefaultAllocationSizeLabel(container),
			null
		);

		updateGridData(container, spinner);
	}

	private WritablePropertyValueModel<Integer> buildAllocationSizeHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getSpecifiedAllocationSize();
				if (value == null) {
					return -1;
				}
				return value;
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedAllocationSize(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultAllocationSizeHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.DEFAULT_ALLOCATION_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultAllocationSize();
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

	private Control addDefaultAllocationSizeLabel(Composite container) {

		Label label = addLabel(
			container,
			JptUiMappingsMessages.DefaultEmpty
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultAllocationSizeLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultAllocationSizeLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultAllocationSizeHolder()) {

			@Override
			protected String transform(Integer value) {

				if (value != null) {
					return NLS.bind(JptUiMappingsMessages.DefaultWithOneParam, value);
				}

				return "";
			}
		};
	}
	/**
	 * Creates the labeled spinner responsible to edit the initial value. The
	 * default value will be shown after the spinner. A value of -1 means the
	 * default value and the model has <code>null</code>.
	 *
	 * @param container The parent container
	 */
	protected void initializeInitialValueWidgets(Composite container) {

		Spinner spinner = addLabeledSpinner(
			container,
			JptUiMappingsMessages.GeneratorComposite_initialValue,
			buildInitialValueHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			addDefaultInitialValueLabel(container),
			null
		);


		updateGridData(container, spinner);
	}
	
	private WritablePropertyValueModel<Integer> buildInitialValueHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.SPECIFIED_INITIAL_VALUE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getSpecifiedInitialValue();

				if (value == null) {
					return -1;
				}

				return value;
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedInitialValue(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultInitialValueHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.DEFAULT_INITIAL_VALUE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultInitialValue();
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

	private Control addDefaultInitialValueLabel(Composite container) {

		Label label = addLabel(
			container,
			JptUiMappingsMessages.DefaultEmpty
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultInitialValueLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultInitialValueLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultInitialValueHolder()) {

			@Override
			protected String transform(Integer value) {

				if (value != null) {
					return NLS.bind(JptUiMappingsMessages.DefaultWithOneParam, value);
				}

				return "";
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

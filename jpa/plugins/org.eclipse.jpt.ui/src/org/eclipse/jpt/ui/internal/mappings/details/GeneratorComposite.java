/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.GeneratorHolder;
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
 * This is the generic pane for a generator.
 *
 * @see IdMapping
 * @see Generator
 * @see SequenceGeneratorComposite - A sub-pane
 * @see TalbeGeneratorComposite - A sub-pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class GeneratorComposite<T extends Generator> extends Pane<GeneratorHolder>
{
	private PropertyValueModel<Generator> generatorHolder;
	/**
	 * Creates a new <code>GeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratorComposite(Pane<? extends GeneratorHolder> parentPane,
                             Composite parent) {

		super(parentPane, parent, false);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.generatorHolder = buildGeneratorHolder();
		
	}
	private PropertyValueModel<Generator> buildGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorHolder, Generator>(getSubjectHolder(), getPropertyName()) {
			@Override
			protected Generator buildValue_() {
				return GeneratorComposite.this.getGenerator(this.subject);
			}
		};
	}

	/**
	 * Retrieves without creating the <code>Generator</code> from the subject.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> or <code>null</code> if it doesn't
	 * exists
	 */
	protected abstract T getGenerator(GeneratorHolder subject);

	private WritablePropertyValueModel<Integer> buildAllocationSizeHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(this.generatorHolder, Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getSpecifiedAllocationSize();
				if (value == null) {
					return -1;
				}
				return value;
			}

			@Override
			public void setValue(Integer value) {
				if (subject != null) {
					setValue_(value);
					return;
				}
				if (value == -1) {
					return;
				}
				retrieveGenerator(getSubject()).setSpecifiedAllocationSize(value);
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
		return new PropertyAspectAdapter<Generator, Integer>(this.generatorHolder, Generator.DEFAULT_ALLOCATION_SIZE_PROPERTY) {
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

	private WritablePropertyValueModel<Integer> buildDefaultInitialValueHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(this.generatorHolder, Generator.DEFAULT_INITIAL_VALUE_PROPERTY) {
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
	 * Creates the new <code>IGenerator</code>.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The newly created <code>IGenerator</code>
	 */
	protected abstract T buildGenerator(GeneratorHolder subject);

	protected final WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<Generator, String>(this.generatorHolder, Generator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			public void setValue(String value) {
				if (subject != null) {
					setValue_(value);
					return;
				}
				if (value.length() == 0) {
					return;
				}
				retrieveGenerator(getSubject()).setName(value);
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setName(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildInitialValueHolder() {
		return new PropertyAspectAdapter<Generator, Integer>(this.generatorHolder, Generator.SPECIFIED_INITIAL_VALUE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getSpecifiedInitialValue();

				if (value == null) {
					return -1;
				}

				return value;
			}

			@Override
			public void setValue(Integer value) {
				if (subject != null) {
					setValue_(value);
					return;
				}
				if (value == -1) {
					return;
				}
				retrieveGenerator(getSubject()).setSpecifiedInitialValue(value);
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

	/**
	 * Retrieves without creating the <code>Generator</code> from the subject.
	 *
	 * @return The <code>Generator</code> or <code>null</code> if it doesn't
	 * exists
	 */
	protected final T getGenerator() {
		return (this.getSubject() == null) ? null : this.getGenerator(this.getSubject());
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

	/**
	 * Retrieves the JPA project.
	 *
	 * @return The JPA project or <code>null</code> if the subject is <code>null</code>
	 */
	protected final JpaProject getJpaProject() {
		return this.getSubject() == null ? null : this.getSubject().getJpaProject();
	}

	/**
	 * Returns the property name used to listen to the ID mapping when the
	 * generator changes.
	 *
	 * @return The property name associated with the generator
	 */
	protected abstract String getPropertyName();

	/**
	 * Retrieves the <code>Generator</code> and if it is <code>null</code>, then
	 * create it.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> which should never be <code>null</code>
	 */
	protected final T retrieveGenerator(GeneratorHolder subject) {
		T generator = this.getGenerator(subject);

		if (generator == null) {
			generator = this.buildGenerator(subject);
		}

		return generator;
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
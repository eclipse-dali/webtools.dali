/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.swt.widgets.Composite;

/**
 * This is the generic pane for a generator.
 *
 * @see IdMapping
 * @see Generator
 * @see SequenceGeneratorComposite - A sub-pane
 * @see TalbeGeneratorComposite - A sub-pane
 *
 * @version 2.2
 * @since 1.0
 */
public abstract class GeneratorComposite<T extends DatabaseGenerator> extends Pane<T>
{

	protected GeneratorBuilder<T> generatorBuilder;

	protected GeneratorComposite(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent,
        GeneratorBuilder<T> generatorBuilder) {

		super(parentPane, subjectHolder, parent);
		this.generatorBuilder = generatorBuilder;
	}

	/**
	 * Creates a new Generator.  This makes it possible for the user
	 * to set values on a Generator before the model object has been created.
	 * Allows them not to first have to check the check box to enable the panel.
	 */
	protected final T buildGenerator() {
		return this.generatorBuilder.addGenerator();
	}

	/**
	 * Retrieves the <code>Generator</code> and if it is <code>null</code>, then
	 * create it.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> which should never be <code>null</code>
	 */
	protected final T retrieveGenerator() {
		T generator = getSubject();

		if (generator == null) {
			generator = this.buildGenerator();
		}

		return generator;
	}

	protected final ModifiablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<Generator, String>(getSubjectHolder(), Generator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}

			@Override
			public void setValue(String value) {
				if (this.subject != null) {
					setValue_(value);
					return;
				}
				if (value.length() == 0) {
					return;
				}
				retrieveGenerator().setName(value);
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setName(value);
			}
		};
	}

	protected void addAllocationSizeCombo(Composite container) {
		new IntegerCombo<DatabaseGenerator>(this, getSubjectHolder(), container) {

			@Override
			protected String getHelpId() {
				return null;//JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<DatabaseGenerator, Integer>(getSubjectHolder(), DatabaseGenerator.DEFAULT_ALLOCATION_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultAllocationSize());
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<DatabaseGenerator, Integer>(getSubjectHolder(), DatabaseGenerator.SPECIFIED_ALLOCATION_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedAllocationSize();
					}

					@Override
					public void setValue(Integer value) {
						retrieveGenerator().setSpecifiedAllocationSize(value);
					}
				};
			}
		};	
	}
	
	protected void addInitialValueCombo(Composite container) {
		new IntegerCombo<DatabaseGenerator>(this, getSubjectHolder(), container) {
			@Override
			protected String getHelpId() {
				return null;//JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<DatabaseGenerator, Integer>(getSubjectHolder(), DatabaseGenerator.DEFAULT_INITIAL_VALUE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultInitialValue());
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<DatabaseGenerator, Integer>(getSubjectHolder(), DatabaseGenerator.SPECIFIED_INITIAL_VALUE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedInitialValue();
					}

					@Override
					public void setValue(Integer value) {
						retrieveGenerator().setSpecifiedInitialValue(value);
					}
				};
			}
		};	
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


	public interface GeneratorBuilder<T> {
		/**
		 * Add a generator to the model and return it
		 */
		T addGenerator();
	}
}
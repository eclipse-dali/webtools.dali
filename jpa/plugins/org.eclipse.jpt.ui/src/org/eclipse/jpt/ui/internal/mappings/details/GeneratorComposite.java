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
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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
public abstract class GeneratorComposite<T extends Generator> extends Pane<GeneratorContainer>
{
	private PropertyValueModel<Generator> generatorHolder;

	protected GeneratorComposite(
		Pane<? extends GeneratorContainer> parentPane,
		Composite parent) {

		super(parentPane, parent, false);
	}
	
	protected GeneratorComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent, false);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.generatorHolder = buildGeneratorHolder();
		
	}
	private PropertyValueModel<Generator> buildGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, Generator>(getSubjectHolder(), getPropertyName()) {
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
	protected abstract T getGenerator(GeneratorContainer subject);


	/**
	 * Creates the new <code>IGenerator</code>.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The newly created <code>IGenerator</code>
	 */
	protected abstract T buildGenerator(GeneratorContainer subject);

	protected final WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<Generator, String>(this.generatorHolder, Generator.NAME_PROPERTY) {
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
				retrieveGenerator(getSubject()).setName(value);
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

	/**
	 * Retrieves without creating the <code>Generator</code> from the subject.
	 *
	 * @return The <code>Generator</code> or <code>null</code> if it doesn't
	 * exists
	 */
	protected final T getGenerator() {
		return (this.getSubject() == null) ? null : this.getGenerator(this.getSubject());
	}

	protected void addAllocationSizeCombo(Composite container) {
		new IntegerCombo<Generator>(this, this.generatorHolder, container) {
			
			@Override
			protected String getLabelText() {
				return JptUiMappingsMessages.GeneratorComposite_allocationSize;
			}
		
			@Override
			protected String getHelpId() {
				return null;//JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.DEFAULT_ALLOCATION_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultAllocationSize());
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedAllocationSize();
					}

					@Override
					public void setValue(Integer value) {
						retrieveGenerator(GeneratorComposite.this.getSubject()).setSpecifiedAllocationSize(value);
					}
				};
			}
		};	
	}
	
	protected void addInitialValueCombo(Composite container) {
		new IntegerCombo<Generator>(this, this.generatorHolder, container) {
			
			@Override
			protected String getLabelText() {
				return JptUiMappingsMessages.GeneratorComposite_initialValue;
			}
		
			@Override
			protected String getHelpId() {
				return null;//JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.DEFAULT_INITIAL_VALUE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultInitialValue());
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Generator, Integer>(getSubjectHolder(), Generator.SPECIFIED_INITIAL_VALUE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedInitialValue();
					}

					@Override
					public void setValue(Integer value) {
						retrieveGenerator(GeneratorComposite.this.getSubject()).setSpecifiedInitialValue(value);
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

	/**
	 * Retrieves the <code>Generator</code> and if it is <code>null</code>, then
	 * create it.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> which should never be <code>null</code>
	 */
	protected final T retrieveGenerator(GeneratorContainer subject) {
		T generator = this.getGenerator(subject);

		if (generator == null) {
			generator = this.buildGenerator(subject);
		}

		return generator;
	}

}
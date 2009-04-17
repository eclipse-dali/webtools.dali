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
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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

	protected void addAllocationSizeCombo(Composite container) {
		new IntegerCombo<Generator>(this, container) {
			
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
					protected void setValue_(Integer value) {
						this.subject.setSpecifiedAllocationSize(value);
					}
				};
			}
		};	
	}
	
	protected void addInitialValueCombo(Composite container) {
		new IntegerCombo<Generator>(this, container) {
			
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
					protected void setValue_(Integer value) {
						this.subject.setSpecifiedInitialValue(value);
					}
				};
			}
		};	
	}
}

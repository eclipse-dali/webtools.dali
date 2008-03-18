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

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.GeneratorHolder;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
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
public abstract class GeneratorComposite<T extends Generator> extends AbstractPane<GeneratorHolder>
{
	/**
	 * Creates a new <code>GeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratorComposite(AbstractPane<? extends GeneratorHolder> parentPane,
                             Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates the new <code>IGenerator</code>.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The newly created <code>IGenerator</code>
	 */
	protected abstract T buildGenerator(GeneratorHolder subject);

	private PropertyValueModel<Generator> buildGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorHolder, Generator>(getSubjectHolder(), propertyName()) {
			@Override
			protected Generator buildValue_() {
				return GeneratorComposite.this.generator(subject);
			}
		};
	}

	protected final WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<Generator, String>(buildGeneratorHolder(), Generator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			public void setValue(String value) {
				if ((subject != null) || (value.length() != 0)) {
					retrieveGenerator(subject()).setName(value);
				}
			}
		};
	}

	/**
	 * Retrieves without creating the <code>Generator</code> from the subject.
	 *
	 * @return The <code>Generator</code> or <code>null</code> if it doesn't
	 * exists
	 */
	protected final T generator() {
		return (this.subject() == null) ? null : this.generator(this.subject());
	}

	/**
	 * Retrieves without creating the <code>Generator</code> from the subject.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> or <code>null</code> if it doesn't
	 * exists
	 */
	protected abstract T generator(GeneratorHolder subject);

	/**
	 * Retrieves the JPA project.
	 *
	 * @return The JPA project or <code>null</code> if the subject is <code>null</code>
	 */
	protected final JpaProject jpaProject() {
		return this.subject() == null ? null : this.subject().jpaProject();
	}

	/**
	 * Returns the property name used to listen to the ID mapping when the
	 * generator changes.
	 *
	 * @return The property name associated with the generator
	 */
	protected abstract String propertyName();

	/**
	 * Retrieves the <code>Generator</code> and if it is <code>null</code>, then
	 * create it.
	 *
	 * @param subject The subject used to retrieve the generator
	 * @return The <code>Generator</code> which should never be <code>null</code>
	 */
	protected final T retrieveGenerator(GeneratorHolder subject) {
		T generator = this.generator(subject);

		if (generator == null) {
			generator = this.buildGenerator(subject);
		}

		return generator;
	}
}
/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.SequenceCombo;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                     ----------------------------------------------------- |
 * | Name:               | I                                                 | |
 * |                     ----------------------------------------------------- |
 * |                     ----------------------------------------------------- |
 * | Sequence Generator: | SequenceCombo                                     | |
 * |                     ----------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmSequenceGenerator
 * @see EntityMappings
 * @see OrmGeneratorsComposite - The parent container
 * @see SequenceCombo
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmSequenceGeneratorComposite extends AbstractPane<OrmSequenceGenerator>
{
	/**
	 * Creates a new <code>OrmSequenceGeneratorComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmSequenceGeneratorComposite(AbstractPane<?> parentPane,
	                                     PropertyValueModel<OrmSequenceGenerator> subjectHolder,
	                                     Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	private WritablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<OrmSequenceGenerator, String>(getSubjectHolder(), OrmSequenceGenerator.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			public void setValue_(String value) {
				subject.setName(value);
			}
		};
	}

	private SequenceCombo<OrmSequenceGenerator> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<OrmSequenceGenerator>(this, parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmSequenceGenerator.DEFAULT_SEQUENCE_NAME_PROPERTY);
				propertyNames.add(OrmSequenceGenerator.SPECIFIED_SEQUENCE_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return JptUiMappingsMessages.SequenceGeneratorComposite_default;
			}

			@Override
			protected Schema schema() {
				// TODO
				return null;
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedSequenceName(value);
			}

			@Override
			protected String value() {
				return subject().getSpecifiedSequenceName();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		buildLabeledText(
			container,
			JptUiOrmMessages.OrmSequenceGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		buildLabeledComposite(
			container,
			JptUiOrmMessages.OrmSequenceGeneratorComposite_sequence,
			buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);
	}
}

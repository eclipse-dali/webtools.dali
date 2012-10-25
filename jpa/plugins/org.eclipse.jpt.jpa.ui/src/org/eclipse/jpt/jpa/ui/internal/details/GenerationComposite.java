/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | v Table Generator                                                         |
 * |                                                                           |
 * |   x Table Generator                                                       |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | TableGeneratorComposite                                             | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * | v Sequence Generator                                                      |
 * |                                                                           |
 * |   x Sequence Generator                                                    |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | SequenceGeneratorComposite                                          | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see GeneratorContainer
 * @see TableGeneratorComposite
 * @see SequenceGeneratorComposite
 * @see AbstractEntityComposite - The parent container
 *
 * @version 2.2
 * @since 2.0
 */
public class GenerationComposite extends Pane<GeneratorContainer>
{

	//These are built to stand alone because we do not want the panels to collapse just
	//because the generator is removed either in the source or using the check box in the UI.  
	//We don't want these to be built on the model generator properties.
	private ModifiablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private ModifiablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;

	
	public GenerationComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.sequenceGeneratorExpansionStateHolder = new SimplePropertyValueModel<Boolean>(Boolean.FALSE);
		this.tableGeneratorExpansionStateHolder    = new SimplePropertyValueModel<Boolean>(Boolean.FALSE);
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.sequenceGeneratorExpansionStateHolder.setValue(Boolean.valueOf(getSubject() != null && getSubject().getSequenceGenerator() != null));
		this.tableGeneratorExpansionStateHolder   .setValue(Boolean.valueOf(getSubject() != null && getSubject().getTableGenerator() != null));
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeTableGeneratorCollapsibleSection(container);
		this.initializeSequenceGeneratorCollapsibleSection(container);
	}

	protected void initializeSequenceGeneratorCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptUiDetailsMessages.GeneratorsComposite_sequenceGeneratorSection);
		SWTTools.controlExpandedState(this.sequenceGeneratorExpansionStateHolder, section);

		section.setClient(GenerationComposite.this.initializeSequenceGeneratorPane(section));
	}

	private Composite initializeSequenceGeneratorPane(Composite container) {
		Composite client = this.getWidgetFactory().createComposite(container);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = addCheckBox(
				client,
			JptUiDetailsMessages.GeneratorsComposite_sequenceGeneratorCheckBox,
			this.buildSequenceGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);
		GridData gridData = new GridData();
		gridData.verticalIndent = 5;
		sequenceGeneratorCheckBox.setLayoutData(gridData);

		// Sequence Generator pane
		Control sequenceGeneratorComposite = this.addSequenceGeneratorComposite(client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = sequenceGeneratorCheckBox.getBorderWidth() + 16;
		sequenceGeneratorComposite.setLayoutData(gridData);

		return client;
	}
	
	protected Control addSequenceGeneratorComposite(Composite container) {
		return new SequenceGeneratorComposite(
			this,
			this.buildSequenceGeneratorHolder(),
			container,
			this.buildSequenceGeneratorBuilder()
		).getControl();
	}

	protected PropertyValueModel<SequenceGenerator> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, SequenceGenerator>(getSubjectHolder(), GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected SequenceGenerator buildValue_() {
				return this.subject.getSequenceGenerator();
			}
		};
	}

	protected GeneratorBuilder<SequenceGenerator> buildSequenceGeneratorBuilder() {
		return new GeneratorBuilder<SequenceGenerator>() {
			public SequenceGenerator addGenerator() {
				return getSubject().addSequenceGenerator();
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildSequenceGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, Boolean>(getSubjectHolder(), GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getSequenceGenerator() != null);
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value.booleanValue() && (this.subject.getSequenceGenerator() == null)) {
					this.subject.addSequenceGenerator();
				}
				else if (!value.booleanValue() && (this.subject.getSequenceGenerator() != null)) {
					this.subject.removeSequenceGenerator();
				}
			}
		};
	}

	protected void initializeTableGeneratorCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptUiDetailsMessages.GeneratorsComposite_tableGeneratorSection);
		SWTTools.controlExpandedState(this.tableGeneratorExpansionStateHolder, section);

		section.setClient(GenerationComposite.this.initializeTableGeneratorPane(section));
	}

	private Composite initializeTableGeneratorPane(Composite container) {
		Composite client = this.getWidgetFactory().createComposite(container);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));

		Button tableGeneratorCheckBox = addCheckBox(
				client,
			JptUiDetailsMessages.GeneratorsComposite_tableGeneratorCheckBox,
			this.buildTableGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);
		GridData gridData = new GridData();
		gridData.verticalIndent = 5;
		tableGeneratorCheckBox.setLayoutData(gridData);


		// Table Generator pane
		Control tableGeneratorComposite = new TableGeneratorComposite(
			this,
			this.buildTableGeneratorHolder(),
			client,
			this.buildTableGeneratorBuilder()
		).getControl();
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = tableGeneratorCheckBox.getBorderWidth() + 16;
		tableGeneratorComposite.setLayoutData(gridData);

		return client;
	}

	private PropertyValueModel<TableGenerator> buildTableGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, TableGenerator>(getSubjectHolder(), GeneratorContainer.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected TableGenerator buildValue_() {
				return this.subject.getTableGenerator();
			}
		};
	}

	private GeneratorBuilder<TableGenerator> buildTableGeneratorBuilder() {
		return new GeneratorBuilder<TableGenerator>() {
			public TableGenerator addGenerator() {
				return getSubject().addTableGenerator();
			}
		};
	}

	protected PropertyValueModel<Boolean> buildTableGeneratorExpanstionStateHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, Boolean>(getSubjectHolder(), GeneratorContainer.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getTableGenerator() != null);
			}		
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildTableGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, Boolean>(getSubjectHolder(), GeneratorContainer.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getTableGenerator() != null);
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value.booleanValue() && (this.subject.getTableGenerator() == null)) {
					this.subject.addTableGenerator();
				}
				else if (!value.booleanValue() && (this.subject.getTableGenerator() != null)) {
					this.subject.removeTableGenerator();
				}
			}
		};
	}

}

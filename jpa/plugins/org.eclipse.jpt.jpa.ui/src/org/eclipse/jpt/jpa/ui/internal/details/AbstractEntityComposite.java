/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EntityNameComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TableComposite                                                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | IdClassComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Attribute Overrides ------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OverridesComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Secondary Tables ---------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Pane                                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Inheritance --------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | InheritanceComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Queries ------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | QueriesComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Generators ---------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | GeneratorsComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 */
public abstract class AbstractEntityComposite<T extends Entity>
	extends Pane<T>
    implements JpaComposite
{
	protected AbstractEntityComposite(
			PropertyValueModel<? extends T> entityModel,
			Composite parentComposite,
	        WidgetFactory widgetFactory,
	        ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
	}
	
	protected void initializeEntityCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ENTITY_SECTION_TITLE);
		section.setClient(this.initializeEntitySection(section));
	}
	
	protected Control initializeEntitySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		//Table widgets
		TableComposite tableComposite = new TableComposite(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableComposite.getControl().setLayoutData(gridData);

		//Entity name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ENTITY_NAME_COMPOSITE_NAME);
		new EntityNameCombo(this, container);

		//Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container, JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}
	
	protected PropertyValueModel<IdClassReference> buildIdClassReferenceModel() {
		return new PropertyAspectAdapter<T, IdClassReference>(getSubjectHolder()) {
			@Override
			protected IdClassReference buildValue_() {
				return this.subject.getIdClassReference();
			}
		};
	}
	
	protected void initializeQueriesCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ENTITY_COMPOSITE_QUERIES);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeQueriesSection(section));
				}
			}
		});
	}

	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite(this, this.buildQueryContainerModel(), container).getControl();
	}
	
	protected PropertyValueModel<QueryContainer> buildQueryContainerModel() {
		return new PropertyAspectAdapter<T, QueryContainer>(getSubjectHolder()) {
			@Override
			protected QueryContainer buildValue_() {
				return this.subject.getQueryContainer();
			}
		};
	}

	protected void initializeAttributeOverridesCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.OVERRIDES_COMPOSITE_ATTRIBUTE_OVERRIDES_SECTION);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeAttributeOverridesSection(section));
				}
			}
		});
	}
	
	protected Control initializeAttributeOverridesSection(Composite container) {
		return new EntityOverridesComposite(this, container).getControl();
	}

	protected void initializeInheritanceCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ENTITY_COMPOSITE_INHERITANCE);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeInheritanceSection(section));
				}
			}
		});
	}

	protected abstract Control initializeInheritanceSection(Composite container);
	
	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_PRIMARY_KEY_GENERATION_SECTION);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeGeneratorsSection(section));
				}
			}
		});
	}

	protected Control initializeGeneratorsSection(Composite container) {
		return new GenerationComposite(this, this.buildGeneratorContainerModel(), container).getControl();
	}
	
	protected PropertyValueModel<GeneratorContainer> buildGeneratorContainerModel() {
		return new PropertyAspectAdapter<T, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return this.subject.getGeneratorContainer();
			}
		};
	}
	
	protected void initializeSecondaryTablesCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.SECONDARY_TABLES_COMPOSITE_SECONDARY_TABLES);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeSecondaryTablesSection(section));
				}
			}
		});
	}

	protected abstract Control initializeSecondaryTablesSection(Composite container);


	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, SpecifiedAccessReference>(getSubjectHolder()) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

}
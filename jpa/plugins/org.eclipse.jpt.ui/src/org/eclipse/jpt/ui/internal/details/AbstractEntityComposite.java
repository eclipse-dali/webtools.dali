/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.QueryContainer;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

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
 *
 * @see Entity
 * @see EntityNameComposite
 * @see InheritanceComposite
 * @see IdClassComposite
 * @see EntityOverridesComposite
 * @see TableComposite
 * @see GenerationComposite
 *
 * TODO talk to JavaEditor people about what we can do to hook in TabbedProperties for the JavaEditor
 *
 * @version 2.3
 * @since 1.0
 */
public abstract class AbstractEntityComposite<T extends Entity>
	extends Pane<T>
    implements JpaComposite
{
	/**
	 * Creates a new <code>AbstractEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractEntityComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	protected abstract void initializeSecondaryTablesSection(Composite container);
	
	protected abstract void initializeInheritanceSection(Composite container);
	
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
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.EntitySection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeEntitySection(container);
	}
	
	protected void initializeEntitySection(Composite container) {
		new TableComposite(this, container);
		new EntityNameComposite(this, container);
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
	}
	
	protected PropertyValueModel<IdClassReference> buildIdClassReferenceHolder() {
		return new PropertyAspectAdapter<Entity, IdClassReference>(getSubjectHolder()) {
			@Override
			protected IdClassReference buildValue_() {
				return this.subject.getIdClassReference();
			}
		};
	}
	
	protected void initializeQueriesCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.EntityComposite_queries);
		this.initializeQueriesSection(container, buildQueryContainerHolder());
	}
	
	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new QueriesComposite(this, queryContainerHolder, container);
	}
	
	private PropertyValueModel<QueryContainer> buildQueryContainerHolder() {
		return new PropertyAspectAdapter<Entity, QueryContainer>(getSubjectHolder()) {
			@Override
			protected QueryContainer buildValue_() {
				return this.subject.getQueryContainer();
			}
		};
	}
	
	protected void initializeAttributeOverridesCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.OverridesComposite_attributeOverridesSection);
		initializeAttributeOverridesSection(container);
	}
	
	protected void initializeAttributeOverridesSection(Composite container) {
		new EntityOverridesComposite(this, container);
	}
	
	protected void initializeInheritanceCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.EntityComposite_inheritance);
		initializeInheritanceSection(container);
	}
	
	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.IdMappingComposite_primaryKeyGenerationSection);
		initializeGeneratorsSection(container, buildGeneratorContainer());
	}
	
	protected void initializeGeneratorsSection(Composite container, PropertyValueModel<GeneratorContainer> generatorContainerHolder) {
		new GenerationComposite(this, generatorContainerHolder, container);
	}
	
	private PropertyValueModel<GeneratorContainer> buildGeneratorContainer() {
		return new PropertyAspectAdapter<Entity, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return this.subject.getGeneratorContainer();
			}
		};
	}
	
	protected void initializeSecondaryTablesCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.SecondaryTablesComposite_secondaryTables);
		initializeSecondaryTablesSection(container);
	}
}
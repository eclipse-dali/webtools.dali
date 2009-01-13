/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see OverridesComposite
 * @see TableComposite
 * @see GeneratorsComposite
 *
 * TODO talk to JavaEditor people about what we can do to hook in TabbedProperties for the JavaEditor
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractEntityComposite<T extends Entity> extends FormPane<T>
                                                                implements JpaComposite
{
	/**
	 * Creates a new <code>AbstractEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AbstractEntityComposite(PropertyValueModel<? extends T> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected abstract void addSecondaryTablesComposite(Composite container);
	
	protected abstract void addInheritanceComposite(Composite container);

	private PropertyValueModel<Table> buildTableHolder() {
		return new TransformationPropertyValueModel<Entity, Table>(getSubjectHolder()) {
			@Override
			protected Table transform_(Entity value) {
				return value.getTable();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		initializeGeneralPane(container);
		initializeQueriesPane(container);
		initializeInheritancePane(container);
		initializeAttributeOverridesPane(container);
		initializeGeneratorsPane(container);
		initializeSecondaryTablesPane(container);
	}

	protected void initializeGeneralPane(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		// Entity Name widgets
		new EntityNameComposite(
			this,
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin)
		);

		// Table widgets
		new TableComposite(
			this,
			buildTableHolder(),
			container
		);

		// Primary Key Class widgets
		new IdClassComposite(
			this,
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			false
		);
	}

	protected void initializeQueriesPane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.EntityComposite_queries
		);

		new QueriesComposite(this, container);
	}

	protected void initializeAttributeOverridesPane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverridesSection
		);

		new OverridesComposite(this, container);
	}

	protected void initializeInheritancePane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.EntityComposite_inheritance
		);

		addInheritanceComposite(container);
	}

	protected void initializeGeneratorsPane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGenerationSection
		);

		new GeneratorsComposite(this, container);
	}

	protected void initializeSecondaryTablesPane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.SecondaryTablesComposite_secondaryTables
		);

		addSecondaryTablesComposite(container);
	}
}
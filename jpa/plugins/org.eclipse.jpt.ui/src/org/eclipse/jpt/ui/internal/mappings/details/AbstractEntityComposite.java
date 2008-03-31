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
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
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
public abstract class AbstractEntityComposite<T extends Entity> extends AbstractFormPane<T>
                                                                implements JpaComposite<T>
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

	protected abstract void buildSecondaryTablesComposite(Composite container);

	private PropertyValueModel<Table> buildTableHolder() {
		return new TransformationPropertyValueModel<Entity, Table>(getSubjectHolder()) {
			@Override
			protected Table transform_(Entity value) {
				return value.getTable();
			}
		};
	}

	private void initializeAttributeOverridesPane(Composite container) {

		container = buildCollapsableSection(
			buildSubPane(container, 5),
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		new OverridesComposite(this, container);
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Entity Name widgets
		new EntityNameComposite(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin)
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
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin)
		);
	}

	private void initializeGeneratorsPane(Composite container) {

		container = buildCollapsableSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGeneration
		);

		new GeneratorsComposite(this, container);
	}

	private void initializeInheritancePane(Composite container) {

		container = buildCollapsableSection(
			container,
			JptUiMappingsMessages.EntityComposite_inheritance
		);

		new InheritanceComposite(this, container);
	}

	@Override
	protected void initializeLayout(Composite container) {

		initializeGeneralPane(container);
		initializeAttributeOverridesPane(container);
		initializeSecondaryTablesPane(container);
		initializeInheritancePane(container);
		initializeQueriesPane(container);
		initializeGeneratorsPane(container);
	}

	private void initializeQueriesPane(Composite container) {

		container = buildCollapsableSection(
			container,
			JptUiMappingsMessages.EntityComposite_queries
		);

		new QueriesComposite(this, container);
	}

	private void initializeSecondaryTablesPane(Composite container) {

		container = buildCollapsableSection(
			container,
			JptUiMappingsMessages.SecondaryTablesComposite_secondaryTables
		);

		buildSecondaryTablesComposite(container);
	}
}
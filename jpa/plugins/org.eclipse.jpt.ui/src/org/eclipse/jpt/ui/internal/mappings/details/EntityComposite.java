/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EntityNameCombo                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TableComposite                                                        | |
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
 * | | SecondaryTablesComposite                                              | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Inheritance --------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | InheritanceComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IBasicMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see EntityNameCombo
 * @see InheritanceComposite
 * @see OverridesComposite
 * @see SecondaryTablesComposite
 * @see TableComposite
 *
 * TODO talk to JavaEditor people about what we can do to hook in TabbedProperties for the JavaEditor
 *
 * @version 2.0
 * @since 1.0
 */
public class EntityComposite extends AbstractFormPane<IEntity>
                             implements IJpaComposite<IEntity>
{
	/**
	 * Creates a new <code>EntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EntityComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                       Composite parent,
	                       TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<ITable> buildTableHolder() {
		return new TransformationPropertyValueModel<IEntity, ITable>(getSubjectHolder()) {
			@Override
			protected ITable transform_(IEntity value) {
				return value.getTable();
			}
		};
	}

	private void initializeAttributeOverridesPane(Composite container) {

		container = buildSection(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		new OverridesComposite(this, container);
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = groupBoxMargin();
		EntityNameCombo entityNameCombo = new EntityNameCombo(this, container);

		// Entity Name widgets
		buildLabeledComposite(
			buildSubPane(container, 1, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.EntityGeneralSection_name,
			entityNameCombo.getControl(),
			IJpaHelpContextIds.ENTITY_NAME
		);

		// Table widgets
		new TableComposite(this, buildTableHolder(), container);
	}

	private void initializeInheritancePane(Composite container) {

		container = buildSection(
			container,
			JptUiMappingsMessages.EntityComposite_inheritance
		);

		new InheritanceComposite(this, container);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		initializeGeneralPane(container);
		initializeAttributeOverridesPane(container);
		initializeSecondaryTablesPane(container);
		initializeInheritancePane(container);
	}

	private void initializeSecondaryTablesPane(Composite container) {

		container = buildSection(
			container,
			JptUiMappingsMessages.SecondaryTablesComposite_secondaryTables
		);

		new SecondaryTablesComposite(this, container);
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractSecondaryTablesComposite;
import org.eclipse.jpt.ui.internal.mappings.details.PrimaryKeyJoinColumnsInSecondaryTableComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PrimaryKeyJoinColumnsInSecondaryTableComposite                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see EntityComposite - The container of this pane
 * @see AddRemoveListPane
 * @see PrimaryKeyJoinColumnsInSecondaryTableComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JavaSecondaryTablesComposite extends AbstractSecondaryTablesComposite<JavaEntity>
{
	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JavaSecondaryTablesComposite(AbstractFormPane<? extends JavaEntity> parentPane,
	                                Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaSecondaryTablesComposite(PropertyValueModel<? extends JavaEntity> subjectHolder,
	                                Composite parent,
	                                TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private ListValueModel<SecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<SecondaryTable>(buildSecondaryTablesListHolder(), 
			Table.SPECIFIED_NAME_PROPERTY);
	}	

	private ListValueModel<SecondaryTable> buildSecondaryTablesListHolder() {
		return new ListAspectAdapter<Entity, SecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<SecondaryTable> listIterator_() {
				return subject.specifiedSecondaryTables();
			}

			@Override
			protected int size_() {
				return subject.specifiedSecondaryTablesSize();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		WritablePropertyValueModel<SecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<Entity>(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildSecondaryTablesAdapter(),
			buildSecondaryTablesListModel(),
			secondaryTableHolder,
			buildSecondaryTableLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS//TODO need a help context id for this
		);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			secondaryTableHolder,
			container
		);
	}
}
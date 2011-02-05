/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import java.util.ListIterator;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractSecondaryTablesComposite;
import org.eclipse.jpt.ui.internal.details.PrimaryKeyJoinColumnsInSecondaryTableComposite;
import org.eclipse.swt.widgets.Composite;

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
	public JavaSecondaryTablesComposite(Pane<? extends JavaEntity> parentPane,
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
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private ListValueModel<JavaSecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<JavaSecondaryTable>(buildSecondaryTablesListHolder(), 
			ReadOnlyTable.SPECIFIED_NAME_PROPERTY);
	}	

	private ListValueModel<JavaSecondaryTable> buildSecondaryTablesListHolder() {
		return new ListAspectAdapter<JavaEntity, JavaSecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<JavaSecondaryTable> listIterator_() {
				return this.subject.specifiedSecondaryTables();
			}

			@Override
			protected int size_() {
				return this.subject.specifiedSecondaryTablesSize();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		WritablePropertyValueModel<SecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<Entity>(
			this,
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
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
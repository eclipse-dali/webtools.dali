/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractSecondaryTablesComposite;
import org.eclipse.jpt.jpa.ui.internal.details.PrimaryKeyJoinColumnsInSecondaryTableComposite;
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

	private ListValueModel<JavaSecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<JavaSecondaryTable>(buildSecondaryTablesListHolder(), 
			ReadOnlyTable.SPECIFIED_NAME_PROPERTY);
	}	

	private ListValueModel<JavaSecondaryTable> buildSecondaryTablesListHolder() {
		return new ListAspectAdapter<JavaEntity, JavaSecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterable<JavaSecondaryTable> getListIterable() {
				return this.subject.getSpecifiedSecondaryTables();
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedSecondaryTablesSize();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		ModifiableCollectionValueModel<SecondaryTable> selectedSecondaryTablesModel =
			buildSelectedSecondaryTablesModel();

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<Entity, SecondaryTable>(
			this,
			container,
			buildSecondaryTablesAdapter(),
			buildSecondaryTablesListModel(),
			selectedSecondaryTablesModel,
			buildSecondaryTableLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS//TODO need a help context id for this
		);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			buildSelectedSecondaryTableModel(selectedSecondaryTablesModel),
			container
		);
	}
}
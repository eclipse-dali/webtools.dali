/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractSecondaryTablesComposite;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.PrimaryKeyJoinColumnsInSecondaryTableComposite;
import org.eclipse.jpt.ui.internal.details.SecondaryTableDialog;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see OrmEntity
 * @see OrmEntityComposite - The container of this pane
 * @see AddRemoveListPane
 * @see PrimaryKeyJoinColumnsInSecondaryTableComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmSecondaryTablesComposite extends AbstractSecondaryTablesComposite<OrmEntity>
{
	/**
	 * Creates a new <code>OrmSecondaryTablesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrmSecondaryTablesComposite(FormPane<? extends OrmEntity> parentPane,
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
	public OrmSecondaryTablesComposite(PropertyValueModel<? extends OrmEntity> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private WritablePropertyValueModel<Boolean> buildDefineInXmlHolder() {
		return new DefineInXmlHolder();
	}

	private ListValueModel<OrmSecondaryTable> buildSecondaryTablesListHolder() {
		List<ListValueModel<OrmSecondaryTable>> list = new ArrayList<ListValueModel<OrmSecondaryTable>>();
		list.add(buildSpecifiedSecondaryTablesListHolder());
		list.add(buildVirtualSecondaryTablesListHolder());
		return new CompositeListValueModel<ListValueModel<OrmSecondaryTable>, OrmSecondaryTable>(list);
	}

	private ListValueModel<OrmSecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<OrmSecondaryTable>(buildSecondaryTablesListHolder(),
			Table.SPECIFIED_NAME_PROPERTY);
	}

	private ListValueModel<OrmSecondaryTable> buildSpecifiedSecondaryTablesListHolder() {
		return new ListAspectAdapter<OrmEntity, OrmSecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<OrmSecondaryTable> listIterator_() {
				return subject.specifiedSecondaryTables();
			}

			@Override
			protected int size_() {
				return subject.specifiedSecondaryTablesSize();
			}
		};
	}

	private ListValueModel<OrmSecondaryTable> buildVirtualSecondaryTablesListHolder() {
		return new ListAspectAdapter<OrmEntity, OrmSecondaryTable>(getSubjectHolder(), OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<OrmSecondaryTable> listIterator_() {
				return subject.virtualSecondaryTables();
			}

			@Override
			protected int size_() {
				return subject.virtualSecondaryTablesSize();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		WritablePropertyValueModel<SecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		WritablePropertyValueModel<Boolean> defineInXmlHolder =
			buildDefineInXmlHolder();

		// Override Define In XML check box
		addCheckBox(
			addSubPane(container, 0, groupBoxMargin),
			JptUiDetailsMessages.OrmSecondaryTablesComposite_defineInXml,
			defineInXmlHolder,
			null
		);

		// Secondary Tables add/remove list pane
		AddRemoveListPane<Entity> listPane = new AddRemoveListPane<Entity>(
			this,
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildSecondaryTablesAdapter(),
			buildSecondaryTablesListModel(),
			secondaryTableHolder,
			buildSecondaryTableLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS//TODO need a help context id for this
		);

		installListPaneEnabler(defineInXmlHolder, listPane);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			secondaryTableHolder,
			container
		);
	}
	
	private void installListPaneEnabler(WritablePropertyValueModel<Boolean> defineInXmlHolder,
	                                    AddRemoveListPane<Entity> listPane) {

		new PaneEnabler(defineInXmlHolder, listPane);
	}
	
	@Override
	protected SecondaryTableDialog buildSecondaryTableDialogForAdd() {
		// defaultSchema and defaultCatalog should not be taken from the Table in this case.  
		// The table default schema could be what is the specified schema on the java table.
		return new SecondaryTableDialog(
			getControl().getShell(), getSubject().getJpaProject(), 
			getSubject().getMappingFileRoot().getCatalog(), 
			getSubject().getMappingFileRoot().getSchema());
	}

	private class DefineInXmlHolder extends ListPropertyValueModelAdapter<Boolean>
		implements WritablePropertyValueModel<Boolean> {

		public DefineInXmlHolder() {
			super(buildVirtualSecondaryTablesListHolder());
		}

		@Override
		protected Boolean buildValue() {
			if (getSubject() == null) {
				return Boolean.FALSE;
			}
			return Boolean.valueOf(getSubject().secondaryTablesDefinedInXml());
		}

		public void setValue(Boolean value) {
			getSubject().setSecondaryTablesDefinedInXml(value.booleanValue());
		}
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractSecondaryTablesComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.PrimaryKeyJoinColumnsInSecondaryTableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.SecondaryTableDialog;
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
	public OrmSecondaryTablesComposite(Pane<? extends OrmEntity> parentPane,
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

	private ModifiablePropertyValueModel<Boolean> buildDefineInXmlHolder() {
		return new DefineInXmlHolder();
	}

	private ListValueModel<ReadOnlySecondaryTable> buildSecondaryTablesListHolder() {
		List<ListValueModel<? extends ReadOnlySecondaryTable>> list = new ArrayList<ListValueModel<? extends ReadOnlySecondaryTable>>();
		list.add(buildSpecifiedSecondaryTablesListHolder());
		list.add(buildVirtualSecondaryTablesListHolder());
		return new CompositeListValueModel<ListValueModel<? extends ReadOnlySecondaryTable>, ReadOnlySecondaryTable>(list);
	}

	private ListValueModel<ReadOnlySecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<ReadOnlySecondaryTable>(buildSecondaryTablesListHolder(),
			ReadOnlyTable.SPECIFIED_NAME_PROPERTY);
	}

	private ListValueModel<OrmSecondaryTable> buildSpecifiedSecondaryTablesListHolder() {
		return new ListAspectAdapter<OrmEntity, OrmSecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterable<OrmSecondaryTable> getListIterable() {
				return this.subject.getSpecifiedSecondaryTables();
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedSecondaryTablesSize();
			}
		};
	}

	ListValueModel<OrmVirtualSecondaryTable> buildVirtualSecondaryTablesListHolder() {
		return new ListAspectAdapter<OrmEntity, OrmVirtualSecondaryTable>(getSubjectHolder(), OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterable<OrmVirtualSecondaryTable> getListIterable() {
				return this.subject.getVirtualSecondaryTables();
			}

			@Override
			protected int size_() {
				return this.subject.getVirtualSecondaryTablesSize();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		ModifiablePropertyValueModel<SecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		ModifiablePropertyValueModel<Boolean> defineInXmlHolder =
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
	
	private void installListPaneEnabler(ModifiablePropertyValueModel<Boolean> defineInXmlHolder,
	                                    AddRemoveListPane<Entity> listPane) {

		new PaneEnabler(defineInXmlHolder, listPane);
	}
	
	@Override
	protected SecondaryTableDialog buildSecondaryTableDialogForAdd() {
		// defaultSchema and defaultCatalog should not be taken from the Table in this case.  
		// The table default schema could be what is the specified schema on the java table.
		return new SecondaryTableDialog(
			getShell(), getSubject().getJpaProject(), 
			getSubject().getMappingFileRoot().getCatalog(), 
			getSubject().getMappingFileRoot().getSchema());
	}

	private class DefineInXmlHolder extends ListPropertyValueModelAdapter<Boolean>
		implements ModifiablePropertyValueModel<Boolean> {

		public DefineInXmlHolder() {
			super(buildVirtualSecondaryTablesListHolder());
		}

		@Override
		protected Boolean buildValue() {
			if (getSubject() == null) {
				return Boolean.FALSE;
			}
			return Boolean.valueOf(getSubject().secondaryTablesAreDefinedInXml());
		}

		public void setValue(Boolean value) {
			getSubject().setSecondaryTablesAreDefinedInXml(value.booleanValue());
		}
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractSecondaryTablesComposite;
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

	private ModifiablePropertyValueModel<Boolean> buildDefineInXmlModel() {
		return ListValueModelTools.isEmptyModifiablePropertyValueModel(this.buildVirtualSecondaryTablesModel(), new DefineInXmlSetValueClosure());
	}

	private ListValueModel<SecondaryTable> buildSecondaryTablesModel() {
		List<ListValueModel<? extends SecondaryTable>> list = new ArrayList<>();
		list.add(buildSpecifiedSecondaryTablesModel());
		list.add(buildVirtualSecondaryTablesModel());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<SecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<>(buildSecondaryTablesModel(), Table.SPECIFIED_NAME_PROPERTY);
	}

	private ListValueModel<OrmSpecifiedSecondaryTable> buildSpecifiedSecondaryTablesModel() {
		return new ListAspectAdapter<OrmEntity, OrmSpecifiedSecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterable<OrmSpecifiedSecondaryTable> getListIterable() {
				return this.subject.getSpecifiedSecondaryTables();
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedSecondaryTablesSize();
			}
		};
	}

	ListValueModel<OrmVirtualSecondaryTable> buildVirtualSecondaryTablesModel() {
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
	@SuppressWarnings("unused")
	protected void initializeLayout(Composite container) {
		ModifiableCollectionValueModel<SpecifiedSecondaryTable> selectedSecondaryTablesModel =
			buildSelectedSecondaryTablesModel();

		ModifiablePropertyValueModel<Boolean> defineInXmlModel =
			buildDefineInXmlModel();

		// Override Define In XML check box
		addCheckBox(
			container,
			JptJpaUiDetailsMessages.ORM_SECONDARY_TABLES_COMPOSITE_DEFINE_IN_XML,
			defineInXmlModel,
			null
		);

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<Entity, SpecifiedSecondaryTable>(
			this,
			container,
			buildSecondaryTablesAdapter(),
			buildSecondaryTablesListModel(),
			selectedSecondaryTablesModel,
			buildSecondaryTableLabelProvider(),
			defineInXmlModel,
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS//TODO need a help context id for this
		);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			buildSelectedSecondaryTableModel(selectedSecondaryTablesModel),
			container
		);
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

	class DefineInXmlSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			getSubject().setSecondaryTablesAreDefinedInXml(value);
		}
	}
}

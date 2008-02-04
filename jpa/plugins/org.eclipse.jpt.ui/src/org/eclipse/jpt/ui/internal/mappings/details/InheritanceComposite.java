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

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                      ---------------------------------------------------- |
 * | Strategy:            | EnumComboViewer                                  | |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Column:              | I                                              |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Discriminator Type:  | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Discriminator Value: | I                                              |v| |
 * |                      ---------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PrimaryKeyJoinColumnsComposite                                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The parent container
 * @see EnumComboViewer
 * @see PrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class InheritanceComposite extends AbstractFormPane<IEntity> {

	private CCombo columnCombo;
	private CCombo discriminatorValueCombo;

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public InheritanceComposite(AbstractFormPane<? extends IEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public InheritanceComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                            Composite parent,
	                            IWidgetFactory widgetFactory) {

		super(subjectHolder, parent,widgetFactory);
	}

	private void addConnectionListener() {
//		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);

		propertyNames.add(IEntity.DEFAULT_DISCRIMINATOR_VALUE_PROPERTY);
		propertyNames.add(IEntity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY);
	}

    private ModifyListener buildColumnComboSelectionListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CCombo combo = (CCombo) e.widget;
				IDiscriminatorColumn discriminatorColumn = subject().getDiscriminatorColumn();
				String tableText = combo.getText();

				if (tableText.equals("")) {
					tableText = null;
					if (discriminatorColumn.getSpecifiedName() == null || discriminatorColumn.getSpecifiedName().equals("")) {
						return;
					}
				}

				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}

				if (discriminatorColumn.getSpecifiedName() == null && tableText != null) {
					discriminatorColumn.setSpecifiedName(tableText);
				}
				if (discriminatorColumn.getSpecifiedName() != null && !discriminatorColumn.getSpecifiedName().equals(tableText)) {
					discriminatorColumn.setSpecifiedName(tableText);
				}
			}
		};
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						InheritanceComposite.this.populateColumnCombo();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				return;
			}

			public void modified(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						InheritanceComposite.this.populateColumnCombo();
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						InheritanceComposite.this.populateColumnCombo();
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				return;
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(table == getDbTable()) {
							if (!getControl().isDisposed()) {
								InheritanceComposite.this.populateColumnCombo();
							}
						}
					}
				});
			}
		};
    }

	private PropertyValueModel<IDiscriminatorColumn> buildDiscriminatorColumnHolder() {
		return new TransformationPropertyValueModel<IEntity, IDiscriminatorColumn>(getSubjectHolder()) {
			@Override
			protected IDiscriminatorColumn transform_(IEntity value) {
				return value.getDiscriminatorColumn();
			}
		};
	}

	private EnumComboViewer<IDiscriminatorColumn, DiscriminatorType> buildDiscriminatorTypeCombo(Composite container) {

		return new EnumComboViewer<IDiscriminatorColumn, DiscriminatorType>(
			this,
			buildDiscriminatorColumnHolder(),
			container)
		{
			@Override
			protected DiscriminatorType[] choices() {
				return DiscriminatorType.values();
			}

			@Override
			protected DiscriminatorType defaultValue() {
				return subject().getDefaultDiscriminatorType();
			}

			@Override
			protected String displayString(DiscriminatorType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					InheritanceComposite.this,
					value
				);
			}

			@Override
			protected DiscriminatorType getValue() {
				return subject().getSpecifiedDiscriminatorType();
			}

			@Override
			protected String propertyName() {
				return IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY;
			}

			@Override
			protected void setValue(DiscriminatorType value) {
				subject().setSpecifiedDiscriminatorType(value);
			}
		};
	}

	private ModifyListener buildDiscriminatorValueComboSelectionListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CCombo combo = (CCombo) e.widget;
				String value = combo.getText();
				String discriminatorValue = subject().getSpecifiedDiscriminatorValue();

				if (value.equals("")) {
					value = null;
					if (discriminatorValue == null || discriminatorValue.equals("")) {
						return;
					}
				}

				if (value != null && combo.getItemCount() > 0 && value.equals(combo.getItem(0))) {
					value = null;
				}

				if (discriminatorValue == null || !discriminatorValue.equals(value)) {
					subject().setSpecifiedDiscriminatorValue(value);
				}
			}
		};
	}

	private EnumComboViewer<IEntity, InheritanceType> buildStrategyCombo(Composite container) {

		return new EnumComboViewer<IEntity, InheritanceType>(this, container) {
			@Override
			protected InheritanceType[] choices() {
				return InheritanceType.values();
			}

			@Override
			protected InheritanceType defaultValue() {
				return subject().getDefaultInheritanceStrategy();
			}

			@Override
			protected String displayString(InheritanceType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					InheritanceComposite.this,
					value
				);
			}

			@Override
			protected InheritanceType getValue() {
				return subject().getSpecifiedInheritanceStrategy();
			}

			@Override
			protected String propertyName() {
				return IEntity.SPECIFIED_INHERITANCE_STRATEGY_PROPERTY;
			}

			@Override
			protected void setValue(InheritanceType value) {
				subject().setSpecifiedInheritanceStrategy(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateColumnCombo();
		populateDiscriminatorValueCombo();
	}

	private Table getDbTable() {
		return this.subject().primaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Strategy widgets
		EnumComboViewer<IEntity, InheritanceType> strategyViewer =
			buildStrategyCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.InheritanceComposite_strategy,
			strategyViewer.getControl(),
			IJpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY
		);

		// Column widgets
		columnCombo = buildCombo(container);
		columnCombo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		columnCombo.addModifyListener(buildColumnComboSelectionListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_column,
			columnCombo,
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN
		);

		// Discriminator Type widgets
		EnumComboViewer<IDiscriminatorColumn, DiscriminatorType> discriminatorTypeViewer =
			buildDiscriminatorTypeCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_discriminatorType,
			discriminatorTypeViewer.getControl(),
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE
		);

		// Discrinator Value widgets
		discriminatorValueCombo = buildCombo(container);
		discriminatorValueCombo.addModifyListener(buildDiscriminatorValueComboSelectionListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.InheritanceComposite_discriminatorValue,
			discriminatorValueCombo,
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE
		);

		// Primary Key Join Columns widgets
		new PrimaryKeyJoinColumnsComposite(
			this,
			container
		);
	}

	private void populateColumnCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnName();

//		if (this.getConnectionProfile().isConnected()) {
//			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
//			Table table = getDbTable();
//			if (table != null) {
//				for (Iterator i = CollectionTools.sort(CollectionTools.list(table.columnNames())).iterator(); i.hasNext();) {
//					this.columnCombo.add((String) i.next());
//				}
//			}
//		}
//		else {
//			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
//		}

		populateColumnName();
	}

	private void populateColumnName() {
		IDiscriminatorColumn discriminatorColumn = subject().getDiscriminatorColumn();
		String tableName = discriminatorColumn.getSpecifiedName();
		String defaultName = discriminatorColumn.getDefaultName();

		if (tableName != null) {
			if (!this.columnCombo.getText().equals(tableName)) {
				this.columnCombo.setText(tableName);
			}
		}
		else {
			if (!this.columnCombo.getText().equals(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultName))) {
				this.columnCombo.select(0);
			}
		}
	}

	private void populateDefaultColumnName() {
		IDiscriminatorColumn discriminatorColumn = subject().getDiscriminatorColumn();
		String defaultTableName = discriminatorColumn.getDefaultName();
		int selectionIndex = columnCombo.getSelectionIndex();
		columnCombo.setItem(0, NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default column name.  clear the selection and then set it again
			columnCombo.clearSelection();
			columnCombo.select(0);
		}
	}

	private void populateDiscriminatorValueCombo() {
		String specifiedValue = this.subject().getSpecifiedDiscriminatorValue();
		String defaultValue = this.subject().getDefaultDiscriminatorValue();

		//TODO enable/disable based on IEntity.DISCRIMINATOR_VALUE_ALLOWED_PROPERTY
		if (this.subject().isDiscriminatorValueAllowed()) {
			this.discriminatorValueCombo.setEnabled(true);
			if (this.discriminatorValueCombo.getItemCount() == 0) {
				this.discriminatorValueCombo.add(JptUiMappingsMessages.DiscriminatorColumnComposite_defaultEmpty);
			}
			if (defaultValue != null) {
				this.discriminatorValueCombo.setItem(0, NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultValue));
			}
			else {
				this.discriminatorValueCombo.setItem(0, JptUiMappingsMessages.DiscriminatorColumnComposite_defaultEmpty);
			}
		}
		else {
			this.discriminatorValueCombo.setEnabled(false);
			if (this.discriminatorValueCombo.getItemCount() == 1) {
				this.discriminatorValueCombo.setText("");
				this.discriminatorValueCombo.removeAll();
			}
		}

		if (specifiedValue != null) {
			if (!this.discriminatorValueCombo.getText().equals(specifiedValue)) {
				this.discriminatorValueCombo.setText(specifiedValue);
			}
		}
		else {
			//combo text does not update when switching between 2 entities that both have a default discriminator value.
			//clear the selection and then set it again
			this.discriminatorValueCombo.clearSelection();
			this.discriminatorValueCombo.select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IEntity.DEFAULT_DISCRIMINATOR_VALUE_PROPERTY ||
		    propertyName == IEntity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY)
		{
			populateDiscriminatorValueCombo();
		}
	}

	private void removeConnectionListener() {
//		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}
}
/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;

import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.details.db.TableCombo;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - Table ----------------------------------------------------------------- |
 * | |          ------------------------------------------------------------ | |
 * | | Table:   | TableCombo                                               | | |
 * | |          ------------------------------------------------------------ | |
 * | |          ------------------------------------------------------------ | |
 * | | Catalog: | CatalogCombo                                             | | |
 * | |          ------------------------------------------------------------ | |
 * | |          ------------------------------------------------------------ | |
 * | | Schema:  | SchemaCombo                                              | | |
 * | |          ------------------------------------------------------------ | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Table
 * @see EntityComposite - The parent container
 * @see TableCombo
 * @see CatalogCombo
 * @see SchemaCombo
 *
 * @TODO repopulate this panel based on the Entity table changing
 *
 * @version 2.0
 * @since 1.0
 */
public class TableComposite extends Pane<Entity>
{
	/**
	 * Creates a new <code>TableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 */
	public TableComposite(Pane<? extends Entity> parentPane,
	                      Composite parent) {

		super(parentPane, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Table group pane
		Group tableGroupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.TableComposite_tableSection
		);

		PropertyValueModel<Table> subjectHolder = buildTableHolder();
		// Table widgets
		addLabeledComposite(
			tableGroupPane,
			JptUiDetailsMessages.TableChooser_label,
			addTableCombo(subjectHolder, tableGroupPane),
			JpaHelpContextIds.ENTITY_TABLE
		);

		// Catalog widgets
		addLabeledComposite(
			tableGroupPane,
			JptUiDetailsMessages.CatalogChooser_label,
			addCatalogCombo(subjectHolder, tableGroupPane),
			JpaHelpContextIds.ENTITY_CATALOG
		);

		// Schema widgets
		addLabeledComposite(
			tableGroupPane,
			JptUiDetailsMessages.SchemaChooser_label,
			addSchemaCombo(subjectHolder, tableGroupPane),
			JpaHelpContextIds.ENTITY_SCHEMA
		);
		
		new PaneEnabler(buildTableEnabledHolder(), this);
	}
	
	protected WritablePropertyValueModel<Table> buildTableHolder() {
		
		return new PropertyAspectAdapter<Entity, Table>(getSubjectHolder(), Entity.TABLE_IS_UNDEFINED_PROPERTY) {
			@Override
			protected Table buildValue_() {
				return this.subject.tableIsUndefined() ? null : this.subject.getTable();
			}
		};
	}
	
	protected WritablePropertyValueModel<Boolean> buildTableEnabledHolder() {
		return new PropertyAspectAdapter<Entity, Boolean>(getSubjectHolder(), Entity.SPECIFIED_TABLE_IS_ALLOWED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.specifiedTableIsAllowed());
			}
		};
	}

	private CatalogCombo<Table> addCatalogCombo(PropertyValueModel<Table> tableHolder, Composite container) {

		return new CatalogCombo<Table>(this, tableHolder, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(Table.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultCatalog();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedCatalog();
			}
		};
	}

	private SchemaCombo<Table> addSchemaCombo(PropertyValueModel<Table> subjectHolder, Composite container) {

		return new SchemaCombo<Table>(this, subjectHolder, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(Table.SPECIFIED_SCHEMA_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultSchema();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedSchema();
			}

			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				return this.getSubject().getDbSchemaContainer();
			}
		};
	}

	private TableCombo<Table> addTableCombo(PropertyValueModel<Table> subjectHolder, Composite container) {

		return new TableCombo<Table>(this, subjectHolder, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Table.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(Table.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(Table.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(Table.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(Table.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				super.propertyChanged(propertyName);
				if (propertyName == Table.DEFAULT_SCHEMA_PROPERTY 
					|| propertyName == Table.SPECIFIED_SCHEMA_PROPERTY
					|| propertyName == Table.DEFAULT_CATALOG_PROPERTY
					|| propertyName == Table.SPECIFIED_CATALOG_PROPERTY ) {
					repopulate();
				}
			}
			
			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				this.getSubject().setSpecifiedName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedName();
			}

			@Override
			protected Schema getDbSchema_() {
				return this.getSubject().getDbSchema();
			}
		};
	}

}
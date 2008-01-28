/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.swt.ListBoxModelAdapter;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.part.PageBook;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |         ---------------------------------------------------------------   |
 * |   Name: |                                                           |v|   |
 * |         ---------------------------------------------------------------   |
 * |                                                                           |
 * |   x Override Default Join Columns                                         |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnsComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * |   x Override Default Inverse Join Columns                                 |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnsComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The parent container
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OverridesComposite extends AbstractFormPane<IEntity>
{
	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OverridesComposite(AbstractFormPane<? extends IEntity> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OverridesComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                          Composite parent,
	                          IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(IAssociationOverride subject) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(shell(), subject);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumn(JoinColumnInAssociationOverrideStateObject stateObject) {

		IAssociationOverride associationOverride = stateObject.getAssociationOverride();
		int index = associationOverride.specifiedJoinColumnsSize();

		IJoinColumn joinColumn = associationOverride.addSpecifiedJoinColumn(index);
		joinColumn.setSpecifiedName(stateObject.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(stateObject.getSpecifiedReferencedColumnName());
	}

	private PostExecution<JoinColumnInAssociationOverrideDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInAssociationOverrideDialog>() {
			public void execute(JoinColumnInAssociationOverrideDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.subject());
				}
			}
		};
	}

	private WritablePropertyValueModel<IAssociationOverride> buildAssociationOverrideHolder() {
		return new SimplePropertyValueModel<IAssociationOverride>();
	}

	private PropertyValueModel<IColumn> buildColumnHolder() {
		return new TransformationPropertyValueModel<IEntity, IColumn>(getSubjectHolder()) {
			@Override
			protected IColumn transform_(IEntity value) {
				return null; //TODO
			}
		};
	}

	private PostExecution<JoinColumnInAssociationOverrideDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInAssociationOverrideDialog>() {
			public void execute(JoinColumnInAssociationOverrideDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	private ListValueModel/*<IAssociationOverride>*/ buildOverrideAttributesListHolder() {
		return new ListAspectAdapter<IEntity, IAssociationOverride>(
			getSubjectHolder(),
			IEntity.DEFAULT_ASSOCIATION_OVERRIDES_LIST,
			IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST)
		{
			@Override
			protected ListIterator<IAssociationOverride> listIterator_() {
				return subject.associationOverrides();
			}
		};
	}

	private StringConverter<IAssociationOverride> buildOverrideAttributesStringConverter() {
		return new StringConverter<IAssociationOverride>() {
			public String convertToString(IAssociationOverride item) {
				return item.displayString();
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		return new PropertyAspectAdapter<IEntity, Boolean>(getSubjectHolder(), "") {
			@Override
			protected Boolean buildValue_() {
				return true;
			}

			@Override
			protected void setValue_(Boolean value) {
				// Not done here
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				overrideDefaultButtonSelected(button.getSelection());
			}
		};
	}

	private PageBook buildOverridePageBook(Composite parent) {
		return new PageBook(parent, SWT.NONE);
	}

	private ListValueModel/*<IAssociationOverride>*/ buildSortedOverrideAttributesListHolder() {
		return new SortedListValueModelAdapter<IAssociationOverride>(
			buildOverrideAttributesListHolder()
		);
	}

//	public void doPopulate(EObject obj) {
//		this.entity = (IEntity) obj;
//		if (this.entity == null) {
//			this.selectedOverride = null;
//			this.columnComposite.populate(null);
//			this.joinColumnsComposite.populate(null);
//			this.listViewer.setInput(null);
//			return;
//		}
//
//		if (this.listViewer.getInput() != entity) {
//			this.listViewer.setInput(entity);
//		}
//		if (!this.subject().getAttributeOverrides().isEmpty()) {
//			if (this.listViewer.getSelection().isEmpty()) {
//				IOverride override = this.subject().getAttributeOverrides().get(0);
//				this.listViewer.setSelection(new StructuredSelection(override));
//			}
//			else {
//				Object selection = ((StructuredSelection) this.listViewer.getSelection()).getFirstElement();
//				if (selection instanceof IAttributeOverride) {
//					this.overridePageBook.showPage(this.columnComposite.getControl());
//					this.columnComposite.enableWidgets(true);
//					this.columnComposite.populate(((IAttributeOverride) selection).getColumn());
//					}
//				else {
//					this.overridePageBook.showPage(this.joinColumnsComposite.getControl());
//					this.joinColumnsComposite.enableWidgets(true);
//					this.joinColumnsComposite.populate(new JoinColumnsOwner((IAssociationOverride) selection));
//				}
//			}
//		}
//		else {
//			this.columnComposite.populate(null);
//			this.ge.enableWidgets(false);
//		}
//	}

	private void editJoinColumn(IJoinColumn joinColumn) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(shell(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInAssociationOverrideStateObject stateObject) {

		IJoinColumn joinColumn = stateObject.getJoinColumn();
		String name = stateObject.getSelectedName();
		String referencedColumnName = stateObject.getSpecifiedReferencedColumnName();

		// Name
		if (stateObject.isDefaultNameSelected()) {
			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null ||
		        !joinColumn.getSpecifiedName().equals(name)){

			joinColumn.setSpecifiedName(name);
		}

		// Referenced Column Name
		if (stateObject.isDefaultReferencedColumnNameSelected()) {
			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null ||
		        !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)) {

			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		WritablePropertyValueModel<IAssociationOverride> associationOverrideHolder =
			buildAssociationOverrideHolder();

		// Attribute Overrides group pane
		container = buildTitledPane(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		List list = buildList(
			container,
			IJpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES
		);

		ListBoxModelAdapter.adapt(
			buildSortedOverrideAttributesListHolder(),
			associationOverrideHolder,
			list,
			buildOverrideAttributesStringConverter()
		);

		// Override Default check box
		Button overrideDefaultButton = buildCheckBox(
		container,
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			buildOverrideDefaultHolder()
		);

		overrideDefaultButton.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		// Override sub-pane
		PageBook overridePane = buildOverridePageBook(container);

		// Join Columns widgets
		Group joinColumnGroupPane = buildTitledPane(
			overridePane,
			JptUiMappingsMessages.OverridesComposite_joinColumn
		);

		JoinColumnsComposite<IAssociationOverride> joinColumnsComposite =
			new JoinColumnsComposite<IAssociationOverride>(
				this,
				associationOverrideHolder,
				joinColumnGroupPane,
				buildJoinColumnsEditor()
			);

		// Column widgets
		new ColumnComposite(
			this,
			buildColumnHolder(),
			overridePane
		);

		overridePane.showPage(joinColumnsComposite.getControl());
	}

	private void overrideDefaultButtonSelected(boolean selected) {

		IEntity entity = subject();
		IOverride override = null;

		if (selected) {
			if (override instanceof IAttributeOverride) {
				int index = entity.specifiedAttributeOverridesSize();
				IAttributeOverride attributeOverride = entity.addSpecifiedAttributeOverride(index);
				attributeOverride.setName(override.getName());
				attributeOverride.getColumn().setSpecifiedName(((IAttributeOverride) override).getColumn().getName());
			}
			else {
				int index = entity.specifiedAssociationOverridesSize();
				IAssociationOverride associationOverride = entity.addSpecifiedAssociationOverride(index);
				associationOverride.setName(override.getName());
				//attributeOverride.getColumn().setSpecifiedName(this.attributeOverride.getColumn().getName());
			}
		}
		else {
			if (override instanceof IAttributeOverride) {
				int index = CollectionTools.indexOf(entity.specifiedAttributeOverrides(), override);
				this.subject().removeSpecifiedAttributeOverride(index);
			}
			else {
				int index = CollectionTools.indexOf(entity.specifiedAssociationOverrides(), override);
				this.subject().removeSpecifiedAssociationOverride(index);
			}
		}
	}

	protected void overridesListSelectionChanged(SelectionChangedEvent event) {
//		if (((StructuredSelection) event.getSelection()).isEmpty()) {
//			this.columnComposite.populate(null);
//			this.columnComposite.enableWidgets(false);
//			this.overrideDefaultButton.setSelection(false);
//			this.overrideDefaultButton.setEnabled(false);
//		}
//		else {
//			this.selectedOverride = getSelectedOverride();
//			if (this.selectedOverride instanceof IAttributeOverride) {
//				boolean specifiedOverride = this.subject().getSpecifiedAttributeOverrides().contains(this.selectedOverride);
//				this.overrideDefaultButton.setSelection(specifiedOverride);
//				this.overridePageBook.showPage(this.columnComposite.getControl());
//				this.columnComposite.populate(((IAttributeOverride) this.selectedOverride).getColumn());
//				this.columnComposite.enableWidgets(specifiedOverride);
//				this.overrideDefaultButton.setEnabled(true);
//			}
//			else {
//				boolean specifiedOverride = this.subject().getSpecifiedAssociationOverrides().contains(this.selectedOverride);
//				this.overrideDefaultButton.setSelection(specifiedOverride);
//				this.overridePageBook.showPage(this.joinColumnsComposite.getControl());
//				this.joinColumnsComposite.populate(new JoinColumnsOwner((IAssociationOverride) getSelectedOverride()));
//				this.joinColumnsComposite.enableWidgets(specifiedOverride);
//				this.overrideDefaultButton.setEnabled(true);
//			}
//		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<IAssociationOverride> {

		public void addJoinColumn(IAssociationOverride subject) {
			OverridesComposite.this.addJoinColumn(subject);
		}

		public void editJoinColumn(IAssociationOverride subject, IJoinColumn joinColumn) {
			OverridesComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IAssociationOverride subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public ListIterator<IJoinColumn> joinColumns(IAssociationOverride subject) {
			return subject.joinColumns();
		}

		public String[] propertyNames() {
			return new String[] {
				IAssociationOverride.DEFAULT_JOIN_COLUMNS_LIST,
				IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST
			};
		}

		public void removeJoinColumns(IAssociationOverride subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}
	}
}
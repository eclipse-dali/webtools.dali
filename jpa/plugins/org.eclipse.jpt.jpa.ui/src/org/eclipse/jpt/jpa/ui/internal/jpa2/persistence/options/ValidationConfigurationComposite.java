/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.options;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  ValidationConfigurationComposite
 */
public class ValidationConfigurationComposite extends Pane<JpaOptions2_0>
{
	/**
	 * Creates a new <code>ValidationGroupComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ValidationConfigurationComposite(
					Pane<? extends JpaOptions2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {

		// SharedCacheMode
		new SharedCacheModeComposite(this, this.buildPersistenceUnit2_0Holder(), parent);

		// ValidationMode
		new ValidationModeComposite(this, this.buildPersistenceUnit2_0Holder(), parent);

		// ValidationGroupPrePersist
		this.addGroupPrePersistListPane(parent);

		// ValidationGroupPreUpdate
		this.addGroupPreUpdateListPane(parent);

		// ValidationGroupPreRemove
		this.addGroupPreRemoveListPane(parent);
	}

	// ********** ValidationGroupPrePersists **********
	
	private void addGroupPrePersistListPane(Composite parent) {

		this.addLabeledComposite(parent, 
			JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPrePersistLabel,
			this.addPrePersistListPane(parent),
			null	// TODO Help
			);
	}
	
	private AddRemoveListPane<JpaOptions2_0> addPrePersistListPane(Composite parent) {

		// List pane
		AddRemoveListPane<JpaOptions2_0> listPane = new AddRemoveListPane<JpaOptions2_0>(
			this,
			parent,
			this.buildPrePersistAdapter(),
			this.buildPrePersistListHolder(),
			this.buildSelectedItemHolder(),
			this.buildLabelProvider()
		)
		{
			@Override
			protected void initializeTable(Table table) {
				super.initializeTable(table);

				Composite container = table.getParent();
				GridData gridData  = (GridData) container.getLayoutData();
				gridData.heightHint = 75;
			}
		};
		return listPane;
	}

	private Adapter buildPrePersistAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addPrePersistClass(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeValidationGroupPrePersist((String) item);
				}
			}
		};
	}

	private ListValueModel<String> buildPrePersistListHolder() {
		return new ListAspectAdapter<JpaOptions2_0, String>(getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return subject.getValidationGroupPrePersists();
			}

			@Override
			protected int size_() {
				return subject.getValidationGroupPrePersistsSize();
			}
		};
	}

	private void addPrePersistClass(ObjectListSelectionModel listSelectionModel) {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPrePersistExists(className)) {
				
				String classRef = this.getSubject().addValidationGroupPrePersist(className);
				listSelectionModel.setSelectedValue(classRef);
			}
		}
	}

	// ********** ValidationGroupPreUpdates **********
	
	private void addGroupPreUpdateListPane(Composite parent) {

		this.addLabeledComposite(parent, 
			JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPreUpdateLabel,
			this.addPreUpdateListPane(parent),
			null	// TODO Help
			);
	}
	
	private AddRemoveListPane<JpaOptions2_0> addPreUpdateListPane(Composite parent) {

		// List pane
		AddRemoveListPane<JpaOptions2_0> listPane = new AddRemoveListPane<JpaOptions2_0>(
			this,
			parent,
			this.buildPreUpdateAdapter(),
			this.buildPreUpdateListHolder(),
			this.buildSelectedItemHolder(),
			this.buildLabelProvider()
		)
		{
			@Override
			protected void initializeTable(Table table) {
				super.initializeTable(table);

				Composite container = table.getParent();
				GridData gridData  = (GridData) container.getLayoutData();
				gridData.heightHint = 75;
			}
		};
		return listPane;
	}

	private Adapter buildPreUpdateAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addPreUpdateClass(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeValidationGroupPreUpdate((String) item);
				}
			}
		};
	}

	private ListValueModel<String> buildPreUpdateListHolder() {
		return new ListAspectAdapter<JpaOptions2_0, String>(getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return subject.getValidationGroupPreUpdates();
			}

			@Override
			protected int size_() {
				return subject.getValidationGroupPreUpdatesSize();
			}
		};
	}

	private void addPreUpdateClass(ObjectListSelectionModel listSelectionModel) {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPreUpdateExists(className)) {
				
				String classRef = this.getSubject().addValidationGroupPreUpdate(className);
				listSelectionModel.setSelectedValue(classRef);
			}
		}
	}

	// ********** ValidationGroupPreRemoves **********
	
	private void addGroupPreRemoveListPane(Composite parent) {

		this.addLabeledComposite(parent, 
			JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPreRemoveLabel,
			this.addPreRemoveListPane(parent),
			null	// TODO Help
			);
	}
	
	private AddRemoveListPane<JpaOptions2_0> addPreRemoveListPane(Composite parent) {

		// List pane
		AddRemoveListPane<JpaOptions2_0> listPane = new AddRemoveListPane<JpaOptions2_0>(
			this,
			parent,
			this.buildPreRemoveAdapter(),
			this.buildPreRemoveListHolder(),
			this.buildSelectedItemHolder(),
			this.buildLabelProvider()
		)
		{
			@Override
			protected void initializeTable(Table table) {
				super.initializeTable(table);

				Composite container = table.getParent();
				GridData gridData  = (GridData) container.getLayoutData();
				gridData.heightHint = 75;
			}
		};
		return listPane;
	}

	private Adapter buildPreRemoveAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addPreRemoveClass(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeValidationGroupPreRemove((String) item);
				}
			}
		};
	}

	private ListValueModel<String> buildPreRemoveListHolder() {
		return new ListAspectAdapter<JpaOptions2_0, String>(getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return subject.getValidationGroupPreRemoves();
			}

			@Override
			protected int size_() {
				return subject.getValidationGroupPreRemovesSize();
			}
		};
	}

	private void addPreRemoveClass(ObjectListSelectionModel listSelectionModel) {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPreRemoveExists(className)) {
				
				String classRef = this.getSubject().addValidationGroupPreRemove(className);
				listSelectionModel.setSelectedValue(classRef);
			}
		}
	}


	// ********** Private methods **********
	
	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Holder() {
		return new PropertyAspectAdapter<JpaOptions2_0, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 buildValue_() {
				return (PersistenceUnit2_0) this.subject.getPersistenceUnit();
			}
		};
	}

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {

			@Override
			public String getText(Object element) {
				String name = (String) element;

				if (name == null) {
					name = JptUiPersistence2_0Messages.GenericPersistenceUnit2_0OptionsTab_noName;
				}
				return name;
			}
		};
	}

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * canceled the dialog
	 */
	private IType chooseType() {
		IJavaProject javaProject = this.getSubject().getJpaProject().getJavaProject();
		IJavaElement[] elements = new IJavaElement[] { javaProject };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				getShell(),
				service,
				scope,
				IJavaElementSearchConstants.CONSIDER_CLASSES,
				false,
				""
			);
		}
		catch (JavaModelException e) {
			JptJpaUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptCommonUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptCommonUiMessages.ClassChooserPane_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	private WritablePropertyValueModel<String> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<String>();
	}
}
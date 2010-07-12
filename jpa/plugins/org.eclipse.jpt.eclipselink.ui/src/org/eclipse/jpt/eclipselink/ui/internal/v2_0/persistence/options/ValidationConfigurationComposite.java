/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options;

import java.util.ListIterator;

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
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  ValidationConfigurationComposite
 */
public class ValidationConfigurationComposite extends Pane<Options2_0>
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
					Pane<Options2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {

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
	
	private AddRemoveListPane<Options2_0> addPrePersistListPane(Composite parent) {

		// List pane
		AddRemoveListPane<Options2_0> listPane = new AddRemoveListPane<Options2_0>(
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
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_PERSIST_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.validationGroupPrePersists();
			}

			@Override
			protected int size_() {
				return subject.validationGroupPrePersistsSize();
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
	
	private AddRemoveListPane<Options2_0> addPreUpdateListPane(Composite parent) {

		// List pane
		AddRemoveListPane<Options2_0> listPane = new AddRemoveListPane<Options2_0>(
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
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_UPDATE_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.validationGroupPreUpdates();
			}

			@Override
			protected int size_() {
				return subject.validationGroupPreUpdatesSize();
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
	
	private AddRemoveListPane<Options2_0> addPreRemoveListPane(Composite parent) {

		// List pane
		AddRemoveListPane<Options2_0> listPane = new AddRemoveListPane<Options2_0>(
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
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_REMOVE_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.validationGroupPreRemoves();
			}

			@Override
			protected int size_() {
				return subject.validationGroupPreRemovesSize();
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

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {

			@Override
			public String getText(Object element) {
				String name = (String) element;

				if (name == null) {
					name = EclipseLinkUiMessages.PersistenceXmlOptionsTab_noName;
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
			JptEclipseLinkUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptUiMessages.ClassChooserPane_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	private WritablePropertyValueModel<String> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<String>();
	}
	
	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Holder() {
		return new TransformationPropertyValueModel<Options2_0, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 transform_(Options2_0 value) {
				return (PersistenceUnit2_0) value.getPersistenceUnit();
			}
		};
	}
}
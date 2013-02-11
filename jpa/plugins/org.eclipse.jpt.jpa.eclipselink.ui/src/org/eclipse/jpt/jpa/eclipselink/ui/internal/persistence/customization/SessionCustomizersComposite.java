/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

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
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  SessionCustomizersComposite
 */
public class SessionCustomizersComposite extends Pane<Customization>
{
	/**
	 * Creates a new <code>SessionCustomizerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public SessionCustomizersComposite(Pane<? extends Customization> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}


	private String addSessionCustomizerClass() {

		IType type = chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().sessionCustomizerExists(className)) {
				
				return this.getSubject().addSessionCustomizer(className);
			}
		}
		return null;
	}

	private Adapter<String> buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter<String>() {
			public String addNewItem() {
				return addSessionCustomizerClass();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<String> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<String> selectedItemsModel) {
				String item = selectedItemsModel.iterator().next();
				getSubject().removeSessionCustomizer(item);
			}
		};
	}

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {

			@Override
			public String getText(Object element) {
				String name = (String) element;

				if (name == null) {
					name = JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_NO_NAME;
				}
				return name;
			}
		};
	}

	private ListValueModel<String> buildListHolder() {
		return new ListAspectAdapter<Customization, String>(getSubjectHolder(), Customization.SESSION_CUSTOMIZER_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return subject.getSessionCustomizers();
			}

			@Override
			protected int size_() {
				return subject.getSessionCustomizersSize();
			}
		};
	}

	private ModifiableCollectionValueModel<String> buildSelectedItemsModel() {
		return new SimpleCollectionValueModel<String>();
	}

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * canceled the dialog
	 */
	private IType chooseType() {
		IJavaProject javaProject = getJavaProject();
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
			JptJpaEclipseLinkUiPlugin.instance().logError(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		typeSelectionDialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_SESSION_CUSTOMIZER_LABEL
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// List pane
		new AddRemoveListPane<Customization, String>(
			this,
			container,
			buildAdapter(),
			buildListHolder(),
			buildSelectedItemsModel(),
			buildLabelProvider()
		);
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}
}

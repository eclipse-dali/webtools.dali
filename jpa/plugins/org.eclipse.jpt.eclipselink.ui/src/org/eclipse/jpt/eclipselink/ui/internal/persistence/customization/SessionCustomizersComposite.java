/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.customization;

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
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
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


	private void addSessionCustomizerClass(ObjectListSelectionModel listSelectionModel) {

		IType type = chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().sessionCustomizerExists(className)) {
				
				String classRef = this.getSubject().addSessionCustomizer(className);
				listSelectionModel.setSelectedValue(classRef);
			}
		}
	}

	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addSessionCustomizerClass(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeSessionCustomizer((String) item);
				}
			}
		};
	}

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {

			@Override
			public String getText(Object element) {
				String name = (String) element;

				if (name == null) {
					name = EclipseLinkUiMessages.PersistenceXmlCustomizationTab_noName;
				}
				return name;
			}
		};
	}

	private ListValueModel<String> buildListHolder() {
		return new ListAspectAdapter<Customization, String>(getSubjectHolder(), Customization.SESSION_CUSTOMIZER_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.sessionCustomizers();
			}

			@Override
			protected int size_() {
				return subject.sessionCustomizersSize();
			}
		};
	}

	private WritablePropertyValueModel<String> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<String>();
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
			JptEclipseLinkUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptCommonUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptCommonUiMessages.ClassChooserPane_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Description
		container = addTitledGroup(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_sessionCustomizerLabel
		);

		// List pane
		new AddRemoveListPane<Customization>(
			this,
			container,
			buildAdapter(),
			buildListHolder(),
			buildSelectedItemHolder(),
			buildLabelProvider()
		)
		{
			@Override
			protected void initializeTable(Table table) {
				super.initializeTable(table);

				Composite container = table.getParent();
				GridData gridData   = (GridData) container.getLayoutData();
				gridData.heightHint = 75;
			}
		};
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}
}

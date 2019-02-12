/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 *  SessionCustomizersComposite
 */
public class EclipseLinkSessionCustomizersComposite
	extends Pane<EclipseLinkCustomization>
{
	/**
	 * Creates a new <code>SessionCustomizerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public EclipseLinkSessionCustomizersComposite(Pane<? extends EclipseLinkCustomization> parentPane,
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
		return new ListAspectAdapter<EclipseLinkCustomization, String>(getSubjectHolder(), EclipseLinkCustomization.SESSION_CUSTOMIZER_LIST) {
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
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(
					getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createJavaSearchScope(new IJavaElement[] { getJavaProject() }),
					IJavaElementSearchConstants.CONSIDER_CLASSES,
					false,
					StringTools.EMPTY_STRING
				);
		} catch (JavaModelException ex) {
			JptJpaEclipseLinkUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		dialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
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
		new AddRemoveListPane<EclipseLinkCustomization, String>(
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

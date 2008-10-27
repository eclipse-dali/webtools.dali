/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.general;

import java.util.ListIterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.eclipselink.core.internal.context.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | Description                                                               |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | x Exclude Unlisted Mapped Classes                                         |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see GeneralProperties
 * @see EclipseLinkGeneralComposite - The parent container
 * @see AddRemoveListPane
 * @see PersistenceUnitMappedClassesComposite
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class ManagedClassesComposite extends Pane<GeneralProperties>
{
	/**
	 * Creates a new <code>ManagedClassesComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ManagedClassesComposite(Pane<? extends GeneralProperties> parentPane,
	                                             Composite parent) {

		super(parentPane, parent);
	}

	private void addMappedClass(ObjectListSelectionModel listSelectionModel) {

		IType type = chooseType();

		if (type != null) {
			ClassRef classRef = getSubject().addSpecifiedClassRef();
			classRef.setClassName(type.getFullyQualifiedName('.'));
			listSelectionModel.setSelectedValue(classRef);
		}
	}

	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addMappedClass(listSelectionModel);
			}

			@Override
			public boolean enableOptionOnSelectionChange(ObjectListSelectionModel listSelectionModel) {
				if (!super.enableOptionOnSelectionChange(listSelectionModel)) {
					return false;
				}

				return findType((ClassRef) listSelectionModel.selectedValue()) != null;
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return EclipseLinkUiMessages.PersistenceXmlGeneralTab_open;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				openMappedClass((ClassRef) listSelectionModel.selectedValue());
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeSpecifiedClassRef((ClassRef) item);
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildExcludeUnlistedMappedClassesHolder() {
		return new PropertyAspectAdapter<GeneralProperties, Boolean>(
			getSubjectHolder(),
			GeneralProperties.SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedExcludeUnlistedClasses();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedExcludeUnlistedClasses(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildExcludeUnlistedMappedClassesStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildExcludeUnlistedMappedClassesHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().getDefaultExcludeUnlistedClasses();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True :
																			EclipseLinkUiMessages.Boolean_False;

						return NLS.bind(
							EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeUnlistedMappedClassesWithDefault,
							defaultStringValue
						);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeUnlistedMappedClasses;
			}
		};
	}

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				ClassRef classRef = (ClassRef) element;
				JavaPersistentType persistentType = classRef.getJavaPersistentType();
				Image image = null;

				if (persistentType != null) {
					image = JpaMappingImageHelper.imageForTypeMapping(persistentType.getMappingKey());
				}

				if (image != null) {
					return image;
				}

				return JptUiPlugin.getImage(JptUiIcons.WARNING);
			}

			@Override
			public String getText(Object element) {
				ClassRef classRef = (ClassRef) element;
				String name = classRef.getClassName();

				if (name == null) {
					name = EclipseLinkUiMessages.PersistenceXmlGeneralTab_mappedClassesNoName;
				}

				return name;
			}
		};
	}

	private ListValueModel<ClassRef> buildItemListHolder() {
		return new ItemPropertyListValueModelAdapter<ClassRef>(
			buildListHolder(),
			ClassRef.JAVA_PERSISTENT_TYPE_PROPERTY,
			ClassRef.CLASS_NAME_PROPERTY
		);
	}

	private ListValueModel<ClassRef> buildListHolder() {
		return new ListAspectAdapter<GeneralProperties, ClassRef>(getSubjectHolder(), GeneralProperties.SPECIFIED_CLASS_REFS_LIST) {
			@Override
			protected ListIterator<ClassRef> listIterator_() {
				return subject.specifiedClassRefs();
			}

			@Override
			protected int size_() {
				return subject.specifiedClassRefsSize();
			}
		};
	}

	private WritablePropertyValueModel<ClassRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<ClassRef>();
	}

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * cancelled the dialog
	 */
	private IType chooseType() {

		IPackageFragmentRoot root = packageFragmentRoot();

		if (root == null) {
			return null;
		}

		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
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

	private IType findType(ClassRef classRef) {
		String className = classRef.getClassName();

		if (className != null) {
			try {
				return getSubject().getJpaProject().getJavaProject().findType(className);
			}
			catch (JavaModelException e) {
				JptEclipseLinkUiPlugin.log(e);
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		new AddRemoveListPane<GeneralProperties>(
			this,
			container,
			this.buildAdapter(),
			this.buildItemListHolder(),
			this.buildSelectedItemHolder(),
			this.buildLabelProvider()
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

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeUnlistedMappedClasses,
			this.buildExcludeUnlistedMappedClassesHolder(),
			this.buildExcludeUnlistedMappedClassesStringHolder(),
			null
		);
	}

	private void openMappedClass(ClassRef classRef) {

		IType type = findType(classRef);

		if (type != null) {
			try {
				IJavaElement javaElement = type.getParent();
				JavaUI.openInEditor(javaElement, true, true);
			}
			catch (PartInitException e) {
				JptEclipseLinkUiPlugin.log(e);
			}
			catch (JavaModelException e) {
				JptEclipseLinkUiPlugin.log(e);
			}
		}
	}

	private IPackageFragmentRoot packageFragmentRoot() {
		IProject project = getSubject().getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);

		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptEclipseLinkUiPlugin.log(e);
		}

		return null;
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.jface.ResourceManagerLabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

@SuppressWarnings("nls")
public class PersistenceUnitClassesComposite
	extends Pane<PersistenceUnit>
{
	public PersistenceUnitClassesComposite(
			Pane<? extends PersistenceUnit> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit, ClassRef>(
			this,
			container,
			this.buildAddRemovePaneAdapter(),
			this.buildItemListModel(),
			this.buildSelectedItemsModel(),
			this.buildClassRefLabelProvider(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		);

		this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_EXCLUDE_UNLISTED_CLASSES, 
			buildExcludeUnlistedClassesModel(),
			buildExcludeUnlistedClassesStringModel(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		);
	}


	protected ClassRef addClassRef() {

		IType type = chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if(classRefExists(className)) {
				return null;
			}
			return getSubject().addSpecifiedClassRef(className);
		}
		return null;
	}
	
	private boolean classRefExists(String className) {
		for (ClassRef classRef : getSubject().getSpecifiedClassRefs()) {
			if( classRef.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	private AddRemovePane.Adapter<ClassRef> buildAddRemovePaneAdapter() {
		return new AddRemovePane.AbstractAdapter<ClassRef>() {
			public ClassRef addNewItem() {
				return addClassRef();
			}

			@Override
			public PropertyValueModel<Boolean> buildOptionalButtonEnabledModel(CollectionValueModel<ClassRef> selectedItemsModel) {
				return new CollectionPropertyValueModelAdapter<Boolean, ClassRef>(selectedItemsModel) {
					@Override
					protected Boolean buildValue() {
						if (this.collectionModel.size() == 1) {
							ClassRef classRef = this.collectionModel.iterator().next();
							return Boolean.valueOf(findType(classRef) != null);				
						}
						return Boolean.FALSE;
					}
				};
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_OPEN;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<ClassRef> selectedItemsModel) {
				openMappedClass(selectedItemsModel.iterator().next());
			}

			public void removeSelectedItems(CollectionValueModel<ClassRef> selectedItemsModel) {
				getSubject().removeSpecifiedClassRefs(selectedItemsModel);
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildExcludeUnlistedClassesModel() {
		return new PropertyAspectAdapter<PersistenceUnit, Boolean>(
			getSubjectHolder(),
			PersistenceUnit.SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedExcludeUnlistedClasses();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedExcludeUnlistedClasses(value);
			}
		};
	}

	private PropertyValueModel<String> buildExcludeUnlistedClassesStringModel() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultExcludeUnlistedClassesModel()) {
			@Override
			protected String transform(Boolean v) {
				if (v != null) {
					String defaultStringValue = v.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_EXCLUDE_UNLISTED_CLASSES_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_EXCLUDE_UNLISTED_CLASSES;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultExcludeUnlistedClassesModel() {
		return new PropertyAspectAdapter<PersistenceUnit, Boolean>(
			getSubjectHolder(),
			PersistenceUnit.SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY,
			PersistenceUnit.DEFAULT_EXCLUDE_UNLISTED_CLASSES_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedExcludeUnlistedClasses() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.getDefaultExcludeUnlistedClasses());
			}
		};
	}

	private ILabelProvider buildClassRefLabelProvider() {
		return new ResourceManagerLabelProvider<ClassRef>(
				CLASS_REF_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER,
				CLASS_REF_LABEL_TEXT_TRANSFORMER,
				this.getResourceManager()
			);
	}

	//TODO this image does not update if the java persistent type mapping is changed.
	//also need to make the image and label the same in the structure view
	private static final Transformer<ClassRef, ImageDescriptor> CLASS_REF_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER = new ClassRefLabelImageDescriptorTransformer();
	/* CU private */ static class ClassRefLabelImageDescriptorTransformer
		extends AbstractTransformer<ClassRef, ImageDescriptor>
	{
		@Override
		protected ImageDescriptor transform_(ClassRef classRef) {
			return this.getImageDescriptor(classRef);
		}

		private ImageDescriptor getImageDescriptor(ClassRef classRef) {
			if (classRef.getJavaManagedType() == null) {
				return JptCommonUiImages.WARNING;
			}
			JavaManagedTypeUiDefinition def = this.getManagedTypeUiDefinition(classRef);
			return (def != null) ?
					def.getImageDescriptor(classRef.getJavaManagedType()) :
						JptCommonUiImages.WARNING;
		}

		private JavaManagedTypeUiDefinition getManagedTypeUiDefinition(ClassRef classRef) {
			PersistenceResourceUiDefinition def = this.getPersistenceResourceUiDefinition(classRef);
			return def == null ? null : def.getJavaManagedTypeUiDefinition(classRef.getJavaManagedType().getType());
		}

		private PersistenceResourceUiDefinition getPersistenceResourceUiDefinition(ClassRef classRef) {
			JpaPlatformUi ui = this.getJpaPlatformUi(classRef);
			return ui == null ? null : (PersistenceResourceUiDefinition) this.getJpaPlatformUi(classRef).getResourceUiDefinition(classRef.getResourceType());
		}

		private JpaPlatformUi getJpaPlatformUi(ClassRef classRef) {
			return (JpaPlatformUi) classRef.getJpaPlatform().getAdapter(JpaPlatformUi.class);
		}
	}

	private static final Transformer<ClassRef, String> CLASS_REF_LABEL_TEXT_TRANSFORMER = new ClassRefLabelTextTransformer();
	/* CU private */ static class ClassRefLabelTextTransformer
		extends AbstractTransformer<ClassRef, String>
	{
		@Override
		protected String transform_(ClassRef classRef) {
			String name = classRef.getClassName();
			return (name != null) ? name : JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_CLASS_REF_NO_NAME;
		}
	}

	private ListValueModel<ClassRef> buildItemListModel() {
		return new ItemPropertyListValueModelAdapter<ClassRef>(
			buildListModel(),
			ClassRef.JAVA_MANAGED_TYPE_PROPERTY,
			ClassRef.CLASS_NAME_PROPERTY
		);
	}

	private ListValueModel<ClassRef> buildListModel() {
		return new ListAspectAdapter<PersistenceUnit, ClassRef>(getSubjectHolder(), PersistenceUnit.SPECIFIED_CLASS_REFS_LIST) {
			@Override
			protected ListIterable<ClassRef> getListIterable() {
				return this.subject.getSpecifiedClassRefs();
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedClassRefsSize();
			}
		};
	}

	private ModifiableCollectionValueModel<ClassRef> buildSelectedItemsModel() {
		return new SimpleCollectionValueModel<ClassRef>();
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
			JptJpaUiPlugin.instance().logError(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		typeSelectionDialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	protected IType findType(ClassRef classRef) {
		String className = classRef.getClassName();

		if (className != null) {
			try {
				return getSubject().getJpaProject().getJavaProject().findType(className.replace('$', '.'));
			}
			catch (JavaModelException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}

		return null;
	}

	protected void openMappedClass(ClassRef classRef) {

		IType type = findType(classRef);

		if (type != null) {
			try {
				IJavaElement javaElement = type.getParent();
				JavaUI.openInEditor(javaElement, true, true);
			}
			catch (PartInitException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
			catch (JavaModelException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}
}

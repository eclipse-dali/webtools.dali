/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import java.util.Collection;
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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  GenericPersistenceUnit2_0OptionsTab
 */
public class GenericPersistenceUnit2_0OptionsTab extends Pane<JpaOptions2_0>
	implements JpaPageComposite
{
	// ********** constructors/initialization **********
	/**
	 * Creates a new <code>GenericPersistenceUnit2_0OptionsTab</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public GenericPersistenceUnit2_0OptionsTab(
											PropertyValueModel<JpaOptions2_0> subjectHolder,
	                                        Composite parent,
	                                        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Composite composite = this.addSection(parent, 
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionTitle,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionDescription);
		composite.setLayout(new GridLayout(2, false));

		this.addLabel(composite, JptUiPersistence2_0Messages.LockingConfigurationComposite_lockTimeoutLabel);
		this.addLockTimeoutCombo(composite);

		this.addLabel(composite, JptUiPersistence2_0Messages.QueryConfigurationComposite_queryTimeoutLabel);
		this.addQueryTimeoutCombo(composite);

		// SharedCacheMode
		this.addLabel(composite, JptUiPersistence2_0Messages.SharedCacheModeComposite_sharedCacheModeLabel);		
		this.addSharedCacheModeCombo(composite, this.buildPersistenceUnit2_0Holder());

		// ValidationMode
		this.addLabel(composite, JptUiPersistence2_0Messages.ValidationModeComposite_validationModeLabel);		
		this.addValidationModeCombo(composite, this.buildPersistenceUnit2_0Holder());

		// ValidationGroupPrePersist
		this.addLabel(composite, JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPrePersistLabel);		
		this.addPrePersistListPane(composite);

		// ValidationGroupPreUpdate
		this.addLabel(composite, JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPreUpdateLabel);		
		this.addPreUpdateListPane(composite);

		// ValidationGroupPreRemove
		this.addLabel(composite, JptUiPersistence2_0Messages.ValidationConfigurationComposite_groupPreRemoveLabel);		
		this.addPreRemoveListPane(composite);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return null;						// TODO - Review for JPA 2.0
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return JptUiPersistence2_0Messages.GenericPersistenceUnit2_0OptionsTab_title;
	}


	//************ lock timeout **********

	private void addLockTimeoutCombo(Composite parent) {
		new IntegerCombo<JpaOptions2_0>(this, parent) {
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultLockTimeout();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(getSubjectHolder(), JpaOptions2_0.LOCK_TIMEOUT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getLockTimeout();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setLockTimeout(value);
					}
				};
			}
		};
	}


	//************ query timeout **********

	private void addQueryTimeoutCombo(Composite parent) {
		new IntegerCombo<JpaOptions2_0>(this, parent) {		
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultQueryTimeout();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(getSubjectHolder(), JpaOptions2_0.QUERY_TIMEOUT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getQueryTimeout();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setQueryTimeout(value);
					}
				};
			}
		};
	}

	//************ validation configuration **********
	// ********** ValidationGroupPrePersists **********

	private void addPrePersistListPane(Composite parent) {
		new AddRemoveListPane<JpaOptions2_0, String>(
			this,
			parent,
			this.buildPrePersistAdapter(),
			this.buildPrePersistListHolder(),
			this.buildSelectedItemsModel(),
			this.buildLabelProvider()
		);
	}

	private Adapter<String> buildPrePersistAdapter() {
		return new AddRemoveListPane.AbstractAdapter<String>() {
			public String addNewItem() {
				return addPrePersistClass();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<String> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<String> selectedItemsModel) {
				String item = selectedItemsModel.iterator().next();
				getSubject().removeValidationGroupPrePersist(item);
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

	private String addPrePersistClass() {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPrePersistExists(className)) {
				
				return this.getSubject().addValidationGroupPrePersist(className);
			}
		}
		return null;
	}

	// ********** ValidationGroupPreUpdates **********

	private void addPreUpdateListPane(Composite parent) {
		new AddRemoveListPane<JpaOptions2_0, String>(
			this,
			parent,
			this.buildPreUpdateAdapter(),
			this.buildPreUpdateListHolder(),
			this.buildSelectedItemsModel(),
			this.buildLabelProvider()
		);
	}

	private Adapter<String> buildPreUpdateAdapter() {
		return new AddRemoveListPane.AbstractAdapter<String>() {
			public String addNewItem() {
				return addPreUpdateClass();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<String> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<String> selectedItemsModel) {
				String item = selectedItemsModel.iterator().next();
				getSubject().removeValidationGroupPreUpdate(item);
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

	private String addPreUpdateClass() {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPreUpdateExists(className)) {
				
				return this.getSubject().addValidationGroupPreUpdate(className);
			}
		}
		return null;
	}

	// ********** ValidationGroupPreRemoves **********

	private void addPreRemoveListPane(Composite parent) {
		new AddRemoveListPane<JpaOptions2_0, String>(
			this,
			parent,
			this.buildPreRemoveAdapter(),
			this.buildPreRemoveListHolder(),
			this.buildSelectedItemsModel(),
			this.buildLabelProvider()
		);
	}

	private Adapter<String> buildPreRemoveAdapter() {
		return new AddRemoveListPane.AbstractAdapter<String>() {
			public String addNewItem() {
				return addPreRemoveClass();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<String> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<String> selectedItemsModel) {
				String item = selectedItemsModel.iterator().next();
				getSubject().removeValidationGroupPreRemove(item);
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

	private String addPreRemoveClass() {
		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.getSubject().validationGroupPreRemoveExists(className)) {
				
				return this.getSubject().addValidationGroupPreRemove(className);
			}
		}
		return null;
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

	private ModifiableCollectionValueModel<String> buildSelectedItemsModel() {
		return new SimpleCollectionValueModel<String>();
	}

	//********* shared cache mode ***********

	private EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode> addSharedCacheModeCombo(Composite parent, PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder) {
		return new EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode>(this, subjectHolder, parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY);
			}

			@Override
			protected SharedCacheMode[] getChoices() {
				return SharedCacheMode.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected SharedCacheMode getDefaultValue() {
				return this.getSubject().getDefaultSharedCacheMode();
			}

			@Override
			protected String displayString(SharedCacheMode value) {
				switch (value) {
					case ALL :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_all;
					case DISABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_disable_selective;
					case ENABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_enable_selective;
					case NONE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_none;
					case UNSPECIFIED :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_unspecified;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected SharedCacheMode getValue() {
				return this.getSubject().getSpecifiedSharedCacheMode();
			}

			@Override
			protected void setValue(SharedCacheMode value) {
				this.getSubject().setSpecifiedSharedCacheMode(value);
			}
		};
	}

	//********* validation mode ***********
	
	private EnumFormComboViewer<PersistenceUnit2_0, ValidationMode> addValidationModeCombo(Composite parent, PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder) {
		return new EnumFormComboViewer<PersistenceUnit2_0, ValidationMode>(this, subjectHolder, parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_VALIDATION_MODE_PROPERTY);
			}

			@Override
			protected ValidationMode[] getChoices() {
				return ValidationMode.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected ValidationMode getDefaultValue() {
				return this.getSubject().getDefaultValidationMode();
			}

			@Override
			protected String displayString(ValidationMode value) {
				switch (value) {
					case AUTO :
						return JptUiPersistence2_0Messages.ValidationModeComposite_auto;
					case CALLBACK :
						return JptUiPersistence2_0Messages.ValidationModeComposite_callback;
					case NONE :
						return JptUiPersistence2_0Messages.ValidationModeComposite_none;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected ValidationMode getValue() {
				return this.getSubject().getSpecifiedValidationMode();
			}

			@Override
			protected void setValue(ValidationMode value) {
				this.getSubject().setSpecifiedValidationMode(value);
			}
		};
	}
}

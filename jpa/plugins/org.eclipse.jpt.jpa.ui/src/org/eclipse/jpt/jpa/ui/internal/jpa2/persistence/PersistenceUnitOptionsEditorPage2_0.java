/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.Options2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode2_0;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitOptionsEditorPage2_0
	extends Pane<Options2_0>
{
	public PersistenceUnitOptionsEditorPage2_0(
			PropertyValueModel<Options2_0> subjectModel,
            Composite parent,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectModel, parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = this.getWidgetFactory().createSection(parent, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_OPTIONS_COMPOSITE_MISCELLANEOUS_SECTION_TITLE);
		section.setDescription(JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_OPTIONS_COMPOSITE_MISCELLANEOUS_SECTION_DESCRIPTION);

		Composite client = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		client.setLayout(layout);
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		this.addLabel(client, JptJpaUiPersistenceMessages2_0.LOCKING_CONFIGURATION_COMPOSITE_LOCK_TIMEOUT_LABEL);
		this.addLockTimeoutCombo(client);

		this.addLabel(client, JptJpaUiPersistenceMessages2_0.QUERY_CONFIGURATION_COMPOSITE_QUERY_TIMEOUT_LABEL);
		this.addQueryTimeoutCombo(client);

		// SharedCacheMode
		this.addLabel(client, JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_SHARED_CACHE_MODE_LABEL);		
		this.addSharedCacheModeCombo(client, this.buildPersistenceUnit2_0Model());

		// ValidationMode
		this.addLabel(client, JptJpaUiPersistenceMessages2_0.VALIDATION_MODE_COMPOSITE_VALIDATION_MODE_LABEL);		
		this.addValidationModeCombo(client, this.buildPersistenceUnit2_0Model());

		// ValidationGroupPrePersist
		this.addLabel(client, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_PERSIST_LABEL);		
		this.addPrePersistListPane(client);

		// ValidationGroupPreUpdate
		this.addLabel(client, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_UPDATE_LABEL);		
		this.addPreUpdateListPane(client);

		// ValidationGroupPreRemove
		this.addLabel(client, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_REMOVE_LABEL);		
		this.addPreRemoveListPane(client);
	}


	//************ lock timeout **********

	private void addLockTimeoutCombo(Composite parent) {
		new IntegerCombo<Options2_0>(this, parent) {
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultLockTimeout();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(getSubjectHolder(), Options2_0.LOCK_TIMEOUT_PROPERTY) {
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
		new IntegerCombo<Options2_0>(this, parent) {		
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultQueryTimeout();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(getSubjectHolder(), Options2_0.QUERY_TIMEOUT_PROPERTY) {
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
		new AddRemoveListPane<Options2_0, String>(
			this,
			parent,
			this.buildPrePersistAdapter(),
			this.buildPrePersistListModel(),
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

	private ListValueModel<String> buildPrePersistListModel() {
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_PERSIST_LIST) {
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
		new AddRemoveListPane<Options2_0, String>(
			this,
			parent,
			this.buildPreUpdateAdapter(),
			this.buildPreUpdateListModel(),
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

	private ListValueModel<String> buildPreUpdateListModel() {
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_UPDATE_LIST) {
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
		new AddRemoveListPane<Options2_0, String>(
			this,
			parent,
			this.buildPreRemoveAdapter(),
			this.buildPreRemoveListModel(),
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

	private ListValueModel<String> buildPreRemoveListModel() {
		return new ListAspectAdapter<Options2_0, String>(getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_REMOVE_LIST) {
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

	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Model() {
		return new PropertyAspectAdapter<Options2_0, PersistenceUnit2_0>(this.getSubjectHolder()) {
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
					name = JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_OPTIONS_TAB_NO_NAME;
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
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(
					getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createJavaSearchScope(new IJavaElement[] { this.getSubject().getJpaProject().getJavaProject() }),
					IJavaElementSearchConstants.CONSIDER_CLASSES,
					false,
					StringTools.EMPTY_STRING
				);
		} catch (JavaModelException ex) {
			JptJpaUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		dialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
	}

	private ModifiableCollectionValueModel<String> buildSelectedItemsModel() {
		return new SimpleCollectionValueModel<String>();
	}

	//********* shared cache mode ***********

	private EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode2_0> addSharedCacheModeCombo(Composite parent, PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder) {
		return new EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode2_0>(this, subjectHolder, parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY);
			}

			@Override
			protected SharedCacheMode2_0[] getChoices() {
				return SharedCacheMode2_0.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected SharedCacheMode2_0 getDefaultValue() {
				return this.getSubject().getDefaultSharedCacheMode();
			}

			@Override
			protected String displayString(SharedCacheMode2_0 value) {
				switch (value) {
					case ALL :
						return JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_ALL;
					case DISABLE_SELECTIVE :
						return JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_DISABLE_SELECTIVE;
					case ENABLE_SELECTIVE :
						return JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_ENABLE_SELECTIVE;
					case NONE :
						return JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_NONE;
					case UNSPECIFIED :
						return JptJpaUiPersistenceMessages2_0.SHARED_CACHE_MODE_COMPOSITE_UNSPECIFIED;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected SharedCacheMode2_0 getValue() {
				return this.getSubject().getSpecifiedSharedCacheMode();
			}

			@Override
			protected void setValue(SharedCacheMode2_0 value) {
				this.getSubject().setSpecifiedSharedCacheMode(value);
			}
		};
	}

	//********* validation mode ***********
	
	private EnumFormComboViewer<PersistenceUnit2_0, ValidationMode2_0> addValidationModeCombo(Composite parent, PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder) {
		return new EnumFormComboViewer<PersistenceUnit2_0, ValidationMode2_0>(this, subjectHolder, parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_VALIDATION_MODE_PROPERTY);
			}

			@Override
			protected ValidationMode2_0[] getChoices() {
				return ValidationMode2_0.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected ValidationMode2_0 getDefaultValue() {
				return this.getSubject().getDefaultValidationMode();
			}

			@Override
			protected String displayString(ValidationMode2_0 value) {
				switch (value) {
					case AUTO :
						return JptJpaUiPersistenceMessages2_0.VALIDATION_MODE_COMPOSITE_AUTO;
					case CALLBACK :
						return JptJpaUiPersistenceMessages2_0.VALIDATION_MODE_COMPOSITE_CALLBACK;
					case NONE :
						return JptJpaUiPersistenceMessages2_0.VALIDATION_MODE_COMPOSITE_NONE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected ValidationMode2_0 getValue() {
				return this.getSubject().getSpecifiedValidationMode();
			}

			@Override
			protected void setValue(ValidationMode2_0 value) {
				this.getSubject().setSpecifiedValidationMode(value);
			}
		};
	}
}

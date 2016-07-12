/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

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
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.Options2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOptions2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.EclipseLinkPersistenceUnitOptionsEditorPage;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitOptionsEditorPage2_0;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Section;

class EclipseLinkPersistenceUnitOptionsEditorPage2_0
	extends EclipseLinkPersistenceUnitOptionsEditorPage
{
	PropertyValueModel<EclipseLinkOptions2_0> optionsModel;

	public EclipseLinkPersistenceUnitOptionsEditorPage2_0(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
            Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeMiscellaneousSection(Section section) {	
		Composite container = this.addSubPane(section, 2, 0, 0, 0, 0);

		TriStateCheckBox checkBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TEMPORAL_MUTABLE_LABEL,
			this.buildTemporalMutableModel(),
			this.buildTemporalMutableStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		checkBox.getCheckBox().setLayoutData(gridData);

		this.optionsModel = this.buildOptions2_0Model();
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.LOCKING_CONFIGURATION_COMPOSITE_LOCK_TIMEOUT_LABEL);
		this.addLockTimeoutCombo(container);

		this.addLabel(container, JptJpaUiPersistenceMessages2_0.QUERY_CONFIGURATION_COMPOSITE_QUERY_TIMEOUT_LABEL);
		this.addQueryTimeoutCombo(container);


		// ValidationMode
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.VALIDATION_MODE_COMPOSITE_VALIDATION_MODE_LABEL);
		this.addValidationModeCombo(container);

		// ValidationGroupPrePersist
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_PERSIST_LABEL);
		this.addPrePersistListPane(container);

		// ValidationGroupPreUpdate
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_UPDATE_LABEL);
		this.addPreUpdateListPane(container);

		// ValidationGroupPreRemove
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_REMOVE_LABEL);
		this.addPreRemoveListPane(container);

		return container;
	}

	@Override
	protected Control initializeLoggingSection(Section section) {			
		return new EclipseLinkLoggingComposite2_0(this, this.buildLogging2_0Model(), section).getControl();
	}

	protected PropertyValueModel<EclipseLinkLogging2_0> buildLogging2_0Model() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(EclipseLinkPersistenceUnit.LOGGING_TRANSFORMER));
	}

	@SuppressWarnings("unused")
	private void addLockTimeoutCombo(Composite container) {
		new PersistenceUnitOptionsEditorPage2_0.LockTimeoutCombo(this, this.optionsModel, container);
	}

	@SuppressWarnings("unused")
	private void addQueryTimeoutCombo(Composite container) {
		new PersistenceUnitOptionsEditorPage2_0.QueryTimeoutCombo(this, this.optionsModel, container);
	}

	private EnumFormComboViewer<PersistenceUnit, ValidationMode2_0> addValidationModeCombo(Composite parent) {
		return new EnumFormComboViewer<PersistenceUnit, ValidationMode2_0>(this, this.getSubjectHolder(), parent) {
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
				return ((PersistenceUnit2_0) this.getSubject()).getDefaultValidationMode();
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
				return ((PersistenceUnit2_0) this.getSubject()).getSpecifiedValidationMode();
			}

			@Override
			protected void setValue(ValidationMode2_0 value) {
				((PersistenceUnit2_0) this.getSubject()).setSpecifiedValidationMode(value);
			}
		};
	}

	// ********** ValidationGroupPrePersists **********

	@SuppressWarnings("unused")
	private void addPrePersistListPane(Composite parent) {
		new AddRemoveListPane<EclipseLinkOptions2_0, String>(
			this,
			this.optionsModel,
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
				EclipseLinkPersistenceUnitOptionsEditorPage2_0.this.optionsModel.getValue().removeValidationGroupPrePersist(item);
			}
		};
	}

	private ListValueModel<String> buildPrePersistListModel() {
		return new ListAspectAdapter<EclipseLinkOptions2_0, String>(this.optionsModel, Options2_0.VALIDATION_GROUP_PRE_PERSIST_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return this.subject.getValidationGroupPrePersists();
			}

			@Override
			protected int size_() {
				return this.subject.getValidationGroupPrePersistsSize();
			}
		};
	}

	String addPrePersistClass() {
		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.optionsModel.getValue().validationGroupPrePersistExists(className)) {
				return this.optionsModel.getValue().addValidationGroupPrePersist(className);
			}
		}
		return null;
	}

	// ********** ValidationGroupPreUpdates **********

	@SuppressWarnings("unused")
	private void addPreUpdateListPane(Composite parent) {
		new AddRemoveListPane<EclipseLinkOptions2_0, String>(
			this,
			this.optionsModel,
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
				EclipseLinkPersistenceUnitOptionsEditorPage2_0.this.optionsModel.getValue().removeValidationGroupPreUpdate(item);
			}
		};
	}

	private ListValueModel<String> buildPreUpdateListModel() {
		return new ListAspectAdapter<EclipseLinkOptions2_0, String>(this.optionsModel, Options2_0.VALIDATION_GROUP_PRE_UPDATE_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return this.subject.getValidationGroupPreUpdates();
			}

			@Override
			protected int size_() {
				return this.subject.getValidationGroupPreUpdatesSize();
			}
		};
	}

	String addPreUpdateClass() {
		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.optionsModel.getValue().validationGroupPreUpdateExists(className)) {
				return this.optionsModel.getValue().addValidationGroupPreUpdate(className);
			}
		}
		return null;
	}

	// ********** ValidationGroupPreRemoves **********

	@SuppressWarnings("unused")
	private void addPreRemoveListPane(Composite parent) {
		new AddRemoveListPane<EclipseLinkOptions2_0, String>(
			this,
			this.optionsModel,
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
				EclipseLinkPersistenceUnitOptionsEditorPage2_0.this.optionsModel.getValue().removeValidationGroupPreRemove(item);
			}
		};
	}

	private ListValueModel<String> buildPreRemoveListModel() {
		return new ListAspectAdapter<EclipseLinkOptions2_0, String>(this.optionsModel, Options2_0.VALIDATION_GROUP_PRE_REMOVE_LIST) {
			@Override
			protected ListIterable<String> getListIterable() {
				return this.subject.getValidationGroupPreRemoves();
			}

			@Override
			protected int size_() {
				return this.subject.getValidationGroupPreRemovesSize();
			}
		};
	}

	String addPreRemoveClass() {

		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('$');
			if( ! this.optionsModel.getValue().validationGroupPreRemoveExists(className)) {
				return this.optionsModel.getValue().addValidationGroupPreRemove(className);
			}
		}
		return null;
	}


	// ********** Private methods **********

	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {

			@Override
			public String getText(Object element) {
				String name = (String) element;

				if (name == null) {
					name = JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_NO_NAME;
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
			JptJpaEclipseLinkUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		dialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		return (dialog.open() == Window.OK)  ? (IType) dialog.getResult()[0] : null;
	}

	private ModifiableCollectionValueModel<String> buildSelectedItemsModel() {
		return new SimpleCollectionValueModel<>();
	}

	private PropertyValueModel<EclipseLinkOptions2_0> buildOptions2_0Model() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(PersistenceUnit2_0.OPTIONS_TRANSFORMER));
	}
}

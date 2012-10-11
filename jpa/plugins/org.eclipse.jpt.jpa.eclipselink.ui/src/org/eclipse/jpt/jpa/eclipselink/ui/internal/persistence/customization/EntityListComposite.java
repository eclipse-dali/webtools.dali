/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CustomizationEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.progress.IProgressService;

/**
 *  EntityListComposite
 */
public class EntityListComposite extends Pane<Customization>
{
	private ModifiableCollectionValueModel<CustomizationEntity> selectedEntitiesModel;
	private PropertyValueModel<CustomizationEntity> selectedEntityModel;

	public EntityListComposite(Pane<? extends Customization> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedEntitiesModel = this.buildSelectedEntitiesModel();
		this.selectedEntityModel = this.buildSelectedEntityModel(this.selectedEntitiesModel);
	}

	private ModifiableCollectionValueModel<CustomizationEntity> buildSelectedEntitiesModel() {
		return new SimpleCollectionValueModel<CustomizationEntity>();
	}

	private PropertyValueModel<CustomizationEntity> buildSelectedEntityModel(CollectionValueModel<CustomizationEntity> selectedEntitiesModel) {
		return new CollectionPropertyValueModelAdapter<CustomizationEntity, CustomizationEntity>(selectedEntitiesModel) {
			@Override
			protected CustomizationEntity buildValue() {
				if (this.collectionModel.size() == 1) {
					return this.collectionModel.iterator().next();
				}
				return null;
			}
		};
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			EclipseLinkUiMessages.CustomizationEntityListComposite_groupTitle
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Entities add/remove list pane
		new AddRemoveListPane<Customization, CustomizationEntity>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntitiesListHolder(),
			this.selectedEntitiesModel,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);

		// Entity Customization property pane
		Hyperlink customizationHyperlink = this.addHyperlink(container, EclipseLinkUiMessages.PersistenceXmlCustomizationTab_customizerLabel);
		this.initializeClassChooser(container, customizationHyperlink);
	}

	private AddRemoveListPane.Adapter<CustomizationEntity> buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<CustomizationEntity>() {

			public CustomizationEntity addNewItem() {
				return EntityListComposite.this.addEntity();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<CustomizationEntity> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<CustomizationEntity> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				CustomizationEntity customizationEntity = selectedItemsModel.iterator().next();
				getSubject().removeEntity(customizationEntity.getName());
			}
		};
	}
	
	private CustomizationEntity addEntity() {
		IType type = this.chooseEntity();

		if (type != null) {
			String entityName = this.getEntityName(type.getFullyQualifiedName());
			if (entityName == null) {
				entityName = type.getElementName();
			}
			
			if( ! this.getSubject().entityExists(entityName)) {
				return this.getSubject().addEntity(entityName);
			}
		}
		return null;
	}
	
	private String getEntityName(String fullyQualifiedTypeName) {

		org.eclipse.jpt.jpa.core.context.Entity entity = getSubject().getPersistenceUnit().getEntity(fullyQualifiedTypeName);
		return entity != null ? entity.getName() : null;
	}
	
	private IType chooseEntity() {
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
		typeSelectionDialog.setTitle(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogTitle);
		typeSelectionDialog.setMessage(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				CustomizationEntity entityCustomization = (CustomizationEntity) element;
				return entityCustomization.getName();
			}
		};
	}

	private ModifiablePropertyValueModel<CustomizationEntity> buildEntityHolder() {
		return new SimplePropertyValueModel<CustomizationEntity>();
	}

	private ListValueModel<CustomizationEntity> buildEntitiesListHolder() {
		return new ListAspectAdapter<Customization, CustomizationEntity>(
				this.getSubjectHolder(), Customization.ENTITIES_LIST) {
			@Override
			protected ListIterable<CustomizationEntity> getListIterable() {
				return this.subject.getEntities();
			}
			@Override
			protected int size_() {
				return this.subject.getEntitiesSize();
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<CustomizationEntity> entityHolder) {
		return new TransformationPropertyValueModel<CustomizationEntity, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(CustomizationEntity value) {
				return Boolean.valueOf(value.entityNameIsValid());
			}
		};
	}


	private ClassChooserPane<CustomizationEntity> initializeClassChooser(Composite container, Hyperlink hyperlink) {
		return new ClassChooserPane<CustomizationEntity>(this, this.selectedEntityModel, this.buildPaneEnablerHolder(this.selectedEntityModel), container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<CustomizationEntity, String>(
					this.getSubjectHolder(), CustomizationEntity.DESCRIPTOR_CUSTOMIZER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return getSubjectParent().getDescriptorCustomizerOf(getSubjectName());
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						getSubjectParent().setDescriptorCustomizerOf(getSubjectName(), value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubjectParent().getDescriptorCustomizerOf(getSubjectName());
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubjectParent().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				getSubjectParent().setDescriptorCustomizerOf(getSubjectName(), className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return EclipseLinkCustomizer.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME;
			}
			
			private String getSubjectName() {
				return this.getSubjectHolder().getValue().getName();
			}
			
			private Customization getSubjectParent() {
				return this.getSubjectHolder().getValue().getParent();
			}
		};
	}

}

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
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomizationEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 *  EntityListComposite
 */
public class EntityListComposite extends Pane<EclipseLinkCustomization>
{
	private ModifiableCollectionValueModel<EclipseLinkCustomizationEntity> selectedEntitiesModel;
	private PropertyValueModel<EclipseLinkCustomizationEntity> selectedEntityModel;

	public EntityListComposite(Pane<? extends EclipseLinkCustomization> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedEntitiesModel = this.buildSelectedEntitiesModel();
		this.selectedEntityModel = this.buildSelectedEntityModel(this.selectedEntitiesModel);
	}

	private ModifiableCollectionValueModel<EclipseLinkCustomizationEntity> buildSelectedEntitiesModel() {
		return new SimpleCollectionValueModel<EclipseLinkCustomizationEntity>();
	}

	private PropertyValueModel<EclipseLinkCustomizationEntity> buildSelectedEntityModel(CollectionValueModel<EclipseLinkCustomizationEntity> selectedEntitiesModel) {
		return new CollectionPropertyValueModelAdapter<EclipseLinkCustomizationEntity, EclipseLinkCustomizationEntity>(selectedEntitiesModel) {
			@Override
			protected EclipseLinkCustomizationEntity buildValue() {
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
			JptJpaEclipseLinkUiMessages.CUSTOMIZATION_ENTITY_LIST_COMPOSITE_GROUP_TITLE
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Entities add/remove list pane
		new AddRemoveListPane<EclipseLinkCustomization, EclipseLinkCustomizationEntity>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntitiesListHolder(),
			this.selectedEntitiesModel,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);

		// Entity Customization property pane
		Hyperlink customizationHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_CUSTOMIZER_LABEL);
		this.initializeClassChooser(container, customizationHyperlink);
	}

	private AddRemoveListPane.Adapter<EclipseLinkCustomizationEntity> buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<EclipseLinkCustomizationEntity>() {

			public EclipseLinkCustomizationEntity addNewItem() {
				return EntityListComposite.this.addEntity();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<EclipseLinkCustomizationEntity> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<EclipseLinkCustomizationEntity> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				EclipseLinkCustomizationEntity customizationEntity = selectedItemsModel.iterator().next();
				getSubject().removeEntity(customizationEntity.getName());
			}
		};
	}
	
	private EclipseLinkCustomizationEntity addEntity() {
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

		dialog.setTitle(JptJpaEclipseLinkUiMessages.CUSTOMIZATION_ENTITY_LIST_COMPOSITE_DIALOG_TITLE);
		dialog.setMessage(JptJpaEclipseLinkUiMessages.CUSTOMIZATION_ENTITY_LIST_COMPOSITE_DIALOG_MESSAGE);

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				EclipseLinkCustomizationEntity entityCustomization = (EclipseLinkCustomizationEntity) element;
				return entityCustomization.getName();
			}
		};
	}

	private ModifiablePropertyValueModel<EclipseLinkCustomizationEntity> buildEntityHolder() {
		return new SimplePropertyValueModel<EclipseLinkCustomizationEntity>();
	}

	private ListValueModel<EclipseLinkCustomizationEntity> buildEntitiesListHolder() {
		return new ListAspectAdapter<EclipseLinkCustomization, EclipseLinkCustomizationEntity>(
				this.getSubjectHolder(), EclipseLinkCustomization.ENTITIES_LIST) {
			@Override
			protected ListIterable<EclipseLinkCustomizationEntity> getListIterable() {
				return this.subject.getEntities();
			}
			@Override
			protected int size_() {
				return this.subject.getEntitiesSize();
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<EclipseLinkCustomizationEntity> entityHolder) {
		return new TransformationPropertyValueModel<EclipseLinkCustomizationEntity, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(EclipseLinkCustomizationEntity value) {
				return Boolean.valueOf(value.entityNameIsValid());
			}
		};
	}


	private ClassChooserPane<EclipseLinkCustomizationEntity> initializeClassChooser(Composite container, Hyperlink hyperlink) {
		return new ClassChooserPane<EclipseLinkCustomizationEntity>(this, this.selectedEntityModel, this.buildPaneEnablerHolder(this.selectedEntityModel), container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkCustomizationEntity, String>(
					this.getSubjectHolder(), EclipseLinkCustomizationEntity.DESCRIPTOR_CUSTOMIZER_PROPERTY) {
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
			
			private EclipseLinkCustomization getSubjectParent() {
				return this.getSubjectHolder().getValue().getParent();
			}
		};
	}

}

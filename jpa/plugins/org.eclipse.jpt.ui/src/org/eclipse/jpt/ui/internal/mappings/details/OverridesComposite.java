/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.List;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.Owner;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class OverridesComposite extends BaseJpaComposite
{
	private ListViewer listViewer;
	
	private IEntity entity;
	private Adapter entityListener;
	
	private IOverride selectedOverride;
	private Adapter overrideListener;
	
	protected PageBook overridePageBook;

	protected ColumnComposite columnComposite;
	protected JoinColumnsComposite joinColumnsComposite;
	
	private Button overrideDefaultButton;
	
	public OverridesComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.entityListener = buildEntityListener();
		this.overrideListener = buildOverrideListener();
	}
	
	private Adapter buildEntityListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				entityChanged(notification);
			}
		};
	}
	
	private Adapter buildOverrideListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				overrideChanged(notification);
			}
		};
	}
	
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		Group attributeOverridesGroup = getWidgetFactory().createGroup(
			composite, JpaUiMappingsMessages.AttributeOverridesComposite_attributeOverrides);
		attributeOverridesGroup.setLayout(new GridLayout(2, true));
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		attributeOverridesGroup.setLayoutData(gridData);
		
		this.listViewer = buildAttributeOverridesListViewer(attributeOverridesGroup);
		gridData = new GridData();
		gridData.verticalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		this.listViewer.getList().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.listViewer.getList(), IJpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES);
		
		this.overrideDefaultButton = getWidgetFactory().createButton(attributeOverridesGroup, "Override Default", SWT.CHECK);
		this.overrideDefaultButton.addSelectionListener(buildOverrideDefaultSelectionListener());
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.overrideDefaultButton.setLayoutData(gridData);
		
		
		this.overridePageBook = buildOverridePageBook(attributeOverridesGroup);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.overridePageBook.setLayoutData(gridData);

		this.joinColumnsComposite = new JoinColumnsComposite(this.overridePageBook, this.commandStack, getWidgetFactory());
		this.columnComposite = new ColumnComposite(this.overridePageBook, this.commandStack, getWidgetFactory());
		this.overridePageBook.showPage(this.joinColumnsComposite.getControl());
	}
	
	protected PageBook buildOverridePageBook(Composite parent) {
		return new PageBook(parent, SWT.NONE);
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionListener(){
		
			public void widgetSelected(SelectionEvent e) {
				overrideDefaultButtonSelected(e);
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
				overrideDefaultButtonSelected(e);
			}
		};
	}
	
	private void overrideDefaultButtonSelected(SelectionEvent e) {
		boolean selection = this.overrideDefaultButton.getSelection();
		if (selection) {
			if (getSelectedOverride() instanceof IAttributeOverride) {
				int index = this.entity.getSpecifiedAttributeOverrides().size();
				IAttributeOverride attributeOverride = this.entity.createAttributeOverride(index);			
				this.entity.getSpecifiedAttributeOverrides().add(attributeOverride);
				attributeOverride.setName(this.selectedOverride.getName());
				attributeOverride.getColumn().setSpecifiedName(((IAttributeOverride) this.selectedOverride).getColumn().getName());
			}
			else {
				int index = this.entity.getSpecifiedAssociationOverrides().size();
				IAssociationOverride associationOverride = this.entity.createAssociationOverride(index);
				String name = this.selectedOverride.getName();
				this.entity.getSpecifiedAssociationOverrides().add(associationOverride);
				associationOverride.setName(name);
				//attributeOverride.getColumn().setSpecifiedName(this.attributeOverride.getColumn().getName());			
			}
		}
		else {
			if (getSelectedOverride() instanceof IAttributeOverride) {
				this.entity.getSpecifiedAttributeOverrides().remove(this.selectedOverride);
			}
			else {
				this.entity.getSpecifiedAssociationOverrides().remove(this.selectedOverride);
			}
		}
	}
	
	
	private ListViewer buildAttributeOverridesListViewer(Composite parent) {
		ListViewer listViewer = new ListViewer(parent, SWT.SINGLE | SWT.BORDER);
		listViewer.setLabelProvider(buildAttributeOverridesLabelProvider());
		listViewer.setContentProvider(buildAttributeOverridesContentProvider());
		
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				overridesListSelectionChanged(event);
			}
		});

		return listViewer;
	}
	
	protected void overridesListSelectionChanged(SelectionChangedEvent event) {
		if (((StructuredSelection) event.getSelection()).isEmpty()) {
			this.columnComposite.populate(null);
			this.columnComposite.enableWidgets(false);
			this.overrideDefaultButton.setSelection(false);
			this.overrideDefaultButton.setEnabled(false);
		}
		else {
			this.selectedOverride = getSelectedOverride();
			if (this.selectedOverride instanceof IAttributeOverride) {
				boolean specifiedOverride = this.entity.getSpecifiedAttributeOverrides().contains(this.selectedOverride);
				this.overrideDefaultButton.setSelection(specifiedOverride);
				this.overridePageBook.showPage(this.columnComposite.getControl());
				this.columnComposite.populate(((IAttributeOverride) this.selectedOverride).getColumn());
				this.columnComposite.enableWidgets(specifiedOverride);
				this.overrideDefaultButton.setEnabled(true);
			}
			else {
				boolean specifiedOverride = this.entity.getSpecifiedAssociationOverrides().contains(this.selectedOverride);
				this.overrideDefaultButton.setSelection(specifiedOverride);
				this.overridePageBook.showPage(this.joinColumnsComposite.getControl());
				this.joinColumnsComposite.populate(new JoinColumnsOwner((IAssociationOverride) getSelectedOverride()));
				this.joinColumnsComposite.enableWidgets(specifiedOverride);
				this.overrideDefaultButton.setEnabled(true);
			}
		}
	}
	
	private ILabelProvider buildAttributeOverridesLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				//TODO also display column name somehow
				return ((IOverride) element).getName();
			}
		};
	}

	
	private IContentProvider buildAttributeOverridesContentProvider() {
		return new IStructuredContentProvider() {
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		
			public void dispose() {
			}
		
			public Object[] getElements(Object inputElement) {
				IEntity entity = (IEntity) inputElement;
				return CollectionTools.addAll(entity.getAttributeOverrides().toArray(), entity.getAssociationOverrides());
			}
		};
	}

	private IOverride getSelectedOverride() {
		return (IOverride) ((StructuredSelection) this.listViewer.getSelection()).getFirstElement();
	}
	
	
	public void doPopulate(EObject obj) {
		this.entity = (IEntity) obj;
		if (this.entity == null) {
			this.selectedOverride = null;
			this.columnComposite.populate(null);
			this.joinColumnsComposite.populate(null);
			return;
		}
		
		if (this.listViewer.getInput() != entity) {
			this.listViewer.setInput(entity);
		}
		if (!this.entity.getAttributeOverrides().isEmpty()) {
			if (this.listViewer.getSelection().isEmpty()) {
				IOverride override = this.entity.getAttributeOverrides().get(0);
				this.listViewer.setSelection(new StructuredSelection(override));
			}
			else {
				Object selection = ((StructuredSelection) this.listViewer.getSelection()).getFirstElement();
				if (selection instanceof IAttributeOverride) {
					this.overridePageBook.showPage(this.columnComposite.getControl());
					this.columnComposite.enableWidgets(true);
					this.columnComposite.populate(((IAttributeOverride) selection).getColumn());
					}
				else {
					this.overridePageBook.showPage(this.joinColumnsComposite.getControl());
					this.joinColumnsComposite.enableWidgets(true);
					this.joinColumnsComposite.populate(new JoinColumnsOwner((IAssociationOverride) selection));
				}
			}
		}
		else {
			this.columnComposite.populate(null);
			this.columnComposite.enableWidgets(false);
		}
	}

	@Override
	protected void doPopulate() {
		this.columnComposite.doPopulate();
		this.joinColumnsComposite.doPopulate();
	}
	
	@Override
	protected void engageListeners() {
		if (this.entity != null) {
			this.entity.eAdapters().add(this.entityListener);
			for (IOverride attributeOverride : this.entity.getAttributeOverrides()) {
				attributeOverride.eAdapters().add(this.overrideListener);
			}	
			for (IOverride attributeOverride : this.entity.getAssociationOverrides()) {
				attributeOverride.eAdapters().add(this.overrideListener);
			}	
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.entity != null) {
			this.entity.eAdapters().remove(this.entityListener);
			for (IOverride attributeOverride : this.entity.getAttributeOverrides()) {
				attributeOverride.eAdapters().remove(this.overrideListener);
			}	
			for (IOverride attributeOverride : this.entity.getAssociationOverrides()) {
				attributeOverride.eAdapters().remove(this.overrideListener);
			}	
		}
	}

	
	protected void entityChanged(Notification notification) {
		switch (notification.getFeatureID(IEntity.class)) {
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				if (notification.getEventType() == Notification.ADD) {
					((IOverride) notification.getNewValue()).eAdapters().add(this.overrideListener);
					final Object newValue = notification.getNewValue();
					((IOverride) newValue).eAdapters().add(this.overrideListener);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (listViewer.getList().isDisposed()) {
								return;
							}
							listViewer.setSelection(new StructuredSelection(newValue));
						}
					});
				}
				else if (notification.getEventType() == Notification.ADD_MANY) {
					List<IOverride> addedList = (List<IOverride>) notification.getNewValue();
					for (IOverride override : addedList) {
						override.eAdapters().add(this.overrideListener);
					}
				}
				else if (notification.getEventType() == Notification.REMOVE) {
					((IOverride) notification.getOldValue()).eAdapters().remove(this.overrideListener);				
				}
				else if (notification.getEventType() == Notification.REMOVE_MANY) {
					List<IOverride> removedList = (List<IOverride>) notification.getOldValue();
					for (IOverride override : removedList) {
						override.eAdapters().remove(this.overrideListener);
					}
				}
				break;
			case JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
			case JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (listViewer.getList().isDisposed()) {
							return;
						}
						listViewer.refresh();
					}
				});
				break;
		}
	}

	protected void overrideChanged(Notification notification) {
		switch (notification.getFeatureID(IOverride.class)) {
			case JpaCoreMappingsPackage.IOVERRIDE__NAME :
				final IOverride override = (IOverride) notification.getNotifier();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						OverridesComposite.this.listViewer.update(override, null);
					}
				});
				break;
			default :
				break;
		}
	}
	
	public void dispose() {
		this.columnComposite.dispose();
		this.joinColumnsComposite.dispose();
		super.dispose();
	}
	

	void addJoinColumn() {
		JoinColumnInAssociationOverrideDialog dialog = new JoinColumnInAssociationOverrideDialog(this.getControl().getShell(), (IAssociationOverride) getSelectedOverride());
		this.addJoinColumnFromDialog(dialog);
	}
	
	private void addJoinColumnFromDialog(JoinColumnInAssociationOverrideDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		int index = ((IAssociationOverride) getSelectedOverride()).getJoinColumns().size();
		IJoinColumn joinColumn = ((IAssociationOverride) getSelectedOverride()).createJoinColumn(index);
		((IAssociationOverride) getSelectedOverride()).getSpecifiedJoinColumns().add(joinColumn);
		joinColumn.setSpecifiedName(dialog.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(dialog.getReferencedColumnName());
	}

	void editJoinColumn(IJoinColumn joinColumn) {
		JoinColumnInAssociationOverrideDialog dialog = new JoinColumnInAssociationOverrideDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}
	
	private void editJoinColumnFromDialog(JoinColumnInAssociationOverrideDialog dialog, IJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
	}
	
	private void editJoinColumnDialogOkd(JoinColumnInAssociationOverrideDialog dialog, IJoinColumn joinColumn) {
		String name = dialog.getSelectedName();
		String referencedColumnName = dialog.getReferencedColumnName();

		if (dialog.isDefaultNameSelected()) {
			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null || !joinColumn.getSpecifiedName().equals(name)){
			joinColumn.setSpecifiedName(name);
		}
		
		if (dialog.isDefaultReferencedColumnNameSelected()) {
			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null || !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)){
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}
	
	private class JoinColumnsOwner extends JpaEObject implements Owner {
		
		IAssociationOverride associationOverride;
		
		public JoinColumnsOwner(IAssociationOverride associationOverride) {
			super();
			this.associationOverride = associationOverride;
		}
		
		public void addJoinColumn() {
			OverridesComposite.this.addJoinColumn();
		}
		
		public boolean containsSpecifiedJoinColumns() {
			return this.associationOverride.containsSpecifiedJoinColumns();
		}
		
		public IJoinColumn createJoinColumn(int index) {
			return this.associationOverride.createJoinColumn(index);
		}
		
		public List<IJoinColumn> getJoinColumns() {
			return this.associationOverride.getJoinColumns();
		}
		
		public List<IJoinColumn> getSpecifiedJoinColumns() {
			return this.associationOverride.getSpecifiedJoinColumns();
		}
		
		public int specifiedJoinColumnsFeatureId() {
			return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS;
		}
		
		public Class owningFeatureClass() {
			return IAssociationOverride.class;
		}
		
		public void editJoinColumn(IJoinColumn joinColumn) {
			OverridesComposite.this.editJoinColumn(joinColumn);
		}
		
		public EObject getEObject() {
			return this.associationOverride;
		}
	}
	

}

/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Arrays;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

// TODO get Default updating appropriately based on Entity name default
	
public class EntityNameCombo extends BaseJpaController
{
	private IEntity entity;
	private Adapter entityListener;
	
	private CCombo combo;
	
	
	public EntityNameCombo(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, commandStack, widgetFactory);
		buildEntityListener();
	}
	
	
	private void buildEntityListener() {
		entityListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				entityChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		combo = this.widgetFactory.createCCombo(parent, SWT.FLAT);
		combo.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					comboModified(e);
				}
			});
	}
	
	private void comboModified(ModifyEvent e) {
		if (isPopulating()) {
			return;
		}
		
		String text = ((CCombo) e.getSource()).getText();
		if (text.equals(combo.getItem(0))) {
			text = null;
		}
		
		entity.setSpecifiedName(text);
		
		// TODO Does this need to be done?
		//this.editingDomain.getCommandStack().execute(SetCommand.create(this.editingDomain, this.entity, MappingsPackage.eINSTANCE.getEntity_SpecifiedName(), text));
	}
	
	private void entityChanged(Notification notification) {
		switch (notification.getFeatureID(IEntity.class)) {
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							populate();
						}
					});
				break;
			case JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							populate();
						}
					});
				break;
		}
	}
	
	@Override
	protected void engageListeners() {
		if (entity != null) {
			entity.eAdapters().add(entityListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (entity != null) {
			entity.eAdapters().remove(entityListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		entity = (IEntity) obj;
		populateCombo();
	}
	
	@Override
	protected void doPopulate() {
		populateCombo();
	}
	
	private void populateCombo() {
		if (entity == null) {
			combo.clearSelection();
			combo.setItems(new String[] {});
			return;
		}
		
		String defaultItem = NLS.bind(JptUiMappingsMessages.EntityGeneralSection_nameDefaultWithOneParam, entity.getDefaultName());
		String specifiedName = entity.getSpecifiedName();
		
		if (specifiedName == null) {
			setComboData(defaultItem, new String[] {defaultItem});
		}
		else {
			setComboData(specifiedName, new String[] {defaultItem});
		}
	}
	
	private void setComboData(String text, String[] items) {
		if (! Arrays.equals(items, combo.getItems())) {
			combo.setItems(items);
		}
		
		if (! text.equals(combo.getText())) {
			combo.setText(text);
		}
	}
	
	public CCombo getCombo() {
		return combo;
	}
	
	@Override
	public Control getControl() {
		return getCombo();
	}
}

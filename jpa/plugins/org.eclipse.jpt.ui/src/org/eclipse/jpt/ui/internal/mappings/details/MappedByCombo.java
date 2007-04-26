/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class MappedByCombo extends BaseJpaController
{
	private INonOwningMapping nonOwningMapping;
	private Adapter nonOwningMappingListener;
	
	private CCombo combo;
	
	public MappedByCombo(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, commandStack, widgetFactory);
		this.nonOwningMappingListener = buildListener();
	}
	
	private Adapter buildListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				nonOwningMappingChanged(notification);
			}
		};
	}
	@Override
	protected void buildWidget(Composite parent) {
		this.combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(combo,IJpaHelpContextIds.MAPPING_MAPPED_BY);
		this.combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String mappedBy = ((CCombo) e.getSource()).getText();
				if (mappedBy.equals("")) { //$NON-NLS-1$
					mappedBy = null;
					if (getNonOwningMapping().getMappedBy() == null || getNonOwningMapping().getMappedBy().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				if (getNonOwningMapping().getMappedBy() == null || !getNonOwningMapping().getMappedBy().equals(mappedBy)) {
					getNonOwningMapping().setMappedBy(mappedBy);
				}
			}
		});
	}
	
	private INonOwningMapping getNonOwningMapping() {
		return this.nonOwningMapping;
	}

	@Override
	protected void doPopulate(EObject obj) {
		this.nonOwningMapping = (INonOwningMapping) obj;
		
		if (this.nonOwningMapping == null) {
			return;
		}
		
		populateCombo();
	}

	private void populateCombo() {
		populateChoices();
		populateMappedByText();
	}
	
	private void populateChoices() {
		if (this.combo.getItemCount() > 0) {
			this.combo.removeAll();
		}
		for (Iterator<String> i = this.nonOwningMapping.possibleMappedByAttributeNames(); i.hasNext(); ) {
			this.combo.add(i.next());
		}
	}
	
	private void populateMappedByText() {
		String mappedBy = this.nonOwningMapping.getMappedBy();
		if (mappedBy != null) {
			if (!this.combo.getText().equals(mappedBy)) {
				this.combo.setText(mappedBy);
			}
		}
	}
	
	@Override
	protected void doPopulate() {
	}

	@Override
	protected void engageListeners() {
		if (this.nonOwningMapping != null) {
			this.nonOwningMapping.eAdapters().add(this.nonOwningMappingListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.nonOwningMapping != null) {
			this.nonOwningMapping.eAdapters().remove(this.nonOwningMappingListener);
		}
	}
	
	protected void nonOwningMappingChanged(Notification notification) {
		if (notification.getFeatureID(INonOwningMapping.class) == JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateMappedByText();
				}
			});
		}
		else if (notification.getFeatureID(IRelationshipMapping.class) == JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateChoices();
				}
			});
		}
	}

	@Override
	public Control getControl() {
		return this.combo;
	}
}

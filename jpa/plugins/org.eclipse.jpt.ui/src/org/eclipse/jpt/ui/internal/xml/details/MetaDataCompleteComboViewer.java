/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.context.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class MetaDataCompleteComboViewer extends BaseJpaController
{
	private XmlTypeMapping mapping;
	private Adapter typeMappingListener;
	
	private ComboViewer comboViewer;


	public MetaDataCompleteComboViewer(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildTypeMappingListener();
	}
	
	
	private void buildTypeMappingListener() {
		this.typeMappingListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				typeMappingChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.comboViewer = new ComboViewer(combo);
		this.comboViewer.setLabelProvider(buildLabelProvider());
		this.comboViewer.add(DefaultFalseBoolean.VALUES.toArray());
		this.comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				MetaDataCompleteComboViewer.this.metadataCompleteSelectionChanged(event.getSelection());
			}
		});
	}
	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == DefaultFalseBoolean.DEFAULT) {
					//TODO need to move this to the model, don't want hardcoded String
					return NLS.bind(JptUiMappingsMessages.MetaDataCompleteCombo_Default, "False");
				}
				return super.getText(element);
			}
		};
	}

	void metadataCompleteSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			DefaultFalseBoolean metadataComplete = (DefaultFalseBoolean) ((IStructuredSelection) selection).getFirstElement();
			if ( ! this.mapping.getMetadataComplete().equals(metadataComplete)) {
				this.mapping.setMetadataComplete(metadataComplete);
//				this.editingDomain.getCommandStack().execute(
//					SetCommand.create(
//						this.editingDomain,
//						this.basicMapping,
//						OrmPackage.eINSTANCE.getBasicMapping_Optional(),
//						optional
//					)
//				);
			}
		}
	}

	private void typeMappingChanged(Notification notification) {
		if (notification.getFeatureID(XmlTypeMapping.class) == 
				OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						populate();
					}
				});
		}
	}
	
	@Override
	protected void engageListeners() {
		if (this.mapping != null) {
			this.mapping.eAdapters().add(this.typeMappingListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.mapping != null) {
			this.mapping.eAdapters().remove(this.typeMappingListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.mapping = (XmlTypeMapping) obj;
		populateCombo();
	}
	
	@Override
	protected void doPopulate() {
		populateCombo();
	}
	
	private void populateCombo() {
		if (this.mapping == null) {
			return;
		}
		
		DefaultFalseBoolean metadataComplete = this.mapping.getMetadataComplete();
		
		if (((IStructuredSelection) this.comboViewer.getSelection()).getFirstElement() != metadataComplete) {
			this.comboViewer.setSelection(new StructuredSelection(metadataComplete));
		}
	}

	
	@Override
	public Control getControl() {
		return this.comboViewer.getCombo();
	}
}

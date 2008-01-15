/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlJavaAttributeChooser extends BaseJpaController<XmlAttributeMapping<? extends AttributeMapping>>
{
	private XmlPersistentAttribute attribute;
	private Adapter persistentAttributeListener;

	private Text text;


	public XmlJavaAttributeChooser(PropertyValueModel<? extends XmlAttributeMapping<? extends AttributeMapping>> subjectHolder,
	                               Composite parent,
	                               TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		buildPersistentAttributeListener();
	}


	private void buildPersistentAttributeListener() {
		this.persistentAttributeListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentAttributeChanged(notification);
			}
		};
	}

	@Override
	protected void buildWidgets(Composite parent) {
		text = getWidgetFactory().createText(parent, "");
		text.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					textModified(e);
				}
			});
	}

	private void textModified(ModifyEvent e) {
		String text = ((Text) e.getSource()).getText();
		attribute.setName(text);

		// TODO Does this need to be done?
		//this.editingDomain.getCommandStack().execute(SetCommand.create(this.editingDomain, this.entity, MappingsPackage.eINSTANCE.getEntity_SpecifiedName(), text));
	}

	private void persistentAttributeChanged(Notification notification) {
		if (notification.getFeatureID(XmlAttributeMapping.class) ==
				OrmPackage.XML_PERSISTENT_ATTRIBUTE__NAME) {
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
		if (attribute() != null) {
			attribute().eAdapters().add(persistentAttributeListener);
		}
	}

	@Override
	protected void disengageListeners() {
		if (attribute() != null) {
			attribute().eAdapters().remove(persistentAttributeListener);
		}
	}

	@Override
	public void doPopulate() {
		populateText();
	}

	private XmlPersistentAttribute attribute() {
		return (subject() != null) ? subject().getPersistentAttribute() : null;
	}

	private void populateText() {
		if (attribute() == null) {
			text.clearSelection();
			return;
		}

		String name = attribute().getName();

		if (name == null) {
			name = "";
		}
		setTextData(name);
	}

	private void setTextData(String textData) {
		if (! textData.equals(text.getText())) {
			text.setText(textData);
		}
	}

	@Override
	public Control getControl() {
		return text;
	}
}
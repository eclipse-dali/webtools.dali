/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.structure;

import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.IEmbeddedId;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.IManyToMany;
import org.eclipse.jpt.core.internal.mappings.IManyToOne;
import org.eclipse.jpt.core.internal.mappings.IOneToMany;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.core.internal.mappings.ITransient;
import org.eclipse.jpt.core.internal.mappings.IVersion;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsImages;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class XmlPersistentAttributeItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
		IStructuredItemContentProvider,
		ITreeItemContentProvider, 
		IItemLabelProvider
{
	public XmlPersistentAttributeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public Object getImage(Object object) {
		XmlAttributeMapping mapping = ((XmlPersistentAttribute) object).getMapping();
		
		Image image;
		if (mapping instanceof IBasic) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.BASIC);
		}
		else if (mapping instanceof IId) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.ID);
		}
		else if (mapping instanceof IVersion) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.VERSION);
		}
		else if (mapping instanceof IEmbedded) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.EMBEDDED);
		}
		else if (mapping instanceof IEmbeddedId) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.EMBEDDED_ID);
		}
		else if (mapping instanceof IOneToOne) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.ONE_TO_ONE);
		}
		else if (mapping instanceof IOneToMany) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.ONE_TO_MANY);
		}
		else if (mapping instanceof IManyToOne) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.MANY_TO_ONE);
		}
		else if (mapping instanceof IManyToMany) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.MANY_TO_MANY);
		}
		else if (mapping instanceof ITransient) {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.TRANSIENT);
		}
		else {
			image = JptUiMappingsImages.getImage(JptUiMappingsImages.NULL_ATTRIBUTE_MAPPING);
		}
		
		// apply "ghosting"
		if (mapping.isVirtual()) {
			Color offwhite = new Color(image.getDevice(), 250, 250, 250);
			ImageData imageData = ImageUtilities.createShadedImage(image, offwhite);
			image = new Image(image.getDevice(), imageData);
		}
		
		return image;
	}
	
	@Override
	public String getText(Object object) {
		return ((XmlPersistentAttribute) object).getName();
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(XmlPersistentAttribute.class)) {
		case OrmPackage.XML_PERSISTENT_ATTRIBUTE__MAPPING:
		case OrmPackage.XML_PERSISTENT_ATTRIBUTE__NAME:
			fireNotifyChanged(new ViewerNotification(notification, notification
					.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}
}

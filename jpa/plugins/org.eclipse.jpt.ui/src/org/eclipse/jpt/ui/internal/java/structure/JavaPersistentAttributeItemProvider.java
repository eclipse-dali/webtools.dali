/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.structure;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.JpaCorePackage;
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
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsImages;

public class JavaPersistentAttributeItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
		IStructuredItemContentProvider,
		ITreeItemContentProvider, 
		IItemLabelProvider
{
	public JavaPersistentAttributeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public Object getImage(Object object) {
		IAttributeMapping mapping = ((IPersistentAttribute) object).getMapping();
		
		if (mapping instanceof IBasic) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.BASIC);
		}
		else if (mapping instanceof IId) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.ID);
		}
		else if (mapping instanceof IVersion) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.VERSION);
		}
		else if (mapping instanceof IEmbedded) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.EMBEDDED);
		}
		else if (mapping instanceof IEmbeddedId) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.EMBEDDED_ID);
		}
		else if (mapping instanceof IOneToOne) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.ONE_TO_ONE);
		}
		else if (mapping instanceof IOneToMany) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.ONE_TO_MANY);
		}
		else if (mapping instanceof IManyToOne) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.MANY_TO_ONE);
		}
		else if (mapping instanceof IManyToMany) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.MANY_TO_MANY);
		}
		else if (mapping instanceof ITransient) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.TRANSIENT);
		}
		else {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.NULL_ATTRIBUTE_MAPPING);
		}
	}
	
	@Override
	public String getText(Object object) {
		return ((IPersistentAttribute) object).getName();
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(IPersistentAttribute.class)) {
		case JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING:
			fireNotifyChanged(new ViewerNotification(notification, notification
					.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}
}

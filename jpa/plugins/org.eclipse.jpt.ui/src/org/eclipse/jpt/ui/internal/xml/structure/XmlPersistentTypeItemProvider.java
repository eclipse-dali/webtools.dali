/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.structure;

import java.util.Collection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsImages;

public class XmlPersistentTypeItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
			IStructuredItemContentProvider,
			ITreeItemContentProvider, 
			IItemLabelProvider
{
	public XmlPersistentTypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	
	@Override
	protected Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(OrmPackage.Literals.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES);
			childrenFeatures.add(OrmPackage.Literals.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES);
		}
		return childrenFeatures;
	}
	
	@Override
	public Object getImage(Object object) {
		ITypeMapping mapping = ((IPersistentType) object).getMapping();
		
		if (mapping instanceof IEntity) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.ENTITY);
		}
		else if (mapping instanceof IEmbeddable) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.EMBEDDABLE);
		}
		else if (mapping instanceof IMappedSuperclass) {
			return JptUiMappingsImages.getImage(JptUiMappingsImages.MAPPED_SUPERCLASS);
		}
		else {
			return null;
		}
	}
	
	@Override
	public String getText(Object object) {
		return ((XmlPersistentType) object).getClass_();
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(XmlPersistentType.class)) {
			case JpaCorePackage.IPERSISTENT_TYPE__MAPPING_KEY:
			case OrmPackage.XML_PERSISTENT_TYPE__CLASS:
				fireNotifyChanged(
					new ViewerNotification(
						notification, notification.getNotifier(), false, true));
				return;
			
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES:
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES:
				fireNotifyChanged(
					new ViewerNotification(
						notification, notification.getNotifier(), true, false));
				return;
		}
		
		super.notifyChanged(notification);
	}
}

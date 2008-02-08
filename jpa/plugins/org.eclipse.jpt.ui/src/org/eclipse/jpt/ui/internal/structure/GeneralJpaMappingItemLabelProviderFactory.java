/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public abstract class GeneralJpaMappingItemLabelProviderFactory
	implements IItemLabelProviderFactory
{
	public IItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider labelProvider) {
		if (item instanceof IPersistentType) {
			return new PersistentTypeItemLabelProvider((IPersistentType) item, labelProvider);
		}
		else if (item instanceof IPersistentAttribute) {
			return new PersistentAttributeItemLabelProvider((IPersistentAttribute) item, labelProvider);
		}
		return null;
	}
	
	
	public static class PersistentTypeItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistentTypeItemLabelProvider(
				IPersistentType persistentType, DelegatingContentAndLabelProvider labelProvider) {
			super(persistentType, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<IPersistentType, String>(IPersistentType.NAME_PROPERTY, (IPersistentType) model()) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new PropertyAspectAdapter<IPersistentType, Image>(IPersistentType.MAPPING_PROPERTY, (IPersistentType) model()) {
				@Override
				protected Image buildValue_() {
					return JpaMappingImageHelper.imageForTypeMapping(subject.mappingKey());
				}
			};
		}	
	}
	
	
	public static class PersistentAttributeItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistentAttributeItemLabelProvider(
				IPersistentAttribute persistentAttribute, DelegatingContentAndLabelProvider labelProvider) {
			super(persistentAttribute, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<IPersistentAttribute, String>(IPersistentAttribute.NAME_PROPERTY, (IPersistentAttribute) model()) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new PropertyAspectAdapter<IPersistentAttribute, Image>(
					new String[] {IPersistentAttribute.DEFAULT_MAPPING_PROPERTY, IPersistentAttribute.SPECIFIED_MAPPING_PROPERTY}, 
					(IPersistentAttribute) model()) {
				@Override
				protected Image buildValue_() {
					Image image = JpaMappingImageHelper.imageForAttributeMapping(subject.mappingKey());
					if (((IPersistentAttribute) model()).isVirtual()) {
						return JptUiIcons.ghost(image);
					}
					return image;
				}
			};
		}	
	}
}

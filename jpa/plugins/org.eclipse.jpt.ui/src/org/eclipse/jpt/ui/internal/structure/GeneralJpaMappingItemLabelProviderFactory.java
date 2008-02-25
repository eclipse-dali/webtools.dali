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

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public abstract class GeneralJpaMappingItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider labelProvider) {
		if (item instanceof PersistentType) {
			return new PersistentTypeItemLabelProvider((PersistentType) item, labelProvider);
		}
		else if (item instanceof PersistentAttribute) {
			return new PersistentAttributeItemLabelProvider((PersistentAttribute) item, labelProvider);
		}
		return null;
	}
	
	
	public static class PersistentTypeItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistentTypeItemLabelProvider(
				PersistentType persistentType, DelegatingContentAndLabelProvider labelProvider) {
			super(persistentType, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<PersistentType, String>(PersistentType.NAME_PROPERTY, (PersistentType) model()) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new PropertyAspectAdapter<PersistentType, Image>(PersistentType.MAPPING_PROPERTY, (PersistentType) model()) {
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
				PersistentAttribute persistentAttribute, DelegatingContentAndLabelProvider labelProvider) {
			super(persistentAttribute, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<PersistentAttribute, String>(PersistentAttribute.NAME_PROPERTY, (PersistentAttribute) model()) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new PropertyAspectAdapter<PersistentAttribute, Image>(
					new String[] {PersistentAttribute.DEFAULT_MAPPING_PROPERTY, PersistentAttribute.SPECIFIED_MAPPING_PROPERTY}, 
					(PersistentAttribute) model()) {
				@Override
				protected Image buildValue_() {
					Image image = JpaMappingImageHelper.imageForAttributeMapping(subject.mappingKey());
					if (((PersistentAttribute) model()).isVirtual()) {
						return JptUiIcons.ghost(image);
					}
					return image;
				}
			};
		}	
	}
}

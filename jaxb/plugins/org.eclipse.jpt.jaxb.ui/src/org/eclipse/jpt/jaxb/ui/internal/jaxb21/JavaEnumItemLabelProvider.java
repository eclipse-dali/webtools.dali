/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;


public class JavaEnumItemLabelProvider
		extends JavaTypeItemLabelProvider<JavaEnum> {
	
	public JavaEnumItemLabelProvider(JavaEnum javaEnum, ItemExtendedLabelProvider.Manager manager) {
		super(javaEnum, manager);
	}
	
	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new JavaEnumImageDescriptorModel(this.item);
	}
	
	
	protected class JavaEnumImageDescriptorModel
			extends AspectPropertyValueModelAdapter<JavaEnum, ImageDescriptor> {
		
		protected final PropertyValueModel<JavaEnumMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JavaEnumImageDescriptorModel(JavaEnum subject) {
			super(new StaticPropertyValueModel<JavaEnum>(subject));
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<JavaEnumMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JavaEnum, JavaEnumMapping> (JavaEnum.MAPPING_PROPERTY, JavaEnumItemLabelProvider.this.item) {
				@Override
				protected JavaEnumMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JavaEnumMapping, Boolean>(this.mappingModel, JavaEnumMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isXmlTransient());
				}
			};
		}
		
		protected PropertyChangeListener buildPropertyChangeListener() {
			// transform the subject's property change events into VALUE property change events
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaEnumImageDescriptorModel.this.aspectChanged();
				}
			};
		}
		
		@Override
		protected ImageDescriptor buildValue_() {
			if ((this.mappingModel.getValue() != null) && (this.isXmlTransientModel.getValue() == Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_TRANSIENT_ENUM;
			}
			return JptJaxbUiImages.JAXB_ENUM;
		}
		
		@Override
		protected void engageSubject_() {
			this.mappingModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
		}
		
		@Override
		protected void disengageSubject_() {
			this.mappingModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
		}
	}
}

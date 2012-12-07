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
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;


public class JaxbClassItemLabelProvider
	extends JaxbTypeItemLabelProvider<JaxbClass>
{
	
	public JaxbClassItemLabelProvider(JaxbClass jaxbPersistentClass, ItemExtendedLabelProvider.Manager manager) {
		super(jaxbPersistentClass, manager);
	}
	
	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new JaxbClassImageDescriptorModel(this.item);
	}
	
	
	protected class JaxbClassImageDescriptorModel
			extends AspectPropertyValueModelAdapter<JaxbClass, ImageDescriptor> {
		
		protected final PropertyValueModel<Boolean> isXmlRegistryModel;
			
		protected final PropertyValueModel<JaxbClassMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JaxbClassImageDescriptorModel(JaxbClass subject) {
			super(new StaticPropertyValueModel<JaxbClass>(subject));
			this.isXmlRegistryModel = buildIsXmlRegistryModel();
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<Boolean> buildIsXmlRegistryModel() {
			return new PropertyAspectAdapter<JaxbClass, Boolean>(JaxbClass.XML_REGISTRY_PROPERTY, JaxbClassItemLabelProvider.this.item) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.getXmlRegistry() != null);
				}
			};
		}
		
		protected PropertyValueModel<JaxbClassMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JaxbClass, JaxbClassMapping> (JaxbType.MAPPING_PROPERTY, JaxbClassItemLabelProvider.this.item) {
				@Override
				protected JaxbClassMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JaxbClassMapping, Boolean>(this.mappingModel, JaxbTypeMapping.XML_TRANSIENT_PROPERTY) {
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
					JaxbClassImageDescriptorModel.this.aspectChanged();
				}
			};
		}
		
		@Override
		protected ImageDescriptor buildValue_() {
			if ((this.mappingModel.getValue() != null) && (this.isXmlTransientModel.getValue() == Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_TRANSIENT_CLASS;
			}
			if (this.isXmlRegistryModel.getValue() == Boolean.TRUE) {
				return JptJaxbUiImages.JAXB_REGISTRY;
			}
			return JptJaxbUiImages.JAXB_CLASS;
		}
		
		@Override
		protected void engageSubject_() {
			this.isXmlRegistryModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.mappingModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
		}
		
		@Override
		protected void disengageSubject_() {
			this.isXmlRegistryModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.mappingModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
		}
	}
}

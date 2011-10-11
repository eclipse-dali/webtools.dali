/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.swt.graphics.Image;


public class JaxbClassItemLabelProvider
		extends JaxbTypeItemLabelProvider {
	
	public JaxbClassItemLabelProvider(
			JaxbClass jaxbPersistentClass, DelegatingContentAndLabelProvider labelProvider) {
		
		super(jaxbPersistentClass, labelProvider);
	}
	
	
	@Override
	public JaxbClass getModel() {
		return (JaxbClass) super.getModel();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new JaxbClassImageModel(getModel());
	}
	
	
	protected class JaxbClassImageModel
			extends AspectPropertyValueModelAdapter<JaxbClass, Image> {
		
		protected final PropertyValueModel<Boolean> isXmlRegistryModel;
			
		protected final PropertyValueModel<JaxbClassMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JaxbClassImageModel(JaxbClass subject) {
			super(new StaticPropertyValueModel(subject));
			this.isXmlRegistryModel = buildIsXmlRegistryModel();
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<Boolean> buildIsXmlRegistryModel() {
			return new PropertyAspectAdapter<JaxbClass, Boolean>(JaxbClass.XML_REGISTRY_PROPERTY, JaxbClassItemLabelProvider.this.getModel()) {
				@Override
				protected Boolean buildValue_() {
					return this.subject.getXmlRegistry() != null;
				}
			};
		}
		
		protected PropertyValueModel<JaxbClassMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JaxbClass, JaxbClassMapping> (JaxbClass.MAPPING_PROPERTY, JaxbClassItemLabelProvider.this.getModel()) {
				@Override
				protected JaxbClassMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JaxbClassMapping, Boolean>(this.mappingModel, JaxbClassMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return this.subject.isXmlTransient();
				}
			};
		}
		
		protected PropertyChangeListener buildPropertyChangeListener() {
			// transform the subject's property change events into VALUE property change events
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JaxbClassImageModel.this.propertyChanged();
				}
			};
		}
		
		@Override
		protected Image buildValue_() {
			if (this.mappingModel.getValue() != null) {
				if (this.isXmlTransientModel.getValue() == Boolean.TRUE) {
					return JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_TRANSIENT_CLASS);
				}
			}
			else if (this.isXmlRegistryModel.getValue() == Boolean.TRUE) {
				return JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_REGISTRY);
			}
			return JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_CLASS);
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

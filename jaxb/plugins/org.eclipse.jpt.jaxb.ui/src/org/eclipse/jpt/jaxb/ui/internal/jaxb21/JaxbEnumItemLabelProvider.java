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
import org.eclipse.jpt.jaxb.core.context.JaxbEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.swt.graphics.Image;


public class JaxbEnumItemLabelProvider
		extends JaxbTypeItemLabelProvider {
	
	public JaxbEnumItemLabelProvider(
			JaxbEnum jaxbEnum, DelegatingContentAndLabelProvider labelProvider) {
		super(jaxbEnum, labelProvider);
	}
	
	
	@Override
	public JaxbEnum getModel() {
		return (JaxbEnum) super.getModel();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new JaxbEnumImageModel(getModel());
	}
	
	
	protected class JaxbEnumImageModel
			extends AspectPropertyValueModelAdapter<JaxbEnum, Image> {
		
		protected final PropertyValueModel<JaxbEnumMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JaxbEnumImageModel(JaxbEnum subject) {
			super(new StaticPropertyValueModel(subject));
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<JaxbEnumMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JaxbEnum, JaxbEnumMapping> (JaxbEnum.MAPPING_PROPERTY, JaxbEnumItemLabelProvider.this.getModel()) {
				@Override
				protected JaxbEnumMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JaxbEnumMapping, Boolean>(this.mappingModel, JaxbEnumMapping.XML_TRANSIENT_PROPERTY) {
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
					JaxbEnumImageModel.this.propertyChanged();
				}
			};
		}
		
		@Override
		protected Image buildValue_() {
			if (this.mappingModel.getValue() != null) {
				if (this.isXmlTransientModel.getValue() == Boolean.TRUE) {
					return JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_TRANSIENT_ENUM);
				}
			}
			return JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_ENUM);
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

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * EntityCustomizerProperties
 */
public class EntityCustomizerProperties extends AbstractModel {

	private Customization customization;
	
	private String entityName;

	public static final String DESCRIPTOR_CUSTOMIZER_PROPERTY = Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY;
	
	SimplePropertyValueModel<Customization> customizationHolder;
	PropertyValueModel<String> descriptorCustomizerHolder;
	PropertyChangeListener descriptorCustomizerListener;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public EntityCustomizerProperties(Customization customization, String entityName) {
		super();
		this.customization = customization;
		this.entityName = entityName;
		this.customizationHolder = new SimplePropertyValueModel<Customization>(this.customization);

		this.descriptorCustomizerHolder = this.buildDescriptorCustomizerAA(this.customizationHolder);
		this.descriptorCustomizerListener = this.buildDescriptorCustomizerChangeListener();
		this.descriptorCustomizerHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.descriptorCustomizerListener);
	}

	// ********** behavior **********
	public boolean entityNameIsValid() {
		return !StringTools.stringIsEmpty(this.entityName);
	}

	public String getEntityName() {
		return this.entityName;
	}

	public String getDescriptorCustomizer() {
		return this.customization.getDescriptorCustomizer(this.entityName);
	}

	public String getDefaultDescriptorCustomizer() {
		return this.customization.getDefaultDescriptorCustomizer();
	}

	public void setDescriptorCustomizer(String descriptorCustomizer) {
		String old = this.getDescriptorCustomizer();
		if (this.attributeValueHasChanged(old, descriptorCustomizer)) {
			this.customization.setDescriptorCustomizer(descriptorCustomizer, this.entityName);
			this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, descriptorCustomizer);
		}
	}

	public JpaProject getJpaProject() {
		return this.customization.getJpaProject();
	}

	// ********** PropertyChangeListener **********
	
	private PropertyValueModel<String> buildDescriptorCustomizerAA(PropertyValueModel<Customization> subjectHolder) {
		return new PropertyAspectAdapter<Customization, String>(
								subjectHolder, DESCRIPTOR_CUSTOMIZER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getDescriptorCustomizer(EntityCustomizerProperties.this.entityName);
			}
		};
	}
	
	private PropertyChangeListener buildDescriptorCustomizerChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				EntityCustomizerProperties.this.descriptorCustomizerChanged(e);
			}
		};
	}

	protected void descriptorCustomizerChanged(PropertyChangeEvent e) {
		String old = (String) e.getOldValue();
		String newDescriptorCustomizer = (String) e.getNewValue();
		this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, newDescriptorCustomizer);
		return;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("name: ");
		sb.append(this.entityName);
		sb.append(", descriptorCustomizer: ");
		sb.append(this.getDescriptorCustomizer());
	}
}

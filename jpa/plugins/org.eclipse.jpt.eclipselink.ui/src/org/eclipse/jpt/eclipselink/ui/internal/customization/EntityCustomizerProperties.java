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

/**
 * EntityCustomizerProperties
 */
public class EntityCustomizerProperties extends AbstractModel {

	private Customization customization;
	
	private String entityName;
	
	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public EntityCustomizerProperties(Customization customization, String entityName) {
		super();
		this.customization = customization;
		this.entityName = entityName;
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
		this.customization.setDescriptorCustomizer(descriptorCustomizer, this.entityName);
	}

	public JpaProject getJpaProject() {
		return this.customization.getJpaProject();
	}
}

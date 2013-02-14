/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmManagedType;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1;

public class GenericOrmConverterType extends AbstractOrmManagedType
	implements OrmConverterType2_1
{
	protected boolean autoApply;

	protected Boolean specifiedAutoApply;

	public GenericOrmConverterType(EntityMappings2_1 parent, XmlConverter_2_1 xmlConverter) {
		super(parent, xmlConverter);
		this.specifiedAutoApply = this.buildSpecifiedAutoApply();
		this.autoApply = this.buildAutoApply();
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAutoApply_(this.buildSpecifiedAutoApply());
		this.setAutoApply(this.buildAutoApply());
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public XmlConverter_2_1 getXmlManagedType() {
		return (XmlConverter_2_1) super.getXmlManagedType();
	}

	public XmlConverter_2_1 getXmlConverter() {
		return this.getXmlManagedType();
	}


	// ********** auto apply **********

	public boolean isAutoApply() {
		return this.autoApply;
	}

	protected void setAutoApply(boolean autoApply) {
		boolean old = this.autoApply;
		this.autoApply = autoApply;
		firePropertyChanged(AUTO_APPLY_PROPERTY, old, autoApply);
	}

	protected boolean buildAutoApply() {
		return this.specifiedAutoApply == null ? this.isDefaultAutoApply() : this.specifiedAutoApply.booleanValue();
	}

	public boolean isDefaultAutoApply() {
		return DEFAULT_AUTO_APPLY;
	}

	public Boolean getSpecifiedAutoApply() {
		return this.specifiedAutoApply;
	}

	public void setSpecifiedAutoApply(Boolean autoApply) {
		this.setSpecifiedAutoApply_(autoApply);
		this.getXmlConverter().setAutoApply(autoApply);
	}

	protected void setSpecifiedAutoApply_(Boolean autoApply) {
		Boolean old = this.specifiedAutoApply;
		this.specifiedAutoApply = autoApply;
		this.firePropertyChanged(SPECIFIED_AUTO_APPLY_PROPERTY, old, autoApply);
	}

	protected Boolean buildSpecifiedAutoApply() {
		return this.getXmlConverter().getAutoApply();
	}


	// ********** JpaStructureNode implementation **********

	public Class<OrmConverterType2_1> getType() {
		return OrmConverterType2_1.class;
	}

	public void dispose() {
		//do nothing
	}


	// ********** OrmManagedType implementation **********

	public int getXmlSequence() {
		return 4;
	}

	public void addXmlManagedTypeTo(XmlEntityMappings entityMappings) {
		entityMappings.getConverters().add(this.getXmlConverter());
	}

	public void removeXmlManagedTypeFrom(XmlEntityMappings entityMappings) {
		entityMappings.getConverters().remove(this.getXmlConverter());
	}

	@Override
	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt) {
		return jrt != null ? this.getJpaFactory2_1().buildJavaConverterType(this, jrt) : null;
	}
}

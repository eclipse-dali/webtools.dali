/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;

public class OrmEclipseLinkCustomizer extends AbstractOrmXmlContextNode
	implements EclipseLinkCustomizer
{
	protected final XmlCustomizerHolder resource;
	
	protected String specifiedCustomizerClass;
	
	protected String defaultCustomizerClass;
	
	public OrmEclipseLinkCustomizer(OrmTypeMapping parent, XmlCustomizerHolder resource, EclipseLinkCustomizer javaCustomizer) {
		super(parent);
		this.resource = resource;
		this.defaultCustomizerClass = getJavaCustomizerClass(javaCustomizer);
		this.specifiedCustomizerClass = getResourceCustomizerClass();
	}
	
	public char getCustomizerClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getCustomizerClass() {
		return this.specifiedCustomizerClass == null ? this.defaultCustomizerClass : this.specifiedCustomizerClass;
	}
	
	public String getDefaultCustomizerClass() {
		return this.defaultCustomizerClass;
	}
	
	protected void setDefaultCustomizerClass(String newDefaultCustomizerClass) {
		String oldDefaultCustomizerClass = this.defaultCustomizerClass;
		this.defaultCustomizerClass = newDefaultCustomizerClass;
		firePropertyChanged(DEFAULT_CUSTOMIZER_CLASS_PROPERTY, oldDefaultCustomizerClass, newDefaultCustomizerClass);
	}
	
	public String getSpecifiedCustomizerClass() {
		return this.specifiedCustomizerClass;
	}
	
	public void setSpecifiedCustomizerClass(String newCustomizerClass) {
		String oldCustomizerClass = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = newCustomizerClass;
		if (oldCustomizerClass != newCustomizerClass) {
			if (this.getResourceCustomizer() != null) {
				this.getResourceCustomizer().setCustomizerClassName(newCustomizerClass);						
				if (this.getResourceCustomizer().isUnset()) {
					removeResourceCustomizer();
				}
			}
			else if (newCustomizerClass != null) {
				addResourceCustomizer();
				getResourceCustomizer().setCustomizerClassName(newCustomizerClass);
			}
		}
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}
	
	protected void setSpecifiedCustomizerClass_(String newCustomizerClass) {
		String oldCustomizerClass = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = newCustomizerClass;
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}
	
	protected XmlCustomizer getResourceCustomizer() {
		return this.resource.getCustomizer();
	}
	
	protected void addResourceCustomizer() {
		this.resource.setCustomizer(EclipseLinkOrmFactory.eINSTANCE.createXmlCustomizer());		
	}
	
	protected void removeResourceCustomizer() {
		this.resource.setCustomizer(null);
	}
	
	
	// **************** updating **************************************
	
	protected void update(EclipseLinkCustomizer javaCustomizer) {
		setDefaultCustomizerClass(getJavaCustomizerClass(javaCustomizer));
		setSpecifiedCustomizerClass_(getResourceCustomizerClass());
	}
	
	protected String getJavaCustomizerClass(EclipseLinkCustomizer javaCustomizer) {
		return (javaCustomizer == null) ? null : javaCustomizer.getCustomizerClass();
	}
	
	protected String getResourceCustomizerClass() {
		XmlCustomizer resource = getResourceCustomizer();
		return (resource == null) ? null : resource.getCustomizerClassName();
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		XmlCustomizer resource = getResourceCustomizer();
		return resource == null ? null : resource.getCustomizerClassNameTextRange();
	}

}

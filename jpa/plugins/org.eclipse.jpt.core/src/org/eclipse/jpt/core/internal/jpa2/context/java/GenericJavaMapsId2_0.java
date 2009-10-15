/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMapsId2_0
	extends AbstractJavaJpaContextNode
	implements JavaMapsId2_0
{
	protected String value;
	
	
	public GenericJavaMapsId2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	
	@Override
	public JavaSingleRelationshipMapping2_0 getParent() {
		return (JavaSingleRelationshipMapping2_0) super.getParent();
	}
	
	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return getParent().getPersistentAttribute().getResourcePersistentAttribute();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String newValue) {
		if (StringTools.stringsAreEqual(this.value, newValue)) {
			return;
		}
		String oldValue = this.value;
		this.value = newValue;
		
		if (newValue != null) {
			if (getMapsIdAnnotation() == null) {
				addMapsIdAnnotation();
			}
			getMapsIdAnnotation().setValue(newValue);
		}
		else {
			removeMapsIdAnnotation();
		}
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setValue_(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected String getResourceMapsId() {
		return (getMapsIdAnnotation() == null) ? null : getMapsIdAnnotation().getValue();
	}
	
	protected MapsId2_0Annotation getMapsIdAnnotation() {
		return (MapsId2_0Annotation) getResourceAttribute().getAnnotation(JPA2_0.MAPS_ID);
	}
	
	protected void addMapsIdAnnotation() {
		getResourceAttribute().addAnnotation(JPA2_0.MAPS_ID);
	}
	
	protected void removeMapsIdAnnotation() {
		getResourceAttribute().removeAnnotation(JPA2_0.MAPS_ID);
	}
	
	public void initialize() {
		this.value = getResourceMapsId();
	}
	
	public void update() {
		this.setValue_(getResourceMapsId());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		MapsId2_0Annotation annotation = this.getMapsIdAnnotation();
		return annotation == null ? null : annotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// no validation rules
	}
}

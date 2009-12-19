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
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMapsIdDerivedIdentityStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaMapsIdDerivedIdentityStrategy2_0
{
	protected String specifiedValue;
	
	protected String defaultValue;
	
	
	public GenericJavaMapsIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
	}
	
	
	public JavaDerivedIdentity2_0 getDerivedIdentity() {
		return (JavaDerivedIdentity2_0) super.getParent();
	}
	
	public JavaSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
	}
	
	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return getMapping().getPersistentAttribute().getResourcePersistentAttribute();
	}
	
	protected MapsId2_0Annotation getAnnotation() {
		return (MapsId2_0Annotation) getResourceAttribute().getNonNullAnnotation(JPA2_0.MAPS_ID);
	}
	
	protected MapsId2_0Annotation getAnnotationOrNull() {
		return (MapsId2_0Annotation) getResourceAttribute().getAnnotation(JPA2_0.MAPS_ID);
	}
	
	protected String getResourceValue() {
		return getAnnotation().getValue();
	}
	
	protected String calculateDefaultValue() {
		// TODO
		return null;
	}
	
	protected void addAnnotation() {
		getResourceAttribute().addAnnotation(JPA2_0.MAPS_ID);
	}
	
	protected void removeAnnotation() {
		getResourceAttribute().removeAnnotation(JPA2_0.MAPS_ID);
	}
	
	public String getSpecifiedValue() {
		return this.specifiedValue;
	}
	
	public void setSpecifiedValue(String newValue) {
		if (StringTools.stringsAreEqual(this.specifiedValue, newValue)) {
			return;
		}
		String oldValue = this.specifiedValue;
		this.specifiedValue = newValue;
		getAnnotation().setValue(newValue);	
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setSpecifiedValue_(String newValue) {
		String oldValue = this.specifiedValue;
		this.specifiedValue = newValue;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	protected void setDefaultValue_(String newValue) {
		if (StringTools.stringsAreEqual(this.defaultValue, newValue)) {
			return;
		}
		String oldValue = this.defaultValue;
		this.defaultValue = newValue;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	public String getValue() {
		return (this.specifiedValue != null) ? this.specifiedValue : this.defaultValue;
	}
	
	public Iterable<String> getSortedValueChoices() {
		// TODO
		return EmptyIterable.<String>instance();
	}
	
	public boolean isSpecified() {
		return getAnnotationOrNull() != null;
	}
	
	public void addStrategy() {
		if (getAnnotationOrNull() == null) {
			addAnnotation();
		}
	}
	
	public void removeStrategy() {
		if (getAnnotationOrNull() != null) {
			removeAnnotation();
		}
	}
	
	public void initialize() {
		this.specifiedValue = getResourceValue();
		this.defaultValue = calculateDefaultValue();
	}
	
	public void update() {
		this.setSpecifiedValue_(getResourceValue());
		this.setDefaultValue_(calculateDefaultValue());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getAnnotation().getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// no validation rules
	}
}

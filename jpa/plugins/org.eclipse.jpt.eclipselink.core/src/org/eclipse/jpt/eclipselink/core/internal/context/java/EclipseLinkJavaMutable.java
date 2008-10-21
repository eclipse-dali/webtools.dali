/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.java.MutableAnnotation;

public class EclipseLinkJavaMutable extends AbstractJavaJpaContextNode implements Mutable
{
	protected boolean defaultMutable;
	protected Boolean specifiedMutable;
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public EclipseLinkJavaMutable(JavaAttributeMapping parent) {
		super(parent);
	}
	
	protected String getMutableAnnotationName() {
		return MutableAnnotation.ANNOTATION_NAME;
	}
	
	protected MutableAnnotation getResourceMutable() {
		return (MutableAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getMutableAnnotationName());
	}
	
	protected void addResourceMutable() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getMutableAnnotationName());
	}
	
	protected void removeResourceMutable() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getMutableAnnotationName());
	}

	protected boolean calculateDefaultMutable() {
		if (this.resourcePersistentAttribute.typeIsDateOrCalendar()) {
			//TODO calculate the default based on the persistence.xml mutable 
			//property setting  for Date and Calendar bug 228042
			return false;
		}
		return this.resourcePersistentAttribute.typeIsSerializable();
	}

	public boolean isMutable() {
		return this.specifiedMutable != null ? this.specifiedMutable.booleanValue() : this.defaultMutable; 
	}
	
	public boolean isDefaultMutable() {
		return this.defaultMutable;
	}
	
	protected void setDefaultMutable(boolean newDefaultMutable) {
		boolean oldDefaultMutable = this.defaultMutable;
		this.defaultMutable = newDefaultMutable;
		firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, oldDefaultMutable, newDefaultMutable);
	}
	
	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}
	
	public void setSpecifiedMutable(Boolean newSpecifiedMutable) {
		if (this.specifiedMutable == newSpecifiedMutable) {
			return;
		}
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;

		if (newSpecifiedMutable != null) {
			if (getResourceMutable() == null) {
				addResourceMutable();
			}
			if (newSpecifiedMutable.booleanValue()) {
				if (getResourceMutable().getValue() == Boolean.FALSE) {
					getResourceMutable().setValue(null);
				}
			}
			else {
				getResourceMutable().setValue(Boolean.FALSE);
			}
		}
		else {
			//have to check if annotation exists in case the change is from false to null or vice versa
			if (getResourceMutable() != null) {
				removeResourceMutable();
			}
		}
		firePropertyChanged(Mutable.SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	protected void setSpecifiedMutable_(Boolean newSpecifiedMutable) {
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		MutableAnnotation resourceMutable = this.getResourceMutable();
		this.specifiedMutable = this.specifiedMutable(resourceMutable);
		this.defaultMutable = this.calculateDefaultMutable();
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		MutableAnnotation resourceMutable = this.getResourceMutable();
		this.setSpecifiedMutable_(this.specifiedMutable(resourceMutable));
		this.setDefaultMutable(this.calculateDefaultMutable());
	}
	
	private Boolean specifiedMutable(MutableAnnotation resourceMutable) {
		if (resourceMutable == null) {
			return null;
		}
		if (resourceMutable.getValue() == null) { //@Mutable is equivalent to @Mutable(true)
			return Boolean.TRUE;
		}
		return resourceMutable.getValue();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		MutableAnnotation resourceMutable = this.getResourceMutable();
		return resourceMutable == null ? null : resourceMutable.getTextRange(astRoot);
	}

}

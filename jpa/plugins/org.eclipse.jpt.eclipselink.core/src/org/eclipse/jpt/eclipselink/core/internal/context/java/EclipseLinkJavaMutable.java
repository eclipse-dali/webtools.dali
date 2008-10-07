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
import org.eclipse.jpt.eclipselink.core.context.java.JavaMutable;
import org.eclipse.jpt.eclipselink.core.resource.java.MutableAnnotation;

public class EclipseLinkJavaMutable extends AbstractJavaJpaContextNode implements JavaMutable
{
	
	protected boolean mutable;	
	protected Boolean defaultMutable;
	protected Boolean specifiedMutable;
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public EclipseLinkJavaMutable(JavaAttributeMapping parent) {
		super(parent);
	}
	
	protected String getMutableAnnotationName() {
		return MutableAnnotation.ANNOTATION_NAME;
	}
	
	protected MutableAnnotation getResourceMutable() {
		return (MutableAnnotation) this.resourcePersistentAttribute.getAnnotation(getMutableAnnotationName());
	}
	
	protected void addResourceMutable() {
		this.resourcePersistentAttribute.addAnnotation(getMutableAnnotationName());
	}
	
	protected void removeResourceMutable() {
		this.resourcePersistentAttribute.removeAnnotation(getMutableAnnotationName());
	}
	
	public boolean hasMutable() {
		return this.mutable;
	}
	
	public void setMutable(boolean newMutable) {
		boolean oldMutable = this.mutable;
		this.mutable = newMutable;
		if (newMutable) {
			addResourceMutable();
		}
		else {
			removeResourceMutable();
		}
		firePropertyChanged(MUTABLE_PROPERTY, oldMutable, newMutable);
		setDefaultMutable(caclulateDefaultMutable());
	}
	
	protected void setMutable_(boolean newMutable) {
		boolean oldMutable = this.mutable;
		this.mutable = newMutable;
		firePropertyChanged(MUTABLE_PROPERTY, oldMutable, newMutable);
	}

	protected Boolean caclulateDefaultMutable() {
		if (hasMutable()) {
			return Boolean.TRUE;
		}
		if (this.resourcePersistentAttribute.typeIsDateOrCalendar()) {
			//TODO calculate the default based on the persistence.xml mutable 
			//property setting  for Date and Calendar bug 228042
			return Boolean.FALSE;
		}
		return Boolean.valueOf(this.resourcePersistentAttribute.typeIsSerializable());
	}

	public Boolean getMutable() {
		return this.specifiedMutable != null ? this.specifiedMutable : this.defaultMutable; 
	}
	
	public Boolean getDefaultMutable() {
		return this.defaultMutable;
	}
	
	protected void setDefaultMutable(Boolean newDefaultMutable) {
		Boolean oldDefaultMutable = this.defaultMutable;
		this.defaultMutable = newDefaultMutable;
		firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, oldDefaultMutable, newDefaultMutable);
	}
	
	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}
	
	public void setSpecifiedMutable(Boolean newSpecifiedMutable) {
		if (!hasMutable()) {
			if (newSpecifiedMutable != null) {
				setMutable(true);
			}
			else {
				return;
			}
		}		
		Boolean oldMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		this.getResourceMutable().setValue(newSpecifiedMutable);
		firePropertyChanged(Mutable.SPECIFIED_MUTABLE_PROPERTY, oldMutable, newSpecifiedMutable);
	}
	
	protected void setSpecifiedMutable_(Boolean newSpecifiedMutable) {
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		MutableAnnotation resourceMutable = this.getResourceMutable();
		this.mutable = resourceMutable != null;
		this.specifiedMutable = this.specifiedMutable(resourceMutable);
		this.defaultMutable = this.caclulateDefaultMutable();
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		MutableAnnotation resourceMutable = this.getResourceMutable();
		setMutable_(resourceMutable != null);
		this.setSpecifiedMutable_(this.specifiedMutable(resourceMutable));
		this.setDefaultMutable(this.caclulateDefaultMutable());
	}
	
	private Boolean specifiedMutable(MutableAnnotation resourceMutable) {
		return resourceMutable == null ? null : resourceMutable.getValue();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		MutableAnnotation resourceMutable = this.getResourceMutable();
		return resourceMutable == null ? null : resourceMutable.getTextRange(astRoot);
	}

}

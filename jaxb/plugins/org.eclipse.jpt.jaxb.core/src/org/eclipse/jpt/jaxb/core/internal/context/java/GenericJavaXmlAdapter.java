/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlAdapter;


public class GenericJavaXmlAdapter
		extends AbstractJavaContextNode
		implements XmlAdapter {
	
	protected final JavaResourceType resourceType;
	
	protected String boundType;
	
	protected String valueType;
	
	
	public GenericJavaXmlAdapter(JaxbContextNode parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		initBoundAndValueTypes();
	}
	
	
	public JavaResourceType getJavaResourceType() {
		return this.resourceType;
	}
	
	
	// ***** overrides *****
	
	@Override
	public IResource getResource() {
		return this.resourceType.getFile();
	}
	
	
	// ***** sync/update *****
	
	protected void initBoundAndValueTypes() {
		String[] types = findBoundAndValueTypes(getJavaResourceType());
		this.boundType = types[0];
		this.valueType = types[1];
	}
	
	
	@Override
	public void update() {
		super.update();
		// these are in update, since their values may depend on other classes
		updateBoundAndValueTypes();
	}
	
	protected void updateBoundAndValueTypes() {
		String[] types = findBoundAndValueTypes(getJavaResourceType());
		setBoundType_(types[0]);
		setValueType_(types[1]);
	}
	
	protected String[] findBoundAndValueTypes(JavaResourceType resourceType) {
		for (JavaResourceMethod resourceMethod : resourceType.getMethods()) {
			if (MARSHAL_METHOD_NAME.equals(resourceMethod.getName()) && resourceMethod.getParametersSize() == 1) {
				String valueType = resourceMethod.getTypeBinding().getQualifiedName();
				String boundType = resourceMethod.getParameterTypeName(0);
				return new String[] { boundType, valueType };
			}
		}
		
		String superResourceTypeName = resourceType.getSuperclassQualifiedName();
		if (! StringTools.isBlank(superResourceTypeName)) {
			JavaResourceType superResourceType 
					= (JavaResourceType) getJaxbProject().getJavaResourceType(superResourceTypeName, JavaResourceAbstractType.AstNodeType.TYPE);
			if (superResourceType != null) {
				return findBoundAndValueTypes(superResourceType);
			}
		}
		
		String objectTypeName = Object.class.getName();
		return new String[] { objectTypeName, objectTypeName };
	}
	
	
	// ***** bound type *****
	
	public String getBoundType() {
		return this.boundType;
	}
	
	protected void setBoundType_(String newType) {
		String oldType = this.boundType;
		this.boundType = newType;
		firePropertyChanged(BOUND_TYPE_PROPERTY, oldType, newType);
	}
	
	
	// ***** value type *****
	
	public String getValueType() {
		return this.valueType;
	}
	
	protected void setValueType_(String newType) {
		String oldType = this.valueType;
		this.valueType = newType;
		firePropertyChanged(VALUE_TYPE_PROPERTY, oldType, newType);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return getJavaResourceType().getNameTextRange();
	}
}

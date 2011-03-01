/*******************************************************************************
 *  Copyright (c) 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.HashSet;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbTransientClass;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;


public class GenericJavaTransientClass
		extends AbstractJavaType
		implements JaxbTransientClass {

	protected JaxbClass superClass;

	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder specifiedAccessOrder;

	public GenericJavaTransientClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.superClass = this.buildSuperClass();
		this.specifiedAccessType = this.getResourceAccessType();
		this.specifiedAccessOrder = this.getResourceAccessOrder();
	}

	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}

	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) super.getParent();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccessType_(this.getResourceAccessType());
		this.setSpecifiedAccessOrder_(this.getResourceAccessOrder());
	}

	@Override
	public void update() {
		super.update();
		this.setSuperClass(this.buildSuperClass());
	}


	// ********** xml transient annotation **********

	protected XmlTransientAnnotation getXmlTransientAnnotation() {
		return (XmlTransientAnnotation) this.getJavaResourceType().getNonNullAnnotation(XmlTransientAnnotation.ANNOTATION_NAME);
	}
	
	
	// ********** JaxbType impl **********
	
	public Kind getKind() {
		return Kind.TRANSIENT;
	}


	// ********** super class **********

	public JaxbClass getSuperClass() {
		return this.superClass;
	}

	protected void setSuperClass(JaxbClass superClass) {
		JaxbClass old = this.superClass;
		this.superClass = superClass;
		this.firePropertyChanged(SUPER_CLASS_PROPERTY, old, superClass);
	}

	protected JaxbClass buildSuperClass() {
		HashSet<JavaResourceType> visited = new HashSet<JavaResourceType>();
		visited.add(this.getJavaResourceType());
		JaxbClass spc = this.getSuperClass(this.getJavaResourceType().getSuperclassQualifiedName(), visited);
		if (spc == null) {
			return null;
		}
		if (CollectionTools.contains(spc.getInheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return spc;
	}

	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit. If it is not found we use
	 * resource persistent type and look for *its* super type.
	 * 
	 * The 'visited' collection is used to detect a cycle in the *resource* type
	 * inheritance hierarchy and prevent the resulting stack overflow.
	 * Any cycles in the *context* type inheritance hierarchy are handled in
	 * #buildSuperPersistentType().
	 */
	protected JaxbClass getSuperClass(String typeName, Collection<JavaResourceType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourceType resourceType = (JavaResourceType) getJaxbProject().getJavaResourceType(typeName, JavaResourceAbstractType.Kind.TYPE);
		if ((resourceType == null) || visited.contains(resourceType)) {
			return null;
		}
		visited.add(resourceType);
		JaxbClass spc = this.getClass(typeName);
		return (spc != null && resourceType.isMapped()) ? spc : this.getSuperClass(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected JaxbClass getClass(String fullyQualifiedTypeName) {
		return this.getParent().getClass(fullyQualifiedTypeName);
	}
	

	// ********** inheritance **********

	public Iterable<JaxbClass> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterable<JaxbClass> getAncestors() {
		return this.getInheritanceHierarchyOf(this.superClass);
	}

	protected Iterable<JaxbClass> getInheritanceHierarchyOf(JaxbClass start) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterable<JaxbClass>(start) {
			@Override
			protected JaxbClass nextLink(JaxbClass jaxbClass) {
				return jaxbClass.getSuperClass();
			}
		};
	}


	// ********** access type **********

	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.getDefaultAccessType();
	}

	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}

	public void setSpecifiedAccessType(XmlAccessType access) {
		this.getAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
		this.setSpecifiedAccessType_(access);
	}

	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}

	public XmlAccessType getDefaultAccessType() {
		return null;
	}

	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(this.getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
	}


	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.getDefaultAccessOrder();
	}

	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}

	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		this.getAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
		this.setSpecifiedAccessOrder_(accessOrder);
	}

	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		this.firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	public XmlAccessOrder getDefaultAccessOrder() {
		return null;
	}

	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(this.getAccessorOrderAnnotation().getValue());
	}

	protected XmlAccessorOrderAnnotation getAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
	}

	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getXmlTransientAnnotation().getTextRange(astRoot);
	}
}

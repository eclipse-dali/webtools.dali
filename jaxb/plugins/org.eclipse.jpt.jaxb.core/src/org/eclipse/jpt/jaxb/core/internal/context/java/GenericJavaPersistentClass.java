/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.HashSet;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ChainIterable;

public class GenericJavaPersistentClass
		extends AbstractJavaPersistentType
		implements JaxbPersistentClass {

	protected JaxbPersistentClass superPersistentClass;

	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;

	public GenericJavaPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.superPersistentClass = this.buildSuperPersistentClass();
		this.specifiedAccessType = this.getResourceAccessType();
		this.specifiedAccessOrder = this.getResourceAccessOrder();
		this.defaultAccessType = this.buildDefaultAccessType();
		this.defaultAccessOrder = this.buildDefaultAccessOrder();
	}

	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
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
		this.setSuperPersistentClass(this.buildSuperPersistentClass());
		this.setDefaultAccessType(this.buildDefaultAccessType());
		this.setDefaultAccessOrder(this.buildDefaultAccessOrder());
	}


	// ********** JaxbType impl **********

	public Kind getKind() {
		return Kind.PERSISTENT_CLASS;
	}
	

	// ********** super persistent class **********

	public JaxbPersistentClass getSuperPersistentClass() {
		return this.superPersistentClass;
	}

	protected void setSuperPersistentClass(JaxbPersistentClass superPersistentClass) {
		JaxbPersistentClass old = this.superPersistentClass;
		this.superPersistentClass = superPersistentClass;
		this.firePropertyChanged(SUPER_PERSISTENT_CLASS_PROPERTY, old, superPersistentClass);
	}

	protected JaxbPersistentClass buildSuperPersistentClass() {
		HashSet<JavaResourceType> visited = new HashSet<JavaResourceType>();
		visited.add(this.getJavaResourceType());
		JaxbPersistentClass spc = this.getSuperPersistentClass(this.getJavaResourceType().getSuperclassQualifiedName(), visited);
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
	protected JaxbPersistentClass getSuperPersistentClass(String typeName, Collection<JavaResourceType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourceType resourceType = this.getJaxbProject().getJavaResourceType(typeName);
		if ((resourceType == null) || visited.contains(resourceType)) {
			return null;
		}
		visited.add(resourceType);
		JaxbPersistentClass spc = this.getPersistentClass(typeName);
		return (spc != null && resourceType.isMapped()) ? spc : this.getSuperPersistentClass(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected JaxbPersistentClass getPersistentClass(String fullyQualifiedTypeName) {
		return this.getParent().getPersistentClass(fullyQualifiedTypeName);
	}

	// ********** inheritance **********

	public Iterable<JaxbPersistentClass> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterable<JaxbPersistentClass> getAncestors() {
		return this.getInheritanceHierarchyOf(this.superPersistentClass);
	}

	protected Iterable<JaxbPersistentClass> getInheritanceHierarchyOf(JaxbPersistentClass start) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterable<JaxbPersistentClass>(start) {
			@Override
			protected JaxbPersistentClass nextLink(JaxbPersistentClass persistentType) {
				return persistentType.getSuperPersistentClass();
			}
		};
	}

	// ********** access type **********

	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.defaultAccessType;
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
		return this.defaultAccessType;
	}

	protected void setDefaultAccessType(XmlAccessType access) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = access;
		this.firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, access);
	}

	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(this.getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
	}

	/**
	 * If there is a @XmlAccessorType on a class, then it is used.
	 * Otherwise, if a @XmlAccessorType exists on one of its super classes, then it is inherited.
	 * Otherwise, the @XmlAccessorType on a package is inherited. 	
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType superAccessType = this.getSuperPersistentClassAccessType();
		if (superAccessType != null) {
			return superAccessType;
		}
		XmlAccessType packageAccessType = getPackageAccessType();
		if (packageAccessType != null) {
			return packageAccessType;
		}
		return XmlAccessType.PUBLIC_MEMBER;
	}

	protected XmlAccessType getSuperPersistentClassAccessType() {
		JaxbPersistentClass superPersistentClass = this.getSuperPersistentClass();
		return superPersistentClass == null ? null : superPersistentClass.getSpecifiedAccessType();
	}

	protected XmlAccessType getPackageAccessType() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessType();
	}


	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.defaultAccessOrder;
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
		return this.defaultAccessOrder;
	}

	protected void setDefaultAccessOrder(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.defaultAccessOrder;
		this.defaultAccessOrder = accessOrder;
		this.firePropertyChanged(DEFAULT_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(this.getAccessorOrderAnnotation().getValue());
	}

	protected XmlAccessorOrderAnnotation getAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
	}

	/**
    * If there is a @XmlAccessorOrder on a class, then it is used.
    * Otherwise, if a @XmlAccessorOrder exists on one of its super classes, then it is inherited (by the virtue of Inherited)
    * Otherwise, the @XmlAccessorOrder on the package of the class is used, if it's there.
    * Otherwise XmlAccessOrder.UNDEFINED. 
  	*/
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder superAccessOrder = this.getSuperPersistentClassAccessOrder();
		if (superAccessOrder != null) {
			return superAccessOrder;
		}
		XmlAccessOrder packageAccessOrder = getPackageAccessOrder();
		if (packageAccessOrder != null) {
			return packageAccessOrder;
		}
		return XmlAccessOrder.UNDEFINED;
	}

	protected XmlAccessOrder getSuperPersistentClassAccessOrder() {
		JaxbPersistentClass superPersistentClass = this.getSuperPersistentClass();
		return superPersistentClass == null ? null : superPersistentClass.getSpecifiedAccessOrder();
	}

	protected XmlAccessOrder getPackageAccessOrder() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessOrder();
	}

}

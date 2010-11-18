package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;


public class GenericJavaPersistentClass
		extends AbstractJaxbContextNode
		implements JaxbPersistentClass {
	
	protected final JavaResourceType resourceType;

	
	public GenericJavaPersistentClass(JaxbRootContextNode parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
	}
	
	
	// ********** synchronize/update **********
	
	public void synchronizeWithResourceModel() {
	}
	
	public void update() {
	}
	
	// ********** JaxbPersistentClass impl **********
	
	public String getName() {
		return this.resourceType.getQualifiedName();
	}
}
